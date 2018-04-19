package com.ecp.common.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 获取某年第一天日期第一秒
	 * 
	 * @param year
	 *            年份
	 * @return String
	 */
	public static Date getFirstDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		System.out.println("========="+currYearFirst);
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天最后一秒
	 * 
	 * @param year
	 *            年份
	 * @return String
	 */
	public static Date getLastDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		int dayMis=1000*60*60*24-1000;//一天的毫秒-1  
          
        //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
        long curMillisecond=currYearLast.getTime();//当天的毫秒  
        long resultMis=curMillisecond+dayMis; //当天最后一秒  
        //得到我需要的时间    当天最后一秒  
        Date resultDate=new Date(resultMis);  
        System.out.println("========="+resultDate);
        return resultDate;
	}
	
	/**
     * 获取某半年的第一天第一秒
     *
     * @param year
     * @param halfAYear
     * @return
     */
    public static Date getFirstDayOfHalfAYear(int year, int halfAYear) {
    	Date resultDate = null;
        switch (halfAYear) {
		case 1:
			resultDate = getFirstDayOfMonth(year, 1);
			break;
		case 2:
			resultDate = getFirstDayOfMonth(year, 7);
			break;
		default:
			break;
		}
        return resultDate;
    }
	/**
     * 获取某半年的最后一天最后一秒
     *
     * @param year
     * @param halfAYear
     * @return
     */
    public static Date getLastDayOfHalfAYear(int year, int halfAYear) {
    	Date resultDate = null;
        switch (halfAYear) {
		case 1:
			resultDate = getLastDayOfMonth(year, 6);
			break;
		case 2:
			resultDate = getLastDayOfMonth(year, 12);
			break;
		default:
			break;
		}
        return resultDate;
    }
	
	/**
     * 获取某季度的第一天第一秒
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(int year, int quarter) {
    	Date resultDate = null;
        switch (quarter) {
		case 1:
			resultDate = getFirstDayOfMonth(year, 1);
			break;
		case 2:
			resultDate = getFirstDayOfMonth(year, 4);
			break;
		case 3:
			resultDate = getFirstDayOfMonth(year, 7);
			break;
		case 4:
			resultDate = getFirstDayOfMonth(year, 10);
			break;
		default:
			break;
		}
        return resultDate;
    }
	/**
     * 获取某季度的最后一天最后一秒
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(int year, int quarter) {
    	Date resultDate = null;
        switch (quarter) {
		case 1:
			resultDate = getLastDayOfMonth(year, 3);
			break;
		case 2:
			resultDate = getLastDayOfMonth(year, 6);
			break;
		case 3:
			resultDate = getLastDayOfMonth(year, 9);
			break;
		case 4:
			resultDate = getLastDayOfMonth(year, 12);
			break;
		default:
			break;
		}
        return resultDate;
    }
	
	/** 
	* 获得该月第一天第一秒
	* @param year 
	* @param month 
	* @return 
	*/  
	public static Date getFirstDayOfMonth(int year,int month){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();
        //设置年份  
        calendar.set(Calendar.YEAR,year);  
        //设置月份  
        calendar.set(Calendar.MONTH, month-1);  
        //获取某月最小天数  
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);  
        //设置日历中月份的最小天数  
        calendar.set(Calendar.DAY_OF_MONTH, firstDay); 
        System.out.println("========="+calendar.getTime());
        return calendar.getTime();  
    }  
	  
	/** 
	* 获得该月最后一天最后一秒
	* @param year 
	* @param month 
	* @return 
	*/  
	public static Date getLastDayOfMonth(int year,int month){  
        Calendar calendar = Calendar.getInstance(); 
        calendar.clear();
        //设置年份  
        calendar.set(Calendar.YEAR,year);  
        //设置月份  
        calendar.set(Calendar.MONTH, month-1);  
        //获取某月最大天数  
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
        //设置日历中月份的最大天数  
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);  
        
        int dayMis=1000*60*60*24-1000;//一天的毫秒-1  
        
        //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
        long curMillisecond=calendar.getTime().getTime();//当天的毫秒  
        long resultMis=curMillisecond+dayMis; //当天最后一秒  
        //得到我需要的时间    当天最后一秒  
        Date resultDate=new Date(resultMis); 
        System.out.println("========="+resultDate);
        return resultDate;
    }
	
	public static void main(String[] args) {
		System.out.println(getFirstDayOfYear(2018));
		System.out.println(getLastDayOfYear(2018));
	}
	
}
