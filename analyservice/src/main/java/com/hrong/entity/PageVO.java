package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hrong
 * @ClassName PageVO
 * @Description
 * @Date 2019/5/21 11:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
	private Integer pageNum;
	private Integer pageSize;
}
