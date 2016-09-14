package com.vsegouin.mowitnow.mower.core.detection;

import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.core.movements.ServoMotor;
import com.vsegouin.mowitnow.mower.core.movements.Wheel;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.enums.PositionState;
import com.vsegouin.mowitnow.mower.enums.WheelState;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by v.segouin on 11/09/2016.
 */
public class DetectorTest {


    private Detector detector;
    private Wheel wheel;
    private ServoMotor servoMotor;

    @BeforeMethod
    public void reset() {
        detector = new Detector(1, 1, Direction.NORTH, new MapArea(5, 5));
        wheel = new Wheel();
        servoMotor = new ServoMotor(Direction.NORTH);
        wheel.addObserver(detector);
        servoMotor.addObserver(detector);
    }

    @Test
    public void testForward() {
        wheel.goForward();
        int[] state = detector.getCurrentState();
        assertEquals(state[0], 1);
        assertEquals(state[1], 2);
        assertEquals(Direction.NORTH.getAngle(), state[2]);
    }

    @Test
    public void testBackward() {
        wheel.goBackward();
        int[] state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(0, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);
    }

    @Test
    public void testTurnRight() {
        int[] state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);

        servoMotor.turnRight();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.EAST.getAngle(), state[2]);

        servoMotor.turnRight();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.SOUTH.getAngle(), state[2]);

        servoMotor.turnRight();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.WEST.getAngle(), state[2]);

        servoMotor.turnRight();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);
    }

    @Test
    public void testTurnRightForward() {
        servoMotor.turnRight();
        wheel.goForward();
        int[] state = detector.getCurrentState();

        assertEquals(2, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.EAST.getAngle(), state[2]);
    }

    @Test
    public void testTurnRightBackward() {
        servoMotor.turnRight();
        wheel.goBackward();
        int[] state = detector.getCurrentState();

        assertEquals(0, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.EAST.getAngle(), state[2]);

    }

    @Test
    public void testTurnLeft() {
        int[] state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);

        servoMotor.turnLeft();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.WEST.getAngle(), state[2]);

        servoMotor.turnLeft();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.SOUTH.getAngle(), state[2]);

        servoMotor.turnLeft();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.EAST.getAngle(), state[2]);

        servoMotor.turnLeft();
        state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);
    }

    @Test
    public void testTurnLeftForward() {
        servoMotor.turnLeft();
        wheel.goForward();
        int[] state = detector.getCurrentState();

        assertEquals(state[0], 0);
        assertEquals(state[1], 1);
        assertEquals(Direction.WEST.getAngle(), state[2]);
    }

    @Test
    public void testTurnLeftBackward() {
        servoMotor.turnLeft();
        wheel.goBackward();
        int[] state = detector.getCurrentState();
        assertEquals(state[0], 2);
        assertEquals(state[1], 1);
        assertEquals(Direction.WEST.getAngle(), state[2]);
    }


    @Test
    public void testIsAskedPositionSafe() throws Exception {
        boolean isSafe = detector.isAskedPositionSafe(WheelState.FORWARD);
        assertEquals(isSafe, true);
    }

    @Test
    public void testGetNextPositionState() throws Exception {
        PositionState state = detector.getNextPositionState(WheelState.FORWARD);
        assertEquals(PositionState.GRASS, state);
    }

    @Test
    public void testGetNextPositionStateError() throws Exception {
        detector = new Detector(1, 1, Direction.NORTH, new MapArea(1, 1));
        wheel = new Wheel();
        servoMotor = new ServoMotor(Direction.NORTH);
        wheel.addObserver(detector);
        servoMotor.addObserver(detector);
        PositionState state = detector.getNextPositionState(WheelState.FORWARD);
        Assert.assertEquals(state, PositionState.OUTBOUND);
    }

    @Test
    public void testGetCurrentState() throws Exception {
        int[] state = detector.getCurrentState();
        assertEquals(1, state[0]);
        assertEquals(1, state[1]);
        assertEquals(Direction.NORTH.getAngle(), state[2]);
    }
}