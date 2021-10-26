package ru.urfu.matching;

import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileSelector;

/**
 * Класс, используемый для того, чтобы лайкать или дизлайкать пользователей
 */

public class Matcher {
    private Profile ownersProfile;
    private ProfileSelector selector;

    public Profile getCurrent() {
        return current;
    }

    private Profile current;

    public Matcher(Profile ownersProfile, ProfileSelector selector) {
        this.ownersProfile = ownersProfile;
        this.selector = selector;
    }

    public void likeRandom(){
        current.getLikedBy().add(ownersProfile);
        //notify client part
        current = selector.getRandomProfile();
    }

    public void dislikeRandom(){
        //notify
        current = selector.getRandomProfile();
    }
    public void sendMessageRandom(){
        //notify
        current = selector.getRandomProfile();
    }

    public void sleepRandom(){
        //notify
        current = selector.getRandomProfile();
    }
}
