package ru.urfu.profile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */

public class ProfileSelector {
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
    public Profile getNextProfile() {
        var liked = MatchHandler.likesTo.get(owner);
        for (Profile profile : liked) {
            if (!viewed.contains(profile)) {
                return extractProfileToCurrentAndView(profile);
            }
        }



        Collection<Profile> profileCollection = ProfileData.getProfileList();
        for (Profile p :
                profileCollection) {
            if (!viewed.contains(p) && !p.equals(owner)) {
                return extractProfileToCurrentAndView(p);
            }
        }

        viewed.clear();
        for (Profile p :
                profileCollection) {
            if (!p.equals(owner)) {
                return extractProfileToCurrentAndView(p);
            }
        }
        var emptyProfile = new Profile(-1);
        emptyProfile.setName("");
        emptyProfile.setCity("");
        emptyProfile.setTelegramUserName("");
        return extractProfileToCurrentAndView(emptyProfile);
    }

    private Profile extractProfileToCurrentAndView(Profile p) {
        viewed.add(p);
        current = p;
        return p;
    }


    public ProfileSelector(Profile owner) {
        this.owner = owner;
    }
}
