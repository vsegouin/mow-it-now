package com.vsegouin.mowitnow.ui.gui.controllers.infos;

import com.google.inject.Inject;
import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.components.visualization.MatrixVisualizerPane;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class TabMapController {
    private final GUIController guiController;
    private final MowerController controller;
    private final LanguageManagerInterface languageManager;
    @FXML
    private TabPane tabMap;

    private List<MatrixVisualizerPane> matrixList;

    /**
     * Basic constructor.
     *
     * @param guiController   the main GUI controller which orchestrate the GUI's component
     * @param controller      the mowers controller to executes the different commands
     * @param languageManager the language manager for internationalization
     */
    @Inject
    public TabMapController(final GUIController guiController, final MowerController controller, final LanguageManagerInterface languageManager) {
        this.guiController = guiController;
        this.controller = controller;
        this.languageManager = languageManager;
        this.matrixList = new ArrayList<>();
    }

    /**
     * Create a new tab with a visualization matrix which correspond to the provided map.
     *
     * @param map the map on which the matrix visualizer will be based on
     */
    public void createTab(final MapArea map) {
        MatrixVisualizerPane mvp = new MatrixVisualizerPane(guiController, controller, map);

        Pane pane = new Pane();
        pane.getStyleClass().add("matrix-pane");
        pane.getChildren().addAll(mvp.getCanvas(), mvp.getOverlay());

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.getStyleClass().add("matrix-pane");

        Tab tab = new Tab(languageManager.getString("label-map") + " " + tabMap.getTabs().size());
        tab.setContent(scrollPane);
        tabMap.getTabs().add(tab);
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(final Event event) {
                guiController.setCurrentMap(controller.fetchMapFromIndex(((Tab) event.getTarget()).getTabPane().getSelectionModel().getSelectedIndex()));
            }
        });

        matrixList.add(mvp);
        redrawMatrix(mvp);
    }

    /**
     * Redraw the matrix's canvas.
     *
     * @param mvp the visualizer to redraw
     */
    public void redrawMatrix(final MatrixVisualizerPane mvp) {
        mvp.drawMap();
    }

    /**
     * Redraw the matrix which is related to the given map.
     *
     * @param map the map related to an existing visualizer to redraw.
     */
    public void redrawMatrix(final MapArea map) {
        matrixList.get(controller.getMapIndex(map)).drawMap();
    }

    /**
     * Redraw all matrix.
     */
    public void redrawMatrixes() {
        for (MatrixVisualizerPane mvp : matrixList) {
            mvp.drawMap();
        }
    }

    /**
     * Fetch all the map and mowers from the main controller and recreate all the tab and matrix.
     * Will select automatically the tab containing the last selected map.
     */
    public void reloadMatrixes() {
        int savedTab = 0;
        int index = 0;
        for (Map.Entry<MapArea, Map<Mower, String>> map : controller.getMapMowersList().entrySet()) {
            createTab(map.getKey());
            if (guiController.getCurrentMap().equals(map.getKey())) {
                savedTab = index;
            }
            index++;
        }
        redrawMatrixes();
        tabMap.getSelectionModel().select(savedTab);
    }

    /**
     * Set the focus on the corresponding tab.
     *
     * @param map the selected map presents in a map
     */
    public void setCurrentTab(final MapArea map) {
        tabMap.getSelectionModel().select(controller.getMapIndex(map));
    }
}
