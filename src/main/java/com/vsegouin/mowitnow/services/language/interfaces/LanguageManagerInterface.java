package com.vsegouin.mowitnow.services.language.interfaces;

import java.util.Locale;

/**
 * The language manager is intended to be injected in the classes which send String that should be localized.
 * It is used to centralized all the operation about internationalization and make the localization simpler.
 * Created by segouin.v on 12/09/2016.
 */
public interface LanguageManagerInterface {
    /**
     * Load the locale corresponding to the region and the country and store it in the object.
     *
     * @param region  the locale's region
     * @param country the locale's country
     * @return return the created locale
     */
    Locale loadLocale(String region, String country);

    /**
     * Get the localized string corresponding to the label.
     *
     * @param label the label to find
     * @return the localized string
     */
    String getString(String label);

    /**
     * Get the localized string corresponding to the label and format it with the parameters.
     *
     * @param label  the label to find
     * @param params the parameters used to format the localized string
     * @return the localized string
     */
    String getString(String label, String... params);

    /**
     * Get the current locale, loaded with loadLocale.
     *
     * @return the current locale.
     */
    Locale getCurrentLocale();
}
