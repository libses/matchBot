package ru.urfu;

public interface FilmDataParserInterface {
    FilmYearGenreMatchData Parse(String data) throws Exception;
}
