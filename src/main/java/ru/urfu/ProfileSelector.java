package ru.urfu;

import java.util.Random;


/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */
public class ProfileSelector {
    private final Random random = new Random();
    private ProfileStorage profiles;
    public Profile getSimilarProfile(Profile first) throws Exception {
        throw new Exception("Not implemented");
    }
    public Profile getRandomProfile() {
        return profiles.getProfileList().get(random.nextInt(profiles.getProfileList().size()));
    }

}
