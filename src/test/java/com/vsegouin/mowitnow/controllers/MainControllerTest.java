package com.vsegouin.mowitnow.controllers;

import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.Direction;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.mower.exceptions.UnknownMowerCommandException;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.file.FileControl;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class MainControllerTest {


    private MainController mainController;
    private MapArea map;

    @BeforeMethod
    public void setUp() {
        mainController = InjectorUtil.getInjector().getInstance(MainController.class);
        mainController.reset();
        map = mainController.init(5, 5);
    }

    @Test
    public void testGetMowerIndex() throws Exception {
        Mower mower = new Mower(map);
        Mower mower1 = new Mower(map);
        Mower mower2 = new Mower(map);
        mainController.addMower(map, mower);
        mainController.addMower(map, mower1);
        mainController.addMower(map, mower2);
        int mowerIndex = mainController.getMowerIndex(map, mower2);
        Assert.assertEquals(mowerIndex, 2);
    }

    @Test
    public void testExecutesCommands() throws Exception {
        FileControl fileControl = InjectorUtil.getInjector().getInstance(FileControl.class);
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructions.txt");
        File file = new File(url.getPath());
        List<String> commands = fileControl.readFile(file.getAbsolutePath());
        MapArea map = mainController.executeFileCommands(commands);
        Assert.assertEquals(map.getMaxAbscissa(), 6);
        Assert.assertEquals(map.getMaxOrdinate(), 6);
        Assert.assertEquals(mainController.getMapMowersList().size(), 2);
        Assert.assertEquals(mainController.getMapMowersList().get(map).size(), 2);
    }

    @Test
    public void testScenario() throws Exception {
        FileControl fileControl = InjectorUtil.getInjector().getInstance(FileControl.class);
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructions.txt");
        File file = new File(url.getPath());
        List<String> commands = fileControl.readFile(file.getAbsolutePath());
        MapArea map = mainController.executeFileCommands(commands);
        Mower mower1 = mainController.fetchMowerFromCoordinates(map, 1, 3);
        Mower mower2 = mainController.fetchMowerFromCoordinates(map, 5, 1);
        Assert.assertNotNull(mower1);
        Assert.assertNotNull(mower2);
        Assert.assertEquals(mower1.getDetector().getCurrentState()[0], 1);
        Assert.assertEquals(mower1.getDetector().getCurrentState()[1], 3);
        Assert.assertEquals(mower1.getDetector().getCurrentState()[2], Direction.NORTH.getAngle());
        Assert.assertEquals(mower2.getDetector().getCurrentState()[0], 5);
        Assert.assertEquals(mower2.getDetector().getCurrentState()[1], 1);
        Assert.assertEquals(mower2.getDetector().getCurrentState()[2], Direction.EAST.getAngle());
    }

    @Test(expectedExceptions = UnknownMowerCommandException.class)
    public void testExecutesCommandsError() throws Exception {
        FileControl fileControl = InjectorUtil.getInjector().getInstance(FileControl.class);
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructionsError.txt");
        File file = new File(url.getPath());
        List<String> commands = fileControl.readFile(file.getAbsolutePath());
        mainController.executeFileCommands(commands);
    }

    @Test(expectedExceptions = MowerInstructionFileException.class)
    public void testExecutesCommandsMalFormatted() throws Exception {
        FileControl fileControl = InjectorUtil.getInjector().getInstance(FileControl.class);
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructionsMalformattedMap.txt");
        File file = new File(url.getPath());
        List<String> commands = fileControl.readFile(file.getAbsolutePath());
        MapArea map = mainController.executeFileCommands(commands);
    }

    @Test
    public void testInit() {
        mainController.init(5, 5);
        assertEquals(mainController.getMapMowersList().size(), 1);
    }

    @Test
    public void testAddMowerFromCoordinate() throws Exception {
        mainController.addMower(map, 1, 1);
        mainController.addMower(map, 2, 3);
        assertEquals(2, mainController.getMapMowersList().get(map).size());
    }

    @Test
    public void testAddMowerFromInstance() throws Exception {
        Mower mower = new Mower(3, 3, Direction.SOUTH, map);
        mainController.addMower(map, mower);
        assertEquals(1, mainController.getMapMowersList().get(map).size());
    }

    @Test
    public void testFetchMowerFromCoordinates() throws Exception {
        Mower mower = new Mower(3, 5, Direction.NORTH, map);
        mainController.addMower(map, mower);
        assertEquals(mower, mainController.fetchMowerFromCoordinates(map, 3, 5));
    }

    @Test
    public void testFetchMowerFromCoordinatesError() throws Exception {
        mainController.addMower(map, 3, 5);
        assertEquals(null, mainController.fetchMowerFromCoordinates(map, 4, 5));
    }

    @Test(expectedExceptions = UnknownMowerCommandException.class)
    public void testExecutesCommands_abbreviationError() throws Exception {
        Mower mower = new Mower(1, 1, Direction.EAST, map);
        mainController.executesCommands(mower, 'X');
    }

    @Test(expectedExceptions = UnknownMowerCommandException.class)
    public void testExecutesCommands_commandNull() throws Exception {
        Mower mower = new Mower(1, 1, Direction.EAST, map);
        mainController.executesCommands(mower, MowerCommands.fetchCommand('P'));
    }
}