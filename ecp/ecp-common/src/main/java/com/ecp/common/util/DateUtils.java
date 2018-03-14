package com.ecp.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Class: DateUtils
 * 日期时间工具类
 * @author srd 
 * @version 1.0 $Date: 2016年12月27日 下午3:40:24
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
     
    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
        "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
 
    /**
     * 方法功能：时间字符串转为Long类型
     * @param str
     * @return
     * @throws ParseException
     * <hr>
     * <b>描述：</b>
     * <p>Description:方法功能详细说明</p> 
     * <p>Version: 1.0</p>
     * <p>Author: srd </p>
     * <p>Date: 2017年2月10日 下午3:27:55</p>
     */
    public static Long formatTimeToLong(String str) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	return sdf.parse(str).getTime();
    }
    
    /**
     * 获取1970年距今的秒数
     * @return
     */
    public static Long getDateTimeLong(){
    	return new Date().getTime()/1000;
    }
    /**
     * LongTime转为yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static String getDateTime(Long time){
    	return formatDateTime(new Date(time));
    }
    /**
     * LongTime转为yyyy-MM-dd
     * @param time
     * @return
     */
    public static String getDate(Long time){
    	return formatDate(new Date(time));
    }
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }
     
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }
     
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }
     
    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }
 
    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
 
    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }
 
    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }
 
    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }
 
    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }
     
    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
 
    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }
     
     
    /**
     * 方法功能：获取开始日期
     * @param date
     * @return
     * <hr>
     * <b>描述：</b>
     * <p>Description:方法功能详细说明</p> 
     * <p>Version: 1.0</p>
     * <p>Author: srd </p>
     * <p>Date: 2017年2月10日 下午3:29:32</p>
     */
    public static Date getDateStart(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
     
    /**
     * 方法功能：获取结束日期
     * @param date
     * @return
     * <hr>
     * <b>描述：</b>
     * <p>Description:方法功能详细说明</p> 
     * <p>Version: 1.0</p>
     * <p>Author: srd </p>
     * <p>Date: 2017年2月10日 下午3:29:47</p>
     */
    public static Date getDateEnd(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59.999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 判断字符串是否是日期
     * @param timeString
     * @return
     */    
    public static boolean isDate(String timeString){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try{
            format.parse(timeString);
        }catch(Exception e){
            return false;
        }
        return true;
    }
     
    /**
     * 格式化时间
     * @param timestamp
     * @return
     */
    public static String dateFormat(Date timestamp){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp);
    }
     
    /**
     * 获取系统时间Timestamp
     * @return
     */
    public static Timestamp getSysTimestamp(){
        return new Timestamp(new Date().getTime());
    }
     
    /**
     * 获取系统时间Date
     * @return
     */
    public static Date getSysDate(){
        return new Date();
    }
     
    /**
     * 生成时间随机数 
     * @return
     */
    public static String getDateRandom(){
        String s=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return s;
    }
    
    /**
     * 得到从 1970-01-01 00:00:00 开始到今天的天数
     * 
     * @param todayMS
     * @return
     * @throws ParseException
     */
    public static int getDaysFrom1970ToToday(Long todayMS) throws ParseException{
    	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long dateMS = sdf.parse("1970-01-01 00:00:00").getTime();
		Long days = (todayMS-dateMS)/1000/60/60/24;
        return days.intValue();
    }
    
    /**
     * 方法功能：获取昨天开始时间
     * @param date
     * @return
     * <hr>
     * <b>描述：</b>
     * <p>Description:方法功能详细说明</p> 
     * <p>Version: 1.0</p>
     * <p>Author: srd </p>
     * <p>Date: 2017年2月10日 下午3:30:17</p>
     */
    public static Long getYesterdayDatetimeStart(Date date){
    	return getDateStart(addDays(date, -1)).getTime();
    }

    /**
     * 方法功能：获取昨天的结束日期
     * @param date
     * @return
     * <hr>
     * <b>描述：</b>
     * <p>Description:方法功能详细说明</p> 
     * <p>Version: 1.0</p>
     * <p>Author: srd </p>
     * <p>Date: 2017年2月10日 下午3:30:36</p>
     */
    public static Long getYesterdayDatetimeEnd(Date date){
    	return getDateEnd(addDays(date, -1)).getTime();
    }
     
}