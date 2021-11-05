package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;
import ru.urfu.profile.ProfileSelector;

import java.util.Objects;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final ProfileData data;
    final Registrar registrar;

    public UpdateHandler(ProfileData data, Bot bot) {
        this.data = data;
        registrar = new Registrar(data, bot);
    }


    public void handleText(Bot bot, Update update) throws Exception {
        long id = getIdFromUpdate(update);
        if (!isRegistered(id))
            registrar.registration(update);
        switch (getTextFromUpdate(update)) {
            case ("Дальше"):
                ProfileSelector selectorNext = data.getMap().get(getIdFromUpdate(update)).getSelector();
                var nextProfile = selectorNext.getNextProfile();
                var ownerNext = data.getMap().get(getIdFromUpdate(update));
                String caption = getCaption(nextProfile);
                if (ownerNext.getLikedBy().contains(nextProfile)){
                    caption = "Ты понравился одному человеку!\n"+caption;
                }
                sendPhotoWithCaption(bot, update, nextProfile, caption);
                break;
            case ("Лайк"):
                ProfileSelector selectorLike = data.getMap().get(getIdFromUpdate(update)).getSelector();
                var currentProfile = selectorLike.getCurrent();
                var owner = data.getMap().get(getIdFromUpdate(update));
                currentProfile.getSelector().addLiked(owner);
                currentProfile.getLikedBy().add(owner);
                var nextProfileLike = selectorLike.getNextProfile();
                String captionLike = getCaption(nextProfileLike);
                if (owner.getLikedBy().contains(nextProfileLike)){
                    captionLike = "Ты понравился одному человеку!\n"+captionLike;
                }
                sendPhotoWithCaption(bot, update, nextProfileLike, captionLike);
                break;
        }

    }

    private String getTextFromUpdate(Update update) {
        return update.getMessage().getText();
    }

    private Long getIdFromUpdate(Update update) {
        return update.getMessage().getFrom().getId();
    }

    private boolean isRegistered(long id) {
        return !(registrar.inRegistration(id) || !ProfileData.containsId(id));
    }

    private void sendPhotoWithCaption(Bot bot, Update update, Profile nextProfile, String message) throws TelegramApiException {
        bot.execute(SendPhoto.builder()
                .chatId(getChatIdFromUpdate(update))
                .photo(new InputFile(nextProfile.getPhotoLink()))
                .replyMarkup(bot.defaultKeyboard)
                .caption(message)
                .build()
        );
    }

    private String getChatIdFromUpdate(Update update) {
        return update.getMessage().getChatId().toString();
    }

    private String getCaption(Profile nextProfile) {
        return String.format("%s\n%s\n%s\n@%s",
                nextProfile.getName(),
                nextProfile.getCity(),
                nextProfile.getAge(),
                nextProfile.getTelegramUserName());
    }

    public void handlePhoto(Bot bot, Update update) throws Exception {
        registrar.registration(update);
    }


}
