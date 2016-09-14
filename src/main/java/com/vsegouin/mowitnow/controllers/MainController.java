package com.vsegouin.mowitnow.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.mower.enums.PositionState;
import com.vsegouin.mowitnow.mower.exceptions.ImpossibleMowerCommandException;
import com.vsegouin.mowitnow.mower.exceptions.UnknownMowerCommandException;
import com.vsegouin.mowitnow.mower.exceptions.UnsafeMoveException;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.file.interfaces.FileControlInterface;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * The main controller which will keep the program coherent and do the differents actions on the mowers.
 * It's the main interface with the different UI
 * Created by v.segouin on 11/09/2016.
 */
@Singleton
public class MainController implements MowerController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private final LanguageManagerInterface languageManager;
    private FileControlInterface fileControl;

    private Map<MapArea, Map<Mower, String>> mapMowersList;
    private List<Map.Entry<Mower, MowerCommands>> commandHistoric;

    /**
     * Basic private constructor, instantiation a new Hashmap which will contains mowers data.
     *
     * @param fileControl     the File controller used to load and executes file.
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public MainController(final FileControlInterface fileControl, final LanguageManagerInterface languageManager) {
        this.fileControl = fileControl;
        this.languageManager = languageManager;
        mapMowersList = new LinkedHashMap<>();
        commandHistoric = new ArrayList<>();
    }

    /**
     * initialize the map.
     *
     * @param x the map width
     * @param y the map height
     * @return the newly created map
     */
    public MapArea init(final int x, final int y) {
        MapArea map = new MapArea(x, y);
        mapMowersList = new LinkedHashMap<>();
        mapMowersList.put(map, new LinkedHashMap<>());
        commandHistoric = new ArrayList<>();
        return map;
    }

    /**
     * Will check the mower's state and will return the mower located at the 'x','y' position.
     *
     * @param x the abscissa where the mower is supposed to be
     * @param y the ordinate where the mower is supposed to be
     * @return the mower located at x,y, will return null if there is no mower.
     */
    @Override
    public Mower fetchMowerFromCoordinates(final MapArea map, final int x, final int y) {
        for (Map.Entry<Mower, String> mower : mapMowersList.get(map).entrySet()) {
            int[] state = mower.getKey().getDetector().getCurrentState();
            if (state[0] == x && state[1] == y) {
                return mower.getKey();
            }
        }
        return null;
    }

    /**
     * create a new mower at the cordinates provided.
     *
     * @param abscissa the abscissa where the mower will be
     * @param ordinate the ordinate where the mower will be
     */
    @Override
    public void addMower(final MapArea map, final int abscissa, final int ordinate) {
        Mower mower = new Mower(abscissa, ordinate, Direction.NORTH, map);
        this.mapMowersList.get(map).put(mower, "");
    }

    /**
     * add a new mower.
     *
     * @param mower the abscissa where the mower will be
     */
    @Override
    public void addMower(final MapArea map, final Mower mower) {
        this.mapMowersList.get(map).put(mower, "");
    }

    /**
     * will check the mower's position in the map.
     *
     * @param mower the mower to find
     * @return the index (order of insertion) of the mower
     */
    @Override
    public int getMowerIndex(final MapArea map, final Mower mower) {
        List keys = new ArrayList<>(this.mapMowersList.get(map).keySet());
        int index = -1;
        for (int i = 0; i < keys.size(); i++) {
            if (mower.equals(keys.get(i))) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Parse the commandLine, will extract each command one by one and will make the mower to execute it.
     *
     * @param mower       the mower which will execute the commands
     * @param commandLine the commands line (ADGB) to execute
     */
    @Override
    public final void executesCommands(final Mower mower, final String commandLine) throws ImpossibleMowerCommandException {
        for (char abbreviation : commandLine.toCharArray()) {
            executesCommands(mower, abbreviation);
        }
    }


    @Override
    public MapArea executeFileCommands(final List<String> fileContent) throws ImpossibleMowerCommandException, MowerInstructionFileException {
        MapArea map = fileControl.initializeMap(fileContent);
        Map<Mower, String> mowers = new LinkedHashMap<>();
        for (int i = 1; i < fileContent.size(); i += 2) {
            String[] mowerInitParams = fileContent.get(i).split(" ");
            Mower mower = new Mower(Integer.parseInt(mowerInitParams[0]), Integer.parseInt(mowerInitParams[1]), Direction.fetchDirection(mowerInitParams[2]), map);
            String commands = fileContent.get(i + 1);
            executesCommands(mower, commands);
            mowers.put(mower, commands);
            LOGGER.info(mower.drawPosition());
        }

        mapMowersList.put(map, mowers);
        return map;
    }

    /**
     * Make the mower to execute one command.
     *
     * @param mower        the mower which will execute the commands
     * @param abbreviation The abbreviation which represents the command
     */
    @Override
    public void executesCommands(final Mower mower, final char abbreviation) throws ImpossibleMowerCommandException {
        MowerCommands command = MowerCommands.fetchCommand(abbreviation);
        if (command == null) {
            throw new UnknownMowerCommandException(languageManager.getString("label-command-dont-exists", Character.toString(abbreviation)));
        } else {
            executesCommands(mower, command);
        }
    }

    @Override
    public void executesCommands(final Mower mower, final MowerCommands command) throws UnknownMowerCommandException {
        if (command == null) {
            throw new UnknownMowerCommandException("command asked is null");
        }
        try {
            switch (command) {
                case LEFT:
                    mower.turnLeft();
                    break;
                case RIGHT:
                    mower.turnRight();
                    break;
                case FORWARD:
                    mower.goForward();
                    break;
                case BACKWARD:
                    mower.goBackward();
                    break;
                default:
                    break;
            }
        } catch (UnsafeMoveException e) {
            LOGGER.error(e);
            LOGGER.info("Unknown move, skipping it");
        }

        addCommandToHistoric(mower, command);
    }

    /**
     * Add a comment to the historic.
     *
     * @param mower   the subject of the historic
     * @param command the action done
     */
    private void addCommandToHistoric(final Mower mower, final MowerCommands command) {
        commandHistoric.add(new AbstractMap.SimpleEntry<>(mower, command));
    }

    @Override
    public List<Map.Entry<Mower, MowerCommands>> getProgramHistoric() {
        return commandHistoric;
    }

    @Override
    public Map<MapArea, Map<Mower, String>> getMapMowersList() {
        return mapMowersList;
    }

    public void setMapMowersList(final Map<MapArea, Map<Mower, String>> mapMowersList) {
        this.mapMowersList = mapMowersList;
    }

    @Override
    public MapArea fetchMapFromIndex(final int selectedIndex) {
        List keys = new ArrayList(mapMowersList.keySet());
        return (MapArea) keys.get(selectedIndex);
    }

    @Override
    public MapArea createMap(final int x, final int y) {
        MapArea map = new MapArea(x, y);
        mapMowersList.put(map, new HashMap<>());
        return map;
    }

    @Override
    public int getMapIndex(final MapArea map) {
        List keys = new ArrayList(this.mapMowersList.keySet());
        int index = -1;
        for (int i = 0; i < keys.size(); i++) {

            if (map.equals(keys.get(i))) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public void reset() {
        this.mapMowersList = new LinkedHashMap<>();
        this.commandHistoric = new ArrayList<>();
    }

    @Override
    public void removeMower(final MapArea map, final int abscissa, final int ordinate) {
        Mower mower = fetchMowerFromCoordinates(map, abscissa, ordinate);
        if (mower != null) {
            mapMowersList.get(map).remove(mower);
            map.setCoordinate(abscissa, ordinate, PositionState.MOWED_GRASS.getStateNumber());
        }
    }
}
