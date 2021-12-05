package ru.urfu.bot;

import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.MatchHandler;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final Registrar registrar;
    private boolean inAdditionalMenu = false;


    public UpdateHandler() {
        registrar = new Registrar();
    }

    public void handleLocation(IUpdate update) {
        getProfileFromUpdate(update).setLocation(update.getLocation());
        ProfileData.getLocationData().addProfile2(getProfileFromUpdate(update));
        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "Позиция прикреплена!", Keyboards.main, update);
    }

    /**
     * Метод, принимающий и обрабатывающий полученный текст
     *
     * @param update апдейт от бота
     * @throws Exception бросает при ошибках регистрации
     */
    public void handleText(IUpdate update) throws Exception {
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
     * обрабатываем действия пользователя в основном меню
     */
    private void handleTextInDefaultMenu(IUpdate update) {
        switch (getTextFromUpdate(update)) {
            case ("Дальше"):
            case ("Назад"):
            case ("назад"):
            case ("Ок, понял"):
            case ("Поехали!\uD83D\uDE40"):
            case ("\uD83D\uDC4E"):
                handleNextCase(update);
                break;

            case ("Лайк"):
            case ("лайк"):
            case ("❤️"):
                handleLikeCase(update);
                break;

            case ("eщё"):
            case ("еще"):
            case ("Ещё"):
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
     */
    private void handleTextInAdditionalMenu(IUpdate update) {
        switch (getTextFromUpdate(update)) {
            case ("Назад"):
                break;

            case ("Взаимные \uD83D\uDC9E"):
                getMutualSympathy(update);
                return;

            case ("Мои ❤️"):
                getLikedByMe(update);
                return;

            case ("Я понравился???"):
                getWhoLikedMe(update);
                return;
        }
        inAdditionalMenu = false;
        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "Возвращаемся к просмотру анкет!", Keyboards.main, update);
        handleNextCase(update);
    }

    /**
     * Получаем список тех, кого я лайкнул
     */
    private void getWhoLikedMe(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var whoLikedUser = MatchHandler.getWhoLikedUser(owner);
        if (whoLikedUser.isEmpty()) {
            MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "Ты никому не нравишься. Совсем.", Keyboards.additionalMenu, update);
        }
        for (Profile p : whoLikedUser) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "с:", Keyboards.additionalMenu, update);
    }

    /**
     * получаем список взаимных симпатий
     */
    private void getMutualSympathy(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var mutual = MatchHandler.getMutualLikes(owner);
        if (mutual.isEmpty()) {
            MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "Нет никакой взаимности...", Keyboards.additionalMenu, update);
        }
        for (Profile p : mutual) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "с:", Keyboards.additionalMenu, update);
    }

    /**
     * получаем список тех кто лайкнул меня
     */
    private void getLikedByMe(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var likes = MatchHandler.getLikesByUser(owner);
        if (likes.isEmpty()) {
            MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update),
                    "Ты же прекрасно знаешь, что не ставил никому лайки. Не ломай бота",
                    Keyboards.additionalMenu, update);
        }
        for (Profile p : likes) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "с:", Keyboards.additionalMenu, update);
    }

    private void help(IUpdate update) {
        var chatId = getChatIdFromUpdate(update);
        var text = "Ты использовал неизвестную мне команду, пожалуйста пользуйся кнопками";
        MessageSender.sendMessageWithKeyboard(chatId, text, Keyboards.invalidCommand, update);
    }

    private void openAdditionalMenu(IUpdate update) {
        MessageSender.sendMessageWithKeyboard(getChatIdFromUpdate(update), "Просмотр данных о симпатиях", Keyboards.additionalMenu, update);
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт от бота
     */
    private void handleLikeCase(IUpdate update) {
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
     */
    private void handleNextCase(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var selector = owner.getSelector();
        var nextProfile = selector.getNextProfile();
        var caption = getCaption(nextProfile);

        if (MatchHandler.isFirstLikesSecond(nextProfile.getProfile(), owner)) {
            caption = "Ты понравился одному человеку!\n" + caption;
        }

        MessageSender.sendPhotoWithCaption(update, nextProfile.getProfile(), caption);
    }

    private Profile getProfileFromUpdate(IUpdate update) {
        return getProfileFromId(getIdFromUpdate(update));
    }


    /**
     * Метод, получающий текст из апдейта
     *
     * @param update апдейт от бота
     * @return возвращает сам текст
     */
    private String getTextFromUpdate(IUpdate update) {
        return update.getMessage().getText();
    }

    /**
     * Метод, получающий ID из апдейта
     *
     * @param update апдейт от бота
     * @return возвращает сам id
     */
    private Long getIdFromUpdate(IUpdate update) {
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
     * Метод, возвращающий айди чата
     *
     * @param update принимает апдейт от бота
     * @return возвращает строку с чат айди
     */
    private String getChatIdFromUpdate(IUpdate update) {
        return update.getMessage().getChatId().toString();
    }


    /**
     * Метод, генерирующий подпись к фотографии
     *
     * @param nextProfileWrapper профиль для генерации
     * @return возвращает подпись
     */
    private String getCaption(ProfileWrapper nextProfileWrapper) {

        var nextProfile = nextProfileWrapper.getProfile();
        return nextProfileWrapper.getInformation() + String.format("%s\n%s\n%s\n@%s",
                nextProfile.getName(),
                nextProfile.getCity(),
                nextProfile.getAge(),
                nextProfile.getTelegramUserName());
    }

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
    public void handlePhoto(IUpdate update) throws Exception {
        registrar.registerFromUpdate(update);
    }

    private Profile getProfileFromId(Long profileId) {
        return ProfileData.getMap().get(profileId);
    }
}
