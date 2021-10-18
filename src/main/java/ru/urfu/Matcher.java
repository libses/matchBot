package ru.urfu;

public class Matcher {
    private Profile ownersProfile;
    private ProfileSelector selector;
    private Profile current;

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
