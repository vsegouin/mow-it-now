package com.vsegouin.mowitnow.ui.cli;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vsegouin.mowitnow.controllers.interfaces.MowerController;
import com.vsegouin.mowitnow.mower.exceptions.ImpossibleMowerCommandException;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.file.FileControl;
import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The entry point of the CLI version of the program.
 * Created by v.segouin on 08/09/2016.
 */
public class MainCLI {
    private static final Logger LOGGER = Logger.getLogger(MainCLI.class);
    private final FileControl fileControl;
    private MowerController controller;
    private LanguageManagerInterface languageManager;

    /**
     * MainCLI base constructor, loaded with Google GUICE.
     *
     * @param controller      the main controller which controls mowers
     * @param languageManager the language manager for internationalization
     * @param fileControl     the file control manager.
     */
    @Inject
    public MainCLI(final MowerController controller, final LanguageManagerInterface languageManager, final FileControl fileControl) {
        this.languageManager = languageManager;
        this.fileControl = fileControl;
        this.controller = controller;
    }

    /**
     * the main entry point which will load the program.
     */
    public static void main(final String[] args) {
        Injector injector = InjectorUtil.getInjector();
        MainCLI app = injector.getInstance(MainCLI.class);
        app.startProgram();
    }

    /**
     * the main method of the app, will tell to provide a file containing instructions and will executes them.
     */
    private void startProgram() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String filePath = "-";
        while (!new File(filePath).exists()) {
            LOGGER.info(languageManager.getString("please-provide-file-path"));
            filePath = scanner.nextLine();
        }
        try {
            List<String> fileContent = fileControl.readFile(filePath);
            controller.executeFileCommands(fileContent);
        } catch (ImpossibleMowerCommandException | IOException | MowerInstructionFileException e) {
            LOGGER.error(e);
        }
    }
}
