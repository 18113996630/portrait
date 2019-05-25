package com.hrong.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author hrong
 * @ClassName ScanOperateLog
 * @Description 浏览商品日志实体
 * @Date 2019/5/25 16:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanOperateLog implements Serializable {

	private static final long serialVersionUID = 1210456249314972232L;

	private Integer userId;
	private Integer productId;
	private Integer productTypeId;
	/**
	 * 浏览时间
	 */
	private Timestamp scanTime;
	/**
	 * 停留时间
	 */
	private String stayTime;
	/**
	 * 终端类别
	 * 0 - pc
	 * 1 - 移动端
	 * 2 - 小程序
	 */
	private int terminalType;
	private String ip;

}
