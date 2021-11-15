package ru.urfu.profile;

import java.lang.reflect.Array;
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
    private final Profile owner;
    private final Random random = new Random();
    private final ProfileData profiles;
    private final ArrayList<Profile> viewed = new ArrayList<>();

    public Profile getCurrent() {
        return current;
    }

    private Profile current;

    /**
     * Метод выбирает и возвращает следующий профиль
     * @return профиль
     */
    public Profile getNextProfile() {
        var liked = owner.getLikedProfiles();
        if (!liked.isEmpty()){
            var p = liked.get(0);
            liked.remove(0);
            viewed.add(p);
            current = p;
            return p;
        }
        Collection<Profile> profileCollection = profiles.getProfileList();
        for (Profile p:
             profileCollection) {
            if (!viewed.contains(p)){
                viewed.add(p);
                current = p;
                return p;
            }
        }
        viewed.clear();
        Profile p = profileCollection.stream().findFirst().orElseThrow();
        viewed.add(p);
        current = p;
        return p;
    }

    public ProfileSelector(ProfileData profiles, Profile owner) {
        this.profiles = profiles;
        this.owner = owner;
    }
}
