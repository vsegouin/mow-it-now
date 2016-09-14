package com.vsegouin.mowitnow.ui.gui.controllers.interfaces;

import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.ui.gui.ProgramState;

import java.util.List;

/**
 * Created by v.segouin on 12/09/2016.
 */
public interface GUIController {
    /**
     * Set the message text readable from anywhere.
     *
     * @param message the message to set
     */
    void setGlobalText(String message);

    /**
     * Create a new tab containing the results of file executing.
     *
     * @param file the file to execute.
     * @return the newly created map
     */
    MapArea addMapPane(List<String> file);

    /**
     * Create a tab with an empty matrix.
     *
     * @param x the width of the matrix
     * @param y the height of the matrix
     * @return the newly created map
     */
    MapArea addMapPane(int x, int y);

    /**
     * Make the current mower to execute the command.
     *
     * @param command the command to execute.
     */
    void executeCommands(MowerCommands command);

    /**
     * Get the current selected mower.
     *
     * @return the current selected mower
     */
    Mower getCurrentlySelectedMower();

    /**
     * Return the program state, the state corresponds to the action that will be executed by executeProgramState.
     *
     * @return the program state
     */
    ProgramState getProgramState();

    /**
     * Set the program state.
     *
     * @param state the program state
     */
    void setProgramState(ProgramState state);

    /**
     * Execute the action corresponding to the program state on the selected area.
     *
     * @param abscissa the abscissa of the selected area
     * @param ordinate the ordinate of the selected area
     */
    void executeProgramState(int abscissa, int ordinate);

    /**
     * Close the application.
     */
    void closeApp();

    /**
     * Reload the interfaces and restore the saved data.
     */
    void reloadApp();

    /**
     * Get the currently selected map.
     *
     * @return the currently selected map
     */
    MapArea getCurrentMap();

    /**
     * Set the currently selected map.
     *
     * @param mapArea the currently selected map
     */
    void setCurrentMap(MapArea mapArea);

    /**
     * Reset the app with empty data.
     */
    void resetApp();

}
