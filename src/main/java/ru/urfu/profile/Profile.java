package ru.urfu.profile;

import ru.urfu.bot.locations.ILocation;
import ru.urfu.bot.MatchHandler;
import ru.urfu.bot.ProfileSelector;

import java.util.Objects;

/**
 * Класс профиля.
 */

public class Profile {
    /**
     * создаёт профиль с определенным id
     *
     * @param id id профиля
     */
    public Profile(long id) {
        this.ID = id;
        MatchHandler.addUser(this);
        this.selector = new ProfileSelector(this);
    }

    private ILocation location;

    public ILocation getLocation() {
        return location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }

    public long getID() {
        return ID;
    }

    private ProfileSelector selector;

    public ProfileSelector getSelector() {
        return selector;
    }

    public void setSelector(ProfileSelector selector) {
        this.selector = selector;
    }

    public final long ID;

    private String telegramUserName;

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setUserName(String telegramName) {
        this.telegramUserName = telegramName;
    }

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

    @Override
    public String toString() {
        return String.format("Profile id is: %s\n" +
                "Profile name is: %s\n" +
                "Profile gender is: %s\n" +
                "Profile age: %s\n",ID, name, gender, age);
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
