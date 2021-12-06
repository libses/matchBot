package ru.urfu.bot;

public class InnerUser implements IUser {

    private final Long id;

    private final String userName;

    private final boolean isTelegram;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isTelegramUser() {
        return isTelegram;
    }

    public InnerUser(Long id, String userName, boolean isTelegram) {
        this.id = id;
        this.userName = userName;
        this.isTelegram = isTelegram;
    }
}
