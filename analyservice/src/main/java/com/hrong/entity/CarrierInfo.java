package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hrong
 * @ClassName CarrierInfo
 * @Description
 * @Date 2019/5/21 14:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierInfo extends BaseEntity{
	/**
	 * 运营商
	 */
	private String carrier;
}
