package ru.urfu.bot.registration;

import ru.urfu.bot.IUpdate;
import ru.urfu.bot.keyboards.Keyboards;
import ru.urfu.bot.MessageSender;
import ru.urfu.profile.Gender;
import ru.urfu.profile.Profile;
import ru.urfu.bot.ProfileData;
import ru.urfu.profile.ProfileStatus;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Класс регистратора
 */

public class Registrar {
    final Map<Long, ProfileInRegistration> profilesInRegistration = new ConcurrentHashMap<>();
    final Map<String, Consumer<IUpdate>> handlers = Map.of(
            "Имя", this::nameHandler,
            "Возраст", this::ageHandler,
            "Город", this::cityHandler,
            "Пол", this::genderHandler,
            "Фото", this::photoHandler);


    public Registrar() {
    }

    /**
     * Сам метод регистрации
     *
     * @param update обрабатывает изменения
     */
    public void registerFromUpdate(IUpdate update) {
        var id = update.getMessage().getFrom().getId();

        if (!profilesInRegistration.containsKey(id)) {
            var profile = new Profile(id);
            profile.setStatus(ProfileStatus.registration);
            profilesInRegistration.put(id, new ProfileInRegistration(profile));

            profile.setUserName(update.getMessage().getFrom().getUserName());

            MessageSender.sendMessage(
                    "Тебя нет в нашей базе, давай зарегистрируемся\n\nНапиши свое имя:)", update);
            return;
        }

        handlers.get(profilesInRegistration.get(id).getCurrentRegistrationStep()).accept(update);

        if (profilesInRegistration.get(id).isRegistrationCompleted()) {
            ProfileData.addProfile(profilesInRegistration.get(id).getProfile());
            profilesInRegistration.remove(id);
        }
    }

    /**
     * Получаем user id
     *
     * @param update update
     * @return id
     */
    public long getId(IUpdate update) {
        return update.getMessage().getFrom().getId();
    }

    /**
     * Получаем город
     *
     * @param update update
     */

    private void cityHandler(IUpdate update) {
        profilesInRegistration.get(getId(update)).updateProgress();
        profilesInRegistration.get(getId(update)).getProfile().setCity(update.getMessage().getText());

        try {
            MessageSender.sendMessage(
                    "Отправь фотку)", update);
        } catch (Exception ignored) {
        }
    }

    /**
     * Получаем фото
     *
     * @param update update
     */

    private void photoHandler(IUpdate update) {
        try {
            var photo = update.getMessage().getPhoto().get(0);

            profilesInRegistration.get(getId(update))
                    .getProfile()
                    .setPhotoLink(photo.getFileId());


            profilesInRegistration.get(getId(update)).updateProgress();

            MessageSender.sendMessageWithKeyboard(
                    "Ты в базе!\n" +
                     "Ты можешь отправить свою геопозицию, если в твоём клиенте есть такая функция" +
                            " и мы будем искать людей возле тебя!",
                    Keyboards.go, update);
        } catch (Exception ignored) {
        }
    }

    /**
     * Получаем возраст
     *
     * @param update update
     */

    private void ageHandler(IUpdate update) {
        try {
            var age = Integer.parseInt(update.getMessage().getText());
            if (age < 10) {
                throw new Exception();
            }
            var id = getId(update);
            profilesInRegistration.get(id).getProfile().setAge(age);
            profilesInRegistration.get(id).updateProgress();
            MessageSender.sendMessage(
                    "Напиши город", update);
        } catch (Exception e) {
            MessageSender.sendMessage(
                    "Введи ещё раз. Ты вводишь странные цифры или тебе слишком мало лет." +
                            "\nРегистрация доступна с 10 лет.", update);
        }
    }

    /**
     * Получаем пол
     *
     * @param update update
     */
    private void genderHandler(IUpdate update) {
        var id = getId(update);

        if (Objects.equals(update.getMessage().getText(), "Женский\uD83D\uDE4B\u200D♀️")) {
            profilesInRegistration.get(id).getProfile().setGender(Gender.female);
        }
        else if (Objects.equals(update.getMessage().getText(), "Non-Binary\uD83C\uDFF3️\u200D⚧️")) {
            profilesInRegistration.get(id).getProfile().setGender(Gender.other);
        }
        else if (Objects.equals(update.getMessage().getText(), "Мужской\uD83D\uDE4B\u200D♂️")) {
            profilesInRegistration.get(id).getProfile().setGender(Gender.male);
        }
        else
        {
            MessageSender.sendMessage("По нашим данным, " +
                    "такого гендера ещё не существует. Воспользуйся кнопками или подожди, когда твой " +
                    "гендер станет известен хоть кому-то, кроме тебя.", update);
            return;
        }

        profilesInRegistration.get(id).updateProgress();
        MessageSender.sendMessage(
                "Сколько тебе лет?", update);
    }

    /**
     * Получаем имя
     *
     * @param update update
     */
    public void nameHandler(IUpdate update) {
        var id = getId(update);

        profilesInRegistration.get(id).getProfile().setName(update.getMessage().getText());
        profilesInRegistration.get(id).updateProgress();

        MessageSender.sendMessageWithKeyboard(
                "Выбери гендер",
                Keyboards.genders, update);
    }

    public boolean inRegistration(long id) {
        return profilesInRegistration.containsKey(id);
    }


}
