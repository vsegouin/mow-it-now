package com.vsegouin.mowitnow.mower.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.segouin on 07/09/2016.
 * This enum contains the different direction a mower can use and the angle they represents
 */
public enum Direction {
    NORTH(0, "North", "N"),
    EAST(90, "East", "E"),
    SOUTH(180, "South", "S"),
    WEST(270, "West", "W");

    private static Map<Integer, Direction> mapAngle = new HashMap<>();
    private static Map<String, Direction> mapAbbreviation = new HashMap<>();

    static {
        for (Direction direction : Direction.values()) {
            mapAngle.put(direction.angle, direction);
            mapAbbreviation.put(direction.abbreviation, direction);
        }
    }

    private String readableName;
    private int angle;
    private String abbreviation;

    /**
     * A basic constructor of the enum.
     *
     * @param angle        the angle (in degrees) the direction is represented by, assuming the north it 0, the east at 90
     * @param readableName a human readable name of the direction.
     * @param abbreviation the abbreviation of the direction (ie : N for north, E for east)
     */
    Direction(final int angle, final String readableName, final String abbreviation) {
        this.angle = angle;
        this.readableName = readableName;
        this.abbreviation = abbreviation;
    }

    /**
     * Find a direction thanks to the angle.
     *
     * @param angle the angle to find
     * @return the direction represented by the angle
     */
    public static Direction fetchDirection(final int angle) {
        return mapAngle.get(angle);
    }

    /**
     * Find a direction thanks to the abbreviation.
     *
     * @param abbreviation the abbreviation of the direction
     * @return the direction which correspond to the abbreviation.
     */
    public static Direction fetchDirection(final String abbreviation) {
        return mapAbbreviation.get(abbreviation);
    }


    public int getAngle() {
        return this.angle;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getReadableName() {
        return readableName;
    }

}
