package com.hrong.utils;

import com.hrong.constant.Carrier;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.SchemaOutputResolver;
import java.util.regex.Pattern;

/**
 * @Author hrong
 * @ClassName CarrierUtil
 * @Description 运营商相关工具方法
 * @Date 2019/5/21 12:50
 **/
@Slf4j
public class CarrierUtil {
	private static final Pattern CHINA_TELECOM = Pattern.compile("(^1(33|53|73|77|99|8[019])\\d{8}$)|(^1700\\d{7}$)");
	private static final Pattern CHINA_UNICOM = Pattern.compile("(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)");
	private static final Pattern CHINA_MOBILE = Pattern.compile("(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)");

	/**
	 * 中国移动 CHINA_MOBILE 1
	 * 中国联通 CHINA_UNICOM 2
	 * 中国电信 CHINA_TELECOM 3
	 * 未知运营商 0
	 */
	public static Carrier getCarrierByTelphone(String telPhone) {
		Carrier type = Carrier.NONE;
		if (CHINA_MOBILE.matcher(telPhone).matches()) {
			type = Carrier.CHINA_MOBILE;
		} else if (CHINA_UNICOM.matcher(telPhone).matches()) {
			type = Carrier.CHINA_UNICOM;
		} else if (CHINA_TELECOM.matcher(telPhone).matches()) {
			type = Carrier.CHINA_TELECOM;
		}
		return type;
	}

	public static void main(String[] args) {
		String tel = "18113996630";
		System.out.println(CHINA_MOBILE.matcher(tel).matches());
	}
}
