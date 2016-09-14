package com.vsegouin.mowitnow.services.dependencies.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vsegouin.mowitnow.services.dependencies.AppInjector;

/**
 * Created by segouin.v on 13/09/2016.
 */
public class InjectorUtil {
    private static Injector injector = Guice.createInjector(new AppInjector());

    /**
     * A private injector to avoid any instantiation.
     */
    private InjectorUtil() {
    }

    public static Injector getInjector() {
        return injector;
    }
}
