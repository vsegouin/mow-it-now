package com.vsegouin.mowitnow.ui.gui.controllers.actions;

import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.ProgramState;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.inject.Inject;

/**
 * Created by segouin.v on 13/09/2016.
 */
public class MapAction {
    private final GUIController controller;
    private final LanguageManagerInterface languageManager;
    @FXML
    private Button removeMowerButton;
    @FXML
    private Button addMowerButton;

    /**
     * Basic constructor.
     *
     * @param controller      the GUI controller which orchestrate the others component.
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public MapAction(final GUIController controller, final LanguageManagerInterface languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    /**
     * Change program state to allow the addition of a mower when clicking on the matrix.
     */
    @FXML
    public void prepareAddMower() {
        if (controller.getCurrentMap() != null) {
            controller.setProgramState(ProgramState.ADD_MOWER);
            controller.setGlobalText(languageManager.getString("label-info-click-on-box-add-mower"));
        } else {
            controller.setGlobalText(languageManager.getString("label-create-map-first"));
        }
    }

    /**
     * Change program state to allow the deletion of a mower when clicking on the matrix.
     */
    @FXML
    public void prepareRemoveMower() {
        if (controller.getCurrentMap() != null) {
            controller.setProgramState(ProgramState.REMOVE_MOVER);
            controller.setGlobalText(languageManager.getString("label-info-click-on-box-to-remove"));
        } else {
            controller.setGlobalText(languageManager.getString("label-create-map-first"));
        }
    }
}
