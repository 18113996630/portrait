package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hrong
 * @ClassName EmailInfo
 * @Description
 * @Date 2019/5/21 14:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailInfo extends BaseEntity {
	/**
	 * 邮件运营商
	 */
	private String email;
}
