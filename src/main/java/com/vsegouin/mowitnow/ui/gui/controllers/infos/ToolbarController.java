package com.vsegouin.mowitnow.ui.gui.controllers.infos;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class ToolbarController {
    @FXML
    private Text textInfo;

    /**
     * Set the global message.
     *
     * @param message the message to set
     */
    public void setInfoMessage(final String message) {
        this.textInfo.setText(message);
    }

    public Text getTextInfo() {
        return textInfo;
    }
}
