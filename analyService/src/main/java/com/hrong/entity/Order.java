package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author huangrong
 * @ClassName Order
 * @Description
 * @Date 2019/5/21 20:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -1101587454628643462L;

	private Integer id;
	private Integer userId;
	private Integer productId;
	private Integer productTypeId;

	/**
	 * 库存
	 */
	private Integer num;

	/**
	 * 合计金额
	 */
	private Double totalAmount;

	/**
	 * 初始金额
	 */
	private Double initAmount;

	/**
	 * 退款金额
	 */
	private Double refundAmount;

	/**
	 * 优惠券金额
	 */
	private Double couponAmount;
	private Timestamp createTime;
	private Timestamp payTime;
	private Integer payType;

	/**
	 * 0：未付款
	 * 1：已付款
	 * 2：已退款
	 */
	private Integer payStatus;


}
