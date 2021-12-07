package ru.urfu.bot;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.urfu.profile.Profile;

public class ProfileDataTest extends TestCase {

    /**
     * Проверка на то, что добавление профиля действительно его добавляет
     */
    public void testAddProfile() {
        var ProfileData = new ProfileData();
        var p = new Profile(0);
        ProfileData.addProfile(p);
        Assert.assertTrue(ProfileData.containsId(0));
    }

}