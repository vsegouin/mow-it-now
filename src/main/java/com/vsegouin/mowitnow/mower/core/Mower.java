package com.vsegouin.mowitnow.mower.core;

import com.vsegouin.mowitnow.mower.core.detection.Detector;
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

/**
 * Created by v.segouin on 07/09/2016.
 * This class is the main controller of the mower behaviour. It is also the main entry point of the whole program.
 * It control the wheels, servomotor and the detector and keep it consistent
 */
public class Mower {
    private static final Logger LOGGER = Logger.getLogger(Mower.class);

    private Detector detector;
    private Wheel wheel;
    private ServoMotor servoMotor;
    private LanguageManagerInterface languageManager;

    /**
     * Basic constructor, will place the mower at 0,0,N.
     *
     * @param map the map where to place the mower
     */
    public Mower(final MapArea map) {
        this(0, 0, Direction.NORTH, map);
    }

    /**
     * Standard constructor, will the mower at the given coordinates.
     *
     * @param initialAbscissa  the abscissa where to place the mower
     * @param initialOrdinate  the ordinate where to place the mower
     * @param initialDirection the direction where the mower have to face at start
     * @param map              the map where to place the mower
     */
    public Mower(final int initialAbscissa, final int initialOrdinate, final Direction initialDirection, final MapArea map) {
        wheel = new Wheel();
        servoMotor = new ServoMotor(initialDirection);
        detector = new Detector(initialAbscissa, initialOrdinate, initialDirection, map);
        wheel.addObserver(detector);
        servoMotor.addObserver(detector);
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
    }

    /**
     * Ask the servomotor to turn left.
     */
    public void turnLeft() {
        servoMotor.turnLeft();
    }

    /**
     * Ask the servomotor to turn right.
     */
    public void turnRight() {
        servoMotor.turnRight();
    }

    /**
     * Will perform a check on the asked action and will move forward if there is no obstacle.
     *
     * @throws UnsafeMoveException if an obstacle is detected
     */
    public void goForward() throws UnsafeMoveException {
        if (detector.isAskedPositionSafe(WheelState.FORWARD)) {
            wheel.goForward();
        } else {
            LOGGER.error(languageManager.getString("asked-move-not-safe"));
            throw new UnsafeMoveException(languageManager.getString("move-cannot-be-done"), detector.getNextPositionState(WheelState.FORWARD));
        }
    }

    /**
     * Will perform a check on the asked action and will move backward if there is no obstacle.
     *
     * @throws UnsafeMoveException if an obstacle is detected
     */
    public void goBackward() throws UnsafeMoveException {
        PositionState state = detector.getNextPositionState(WheelState.BACKWARD);
        if (state == PositionState.MOWER || state == PositionState.OUTBOUND) {
            LOGGER.error(languageManager.getString("asked-move-not-safe"));
            throw new UnsafeMoveException(languageManager.getString("move-cannot-be-done"), detector.getNextPositionState(WheelState.BACKWARD));
        } else {
            wheel.goBackward();
        }
    }

    /**
     * draw the current state of the mower and the map in a string, can be used on CLI.
     *
     * @return the string where the positions are drawed
     */
    public String drawPosition() {
        StringBuilder representation = new StringBuilder();
        representation.append(detector.getCurrentStateString()).append("\n\r");
        MapArea map = detector.getMap();
        int[] mowerState = detector.getCurrentState();
        int[][] matrix = map.getMatrix();
        representation.append("\n\r");
        for (int ordinate = matrix.length - 1; ordinate >= 0; ordinate--) {
            representation.append("|");
            for (int abscissa = 0; abscissa <= matrix[ordinate].length - 1; abscissa++) {
                if (abscissa == mowerState[0] && ordinate == mowerState[1]) {
                    representation.append(Direction.fetchDirection(mowerState[2]).getAbbreviation()).append(",");
                } else {
                    representation.append(matrix[abscissa][ordinate]).append(",");
                }
            }
            representation.deleteCharAt(representation.length() - 1);
            representation.append("|\n\r");
        }
        representation.append(getStatistics());
        return representation.toString();
    }

    /**
     * Give the globals statistics of the mower.
     *
     * @return a string containing the global statistics of the mower.
     */
    public String getStatistics() {
        String statistics = languageManager.getString("distance-done", Integer.toString(wheel.getDistance())) + "\n\r";
        statistics += languageManager.getString("times-turned", Integer.toString(servoMotor.getNumberOfMovement()));
        return statistics;
    }


    public Detector getDetector() {
        return detector;
    }


}
