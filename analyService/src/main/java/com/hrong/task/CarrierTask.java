package com.hrong.task;

import com.hrong.constant.ConfigConstant;
import com.hrong.entity.CarrierInfo;
import com.hrong.entity.YearBase;
import com.hrong.map.CarrierMap;
import com.hrong.map.YearBaseMap;
import com.hrong.reduce.CarrierReduce;
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
 * @ClassName CarrierTask
 * @Description
 * @Date 2019/5/21 14:20
 **/
@Slf4j
public class CarrierTask {
	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		env.getConfig().setGlobalJobParameters(params);

		DataSource<String> text = env.readTextFile(params.get("input"));

		try {
			List<CarrierInfo> carrierInfos = text.map(new CarrierMap())
					.groupBy("groupField")
					.reduce(new CarrierReduce())
					.collect();
			for (CarrierInfo carrierInfo : carrierInfos) {
				String carrier = carrierInfo.getCarrier();
				Document document = new Document("carrier", carrier);
				Document resultDoc = MongodbUtil.queryOneRecordByDocument(ConfigConstant.MONGO_TABLE_CARRIER, document);
				if (resultDoc == null) {
					MongodbUtil.insert2CollectionByModel(ConfigConstant.MONGO_TABLE_CARRIER, carrierInfo);
				} else {
					Long totalCount = (Long) resultDoc.get("count") + carrierInfo.getCount();
					resultDoc.put("count", totalCount);
					MongodbUtil.saveOrUpdateOneRecord(ConfigConstant.MONGO_TABLE_CARRIER, resultDoc);
				}
			}
			log.info("成功将{}条数据持久化至mongdb", carrierInfos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
