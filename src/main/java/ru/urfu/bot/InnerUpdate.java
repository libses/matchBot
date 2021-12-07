package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

/**
 * Класс Event/Update, используемый для внутренней логики бота. Представляет собой обернутое сообщение.
 */

public class InnerUpdate implements IUpdate {

    private ILocation location;

    private final IMessage message;

    private boolean hasLocation;

    private UpdateSource updateSource;

    public UpdateSource getUpdateSource() {
        return updateSource;
    }

    public InnerUpdate(IMessage message, UpdateSource source) {
        this.message = message;
        this.updateSource = source;
    }

    public InnerUpdate(IMessage message, ILocation location, UpdateSource source) {
        this.message = message;
        this.updateSource = source;
        this.hasLocation = true;
        this.location = location;
    }

    public IMessage getMessage() {
        return message;
    }

    public boolean hasLocation() {
        return hasLocation;
    }

    public ILocation getLocation() {
        return location;
    }
}
