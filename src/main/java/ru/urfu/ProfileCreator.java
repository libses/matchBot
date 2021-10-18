package ru.urfu;

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
        profile.setCords(AskForCords());
        profile.setDescription(AskForDescription());
        profile.setName(AskForName());
        profile.setPhotoLink(AskForPhoto());
        return profile;
    }

    public String AskForPhoto() throws Exception{
        throw new Exception("not implemented");
    }

    public String AskForName() throws Exception {
        throw new Exception("not implemented");
    }

    public Coordinates AskForCords() throws Exception {
        throw new Exception("not implemented");
    }

    public String AskForDescription() throws Exception {
        throw new Exception("not implemented");
    }

}
