package ru.urfu.bot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.urfu.profile.Profile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Селектор (выбиратель) профилей. Позволяет выбрать следующий профиль из хранилища. Реализован только вариант с рандомом
 */
public class ProfileSelector {
    private int counter;
    private final double oneMeter = (1d / 40075000d) * 360d;

    private final Profile owner;
    private final ArrayList<Profile> viewed = new ArrayList<>();

    public Profile getCurrent() {
        return current;
    }

    private Profile current;

    private ProfileData ProfileData;

    /**
     * Метод выбирает и возвращает следующую обертку над профилем
     *
     * @return обертка над профилем
     */
    public ProfileWrapper getNextProfileWrapper() {
        clearViewedIfAtTheEndOfCollection();

        if (owner.getLocation() != null) {
            ProfileWrapper profile = handleLocationCase(10);
            if (profile != null) return profile;
        }

        ProfileWrapper p = handleDefaultCase();
        if (p != null) return p;

        Profile emptyProfile = getEmptyProfile();
        return wrapProfile(extractProfileToCurrentAndView(emptyProfile), "");
    }

    /**
     * Метод для получения пустого профиля
     * @return пустой профиль
     */
    private Profile getEmptyProfile() {
        var emptyProfile = new Profile(-1);
        emptyProfile.setName("");
        emptyProfile.setCity("");
        emptyProfile.setUserName("");
        return emptyProfile;
    }

    /**
     * Очищает коллекцию просмотренных и обнуляет счетчик просмотренных
     */
    private void clearViewedIfAtTheEndOfCollection() {
        if (counter >= ProfileData.Count - 1) {
            viewed.clear();
            counter = 0;
        }
    }

    /**
     * Обрабатывает обычную ситуацию выбора анкет по порядку
     * @return пустую обертку над профилем
     */
    private ProfileWrapper handleDefaultCase() {
        Collection<Profile> profileCollection = ProfileData.getProfileList();
        for (Profile p :
                profileCollection) {
            if (!viewed.contains(p) && !p.equals(owner)) {
                return wrapProfile(extractProfileToCurrentAndView(p), "");
            }
        }
        return null;
    }


    /**
     * Обрабатывает ситуацию, когда у пользователя указана локация
     * @param radius радиус поиска в километрах
     * @return возвращает один из профилей в радиусе
     */
    private ProfileWrapper handleLocationCase(int radius) {
        var gettedList = ProfileData.getLocationData().getProfilesIn(owner.getLocation(), radius);
        for (Profile profile : gettedList) {
            if (!viewed.contains(profile) && !profile.equals(owner)) {
                double oneKiloMeter = oneMeter * 1000;
                var distance = owner.getLocation().FindDistanceTo(profile.getLocation()) / oneKiloMeter;
                return wrapProfile(extractProfileToCurrentAndView(profile), "Менее чем в " + ((int)distance + 1) + " км от тебя!\n");
            }
        }
        return null;
    }

    /**
     * Оборачивает профиль с подписью
     * @param p профиль для обертки
     * @param info информация о профиле
     * @return возвращает обернутый профиль
     */
    private ProfileWrapper wrapProfile(Profile p, String info) {
        return new ProfileWrapper(p, info);
    }

    /**
     * Прибавляет счётчик просмотренных, добавляет профиль в просмотренное и ставит current равным профилю p
     * @param p профиль для экстракции
     * @return возвращает сам профиль p без изменений
     */
    private Profile extractProfileToCurrentAndView(Profile p) {
        counter++;
        viewed.add(p);
        current = p;
        return p;
    }

    public ProfileSelector(Profile owner, ru.urfu.bot.ProfileData profileData) {
        this.owner = owner;
        ProfileData = profileData;
    }
}
