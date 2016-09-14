package com.vsegouin.mowitnow.ui.gui.controllers.remote;

import com.google.inject.Inject;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.ui.gui.controllers.MainControllerGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class RemoteControlController {
    private final MainControllerGUI controller;
    @FXML
    private Button turnLeftButton;
    @FXML
    private Button goForwardButton;
    @FXML
    private Button turnRightButton;
    @FXML
    private Button goBackwardButton;

    /**
     * Basic constructor.
     *
     * @param controller the main GUI controller which orchestrate the GUI's component
     */
    @Inject
    public RemoteControlController(final MainControllerGUI controller) {
        this.controller = controller;
    }

    /**
     * Make the selected mower to turn left.
     */
    @FXML
    public void goLeftClicked() {
        controller.executeCommands(MowerCommands.LEFT);
    }

    /**
     * Make the selected mower to go forward.
     */
    @FXML
    public void goForwardClicked() {
        controller.executeCommands(MowerCommands.FORWARD);

    }

    /**
     * Make the selected mower to turn right.
     */
    @FXML
    public void goRightClicked() {
        controller.executeCommands(MowerCommands.RIGHT);
    }

    /**
     * Make the selected mower to go backward.
     */
    @FXML
    public void goBackwardClicked() {
        controller.executeCommands(MowerCommands.BACKWARD);
    }


}
