package com.vsegouin.mowitnow.services.file.exceptions;

/**
 * This exception must be thrown if an error is found in the file containing the mowers commands
 * Created by v.segouin on 08/09/2016.
 */
public class MowerInstructionFileException extends Exception {
    private final String message;

    /**
     * A basic constructor, which will set the message into the global variable 'message'.
     *
     * @param message the error message to show.
     */
    public MowerInstructionFileException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
