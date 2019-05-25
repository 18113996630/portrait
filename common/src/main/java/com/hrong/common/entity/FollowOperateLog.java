package com.hrong.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author hrong
 * @ClassName FollowOperateLog
 * @Description 关注商品行为日志
 * @Date 2019/5/25 16:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowOperateLog implements Serializable {
	private static final long serialVersionUID = 8483632487806972360L;
	private Integer userId;
	private Integer productId;
	private Integer productTypeId;
	/**
	 * 操作时间
	 */
	private Timestamp operateTime;
	/**
	 * 操作类型
	 * 0 - 取消
	 * 1 - 收藏
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
