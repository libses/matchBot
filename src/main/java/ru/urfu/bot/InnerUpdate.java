package ru.urfu.bot;

public class InnerUpdate implements IUpdate {

    ILocation location;

    IMessage message;

    boolean isFromTelegram;

    boolean hasLocation;

    public InnerUpdate(IMessage message, boolean isFromTelegram) {
        this.message = message;
        this.isFromTelegram = isFromTelegram;
    }

    public InnerUpdate(IMessage message, boolean isFromTelegram, ILocation location) {
        this.message = message;
        this.isFromTelegram = isFromTelegram;
        this.hasLocation = true;
        this.location = location;
    }

    public IMessage getMessage() {
        return message;
    }

    public boolean isFromTelegram() {
        return isFromTelegram;
    }

    public boolean hasLocation() {
        return hasLocation;
    }

    public ILocation getLocation() {
        return location;
    }
}
