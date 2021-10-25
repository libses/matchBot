package ru.urfu.profile;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;


/**
 * Класс хранилище профилей, реализованный в виде Map для рандомной селекции.
 */

public class ProfileData {

    private static final Map<Long, Profile> profileList = new ConcurrentHashMap<>();


    public Stream<Profile> getAllProfiles() {
        return profileList.values().stream();
    }

    private static final Lock lock = new ReentrantLock();

    public void addProfile(Profile profile) {
        profileList.put(profile.ID, profile);
    }

    public static boolean containsId(long id) {
        return profileList.containsKey(id);
    }

}
