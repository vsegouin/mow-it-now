package com.vsegouin.mowitnow.mower.core.detection;

import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.core.movements.ServoMotor;
import com.vsegouin.mowitnow.mower.core.movements.Wheel;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.enums.PositionState;
import com.vsegouin.mowitnow.mower.enums.WheelState;
import com.vsegouin.mowitnow.mower.exceptions.UnsafeMoveException;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by v.segouin on 07/09/2016.
 * This class reads the map which represents the area to mow, and updates the map according to the movements of the mower
 * it can also check if a movement is safe before starting any mowing, if the mower's state is set on SAFE
 */
public class Detector implements Observer {
    private static final Logger LOGGER = Logger.getLogger(Detector.class);
    private static EnumMap<Direction, EnumMap<WheelState, Integer>> movementsOperation;

    /**
     * This static bloc initialize the possible matrix move depending on the direction and the wheel state.
     * This can seems bloated, but it reduce drastically the cyclomatic complexity and increase performances afterward.
     */
    static {
        movementsOperation = new EnumMap<>(Direction.class);
        EnumMap<WheelState, Integer> moveNorth = new EnumMap<>(WheelState.class);
        EnumMap<WheelState, Integer> moveSouth = new EnumMap<>(WheelState.class);
        EnumMap<WheelState, Integer> moveWest = new EnumMap<>(WheelState.class);
        EnumMap<WheelState, Integer> moveEast = new EnumMap<>(WheelState.class);
        moveNorth.put(WheelState.FORWARD, 1);
        moveNorth.put(WheelState.BACKWARD, -1);
        moveSouth.put(WheelState.FORWARD, -1);
        moveSouth.put(WheelState.BACKWARD, 1);
        moveWest.put(WheelState.BACKWARD, 1);
        moveWest.put(WheelState.FORWARD, -1);
        moveEast.put(WheelState.BACKWARD, -1);
        moveEast.put(WheelState.FORWARD, 1);
        movementsOperation.put(Direction.EAST, moveEast);
        movementsOperation.put(Direction.NORTH, moveNorth);
        movementsOperation.put(Direction.SOUTH, moveSouth);
        movementsOperation.put(Direction.WEST, moveWest);
    }

    private LanguageManagerInterface languageManager;
    private MapArea map;
    private Direction currentDirection;
    private int currentAbscissa;
    private int currentOrdinate;

    /**
     * Initializes the detector with the current state of the mower.
     *
     * @param abscissa         initial abscissa of the mower on the area to mow
     * @param ordinate         initial ordinate of the mower on the area to mow
     * @param initialDirection initial direction of the mower
     * @param map              the area to mow
     */
    public Detector(final int abscissa, final int ordinate, final Direction initialDirection, final MapArea map) {
        this.currentAbscissa = abscissa;
        this.currentOrdinate = ordinate;
        this.currentDirection = initialDirection;
        this.map = map;
        this.map.setCoordinate(abscissa, ordinate, PositionState.MOWER.getStateNumber());
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
    }

    /**
     * Intercepts signal from wheels and servomotor and updates the class depending on the action
     * Servomotor : Updates the current direction
     * Wheel : update the map depending on the movement.
     *
     * @param observable a Servomotor or a wheel
     * @param o          same as observable
     */
    @Override
    public void update(final Observable observable, final Object o) {
        if (o instanceof ServoMotor) {
            ServoMotor r = (ServoMotor) o;
            currentDirection = r.getCurrentDirection();
            LOGGER.debug("current position : " + currentDirection.getReadableName());
        } else if (o instanceof Wheel) {
            Wheel wheel = (Wheel) o;
            this.updateCurrentPosition(wheel.getState());
        }
    }

    /**
     * Updates the map by changing the area where the mower was by the MOWED_GRASS state
     * and the next area with the MOWER state.
     *
     * @param state the state of the wheel (backward, forward, or still)
     */
    private void updateCurrentPosition(final WheelState state) {
        this.map.setCoordinate(currentAbscissa, currentOrdinate, PositionState.MOWED_GRASS.getStateNumber());
        computeNextStep(state);
        this.map.setCoordinate(currentAbscissa, currentOrdinate, PositionState.MOWER.getStateNumber());
    }

    /**
     * Computes the next step depending on the mower's direction and updates the detector's abscissa and ordinate.
     *
     * @param state the state of the wheel (backward, forward, or still)
     */
    private void computeNextStep(final WheelState state) {
        this.computeNextStep(state, false);
    }

    /**
     * Computes the next step depending on the mower's direction and updates the detector's abscissa and ordinate.
     *
     * @param state  the state of the wheel (backward, forward, or still)
     * @param dryRun if false the real position of the detector will not be updated, used to simulate a move
     * @return the new coordinates of the mower
     */
    private int[] computeNextStep(final WheelState state, final boolean dryRun) {
        int[] bufferCoordinate = new int[2];
        bufferCoordinate[0] = currentAbscissa;
        bufferCoordinate[1] = currentOrdinate;
        if (currentDirection == Direction.SOUTH || currentDirection == Direction.NORTH) {
            bufferCoordinate[1] += movementsOperation.get(currentDirection).get(state);
        } else if (currentDirection == Direction.EAST || currentDirection == Direction.WEST) {
            bufferCoordinate[0] += movementsOperation.get(currentDirection).get(state);
        }

        if (!dryRun) {
            currentAbscissa = bufferCoordinate[0];
            currentOrdinate = bufferCoordinate[1];
        }
        return bufferCoordinate;
    }

    /**
     * Check if the next move done by the mower is safe (ie : the asked position isn't outbound and there is no mower already on it).
     *
     * @param wheelState the state of the wheel (backward, forward or still)
     * @return check if there is a mower on the path or if the path is outbound
     * @throws UnsafeMoveException if the asked box contains something different than grass.
     */
    public boolean isAskedPositionSafe(final WheelState wheelState) throws UnsafeMoveException {
        PositionState positionState = getNextPositionState(wheelState);
        return positionState == PositionState.MOWED_GRASS || positionState == PositionState.GRASS;
    }

    /**
     * Simulate the mower move and get the next.
     *
     * @param wheelState the movement to simulate
     * @return the position state of the next step
     * @throws UnsafeMoveException if the asked move make the mower going outside of the area.
     */
    public PositionState getNextPositionState(final WheelState wheelState) throws UnsafeMoveException {
        int[] nextCoordinate = computeNextStep(wheelState, true);
        PositionState state = PositionState.OUTBOUND;
        try {
            state = PositionState.valueOf(map.getCoordinate(nextCoordinate[0], nextCoordinate[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error(e);
        }
        return state;
    }

    /**
     * Get the current abscissa, ordinate and direction of the detector.
     *
     * @return an array of int which represents the state of the detector [0] : abscissa [1] : ordinate [2] : direction angle
     */
    public int[] getCurrentState() {
        return new int[]{currentAbscissa, currentOrdinate, currentDirection.getAngle()};
    }

    /**
     * Get the current abscissa, ordinate and direction of the detector.
     *
     * @return a human readable string of the current mower's state
     */
    public String getCurrentStateString() {
        return "X: " + currentAbscissa + " Y: " + currentOrdinate + " D: " + languageManager.getString(currentDirection.getReadableName().toLowerCase());
    }

    public MapArea getMap() {
        return map;
    }
}
