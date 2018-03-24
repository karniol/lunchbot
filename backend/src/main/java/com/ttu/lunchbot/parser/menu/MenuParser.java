package com.ttu.lunchbot.parser.menu;

import com.ttu.lunchbot.model.Menu;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;


public abstract class MenuParser {

    /**
     * Time zone code identifier for Tallinn as listed in the Java TimeZone specification.
     * @see TimeZone
     */
    static final String TIME_ZONE_ID_TALLINN = "Europe/Tallinn";

    /**
     * Locale language tag for Estonian as listed in the Java Locale specification.
     * @see Locale
     */
    static final String LANGUAGE_TAG_ET = "et";

    /**
     * Currency tag for Euros as listed in the Java Currency specification.
     * @see Currency
     */
    static final String CURRENCY_TAG_EUR = "EUR";

    /**
     * Strategy for parsing menu texts into MenuItems and Menus.
     */
    @Getter
    final MenuParserStrategy strategy;

    MenuParser(MenuParserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @param file File object representing a menu document.
     * @return Collection of parsed Menus.
     * @see Menu
     */
    public abstract ArrayList<Menu> parseMenus(File file);

}
