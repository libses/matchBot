package ru.urfu.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */

public class ProfileSelector {
    private final Random random = new Random();
    private final ProfileData profiles;
    private final ArrayList<Profile> viewed = new ArrayList<>();

    public Profile getNextProfile() {
        Collection<Profile> profileCollection = profiles.getProfileList();
        for (Profile p:
             profileCollection) {
            if (!viewed.contains(p)){
                viewed.add(p);
                return p;
            }
        }
        viewed.clear();
        Profile p = profileCollection.stream().findFirst().orElseThrow();
        viewed.add(p);
        return p;
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
