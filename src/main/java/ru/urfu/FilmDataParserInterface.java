package ru.urfu;

/**
 * Интерфейс парсера фильмов из сырой даты в понятный формат
 */
public interface FilmDataParserInterface {
    FilmYearGenreMatchData Parse(String data) throws Exception;
}
