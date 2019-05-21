package com.hrong.task;

import com.hrong.constant.ConfigConstant;
import com.hrong.entity.CarrierInfo;
import com.hrong.entity.EmailInfo;
import com.hrong.map.CarrierMap;
import com.hrong.map.EmailMap;
import com.hrong.reduce.CarrierReduce;
import com.hrong.reduce.EmailReduce;
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
 * @ClassName EmailTask
 * @Description
 * @Date 2019/5/21 15:42
 **/
@Slf4j
public class EmailTask {
	public static void main(String[] args) {
		final ParameterTool params = ParameterTool.fromArgs(args);
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		env.getConfig().setGlobalJobParameters(params);

		DataSource<String> text = env.readTextFile(params.get("input"));

		try {
			List<EmailInfo> emailInfos = text.map(new EmailMap())
					.groupBy("groupField")
					.reduce(new EmailReduce())
					.collect();
			for (EmailInfo emailInfo : emailInfos) {
				String email = emailInfo.getEmail();
				Document document = new Document("email", email);
				Document resultDoc = MongodbUtil.queryOneRecordByDocument(ConfigConstant.MONGO_TABLE_EMAIL, document);
				if (resultDoc == null) {
					MongodbUtil.insert2CollectionByModel(ConfigConstant.MONGO_TABLE_EMAIL, email);
				} else {
					Long totalCount = (Long) resultDoc.get("count") + emailInfo.getCount();
					resultDoc.put("count", totalCount);
					MongodbUtil.saveOrUpdateOneRecord(ConfigConstant.MONGO_TABLE_EMAIL, resultDoc);
				}
			}
			log.info("成功将{}条数据持久化至mongdb", emailInfos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
