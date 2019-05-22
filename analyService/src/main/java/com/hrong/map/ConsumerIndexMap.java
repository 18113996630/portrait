package com.hrong.map;

import com.hrong.entity.Order;
import com.hrong.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.hrong.constant.ParamConstant.END_TIME;
import static com.hrong.constant.ParamConstant.START_TIME;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexMap
 * @Description
 * @Date 2019/5/21 20:26
 **/
@Slf4j
public class ConsumerIndexMap extends RichMapFunction<String, Order> {
	private static final long serialVersionUID = 7092486010802524936L;
	private static final FastDateFormat FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	private static String startTime = null;
	private static String endTime = null;

	@Override
	public void open(Configuration parameters) throws Exception {
		if (startTime != null && endTime != null) {
			log.error("已检测到查询条件：[开始时间：{},结束时间：{}]，开始map操作",startTime,endTime);
			return;
		}
		List<Tuple2<String, String>> dateParam = getRuntimeContext().getBroadcastVariable("date_param");
		for (Tuple2<String, String> tuple2 : dateParam) {
			String f0 = tuple2.f0;
			String f1 = tuple2.f1;
			if (f0.equals(START_TIME)) {
				startTime = f1;
				log.error("获取到开始时间参数：{}",startTime);
			}
			if (f0.equals(END_TIME)) {
				endTime = f1;
				log.error("获取到结束时间参数：{}",endTime);
			}
		}
	}

	@Override
	public Order map(String input) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(input))) {
			log.error("输入数据为空，无法获取订单相关信息");
			return null;
		}
		String[] data = String.valueOf(input).split(",");
		Order order = null;
		try {
			//"1,1,1,1,2,100.0,100.0,0.0,0.0,2019-02-11 7:40:12,2019-02-11 8:41:12,1,1"
			//"2,1,9,1,1,50.5,60.5,0.0,10.0,2019-02-12 17:02:12,2019-02-12 17:03:12,2,1"
			//"3,2,2,1,3,100,100,0.0,0.0,2019-03-21 10:40:12,2019-05-21 10:41:12,1,1"
			//"4,4,5,1,2,10.5,10.5,0.0,0.0,2019-02-09 17:40:12,2019-02-09 17:41:12,2,1"
			//"5,5,2,1,5,68.9,68.9,0.0,0.0,2019-02-27 13:40:12,2019-02-27 13:41:12,1,1"
			//"6,1,7,1,5,200,200,0.0,0.0,2019-01-02 12:40:12,2019-01-02 12:41:12,1,1"
			//"7,7,5,1,4,400,400.1,0.0,0.1,2019-04-18 09:40:12,2019-04-18 09:41:12,3,1"
			//"8,5,9,1,2,999,999,999,0.0,2019-02-06 17:00:12,2019-02-06 17:01:12,1,1"
			//"9,9,4,1,1,10,10,0.0,0.0,2019-05-22 23:40:12,2019-05-22 23:41:12,3,1"
			Integer id = Integer.valueOf(data[0]);
			Integer userId = Integer.valueOf(data[1]);
			Integer productId = Integer.valueOf(data[2]);
			Integer productTypeId = Integer.valueOf(data[3]);
			Integer num = Integer.valueOf(data[4]);
			Double totalAmount = Double.valueOf(data[5]);
			Double initAmount = Double.valueOf(data[6]);
			Double refundAmount = Double.valueOf(data[7]);
			Double couponAmount = Double.valueOf(data[8]);
			Timestamp createTime = Timestamp.valueOf(data[9]);
			Timestamp payTime = Timestamp.valueOf(data[10]);
			Integer payType = Integer.valueOf(data[11]);
			Integer payStatus = Integer.valueOf(data[12]);
			Date startDate = FORMAT.parse(startTime);
			Date endDate = FORMAT.parse(endTime);
			//过滤掉处于开始时间之前与结束时间之后的数据
			boolean isRight = startDate.before(createTime) && endDate.after(createTime);
			if (isRight){
				order = new Order(id, userId, productId, productTypeId, num, totalAmount, initAmount, refundAmount, couponAmount, createTime, payTime, payType, payStatus);
				log.error("成功将输入数据转化为Order对象:{}", order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("无法获取订单相关信息，输入数据有误:{},错误信息:{}", input, e.getMessage());
		}
		return order;
	}
}
