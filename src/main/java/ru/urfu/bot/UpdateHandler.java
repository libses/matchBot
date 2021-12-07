package ru.urfu.bot;

import ru.urfu.bot.keyboards.Keyboards;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.Profile;

/**
 * Класс, который принимает и обрабатывает обновления
 */
public class UpdateHandler {
    final Registrar registrar;
    private boolean inAdditionalMenu = false;
    private final ProfileData ProfileData;


    public UpdateHandler() {
        this.ProfileData = new ProfileData();
        this.registrar = new Registrar(ProfileData);
    }

    /**
     * Обрабатывает апдейт
     * @param innerUpdate апдейт
     */
    public void handleUpdate(IUpdate innerUpdate) {
        if (innerUpdate.hasLocation()) {
            handleLocation(innerUpdate);
        }
        else if (innerUpdate.getMessage().hasPhoto()) {
            try {
                handlePhoto(innerUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (innerUpdate.getMessage().hasText()) {
            try {
                handleText(innerUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }


    /**
     * Обрабатывает апдейт
     * @param update апдейт
     */
    private void handleLocation(IUpdate update) {
        getProfileFromUpdate(update).setLocation(update.getLocation());
        ProfileData.getLocationData().addProfile(getProfileFromUpdate(update));
        MessageSender.sendMessageWithKeyboard("Позиция прикреплена!", Keyboards.main, update);
    }

    /**
     * Метод, принимающий и обрабатывающий полученный текст
     *
     * @param update апдейт от бота
     */
    private void handleText(IUpdate update) {
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
        switch (getCommandFromUpdate(update)) {
            case ("Дальше"):
            case ("Назад"):
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
                return;

            default:
                help(update);
                return;
        }
        getProfileFromUpdate(update).setCurrentKeyboard(Keyboards.main);
    }

    /**
     * Обрабатываем действия пользователя в дополнительном меню
     */
    private void handleTextInAdditionalMenu(IUpdate update) {
        switch (getCommandFromUpdate(update)) {
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
        MessageSender.sendMessageWithKeyboard("Возвращаемся к просмотру анкет!", Keyboards.main, update);
        handleNextCase(update);
    }

    /**
     * Получаем список тех, кого я лайкнул
     */
    private void getWhoLikedMe(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var whoLikedUser = MatchData.getWhoLikedUser(owner);
        if (whoLikedUser.isEmpty()) {
            MessageSender.sendMessageWithKeyboard("Ты никому не нравишься. Совсем.", Keyboards.additionalMenu, update);
        }
        for (Profile p : whoLikedUser) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard("с:", Keyboards.additionalMenu, update);
    }

    /**
     * получаем список взаимных симпатий
     */
    private void getMutualSympathy(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var mutual = MatchData.getMutualLikes(owner);
        if (mutual.isEmpty()) {
            MessageSender.sendMessageWithKeyboard("Нет никакой взаимности...", Keyboards.additionalMenu, update);
        }
        for (Profile p : mutual) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard("с:", Keyboards.additionalMenu, update);
    }

    /**
     * получаем список тех кто лайкнул меня
     */
    private void getLikedByMe(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var likes = MatchData.getLikesByUser(owner);
        if (likes.isEmpty()) {
            MessageSender.sendMessageWithKeyboard(
                    "Ты же прекрасно знаешь, что не ставил никому лайки. Не ломай бота",
                    Keyboards.additionalMenu, update);
        }
        for (Profile p : likes) {
            MessageSender.sendPhotoWithCaption(update, p, getCaption(p));
        }

        MessageSender.sendMessageWithKeyboard("с:", Keyboards.additionalMenu, update);
    }

    private void help(IUpdate update) {
        var text = "Ты использовал неизвестную мне команду, пожалуйста пользуйся кнопками";
        MessageSender.sendMessageWithKeyboard(text, Keyboards.invalidCommand, update);
        getProfileFromUpdate(update).setCurrentKeyboard(Keyboards.invalidCommand);
    }

    private void openAdditionalMenu(IUpdate update) {
        getProfileFromUpdate(update).setCurrentKeyboard(Keyboards.additionalMenu);
        MessageSender.sendMessageWithKeyboard("Просмотр данных о симпатиях", Keyboards.additionalMenu, update);
    }

    /**
     * Метод, который обрабатывает ситуацию получения лайка
     *
     * @param update апдейт
     */
    private void handleLikeCase(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var selector = this.ProfileData.getProfileSelector(owner);
        var other = selector.getCurrent();
        if (other.getID() != -1) {
            MatchData.likeProfile(owner, selector.getCurrent());
        }
        handleNextCase(update);
    }


    /**
     * Метод, обрабатывающий ситуацию пропуска профиля.
     *
     * @param update апдейт
     */
    private void handleNextCase(IUpdate update) {
        var owner = getProfileFromUpdate(update);
        var selector = this.ProfileData.getProfileSelector(owner);
        var nextProfile = selector.getNextProfileWrapper();
        if (nextProfile.getProfile().ID == -1) {
            MessageSender.sendMessageWithKeyboard("К сожалению нам некого тебе показать",
                    Keyboards.invalidCommand,
                    update);

            return;
        }
        var caption = getCaption(nextProfile);

        if (MatchData.isFirstLikesSecond(nextProfile.getProfile(), owner)) {
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
     * @param update апдейт
     * @return возвращает сам текст
     */
    private String getCommandFromUpdate(IUpdate update) {
        var keyboard = getProfileFromUpdate(update).getCurrentKeyboard();
        var messageText = update.getMessage().getText();

        return keyboard.getCommand(messageText);
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
     * @param update принимает апдейт
     * @return возвращает строку с чат айди
     */
    private String getChatIdFromUpdate(IUpdate update) {
        return update.getMessage().getChatId().toString();
    }


    /**
     * Метод, генерирующий подпись к фотографии
     *
     * @param nextProfileWrapper профиль-обёртка для генерации
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

    /**
     * Метод, генерирующий подпись к фотографии
     * @param nextProfile профиль, для которого генерируется подпись
     * @return возвращает саму подпись
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
     */
    private void handlePhoto(IUpdate update) {
        registrar.registerFromUpdate(update);
    }

    private Profile getProfileFromId(Long profileId) {
        return ProfileData.getMap().get(profileId);
    }
}
