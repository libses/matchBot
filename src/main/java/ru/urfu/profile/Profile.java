package ru.urfu.profile;

import java.util.*;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс профиля.
 */

public class Profile {
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


    private long telegramID;

    public long getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(long telegramID) {
        this.telegramID = telegramID;
    }


    private String telegramUserName;

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setTelegramUserName(String telegramName) {
        this.telegramUserName = telegramName;
    }


    private String vkID;

    public String getVkID() {
        return vkID;
    }

    public void setVkID(String vkID) {
        this.vkID = vkID;
    }


    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void printProfile() {
    }

}
