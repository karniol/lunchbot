package com.ttu.lunchbot.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class CalendarConverter {

    /**
     * Copied from https://kodejava.org/how-do-i-convert-between-old-date-and-calendar-object-with-the-new-java-8-date-time/
     * @param calendarTime
     * @return
     */
    public LocalDate calendarToLocalDate(Calendar calendarTime) {
        return LocalDateTime.ofInstant(calendarTime.toInstant(),
                ZoneId.systemDefault()).toLocalDate();
    }
}
