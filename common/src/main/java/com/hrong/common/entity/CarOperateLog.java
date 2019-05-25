package com.hrong.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author hrong
 * @ClassName CarOperateLog
 * @Description 购物车行为日志
 * @Date 2019/5/25 16:49
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarOperateLog implements Serializable {
	private static final long serialVersionUID = 6188993868496471091L;
	private Integer userId;
	private Integer productId;
	private Integer productTypeId;
	/**
	 * 操作时间
	 */
	private Timestamp operateTime;
	/**
	 * 操作类型
	 * 0 - 删除
	 * 1 - 加入购物车
	 */
	private Integer operateType;
	/**
	 * 终端类别
	 * 0 - pc
	 * 1 - 移动端
	 * 2 - 小程序
	 */
	private int terminalType;
	private String ip;
}
