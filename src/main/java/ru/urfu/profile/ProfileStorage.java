package ru.urfu.profile;

import java.util.ArrayList;

/**
 * Класс хранилище профилей, реализованный в виде ArrayList для рандомной селекции.
 */

public class ProfileStorage implements ProfileStorageInterface {
    private int currentId = 0;

    public int getCurrentId() {
        return currentId;
    }

    private ArrayList<Profile> profileList = new ArrayList<>();

    public ArrayList<Profile> getProfileList() {
        return profileList;
    }

    public void addProfile(Profile profile) {
        profileList.add(profile);
        currentId++;
    }

}
