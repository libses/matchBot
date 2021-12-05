package ru.urfu.profile;

import ru.urfu.bot.LocationData;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Класс хранилище профилей, реализованный в виде Map для рандомной селекции.
 */

public class ProfileData {

    public static int Count = 0;

    private static final Map<Long, Profile> profileList = new ConcurrentHashMap<>();

    private static final LocationData locationData = new LocationData();

    public static LocationData getLocationData() {return locationData;}

    public static Map<Long, Profile> getMap() {
        return profileList;
    }

    public static Collection<Profile> getProfileList() {
        return profileList.values();
    }

    public static void addProfile(Profile profile) {
        profileList.put(profile.ID, profile);
        Count++;
    }

    public static boolean containsId(long id) {
        return profileList.containsKey(id);
    }

}
