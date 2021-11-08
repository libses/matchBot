package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;
import ru.urfu.profile.ProfileSelector;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final ProfileData data;
    final Registrar registrar;
    final Bot bot;

    public UpdateHandler(ProfileData data, Bot bot) {
        this.bot = bot;
        this.data = data;
        registrar = new Registrar(data, bot);
    }


    public void handleText(Update update) throws Exception {
        long id = getIdFromUpdate(update);
        if (!isRegistered(id))
            registrar.registration(update);
        switch (getTextFromUpdate(update)) {
            //Обрабатываем случаи когда юзер только зарегался и когда пропускает анкету
            case ("Поехали!\uD83D\uDE40"):

            case ("\uD83D\uDC4E"):
                dislikeHandler(update);
                break;

            //Обрабатываем лайк
            case ("❤️"):
                likeHandler(update);
                break;
        }

    }

    private void likeHandler(Update update) throws TelegramApiException {
        var selectorLike = data.getMap().get(getIdFromUpdate(update)).getSelector();

        var currentProfile = selectorLike.getCurrent();
        var owner = data.getMap().get(getIdFromUpdate(update));
        var nextProfileLike = selectorLike.getNextProfile();

        var captionLike = getCaption(nextProfileLike);

        currentProfile.getSelector().addLiked(owner);
        currentProfile.getLikedBy().add(owner);


        if (owner.getLikedBy().contains(nextProfileLike)) {
            captionLike = "Ты понравился одному человеку!\n" + captionLike;
        }

        sendPhotoWithCaption(update, nextProfileLike, captionLike);
    }

    private void dislikeHandler(Update update) throws TelegramApiException {
        var selectorNext = data.getMap().get(getIdFromUpdate(update)).getSelector();

        var nextProfile = selectorNext.getNextProfile();
        var ownerNext = data.getMap().get(getIdFromUpdate(update));

        var caption = getCaption(nextProfile);

        if (ownerNext.getLikedBy().contains(nextProfile)) {
            caption = "Ты понравился одному человеку!\n" + caption;
        }

        sendPhotoWithCaption(update, nextProfile, caption);
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

    public void handlePhoto(Update update) throws Exception {
        registrar.registration(update);
    }


}
