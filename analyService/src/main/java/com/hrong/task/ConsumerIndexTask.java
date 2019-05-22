package com.hrong.task;

import com.hrong.map.ConsumerIndexMap;
import com.hrong.reduce.ConsumerIndexReduce;
import com.hrong.utils.DateUtil;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.utils.ParameterTool;
import scala.Tuple2;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexTask
 * @Description 消费能力指数计算任务
 * 先将不符合时间条件的过滤掉,将数据map为Order类型，
 * 根据下单时间进行排序
 * 根据根据userId进行分组
 * 取出订单最大值，计算平均值和下单频率
 * 下单频率=两次下单之间的时间间隔
 * @Date 2019/5/21 20:25
 **/
public class ConsumerIndexTask {
	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		env.getConfig().setGlobalJobParameters(params);
		DataSource<String> text = env.readTextFile(params.get("input"));


		DataSource<Tuple2> pararmDataSource = env.fromElements(new Tuple2<>("startTime", DateUtil.getNowTime()),
				new Tuple2<>("endTime", DateUtil.getTimeByOffset(-1)));
		text.map(new ConsumerIndexMap())
				.withBroadcastSet(pararmDataSource, "date_param")
				.sortPartition("createTime", Order.ASCENDING)
				.groupBy("userId");
//				.reduceGroup(new ConsumerIndexReduce());
	}
}
