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
import ru.urfu.profile.Keyboards;
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
    final Keyboards keyboards;


    public UpdateHandler(ProfileData data, Bot bot) {
        this.bot = bot;
        this.data = data;
        registrar = new Registrar(data, bot);
        keyboards = new Keyboards();
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
        sendMessage(getChatIdFromUpdate(update), "Возвращаемся к просмотру анкет!", keyboards.main);
        var owner = getProfileFromUpdate(update);
        var currentProfile = owner.getSelector().getCurrent();
        var photo = new InputFile(currentProfile.getPhotoLink());
        sendPhoto(getChatIdFromUpdate(update), photo, keyboards.main, getCaption(currentProfile));
    }

    private void getWhoLikedMe(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var likedBy = owner.getLikedBy();

        for (Profile p : likedBy) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private Profile getProfileFromId(Long profileId) {
        return data.getMap().get(profileId);
    }

    private void getMutualSympathy(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var mutualLikes = owner.getMutualLikes();

        for (Profile p : mutualLikes) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private void getLikedByMe(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var likedByOwner = owner.getLikedProfiles();

        for (Profile p : likedByOwner) {
            sendPhotoWithCaption(update, p, getCaption(p));
        }
    }

    private void help(Update update) throws TelegramApiException {
        var chatId = getChatIdFromUpdate(update);
        var text = "Ты использовал неизвестную мне команду, пожалуйста пользуйся кнопками";
        var replyMarkup = ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("Ок, понял"))))
                .build();

        sendMessage(chatId, text, keyboards.invalidCommand);
    }

    private void sendMessage(String chatId, String text, ReplyKeyboardMarkup replyMarkup) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyMarkup)
                .build());
    }

    private void openAdditionalMenu(Update update) throws TelegramApiException {
        sendMessage(getChatIdFromUpdate(update), "Просмотр данных о симпатиях", keyboards.additionalMenu);
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает эксепшн при сбое api
     */
    private void handleLikeCase(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var selector = owner.getSelector();

        var currentProfile = selector.getCurrent();
        var nextProfile = selector.getNextProfile();

        var caption = getCaption(nextProfile);

        currentProfile.addLikedBy(owner);
        owner.addToLiked(currentProfile);

        if (owner.getLikedBy().contains(nextProfile)) {
            caption = "Ты понравился одному человеку!\n" + caption;
        }

        sendPhotoWithCaption(update, nextProfile, caption);
    }


    /**
     * Метод, обрабатывающий ситуацию дизлайка.
     *
     * @param update апдейт от бота
     * @throws TelegramApiException бросает при сбое апи
     */
    private void handleDislikeCase(Update update) throws TelegramApiException {
        var owner = getProfileFromUpdate(update);
        var selector = owner.getSelector();
        var nextProfile = selector.getNextProfile();
        var caption = getCaption(nextProfile);

        if (owner.getLikedBy().contains(nextProfile)) {
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
            sendPhoto(chatId, photo, keyboards.main, message);
        }
        catch (Exception e)
        {
            sendMessage(chatId, "Анкеты кончились или произошла ошибка!", keyboards.main);
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
}
