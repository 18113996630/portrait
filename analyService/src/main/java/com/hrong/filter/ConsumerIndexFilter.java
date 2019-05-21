package com.hrong.filter;

import com.hrong.entity.Order;
import com.hrong.utils.DateUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.hrong.constant.ParamConstant.END_TIME;
import static com.hrong.constant.ParamConstant.START_TIME;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexFilter
 * @Description
 * @Date 2019/5/21 20:47
 **/
public class ConsumerIndexFilter extends RichFilterFunction<Order> {
	private static final long serialVersionUID = 6149525453461349639L;
	private static final FastDateFormat FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	private static String startTime = DateUtil.getTimeByOffset(-1);
	private static String endTime = DateUtil.getNowTime();

	@Override
	public void open(Configuration parameters) throws Exception {
		List<Tuple2<String, String>> dateParam = getRuntimeContext().getBroadcastVariable("date_param");
		for (Tuple2<String, String> tuple2 : dateParam) {
			String f0 = tuple2.f0;
			String f1 = tuple2.f1;
			if (f0.equals(START_TIME)) {
				startTime = f1;
			}
			if (f0.equals(END_TIME)) {
				endTime = f1;
			}
		}
	}

	@Override
	public boolean filter(Order order) throws Exception {
		Timestamp createTime = order.getCreateTime();
		Date startDate = FORMAT.parse(startTime);
		Date endDate = FORMAT.parse(endTime);
		//过滤掉处于开始时间之前与结束时间之后的数据
		return startDate.before(createTime) && endDate.after(createTime);
	}

	public static void main(String[] args) {

	}
}
