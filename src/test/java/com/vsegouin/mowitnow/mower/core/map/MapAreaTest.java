package com.vsegouin.mowitnow.mower.core.map;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class MapAreaTest {
    private static Logger LOGGER = Logger.getLogger(MapAreaTest.class);

    @Test
    public void testMapCreation() {
        MapArea map = new MapArea(5, 5);

        assertEquals(map.getMaxAbscissa(), 6);
        assertEquals(map.getMaxOrdinate(), 6);
        assertEquals(map.getMatrix().length, 6);
        assertEquals(map.getMatrix()[0].length, 6);
    }
}