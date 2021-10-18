package ru.urfu.matching;

import ru.urfu.APIs.FilmYearGenreMatchData;
import ru.urfu.APIs.MusicMatchingData;

/**
 * Класс, хранящий музыкальные и кинопредпочтения
 */


public class MatchingData implements MatchData{
    private FilmYearGenreMatchData films;
    private MusicMatchingData music;
}
