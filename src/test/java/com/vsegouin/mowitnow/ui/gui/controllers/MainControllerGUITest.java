package com.vsegouin.mowitnow.ui.gui.controllers;

import com.vsegouin.mowitnow.controllers.MainController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.file.FileControl;
import com.vsegouin.mowitnow.ui.gui.ProgramState;
import com.vsegouin.mowitnow.ui.gui.controllers.helper.JavaFXInitializer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;


/**
 * Created by segouin.v on 14/09/2016.
 */

public class MainControllerGUITest {
    private MainController mainController;
    private MainControllerGUI mainControllerGUI;
    private MapArea testMap;
    private FileControl fileControl;

    @BeforeClass
    public void init() throws IOException, InterruptedException {
        fileControl = InjectorUtil.getInjector().getInstance(FileControl.class);
        JavaFXInitializer.initialize();
        mainControllerGUI = (MainControllerGUI) JavaFXInitializer.getController();
        testMap = new MapArea(5, 5);
    }


    @Test
    public void testSetGlobalText() throws Exception {
        mainControllerGUI.setGlobalText("test message");
        assertEquals(mainControllerGUI.getToolbarController().getTextInfo().getText(), "test message");
    }

    @Test
    public void testAddMapPane() throws Exception {
        mainControllerGUI.addMapPane(5, 5);
        Assert.assertEquals(mainControllerGUI.getController().getMapMowersList().size(), 1);
    }

    @Test
    public void testAddMapPane1() throws Exception {
        mainControllerGUI.addMapPane(5, 5);
        Assert.assertEquals(mainControllerGUI.getController().getMapMowersList().size(), 2);

    }

    @Test
    public void testExecuteCommands() throws Exception {

    }

    @Test
    public void testGetCurrentlySelectedMower() throws Exception {

    }

    @Test
    public void testGetSetProgramState() throws Exception {
        mainControllerGUI.setProgramState(null);
        Assert.assertNull(mainControllerGUI.getProgramState());
        mainControllerGUI.setProgramState(ProgramState.ADD_MOWER);
        Assert.assertEquals(ProgramState.ADD_MOWER, mainControllerGUI.getProgramState());
    }

    @Test
    public void testExecuteProgramState_addMower() throws Exception {
        MapArea map = mainControllerGUI.addMapPane(5, 5);
        mainControllerGUI.setProgramState(ProgramState.ADD_MOWER);
        mainControllerGUI.executeProgramState(2, 3);
        Mower mower = mainControllerGUI.getController().fetchMowerFromCoordinates(map, 2, 3);
        Assert.assertNotNull(mower);

        mainControllerGUI.setProgramState(ProgramState.SELECT_MOWER);
        mainControllerGUI.executeProgramState(2, 3);
        Assert.assertEquals(mainControllerGUI.getCurrentlySelectedMower(), mower);

        mainControllerGUI.setProgramState(ProgramState.REMOVE_MOVER);
        mainControllerGUI.executeProgramState(2, 3);
        Mower mower2 = mainControllerGUI.getController().fetchMowerFromCoordinates(map, 2, 3);
        Assert.assertNull(mower2);


    }

    @Test
    public void testGetCurrentMap() throws Exception {
        MapArea map = mainControllerGUI.addMapPane(10, 10);
        Assert.assertEquals(mainControllerGUI.getCurrentMap(), map);
    }

    @Test
    public void testSetCurrentMap() throws Exception {
        MapArea map = mainControllerGUI.addMapPane(6, 1);
        MapArea map2 = mainControllerGUI.addMapPane(2, 2);
        Assert.assertEquals(mainControllerGUI.getCurrentMap(), map2);
        mainControllerGUI.setCurrentMap(map);
        Assert.assertEquals(mainControllerGUI.getCurrentMap(), map);

    }

    /**
     * Impossible to test in another Thread than the application.
     */
    @Test(expectedExceptions = IllegalStateException.class)
    public void testResetApp() throws Exception {
        mainControllerGUI.resetApp();
    }

}