package com.vsegouin.mowitnow.mower.exceptions;

import com.vsegouin.mowitnow.mower.enums.PositionState;

/**
 * Created by v.segouin on 08/09/2016.
 */
public class UnsafeMoveException extends ImpossibleMowerCommandException {
    private final String message;
    private final PositionState askedPositionState;

    /**
     * A constructor, which will set the message into the global variable 'message', and the asked state which caused
     * the error.
     *
     * @param message           the error message to show.
     * @param nextPositionState the state which caused the error
     */
    public UnsafeMoveException(final String message, final PositionState nextPositionState) {
        this.message = message;
        this.askedPositionState = nextPositionState;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public PositionState getAskedPositionState() {
        return askedPositionState;
    }

}
