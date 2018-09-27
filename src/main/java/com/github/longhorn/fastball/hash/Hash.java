package com.github.longhorn.fastball.hash;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public final class Hash {
    private static final HashFunction CRC32 = Hashing.crc32c();

    private Hash() {
    }

    /**
     * Calculate CRC32 hash of one to many strings. If the provided item is more then one, each item will be \
     * concatenated by underscore.
     *
     * @param input the input string(s)
     * @return String of the hash value
     */
    public static String crc32(Object... input) {
        Iterable it;
        if (null == input) {
            it = Collections.EMPTY_LIST;
        } else {
            it = Arrays.stream(input)
                    .map(i -> Optional.fromNullable(i).or(StringUtils.EMPTY))
                    .collect(Collectors.toList());
        }
        return CRC32.hashBytes(Joiner.on("_").join(it).getBytes(Charset.defaultCharset())).toString();
    }
}
