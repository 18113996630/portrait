package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hrong
 * @ClassName ConsumerIndex
 * @Description
 * @Date 2019/5/21 20:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerIndex extends BaseEntity {
	/**
	 * 消费能力指数 0.3*订单均价+0.3*订单最高价+0.4*下单频率
	 */
	private Double index;
	private Integer userId;
}
