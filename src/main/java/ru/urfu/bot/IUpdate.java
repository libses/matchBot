package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

/**
 * Интерфейс для Events/Updates, необходимый во внутренней логике
 */

public interface IUpdate {

    IMessage getMessage();

    boolean isFromTelegram();

    boolean hasLocation();

    ILocation getLocation();
}
