package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author hrong
 * @ClassName YearBase
 * @Description
 * @Date 2019/5/20 10:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearBase implements Serializable {
	/**
	 * 年代类型
	 */
	private String yearType;
	/**
	 * 数量
	 */
	private Long count;
}
