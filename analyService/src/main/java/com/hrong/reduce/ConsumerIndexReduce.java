package com.hrong.reduce;

import com.hrong.entity.ConsumerIndex;
import com.hrong.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichGroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexReduce
 * @Description 根据已经按照id分组的数据进行reduce处理
 * @Date 2019/5/21 20:40
 **/
@Slf4j
public class ConsumerIndexReduce extends RichGroupReduceFunction<Order, ConsumerIndex> {
	private static final long serialVersionUID = 5361758375616332609L;

	@Override
	public void reduce(Iterable<Order> orders, Collector<ConsumerIndex> out) throws Exception {
		Iterator<Order> iterator = orders.iterator();
		int frequency = 0;
		while (iterator.hasNext()) {
			Order order = iterator.next();
			log.error("order:{}",order);
		}
	}
}
