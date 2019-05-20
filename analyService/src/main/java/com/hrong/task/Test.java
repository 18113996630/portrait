package com.hrong.task;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;

/**
 * @Author hrong
 * @ClassName Test
 * @Description
 * @Date 2019/5/20 10:40
 **/
public class Test {
	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		env.getConfig().setGlobalJobParameters(params);

		DataSource<String> text = env.readTextFile(params.get("input"));
		ReduceOperator<Object> reduce = text.flatMap(null).groupBy(0).reduce(null);
		try {
			env.execute("test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
