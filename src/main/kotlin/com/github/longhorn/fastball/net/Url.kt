package com.github.longhorn.fastball.net

import org.apache.commons.lang3.StringUtils
import java.net.MalformedURLException
import java.net.URL

/**
 * Check is the url a fully qualified one.
 *
 * @param url the url to be checked
 * @return the boolean value of the result
 */
fun isFullyQualifiedURL(url: String?): Boolean {
    if (StringUtils.isBlank(url)) {
        return false
    }
    try {
        URL(url)
    } catch (e: MalformedURLException) {
        return false
    }

    return true
}

/**
 * Extract the path, query parameters and reference from a url.
 *
 * @param url the url to be checked
 * @return the string of path, query parameters and reference
 */
fun getPathQueryRef(input: String): String {
    val url: URL
    try {
        url = URL(input)
    } catch (e: MalformedURLException) {
        return input
    }

    val query = if (StringUtils.isBlank(url.query)) "" else "?" + url.query
    val ref = if (StringUtils.isBlank(url.ref)) "" else "#" + url.ref
    return url.path + query + ref
}

/**
 * Check is the input a fully qualified URL.
 *
 * @param testee input
 */
fun assertFullyQualified(testee: String) {
    if (StringUtils.isBlank(testee)) {
        throw IllegalArgumentException("empty input")
    }
    try {
        URL(testee)
    } catch (e: MalformedURLException) {
        throw IllegalArgumentException(String.format("%s isn't a fully qualified URL", testee))
    }

}
