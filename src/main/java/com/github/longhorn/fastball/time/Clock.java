package com.github.longhorn.fastball.time;

import com.github.longhorn.fastball.time.enums.Seconds;
import com.google.errorprone.annotations.Var;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Clock {
    private static final int UNIX_TS_WITHOUT_MILLIS_LENGTH = 10;
    private static final String ISO_8601_FORMAT_WITH_MILLISECOND = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String SQL_TS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String BEGIN_OF_DAY_DEFINE = "T00:00:00";
    private static final String END_OF_DAY_DEFINE = "T23:59:59";
    private long unixTsMilli;
    private long unixTs;
    private TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");

    /**
     * Private constructor, initial the instance with unix timestamp.
     *
     * @param unixTsMilli unix timestamp with millisecond
     */
    private Clock(long unixTsMilli) {
        this.unixTsMilli = unixTsMilli;
        unixTs = (long) (unixTsMilli * Seconds.getMILLISECOND());
    }

    /**
     * Create an instance for current time.
     *
     * @return Clock instance of current time
     */
    public static Clock fromNow() {
        return new Clock(System.currentTimeMillis());
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time
     */
    public static Clock fromUnixTs(int unixTs) {
        return Clock.fromUnixTs(String.valueOf(unixTs));
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time
     */
    public static Clock fromUnixTs(long unixTs) {
        return Clock.fromUnixTs(String.valueOf(unixTs));
    }

    /**
     * Create an instance by giving unix timestamp.
     *
     * @param unixTs unix timestamp
     * @return Clock instance of given time
     */
    public static Clock fromUnixTs(@Var String unixTs) {
        if (unixTs.length() <= UNIX_TS_WITHOUT_MILLIS_LENGTH) {
            long unixTsWithMillis = (long) (Long.valueOf(unixTs) / Seconds.getMILLISECOND());
            unixTs = String.valueOf(unixTsWithMillis);
        }
        return new Clock(Long.parseLong(unixTs));
    }

    /**
     * Create an instance by giving ISO-8601 string.
     *
     * @param iso8601 ISO-8601 time
     * @return Clock instance of given time
     * @throws ParseException Parse Exception will be thrown if the input is invalid
     */
    public static Clock fromIso8601(String iso8601) throws ParseException {
        return new Clock(
                ZonedDateTime.parse(iso8601, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant().toEpochMilli()
        );
    }

    /**
     * Get epoch second.
     *
     * @return long of the epoch second
     */
    public long getEpochSecond() {
        return unixTs;
    }

    /**
     * Get epoch second string.
     *
     * @return String of the unix timestamp
     */
    public String getEpochSecondString() {
        return String.valueOf(getEpochSecond());
    }

    /**
     * Get epoch millisecond.
     *
     * @return long of the unix timestamp
     */
    public long toEpochMilli() {
        return unixTsMilli;
    }

    /**
     * Get unix timestamp with millisecond of the Clock
     *
     * @return String of the unix timestamp
     */
    public String toEpochMilliString() {
        return String.valueOf(unixTsMilli);
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
        return sdf.format(new Date(unixTsMilli));
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
        return sdf.format(new Date(unixTsMilli));
    }

    /**
     * Get SQL timestamp format string.
     *
     * @return String of SQL timestamp of the instance.
     */
    public String getSqlTs() {
        return getSqlTs(defaultTimeZone);
    }

    /**
     * Get SQL timestamp format string.
     *
     * @param timeZone TimeZone
     * @return String of SQL timestamp of the instance.
     */
    public String getSqlTs(TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(SQL_TS_FORMAT);
        sdf.setTimeZone(timeZone);
        sdf.setLenient(false);
        return sdf.format(new Date(unixTsMilli));
    }

    /**
     * Check is 2 instances at the same moment.
     *
     * @param clock Clock
     * @return Boolean of the result
     */
    public boolean isEqualTo(Clock clock) {
        return toEpochMilli() == clock.toEpochMilli();
    }

    /**
     * Check is the instance at the end of the day.
     *
     * @return Boolean of the result
     */
    public boolean isAtBeginOfDay() {
        return getIso8601().contains(BEGIN_OF_DAY_DEFINE);
    }

    /**
     * Check is the instance at the end of the day.
     *
     * @param timeZone TimeZone
     * @return Boolean of the result
     */
    public boolean isAtBeginOfDay(TimeZone timeZone) {
        return getIso8601(timeZone).contains(BEGIN_OF_DAY_DEFINE);
    }

    /**
     * Check is the instance at the end of the day.
     *
     * @return Boolean of the result
     */
    public boolean isAtEndOfDay() {
        return getIso8601().contains(END_OF_DAY_DEFINE);
    }

    /**
     * Check is the instance at the end of the day.
     *
     * @param timeZone TimeZone
     * @return Boolean of the result
     */
    public boolean isAtEndOfDay(TimeZone timeZone) {
        return getIso8601(timeZone).contains(END_OF_DAY_DEFINE);

    }

}
