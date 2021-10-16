package ru.urfu;

public class ProfileCreator {
    private ProfileStorageInterface profiles;

    public Profile CreateProfile(){
        int id = profiles.getCurrentId() + 1;
        return new Profile(id);
    }
    public ProfileCreator(ProfileStorageInterface profiles){
        this.profiles = profiles;
    }
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
