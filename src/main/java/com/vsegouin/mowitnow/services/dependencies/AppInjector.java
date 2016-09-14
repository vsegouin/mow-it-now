package com.vsegouin.mowitnow.services.dependencies;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.vsegouin.mowitnow.controllers.MainController;
import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.services.file.FileControl;
import com.vsegouin.mowitnow.services.file.interfaces.FileControlInterface;
import com.vsegouin.mowitnow.services.language.LanguageManager;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.gui.MainApp;
import com.vsegouin.mowitnow.ui.gui.controllers.MainControllerGUI;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GUIController;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.GraphicalApplication;

/**
 * The dependency injector created with Google GUICE.
 * The serivce's interface and their concrete should be bind in the configure method.
 */
public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        bind(LanguageManagerInterface.class).to(LanguageManager.class);
        bind(FileControlInterface.class).to(FileControl.class);
        bind(MowerController.class).to(MainController.class);
        bind(GUIController.class).to(MainControllerGUI.class).in(Singleton.class);
        bind(GraphicalApplication.class).to(MainApp.class).in(Singleton.class);
    }

}
