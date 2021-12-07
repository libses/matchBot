package ru.urfu.bot.locations;

import junit.framework.TestCase;
import org.junit.Assert;

public class LocationTest extends TestCase {

    /**
     * Проверяем, что локация создаётся корректно и корректно округляется
     */
    public void testCreation(){
        var location = new Location(12.3456, 78.111111);
        var x = location.getLongitude();
        var y = location.getLatitude();
        Assert.assertTrue(doublesAlmostEqual(x, 12.35));
        Assert.assertTrue(doublesAlmostEqual(y, 78.11));
    }

    /**
     * Проверяем метод equals на корректность работы
     */
    public void testTestEquals() {
        var location1 = new Location(12.3456, 78.111111);
        var location2 = new Location(12.3457, 78.111112);
        Assert.assertEquals(location1, location2);
    }

    /**
     * Проверяем метод FindDistanceTo на корректность работы
     */
    public void testFindDistanceTo() {
        var location1 = new Location(12.3451, 78.11999);
        var location2 = new Location(12.3499, 78.11500);
        Assert.assertTrue(doublesAlmostEqual(location1.FindDistanceTo(location2), 0));
    }

    /**
     * Проверяем корректность метода округления
     */
    public void testRound() {
        var result1 = Location.round(10.109100, 1);
        Assert.assertTrue(doublesAlmostEqual(result1, 10.1));
        var result2 = Location.round(10.109100, 2);
        Assert.assertTrue(doublesAlmostEqual(result2, 10.11));
        var result3 = Location.round(10.109100, 3);
        Assert.assertTrue(doublesAlmostEqual(result3, 10.109));
    }

    /**
     * Метод для примерного сравнения с точностью 0.00001
     * @param v1  первый дабл
     * @param v2 второй дабл
     * @return булеан, равно или нет
     */
    private boolean doublesAlmostEqual(double v1, double v2) {
        return Math.abs(v1 - v2) < 0.00001;
    }
}