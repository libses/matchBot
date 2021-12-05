package ru.urfu.bot;

import ru.urfu.profile.Profile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationData {
    private final double oneMeter = (1d / 40075000d) * 360d;
    private final double oneKiloMeter = oneMeter * 1000;

    private final double precision = oneKiloMeter;

    private Map<Double, Map<Double, List<Profile>>> map;

    private Map<ILocation, List<Profile>> map2;

    public LocationData(){
        map = new HashMap<>();
        map2 = new HashMap<>();
    }

    public List<Profile> getProfilesIn(double longitude, double latitude) {
        var first = map.get(longitude);
        if (first != null) {
            return first.get(latitude);
        }
        return null;
    }

    public List<Profile> getProfilesIn(ILocation location) {
        return map2.get(location);
    }

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

    public List<Profile> getProfilesIn(double longitude, double latitude, int radius) {
        var list = new ArrayList<Profile>();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                var getted = getProfilesIn(longitude + i * precision, latitude + j * precision);
                if (getted != null) {
                    list.addAll(getted);
                }
            }
        }
        return list;
    }

//    public void addProfile(Profile profile) {
//        var kLongitude = round(profile.getLocation().getLongitude(), precisionInt);
//        var kLatitude = round(profile.getLocation().getLatitude(), precisionInt);
//        if (map.containsKey(kLongitude)) {
//            var first = map.get(kLongitude);
//            if (first.containsKey(kLatitude)) {
//                first.get(kLatitude).add(profile);
//            }
//            else
//            {
//                first.put(kLatitude, new ArrayList<>());
//                first.get(kLatitude).add(profile);
//            }
//        }
//        else {
//            map.put(kLongitude, new HashMap<Double, List<Profile>>());
//            var first = map.get(kLongitude);
//            first.put(kLatitude, new ArrayList<>());
//            first.get(kLatitude).add(profile);
//        }
//    }
//
    public void addProfile2(Profile p){
        if (!map2.containsKey(p.getLocation())) {
            map2.put(p.getLocation(), new ArrayList<>());
        }

        map2.get(p.getLocation()).add(p);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
