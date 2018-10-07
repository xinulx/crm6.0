package com.mine.App.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * @ClassName: DateHelper
 * @Description: 日期工具类
 * @author: wangshibao（wangshibao@tydic.com）
 * @date: 201511月30日 下午2:28:39
 */
public class DateHelper {

	/**
	 * @FieldName: logger
	 * @FieldType: Logger
	 * @Description: 日志
	 */
	protected static final Logger logger = Logger.getLogger(DateHelper.class);
    
    public static final String SDF_YMD1 = "yyyy-MM-dd";
    public static final String SDF_YMD2 = "yyyy/MM/dd";
    public static final String SDF_YMD3 = "yyyy年MM月dd日";
    public static final String SDF_YMDHMS1 = "yyyy-MM-dd HH:mm:ss";
    public static final String SDF_YMDHMS2 = "yyyy-MM-dd hh:mm:ss";
    public static final String SDF_YMDHMS3 = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String[] DATE_PATTERNS = new String[] { "yyyy-MM-dd" };
    public static final String[] CONVERT_DATE_PATTERNS = new String[] {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd hh:mm:ss" };

    /**
     * @Title: buildDate
     * @Description: 由年月日构建Date
     * @param year
     * @param month
     * @param day
     * @return Date
     */
    public static Date buildDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
    
    /**
     * @Title: getDateMargin
     * @Description: 计算日期相差天数
     * @param start
     * @param end
     * @return int
     */
    public static int getDateMargin(Date start, Date end) {
    	Long margin;
        margin = end.getTime() - start.getTime();
        margin = margin / (1000*60*60*24);
        return margin.intValue();
    }
    
    /**
     * @Title: getDateMargin
     * @Description: 计算日期相差天数
     * @param start
     * @param end
     * @return int
     */
    public static int getDateMargin(long start, long end) {
    	Long margin;
        margin = end - start;
        margin = margin / (1000*60*60*24);
        return margin.intValue();
    }
    
    /**
     * @Title: getDateMargin
     * @Description: 计算日期相差天数
     * @param start
     * @param end
     * @return int
     */
    public static int getDateMargin(String start, String end){
		return getDateMargin(stringToDate(start), stringToDate(end));
    }

    /**
     * @Title: getDateString
     * @Description: 得到日期字符串（yyyy-MM-dd）
     * @return String
     */
    public static String getDateString() {
        return getDateString("yyyy-MM-dd");
    }

    /**
     * @Title: getDateString
     * @Description: 得到日期字符串
     * @param date
     * @param partten
     * @return String
     */
    public static String getDateString(Date date, String partten) {
    	if(date == null) date = nowDate();
        String result = null;
        SimpleDateFormat formatter = new SimpleDateFormat(partten);
        result = formatter.format(date);
        return result;
    }
    
    /**
     * @Title: getDateString
     * @Description: 得到日期字符串
     * @param time
     * @param partten
     * @return String
     */
    public static String getDateString(Long time, String partten) {
        return getDateString(new Date(time), partten);
    }

    /**
     * @Title: getDateString
     * @Description: 得到日期字符串（当前日期）
     * @param partten
     * @return String
     */
    public static String getDateString(String partten) {
        return getDateString(nowDate(), partten);
    }

    /**
     * @Title: getDayCountOfMonth
     * @Description: 取得某月的天数
     * @param year
     * @param month
     * @return int
     */
    public static int getDayCountOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);
        return calendar.get(Calendar.DATE);
    }
    
    /**
     * @Title: getFirstDateOfMonth
     * @Description: 获得月第一天的日期
     * @param date
     * @return Date
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }
    
    /**
     * @Title: getFirstDayOfMonth
     * @Description: 获得某年某月第一天的日期
     * @param year
     * @param month
     * @return Date
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }
    
    /**
     * @Title: getFirstDayOfQuarter
     * @Description: 获得某年某季度的第一天的日期
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getFirstDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = (quarter - 1) * 3 + 1;
        }
        return getFirstDayOfMonth(year, month);
    }
    
    /**
     * @Title: getFirstDayOfYear
     * @Description: 获得某年的第一天的日期
     * @param year
     * @return Date
     */
    public static Date getFirstDayOfYear(int year) {
        return getFirstDayOfMonth(year, 1);
    }

    /**
     * @Title: getLastDateOfMonth
     * @Description: 获得月最后一天的日期
     * @param date
     * @return Date
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        return getPrevDate(calendar.getTime(), 1);
    }
    
    /**
     * @Title: getLastDayOfMonth
     * @Description: 获得某年某月最后一天的日期
     * @param year
     * @param month
     * @return Date
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return getPrevDate(calendar.getTime(), 1);
    }
    
    /**
     * @Title: getLastDayOfQuarter
     * @Description: 获得某年某季度的最后一天的日期
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getLastDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = quarter * 3;
        }
        return getLastDayOfMonth(year, month);

    }

    /**
     * @Title: getLastDayOfYear
     * @Description: 获得某年的最后一天的日期
     * @param year
     * @return Date
     */
    public static Date getLastDayOfYear(int year) {
        return getLastDayOfMonth(year, 12);
    }

    /**
     * @Title: getNextDate
     * @Description:  获得某一日期的后N天日期
     * @param date
     * @param next
     * @return
     */
    public static Date getNextDate(Date date, int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + next);
        return calendar.getTime();
    }

    /**
     * @Title: getNextDateString
     * @Description: 获得某一日期的后N天日期（String）
     * @param date
     * @param next
     * @param partten
     * @return String
     */
    public static String getNextDateString(Date date, int next, String partten) {
        return getDateString(getNextDate(date, next), partten);
    }

    /**
     * @Title: getNextYear
     * @Description: 返回下N年的当前日期
     * @param currentDate
     * @param next
     * @return
     */
    public static Date getNextYear(Date currentDate, int next) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.YEAR, next);//在年上加1
		return cal.getTime();
	}

    /**
     * @Title: getNextYearString
     * @Description: 返回下N年的当前日期（String）
     * @param currentDate
     * @param next
     * @return String
     */
    public static String getNextYearString(Date currentDate, int next){
    	Date nextYear = getNextYear(currentDate, next);
    	return getDateString(nextYear, "yyyy");
    }

    /**
     * @Title: getPrevDate
     * @Description: 获得某一日期的前N天日期
     * @param date
     * @param Prev
     * @return Date
     */
    public static Date getPrevDate(Date date, int Prev) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - Prev);
        return calendar.getTime();
    }

    /**
     * @Title: getPrevDateString
     * @Description: 获得某一日期的前N天日期（String）
     * @param date
     * @param Prev
     * @param partten
     * @return String
     */
    public static String getPrevDateString(Date date, int Prev, String partten) {
        return getDateString(getPrevDate(date, Prev), partten);
    }

    /**
     * @Title: getWeekFirstDate
     * @Description: 获取某一周的第一天(周一为一周第一天)
     * @param date
     * @return Date
     */
    public static Date getWeekFirstDate(Date date) {
    	// DATE转化为Calendar
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    	calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - dayOfWeek + 2);
    	return calendar.getTime();
    }
    
    /**
     * @Title: getWeekLastDate
     * @Description: 获取某一周的最后一天(周一为一周第一天)
     * @param date
     * @return Date
     */
    public static Date getWeekLastDate(Date date) {
		// DATE转化为Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - dayOfWeek + 2);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 6);
		return calendar.getTime();
    }
    
    /**
     * @Title: nowDate
     * @Description: 当前日期
     * @return Date
     */
    public static Date nowDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
//    	return new Date(System.currentTimeMillis());
    }
    
    /**
     * @Title: stringToDate
     * @Description: String转换为Date类型
     * @param param
     * @return Date
     */
	public static Date stringToDate(String param){
    	return stringToDate(param, null);
    }
    
    /**
     * @Title: stringToDate
     * @Description: String转换为Date类型
     * @param param
     * @return Date
     */
    public static Date stringToDate(String param, String partten) {
        if(param == null || param == "" || param.indexOf("null") >=0) {
            return null;
        }
        else {
        	if(partten == null){
        		partten = SDF_YMD1;
        	}
        	Date date = null;
        	SimpleDateFormat sdf = new SimpleDateFormat(partten);// hh:mm:ss
            param = param.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "").replaceAll("/", "-").replaceAll("\\.", "-");
            param = param.replaceAll("T", " ").replaceAll("时", ":").replaceAll("分", ":").replaceAll("秒", "");
            try {
				date = sdf.parse(param);
			} 
            catch (ParseException e) {
            	logger.error("String[" + param + "]转换为Date类型错误", e);
			}
            return new Date(date.getTime());
        }
    }
    
    /**
     * 返回当前系统时间<br>
     * 格式：XXXX年XX月XX日XX分XX秒<br>
     * @return
     */
    public  static String chineseDateStr(){
    	return new SimpleDateFormat(SDF_YMDHMS3).format(new Date());
    }
    
    /**
     * 返回当前日期字符串<br>
     * 格式：yyyyMMdd
     * @return
     */
    public static String getCurrTimeStr(){
    	return getDateString("yyyyMMdd");
    }
    
    /**
     * 计算日期相差天数(日期为字符串)<br>
     * @param start 开始日期<br>
     * @param end 结束日期<br>
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String start,String end) throws ParseException{  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Calendar cal = Calendar.getInstance();    
		cal.setTime(sdf.parse(start));    
		long time1 = cal.getTimeInMillis();                 
		cal.setTime(sdf.parse(end));    
		long time2 = cal.getTimeInMillis();         
		long between_days=(time2-time1)/(1000*3600*24);  
		return Integer.parseInt(String.valueOf(between_days));     
    }
    
    /**
     * 测试方法
     * @param argv
     * @throws ParseException
     */
    public static void main(String[] argv) throws Exception {
    	System.out.println(getDateMargin(DateHelper.getDateString(), "2014-03-28"));
    	System.out.println(getDateString(getFirstDateOfMonth(new Date()), "yyyy-MM-dd"));
    	System.out.println(getDateString(getLastDateOfMonth(new Date()), "yyyy-MM-dd"));
    	System.out.println(getWeekFirstDate(new Date()));
    	System.out.println(stringToDate("2014-03-17 01:30:00"));
    	System.out.println(stringToDate("Fri May 30 00:00:00 UTC+0800 2008"));
    	System.out.println(stringToDate("2014/03/17"));
    }
}