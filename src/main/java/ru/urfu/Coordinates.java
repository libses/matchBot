package ru.urfu;

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
