package com.vsegouin.mowitnow.ui.gui.controllers.infos;

import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by v.segouin on 12/09/2016.
 */
public class MowerListController {
    private final MowerController controller;

    @FXML
    private ListView<String> mowersList;
    private LanguageManagerInterface languageManager;

    /**
     * Basic constructor.
     *
     * @param controller      the mowers controller containing mowers data
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public MowerListController(final MowerController controller, final LanguageManagerInterface languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    /**
     * Update the list of mower related to the map.
     *
     * @param currentMap the map containing the mowers
     */
    public void updateMowerList(final MapArea currentMap) {
        Map<Mower, String> historic = controller.getMapMowersList().get(currentMap);
        ArrayList<String> historicList = new ArrayList<>();
        for (Map.Entry<Mower, String> historicMap : historic.entrySet()) {
            String mowerHistoric = historicMap.getKey().getDetector().getCurrentStateString();
            String mowerIndex = Integer.toString(controller.getMowerIndex(currentMap, historicMap.getKey()));
            historicList.add(languageManager.getString("mower-historic", mowerIndex, mowerHistoric));
        }
        ObservableList<String> items = FXCollections.observableArrayList(historicList);
        mowersList.setItems(items);

    }
}
