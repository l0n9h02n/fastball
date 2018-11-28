# Fastball: Frequency used JVM libraries

[![Build Status](https://travis-ci.org/l0n9h02n/fastball.svg?branch=master)](https://travis-ci.org/l0n9h02n/fastball) [![codecov](https://codecov.io/gh/l0n9h02n/fastball/branch/master/graph/badge.svg)](https://codecov.io/gh/l0n9h02n/fastball)

## mvnrepository
https://mvnrepository.com/artifact/com.github.l0n9h02n/fastball

## Usage

### Time

#### Clock
Some useful tools for converting the time format between UNIX timestamp, ISO-8601, and SQL timestamp.
Support multiple time zone and provide interface to check is the time at the begin/end of the day.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/time/ClockTest.java

#### Period
An utility for parsing or assigning the input extended (https://www.loc.gov/standards/datetime/pre-submission.html)
ISO-8601 format (https://en.wikipedia.org/wiki/ISO_8601) period to Clock instances. Could be used for further
functionalities like checking is the period a whole month or matching the excepted duration. Since there is no time
zone be represented in the `Years`, `Week dates`, `Ordinal dates` expresses, so we supports `Calendar dates` and
`Durations` with `Time intervals` only.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/time/PeriodTest.java

### Net

#### Url
Extends java.net.URL, supports the following functions currently:
1. isFullyQualifiedURL(): Check is the url a fully qualified one.
2. getPathQueryRef(): Extract the path, query parameters and reference from a url.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/net/UrlTest.java

#### UriBuilderUtil
Since the original `javax.ws.rs.core.UriBuilder` will throw `IllegalArgumentException` if the parameter name or value
is `null`. We have to check the value or catch the exception manually. It might cause the `NPath complexity` warning and
make our codes longer than necessary, like
```java
if (null != id) {
    builder.queryParam("id", id);
}
if (ArrayUtils.isNotEmpty(list)) {
    builder.queryParam("list", list);
}
```
To resolve this pain point, we provide 3 interface to handle the `null` and `emtpy` check.

Besides, the original argument of value is an array. The `queryParamIfNotEmptyCollection` function will concatenate
each element by comma (,) which might easy your life.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/net/UriBuilderUtilTest.java