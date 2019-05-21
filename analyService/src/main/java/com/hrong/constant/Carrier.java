package com.hrong.constant;

/**
 * @Author hrong
 * @ClassName Carrier
 * @Description
 * @Date 2019/5/21 14:29
 **/
public enum  Carrier {
	/**
	 * 中国移动，值为1
	 */
	CHINA_TELECOM("中国移动", 1),
	/**
	 * 中国联通，值为2
	 */
	CHINA_UNICOM("中国联通", 2),
	/**
	 * 中国电信，值为3
	 */
	CHINA_MOBILE("中国电信", 3),
	/**
	 * 其他运营商
	 */
	NONE("未知运营商",0);

	private String carrierName;
	private int value;

	Carrier(String carrierName, int value) {
		this.carrierName = carrierName;
		this.value = value;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
