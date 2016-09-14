package com.vsegouin.mowitnow.ui.gui.controllers.actions;

import com.vsegouin.mowitnow.ui.gui.ProgramState;
import com.vsegouin.mowitnow.ui.gui.controllers.MainControllerGUI;
import com.vsegouin.mowitnow.ui.gui.controllers.helper.JavaFXInitializer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by v.segouin on 14/09/2016.
 */
public class MapActionTest {
    private MapAction mapActionController;
    private MainControllerGUI mainControllerGUI;

    @BeforeClass
    public void init() throws IOException, InterruptedException {
        JavaFXInitializer.initialize();
        mainControllerGUI = (MainControllerGUI) JavaFXInitializer.getController();
        mapActionController = mainControllerGUI.getActionPaneController();
    }


    @Test(priority = 2)
    public void testPrepareAddMower() throws Exception {
        mainControllerGUI.addMapPane(5, 5);
        mapActionController.prepareAddMower();
        Assert.assertEquals(mainControllerGUI.getProgramState(), ProgramState.ADD_MOWER);
    }

    @Test(priority = 2)
    public void testPrepareRemoveMower() throws Exception {
        mainControllerGUI.addMapPane(5, 5);
        mapActionController.prepareRemoveMower();
        Assert.assertEquals(mainControllerGUI.getProgramState(), ProgramState.REMOVE_MOVER);
    }

}