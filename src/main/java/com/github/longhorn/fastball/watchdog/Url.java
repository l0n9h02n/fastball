package com.github.longhorn.fastball.watchdog;

import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

public final class Url {
    private Url() {
    }

    /**
     * Check is the input a fully qualified URL.
     *
     * @param testee input
     */
    public static void assertFullyQualified(String testee) {
        if (StringUtils.isBlank(testee)) {
            throw new IllegalArgumentException("empty input");
        }
        try {
            new URL(testee);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("%s isn't a fully qualified URL", testee));
        }
    }
}
