package ru.urfu.profile;

import org.telegram.telegrambots.meta.api.objects.Location;
import ru.urfu.matching.Matcher;

import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

/**
 * Класс, позволяющий создавать профиль.
 */

public class ProfileCreator {
    private final ProfileData profiles;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Создаёт профиль по id
     * @param id id человека
     * @return возвращает профиль
     * @throws Exception эксепшн если id уже есть
     */
    public Profile CreateProfile(long id) throws Exception {
        if (ProfileData.containsId(id))
            throw new Exception(String.format("Пользователь с таким id уже существует (%s)",id));

        Profile profile = new Profile(id);
        profiles.addProfile(profile);
        return profile;
    }

    public ProfileCreator(ProfileData profiles) {
        this.profiles = profiles;
    }

    /**
     * Спрашиваем всё о человеке и записываем в соответсвующие свойства
     *
     * @return возвращает готовый профиль
     * @throws Exception кидает ошибку
     */
    public Profile createFullProfile() throws Exception {
        Profile profile = CreateProfile(0);
        profile.setGender(askForGender());
        profile.setName(askForName());
        profile.setSelector(new ProfileSelector(profiles));
        profile.setMatcher(new Matcher(profile, profile.getSelector()));
        profile.setPhotoLink(askForPhoto());
        profile.setLocation(askForLocation());
        profile.setDescription(askForDescription());
        throw new Exception("not implemented");
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

    public Gender askForGender() throws Exception {
        throw new Exception("not implemented");
    }

    public Boolean askYesOrNo(){
        var input = scanner.nextLine();
        return  ("y".equals(input.toLowerCase(Locale.ROOT)) || "yes".equals(input.toLowerCase(Locale.ROOT)));
    }

}
