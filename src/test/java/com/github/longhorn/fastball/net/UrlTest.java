package com.github.longhorn.fastball.net;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class UrlTest {

    @Test
    public void testIsFullyQualifiedURL() {
        assertThat(UrlKt.isFullyQualifiedURL(null)).isFalse();
        assertThat(UrlKt.isFullyQualifiedURL("")).isFalse();
        assertThat(UrlKt.isFullyQualifiedURL("/foo/bar/index.php")).isFalse();
        assertThat(UrlKt.isFullyQualifiedURL("https://github.com/#foo")).isTrue();
    }

    @Test
    public void testGetPathQueryRef() {
        assertThat(UrlKt.getPathQueryRef("https://github.com/#foo")).isEqualTo("/#foo");
        assertThat(UrlKt.getPathQueryRef("https://github.com/")).isEqualTo("/");
        assertThat(UrlKt.getPathQueryRef("https://github.com/abc?a=1&b=2")).isEqualTo("/abc?a=1&b=2");
        assertThat(UrlKt.getPathQueryRef("/foo/bar/index.php")).isEqualTo("/foo/bar/index.php");
    }

    @Test
    public void testAssertFullyQualified_happyPath() {
        UrlKt.assertFullyQualified("http://www.example.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertFullyQualified_invalidUrl() {
        UrlKt.assertFullyQualified("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertFullyQualified_blankInput() {
        UrlKt.assertFullyQualified(StringUtils.EMPTY);
    }
}
