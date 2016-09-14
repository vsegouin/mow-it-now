package com.vsegouin.mowitnow.ui.gui.controllers.infos;

import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.core.Mower;
import com.vsegouin.mowitnow.mower.core.map.MapArea;
import com.vsegouin.mowitnow.mower.enums.MowerCommands;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by v.segouin on 13/09/2016.
 */
public class HistoricPaneController {
    private MowerController controller;

    @FXML
    private ListView<String> list;
    private LanguageManagerInterface languageManager;

    /**
     * Basic constructor with guice injection.
     *
     * @param controller      the mower's main controller
     * @param languageManager language manager used for internationalization
     */
    @Inject
    public HistoricPaneController(final MowerController controller, final LanguageManagerInterface languageManager) {
        this.controller = controller;
        this.languageManager = languageManager;
    }

    /**
     * Get the different action done in the corresponding map and write them.
     *
     * @param map the map corresponding to the wanted historic.
     */
    public void updateHistoric(final MapArea map) {
        List<Map.Entry<Mower, MowerCommands>> historic = controller.getProgramHistoric();
        ArrayList<String> historicList = new ArrayList<>();
        for (Map.Entry<Mower, MowerCommands> historicMap : historic) {
            if (controller.getMowerIndex(map, historicMap.getKey()) != -1) {
                String mowerIndex = Integer.toString(controller.getMowerIndex(map, historicMap.getKey()));
                String commandName = languageManager.getString(historicMap.getValue().getCommandName().toLowerCase());
                String str = languageManager.getString("historic-mower-command", mowerIndex, commandName);
                historicList.add(str);
            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(historicList);
        list.setItems(items);

    }

}
