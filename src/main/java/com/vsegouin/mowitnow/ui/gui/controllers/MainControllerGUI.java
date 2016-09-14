package com.vsegouin.mowitnow.ui.gui.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vsegouin.mowitnow.controllers.MainController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.mower.exceptions.ImpossibleMowerCommandException;
import com.vsegouin.mowitnow.mower.exceptions.UnknownMowerCommandException;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.MainApp;
import com.vsegouin.mowitnow.ui.gui.ProgramState;
import com.vsegouin.mowitnow.ui.gui.controllers.actions.MapAction;
import com.vsegouin.mowitnow.ui.gui.controllers.infos.HistoricPaneController;
import com.vsegouin.mowitnow.ui.gui.controllers.infos.MowerListController;
import com.vsegouin.mowitnow.ui.gui.controllers.infos.TabMapController;
import com.vsegouin.mowitnow.ui.gui.controllers.infos.ToolbarController;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.FxmlController;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import com.vsegouin.mowitnow.ui.gui.controllers.menu.MenuBarController;
import com.vsegouin.mowitnow.ui.gui.controllers.remote.RemoteControlController;
import com.vsegouin.mowitnow.ui.gui.utils.SceneUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by v.segouin on 12/09/2016.
 */
@Singleton
public class MainControllerGUI implements GUIController, FxmlController {
    private static final Logger LOGGER = Logger.getLogger(MainControllerGUI.class);
    private final MainController controller;
    private final LanguageManagerInterface languageManager;
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private ToolbarController toolbarController;
    @FXML
    private TabMapController tabMapController;
    @FXML
    private HistoricPaneController historicController;
    @FXML
    private RemoteControlController remoteControlController;
    @FXML
    private MowerListController mowersListController;
    @FXML
    private MapAction actionPaneController;

    private Mower currentlySelectedMower;
    private ProgramState programState;
    private MapArea currentlySelectedMap;
    private Stage currentStage;

    /**
     * The basic constructor, with DI thanks to Guice.
     *
     * @param controller      the mower's main controller to execute program's command
     * @param languageManager the language manager used for internationalization
     */
    @Inject
    public MainControllerGUI(final MainController controller, final LanguageManagerInterface languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    @Override
    public void setGlobalText(final String message) {
        toolbarController.setInfoMessage(message);
    }

    @Override
    public MapArea addMapPane(final int x, final int y) {
        MapArea map = controller.createMap(x, y);
        tabMapController.createTab(map);
        tabMapController.setCurrentTab(map);
        this.currentlySelectedMap = map;

        updatesGlobalInfosPane(map);
        return map;
    }

    @Override
    public MapArea addMapPane(final List<String> file) {
        MapArea map = null;
        try {
            map = controller.executeFileCommands(file);
            this.currentlySelectedMap = map;
            tabMapController.createTab(map);
            tabMapController.setCurrentTab(map);
            updatesGlobalInfosPane(map);
        } catch (MowerInstructionFileException | ImpossibleMowerCommandException e) {
            setGlobalText(e.getMessage());
            LOGGER.error(e);
        }
        return map;
    }

    /**
     * Reload the different pane to keep the user interface up to date.
     *
     * @param mapArea the map on which the update is related
     */
    private void updatesGlobalInfosPane(final MapArea mapArea) {
        historicController.updateHistoric(mapArea);
        mowersListController.updateMowerList(mapArea);
        tabMapController.redrawMatrix(currentlySelectedMap);
    }

    @Override
    public void executeCommands(final MowerCommands command) {
        if (currentlySelectedMower == null) {
            setGlobalText(languageManager.getString("label-error-no-mower-selected"));
        } else {
            try {
                controller.executesCommands(currentlySelectedMower, command);
                updatesGlobalInfosPane(currentlySelectedMap);
            } catch (UnknownMowerCommandException e) {
                setGlobalText(e.getMessage());
                LOGGER.error(e);
            }
        }
    }

    @Override
    public Mower getCurrentlySelectedMower() {
        return currentlySelectedMower;
    }

    @Override
    public ProgramState getProgramState() {
        return programState;
    }

    @Override
    public void setProgramState(final ProgramState state) {
        this.programState = state;
    }

    @Override
    public void executeProgramState(final int abscissa, final int ordinate) {
        if (getProgramState() == ProgramState.ADD_MOWER && controller.fetchMowerFromCoordinates(currentlySelectedMap, abscissa, ordinate) == null) {
            controller.addMower(currentlySelectedMap, abscissa, ordinate);
            programState = null;
        } else if (getProgramState() == ProgramState.REMOVE_MOVER && controller.fetchMowerFromCoordinates(currentlySelectedMap, abscissa, ordinate) != null) {
            controller.removeMower(currentlySelectedMap, abscissa, ordinate);
        }
        currentlySelectedMower = controller.fetchMowerFromCoordinates(currentlySelectedMap, abscissa, ordinate);
        updatesGlobalInfosPane(currentlySelectedMap);
    }

    @Override
    public void closeApp() {
        MainApp.getInstance().closeApp();
    }

    @Override
    public void reloadApp() {
        MainApp.getInstance().reload();
        if (controller.getMapMowersList().size() > 0) {
            tabMapController.reloadMatrixes();
            updatesGlobalInfosPane(currentlySelectedMap);
        }
    }

    @Override
    public MapArea getCurrentMap() {
        return currentlySelectedMap;
    }

    @Override
    public void setCurrentMap(final MapArea mapArea) {
        this.currentlySelectedMap = mapArea;
        this.currentlySelectedMower = null;
        updatesGlobalInfosPane(mapArea);
    }

    @Override
    public void resetApp() {
        controller.reset();
        this.currentlySelectedMap = null;
        this.currentlySelectedMower = null;
        try {
            Parent root = SceneUtil.loadParent(currentStage, "scenes/main-scene.fxml", languageManager.getCurrentLocale());
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            setGlobalText(languageManager.getString("label-error-cannot-reload-map"));
            LOGGER.error(e);
        }
    }

    public MainController getController() {
        return controller;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public ToolbarController getToolbarController() {
        return toolbarController;
    }

    public TabMapController getTabMapController() {
        return tabMapController;
    }

    public HistoricPaneController getHistoricController() {
        return historicController;
    }

    public RemoteControlController getRemoteControlController() {
        return remoteControlController;
    }

    public MowerListController getMowersListController() {
        return mowersListController;
    }

    public MapAction getActionPaneController() {
        return actionPaneController;
    }


    @Override
    public void setStageAndSetupListeners(final Stage stage) {
        this.currentStage = stage;
    }

}
