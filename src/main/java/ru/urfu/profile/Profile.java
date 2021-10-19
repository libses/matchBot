package ru.urfu.profile;

import ru.urfu.matching.MatchData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.telegram.telegrambots.meta.api.objects.Location;
import ru.urfu.matching.Matcher;

public class Profile {
    private ProfileSelector selector;

    private Matcher matcher;

    public ProfileSelector getSelector() {
        return selector;
    }

    public void setSelector(ProfileSelector selector) {
        this.selector = selector;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    private final UUID ID;

    private static final Set<UUID> usedIDs = new HashSet<>();

    private final ArrayList<Profile> likedBy;
    public ArrayList<Profile> getLikedBy() {
        return likedBy;
    }

    private String telegramID;
    public String getTelegramID() {
        return telegramID;
    }
    public void setTelegramID(String telegramID) {
        this.telegramID = telegramID;
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

    private Gender gender;
    public Gender getGender(){return this.gender;}
    public void setGender(Gender gender){this.gender = gender;}

    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Profile(UUID id) throws Exception {
        if(usedIDs.contains(id))
            throw new Exception("Пользователь с таким id уже существует");

        this.ID = id;
        this.likedBy = new ArrayList<>();
    }

    private String photoLink;
    public String getPhotoLink() {
        return photoLink;
    }
    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public static boolean isUsedId(UUID id){
        return usedIDs.contains(id);
    }

    public void printProfile(){
        System.out.println("Profile name is: " + name);
        System.out.println("Profile gender is: " + gender);
    }

}
