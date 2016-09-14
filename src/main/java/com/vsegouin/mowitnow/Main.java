package com.vsegouin.mowitnow;

import com.google.inject.Injector;
import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import com.vsegouin.mowitnow.ui.cli.MainCLI;
import com.vsegouin.mowitnow.ui.gui.MainApp;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Scanner;

/**
 * The main application class.
 * Created by v.segouin on 07/09/2016.
 */

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private LanguageManagerInterface languageManager;
    private Scanner scanner;

    /**
     * A basic constructor where the language manager is injected thanks to Google Guice.
     *
     * @param languageManager the language manager used for internationalization.
     */
    @Inject
    public Main(final LanguageManagerInterface languageManager) {
        this.languageManager = languageManager;
    }

    /**
     * The main entry point of the program, will ask what kind of interface the user wants to use, and will call the
     * right entry point.
     *
     * @param args the generics arguments.
     */
    public static void main(final String[] args) {
        Injector injector = InjectorUtil.getInjector();
        Main app = injector.getInstance(Main.class);
        app.startProgram();
    }

    /**
     * Start the program.
     */
    private void startProgram() {

        scanner = new Scanner(System.in, "UTF-8");
        String scanResult = "-1";
        while (!("1".equals(scanResult) || "2".equals(scanResult) || "4".equals(scanResult) || "3".equals(scanResult))) {
            LOGGER.info(languageManager.getString("choose-an-ui"));
            scanResult = scanner.nextLine();
        }
        executeCommand(scanResult);
    }

    /**
     * Execute the command asked by the user.
     *
     * @param scanResult the command asked by the user
     */
    private void executeCommand(final String scanResult) {
        switch (scanResult) {
            case "1":
                LOGGER.info(languageManager.getString("loading-gui"));
                MainApp.main();
                break;
            case "2":
                MainCLI.main();
                break;
            case "3":
                changeLanguage();
                startProgram();
                break;
            default:
                LOGGER.info(languageManager.getString("program-is-interrupted"));
                break;
        }
    }

    /**
     * Change the program language with the locale asked by the user.
     */
    private void changeLanguage() {
        LOGGER.info(languageManager.getString("choose-language"));
        String language = scanner.nextLine();
        while (!("1".equals(language) || "2".equals(language)) || "3".equals(language)) {
            LOGGER.info(languageManager.getString("choose-language"));
            language = scanner.nextLine();
        }
        if ("1".equals(language)) {
            languageManager.loadLocale("fr", "fr");
        } else if ("2".equals(language)) {
            languageManager.loadLocale("en", "us");
        }
    }
}
