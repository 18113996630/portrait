package com.hrong.data.entity;

/**
 * @Author hrong
 * @ClassName ResultVo
 * @Description
 * @Date 2019/5/25 16:03
 **/
public class ResultVo<T> {
	private int code;
	private T data;
	private String message;

	public static <T> ResultVo success(T data) {
		ResultVo result = new ResultVo();
		result.setCode(200);
		result.setMessage("success");
		result.setData(data);
		return result;
	}

	public static <T> ResultVo error(Integer code, String message) {
		ResultVo result = new ResultVo();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
