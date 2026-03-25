package com.oa.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_MM_SS = "HH:mm:ss";

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /** 获取当前Date型日期 */
    public static Date getNowDate() {
        return new Date();
    }

    /** 获取当前日期, 默认格式为yyyy-MM-dd */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /** 获取当前时间 */
    public static String getTime() {
        return dateTimeNow(HH_MM_SS);
    }

    /** 获取当前日期时间 */
    public static String getDateTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /** 获取当前日期时间（紧凑格式） */
    public static String getDateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /** 根据格式获取当前日期时间字符串 */
    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /** 日期对象转字符串 */
    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /** 日期路径 即年/月/日 如2018/08/08 */
    public static String datePath() {
        return DateFormatUtils.format(new Date(), "yyyy/MM/dd");
    }

    /** 日期路径 即年月日 如20180808 */
    public static String dateTime() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd");
    }

    /** 日期型字符串转化为日期 */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), PARSE_PATTERNS);
        } catch (ParseException e) {
            // 可根据需要记录日志
            return null;
        }
    }

    /** 获取服务器启动时间 */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /** 计算两个时间差，返回格式：X天X小时X分钟 */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long msPerDay = 1000L * 60 * 60 * 24;
        long msPerHour = 1000L * 60 * 60;
        long msPerMinute = 1000L * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        long days = diff / msPerDay;
        long hours = (diff % msPerDay) / msPerHour;
        long minutes = (diff % msPerDay % msPerHour) / msPerMinute;
        return days + "天" + hours + "小时" + minutes + "分钟";
    }

    /** 获取某天的开始时间 */
    public static Date getDayStart(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /** 获取某天的结束时间 */
    public static Date getDayEnd(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /** 判断是否为工作日（周一到周五） */
    public static boolean isWorkDay(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }
}