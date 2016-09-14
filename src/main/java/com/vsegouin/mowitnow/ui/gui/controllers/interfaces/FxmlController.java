package com.vsegouin.mowitnow.ui.gui.controllers.interfaces;

import javafx.stage.Stage;

/**
 * If a controller implements this interface, the setStageAndSetupListeners method will be automatically called when the
 * FXML Loader will load the interface.
 * Created by segouin.v on 14/09/2016.
 */
@FunctionalInterface
public interface FxmlController {
    /**
     * Store the stage in the current controller.
     *
     * @param stage the stage to store, where the controller is technically located
     */
    void setStageAndSetupListeners(Stage stage);
}
