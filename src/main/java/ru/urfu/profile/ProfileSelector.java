package ru.urfu.profile;

import java.util.Random;


/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */
public class ProfileSelector {
    private final Random random = new Random();
    private final ProfileData profiles;

    public Profile getSimilarProfile(Profile first) throws Exception {
        throw new Exception("Not implemented");
    }

    public Profile getRandomProfile() {
        return profiles.getAllProfiles()
                .skip(random.nextInt((int) profiles.getAllProfiles().count()))
                .findAny()
                .orElseThrow();
    }

    public ProfileSelector(ProfileData profiles) {
        this.profiles = profiles;
    }
}
