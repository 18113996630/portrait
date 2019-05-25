package com.hrong.reduce;

import com.hrong.constant.ParamConstant;
import com.hrong.entity.ConsumerIndex;
import com.hrong.entity.ConsumerIndexDetail;
import com.hrong.entity.Order;
import com.hrong.utils.DateUtil;
import com.hrong.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichGroupReduceFunction;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.hrong.constant.ParamConstant.AMOUNT_DIGIT;
import static com.hrong.constant.ParamConstant.WEIGHT_AVG_AMOUNT;
import static com.hrong.constant.ParamConstant.WEIGHT_MAX_AMOUNT;
import static com.hrong.constant.ParamConstant.WEIGHT_ORDER_FREQUENCY;

/**
 * @Author hrong
 * @ClassName ConsumerIndexReduce
 * @Description 根据已经按照id分组的数据进行reduce处理
 * @Date 2019/5/21 20:40
 **/
@Slf4j
public class ConsumerIndexReduce extends RichGroupReduceFunction<Order, ConsumerIndex> {
	private static final long serialVersionUID = 5361758375616332609L;

	@Override
	public void reduce(Iterable<Order> orders, Collector<ConsumerIndex> out) throws Exception {
		log.error("开始进行reduce操作》》》》》");
		Iterator<Order> iterator = orders.iterator();
		int userId = 0;
		//总的频率之和
		int frequency = 0;
		//平均下单频率
		//下单总价
		Double totalAmout = 0.0;
		Double maxAmount = 0.0;
		Timestamp beforePayTime = null;

		List<Timestamp> orderTime = new ArrayList<>(100);
		List<Integer> intervalDays = new ArrayList<>(100);
		List<Double> amounts = new ArrayList<>(100);
		int idx = 0;

		while (iterator.hasNext()) {
			Order order = iterator.next();
			Double amount = order.getTotalAmount();
			Timestamp payTime = order.getPayTime();

			totalAmout += amount;
			int interval = idx == 0 ? 0 : DateUtil.computeIntervalWith2Time(beforePayTime, payTime);
			maxAmount = amount > maxAmount ? amount : maxAmount;
			beforePayTime = payTime;
			if (userId == 0) {
				userId = order.getUserId();
			}

			orderTime.add(payTime);
			intervalDays.add(interval);
			amounts.add(amount);
			frequency += interval;
			idx++;
		}
		double avgFrequency = idx == 1 ? frequency : (double) (frequency / (idx - 1));
		double avgAmount = (double) (totalAmout / idx);
		//消费能力指数详情
		ConsumerIndexDetail detail = new ConsumerIndexDetail(userId, orderTime, intervalDays, amounts);

		avgAmount = NumberUtil.formatNumber(avgAmount, AMOUNT_DIGIT);
		double index = WEIGHT_MAX_AMOUNT * maxAmount + WEIGHT_AVG_AMOUNT * avgAmount + WEIGHT_ORDER_FREQUENCY * avgFrequency;
		ConsumerIndex consumerIndex = new ConsumerIndex(index, userId);
		log.error("detail:{}", detail);
		log.error("{}计算过程：0.3*{}+0.3*{}+0.4*{}={}", userId, maxAmount, avgAmount, avgFrequency, index);
		log.error("consumerIndex:{}", consumerIndex);
		out.collect(consumerIndex);
	}
}
