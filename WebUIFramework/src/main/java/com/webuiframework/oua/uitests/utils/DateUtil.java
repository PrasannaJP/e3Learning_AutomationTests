package com.webuiframework.oua.uitests.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * DateUtils.now("dd MMMMM yyyy")
 * DateUtils.now("yyyyMMdd")
 * DateUtils.now("dd.MM.yy")
 * DateUtils.now("MM/dd/yy")
 * DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z")
 * DateUtils.now("EEE, MMM d, ''yy")
 * DateUtils.now("h:mm a")
 * DateUtils.now("H:mm:ss:SSS")
 * DateUtils.now("K:mm a,z")
 * DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa")
 */
public final class DateUtil {

    private DateUtil(){}

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd_HH-mm-ss-SSS";

    /**
     * Get time now
     * @return time on specify format
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    /**
     * Get time now
     * @param dateFormat for time
     * @return time on specify format
     */
    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}

