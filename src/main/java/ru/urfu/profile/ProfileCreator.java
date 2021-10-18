package ru.urfu.profile;

import com.pengrad.telegrambot.model.Location;

/**
 * Класс, позволяющий создавать профиль.
 */

public class ProfileCreator {
    private final ProfileStorageInterface profiles;

    public Profile CreateProfile(){
        int id = profiles.getCurrentId() + 1;
        Profile profile = new Profile(id);
        profiles.addProfile(profile);
        return profile;
    }
    public ProfileCreator(ProfileStorageInterface profiles){
        this.profiles = profiles;
    }

    /**
     * Спрашиваем всё о человеке и записываем в соответсвующие свойства
     * @return возвращает готовый профиль
     * @throws Exception кидает ошибку
     */
    public Profile CreateFullProfile() throws Exception {
        Profile profile = CreateProfile();
        profile.setCords(askForCords());
        profile.setDescription(askForDescription());
        profile.setName(askForName());
        profile.setPhotoLink(askForPhoto());
        return profile;
    }

    public String askForPhoto() throws Exception{
        throw new Exception("not implemented");
    }

    public String askForName() throws Exception {
        throw new Exception("not implemented");
    }

    public Location askForCords() throws Exception {
        throw new Exception("not implemented");
    }

    public String askForDescription() throws Exception {
        throw new Exception("not implemented");
    }

}
