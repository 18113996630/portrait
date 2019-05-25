package com.hrong.constant;

/**
 * @author hrong
 */
public enum Email {
	/**
	 * 常见邮箱及其后缀
	 */
	WANGYI163("网易163邮箱", "@163.com"),
	MOBILE139("手机139邮箱", "@139.com"),
	SOUHU("搜狐邮箱", "@sohu.com"),
	QQ("QQ邮箱", "@qq.com"),
	TIANYI189("天翼189邮箱", "@189.com"),
	WANGYI126("网易126邮箱", "@126.com"),
	TOM("TOM邮箱", "@tom.com"),
	SINA("新浪邮箱", "@sina.com"),
	ALIYUN("阿里云邮箱", "@aliyun.com"),
	FOXMAIL("foxmail邮箱", "@foxmail.com"),
	WANGYIVIP163("网易VIP163邮箱", "@vip.163.com"),
	NONE("未知邮箱", "-"),
	;
	private String name;
	private String suffix;

	Email(String name, String suffix) {
		this.name = name;
		this.suffix = suffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return "Email{" +
				"name='" + name + '\'' +
				", suffix='" + suffix + '\'' +
				'}';
	}
}
