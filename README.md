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
zone be represented in the <b>Years</b>, <b>Week dates</b>, <b>Ordinal dates</b> expresses, so we supports
<b>Calendar dates</b> and <b>Durations</b> with <b>Time intervals</b> only.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/time/PeriodTest.java

### Net
#### Url
Extends java.net.URL, supports the following functions currently:
1. isFullyQualifiedURL(): Check is the url a fully qualified one.
2. getPathQueryRef(): Extract the path, query parameters and reference from a url.

For more samples, see:
https://github.com/l0n9h02n/fastball/blob/master/src/test/java/com/github/longhorn/fastball/net/UrlTest.java
