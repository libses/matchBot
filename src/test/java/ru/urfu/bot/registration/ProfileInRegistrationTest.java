package ru.urfu.bot.registration;

import org.junit.Assert;
import org.junit.Test;
import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileData;

public class ProfileInRegistrationTest {
    public ProfileInRegistrationTest() {

    }

    /**
     * Проверяет, что профиль не меняется при начале регистрации и находится в начальном положении регистрации
     * @throws Exception бросает если id уже есть
     */
    @Test
    public void profileStaysTheSameAndFieldsWorkCorrectlyTest() throws Exception {
        Profile profile = new Profile(0, new ProfileData());
        ProfileInRegistration prIR = new ProfileInRegistration(profile);
        Assert.assertEquals(prIR.getProfile(), profile);
        Assert.assertFalse(prIR.isRegistrationCompleted());
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Имя");
    }

    /**
     * Проверяет, что при регистрации прогресс регистрации увеличивается пропорционально
     * @throws Exception бросает если id уже есть
     */
    @Test
    public void increaseProgressTest() throws Exception{
        Profile profile = new Profile(0, new ProfileData());
        ProfileInRegistration prIR = new ProfileInRegistration(profile);
        prIR.updateProgress();
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Пол");
        prIR.updateProgress();
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Возраст");
        prIR.updateProgress();
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Город");
        prIR.updateProgress();
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Фото");
        Assert.assertFalse(prIR.isRegistrationCompleted());
        prIR.updateProgress();
        Assert.assertTrue(prIR.isRegistrationCompleted());
    }

}