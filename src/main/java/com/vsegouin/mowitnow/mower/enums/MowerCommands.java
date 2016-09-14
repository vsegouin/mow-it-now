package com.vsegouin.mowitnow.mower.enums;

import java.util.HashMap;

/**
 * The different commands a mower can execute.
 * Created by v.segouin on 08/09/2016.
 */
public enum MowerCommands {
    LEFT('G', "Left"),
    RIGHT('D', "Right"),
    FORWARD('A', "Forward"),
    BACKWARD('R', "Backward");

    private static HashMap<Character, MowerCommands> map;

    static {
        map = new HashMap<>();
        for (MowerCommands mowerCommands : MowerCommands.values()) {
            map.put(mowerCommands.abbreviation, mowerCommands);
        }
    }

    private final char abbreviation;
    private String commandName;

    /**
     * A basic constructor.
     *
     * @param abbreviation the abbreviation which represents the command
     * @param commandName  a human readable name of the command.
     */
    MowerCommands(final char abbreviation, final String commandName) {
        this.abbreviation = abbreviation;
        this.commandName = commandName;
    }

    /**
     * Find a commands thanks to the abbreviation.
     *
     * @param abbreviation the abbreviation which represents the commands
     * @return the command found with the abbreviation.
     */
    public static MowerCommands fetchCommand(final char abbreviation) {
        return map.get(abbreviation);
    }

    public String getCommandName() {
        return commandName;
    }

}
