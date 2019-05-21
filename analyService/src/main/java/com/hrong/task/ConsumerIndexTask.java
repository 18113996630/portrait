package com.hrong.task;

import com.hrong.filter.ConsumerIndexFilter;
import com.hrong.map.ConsumerIndexMap;
import com.hrong.reduce.ConsumerIndexReduce;
import com.hrong.utils.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.utils.ParameterTool;
import scala.Tuple2;
import scala.annotation.meta.param;

import java.util.Map;

/**
 * @Author huangrong
 * @ClassName ConsumerIndexTask
 * @Description 消费能力指数计算任务
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
				.filter(new ConsumerIndexFilter())
				.withBroadcastSet(pararmDataSource, "date_param")
				.groupBy("id")
				.reduceGroup(new ConsumerIndexReduce());
	}
}
