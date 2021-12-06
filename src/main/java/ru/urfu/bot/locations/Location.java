package ru.urfu.bot.locations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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

    public double FindDistanceTo(ILocation other) {
        var dX = other.getLongitude() - this.longitude;
        var dY = other.getLatitude() - this.latitude;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
