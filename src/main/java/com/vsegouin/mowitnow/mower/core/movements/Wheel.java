package com.vsegouin.mowitnow.mower.core.movements;

import com.vsegouin.mowitnow.mower.enums.WheelState;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import java.util.Observable;

/**
 * Created by v.segouin on 07/09/2016.
 * This class represents the wheels of a mower and permits it to go forward or backward
 */
public class Wheel extends Observable {
    private static final Logger LOGGER = Logger.getLogger(Wheel.class);
    private int distance;
    private WheelState state;
    private LanguageManagerInterface languageManager;

    /**
     * Basic constructor, initiate the total distance at 0.
     */
    public Wheel() {
        distance = 0;
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
    }

    /**
     * Move the mower one step forward.
     *
     * @return the total distance traveled since the start of the mower
     */
    public int goForward() {
        LOGGER.debug(languageManager.getString("moving-forward"));
        state = WheelState.FORWARD;
        setChanged();
        notifyObservers(this);
        state = WheelState.STILL;
        return ++distance;
    }

    /**
     * Move the mower one step backward.
     *
     * @return the total distance traveled since the start of the mower
     */
    public int goBackward() {
        LOGGER.debug(languageManager.getString("moving-backward"));
        state = WheelState.BACKWARD;
        setChanged();
        notifyObservers(this);
        state = WheelState.STILL;
        return ++distance;
    }

    public int getDistance() {
        return distance;
    }

    public WheelState getState() {
        return state;
    }
}
