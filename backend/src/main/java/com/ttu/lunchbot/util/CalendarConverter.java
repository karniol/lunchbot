package com.ttu.lunchbot.util;

import com.ttu.lunchbot.spring.model.Menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Convert Calendar to LocalDate.
 */
public class CalendarConverter {

    /**
     * @param calendar A Calendar object with the time we wish to convert
     * @return A LocalDate object with the same time as the Calendar object
     * @see <a href="https://kodejava.org/how-do-i-convert-between-old-date-and-calendar-object-with-the-new-java-8-date-time/">
     *     How do I convert between old Date and Calendar object with the new Java 8 Date Time?</a>
     */
    public LocalDate toLocalDate(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
    }

    /**
     * Return the number of days between the given menu date and the current date
     * @param menu The menu of a food service
     * @return Number of days between the given menu date and the current date
     */
    public long daysBetweenNowAndDateOfMenu(Menu menu) {
        return Math.abs(ChronoUnit.DAYS.between(menu.getDate(), LocalDate.now()));
    }
}
