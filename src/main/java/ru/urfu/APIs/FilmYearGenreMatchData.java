package ru.urfu.APIs;

/**
 * Класс информации о фильме.
 */

public class FilmYearGenreMatchData {
    private double year;
    private double russian;
    private double budget;
    private double ageRate;
    private double IMBDRating;
    private double time;
    private GenreData genreData;

    public double getYear() {
        return year;
    }

    public void setYear(double year) {
        this.year = year;
    }

    public double getRussian() {
        return russian;
    }

    public void setRussian(double russian) {
        this.russian = russian;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getAgeRate() {
        return ageRate;
    }

    public void setAgeRate(double ageRate) {
        this.ageRate = ageRate;
    }

    public double getIMBDRating() {
        return IMBDRating;
    }

    public void setIMBDRating(double IMBDRating) {
        this.IMBDRating = IMBDRating;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public GenreData getGenreData() {
        return genreData;
    }

    public void setGenreData(GenreData genreData) {
        this.genreData = genreData;
    }
}
