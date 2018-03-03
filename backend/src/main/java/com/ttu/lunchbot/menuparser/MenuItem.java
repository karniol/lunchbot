package com.ttu.lunchbot.menuparser;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Data object for storing food and drink descriptions along with prices in various languages and formats.
 */
public class MenuItem {

    /**
     * Names of the MenuItems corresponding to different locales.
     */
    private final HashMap<Locale, String> names;

    /**
     * Prices of the MenuItems corresponding to different currencies.
     */
    private final HashMap<Currency, BigDecimal> prices;

    /**
     * Create a new empty MenuItem.
     */
    MenuItem() {
        this.names = new HashMap<>();
        this.prices = new HashMap<>();
    }

    /**
     * Add or update the name of the MenuItem for the specified Locale.
     * @param locale Locale associated with the name.
     * @param name Name of the MenuItem.
     */
    void addName(Locale locale, String name) {
        this.names.put(locale, name);
    }

    /**
     * Add or update the price of the MenuItem for the specified Currency.
     * @param currency Currency associated with the price.
     * @param price Price of the MenuItem.
     */
    void addPrice(Currency currency, BigDecimal price) {
        this.prices.put(currency, price);
    }

    /**
     * @return Collection of all added names for the MenuItem.
     */
    public HashMap<Locale, String> getNames() {
        return this.names;
    }

    /**
     * @param locale Locale for which the name is requested.
     * @return Name of the MenuItem corresponding to the given Locale.
     */
    public String getName(Locale locale) {
        return this.names.get(locale);
    }

    /**
     * @return Collection of all added prices for the MenuItem.
     */
    HashMap<Currency, BigDecimal> getPrices() {
        return this.prices;
    }

    /**
     * @param currency Currency for which the price is requested.
     * @return Price of the MenuItem corresponding to the given Currency.
     */
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
