package com.hrong.utils;

import com.hrong.constant.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author hrong
 * @ClassName EmailUtil
 * @Description
 * @Date 2019/5/21 15:24
 **/
@Slf4j
public class EmailUtil {
	/**
	 * 获取所属邮件运营商
	 * @param email email地址
	 * @return 运营商枚举值
	 */
	public static Email getRelationCompanyByEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return Email.NONE;
		}
		Email[] values = Email.values();
		for (Email value : values) {
			String suffix = value.getSuffix();
			if (email.toLowerCase().contains(suffix)) {
				return value;
			}
		}
		return Email.NONE;
	}
}
