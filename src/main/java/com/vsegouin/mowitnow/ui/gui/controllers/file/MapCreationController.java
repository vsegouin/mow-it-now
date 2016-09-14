package com.vsegouin.mowitnow.ui.gui.controllers.file;

import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.inject.Inject;

/**
 * Created by segouin.v on 13/09/2016.
 */
public class MapCreationController {
    private static final Logger LOGGER = Logger.getLogger(MapCreationController.class);
    private final GUIController controller;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    @FXML
    private TextField widthForm;
    @FXML
    private TextField heightForm;
    private LanguageManagerInterface languageManager;

    /**
     * Basic constructor.
     *
     * @param controller      the GUI controller which orchestrate the others component.
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public MapCreationController(final GUIController controller, final LanguageManagerInterface languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    /**
     * Will close the current dialog box.
     */
    public void cancelAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Get the form values, check if it's a regular number and will create a new empty map.
     */
    public void createNewMap() {
        int width = 0;
        int height = 0;
        try {
            width = Integer.parseInt(widthForm.getText());
            height = Integer.parseInt(widthForm.getText());
            controller.addMapPane(width - 1, height - 1);
            Stage stage = (Stage) okButton.getScene().getWindow();
            // do what you have to do
            this.controller.setGlobalText(languageManager.getString("label-map-created", Integer.toString(width), Integer.toString(height)));
            stage.close();

        } catch (NumberFormatException e) {
            LOGGER.error(e);
        }

    }
}
