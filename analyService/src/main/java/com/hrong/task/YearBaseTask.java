package com.hrong.task;

import com.hrong.constant.ConfigConstant;
import com.hrong.entity.YearBase;
import com.hrong.map.YearBaseMap;
import com.hrong.reduce.YearBaseReduce;
import com.hrong.utils.MongodbUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.Collections;
import java.util.List;

/**
 * @Author hrong
 * @ClassName YearBaseTask
 * @Description
 * @Date 2019/5/20 10:40
 **/
@Slf4j
public class YearBaseTask {
	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		env.getConfig().setGlobalJobParameters(params);

		DataSource<String> text = env.readTextFile(params.get("input"));
		try {
			List<YearBase> yearType = text.map(new YearBaseMap())
					.groupBy("groupField")
					.reduce(new YearBaseReduce())
					.collect();
			for (YearBase yearBase : yearType) {
				String type = yearBase.getYearType();
				Document document = new Document("type", type);
				Document resultDoc = MongodbUtil.queryOneRecordByDocument(ConfigConstant.MONGO_TABLE_YEAR, document);
				if (resultDoc == null) {
					MongodbUtil.insert2CollectionByModel(ConfigConstant.MONGO_TABLE_YEAR, yearBase);
				} else {
					Long totalCount = (Long) resultDoc.get("count") + yearBase.getCount();
					resultDoc.put("count", totalCount);
					MongodbUtil.saveOrUpdateOneRecord(ConfigConstant.MONGO_TABLE_YEAR, resultDoc);
				}
			}
			log.error("成功将{}条数据持久化至mongdb", yearType.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
