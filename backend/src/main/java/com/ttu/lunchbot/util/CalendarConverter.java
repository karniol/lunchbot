package com.ttu.lunchbot.util;

import com.ttu.lunchbot.spring.model.Menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class CalendarConverter {

    /**
     * Copied from https://kodejava.org/how-do-i-convert-between-old-date-and-calendar-object-with-the-new-java-8-date-time/
     * @param calendarTime  A Calendar object with the time we wish to convert
     * @return              A LocalDate object with the same time as the Calendar object
     */
    public LocalDate calendarToLocalDate(Calendar calendarTime) {
        return LocalDateTime.ofInstant(calendarTime.toInstant(),
                ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param menu  The menu of a food service
     * @return      The amount of days between the menu date and the current date
     */
    public long getAbsDaysFromNow(Menu menu) {
        return Math.abs(ChronoUnit.DAYS.between(menu.getDate(), LocalDate.now()));
    }

}
