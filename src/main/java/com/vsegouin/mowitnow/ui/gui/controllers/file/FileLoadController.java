package com.vsegouin.mowitnow.ui.gui.controllers.file;

import com.google.inject.Inject;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.file.interfaces.FileControlInterface;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.MainControllerGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class FileLoadController {
    private static final Logger LOGGER = Logger.getLogger(FileLoadController.class);
    private final MainControllerGUI controller;
    private final LanguageManagerInterface languageManager;
    private FileControlInterface fileControl;

    @FXML
    private Label loadFileTitleLabel;
    @FXML
    private Label selectFileLoadLabel;
    @FXML
    private Label fileSelectedLabel;
    @FXML
    private Label infoMessageLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    @FXML
    private Button selectFileButton;
    private File file;

    /**
     * Basic constructor.
     *
     * @param controller      the GUI controller which orchestrate the others component
     * @param fileControl     the class to manage file loaded
     * @param languageManager the language manager for internationalization.
     */
    @Inject
    public FileLoadController(final MainControllerGUI controller, final FileControlInterface fileControl, final LanguageManagerInterface languageManager) {
        this.fileControl = fileControl;
        this.languageManager = languageManager;
        this.controller = controller;

    }

    /**
     * Open a dialog box to choose a file, and will check if the selected file is correctly formatted.
     */
    public void loadFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(languageManager.getString("open-resource-file"));
        file = fileChooser.showOpenDialog(new Stage());
        try {
            List<String> controls = fileControl.readFile(file.getAbsolutePath());
            fileControl.checkFileIntegrity(controls);
            infoMessageLabel.setText(languageManager.getString("file-ready-to-execute"));
            fileSelectedLabel.setText(file.getAbsolutePath());
            okButton.setDisable(false);
        } catch (MowerInstructionFileException e) {
            infoMessageLabel.setText(e.getMessage());
            LOGGER.error(e);
            okButton.setDisable(true);
        } catch (IOException e) {
            infoMessageLabel.setText(languageManager.getString("file-not-exists"));
            LOGGER.error(e);
            okButton.setDisable(true);
        } catch (NullPointerException e) {
            LOGGER.error(e);
            okButton.setDisable(true);
        }
    }

    /**
     * Will close the current dialog box.
     */
    public void cancelAction() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        this.controller.setGlobalText(languageManager.getString("label-action-stopped-by-user"));
        stage.close();
    }

    /**
     * Fetch the selected file and execute it thanks to the mowers controller.
     * if an error is detected will disable the button and ask for a new file.
     */
    public void executeFileAction() {
        try {
            controller.addMapPane(fileControl.readFile(file.getAbsolutePath()));
            // get a handle to the stage
            Stage stage = (Stage) okButton.getScene().getWindow();
            // do what you have to do
            this.controller.setGlobalText(languageManager.getString("label-file-executed", file.getAbsolutePath()));
            stage.close();
        } catch (IOException e) {
            this.infoMessageLabel.setText(languageManager.getString("error-occured-choose-another-file"));
            this.okButton.setDisable(true);
            LOGGER.error(e);
        }
    }
}
