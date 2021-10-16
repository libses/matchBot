package ru.urfu;

import java.util.Random;

public class ProfileSelector {
    private final Random random = new Random();
    private ProfileStorage profiles;
    public Profile GetSimilarProfile(Profile first) throws Exception {
        throw new Exception("Not implemented");
    }
    public Profile GetRandomProfile() {
        return profiles.getProfileList().get(random.nextInt(profiles.getProfileList().size()));
    }

}
