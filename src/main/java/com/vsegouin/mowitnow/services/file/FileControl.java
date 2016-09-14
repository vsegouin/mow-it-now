package com.vsegouin.mowitnow.services.file;

import com.google.inject.Singleton;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.file.interfaces.FileControlInterface;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v.segouin on 08/09/2016.
 * This class can read an instruction file, generate the stories and executes thems.
 */
@Singleton
public class FileControl implements FileControlInterface {

    private static final Logger LOGGER = Logger.getLogger(FileControl.class);
    private static final int MINIMAL_FILE_SIZE = 3;
    private LanguageManagerInterface languageManager;

    /**
     * The default constructor used by the Dependency injector.
     *
     * @param languageManager The injected language manager for internationalization
     */
    @Inject
    public FileControl(final LanguageManagerInterface languageManager) {
        this.languageManager = languageManager;
    }

    /**
     * check if a file exists.
     *
     * @param filePath location of the file to tests
     * @return true if the file exists, false if not
     */
    public static boolean fileExists(final String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Check the initial position of a mower and throw an exception if it contains an error.
     *
     * @param mapSize         the map in order to check if initial position aren't outside boundaries.
     * @param mowerDataString the String map containing the mower's initial position.
     * @param index           the line number in the file, used for the exception
     * @throws MowerInstructionFileException if a parameter is incorrect
     */
    private static void checkInitialMowersPositionAreCorrect(final int[] mapSize, final String[] mowerDataString, final int index) throws MowerInstructionFileException {
        LanguageManagerInterface languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
        if (mowerDataString.length < MINIMAL_FILE_SIZE || !mowerDataString[0].matches("\\d+") || !mowerDataString[1].matches("\\d+")) {
            throw new MowerInstructionFileException(languageManager.getString("label-error-initial-position-not-integer", Integer.toString(index + 1)));
        }
        if (Direction.fetchDirection(mowerDataString[2]) == null) {
            throw new MowerInstructionFileException(languageManager.getString("label-error-initial-direction-incorrect", mowerDataString[2], Integer.toString(index + 1)));
        }
        if (Integer.parseInt(mowerDataString[0]) > mapSize[0] || Integer.parseInt(mowerDataString[1]) > mapSize[1]) {
            throw new MowerInstructionFileException(languageManager.getString("label-error-initial-position-out-of-bound"));
        }
    }

    /**
     * Check a command line and throw an exception if it contains an unhandled command.
     *
     * @param commandsString the commands line to check
     * @param line           the index of the line in the file, used in the exception
     * @throws MowerInstructionFileException if one of the commands aren't expected
     */
    private static void checkCommandsExists(final String commandsString, final int line) throws MowerInstructionFileException {
        for (char abbreviation : commandsString.toCharArray()) {
            if (MowerCommands.fetchCommand(abbreviation) == null) {
                throw new MowerInstructionFileException("Command : " + abbreviation + " doesn't exists at line : " + (line + 2));
            }

        }
    }

    /**
     * use the maps configured in the file and will create a new map.
     *
     * @param fileContents the content of the file which contains commands data.
     * @return a new map instance configured with the parameters set in the file.
     */
    @Override
    public MapArea initializeMap(final List<String> fileContents) throws MowerInstructionFileException {
        String[] mapSize = fileContents.get(0).split(" ");
        if (mapSize.length < 2) {
            throw new MowerInstructionFileException("A map initialization param is missing");
        }
        try {
            return new MapArea(Integer.parseInt(mapSize[0]), Integer.parseInt(mapSize[1]));
        } catch (NumberFormatException e) {
            throw new MowerInstructionFileException("A map initialization param is not a number");
        }
    }

    /**
     * Will read and parse the 'commandLines' extracted with the 'readFile' method and will set them on the map,
     * it will return a map with the mowers and their commands.
     *
     * @param initialMap    the map where the mower will be set on.
     * @param commandsLines the content of the file which contains commands data.
     * @return a map containing all the mowers with their corresponding commands
     */
    @Override
    public Map<Mower, String> loadMowers(final List<String> commandsLines, final MapArea initialMap) {
        LinkedHashMap<Mower, String> mowersList = new LinkedHashMap<>();

        for (int i = 1; i < commandsLines.size(); i += 2) {
            String[] mowerInitParams = commandsLines.get(i).split(" ");
            Mower mower = new Mower(Integer.parseInt(mowerInitParams[0]), Integer.parseInt(mowerInitParams[1]), Direction.fetchDirection(mowerInitParams[2]), initialMap);
            mowersList.put(mower, commandsLines.get(i + 1));
        }
        return mowersList;
    }

    /**
     * Read a file and puts all the data in a String array.
     *
     * @param filename the file location
     * @return an array containing line by line the content of the file
     * @throws IOException if the file doesn't exists or if it's broken
     */
    @Override
    public List<String> readFile(final String filename) throws IOException {
        List<String> records = new ArrayList<>();
        InputStreamReader fr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
        BufferedReader reader = new BufferedReader(fr);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
        } catch (Exception e) {
            LOGGER.error(languageManager.getString("file-read-exception", filename));
            LOGGER.error(e);
            records = new ArrayList<>();
        } finally {
            reader.close();
            fr.close();
        }

        return records;

    }

    /**
     * Will read all the lines contained in the 'lines' variable and will check steps by steps if the file is
     * correctly written.
     *
     * @param lines the commands data located in the 'FilePath' variable and extracted with readFile()
     * @return true if the files doesn't contains any error
     * @throws MowerInstructionFileException if the file is malformed
     */
    @Override
    public boolean checkFileIntegrity(final List<String> lines) throws MowerInstructionFileException {
        if (lines.size() < MINIMAL_FILE_SIZE) {
            throw new MowerInstructionFileException("The file must at least contains the map size and the first mower data [initial position, list of commands]");
        } else if ((lines.size() - 1) % 2 != 0) {
            throw new MowerInstructionFileException("One of the mower's data is missing");
        }
        checkMapDataAreCorrect(lines.get(0));
        int[] mapSize = {Integer.parseInt(lines.get(0).split(" ")[0]), Integer.parseInt(lines.get(0).split(" ")[1])};
        for (int i = 1; i < lines.size(); i += 2) {
            String mowerString = lines.get(i);
            String commandsString = lines.get(i + 1);
            String[] mowerDataString = mowerString.split(" ");

            checkInitialMowersPositionAreCorrect(mapSize, mowerDataString, i);
            checkCommandsExists(commandsString, i);
        }
        return true;
    }

    /**
     * Will check if the line which contains the map data is correct.
     *
     * @param mapLine the line containing the map data
     * @throws MowerInstructionFileException if there is an error
     */
    private void checkMapDataAreCorrect(final String mapLine) throws MowerInstructionFileException {
        String[] t = mapLine.split(" ");
        if (t.length < 2) {
            throw new MowerInstructionFileException("The map instructions aren't complete");
        }
        if (!t[0].matches("\\d+") || !t[1].matches("\\d+")) {
            throw new MowerInstructionFileException("One of the map parameter is not an integer");
        }
    }
}
