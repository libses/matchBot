package ru.urfu;

/**
 * Интерфейс конвертёра из сырых данных, полученных с сервера, в более удобный формат
 */

public interface DataToMatchDataConverter {
    MatchData convert(String data) throws Exception;
}
