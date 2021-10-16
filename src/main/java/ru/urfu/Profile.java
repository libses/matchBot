package ru.urfu;

public class Profile {
    private final int id;

    private Coordinates cords;

    public Coordinates getCords() {
        return cords;
    }

    public void setCords(Coordinates cords) {
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
    }
    private String photoLink;

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

}
