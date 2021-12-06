package ru.urfu.bot.locations;

import ru.urfu.profile.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, хранящий локации профилей, которые имеют локацию.
 */

public class LocationData {
    private final double oneMeter = (1d / 40075000d) * 360d;
    private final double oneKiloMeter = oneMeter * 1000;

    private final double precision = oneKiloMeter;

    private final Map<ILocation, List<Profile>> profilesInLocationMap;

    public LocationData(){
        profilesInLocationMap = new HashMap<>();
    }

    /**
     * Позволяет получить пользователей в определенной локации
     * @param location локация
     * @return возвращает список профилей
     */
    public List<Profile> getProfilesIn(ILocation location) {
        return profilesInLocationMap.get(location);
    }

    /**
     * Позволяет получить пользователей в определенном радиусе от локации
     * @param location локация
     * @param radius радиус в километрах
     * @return возвращает список профилей
     */
    public List<Profile> getProfilesIn(ILocation location, int radius) {
        var list = new ArrayList<Profile>();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                var newLocation = new Location(location.getLongitude() + i * precision, location.getLatitude() + j * precision);
                var getted = getProfilesIn(newLocation);
                if (getted != null) {
                    list.addAll(getted);
                }
            }
        }
        return list;
    }

    public void addProfile(Profile p){
        if (!profilesInLocationMap.containsKey(p.getLocation())) {
            profilesInLocationMap.put(p.getLocation(), new ArrayList<>());
        }

        profilesInLocationMap.get(p.getLocation()).add(p);
    }
}
