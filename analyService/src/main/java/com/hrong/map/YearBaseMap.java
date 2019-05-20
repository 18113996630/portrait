package com.hrong.map;

import com.hrong.entity.YearBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

/**
 * @Author hrong
 * @ClassName YearBaseMap
 * @Description
 * @Date 2019/5/20 10:58
 **/
public class YearBaseMap extends RichMapFunction<String, YearBase> {
	@Override
	public YearBase map(String input) throws Exception {
		if (StringUtils.isBlank(input)) {
			return null;
		}
		String[] datas = input.split(",");
		String userId = datas[0];
		String userName = datas[1];
		String sex = datas[2];
		String telPhone = datas[3];
		String email = datas[4];
		String age = datas[5];
		String register_time = datas[6];
		String userType = datas[7];
		return null;
	}
}
