package com.github.longhorn.fastball.time;

import com.google.errorprone.annotations.Var;
import org.junit.Test;

import java.text.ParseException;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class ClockTest {

    @Test
    public void testGetUnixTs() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(String.valueOf(clock.getUnixTs()));
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetUnixTsMilli() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{13}$");
        Matcher matcher = pattern.matcher(String.valueOf(clock.getUnixTsMilli()));
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetUnixTsString() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(clock.getUnixTsString());
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetUnixTsMilliString() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{13}$");
        Matcher matcher = pattern.matcher(clock.getUnixTsMilliString());
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetIso8601MillilsFromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$");
        Matcher matcher = pattern.matcher(clock.getIso8601Millis());
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetIso8601MillisWithTimeZoneFromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+08:00$");
        Matcher matcher = pattern.matcher(clock.getIso8601Millis(TimeZone.getTimeZone("Asia/Taipei")));
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetIso8601FromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$");
        Matcher matcher = pattern.matcher(clock.getIso8601());
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetUnixTsFromIso8601() {
        try {
            @Var Clock clock = Clock.fromIso8601("2018-06-26T03:03:19Z");
            assertThat(clock.getUnixTs()).isEqualTo(1529982199L);
            clock = Clock.fromIso8601("2018-06-26T03:03:19.123Z");
            assertThat(clock.getUnixTsMilli()).isEqualTo(1529982199123L);
            clock = Clock.fromIso8601("2018-06-26T11:03:19+08:00");
            assertThat(clock.getUnixTs()).isEqualTo(1529982199L);
            clock = Clock.fromIso8601("2018-06-26T11:03:19.123+08:00");
            assertThat(clock.getUnixTsMilli()).isEqualTo(1529982199123L);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetIso8601MillisFromUnixTs() {
        // integer
        @Var Clock clock = Clock.fromUnixTs(1529982199);
        assertThat(clock.getIso8601Millis()).isEqualTo("2018-06-26T03:03:19.000Z");
        // long
        clock = Clock.fromUnixTs(1529982199L);
        assertThat(clock.getIso8601Millis()).isEqualTo("2018-06-26T03:03:19.000Z");
        // string
        clock = Clock.fromUnixTs("1529982199");
        assertThat(clock.getIso8601Millis()).isEqualTo("2018-06-26T03:03:19.000Z");
    }

    @Test
    public void testGetIso8601FromUnixTs() {
        // integer
        @Var Clock clock = Clock.fromUnixTs(1529982199);
        assertThat(clock.getIso8601()).isEqualTo("2018-06-26T03:03:19Z");
        // long
        clock = Clock.fromUnixTs(1529982199L);
        assertThat(clock.getIso8601()).isEqualTo("2018-06-26T03:03:19Z");
        // string
        clock = Clock.fromUnixTs("1529982199");
        assertThat(clock.getIso8601()).isEqualTo("2018-06-26T03:03:19Z");
    }

    @Test
    public void testGetSqlTs() {
        Clock clock = Clock.fromUnixTs(1529982199);
        assertThat(clock.getSqlTs()).isEqualTo("2018-06-26 03:03:19");
        assertThat(clock.getSqlTs(TimeZone.getTimeZone("Asia/Taipei"))).isEqualTo("2018-06-26 11:03:19");
    }

    @Test
    public void testIsEqualTo() {
        assertThat(Clock.fromUnixTs(1529982199).isEqualTo(Clock.fromUnixTs(1529982199))).isTrue();
        assertThat(Clock.fromUnixTs(1529982199).isEqualTo(Clock.fromUnixTs(1529982200))).isFalse();
    }

    @Test
    public void testIsAtBeginOfDay() {
        try {
            @Var Clock clock = Clock.fromIso8601("2018-06-26T00:00:00Z");
            assertThat(clock.isAtBeginOfDay()).isTrue();
            clock = Clock.fromIso8601("2018-06-26T00:00:01Z");
            assertThat(clock.isAtBeginOfDay()).isFalse();
            clock = Clock.fromIso8601("2018-06-26T00:00:00+08:00");
            assertThat(clock.isAtBeginOfDay()).isFalse();
            clock = Clock.fromIso8601("2018-06-26T00:00:00+08:00");
            assertThat(clock.isAtBeginOfDay(TimeZone.getTimeZone("Asia/Taipei"))).isTrue();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testIsAtEndOfDay() {
        try {
            @Var Clock clock = Clock.fromIso8601("2018-06-26T23:59:59Z");
            assertThat(clock.isAtEndOfDay()).isTrue();
            clock = Clock.fromIso8601("2018-06-26T00:00:01Z");
            assertThat(clock.isAtEndOfDay()).isFalse();
            clock = Clock.fromIso8601("2018-06-26T23:59:59+08:00");
            assertThat(clock.isAtEndOfDay()).isFalse();
            clock = Clock.fromIso8601("2018-06-26T23:59:59+08:00");
            assertThat(clock.isAtEndOfDay(TimeZone.getTimeZone("Asia/Taipei"))).isTrue();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
