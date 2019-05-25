package com.hrong.reduce;

import com.hrong.entity.YearBase;
import org.apache.flink.api.common.functions.RichReduceFunction;

/**
 * @Author hrong
 * @ClassName YearBaseReduce
 * @Description
 * @Date 2019/5/21 11:06
 **/
public class YearBaseReduce extends RichReduceFunction<YearBase> {
	private static final long serialVersionUID = 6494349698393240398L;

	@Override
	public YearBase reduce(YearBase value1, YearBase value2) throws Exception {
		YearBase res = new YearBase();
		res.setYearType(value1.getYearType());
		res.setGroupField(value1.getGroupField());
		res.setCount(value1.getCount() + value2.getCount());
		return res;
	}
}
