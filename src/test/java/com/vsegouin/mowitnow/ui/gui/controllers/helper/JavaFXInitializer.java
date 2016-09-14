package com.vsegouin.mowitnow.ui.gui.controllers.helper;

import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.LanguageManager;
import com.vsegouin.mowitnow.ui.gui.controllers.interfaces.FxmlController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class JavaFXInitializer extends Application {
    public static ThreadLocal controller;
    public static ThreadLocal instance;

    private static Object barrier = new Object();
    private static Thread t;

    public static Object initialize() throws InterruptedException {
        if (instance != null) {
            return instance.get();
        }
        t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(JavaFXInitializer.class);
            }
        };
        t.setDaemon(true);
        t.start();
        synchronized (barrier) {
            barrier.wait();
        }
        return instance;
    }

    public static void destroy() {
        try {
            ((JavaFXInitializer) instance.get()).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getController() {
        return controller.get();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("scenes/main-scene.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("language.GUIBundle", InjectorUtil.getInjector().getInstance(LanguageManager.class).getLocale());
        FXMLLoader loader = new FXMLLoader(url, bundle);
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(final Class<?> type) {
                return InjectorUtil.getInjector().getInstance(type);
            }
        });
        loader.load();
        controller = new ThreadLocal() {
            protected Object initialValue() {
                return loader.getController();
            }
        };

        if (controller.get() instanceof FxmlController) {
            ((FxmlController) controller.get()).setStageAndSetupListeners(primaryStage);
        }
        instance = new ThreadLocal() {
            protected Object initialValue() {
                return JavaFXInitializer.this;
            }
        };
        synchronized (barrier) {
            barrier.notify();
        }
    }


}