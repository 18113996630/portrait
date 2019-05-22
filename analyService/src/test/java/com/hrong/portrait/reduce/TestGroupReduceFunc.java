package com.hrong.portrait.reduce;

import com.hrong.entity.ConsumerIndex;
import com.hrong.map.ConsumerIndexMap;
import com.hrong.reduce.ConsumerIndexReduce;
import com.hrong.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @Author hrong
 * @ClassName TestGroupReduceFunc
 * @Description
 * @Date 2019/5/22 17:41
 **/
@Slf4j
public class TestGroupReduceFunc {
	public static void main(String[] args){
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<String> source = env.fromElements(
				"1,1,1,1,2,100.0,100.0,0.0,0.0,2019-02-11 7:40:12,2019-02-11 8:41:12,1,1",
				"2,1,9,1,1,50.5,60.5,0.0,10.0,2019-02-12 17:02:12,2019-02-12 17:03:12,2,1",
				"3,2,2,1,3,100,100,0.0,0.0,2019-03-21 10:40:12,2019-05-21 10:41:12,1,1",
				"4,4,5,1,2,10.5,10.5,0.0,0.0,2019-02-09 17:40:12,2019-02-09 17:41:12,2,1",
				"5,5,2,1,5,68.9,68.9,0.0,0.0,2019-02-27 13:40:12,2019-02-27 13:41:12,1,1",
				"6,1,7,1,5,200,200,0.0,0.0,2019-01-02 12:40:12,2019-01-02 12:41:12,1,1",
				"7,7,5,1,4,400,400.1,0.0,0.1,2019-04-18 09:40:12,2019-04-18 09:41:12,3,1",
				"8,5,9,1,2,999,999,999,0.0,2018-02-06 17:00:12,2018-02-06 17:01:12,1,1",
				"9,9,4,1,1,10,10,0.0,0.0,2019-05-22 23:40:12,2019-05-22 23:41:12,3,1"
		);
		DataSource<Tuple2> pararmDataSource = env.fromElements(new Tuple2<>("startTime",  DateUtil.getTimeByOffset(-1)),
				new Tuple2<>("endTime",DateUtil.getNowTime()));
		pararmDataSource.printOnTaskManager("输入参数：");
		UnsortedGrouping<com.hrong.entity.Order> group = source.map(new ConsumerIndexMap())
				.withBroadcastSet(pararmDataSource, "date_param")
				.sortPartition("createTime", Order.ASCENDING)
				.groupBy("userId");
		log.error("根据userId排序完成，开始进行聚合操作");
		GroupReduceOperator<com.hrong.entity.Order, ConsumerIndex> reduceGroup = group.reduceGroup(new ConsumerIndexReduce());
		try {
			reduceGroup.print();
		} catch (Exception e) {
			log.error("reduce方法结束");
		}
	}
}
