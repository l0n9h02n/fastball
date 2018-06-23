package org.longhorn.fastball.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Clock {
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
     * Get the ISO-8601 format time of the instance.
     *
     * @return String of the ISO-8601 format time of the instance
     */
    public String getIso8601() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(defaultTimeZone);
        sdf.setLenient(false);
        return sdf.format(new Date(unixTs));
    }

}
