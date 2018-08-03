package service.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		String output=getFirstDayOfNextMonth("1960-12-2","yyyy-MM-dd");
		System.out.println("下一个月："+output);
	}
	
	/**
	 * 获取指定日期下个月的第一天
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static String getFirstDayOfNextMonth(String dateStr,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH,1);//设置月份为每月1号
			calendar.add(Calendar.MONTH, 1);//设置月份加1
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
