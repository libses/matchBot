package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.MatchHandler;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final Registrar registrar;
    final Bot bot;
    private boolean inAdditionalMenu = false;


    public UpdateHandler(Bot bot) {
        this.bot = bot;
        registrar = new Registrar(bot);
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
            registrar.registerFromUpdate(update);
            return;
        }

        if (inAdditionalMenu) {
            openAdditionalMenu(update);
            handleTextInAdditionalMenu(update);
            return;
        }

        handleTextInDefaultMenu(update);
    }


    /**
     *обрабатываем действия пользователя в основном меню
     */
    private void handleTextInDefaultMenu(Update update) throws TelegramApiException {
        switch (getTextFromUpdate(update)) {
            case ("Назад"):
            case ("Ок, понял"):
            case ("Поехали!\uD83D\uDE40"):
            case ("\uD83D\uDC4E"):
                handleNextCase(update);
                break;

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

    /**
    * Обрабатываем действия пользователя в дополнительном меню
    * */
    private void handleTextInAdditionalMenu(Update update) throws TelegramApiException {
        switch (getTextFromUpdate(update)) {
            case ("Назад"):
                break;

            case ("Взаимные \uD83D\uDC9E"):
                getMutualSympathy(update);
                break;

            case ("Мои ❤️"):
                getLikedByMe(update);
                break;

            case ("Я понравился???"):
                getWhoLikedMe(update);
                break;
        }
        inAdditionalMenu = false;
        sendMessage(getChatIdFromUpdate(update), "Возвращаемся к просмотру анкет!", Keyboards.main);
        handleNextCase(update);
    }

    /**
    * Получаем список тех, кого я лайкнул
    * */
    private void getWhoLikedMe(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var whoLikedUser = MatchHandler.getWhoLikedUser(owner);
        if (whoLikedUser.isEmpty()) {
            sendMessage(getChatIdFromUpdate(update), "Ты никому не нравишься. Совсем.", Keyboards.main);
        }
        for (Profile p : whoLikedUser) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    /**
     * получаем список взаимных симпатий
     */
    private void getMutualSympathy(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var mutual = MatchHandler.getMutualLikes(owner);
        if (mutual.isEmpty()) {
            sendMessage(getChatIdFromUpdate(update), "Нет никакой взаимности...", Keyboards.main);
        }
        for (Profile p : mutual) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    /**
     *получаем список тех кто лайкнул меня
     */
    private void getLikedByMe(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var likes = MatchHandler.getLikesByUser(owner);
        if (likes.isEmpty()) {
            sendMessage(getChatIdFromUpdate(update),
                    "Ты же прекрасно знаешь, что не ставил никому лайки. Не ломай бота",
                    Keyboards.main);
        }
        for (Profile p : likes) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private void help(Update update) throws TelegramApiException {
        var chatId = getChatIdFromUpdate(update);
        var text = "Ты использовал неизвестную мне команду, пожалуйста пользуйся кнопками";
        sendMessage(chatId, text, Keyboards.invalidCommand);
    }

    private void openAdditionalMenu(Update update) throws TelegramApiException {
        sendMessage(getChatIdFromUpdate(update), "Просмотр данных о симпатиях", Keyboards.additionalMenu);
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает эксепшн при сбое api
     */
    private void handleLikeCase(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var other = owner.getSelector().getCurrent();
        if (other.getID() != -1) {
            MatchHandler.likeProfile(owner, owner.getSelector().getCurrent());
        }
        handleNextCase(update);
    }


    /**
     * Метод, обрабатывающий ситуацию пропуска профиля.
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает при сбое апи
     */
    private void handleNextCase(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var selector = owner.getSelector();
        var nextProfile = selector.getNextProfile();
        var caption = getCaption(nextProfile);

        if (MatchHandler.isFirstLikesSecond(nextProfile, owner)) {
            caption = "Ты понравился одному человеку!\n" + caption;
        }

        sendPhotoWithCaption(update, nextProfile, caption);
    }

    private Profile getProfileFromUpdate(Update update) {
        return getProfileFromId(getIdFromUpdate(update));
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
    private void sendPhotoWithCaption(Update update, Profile nextProfile, String message) throws TelegramApiException {
        var chatId = getChatIdFromUpdate(update);
        try
        {
            var photo = new InputFile(nextProfile.getPhotoLink());
            sendPhoto(chatId, photo, Keyboards.main, message);
        }
        catch (Exception e)
        {
            sendMessage(chatId, "Анкеты кончились или произошла ошибка!", Keyboards.main);
        }

    }

    private void sendPhoto(String chatId, InputFile photo, ReplyKeyboardMarkup replyMarkup, String caption)
            throws TelegramApiException
    {
        bot.execute(SendPhoto.builder()
                .chatId(chatId)
                .photo(photo)
                .replyMarkup(replyMarkup)
                .caption(caption)
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
        registrar.registerFromUpdate(update);
    }
    private Profile getProfileFromId(Long profileId) {
        return ProfileData.getMap().get(profileId);
    }

    private void sendMessage(String chatId, String text, ReplyKeyboardMarkup replyMarkup) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyMarkup)
                .build());
    }
}
