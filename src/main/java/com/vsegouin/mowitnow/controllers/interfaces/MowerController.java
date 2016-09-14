package com.vsegouin.mowitnow.controllers.interfaces;

import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.mower.exceptions.ImpossibleMowerCommandException;
import com.vsegouin.mowitnow.mower.exceptions.UnknownMowerCommandException;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;

import java.util.List;
import java.util.Map;

/**
 * Created by v.segouin on 12/09/2016.
 */
public interface MowerController {
    /**
     * Return the mower presents in the map at coordinate x y.
     *
     * @param map the map where to find the mower
     * @param x   the abscissa to seek
     * @param y   the ordinate to seek
     * @return the found mower
     */
    Mower fetchMowerFromCoordinates(MapArea map, int x, int y);

    /**
     * Add a new mower in the map at the x y coordinate.
     *
     * @param map      the map where to put the mower
     * @param abscissa the abscissa to put the mower
     * @param ordinate the ordinate to put the mower
     */
    void addMower(MapArea map, int abscissa, int ordinate);

    /**
     * Add a new mower in the map at the x y coordinate.
     *
     * @param map   the map where to put the mower
     * @param mower the mower to add
     */
    void addMower(MapArea map, Mower mower);

    /**
     * Get the mower's addition number.
     *
     * @param map   the map where the mower is supposed to be.
     * @param mower the mower to find
     * @return the mower's number
     */
    int getMowerIndex(MapArea map, Mower mower);

    /**
     * Make the mower execute the command line.
     *
     * @param mower       the mower which wille execute the commands
     * @param commandLine the commands to execute
     * @throws UnknownMowerCommandException if asked command is null
     */
    void executesCommands(Mower mower, String commandLine) throws ImpossibleMowerCommandException;

    /**
     * Create a new map, its mowers and their commands and execute it all.
     *
     * @param fileContent the mower which will execute the command
     * @return the created map
     * @throws MowerInstructionFileException if file is bad formatted
     * @throws UnknownMowerCommandException  if command doesn't exists or is null
     */
    MapArea executeFileCommands(List<String> fileContent) throws ImpossibleMowerCommandException, MowerInstructionFileException;

    /**
     * Make the mower executes the command.
     *
     * @param mower        the mower which will execute the command
     * @param abbreviation the character which correspond to a command
     * @throws UnknownMowerCommandException if command doesn't exists
     */
    void executesCommands(Mower mower, char abbreviation) throws ImpossibleMowerCommandException;

    /**
     * Make the mower executes the command.
     *
     * @param mower   the mower which will execute the command
     * @param command the command to execute
     * @throws UnknownMowerCommandException if command doesn't exists
     */
    void executesCommands(Mower mower, MowerCommands command) throws ImpossibleMowerCommandException;

    /**
     * Get the full historic of the program.
     *
     * @return the full historic of the programs
     */
    List<Map.Entry<Mower, MowerCommands>> getProgramHistoric();

    /**
     * Get the complete list of the Map and the mowers and their commands line.
     *
     * @return the complete list of the Map and the mowers and their commands line
     */
    Map<MapArea, Map<Mower, String>> getMapMowersList();

    /**
     * Get the map located at the provided index.
     *
     * @param index the map's index to get
     * @return the found map
     */
    MapArea fetchMapFromIndex(int index);

    /**
     * Create a new empty map and add it to the mowerMapsList.
     *
     * @param x the map's width
     * @param y the map's height
     * @return the created map
     */
    MapArea createMap(int x, int y);

    /**
     * Get the map index.
     *
     * @param map the map to looks for
     * @return the place number of the map in the list
     */
    int getMapIndex(MapArea map);

    /**
     * Empty all the data.
     */
    void reset();

    /**
     * Remove the mower from the map.
     *
     * @param map      the map where to do the withdrawal
     * @param abscissa the supposed abscissa of the mower
     * @param ordinate the supposed ordinate of the mower
     */
    void removeMower(MapArea map, int abscissa, int ordinate);
}
