package ru.urfu.bot;

import ru.urfu.profile.Profile;

import java.util.*;

/**
 * Тут хранится информация о симпатиях
 */
public class MatchData {
    private static final Map<Profile, Set<Profile>> likesTo = new HashMap<>();
    private static final Map<Profile, Set<Profile>> likedBy = new HashMap<>();

    /**
     * Получаем профили, которые понравились юзеру
     * @param user юзер
     * @return профили
     */
    public static Set<Profile> getLikesByUser(Profile user) {
        return likesTo.get(user);
    }

    /**
     * Получаем профили, которым понравился юзер
     * @param profile юзер
     * @return профили
     */
    public static Set<Profile> getWhoLikedUser(Profile profile) {
        return likedBy.get(profile);
    }

    /**
     * Добавляем информацию о том, кого лайкнул юзер
     * @param liker юзер
     * @param liked кого лайкнул юзер
     */
    public static void likeProfile(Profile liker, Profile liked) {
        AddInListOrCreate(likesTo, liker, liked);
        AddInListOrCreate(likedBy, liked, liker);
    }

    /**
     * Добавляем юзера в коллекции
     * @param profile юзер
     */
    public static void addUser(Profile profile) {
        if (!"".equals(profile.getName())) {
            likesTo.put(profile, new HashSet<>());
            likedBy.put(profile, new HashSet<>());
        }
    }

    /**
     * Проверяем, нравится ли первому юзеру второй
     * @param first первый юзер
     * @param second второй юзер
     * @return результат
     */
    public static boolean isFirstLikesSecond(Profile first, Profile second) {
        try {
            return likesTo.get(first).contains(second);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Возвращаем взаимные лайки с юзером
     * @param profile юзер
     * @return список анкет
     */
    public static Set<Profile> getMutualLikes(Profile profile) {
        HashSet<Profile> set = new HashSet<>();
        var likesBy = likedBy.get(profile);
        for (Profile p : likesTo.get(profile)) {
            if (likesBy.contains(p)) {
                set.add(p);
            }
        }
        return set;
    }

    /**
     * Обновляем или создаем информацию о лайке
     * @param map где обновляем или создаем
     */
    private static void AddInListOrCreate(Map<Profile, Set<Profile>> map, Profile keyProfile, Profile valueProfile) {
        if (!map.containsKey(keyProfile)) {
            map.put(keyProfile, new HashSet<>());
        }
        map.get(keyProfile).add(valueProfile);
    }
}
