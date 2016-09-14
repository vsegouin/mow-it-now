package com.vsegouin.mowitnow.services.language;

import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;

import javax.inject.Singleton;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The implementation of Language Manager Interface.
 * Created by segouin.v on 12/09/2016.
 */
@Singleton
public class LanguageManager implements LanguageManagerInterface {
    private Locale locale;
    private ResourceBundle resourceBundle;

    /**
     * A basic constructor which will automatically load english and MessageBundle File.
     */
    public LanguageManager() {
        locale = loadLocale("en", "us");
        resourceBundle = ResourceBundle.getBundle("language.MessagesBundle", locale);
    }

    @Override
    public Locale loadLocale(final String region, final String country) {
        locale = new Locale(region, country);
        resourceBundle = ResourceBundle.getBundle("language.MessagesBundle", locale);
        return locale;
    }

    @Override
    public String getString(final String label, final String... params) {
        return MessageFormat.format(getString(label), params);
    }

    @Override
    public Locale getCurrentLocale() {
        return locale;
    }

    @Override
    public String getString(final String label) {
        return resourceBundle.getString(label);
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}
