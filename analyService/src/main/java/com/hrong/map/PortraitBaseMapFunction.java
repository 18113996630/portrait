package com.hrong.map;

import com.hrong.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.sql.Timestamp;

/**
 * @Author hrong
 * @ClassName PortraitBaseMapFunction
 * @Description
 * @Date 2019/5/21 10:19
 **/
@Slf4j
public class PortraitBaseMapFunction extends RichMapFunction<String, UserInfo> {
	private static final long serialVersionUID = 4235811796843453901L;
	@Override
	public UserInfo map(String value) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(value))) {
			log.error("输入数据为空，无法转换");
			return null;
		}
		String[] data = String.valueOf(value).split(",");
		try {
			String userId = data[0];
			String userName = data[1];
			String sex = data[2];
			String telPhone = data[3];
			String email = data[4];
			String age = data[5];
			String registerTime = data[6];
			String userType = data[7];
			return new com.hrong.entity.UserInfo(Integer.valueOf(userId), userName, Integer.valueOf(sex),
					telPhone, email, Integer.valueOf(age),
					Timestamp.valueOf(registerTime), Integer.valueOf(userType));
		} catch (Exception e) {
			log.error("输入数据有误:{}", java.lang.String.valueOf(value));
		}
		return null;
	}
}
