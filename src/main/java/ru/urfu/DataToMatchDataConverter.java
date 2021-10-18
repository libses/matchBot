package ru.urfu;

/**
 * Интерфейс конвертёра из сырых данных, полученных с сервера, в более удобный формат
 */

public interface DataToMatchDataConverter {
    MatchData Convert(String data) throws Exception;
}
