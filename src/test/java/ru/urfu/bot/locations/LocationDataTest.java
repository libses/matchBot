package ru.urfu.bot.locations;

import junit.framework.TestCase;
import org.junit.Assert;
import ru.urfu.profile.Profile;

public class LocationDataTest extends TestCase {

    /**
     * Проверяем корректность работы добавления локации и получения локации из структуры с определенным радиусом
     * Проверяем правильность округления
     */
    public void testGetProfilesInRadius() {
        var p1 = new Profile(0);
        p1.setLocation(new Location(10, 20));
        var locData = new LocationData();
        locData.addProfile(p1);
        var list = locData.getProfilesIn(new Location(10.05, 20.05), 20);
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(list.contains(p1));
        var p2 = new Profile(1);
        p2.setLocation(new Location(0, 0));
        locData.addProfile(p2);
        var list2 = locData.getProfilesIn(new Location(10.05, 20.05), 20);
        Assert.assertEquals(list2.size(), 1);
        Assert.assertTrue(list2.contains(p1));
        var p3 = new Profile(2);
        p3.setLocation(new Location(10.01, 20.01));
        locData.addProfile(p3);
        var list3 = locData.getProfilesIn(new Location(10.05, 20.05), 20);
        Assert.assertTrue(list3.contains(p1));
        Assert.assertTrue(list3.contains(p3));
        Assert.assertEquals(list3.size(), 2);

    }

    /**
     * Проверяем корректность работы добавления локации и получения локации из структуры
     * Проверяем правильность округления
     */
    public void testTestGetProfilesIn() {
        var p1 = new Profile(0);
        p1.setLocation(new Location(10, 20));
        var locData = new LocationData();
        locData.addProfile(p1);
        var list = locData.getProfilesIn(new Location(10, 20));
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(list.contains(p1));
    }
}