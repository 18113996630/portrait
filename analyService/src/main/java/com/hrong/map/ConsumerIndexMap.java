package com.hrong.map;

import com.hrong.entity.ConsumerIndex;
import com.hrong.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import scala.Int;

import java.sql.Timestamp;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexMap
 * @Description
 * @Date 2019/5/21 20:26
 **/
@Slf4j
public class ConsumerIndexMap extends RichMapFunction<String, Order> {
	private static final long serialVersionUID = 7092486010802524936L;

	@Override
	public Order map(String input) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(input))) {
			log.error("输入数据为空，无法获取订单相关信息");
			return null;
		}
		String[] data = String.valueOf(input).split(",");
		Order order = null;
		try {
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
			order = new Order(id, userId, productId, productTypeId, num, totalAmount, initAmount, refundAmount, couponAmount, createTime, payTime, payType, payStatus);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("无法获取订单相关信息，输入数据有误:{},错误信息:{}", input, e.getMessage());
		}
		return order;
	}
}
