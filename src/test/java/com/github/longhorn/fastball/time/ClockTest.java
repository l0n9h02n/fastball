package com.github.longhorn.fastball.time;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;

public class ClockTest {

    @Test
    public void testGetIso8601FromNow() {
        Clock clock = Clock.fromNow();
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$");
        Matcher matcher = pattern.matcher(clock.getIso8601());
        assertThat(matcher.find()).isTrue();
    }


}
