package ru.urfu.profile;

import junit.framework.TestCase;
import org.junit.Assert;

public class ProfileCreatorTest extends TestCase {
    public void testProfileCreator() throws Exception {
        var profiles = new ProfileData();
        var profile0 = new Profile(0, profiles);
        var profile1 = new Profile(1, profiles);
        profiles.addProfile(profile0);
        profiles.addProfile(profile1);
        var pc = new ProfileCreator(profiles);
        Assert.assertThrows(Exception.class, () -> pc.CreateProfile(0));
        var profile2 = pc.CreateProfile(20);
        Assert.assertTrue(profiles.getProfileList().contains(profile2));
        Assert.assertTrue(profiles.getProfileList().contains(profile1));
        Assert.assertTrue(profiles.getProfileList().contains(profile0));
    }

}