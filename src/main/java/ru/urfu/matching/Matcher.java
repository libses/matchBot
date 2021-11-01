package ru.urfu.matching;

import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileSelector;

/**
 * Класс, используемый для того, чтобы лайкать или дизлайкать пользователей
 */

public class Matcher {
    private final Profile ownersProfile;
    private final ProfileSelector selector;

    private Profile current;

    public Matcher(Profile ownersProfile, ProfileSelector selector) {
        this.ownersProfile = ownersProfile;
        this.selector = selector;
    }

}
