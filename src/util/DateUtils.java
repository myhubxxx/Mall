package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private final static String DATE = "yyyy-MM-dd";
	private final static String TIME = "yyyy-MM-dd HH:mm:ss";
	
	public static String getSimpleDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		return sdf.format(new Date());
	}
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(TIME);
		return sdf.format(new Date());
	}

}
