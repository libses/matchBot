package ru.urfu;

import java.util.ArrayList;

public class ProfileStorage implements ProfileStorageInterface{
    private int currentId = 0;

    public int getCurrentId() {
        return currentId;
    }

    private ArrayList<Profile> profileList;

    public ArrayList<Profile> getProfileList() {
        return profileList;
    }

    public void AddNewProfile(Profile profile) {
        profileList.add(profile);
        currentId++;
    }

}
