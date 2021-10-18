package ru.urfu;

/**
 * Интерфейс парсера фильмов из сырой даты в понятный формат
 */
public interface FilmDataParserInterface {
    FilmYearGenreMatchData parse(String data) throws Exception;
}
