package ru.urfu.bot.registration;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import ru.urfu.profile.Profile;

public class ProfileInRegistrationTest {
    public ProfileInRegistrationTest() {

    }
    @Test
    public void profileStaysTheSameAndFieldsWorkCorrectlyTest() throws Exception {
        Profile profile = new Profile(0);
        ProfileInRegistration prIR = new ProfileInRegistration(profile);
        Assert.assertEquals(prIR.getProfile(), profile);
        Assert.assertFalse(prIR.isRegistrationCompleted());
        Assert.assertEquals(prIR.getCurrentRegistrationStep(), "Имя");
    }

    @Test
    public void increaseProgressTest() throws Exception{
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