package ru.urfu.profile;

import junit.framework.TestCase;
import org.junit.Assert;

public class ProfileSelectorTest extends TestCase {

    public void testGetNextProfile() throws Exception {
        var profiles = new ProfileData();
        var profile0 = new Profile(0);
        var profile1 = new Profile(1);
        profiles.addProfile(profile0);
        profiles.addProfile(profile1);
        var ps = new ProfileSelector(profiles);
        var next = ps.getNextProfile();
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(next.equals(profile0) || next.equals(profile1));
            next = ps.getNextProfile();
        }
    }

    public void testGetRandomProfile() throws Exception {
        var profiles = new ProfileData();
        var profile0 = new Profile(0);
        var profile1 = new Profile(1);
        profiles.addProfile(profile0);
        profiles.addProfile(profile1);
        var ps = new ProfileSelector(profiles);
        var next = ps.getRandomProfile();
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(next.equals(profile0) || next.equals(profile1));
            next = ps.getRandomProfile();
        }
    }
}