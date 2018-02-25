package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

/**
 * todo
 */
public class MenuItem {

    /**
     * Names of the menu items corresponding to different locales.
     */
    private final HashMap<Locale, String> names;

    /**
     * Prices of the menu items corresponding to different currencies.
     */
    private final HashMap<Currency, BigDecimal> prices;

    MenuItem() {
        this.names = new HashMap<>();
        this.prices = new HashMap<>();
    }

    void addName(Locale locale, String name) {
        this.names.put(locale, name);
    }

    void addPrice(Currency currency, BigDecimal price) {
        this.prices.put(currency, price);
    }

    String getName(Locale locale) {
        return this.names.get(locale);
    }

    BigDecimal getPrice(Currency currency) {
        return this.prices.get(currency);
    }
}
