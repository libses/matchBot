package ru.urfu.bot;

import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import ru.urfu.bot.locations.Location;
import ru.urfu.profile.Profile;

public class ProfileSelectorTest extends TestCase {

    public void testGetNextProfileWrapper() {
        var owner = new Profile(0);
        owner.setLocation(new Location(0,0));
        ProfileData.getLocationData().addProfile(owner);
        var selector = new ProfileSelector(owner);
        var p2 = new Profile(2);
        ProfileData.addProfile(p2);
        var p1 = new Profile(10);
        p1.setLocation(new Location(0,0));
        ProfileData.addProfile(p1);
        ProfileData.getLocationData().addProfile(p1);
        Assert.assertNull(selector.getCurrent());
        var next = selector.getNextProfileWrapper();
        Assert.assertSame(next.getProfile(), p1);
        Assert.assertSame(selector.getCurrent(), p1);
        next = selector.getNextProfileWrapper();
        Assert.assertSame(next.getProfile(), p2);
    }
}