package ru.urfu.bot;

import ru.urfu.profile.Profile;

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
