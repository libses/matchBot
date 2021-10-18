package ru.urfu.profile;

import ru.urfu.profile.Profile;

/**
 * Интерфейс хранилища профилей. Обязует возвращать количество занятых мест (getCurrentId) и иметь возможность добавить
 * профиль
 */


public interface ProfileStorageInterface {
    int getCurrentId();
    void addProfile(Profile profile);
}
