package com.vsegouin.mowitnow.mower.core.movements;

import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import java.util.Observable;

/**
 * Created by v.segouin on 07/09/2016.
 * The servomotor is used to set the direction of the mower.
 */
public class ServoMotor extends Observable {
    private static final Logger LOGGER = Logger.getLogger(ServoMotor.class);
    private static final int ANGLE_STEP_VALUE = 90;
    private static final int COMPLETE_TURN_ANGLE = 360;
    private Direction direction;
    private int numberOfMovement;
    private LanguageManagerInterface languageManager;

    /**
     * @param direction the initial direction of the servomotor.
     */
    public ServoMotor(final Direction direction) {
        this.direction = direction;
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
    }

    /**
     * Turn the mower on the left and update the detectors.
     */
    public void turnLeft() {
        LOGGER.debug(languageManager.getString("turning-left"));
        int angle = direction.getAngle() - ANGLE_STEP_VALUE;
        if (angle < 0) {
            angle = Direction.WEST.getAngle();
        }
        direction = Direction.fetchDirection(angle);
        numberOfMovement++;
        setChanged();
        notifyObservers(this);
    }

    /**
     * Turn the mower on the right and update the detectors.
     */
    public void turnRight() {
        LOGGER.debug(languageManager.getString("turning-right"));
        int angle = direction.getAngle() + ANGLE_STEP_VALUE;
        if (angle >= COMPLETE_TURN_ANGLE) {
            angle = Direction.NORTH.getAngle();
        }
        direction = Direction.fetchDirection(angle);
        numberOfMovement++;
        setChanged();
        notifyObservers(this);
    }

    public Direction getCurrentDirection() {
        return direction;
    }

    public int getNumberOfMovement() {
        return numberOfMovement;
    }
}
