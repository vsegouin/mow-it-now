package com.vsegouin.mowitnow.ui.gui.controllers.file;

import com.vsegouin.mowitnow.services.file.FileControl;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by segouin.v on 14/09/2016.
 */
public class BulkAddProjectController {
    private static final Logger LOGGER = Logger.getLogger(BulkAddProjectController.class);
    private final GUIController mainController;
    private final FileControl fileControl;

    @FXML
    private TextArea lineCommandTextArea;
    @FXML
    private Text lineCommandErrorsText;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    private LanguageManagerInterface languageManager;

    /**
     * Basic controller.
     *
     * @param controller      the controller which manage the whole GUI
     * @param fc              the file control service.
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public BulkAddProjectController(final GUIController controller, final FileControl fc, final LanguageManagerInterface languageManager) {
        this.mainController = controller;
        this.languageManager = languageManager;
        this.fileControl = fc;
    }

    /**
     * check the commands provided by user and process the addition to the controllers.
     */
    @FXML
    public void newProjectAction() {
        String text = lineCommandTextArea.getText();
        List<String> commandList = Arrays.asList(text.split("\\r?\\n"));
        try {
            fileControl.checkFileIntegrity(commandList);
            mainController.addMapPane(commandList);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (MowerInstructionFileException e) {
            LOGGER.error(e);
            lineCommandErrorsText.setText(e.getMessage());
        }
    }

    /**
     * Close the dialog box.
     */
    @FXML
    public void cancelAction() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        this.mainController.setGlobalText(languageManager.getString("label-action-stopped-by-user"));
        stage.close();
    }
}
