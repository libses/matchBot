package ru.urfu.profile;

import java.util.Scanner;

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

}
