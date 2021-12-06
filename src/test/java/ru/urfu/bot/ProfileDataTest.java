package ru.urfu.bot;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.urfu.profile.Profile;

public class ProfileDataTest extends TestCase {

    public void testAddProfile() {
        var p = new Profile(0);
        ProfileData.addProfile(p);
        Assert.assertTrue(ProfileData.containsId(0));
    }

}