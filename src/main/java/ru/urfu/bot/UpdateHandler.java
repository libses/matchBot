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
        if (!isRegistered(id))
            registrar.registration(update);

        if (inAdditionalMenu) {
            additionalMenuHandler(update);
            return;
        }

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
            case ("Еще"):
                inAdditionalMenu = true;
                additionalMenu(update);
                break;
            default:
                help(update);

        }

    }

    private void additionalMenuHandler(Update update) throws TelegramApiException {
        var message = update.getMessage().getText();
        switch (message) {
            case ("Назад"):
                inAdditionalMenu = false;
                break;

            case ("Взаимные симпатии"):
            case ("Мне понравились"):
                return;
        }
        var id = update.getMessage().getChat().getId();

        bot.execute(SendMessage.builder()
                .chatId(id.toString())
                .replyMarkup(bot.defaultKeyboard)
                .build());
    }

    private void help(Update update) {
    }

    private void additionalMenu(Update update) throws TelegramApiException {
        var id = update.getMessage().getChat().getId();
        bot.execute(SendMessage.builder()
                .chatId(id.toString())
                .replyMarkup(bot.additionalMenuKeyboard)
                .build());
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает эксепшн при сбое api
     */
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


    /**
     * Метод, обрабатывающий ситуацию дизлайка.
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает при сбое апи
     */
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
