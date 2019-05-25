package com.hrong.map;

import com.hrong.constant.ConfigConstant;
import com.hrong.entity.YearBase;
import com.hrong.utils.DateUtil;
import com.hrong.utils.HbaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.util.StringJoiner;

/**
 * @Author hrong
 * @ClassName YearBaseMap
 * @Description
 * @Date 2019/5/20 10:58
 **/
@Slf4j
public class YearBaseMap extends RichMapFunction<String, YearBase> {
	private static final long serialVersionUID = -2994417317685605063L;
	private HbaseUtil hbaseUtil = HbaseUtil.getInstance();

	@Override
	public YearBase map(String input) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(input))) {
			log.error("输入数据为空，无法获取用户年龄相关信息");
			return null;
		}
		String[] data = input.split(",");
		YearBase yearBase = null;
		try {
			String userId = data[0];
			String age = data[5];
			String yearType = DateUtil.transformAge2Year(age);
			//将计算好的userId-yearType保存到hbase
			hbaseUtil.insertOneRecord(ConfigConstant.HBASE_TABLE_USER_INFO, userId,
					ConfigConstant.HBASE_TABLE_COLUMN_FAMILY, ConfigConstant.HBASE_TABLE_COLUMN_YEAR, yearType);

			yearBase = new YearBase();
			yearBase.setCount(1L);
			yearBase.setYearType(yearType);
			yearBase.setGroupField(new StringJoiner("=")
					.add(ConfigConstant.GROUP_FIELD_YEAR_BASE)
					.add(yearType)
					.toString());
		} catch (Exception e) {
			log.error("输入数据有误:{}", input);
		}
		return yearBase;
	}
}
