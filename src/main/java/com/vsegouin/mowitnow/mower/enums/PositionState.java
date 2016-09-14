package com.vsegouin.mowitnow.mower.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.segouin on 07/09/2016.
 * This enum contains the different state of a map area, used by the detector to help the mower to move safely in space
 * GRASS : A fresh piece of grass not mowed
 * MOWED_GRASS : A piece of grass where a mower is already passed
 * MOWER : A mower is already presents on this area
 * OUTBOUND : The area is outside the authorized workspace
 */
public enum PositionState {
    GRASS(0),
    MOWED_GRASS(1),
    MOWER(2),
    OUTBOUND(3);

    private static Map<Integer, PositionState> map = new HashMap<>();

    static {
        for (PositionState state : PositionState.values()) {
            map.put(state.value, state);
        }
    }

    private final int value;

    /**
     * The basic constructor, the 'value' represents the value of a particular state in the map's matrix.
     *
     * @param value the map's matrix representation of the state.
     */
    PositionState(final int value) {
        this.value = value;
    }

    /**
     * find a state thanks to the state number.
     *
     * @param stateNumber the state number to find
     * @return state represented by the stateNumber
     */
    public static PositionState valueOf(final int stateNumber) {
        return map.get(stateNumber);
    }


    public int getStateNumber() {
        return value;
    }
}
