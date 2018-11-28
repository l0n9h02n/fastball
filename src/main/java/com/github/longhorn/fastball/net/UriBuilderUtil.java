package com.github.longhorn.fastball.net;

import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;

import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class UriBuilderUtil {
    private static final String COMMA = ",";

    private UriBuilderUtil() {
    }

    /**
     * Append a query parameter to the existing set of query parameters if match the condition. If multiple values are
     * supplied the parameter will be added once per value.
     *
     * @param builder   instance of UriBuilder
     * @param condition condition
     * @param name      the query parameter name, may contain URI template parameters.
     * @param values    the query parameter value(s), each object will be converted to a {@code String} using its
     *                  {@code toString()} method. Stringified values may contain URI template parameters.
     * @return the updated UriBuilder.
     */
    public static UriBuilder queryParamWithCondition(
            UriBuilder builder, boolean condition, String name, Object... values
    ) {
        if (condition) {
            return builder.queryParam(name, values);
        }
        return builder;
    }

    /**
     * Append a query parameter to the existing set of query parameters if the value is not null. If multiple values are
     * supplied the parameter will be added once per value.
     *
     * @param builder instance of UriBuilder
     * @param name    the query parameter name, may contain URI template parameters.
     * @param values  the query parameter value(s), each object will be converted to a {@code String} using its
     *                {@code toString()} method. Stringified values may contain URI template parameters.
     * @return the updated UriBuilder.
     */
    public static UriBuilder queryParamIfNotNull(UriBuilder builder, String name, Object... values) {
        if (!Objects.isNull(values)) {
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                if (!Objects.isNull(values[i])) {
                    objects.add(values[i]);
                }
            }
            if (CollectionUtils.isNotEmpty(objects)) {
                return builder.queryParam(name, objects.toArray());
            }
        }
        return builder;
    }

    /**
     * Append a query parameter to the existing set of query parameters if the value is not empty. If multiple values
     * are supplied the parameter will be added once per value.
     *
     * @param builder instance of UriBuilder
     * @param name    the query parameter name, may contain URI template parameters.
     * @param values  the query parameter value(s), each object will be converted to a {@code String} using its
     *                {@code toString()} method. Stringified values may contain URI template parameters.
     * @return the updated UriBuilder.
     */
    public static UriBuilder queryParamIfNotEmptyCollection(UriBuilder builder, String name, Collection values) {
        if (CollectionUtils.isNotEmpty(values)) {
            return builder.queryParam(name, Joiner.on(COMMA).join(values));
        }
        return builder;
    }
}
