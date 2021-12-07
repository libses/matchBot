package ru.urfu.profile;

import org.junit.Before;
import org.junit.Test;
import ru.urfu.bot.MatchData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchDataTest {
    private List<Profile> profileList;

    /**
     * Очищаем историю для следующих тестов
     */
    @Before
    public void setUp() {
        profileList = new ArrayList<>();
    }

    /**
     * Проверяем, чтобы понравившиеся анкеты добавлялись к списку юзера
     */
    @Test
    public void likeProfile_should_addUserToLiked() {
        generateProfiles();
        var liker = profileList.get(0);
        var liked = profileList.get(1);

        MatchData.likeProfile(liker, liked);

        assertThat(MatchData.getWhoLikedUser(liked)).contains(liker);
    }

    /**
     * Анкета лайкнувшего юзера должна добавиться в список выразивших симпатию понравившегося юзера
     */
    @Test
    public void likeProfile_should_addUserToLikesTo() {
        generateProfiles();
        var liker = profileList.get(0);
        var liked = profileList.get(1);

        MatchData.likeProfile(liker, liked);

        assertThat(MatchData.getWhoLikedUser(liked)).contains(liker);
    }


    /**
     * Проверяем что получаем верный список понравившихся профилей
     */
    @Test
    public void getLikesByUser_should_returnCorrectData() {
        generateProfiles();
        var liker = new Profile(1234);
        var expected = Set.of(profileList.get(0), profileList.get(2), profileList.get(6));

        MatchData.likeProfile(liker, profileList.get(0));
        MatchData.likeProfile(liker, profileList.get(2));
        MatchData.likeProfile(liker, profileList.get(6));

        var result = MatchData.getLikesByUser(liker);

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }


    /**
     * проверяем чтобы полученные лайки корректо отражались у юзера
     */
    @Test
    public void getWhoLikedUser_should_returnCorrectData() {
        generateProfiles();
        var user = new Profile(1234);

        for (var profile : profileList)
            MatchData.likeProfile(profile, user);

        var whoLikedUser = MatchData.getWhoLikedUser(user);

        assertThat(whoLikedUser).containsExactlyInAnyOrderElementsOf(profileList);
    }

    /**
     * Проверяем корректоное отображение взаимных лайков
     */
    @Test
    public void getMutualLikes_should_returnCorrectData() {
        generateProfiles();
        
        var user = new Profile(1123);
        for (var profile :
                profileList) {
            MatchData.likeProfile(profile, user);
            MatchData.likeProfile(user,profile);
        }

        var mutualLikes = MatchData.getMutualLikes(user);

        assertThat(mutualLikes).containsExactlyInAnyOrderElementsOf(profileList);
    }

    /**
     * Генерируем пользователей
     */
    private void generateProfiles() {
        profileList.clear();
        for (var i = 0; i < 10; i++)
            profileList.add(new Profile(i));
    }
}