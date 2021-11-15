package ru.urfu.profile;

import java.util.*;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Класс профиля.
 */

public class Profile {
    /**
     * создаёт профиль с определенным id
     *
     * @param id id профиля
     * @throws Exception бросает если такой id уже есть
     */
    public Profile(long id, ProfileData data) throws Exception {
        if (ProfileData.containsId(id))
            throw new Exception("Пользователь с таким id уже существует");

        this.ID = id;
        this.likedBy = new ArrayList<>();
        this.selector = new ProfileSelector(data);
    }


    private ProfileSelector selector;

    public ProfileSelector getSelector() {
        return selector;
    }

    public void setSelector(ProfileSelector selector) {
        this.selector = selector;
    }


    public final long ID;

    private final ArrayList<Profile> likedBy;

    public ArrayList<Profile> getLikedBy() {
        return likedBy;
    }


    private String telegramUserName;

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setTelegramUserName(String telegramName) {
        this.telegramUserName = telegramName;
    }


//    private MatchData matchData;
//
//    public MatchData getMatchData() {
//        return matchData;
//    }
//
//    public void setMatchData(MatchData matchData) {
//        this.matchData = matchData;

//    }


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private Gender gender;

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    private String photoLink;

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }


    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    private ProfileStatus status = ProfileStatus.registration;

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    private final List<Profile> likedProfiles = new ArrayList<>();

    public Stream<Profile> getLikedProfiles() {
        return likedProfiles.stream();
    }

    public void addToLiked(Profile profile){
        likedProfiles.add(profile);
    }

    private final List<Profile> mutualLikes = new ArrayList<>();

    public Stream<Profile> getMutualLikes() {
        return mutualLikes.stream();
    }

    public void addToMutualLikes(Profile profile){
        likedProfiles.remove(profile);
        mutualLikes.add(profile);
    }

    @Override
    public String toString() {
        return String.format("Profile name is: %s\n" +
                "Profile gender is: %s\n" +
                "Profile age: %s\n", name, gender, age);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(ID, profile.ID);
    }


    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
