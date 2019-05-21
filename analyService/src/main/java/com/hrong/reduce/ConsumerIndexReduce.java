package com.hrong.reduce;

import com.hrong.entity.ConsumerIndex;
import com.hrong.entity.Order;
import org.apache.flink.api.common.functions.RichGroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexReduce
 * @Description
 * @Date 2019/5/21 20:40
 **/
public class ConsumerIndexReduce extends RichGroupReduceFunction<Order, ConsumerIndex> {
	private static final long serialVersionUID = 5361758375616332609L;

	@Override
	public void reduce(Iterable<Order> orders, Collector<ConsumerIndex> out) throws Exception {
		Iterator<Order> iterator = orders.iterator();
		while (iterator.hasNext()) {
			iterator.next();
		}
	}
}
