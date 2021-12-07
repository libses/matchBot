package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

/**
 * Интерфейс для Events/Updates, необходимый во внутренней логике
 */

public interface IUpdate {

    IMessage getMessage();

    UpdateSource getUpdateSource();

    boolean hasLocation();

    ILocation getLocation();
}
