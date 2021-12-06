package ru.urfu.profile;

import org.junit.Before;
import org.junit.Test;
import ru.urfu.bot.MatchHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchHandlerTest {
    private List<Profile> profileList;

    /**
     * Очищаем историю для следующих тестов
     */
    @Before
    public void setUp() {
        profileList = new ArrayList<>();

        MatchHandler.likesTo.clear();
        MatchHandler.likedBy.clear();
    }

    /**
     * Проверяем, чтобы понравившиеся анкеты добавлялись к списку юзера
     */
    @Test
    public void likeProfile_should_addUserToLiked() {
        generateProfiles();
        var liker = profileList.get(0);
        var liked = profileList.get(1);

        MatchHandler.likeProfile(liker, liked);

        assertThat(MatchHandler.likesTo.get(liker)).contains(liked);
    }

    /**
     * Анкета лайкнувшего юзера должна добавиться в список выразивших симпатию понравившегося юзера
     */
    @Test
    public void likeProfile_should_addUserToLikesTo() {
        generateProfiles();
        var liker = profileList.get(0);
        var liked = profileList.get(1);

        MatchHandler.likeProfile(liker, liked);

        assertThat(MatchHandler.likedBy.get(liked)).contains(liker);
    }


    /**
     * Проверяем что получаем верный список понравившихся профилей
     */
    @Test
    public void getLikesByUser_should_returnCorrectData() {
        generateProfiles();
        var liker = new Profile(1234);
        var expected = Set.of(profileList.get(0), profileList.get(2), profileList.get(6));

        MatchHandler.likeProfile(liker, profileList.get(0));
        MatchHandler.likeProfile(liker, profileList.get(2));
        MatchHandler.likeProfile(liker, profileList.get(6));

        var result = MatchHandler.getLikesByUser(liker);

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    /**
     * addUser должен добавлять профиль в likesTo
     */
    @Test
    public void addUser_should_addUserToLikesTo() {
        var user = new Profile(1);

        MatchHandler.addUser(user);

        assertThat(MatchHandler.likesTo).containsKey(user);
    }

    /**
     * addUser должен добавлять профиль в  likedBy
     */
    @Test
    public void addUser_should_addUserToLikedBy() {
        var user = new Profile(1);

        MatchHandler.addUser(user);

        assertThat(MatchHandler.likedBy).containsKey(user);
    }

    /**
     * проверяем чтобы полученные лайки корректо отражались у юзера
     */
    @Test
    public void getWhoLikedUser_should_returnCorrectData() {
        generateProfiles();
        var user = new Profile(1234);

        for (var profile : profileList)
            MatchHandler.likeProfile(profile, user);

        var whoLikedUser = MatchHandler.getWhoLikedUser(user);

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
            MatchHandler.likeProfile(profile, user);
            MatchHandler.likeProfile(user,profile);
        }

        var mutualLikes = MatchHandler.getMutualLikes(user);

        assertThat(mutualLikes).containsExactlyInAnyOrderElementsOf(profileList);
    }

    /**
     * Генерируем пользователей
     */
    private void generateProfiles() {
        for (var i = 0; i < 10; i++)
            profileList.add(new Profile(i));
    }
}