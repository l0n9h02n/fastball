package com.github.longhorn.fastball.time;

import com.github.longhorn.fastball.time.enums.Seconds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Clock {
    private static final int UNIX_TS_WITHOUT_MILLIS_LENGTH = 10;
    private static final String ISO_8601_FORMAT_WITH_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private long unixTs;
    private TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");

    /**
     * Private constructor, initial the instance with unix timestamp
     *
     * @param unixTs unix timestamp
     */
    private Clock(long unixTs) {
        this.unixTs = unixTs;
    }

    /**
     * Create an instance for current time.
     *
     * @return Clock instance of current time.
     */
    public static Clock fromNow() {
        return new Clock(System.currentTimeMillis());
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time.
     */
    public static Clock fromUnixTs(int unixTs) {
        return Clock.fromUnixTs(String.valueOf(unixTs));
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time.
     */
    public static Clock fromUnixTs(long unixTs) {
        return Clock.fromUnixTs(String.valueOf(unixTs));
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time.
     */
    public static Clock fromUnixTs(String unixTs) {
        if (unixTs.length() <= UNIX_TS_WITHOUT_MILLIS_LENGTH) {
            long unixTsWithMillis = (long) (Long.valueOf(unixTs) / Seconds.Companion.getMILLISECOND());
            unixTs = String.valueOf(unixTsWithMillis);
        }
        return new Clock(Long.parseLong(unixTs));
    }

    /**
     * Get the ISO-8601 format time with millisecond of the instance.
     *
     * @return String of the ISO-8601 format time of the instance
     */
    public String getIso8601Millis() {
        return getIso8601Millis(defaultTimeZone);
    }

    /**
     * Get the ISO-8601 format time with millisecond of the instance.
     *
     * @param timeZone TimeZone
     * @return String of the ISO-8601 format time of the instance
     */
    public String getIso8601Millis(TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_FORMAT_WITH_MILLISECOND);
        sdf.setTimeZone(timeZone);
        sdf.setLenient(false);
        return sdf.format(new Date(unixTs));
    }

    /**
     * Get the ISO-8601 format time of the instance.
     *
     * @return String of the ISO-8601 format time of the instance
     */
    public String getIso8601() {
        return getIso8601(defaultTimeZone);
    }

    /**
     * Get the ISO-8601 format time of the instance.
     *
     * @param timeZone TimeZone
     * @return String of the ISO-8601 format time of the instance
     */
    public String getIso8601(TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_FORMAT);
        sdf.setTimeZone(timeZone);
        sdf.setLenient(false);
        return sdf.format(new Date(unixTs));
    }
}
