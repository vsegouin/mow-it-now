package com.vsegouin.mowitnow.mower.exceptions;

/**
 * Created by segouin.v on 13/09/2016.
 */
public class UnknownMowerCommandException extends ImpossibleMowerCommandException {
    private final String message;

    /**
     * The basic constructor which will set the exception's message.
     *
     * @param message the exception's message
     */
    public UnknownMowerCommandException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
