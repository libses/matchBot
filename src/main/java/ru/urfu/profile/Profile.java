package ru.urfu.profile;

import com.pengrad.telegrambot.model.Location;
import ru.urfu.matching.MatchData;

import java.util.ArrayList;

public class Profile {
    private final int id;

    private String telegramID;

    private String vkID;

    public ArrayList<Profile> getLikedBy() {
        return likedBy;
    }

    private final ArrayList<Profile> likedBy;

    public String getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(String telegramID) {
        this.telegramID = telegramID;
    }

    public String getVkID() {
        return vkID;
    }

    public void setVkID(String vkID) {
        this.vkID = vkID;
    }

    private Location cords;

    public Location getCords() {
        return cords;
    }

    public void setCords(Location cords) {
        this.cords = cords;
    }

    private MatchData matchData;

    public MatchData getMatchData() {
        return matchData;
    }

    public void setMatchData(MatchData matchData) {
        this.matchData = matchData;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public Profile(int id) {
        this.id = id;
        this.likedBy = new ArrayList<>();
    }

    private String photoLink;

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

}
