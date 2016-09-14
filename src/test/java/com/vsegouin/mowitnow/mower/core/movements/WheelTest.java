package com.vsegouin.mowitnow.mower.core.movements;

import com.vsegouin.mowitnow.mower.enums.WheelState;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class WheelTest {
    @Test
    public void testGoForward() throws Exception {
        Wheel wheel = new Wheel();
        assertEquals(wheel.getDistance(), 0);
        wheel.goForward();
        assertEquals(checkResult(wheel), true);
    }

    @Test
    public void testGoBackward() throws Exception {
        Wheel wheel = new Wheel();
        assertEquals(wheel.getDistance(), 0);
        wheel.goBackward();
        assertEquals(checkResult(wheel), true);
    }

    @Test
    private boolean checkResult(Wheel wheel) {
        assertEquals(wheel.getDistance(), 1);
        assertEquals(wheel.getState(), WheelState.STILL);
        return true;
    }
}