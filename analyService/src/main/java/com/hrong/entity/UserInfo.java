package com.hrong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author hrong
 * @ClassName UserInfo
 * @Description
 * @Date 2019/5/21 10:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -6185084262563673840L;

	private int userId;
	private String userName;
	private int sex;
	private String telPhone;
	private String email;
	private int age;
	private Timestamp registerTime;
	private int useType;
}
