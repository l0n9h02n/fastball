package com.github.longhorn.fastball.hash;

import org.junit.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class HashTest {
    @Test
    public void testCrc32cHash() {
        assertThat(Hash.crc32("foo")).isEqualTo("1daec4cf");
        assertThat(Hash.crc32("foo", "bar")).isEqualTo("ae843662");
        assertThat(Hash.crc32(null)).isEqualTo("00000000");
        assertThat(Hash.crc32(null, null)).isEqualTo("a6b54b5d");
        assertThat(Hash.crc32(Arrays.asList(1, 2, 3), "a", "b")).isEqualTo("4b4bd358");
    }
}
