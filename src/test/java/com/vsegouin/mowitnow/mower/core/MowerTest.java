package com.vsegouin.mowitnow.mower.core;

import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.exceptions.UnsafeMoveException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class MowerTest {

    @Test
    public void testGoForward() throws UnsafeMoveException {
        Mower mower = new Mower(0, 0, Direction.NORTH, new MapArea(5, 5));
        mower.goForward();
        int[] state = mower.getDetector().getCurrentState();
        Assert.assertEquals(state[0], 0);
        Assert.assertEquals(state[1], 1);
        Assert.assertEquals(state[2], Direction.NORTH.getAngle());
    }

    @Test
    public void testGoBackward() throws UnsafeMoveException {
        Mower mower = new Mower(5, 5, Direction.NORTH, new MapArea(5, 5));
        mower.goBackward();
        int[] state = mower.getDetector().getCurrentState();
        Assert.assertEquals(state[0], 5);
        Assert.assertEquals(state[1], 4);
        Assert.assertEquals(state[2], Direction.NORTH.getAngle());
    }

    @Test(expectedExceptions = UnsafeMoveException.class)
    public void testGoBackward_Error() throws UnsafeMoveException {
        Mower mower = new Mower(0, 0, Direction.NORTH, new MapArea(5, 5));
        mower.goBackward();
    }

    @Test(expectedExceptions = UnsafeMoveException.class)
    public void testGoForward_Error() throws UnsafeMoveException {
        Mower mower = new Mower(0, 0, Direction.NORTH, new MapArea(5, 5));
        mower.goBackward();
    }

}