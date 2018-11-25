package com.github.longhorn.fastball.watchdog;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class UrlTest {
    @Test
    public void testHappyPath() {
        Url.assertFullyQualified("http://www.example.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUrl() {
        Url.assertFullyQualified("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankInput() {
        Url.assertFullyQualified(StringUtils.EMPTY);
    }
}
