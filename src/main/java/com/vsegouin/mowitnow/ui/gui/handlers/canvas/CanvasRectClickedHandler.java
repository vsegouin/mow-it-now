package com.vsegouin.mowitnow.ui.gui.handlers.canvas;

import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * This handler manage the actions to be done when the user click on one of the graphical matrix's rectangle.
 * Depending on the program's state the action will be different.
 * Created by v.segouin on 11/09/2016.
 */
public class CanvasRectClickedHandler implements EventHandler<MouseEvent> {
    private GUIController context;
    private int abscissa;
    private int ordinate;

    /**
     * Basic constructor.
     *
     * @param context  the container's context
     * @param abscissa the abscissa which the box represents
     * @param ordinate the ordinate which the box represents
     */
    public CanvasRectClickedHandler(final GUIController context, final int abscissa, final int ordinate) {
        this.context = context;
        this.abscissa = abscissa;
        this.ordinate = ordinate;
    }

    @Override
    public void handle(final MouseEvent event) {
        context.executeProgramState(abscissa, ordinate);
    }

}
