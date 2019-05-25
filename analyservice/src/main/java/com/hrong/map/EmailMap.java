package com.hrong.map;

import com.hrong.constant.ConfigConstant;
import com.hrong.constant.Email;
import com.hrong.entity.EmailInfo;
import com.hrong.entity.UserInfo;
import com.hrong.utils.EmailUtil;
import com.hrong.utils.HbaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @Author hrong
 * @ClassName EmailMap
 * @Description
 * @Date 2019/5/21 15:20
 **/
@Slf4j
public class EmailMap extends RichMapFunction<String, EmailInfo> {
	private static final long serialVersionUID = -4836419379246560751L;
	private HbaseUtil hbaseUtil = HbaseUtil.getInstance();

	@Override
	public EmailInfo map(String input) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(input))) {
			log.error("输入数据为空，无法获取邮箱信息");
			return null;
		}
		String[] data = String.valueOf(input).split(",");
		EmailInfo emailInfo = null;
		try {
			String userId = data[0];
			String email = data[4];
			Email companyByEmail = EmailUtil.getRelationCompanyByEmail(email);

			hbaseUtil.insertOneRecord(ConfigConstant.HBASE_TABLE_USER_INFO, userId,
					ConfigConstant.HBASE_TABLE_COLUMN_FAMILY,
					ConfigConstant.HBASE_TABLE_COLUMN_EMAIL,
					companyByEmail.getName());

			emailInfo = new EmailInfo();
			emailInfo.setGroupField(new StringJoiner("=")
					.add(ConfigConstant.GROUP_FIELD_EMAIL_BASE)
					.add(companyByEmail.getName()).toString());
			emailInfo.setCount(1L);
			emailInfo.setEmail(companyByEmail.getName());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("输入数据有误:{}",input);
		}
		return emailInfo;
	}
}
