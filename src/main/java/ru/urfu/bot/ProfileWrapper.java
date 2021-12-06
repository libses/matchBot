package ru.urfu.bot;

import ru.urfu.profile.Profile;

/**
 * Класс обертки над профилем. Представляет собой профиль с какой-то подписью или технической информацией
 */

public class ProfileWrapper {
    private final Profile profile;
    private final String information;

    public Profile getProfile() {
        return profile;
    }

    public String getInformation() {
        return information;
    }

    public ProfileWrapper(Profile profile, String information) {
        this.information = information;
        this.profile = profile;
    }
}
