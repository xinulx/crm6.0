package com.mine.App.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils
{
    
    /**
     * 
     * @Description 获取月份，入参是两个Date类型数据
     * @author: wangyl
     * @version:
     * @createTime: 2014-12-24 上午11:39:02
     * @return int
     */
    public static int getMonth(Date start, Date end)
    {
        if (start.after(end))
        {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);
        
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        
        if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1))
        {
            return year * 12 + month + 1;
        }
        else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1))
        {
            return year * 12 + month;
        }
        else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1))
        {
            return year * 12 + month;
        }
        else
        {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
    
    /**
     * 
     * @Description: 获取两个日期直接的天数
     * @author: wangyl
     * @version:
     * @createTime: 2014-12-29 上午10:52:47
     * @param
     * @return long
     */
    public static long getDays(String startTime, String endTime, String dateFormat)
    {
        DateFormat df = new SimpleDateFormat(dateFormat);
        long countDay = 0;
        try
        {
            Date date1 = df.parse(startTime);
            Date date2 = df.parse(endTime);
            countDay = date1.getTime() - date2.getTime();
            countDay = countDay / 1000 / 60 / 60 / 24;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return countDay;
    }
    
    /**
     * 
     * @Description:两个时间进行比较，时间格式
     * @author: wangyl
     * @version:
     * @createTime: 2014-12-24 下午2:24:09
     * @return int
     */
    public static int compareTime(String startTime, String endTime, String dateFormat)
    {
        
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try
        {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        }
        catch (ParseException e)
        {
            throw new RuntimeException("时间格式不正确");
        }
        int result = c1.compareTo(c2);
        /**
         * if (result == 0){ System.out.println("c1=c2"); }else if (result < 0){ System.out.println("c1<c2"); }else{
         * System.out.println("c1>c2"); }
         */
        return result;
    }
    
    /**
     * 
     * @Description: 获取两个日期之间的年份
     * @author: wangyl
     * @version:
     * @createTime: 2014-12-29 上午11:31:38
     * @param
     * @return int
     */
    public static int compareYear(Date d1, Date d2)
    {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(d1);
        ca2.setTime(d2);
        return ca2.get(Calendar.YEAR) - ca1.get(Calendar.YEAR);
    }
    
    /**
     * 
     * @Description: 获取当前月份的第一天
     * @author: songtao
     * @version:
     * @createTime: 2015-1-17上午11:31:38
     * @param dateFormat 返回的日期格式
     * @return String
     */
    public static String getFirstDayOfMonth(String dateFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime());
    }
    
    /**
     * 
     * @Description: 获取当前月份的最后一天
     * @author: songtao
     * @version:
     * @createTime: 2015-1-17上午11:31:38
     * @param dateFormat 返回的日期格式
     * @return String
     */
    public static String getLastDayOfMonth(String dateFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();
        cal_1.set(Calendar.DAY_OF_MONTH, cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(cal_1.getTime());
    }
    
    /**
     * 
     * @Description: 获取当前月份的指定某一天
     * @author: songtao
     * @version:
     * @createTime: 2015-1-17上午11:31:38
     * @param dateFormat 返回的日期格式
     * @return String
     */
    public static String getDayOfCurDayMonth(Date date, String dateFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        int dayNow = cal_1.get(Calendar.DAY_OF_MONTH);
        cal_1.setTime(date);
        int day = cal_1.get(Calendar.DAY_OF_MONTH);
        cal_1 = Calendar.getInstance();
        if (day > dayNow)
        {
            cal_1.add(Calendar.MONTH, -1);
        }
        cal_1.set(Calendar.DAY_OF_MONTH, day);// 设置为当前日期,当前日期既为本月指定日期
        return format.format(cal_1.getTime());
    }
    
    /**
     * 
     * @Description: 获取下一月份的指定某一天的前一天
     * @author: songtao
     * @version:
     * @createTime: 2015-1-17上午11:31:38
     * @param dateFormat 返回的日期格式
     * @return String
     */
    public static String getDayOfNextDayMonth(Date date, String dateFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        // 获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        int dayNow = cal_1.get(Calendar.DAY_OF_MONTH);
        cal_1.setTime(date);
        int day = cal_1.get(Calendar.DAY_OF_MONTH);
        cal_1 = Calendar.getInstance();
        if (day > dayNow)
        {
            cal_1.add(Calendar.MONTH, 0);
        }
        else
        {
            cal_1.add(Calendar.MONTH, 1);
        }
        cal_1.set(Calendar.DAY_OF_MONTH, day - 1);// 设置为当前日期,当前日期既为本月指定日期
        return format.format(cal_1.getTime());
    }
    
    /**
     * <一句话功能简述>将传来的字符串格式化为yyyy-MM-dd HH:mm:ss形式的时间字符串
     * 
     * @param str
     * @return
     * @fileNamw TimeUtils.java
     * @time 2017年5月8日下午3:35:13
     * @author songhw
     * @see [类、类#方法、类#成员]
     */
    public static String formateStr(String str)
    {
        char[] result = new char[20];
        char[] charArray = str.toCharArray();
        for (int i = 0, j = 0; i < charArray.length; i++, j++)
        {
            if (i == 4 || i == 6)
                result[j++] = '-';
            else if (i == 8)
                result[j++] = ' ';
            else if (i == 10 || i == 12)
                result[j++] = ':';
            else
                ;
            result[j] = charArray[i];
        }
        return String.valueOf(result);
    }
    
    public static String getNowTimeWithFormat(String format){
    	SimpleDateFormat fmt = new SimpleDateFormat(format);
        Calendar time = Calendar.getInstance();// 获取当前日期
        return fmt.format(time.getTime());
    }
    
}
