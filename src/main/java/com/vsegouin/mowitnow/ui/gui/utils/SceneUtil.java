package com.vsegouin.mowitnow.ui.gui.utils;

import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.FxmlController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class SceneUtil {
    /**
     * Basic private constructor to avoid any instantiation.
     */
    private SceneUtil() {

    }

    /**
     * A global method which will load the FXMLLoader, import the Guice and set the locale for the interface building.
     *
     * @param fxmlPath the fxml file path to build
     * @param locale   the locale for the localization.
     * @param stage    the stage where the the loaded interface will be put.
     * @return the result of the building
     * @throws IOException if the FXML path is wrong
     */
    public static Parent loadParent(final Stage stage, final String fxmlPath, final Locale locale) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fxmlPath);
        ResourceBundle bundle = ResourceBundle.getBundle("language.GUIBundle", locale);
        FXMLLoader loader = new FXMLLoader(url, bundle);
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(final Class<?> type) {
                return InjectorUtil.getInjector().getInstance(type);
            }
        });
        Parent node = loader.load();
        Object controller = loader.getController();
        if (controller instanceof FxmlController) {
            ((FxmlController) controller).setStageAndSetupListeners(stage);
        }
        return node;
    }

}
