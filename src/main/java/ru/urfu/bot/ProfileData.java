package ru.urfu.bot;

import ru.urfu.bot.locations.LocationData;
import ru.urfu.profile.Profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Класс хранилище профилей, реализованный в виде Map для рандомной селекции.
 */

public class ProfileData {

    public int Count = 0;

    private final Map<Long, Profile> profileList = new ConcurrentHashMap<>();

    private final Map<Profile, ProfileSelector> selectorMap = new HashMap<>();

    public ProfileSelector getProfileSelector(Profile profile){
        return selectorMap.get(profile);
    }

    private final LocationData locationData = new LocationData();

    public LocationData getLocationData() {return locationData;}

    public Map<Long, Profile> getMap() {
        return profileList;
    }

    public Collection<Profile> getProfileList() {
        return profileList.values();
    }

    public void addProfile(Profile profile) {
        selectorMap.put(profile, new ProfileSelector(profile, this));
        profileList.put(profile.ID, profile);
        Count++;
    }

    public boolean containsId(long id) {
        return profileList.containsKey(id);
    }

}
