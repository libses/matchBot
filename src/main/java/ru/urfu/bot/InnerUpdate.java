package ru.urfu.bot;

public class InnerUpdate implements IUpdate {

    IMessage message;

    boolean isFromTelegram;

    public InnerUpdate(IMessage message, boolean isFromTelegram) {
        this.message = message;
        this.isFromTelegram = isFromTelegram;
    }

    public IMessage getMessage() {
        return message;
    }

    public boolean isFromTelegram() {
        return isFromTelegram;
    }
}
