package com.vsegouin.mowitnow.mower.core.movements;

import com.vsegouin.mowitnow.mower.enums.Direction;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class ServoMotorTest {
    @Test
    public void testTurnRight() {
        ServoMotor servoMotor = new ServoMotor(Direction.NORTH);
        servoMotor.turnRight();
        assertEquals(servoMotor.getCurrentDirection(), Direction.EAST);
        servoMotor.turnRight();
        assertEquals(servoMotor.getCurrentDirection(), Direction.SOUTH);
        servoMotor.turnRight();
        assertEquals(servoMotor.getCurrentDirection(), Direction.WEST);
        servoMotor.turnRight();
        assertEquals(servoMotor.getCurrentDirection(), Direction.NORTH);
        assertEquals(servoMotor.getNumberOfMovement(), 4);
    }

    @Test
    public void testTurnLeft() {
        ServoMotor servoMotor = new ServoMotor(Direction.NORTH);
        servoMotor.turnLeft();
        assertEquals(servoMotor.getCurrentDirection(), Direction.WEST);
        servoMotor.turnLeft();
        assertEquals(servoMotor.getCurrentDirection(), Direction.SOUTH);
        servoMotor.turnLeft();
        assertEquals(servoMotor.getCurrentDirection(), Direction.EAST);
        servoMotor.turnLeft();
        assertEquals(servoMotor.getCurrentDirection(), Direction.NORTH);
        assertEquals(servoMotor.getNumberOfMovement(), 4);
    }


}