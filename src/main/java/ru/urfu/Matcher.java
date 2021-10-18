package ru.urfu;

public class Matcher {
    private Profile ownersProfile;
    private ProfileSelector selector;
    private Profile current;

    public void LikeRandom(){
        current.getLikedBy().add(ownersProfile);
        //notify client part
        current = selector.GetRandomProfile();
    }

    public void DislikeRandom(){
        //notify
        current = selector.GetRandomProfile();
    }
    public void SendMessageRandom(){
        //notify
        current = selector.GetRandomProfile();
    }

    public void SleepRandom(){
        //notify
        current = selector.GetRandomProfile();
    }
}
