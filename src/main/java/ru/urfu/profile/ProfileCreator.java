package ru.urfu.profile;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.UUID;

/**
 * Класс, позволяющий создавать профиль.
 */

public class ProfileCreator {
    private final ProfileStorageInterface profiles;

    public Profile CreateProfile() throws Exception {
        var id = UUID.randomUUID();

        while (Profile.isUsedId(id))
            id = UUID.randomUUID();

        Profile profile = new Profile(id);
        profiles.addProfile(profile);
        return profile;
    }

    public ProfileCreator(ProfileStorageInterface profiles) {
        this.profiles = profiles;
    }

    /**
     * Спрашиваем всё о человеке и записываем в соответсвующие свойства
     *
     * @return возвращает готовый профиль
     * @throws Exception кидает ошибку
     */
    public Profile CreateFullProfile() throws Exception {
        Profile profile = CreateProfile();
        profile.setGender(askForGender());
        profile.setName(askForName());
        profile.setPhotoLink(askForPhoto());
        profile.setLocation(askForLocation());
        profile.setDescription(askForDescription());
        return profile;
    }

    public String askForPhoto() throws Exception {
        throw new Exception("not implemented");
    }

    public String askForName() throws Exception {
        throw new Exception("not implemented");
    }

    public Location askForLocation() throws Exception {
        throw new Exception("not implemented");
    }

    public String askForDescription() throws Exception {
        throw new Exception("not implemented");
    }

    public Gender askForGender() throws Exception {
        throw new Exception("not implemented");
    }

}
