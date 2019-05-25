package com.hrong.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author hrong
 * @ClassName NumberUtil
 * @Description
 * @Date 2019/5/25 08:02
 **/
public class NumberUtil {

	/**
	 * 格式化小数
	 */
	public static double formatNumber(Double number, int digit){
		BigDecimal decimal = new BigDecimal(number);
		BigDecimal result = decimal.setScale(digit, RoundingMode.HALF_UP);
		return result.doubleValue();
	}
}
