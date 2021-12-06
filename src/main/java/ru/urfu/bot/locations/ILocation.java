package ru.urfu.bot.locations;

/**
 * Интерфейс для локаций на земном шаре
 */

public interface ILocation {
    double getLongitude();

    double getLatitude();

    double FindDistanceTo(ILocation other);
}
