package com.hrong.reduce;

import com.hrong.entity.EmailInfo;
import org.apache.flink.api.common.functions.RichReduceFunction;

/**
 * @Author hrong
 * @ClassName EmailReduce
 * @Description
 * @Date 2019/5/21 15:41
 **/
public class EmailReduce extends RichReduceFunction<EmailInfo> {
	private static final long serialVersionUID = 8370740960992471436L;

	@Override
	public EmailInfo reduce(EmailInfo value1, EmailInfo value2) throws Exception {
		EmailInfo res = new EmailInfo();
		res.setEmail(value1.getEmail());
		res.setGroupField(value1.getGroupField());
		res.setCount(value1.getCount() + value2.getCount());
		return res;
	}
}
