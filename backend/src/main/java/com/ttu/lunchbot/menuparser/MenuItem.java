package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

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

    HashMap<Locale, String> getNames() {
        return this.names;
    }

    String getName(Locale locale) {
        return this.names.get(locale);
    }

    HashMap<Currency, BigDecimal> getPrices() {
        return this.prices;
    }

    BigDecimal getPrice(Currency currency) {
        return this.prices.get(currency);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ");
        Locale[] locales = this.getNames().keySet().toArray(new Locale[]{});
        for (int i = 0; i < this.getNames().size(); ++i) {
            sb.append(this.getName(locales[i]));
            sb.append("");

            if (this.getNames().size() > 1 && i < this.getNames().size() - 1) {
                sb.append(" | ");
            }
        }

        sb.append("\nPrice: ");
        Currency[] currencies = this.getPrices().keySet().toArray(new Currency[]{});
        for (int j = 0; j < this.getPrices().size(); ++j) {
            sb.append(this.getPrice(currencies[j]));
            sb.append(" ");
            sb.append(currencies[j].getDisplayName());

            if (this.getPrices().size() > 1 && j < this.getPrices().size() - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }
}
