package ru.urfu.profile;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.urfu.bot.ProfileData;
import ru.urfu.bot.ProfileSelector;

public class ProfileSelectorTest extends TestCase {

    /**
     * Тест, проверяющий корректность работы выбора следующего профиля
     */
    public void testGetNextProfile() {
        var profile0 = new Profile(2);
        var profile1 = new Profile(3);
        ProfileData.addProfile(profile0);
        ProfileData.addProfile(profile1);
        var ps = new ProfileSelector(profile0);
        var next = ps.getNextProfileWrapper();
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(next.equals(profile0) || next.equals(profile1));
            next = ps.getNextProfileWrapper();
        }
    }
}