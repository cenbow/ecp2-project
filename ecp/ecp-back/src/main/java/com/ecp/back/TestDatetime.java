package com.ecp.back;

import java.util.Calendar;

public class TestDatetime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar datatime = Calendar.getInstance();
		String year = String.valueOf(datatime.get(Calendar.YEAR));
		String month="";
		int tempMonth=datatime.get(Calendar.MONTH);
		/*if(tempMonth<10){
			month='0'+String.valueOf(tempMonth);
		}
		else
			month=String.valueOf(tempMonth);*/
		
		month=String.format("%02d",tempMonth);
		
		String yearmonth = year + month;
		System.out.println(yearmonth);
		System.out.println(month);

	}

}
