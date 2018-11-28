package com.github.longhorn.fastball.net;

import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class UriBuilderUtilTest {
    @Test
    public void test() {
        UriBuilder builder = UriBuilder.fromUri("https://tw.yahoo.com")
                .path("foo");
        UriBuilderUtil.queryParamWithCondition(builder, true, "a", "a");
        UriBuilderUtil.queryParamWithCondition(builder, false, "b", "b");
        UriBuilderUtil.queryParamIfNotNull(builder, "c", "c");
        UriBuilderUtil.queryParamIfNotNull(builder, "d", null);
        UriBuilderUtil.queryParamIfNotEmptyCollection(builder, "e", Arrays.asList("e1", "e2"));
        UriBuilderUtil.queryParamIfNotEmptyCollection(builder, "f", Collections.EMPTY_LIST);
        try {
            String url = builder.build().toURL().toString();
            assertThat(url).isEqualTo("https://tw.yahoo.com/foo?a=a&c=c&e=e1%2Ce2");
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            fail();
        }
    }
}
