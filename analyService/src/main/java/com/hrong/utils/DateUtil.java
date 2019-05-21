package com.hrong.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import scala.Int;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Author hrong
 * @ClassName DateUtil
 * @Description
 * @Date 2019/5/20 11:22
 **/
@Slf4j
public class DateUtil {
	private static final FastDateFormat FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	private static Calendar instance = Calendar.getInstance();


	public static String getNowTime() {
		return FORMAT.format(new Date());
	}

	public static String getTimeByOffset(int year) {
		instance.setTime(new Date());
		return getTimeByOffset(year, 0, 0, 0, 0, 0);
	}

	public static String getTimeByOffset(int year, int month) {
		instance.setTime(new Date());
		return getTimeByOffset(year, month, 0, 0, 0, 0);
	}

	public static String getTimeByOffset(int year, int month, int day) {
		instance.setTime(new Date());
		return getTimeByOffset(year, month, day, 0, 0, 0);
	}

	public static String getTimeByOffset(int year, int month, int day, int hour) {
		instance.setTime(new Date());
		return getTimeByOffset(year, month, day, hour, 0, 0);
	}

	public static String getTimeByOffset(int year, int month, int day, int hour, int minute) {
		instance.setTime(new Date());
		return getTimeByOffset(year, month, day, hour, minute, 0);
	}

	public static String getTimeByOffset(int year, int month, int day, int hour, int minute, int second) {
		instance.setTime(new Date());
		instance.add(Calendar.YEAR, year);
		instance.add(Calendar.MONTH, month);
		instance.add(Calendar.DAY_OF_MONTH, day);
		instance.add(Calendar.HOUR, hour);
		instance.add(Calendar.MINUTE, minute);
		instance.add(Calendar.SECOND, second);
		return FORMAT.format(instance.getTime());
	}

	/**
	 * 输入年龄，得到所属年代信息
	 */
	public static String transformAge2Year(String ageString) {
		String yearType = "未知";
		int age = Integer.parseInt(ageString);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -age);
		int year = calendar.get(Calendar.YEAR);
		yearType = year >= 1950 ? "50后" : yearType;
		yearType = year >= 1960 ? "60后" : yearType;
		yearType = year >= 1970 ? "70后" : yearType;
		yearType = year >= 1980 ? "80后" : yearType;
		yearType = year >= 1990 ? "90后" : yearType;
		yearType = year >= 2000 ? "00后" : yearType;
		yearType = year >= 2010 ? "10后" : yearType;

		return yearType;
	}

	public static void main(String[] args) throws ParseException {
		String startTime = DateUtil.getTimeByOffset(-1);
		String endTime = DateUtil.getTimeByOffset(1);
		Date now = new Date();
		Date startDate = FORMAT.parse(startTime);
		Date endDate = FORMAT.parse(endTime);
		System.out.println(startDate.before(now) && endDate.after(now));
	}
}
