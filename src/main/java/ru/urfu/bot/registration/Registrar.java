package ru.urfu.bot.registration;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.Bot;
import ru.urfu.profile.Gender;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;
import ru.urfu.profile.ProfileStatus;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class Registrar {
    ProfileData data;
    Bot bot;
    Map<Long, ProfileInRegistration> profilesInRegistration = new ConcurrentHashMap<>();
    final Map<String, Consumer<Update>> handlers = Map.of(
            "Имя", this::nameHandler,
            "Возраст", this::ageHandler,
            "Город", this::cityHandler,
            "Пол", this::genderHandler);


    public Registrar(ProfileData storage, Bot bot) {
        this.bot = bot;
        this.data = storage;
    }

    public void registration(Update update) throws Exception {
        var id = update.getMessage().getContact().getUserId();

        if (!ProfileData.containsId(id)) {
            var profile = new Profile(id);
            profile.setStatus(ProfileStatus.registration);
            profilesInRegistration.put(id, new ProfileInRegistration(profile));

            bot.execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text("Напиши свое имя:)")
                    .build());
            return;
        }

        handlers.get(profilesInRegistration.get(id).getCurrentRegistrationStep()).accept(update);

        if (profilesInRegistration.get(id).isRegistrationCompleted())
            profilesInRegistration.remove(id);
    }

    private long getId(Update update) {
        return update.getMessage().getContact().getUserId();
    }

    private void cityHandler(Update update) {
        profilesInRegistration.get(update.getMessage().getContact().getUserId()).updateProgress();
        try {
            bot.execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text("Отправь свое фото")
                    .build());
        } catch (Exception ignored) {
        }
    }


    private void photoHandler(Update update) {
        try {
            var photo = update.getMessage().getPhoto().get(0);

            profilesInRegistration.get(getId(update))
                    .getProfile()
                        .setPhotoLink(photo.getFileId());

        } catch (Exception ignored) {
        }
    }


    private void ageHandler(Update update) {
        try {
            var age = Integer.parseInt(update.getMessage().getText());
            var id = getId(update);
            profilesInRegistration.get(id).getProfile().setAge(age);
            profilesInRegistration.get(id).updateProgress();
        } catch (Exception e) {
            try {
                bot.execute(SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString())
                        .text("Введи возраст еще раз")
                        .build());
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void genderHandler(Update update) {
        var id = getId(update);

        if (Objects.equals(update.getMessage().getText(), "female")) {
            profilesInRegistration.get(id).getProfile().setGender(Gender.female);
        }

        profilesInRegistration.get(id).getProfile().setGender(Gender.male);
        profilesInRegistration.get(id).updateProgress();

        try {
            bot.execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text("Напиши свой возраст")
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void nameHandler(Update update) {
        var id = getId(update);

        profilesInRegistration.get(id).getProfile().setName(update.getMessage().getText());
        profilesInRegistration.get(id).updateProgress();

        try {
            bot.execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text("Напиши свой пол")
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}