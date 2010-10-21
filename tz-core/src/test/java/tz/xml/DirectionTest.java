package tz.xml;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Dmitry Shyshkin
 */
public class DirectionTest {
    @Test
    public void testEast() {
        Assert.assertEquals(Direction.EAST.moveX(0, 0), 1);
        Assert.assertEquals(Direction.EAST.moveY(0, 0), 0);
        Assert.assertEquals(Direction.EAST.moveX(0, 1), 1);
        Assert.assertEquals(Direction.EAST.moveY(0, 1), 1);
    }

    @Test
    public void testWest() {
        Assert.assertEquals(Direction.WEST.moveX(1, 0), 0);
        Assert.assertEquals(Direction.WEST.moveY(1, 0), 0);
        Assert.assertEquals(Direction.WEST.moveX(1, 1), 0);
        Assert.assertEquals(Direction.WEST.moveY(1, 1), 1);
    }

    @Test
    public void testSouthEast() {
        Assert.assertEquals(Direction.SOUTH_EAST.moveX(0, 0), 0);
        Assert.assertEquals(Direction.SOUTH_EAST.moveY(0, 0), 1);
        Assert.assertEquals(Direction.SOUTH_EAST.moveX(0, 1), 1);
        Assert.assertEquals(Direction.SOUTH_EAST.moveY(0, 1), 2);
    }

}
