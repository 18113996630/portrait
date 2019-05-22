package com.hrong.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author hrong
 * @ClassName ConsumerIndexDetail
 * @Description 用户消费指数详情
 * @Date 2019/5/22 12:37
 **/
public class ConsumerIndexDetail {
	private Integer userId;
	private List<Timestamp> orderTime;
	private List<Integer> intervalDays;
	private List<Double> amounts;
}
