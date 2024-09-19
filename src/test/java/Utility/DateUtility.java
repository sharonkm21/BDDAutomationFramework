package Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateUtility
{
    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(formatter);
    }

    public static String addDaysToCurrentDate(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = now.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return newDate.format(formatter);
    }

    public static String subtractDaysFromCurrentDate(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = now.minusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return newDate.format(formatter);
    }

    public static String getCurrentTimestamp(String format) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return now.format(formatter);
        } catch (DateTimeParseException e) {
            return "Invalid date format: " + format;
        }
    }

    public static String addHours(String timestamp, int hours, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.plusHours(hours);
        return formatTimestamp(updatedTime, format);
    }

    public static String addMinutes(String timestamp, int minutes, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.plusMinutes(minutes);
        return formatTimestamp(updatedTime, format);
    }

    public static String addSeconds(String timestamp, int seconds, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.plusSeconds(seconds);
        return formatTimestamp(updatedTime, format);
    }

    public static String addMonths(String timestamp, int months, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.plusMonths(months);
        return formatTimestamp(updatedTime, format);
    }

    public static String addYears(String timestamp, int years, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.plusYears(years);
        return formatTimestamp(updatedTime, format);
    }

    public static LocalDateTime parseTimestamp(String timestamp, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(timestamp, formatter);
    }

    public static String formatTimestamp(LocalDateTime dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static String subtractHours(String timestamp, int hours, String format) {
            LocalDateTime time = parseTimestamp(timestamp, format);
            LocalDateTime updatedTime = time.minusHours(hours);
            return formatTimestamp(updatedTime, format);
    }

    public static String subtractMinutes(String timestamp, int minutes, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.minusMinutes(minutes);
        return formatTimestamp(updatedTime, format);
    }

    public static String subtractSeconds(String timestamp, int seconds, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.minusSeconds(seconds);
        return formatTimestamp(updatedTime, format);
    }

    public static String subtractMonths(String timestamp, int months, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.minusMonths(months);
        return formatTimestamp(updatedTime, format);
    }

    public static String subtractYears(String timestamp, int years, String format) {
        LocalDateTime time = parseTimestamp(timestamp, format);
        LocalDateTime updatedTime = time.minusYears(years);
        return formatTimestamp(updatedTime, format);
    }

    public static long getTimeDifference(String date1, String date2, String format, String unit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);

        switch (unit.toLowerCase()) {
            case "days":
                return ChronoUnit.DAYS.between(dateTime1, dateTime2);
            case "hours":
                return ChronoUnit.HOURS.between(dateTime1, dateTime2);
            case "minutes":
                return ChronoUnit.MINUTES.between(dateTime1, dateTime2);
            case "seconds":
                return ChronoUnit.SECONDS.between(dateTime1, dateTime2);
            default:
                throw new IllegalArgumentException("Invalid time unit. Use 'days', 'hours', 'minutes', or 'seconds'.");
        }
    }
}
