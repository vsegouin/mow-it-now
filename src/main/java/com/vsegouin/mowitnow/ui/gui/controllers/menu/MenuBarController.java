package com.vsegouin.mowitnow.ui.gui.controllers.menu;

import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import com.vsegouin.mowitnow.ui.gui.utils.SceneUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by segouin.v on 12/09/2016.
 */
public class MenuBarController extends MenuBar {
    private LanguageManagerInterface languageManager;
    private GUIController controller;

    @FXML
    private MenuItem newMapMenuItem;
    @FXML
    private MenuItem loadFileMenuItem;
    @FXML
    private MenuItem closeAppMenuItem;
    @FXML
    private MenuItem newProjectMenuItem;

    /**
     * The basic constructor.
     *
     * @param languageManager The language manager for the internationalization
     * @param guiController   the main GUI controller.
     */
    @Inject
    public MenuBarController(final LanguageManagerInterface languageManager, final GUIController guiController) {
        this.languageManager = languageManager;
        this.controller = guiController;
    }

    /**
     * Open the load file dialog box.
     *
     * @throws IOException if the fxml file is malformed
     */
    public void loadFileAction() throws IOException {
        Stage stage = new Stage();
        Parent root = SceneUtil.loadParent(stage, "scenes/modals/modal-load-file.fxml", languageManager.getCurrentLocale());
        Scene scene = new Scene(root);
        stage.setTitle(languageManager.getString("load-file"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load the french locale and reload the interface.
     */
    public void switchToFrench() {
        languageManager.loadLocale("fr", "fr");
        controller.reloadApp();
    }

    /**
     * Load the french locale and reload the interface.
     */
    public void switchToEnglish() {
        languageManager.loadLocale("en", "us");
        controller.reloadApp();
    }

    /**
     * Open the manual map creation dialog box.
     *
     * @throws IOException if the fxml file is malformed
     */
    public void createNewMap() throws IOException {
        Stage stage = new Stage();
        Parent root = SceneUtil.loadParent(stage, "scenes/modals/modal-new-map.fxml", languageManager.getCurrentLocale());
        Scene scene = new Scene(root);
        stage.setTitle(languageManager.getString("label-load-file"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Open the create new project dialog box.
     *
     * @throws IOException if the fxml file is malformed
     */
    public void createNewProject() throws IOException {
        Stage stage = new Stage();
        Parent root = SceneUtil.loadParent(stage, "scenes/modals/modal-new-project.fxml", languageManager.getCurrentLocale());
        Scene scene = new Scene(root);
        stage.setTitle(languageManager.getString("label-create-new-project"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Close the application.
     */
    public void closeApplication() {
        controller.closeApp();
    }

    /**
     * Open the bulk project add dialog box.
     *
     * @throws IOException if the fxml contains error.
     */
    public void bulkAddAction() throws IOException {
        Stage stage = new Stage();
        Parent root = SceneUtil.loadParent(stage, "scenes/modals/modal-bulk-edit.fxml", languageManager.getCurrentLocale());
        Scene scene = new Scene(root);
        stage.setTitle(languageManager.getString("label-bulk-add-project"));
        stage.setScene(scene);
        stage.show();
    }
}
