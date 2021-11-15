package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;

import java.util.List;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final ProfileData data;
    final Registrar registrar;
    final Bot bot;
    private boolean inAdditionalMenu = false;


    public UpdateHandler(ProfileData data, Bot bot) {
        this.bot = bot;
        this.data = data;
        registrar = new Registrar(data, bot);
    }


    /**
     * Метод, принимающий и обрабатывающий полученный текст
     *
     * @param update апдейт от бота
     * @throws Exception бросает при ошибках регистрации
     */
    public void handleText(Update update) throws Exception {
        long id = getIdFromUpdate(update);
        if (!isRegistered(id)) {
            registrar.registration(update);
            return;
        }

        if (inAdditionalMenu) {
            handleTextInAdditionalMenu(update);
            return;
        }

        handleTextInDefaultMenu(update);

    }

    private void handleTextInDefaultMenu(Update update) throws TelegramApiException {
        switch (getTextFromUpdate(update)) {
            //Обрабатываем случаи когда юзер только зарегался и когда пропускает анкету
            case ("Назад"):
            case ("Ок, понял"):
            case ("Поехали!\uD83D\uDE40"):
            case ("\uD83D\uDC4E"):
                handleDislikeCase(update);
                break;
            //Обрабатываем лайк
            case ("❤️"):
                handleLikeCase(update);
                break;
            case ("Еще"):
                inAdditionalMenu = true;
                openAdditionalMenu(update);
                break;
            default:
                help(update);

        }
    }

    private void handleTextInAdditionalMenu(Update update) throws TelegramApiException {
        var message = getTextFromUpdate(update);

        switch (message) {
            case ("Назад"):
                inAdditionalMenu = false;
                bot.execute(SendMessage.builder()
                        .text("Смотрим дальше:)")
                        .chatId(getChatIdFromUpdate(update))
                        .replyMarkup(bot.defaultKeyboard)
                        .build());
                handleTextInDefaultMenu(update);
                break;

            case ("Взаимные симпатии"):
                getMutualSympathy(update);
                break;
            case ("Мне понравились"):
                getLikedByMe(update);
                break;
            case ("Я понравился"):
                getWhoLikedMe(update);
                break;
        }
    }

    private void getWhoLikedMe(Update update) throws TelegramApiException {
        var profileId = getIdFromUpdate(update);
        var likedBy = data.getMap().get(profileId).getLikedBy();

        for (Profile p : likedBy) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private void getMutualSympathy(Update update) throws TelegramApiException {
        var profileId = getIdFromUpdate(update);
        var likedByMe = data.getMap().get(profileId).getMutualLikes();

        for (Profile p : likedByMe) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private void getLikedByMe(Update update) throws TelegramApiException {
        var profileId = getIdFromUpdate(update);
        var likedByMe = data.getMap().get(profileId).getLikedProfiles();

        for (Profile p : likedByMe) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    /*
     * обрабатываетм неизвестыные команды
     */
    private void help(Update update) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .chatId(getChatIdFromUpdate(update))
                .text("Ты использовал неизвестную мне команду, пожалуйста пользуйся кнопками")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("Ок, понял"))))
                        .build())
                .build());
    }

/*
* открываем дополнительное меняю
* */
    private void openAdditionalMenu(Update update) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .text("Просмотр данных о симпатиях")
                .chatId(getChatIdFromUpdate(update))
                .replyMarkup(bot.additionalMenuKeyboard)
                .build());
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает эксепшн при сбое api
     */
    private void handleLikeCase(Update update) throws TelegramApiException {
        var profile = data.getMap().get(getIdFromUpdate(update));
        var selectorLike = profile.getSelector();

        var currentProfile = selectorLike.getCurrent();
        var nextProfileLike = selectorLike.getNextProfile();

        var captionLike = getCaption(nextProfileLike);

        currentProfile.getLikedBy().add(profile);
        profile.getLikedProfiles().add(currentProfile);

        if (profile.getLikedBy().contains(nextProfileLike)) {
            captionLike = "Ты понравился одному человеку!\n" + captionLike;
        }

        sendPhotoWithCaption(update, nextProfileLike, captionLike);
    }


    /**
     * Метод, обрабатывающий ситуацию дизлайка.
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает при сбое апи
     */
    private void handleDislikeCase(Update update) throws TelegramApiException {
        var selectorNext = data.getMap().get(getIdFromUpdate(update)).getSelector();

        var nextProfile = selectorNext.getNextProfile();
        var ownerNext = data.getMap().get(getIdFromUpdate(update));

        var caption = getCaption(nextProfile);

        if (ownerNext.getLikedBy().contains(nextProfile)) {
            caption = "Ты понравился одному человеку!\n" + caption;
        }

        sendPhotoWithCaption(update, nextProfile, caption);
    }


    /**
     * Метод, получающий текст из апдейта
     *
     * @param update апдейт от бота
     * @return возвращает сам текст
     */
    private String getTextFromUpdate(Update update) {
        return update.getMessage().getText();
    }

    /**
     * Метод, получающий ID из апдейта
     *
     * @param update апдейт от бота
     * @return возвращает сам id
     */
    private Long getIdFromUpdate(Update update) {
        return update.getMessage().getFrom().getId();
    }


    /**
     * Метод, который проверяет, зарегистрирован ли человек
     *
     * @param id принимает id на вход
     * @return возвращает boolean (да/нет)
     */
    private boolean isRegistered(long id) {
        return !(registrar.inRegistration(id) || !ProfileData.containsId(id));
    }


    /**
     * Метод для отправки фотографии с подписью
     *
     * @param update      апдейт от бота
     * @param nextProfile следующий профиль
     * @param message     само сообщение
     * @throws TelegramApiException бросает при ошибках апи
     */
    private void sendPhotoWithCaption
    (Update update, Profile nextProfile, String message)
            throws TelegramApiException {

        bot.execute(SendPhoto.builder()
                .chatId(getChatIdFromUpdate(update))
                .photo(new InputFile(nextProfile.getPhotoLink()))
                .replyMarkup(bot.defaultKeyboard)
                .caption(message)
                .build()
        );
    }


    /**
     * Метод, возвращающий айди чата
     *
     * @param update принимает апдейт от бота
     * @return возвращает строку с чат айди
     */
    private String getChatIdFromUpdate(Update update) {
        return update.getMessage().getChatId().toString();
    }


    /**
     * Метод, генерирующий подпись к фотографии
     *
     * @param nextProfile профиль для генерации
     * @return возвращает подпись
     */
    private String getCaption(Profile nextProfile) {

        return String.format("%s\n%s\n%s\n@%s",
                nextProfile.getName(),
                nextProfile.getCity(),
                nextProfile.getAge(),
                nextProfile.getTelegramUserName());
    }


    /**
     * Метод, принимающий фото
     *
     * @param update апдейт от бота
     * @throws Exception бросает внутренний метод
     */
    public void handlePhoto(Update update) throws Exception {
        registrar.registration(update);
    }


}
