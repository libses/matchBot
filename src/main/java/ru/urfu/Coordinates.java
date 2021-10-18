package ru.urfu;

/**
 * Класс координат, используемый для установления геотегов с полями <b>X</b> и <b>Y</b>
 */

public class Coordinates {
    private double X;
    private double Y;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public Coordinates(double x, double y) {
        X = x;
        Y = y;
    }
}
