package com.hrong.reduce;

import com.hrong.entity.CarrierInfo;
import org.apache.flink.api.common.functions.RichReduceFunction;

/**
 * @Author hrong
 * @ClassName CarrierReduce
 * @Description
 * @Date 2019/5/21 14:45
 **/
public class CarrierReduce extends RichReduceFunction<CarrierInfo> {
	private static final long serialVersionUID = 2878892950795157250L;

	@Override
	public CarrierInfo reduce(CarrierInfo value1, CarrierInfo value2) throws Exception {
		CarrierInfo res = new CarrierInfo();
		res.setCarrier(value1.getCarrier());
		res.setGroupField(value1.getGroupField());
		res.setCount(value1.getCount() + value2.getCount());
		return res;
	}
}
