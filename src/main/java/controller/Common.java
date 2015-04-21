package controller;

import java.util.Calendar;

public class Common
{
	public static String getCurrentTime()
	{
		Calendar calendar = Calendar.getInstance();
		String time = new String();
		int hour, minute, second;
		
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		
		if(hour < 10)
			time += "0" + hour + ":";
		else
			time += hour + ":";
		
		if(minute < 10)
			time += "0" + minute + ":";
		else
			time += minute + ":";
		
		if(second < 10)
			time += "0" + second;
		else
			time += second;
		
		return time;
	}
	
	public static String getDate()
	{
		Calendar calendar = Calendar.getInstance();
		String date = new String();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		date += day + "." + month + "." + year;
		return date;
	}
}
