package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author hrong
 * @ClassName YearBase
 * @Description 对用户所属年代的描述
 * @Date 2019/5/20 10:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearBase extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 4719102622215737005L;
	/**
	 * 年代类型
	 */
	private String yearType;
}
