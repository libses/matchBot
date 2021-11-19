package ru.urfu.profile;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Класс хранилище профилей, реализованный в виде Map для рандомной селекции.
 */

public class ProfileData {

    private static final Map<Long, Profile> profileList = new ConcurrentHashMap<>();

    public static Map<Long, Profile> getMap() {
        return profileList;
    }

    public static Collection<Profile> getProfileList() {
        return profileList.values();
    }

    public static void addProfile(Profile profile) {
        profileList.put(profile.ID, profile);
    }

    public static boolean containsId(long id) {
        return profileList.containsKey(id);
    }

}
