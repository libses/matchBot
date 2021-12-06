package ru.urfu.bot;

import ru.urfu.bot.ProfileData;
import ru.urfu.bot.ProfileWrapper;
import ru.urfu.profile.Profile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */

public class ProfileSelector {
    private int counter;
    private final double oneMeter = (1d / 40075000d) * 360d;

    private final Profile owner;
    private final ArrayList<Profile> viewed = new ArrayList<>();

    public Profile getCurrent() {
        return current;
    }

    private Profile current;

    /**
     * Метод выбирает и возвращает следующий профиль
     *
     * @return профиль
     */
    public ProfileWrapper getNextProfileWrapper() {
        if (counter >= ProfileData.Count - 1) {
            viewed.clear();
            counter = 0;
        }
        if (owner.getLocation() != null) {
            var i = 10;
            var gettedList = ProfileData.getLocationData().getProfilesIn(owner.getLocation(), i);
            for (Profile profile : gettedList) {
                if (!viewed.contains(profile) && !profile.equals(owner)) {
                    double oneKiloMeter = oneMeter * 1000;
                    var distance = owner.getLocation().FindDistanceTo(profile.getLocation()) / oneKiloMeter;
                    return wrapProfile(extractProfileToCurrentAndView(profile), "Менее чем в " + ((int)distance + 1) + " км от тебя!\n");
                }
            }
        }
        Collection<Profile> profileCollection = ProfileData.getProfileList();
        for (Profile p :
                profileCollection) {
            if (!viewed.contains(p) && !p.equals(owner)) {
                return wrapProfile(extractProfileToCurrentAndView(p), "");
            }
        }

        var emptyProfile = new Profile(-1);
        emptyProfile.setName("");
        emptyProfile.setCity("");
        emptyProfile.setUserName("");
        return wrapProfile(extractProfileToCurrentAndView(emptyProfile), "");
    }

    private ProfileWrapper wrapProfile(Profile p, String info) {
        return new ProfileWrapper(p, info);
    }

    private Profile extractProfileToCurrentAndView(Profile p) {
        counter++;
        viewed.add(p);
        current = p;
        return p;
    }


    public ProfileSelector(Profile owner) {
        this.owner = owner;
    }
}
