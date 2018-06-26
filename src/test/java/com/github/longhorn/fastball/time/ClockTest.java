package com.github.longhorn.fastball.time;

import com.google.errorprone.annotations.Var;
import org.junit.Test;

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;

public class ClockTest {

    @Test
    public void testGetIso8601MillilsFromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$");
        Matcher matcher = pattern.matcher(clock.getIso8601Millis());
        System.out.println(clock.getIso8601Millis());
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetIso8601MillisWithTimeZoneFromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+08:00$");
        Matcher matcher = pattern.matcher(clock.getIso8601Millis(TimeZone.getTimeZone("Asia/Taipei")));
        System.out.println(clock.getIso8601Millis(TimeZone.getTimeZone("Asia/Taipei")));
        assertThat(matcher.find()).isTrue();
    }

    @Test
    public void testGetIso8601FromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$");
        Matcher matcher = pattern.matcher(clock.getIso8601());
        System.out.println(clock.getIso8601());
        assertThat(matcher.find()).isTrue();
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
}
