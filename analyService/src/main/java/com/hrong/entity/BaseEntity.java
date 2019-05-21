package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hrong
 * @ClassName BaseEntity
 * @Description
 * @Date 2019/5/21 10:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
	/**
	 * 数量
	 */
	private Long count;
	/**
	 * 分组字段
	 */
	private String groupField;
}
