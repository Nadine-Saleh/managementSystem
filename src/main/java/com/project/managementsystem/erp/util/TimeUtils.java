package com.project.managementsystem.erp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatCurrentDateTime() {
        return LocalDateTime.now().format(formatter);
    }

    public static String formatTimestamp(String unixTimestampStr) {
        try {
            long unixSeconds = Long.parseLong(unixTimestampStr);
            return LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochSecond(unixSeconds),
                    java.time.ZoneId.systemDefault()
            ).format(formatter);
        } catch (NumberFormatException ex) {
            return "Invalid Date";
        }
    }
}