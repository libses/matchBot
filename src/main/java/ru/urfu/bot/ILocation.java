package ru.urfu.bot;

public interface ILocation {
    double getLongitude();

    double getLatitude();

    double FindDistanceTo(ILocation other);
}
