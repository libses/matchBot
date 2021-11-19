package ru.urfu.bot.registration;

import org.junit.Assert;
import org.junit.Test;
import ru.urfu.profile.Profile;

public class ProfileInRegistrationTest {
    public ProfileInRegistrationTest() {

    }

    /**
     * Проверяет, что профиль не меняется при начале регистрации и находится в начальном положении регистрации
     */
    @Test
    public void profileStaysTheSameAndFieldsWorkCorrectlyTest() {
        Profile profile = new Profile(0);
        ProfileInRegistration prIR = new ProfileInRegistration(profile);
        Assert.assertEquals(prIR.getProfile(), profile);
        Assert.assertFalse(prIR.isRegistrationCompleted());
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Имя");
    }

    /**
     * Проверяет, что при регистрации прогресс регистрации увеличивается пропорционально
     */
    @Test
    public void increaseProgressTest() {
        Profile profile = new Profile(0);
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