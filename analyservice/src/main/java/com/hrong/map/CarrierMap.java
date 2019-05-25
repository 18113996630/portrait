package com.hrong.map;

import com.hrong.constant.Carrier;
import com.hrong.constant.ConfigConstant;
import com.hrong.entity.CarrierInfo;
import com.hrong.utils.CarrierUtil;
import com.hrong.utils.HbaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.util.StringJoiner;

/**
 * @Author hrong
 * @ClassName CarrierMap
 * @Description
 * @Date 2019/5/21 14:21
 **/
@Slf4j
public class CarrierMap extends RichMapFunction<String, CarrierInfo> {
	private static final long serialVersionUID = -7196241259408889517L;
	private HbaseUtil hbaseUtil = HbaseUtil.getInstance();

	@Override
	public CarrierInfo map(String input) throws Exception {
		if (StringUtils.isBlank(java.lang.String.valueOf(input))) {
			log.error("输入数据为空，无法获取运营商信息");
			return null;
		}
		String[] data = input.split(",");
		CarrierInfo carrierInfo = null;
		try {
			String userId = data[0];
			String telPhone = data[3];
			//根据手机号码获取所属运营商数据
			Carrier carrier = CarrierUtil.getCarrierByTelphone(telPhone);
			hbaseUtil.insertOneRecord(ConfigConstant.HBASE_TABLE_USER_INFO, userId,
					ConfigConstant.HBASE_TABLE_COLUMN_FAMILY, ConfigConstant.HBASE_TABLE_COLUMN_CARRIER, carrier.getCarrierName());

			carrierInfo = new CarrierInfo();
			carrierInfo.setCarrier(carrier.getCarrierName());
			carrierInfo.setCount(1L);
			carrierInfo.setGroupField(new StringJoiner("=")
					.add(ConfigConstant.GROUP_FIELD_CARRIER_BASE)
					.add(carrier.getCarrierName())
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("输入数据有误:{}", input);
		}
		return carrierInfo;
	}
}
