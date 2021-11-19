package ru.urfu.profile;

import java.util.*;

public class MatchHandler {
    static final Map<Profile, Set<Profile>> likesTo = new HashMap<>();
    static final Map<Profile, Set<Profile>> likedBy = new HashMap<>();

    public static void likeProfile(Profile liker, Profile liked) {
        AddInListOrCreate(likesTo, liker, liked);
        AddInListOrCreate(likedBy, liked, liker);
    }
    public static Set<Profile> getLikesByUser(Profile profile) {
        return likesTo.get(profile);
    }

    public static void addUser(Profile profile) {
        if (!"".equals(profile.getName())) {
            likesTo.put(profile, new HashSet<>());
            likedBy.put(profile, new HashSet<>());
        }
    }

    public static Set<Profile> getWhoLikedUser(Profile profile) {
        return likedBy.get(profile);
    }

    public static boolean isFirstLikesSecond(Profile first, Profile second) {
        try {
            return likesTo.get(first).contains(second);
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Set<Profile> getMutualLikes(Profile profile) {
        HashSet<Profile> set = new HashSet<>();
        var likesBy = likedBy.get(profile);
        for (Profile p: likesTo.get(profile)) {
            if (likesBy.contains(p)) {
                set.add(p);
            }
        }
        return set;
    }

    private static void AddInListOrCreate(Map<Profile, Set<Profile>> map, Profile keyProfile, Profile valueProfile) {
        if (!map.containsKey(keyProfile)) {
            map.put(keyProfile, new HashSet<>());
        }
        map.get(keyProfile).add(valueProfile);
    }
}
