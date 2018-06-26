package com.github.longhorn.fastball.time;

import com.google.errorprone.annotations.Var;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;

import java.text.ParseException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class PeriodTest {
    @Test
    public void testParse() {
        try {
            @Var Period p = Period.parse("2017-01-01/P1Y3M5DT6H7M30S");
            assertThat(p.getStart().getIso8601()).isEqualTo("2017-01-01T00:00:00Z");
            assertThat(p.getEnd().getIso8601()).isEqualTo("2018-04-06T06:07:30Z");
            assertThat(p.getExacted()).isNull();
            p = Period.parse("2017-01-01T00:00:00+08:00/2017-01-01T00:00:00Z");
            assertThat(p.getStart().getIso8601()).isEqualTo("2016-12-31T16:00:00Z");
            assertThat(p.getEnd().getIso8601()).isEqualTo("2017-01-01T00:00:00Z");
            assertThat(p.getExacted()).isNull();
            p = Period.parse("2017-01-01T00:00:00-08:00");
            assertThat(p.getStart()).isNull();
            assertThat(p.getEnd()).isNull();
            assertThat(p.getExacted().getIso8601()).isEqualTo("2017-01-01T08:00:00Z");
            p = Period.parse("2017-01-01T00:00:00+08:00");
            assertThat(p.getStart()).isNull();
            assertThat(p.getEnd()).isNull();
            assertThat(p.getExacted().getIso8601()).isEqualTo("2016-12-31T16:00:00Z");
        } catch (ParseException e) {
            System.err.println(ExceptionUtils.getFullStackTrace(e));
            fail();
        }
    }

}
