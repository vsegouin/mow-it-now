package com.vsegouin.mowitnow.ui.gui;

import com.google.inject.Singleton;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GraphicalApplication;
import com.vsegouin.mowitnow.ui.gui.utils.SceneUtil;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * This class will contains the GUI of the app.
 */
@Singleton
public class MainApp extends Application implements GraphicalApplication {
    private static final Logger LOGGER = Logger.getLogger(MainApp.class);
    private static MainApp instance;
    private LanguageManagerInterface languageManager;
    private Parent root;
    private Stage primaryStage;

    /**
     * The basic constructor, with a workaround to keep the instance of the Application.
     */
    public MainApp() {
        if (instance == null) {
            instance = this;
        }
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
    }

    // static method to get instance of view
    public static MainApp getInstance() {
        return instance;
    }

    /**
     * Entry point which will launch the GUI.
     */
    public static void main() {
        MainApp.launch();
    }

    @Override
    public void start(final Stage stage) throws IOException {
        this.root = SceneUtil.loadParent(stage, "scenes/main-scene.fxml", languageManager.getCurrentLocale());
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/main.css");
        this.primaryStage = stage;
        this.primaryStage.setTitle(languageManager.getString("project-name"));
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    @Override
    public void closeApp() {
        primaryStage.close();
    }

    @Override
    public void reload() {
        try {
            this.root = SceneUtil.loadParent(this.primaryStage, "scenes/main-scene.fxml", languageManager.getCurrentLocale());
            primaryStage.getScene().setRoot(root);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

}