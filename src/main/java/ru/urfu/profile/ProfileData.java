package ru.urfu.profile;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


/**
 * Класс хранилище профилей, реализованный в виде Map для рандомной селекции.
 */

public class ProfileData {

    private static final Map<Long, Profile> profileList = new ConcurrentHashMap<>();

    public Map<Long, Profile> getMap() {
        return profileList;
    }


    public Stream<Profile> getAllProfiles() {
        return profileList.values().stream();
    }


    public Collection<Profile> getProfileList() {
        return profileList.values();
    }

    public void addProfile(Profile profile) {
        profileList.put(profile.ID, profile);
    }


    public static boolean containsId(long id) {
        return profileList.containsKey(id);
    }

}
