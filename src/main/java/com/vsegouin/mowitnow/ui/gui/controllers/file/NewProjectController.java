package com.vsegouin.mowitnow.ui.gui.controllers.file;

import com.google.inject.Inject;
import com.vsegouin.mowitnow.services.language.LanguageManager;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class NewProjectController {
    private final GUIController controller;
    private final LanguageManagerInterface languageManager;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    /**
     * Basic constructor.
     *
     * @param controller      the main GUI controller which orchestrate the GUI's component
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public NewProjectController(final GUIController controller, final LanguageManager languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    /**
     * Reset the app and reload the interface to get a brand new program.
     */
    @FXML
    public void newProjectAction() {
        controller.resetApp();
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        this.controller.setGlobalText(languageManager.getString("label-new-project-loaded"));
        stage.close();
    }

    /**
     * Close the dialog box.
     */
    @FXML
    public void cancelAction() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        this.controller.setGlobalText(languageManager.getString("label-action-stopped-by-user"));
        stage.close();

    }
}
