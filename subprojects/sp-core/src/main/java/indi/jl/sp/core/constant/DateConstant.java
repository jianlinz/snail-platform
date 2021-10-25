package indi.jl.sp.core.constant;

import java.time.format.DateTimeFormatter;

/**
 * 全局时间常量
 */
public class DateConstant {

    /**
     * 年月日 时分秒
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 年月日 时分秒 formatter
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * 年月日 时分秒 formatter
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
}
