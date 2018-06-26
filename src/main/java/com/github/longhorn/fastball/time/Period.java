package com.github.longhorn.fastball.time;

import com.google.common.base.Optional;
import com.google.errorprone.annotations.Var;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility parse the input extended format (https://www.loc.gov/standards/datetime/pre-submission.html)\
 * ISO-8601 (https://en.wikipedia.org/wiki/ISO_8601) period to Clock instance. Could be used for further\
 * functionalities. Since there is no time zone be represented in the <b>Years</b>, <b>Week dates</b>,\
 * <b>Ordinal dates</b> expresses, so we supports <b>Calendar dates</b> and <b>Durations</b> with <b>Time intervals</b>\
 * only.
 */
public class Period {
    private static final Pattern INIT_PATTERN
            = Pattern.compile("(?<start>\\d{4}-[\\w-:+]*)(|\\/(?<end>(\\d{4}-[\\w-:+]*)|P\\d{1}\\w*))$");
    private static final Pattern CALENDAR_PATTERN
            = Pattern.compile("^(?<year>\\d{4})\\-(?<month>0+[1-9]|1[012])-(?<day>0+[1-9]|[12]\\d{1}|3[01])(|T(?<hour" +
            ">0+\\d{1}|1+\\d{1}|2+[0-3]):(?<minute>[0-5]\\d{1}):(?<second>[0-5]\\d{1})(?<timeZone>Z|[+-](0+\\d{1}|1+" +
            "\\d{1}|2+[0-3]):([0-5]\\d{1})))$");
    private static final Pattern DURATION_PATTERN
            = Pattern.compile("^P((?<year>\\d*)Y)((?<month>\\d*)M)((?<day>\\d*)D)T((?<hour>\\d*)H)((?<minute>\\d*)M)(" +
            "(?<second>\\d*)S)$");

    private Clock start;
    private Clock end;
    private Clock exacted;

    /**
     * Private constructor, initial the instance with start, end, and exacted Clock.
     *
     * @param start   start clock
     * @param end     end clock
     * @param exacted exacted clock
     */
    private Period(Clock start, Clock end, Clock exacted) {
        this.start = start;
        this.end = end;
        this.exacted = exacted;
    }

    /**
     * Create an instance by a period string.
     *
     * @param period period string
     * @return Period instance
     * @throws ParseException Parse Exception will be thrown if the period is invalid
     */
    public static Period parse(String period) throws ParseException {
        @Var Clock startClock;
        @Var Clock endClock;
        @Var Clock exactedClock = null;
        Matcher matcher = INIT_PATTERN.matcher(period);
        if (matcher.find()) {
            String start = matcher.group("start");
            startClock = convert2Clock(start, null);
            String end = matcher.group("end");
            endClock = convert2Clock(end, startClock);
            if (null == endClock) {
                exactedClock = startClock;
                startClock = null;
            }
        } else {
            throw new IllegalArgumentException("Invalid period");
        }
        return new Period(startClock, endClock, exactedClock);
    }

    /**
     * Convert the IS8601 format string to Clock instance. Will try calendar and duration patterns. If the time matches\
     * duration pattern, will add the correspond second from the start clock.
     *
     * @param time       ISO-8601 format time
     * @param startClock start clock for calculating the end time
     * @return Clock of the time.
     * @throws ParseException Parse Exception will be thrown if the period is invalid
     */
    @Nullable
    private static Clock convert2Clock(String time, @Var Clock startClock) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        @Var Matcher matcher = CALENDAR_PATTERN.matcher(time);
        if (matcher.find()) {
            String year = matcher.group("year");
            String month = Optional.fromNullable(matcher.group("month")).or("01");
            String day = Optional.fromNullable(matcher.group("day")).or("01");
            String hour = Optional.fromNullable(matcher.group("hour")).or("00");
            String minute = Optional.fromNullable(matcher.group("minute")).or("00");
            String second = Optional.fromNullable(matcher.group("second")).or("00");
            String timeZone = Optional.fromNullable(matcher.group("timeZone")).or("Z");
            return Clock.fromIso8601(
                    String.format("%s-%s-%sT%s:%s:%s%s", year, month, day, hour, minute, second, timeZone)
            );
        }
        matcher = DURATION_PATTERN.matcher(time);
        if (matcher.find()) {
            startClock = Optional.fromNullable(startClock).or(Clock.fromNow());
            String year = Optional.fromNullable(matcher.group("year")).or("0");
            String month = Optional.fromNullable(matcher.group("month")).or("0");
            String day = Optional.fromNullable(matcher.group("day")).or("0");
            String hour = Optional.fromNullable(matcher.group("hour")).or("0");
            String minute = Optional.fromNullable(matcher.group("minute")).or("0");
            String second = Optional.fromNullable(matcher.group("second")).or("0");
            return Clock.fromUnixTs(
                    startClock.getUnixTs()
                            + TimeUnit.DAYS.toSeconds(365 * Integer.valueOf(year))
                            + TimeUnit.DAYS.toSeconds(30 * Integer.valueOf(month))
                            + TimeUnit.DAYS.toSeconds(Integer.valueOf(day))
                            + TimeUnit.HOURS.toSeconds(Integer.valueOf(hour))
                            + TimeUnit.MINUTES.toSeconds(Integer.valueOf(minute))
                            + TimeUnit.SECONDS.toSeconds(Integer.valueOf(second))
            );
        }
        return null;
    }

    /**
     * Get the start clock.
     *
     * @return Start clock
     */
    public Clock getStart() {
        return start;
    }

    /**
     * Get the end clock.
     *
     * @return End clock
     */
    public Clock getEnd() {
        return end;
    }

    /**
     * Get the exacted clock.
     *
     * @return Exacted clock
     */
    public Clock getExacted() {
        return exacted;
    }
}
