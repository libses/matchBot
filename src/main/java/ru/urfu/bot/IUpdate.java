package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

/**
 * Интерфейс для Events/Updates, необходимый во внутренней логике.
 * Он хранит в себе внутреннее сообщение, источник ивента и локацию.
 */
public interface IUpdate {

    IMessage getMessage();

    UpdateSource getUpdateSource();

    boolean hasLocation();

    ILocation getLocation();
}
