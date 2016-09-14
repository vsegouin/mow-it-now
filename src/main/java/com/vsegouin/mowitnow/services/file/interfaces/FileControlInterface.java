package com.vsegouin.mowitnow.services.file.interfaces;

import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This interface contains all the necessary methods to load a file and initialize the programs.
 * Created by v.segouin on 12/09/2016.
 */
public interface FileControlInterface {
    /**
     * Read the file and returns the commands list.
     *
     * @param fileLocation the file path
     * @return an array of String which contains the file parsed
     * @throws IOException if the file doesn't exists.
     */
    List<String> readFile(String fileLocation) throws IOException;

    /**
     * Check if a file has all the requirements to be considered as valid.
     *
     * @param controls the commands value to check
     * @return true if the file is valid
     * @throws MowerInstructionFileException if it contains an error.
     */
    boolean checkFileIntegrity(List<String> controls) throws MowerInstructionFileException;

    /**
     * Initialize a new map thanks to the command file.
     *
     * @param fileContent the commands to use to create the map
     * @return the created map
     * @throws MowerInstructionFileException if file is mal formatted
     */
    MapArea initializeMap(List<String> fileContent) throws MowerInstructionFileException;

    /**
     * Load the mowers and their corresponding commands line from file.
     *
     * @param fileContent the commands to use to load the mowers
     * @param map         the map where the mowers will progress
     * @return the list of mowers and their corresponding commands.
     */
    Map<Mower, String> loadMowers(List<String> fileContent, MapArea map);

}
