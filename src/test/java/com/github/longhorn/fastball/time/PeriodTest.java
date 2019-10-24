package com.github.longhorn.fastball.time;

import com.google.errorprone.annotations.Var;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class PeriodTest {

    @Test
    public void testParse() {
        try {
            // <exacted> -08:00
            @Var Period p = Period.parse("2017-01-01T00:00:00-08:00");
            assertThat(p.getStart()).isNull();
            assertThat(p.getEnd()).isNull();
            assertThat(p.getExacted().getIso8601()).isEqualTo("2017-01-01T08:00:00Z");
            assertThat(p.toString()).isEqualTo("2017-01-01T00:00:00-08:00");
            // <exacted> +08:00
            p = Period.parse("2017-01-01T00:00:00+08:00");
            assertThat(p.getStart()).isNull();
            assertThat(p.getEnd()).isNull();
            assertThat(p.getExacted().getIso8601()).isEqualTo("2016-12-31T16:00:00Z");
            // <start>/<end>
            p = Period.parse("2017-01-01T00:00:00+08:00/2017-01-01T00:00:00Z");
            assertThat(p.getStart().getIso8601()).isEqualTo("2016-12-31T16:00:00Z");
            assertThat(p.getEnd().getIso8601()).isEqualTo("2017-01-01T00:00:00Z");
            assertThat(p.getExacted()).isNull();
            // <start>/<duration>
            p = Period.parse("2017-01-01/P1Y3M5DT6H7M30S");
            assertThat(p.getStart().getIso8601()).isEqualTo("2017-01-01T00:00:00Z");
            assertThat(p.getEnd().getIso8601()).isEqualTo("2018-04-06T06:07:30Z");
            assertThat(p.getExacted()).isNull();
        } catch (ParseException e) {
            System.err.println(ExceptionUtils.getStackTrace(e));
            fail();
        }
    }

    @Test
    public void testIsWholeMonth() {
        try {
            assertThat(
                    Period.parse("2017-01-01T00:00:00+08:00/2017-01-02T00:00:00+08:00")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000+08:00/2017-01-02T00:00:00.000+08:00")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000+08:00/2017-01-31T23:59:59.999+08:00")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))).isTrue();
            assertThat(
                    Period.parse("2017-01-01/2017-01-02")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00Z/2017-01-02T00:00:00Z")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000Z/2017-01-02T00:00:00.000Z")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000Z/2017-01-31T23:59:59.999Z")
                            .isWholeMonth(TimeZone.getTimeZone("UTC"))
            ).isTrue();
            assertThat(
                    Period.parse("2017-01-01/2017-01-31")
                            .isWholeMonth(TimeZone.getTimeZone("Asia/Taipei"))
            ).isTrue();
        } catch (ParseException e) {
            System.err.println(ExceptionUtils.getStackTrace(e));
            fail();
        }
    }

    @Test
    public void testIsTalliedWithDuration() {
        try {
            assertThat(
                    Period.parse("2017-01-01T00:00:00+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00+08:00/2017-01-02T00:00:00+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isTrue();
            assertThat(
                    Period.parse("2017-01-01T00:00:00+08:00/2017-01-02T00:00:01+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isFalse();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000+08:00/2017-01-02T00:00:00.000+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isTrue();
            assertThat(
                    Period.parse("2017-01-01T00:00:00.000+08:00/2017-01-02T00:00:01.000+08:00")
                            .isTalliedWithDuration(TimeUnit.DAYS, 1)
            ).isFalse();
        } catch (ParseException e) {
            System.err.println(ExceptionUtils.getStackTrace(e));
            fail();
        }
    }
}
