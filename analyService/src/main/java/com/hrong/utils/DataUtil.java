package com.hrong.utils;

import lombok.extern.slf4j.Slf4j;
import scala.Int;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @Author hrong
 * @ClassName DataUtil
 * @Description
 * @Date 2019/5/20 11:22
 **/
@Slf4j
public class DataUtil {
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
}
