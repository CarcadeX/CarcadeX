package me.redtea.carcadex.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Formatter;

/**
 * @since 1.0.0
 * @author itzRedTea
 */
public class NumUtils {
    /**
     *
     * @param durationTicks time in ticks that will be parsed (one second = 20 ticks)
     * @return parsed string in format HOURS:MINUTES:SECONDS
     */
    public static String formatDurationTime(long durationTicks) {
        long hours = 0L;
        long minutes = 0L;
        long seconds = durationTicks / 20;

        if (seconds >= 3600) {
            hours = seconds / 3600;
            seconds -= hours * 3600;
        }
        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds -= minutes * 60;
        }
        return new Formatter().format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds).toString();
    }

    /**
     * <h1>Parse string to time in ticks</h1>
     *
     * <h2>Allowed formats</h2>
     * <ul>
     *   <li><b><i>none</i></b> - ticks (one second = 20 ticks)</li>
     *   <li><b>s</b> - seconds</li>
     *   <li><b>m</b> - minutes</li>
     *   <li><b>h</b> - hours</li>
     *   <li><b>d</b> - days</li>
     * </ul>
     *
     * <p>For example: "20 1s 2h"</p>
     *
     * @param timeString string to parse
     * @return parsed time in ticks
     */
    public static long ticksFromString(@NotNull String timeString) {
        return timeFromString(timeString, 20);
    }

    /**
     * <h1>Parse string to time in millis</h1>
     *
     * <h2>Allowed formats</h2>
     * <ul>
     *   <li><b><i>none</i></b> - millis (= 1000 seconds)</li>
     *   <li><b>s</b> - seconds</li>
     *   <li><b>m</b> - minutes</li>
     *   <li><b>h</b> - hours</li>
     *   <li><b>d</b> - days</li>
     * </ul>
     *
     * <p>For example: "20 1s 2h"</p>
     *
     * @param timeString string to parse
     * @return parsed time in millis
     */
    public static long millisFromString(@NotNull String timeString) {
        return timeFromString(timeString, 1000);
    }

    /**
     * <h1>Parse string to time in sec</h1>
     *
     * @param timeString string to parse
     * @return parsed time in sec
     * @see NumUtils#millisFromString
     */
    public static long secondsFromString(@NotNull String timeString) {
        return millisFromString(timeString)/1000;
    }

    /**
     * <h1>Parse string to time in minutes</h1>
     *
     * @param timeString string to parse
     * @return parsed time in mins
     * @see NumUtils#millisFromString
     */
    public static long minutesFromString(@NotNull String timeString) {
        return millisFromString(timeString)/1000/60;
    }

    /**
     * <h1>Parse string to time in minutes</h1>
     *
     * @param timeString string to parse
     * @return parsed time in hours
     * @see NumUtils#millisFromString
     */
    public static long hoursFromString(@NotNull String timeString) {
        return millisFromString(timeString)/1000/60/60;
    }

    /**
     * <h1>Parse string to time in minutes</h1>
     *
     * @param timeString string to parse
     * @return parsed time in days
     * @see NumUtils#millisFromString
     */
    public static long daysFromString(@NotNull String timeString) {
        return millisFromString(timeString)/1000/60/60/24;
    }

    public static long timeFromString(@NotNull String timeString, int k) {
        long result = 0L;
        for(String sub : timeString.split(" ")) {
            if(sub.matches("[0-9]+")) {
                result += Long.parseLong(sub);
            } else if(sub.contains("s")) {
                result += Long.parseLong(sub.replace("s", "")) * k;
            } else if (sub.contains("m")) {
                result += Long.parseLong(sub.replace("m", "")) * k * 60;
            } else if (sub.contains("h")) {
                result += Long.parseLong(sub.replace("h", "")) * k * 60 * 60;
            } else if (sub.contains("d")) {
                result += Long.parseLong(sub.replace("d", "")) * k * 60 * 60 * 24;
            }
        }
        return result;
    }
}
