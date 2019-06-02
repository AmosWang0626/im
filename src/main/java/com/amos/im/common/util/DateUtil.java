package com.amos.im.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author DaoyuanWang
 */
public class DateUtil {

    private static Calendar calendar = Calendar.getInstance();

    /**
     * 日期格式化
     */
    private static final ThreadLocal<DateFormat> FORMAT_YEAR_2_SECOND =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<DateFormat> FORMAT_YEAR_2_DAY =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<DateFormat> FORMAT_YEAR_2_MONTH =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM"));

    private void changeTimeByMillis() {
        long time1 = System.currentTimeMillis();
        // 直接操作时间戳, 是按照毫秒累加的, 1000也即增加一秒
        long time2 = System.currentTimeMillis() + 1000;
        System.out.println(time1);
        System.out.println(time2);

        Date date1 = new Date(time1);
        Date date2 = new Date(time2);

        // Wed Jan 31 15:05:10 CST 2018
        System.out.println(date1);
        // Wed Jan 31 15:05:11 CST 2018
        System.out.println(date2);
    }

    /**
     * 将字符串转化为日期
     */
    private static void formatString2Date() {
        try {
            Date testDate = FORMAT_YEAR_2_MONTH.get().parse("2017-09");
            System.out.println(FORMAT_YEAR_2_DAY.get().format(testDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * calendar 转化日期
     */
    private void calendarFormat() {
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        Date time = calendar.getTime();
        System.out.println(FORMAT_YEAR_2_SECOND.get().format(time));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));

        // 超过五分钟的认证中,重新执行任务
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        time = calendar.getTime();
        System.out.println(FORMAT_YEAR_2_SECOND.get().format(time));

        // long timeInMillis = calendar.getTimeInMillis();
        // long currentTimeMillis = System.currentTimeMillis();
        // System.out.println("SSS:" + (currentTimeMillis - timeInMillis) / (1000 * 60));
    }

    /**
     * 根据日期获取当前的年月日
     */
    private static void getYearMonthDay() {
        calendar.setTime(new Date());
        String year = calendar.get(Calendar.YEAR) + "";
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year + "年" + month + "月" + day + "日");
    }

    /**
     * 获取指定日期当前月第一天
     *
     * @param date 日期
     * @return 日期
     */
    private static Date getPreDay(Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期
     *
     * @param date 当前日期
     * @param i    参数
     * @return 日期
     */
    private static Date getTestDay(Date date, int i) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendar.getTime();
    }

    public static String getNowStr() {
        return FORMAT_YEAR_2_SECOND.get().format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(new Date().toInstant());
    }

}
