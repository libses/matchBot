package ru.urfu.profile;

import org.telegram.telegrambots.meta.api.objects.Location;
import ru.urfu.matching.Matcher;

import java.util.Scanner;
import java.util.UUID;

/**
 * Класс, позволяющий создавать профиль.
 */

public class ProfileCreator {
    private final ProfileStorage profiles;

    private final Scanner scanner = new Scanner(System.in);

    public Profile CreateProfile() throws Exception {
        var id = UUID.randomUUID();

        while (Profile.isUsedId(id))
            id = UUID.randomUUID();

        Profile profile = new Profile(id);
        profiles.addProfile(profile);
        return profile;
    }

    public ProfileCreator(ProfileStorage profiles) {
        this.profiles = profiles;
    }

    /**
     * Спрашиваем всё о человеке и записываем в соответсвующие свойства
     *
     * @return возвращает готовый профиль
     * @throws Exception кидает ошибку
     */
    public Profile createFullProfile() throws Exception {
        Profile profile = CreateProfile();
        profile.setGender(askForGender());
        profile.setName(askForName());
        profile.setSelector(new ProfileSelector(profiles));
        profile.setMatcher(new Matcher(profile, profile.getSelector()));
        //profile.setPhotoLink(askForPhoto());
        //profile.setLocation(askForLocation());
        //profile.setDescription(askForDescription());
        return profile;
    }

    public String askForPhoto() throws Exception {
        throw new Exception("not implemented");
    }

    public String askForName() {
        System.out.println("ur name?");
        return scanner.nextLine();
    }

    public Location askForLocation() throws Exception {
        throw new Exception("not implemented");
    }

    public String askForDescription() throws Exception {
        throw new Exception("not implemented");
    }

    public Gender askForGender() {
        System.out.println("what is your gender? (f/m)");
        if ("f".equals(scanner.nextLine())){
            return Gender.female;
        }
        return Gender.male;
    }

    public Boolean askYesOrNo(){
        var input = scanner.nextLine();
        return  ("y".equals(input) || "yes".equals(input) || "Y".equals(input) || "Yes".equals(input));
    }

}
