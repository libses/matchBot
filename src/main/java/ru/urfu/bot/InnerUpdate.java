package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

/**
 * Класс Event/Update, используемый для внутренней логики бота. Представляет собой обернутое сообщение.
 */

public class InnerUpdate implements IUpdate {

    ILocation location;

    final IMessage message;

    final boolean isFromTelegram;

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
