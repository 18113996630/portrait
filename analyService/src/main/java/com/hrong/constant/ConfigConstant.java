package com.hrong.constant;

/**
 * @Author hrong
 * @ClassName ConfigConstant
 * @Description
 * @Date 2019/5/21 09:52
 **/
public class ConfigConstant {
	public static final String ZK_URL = "hbase.zookeeper.quorum";
	/**
	 * hbase相关
	 */
	public static final String HBASE_DIR = "hbase.rootdir";
	public static final String HBASE_TABLE_USER_INFO = "user_info";
	public static final String HBASE_TABLE_COLUMN_FAMILY = "base_info";
	public static final String HBASE_TABLE_COLUMN_YEAR = "year";
	public static final String HBASE_TABLE_COLUMN_CARRIER = "carrier";
	public static final String HBASE_TABLE_COLUMN_EMAIL = "email";

	/**
	 * mongodb相关
	 */
	public static final String MONGO_DOCUMENT_ID = "_id";
	public static final String MONGO_TABLE_YEAR = "year_census";
	public static final String MONGO_TABLE_CARRIER = "carrier_census";
	public static final String MONGO_TABLE_EMAIL = "email_census";


	/**
	 * 分组字段
	 */
	public static final String GROUP_FIELD_YEAR_BASE = "year_type";
	public static final String GROUP_FIELD_CARRIER_BASE = "carrier_type";
	public static final String GROUP_FIELD_EMAIL_BASE = "email_type";


}
