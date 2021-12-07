package ru.urfu.bot.locations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Класс локации с точностью до одного километра ста метров
 */
public class Location implements ILocation{

    private final double longitude;
    private final double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.longitude, longitude) == 0 && Double.compare(location.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    public Location(double longitude, double latitude) {
        this.latitude = round(latitude, 2);
        this.longitude = round(longitude, 2);
    }

    /**
     * Позволяет найти приблизительную дистанцию между двумя локациями
     * @param other другая локация
     * @return возвращает расстояние
     */
    public double FindDistanceTo(ILocation other) {
        var dX = other.getLongitude() - this.longitude;
        var dY = other.getLatitude() - this.latitude;
        return Math.sqrt(dX * dX + dY * dY);
    }

    /**
     * Метод для округления даблов
     * @param value значение для округления
     * @param places количество знаков после запятой
     * @return возвращает обрезанный дабл
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
