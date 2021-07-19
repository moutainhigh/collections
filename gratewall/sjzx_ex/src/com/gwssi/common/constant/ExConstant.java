package com.gwssi.common.constant;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ExConstant 类描述：北京工商数据交换平台系统常量类 创建人：lizheng 创建时间：Mar
 * 15, 2013 2:02:14 PM 修改人：lizheng 修改时间：Mar 15, 2013 2:02:14 PM 修改备注：
 * 
 * @version
 * 
 */

public class ExConstant
{

	public static String		SERVER_SYSTEM_TYPE;

	/** 文件路径 */
	public static String		FILE_PATH_ROOT;							// 文件根目录

	public static String		SHARE_RECORD;								// 共享备案文件存放路径

	public static String		COLLECT_RECORD;							// 采集备案文件存放路径

	public static String		REPORT;									// 报告存放路径

	public static String		SHARE_XML;									// 共享xml文件存放路径

	public static String		SHARE_CONFIG;								// 共享服务配置文件存放路径

	public static String		COLLECT_XML;								// 采集xml文件存放路径

	public static String		RES_TBL_RECORD;							// 导入采集表excel文件存放路径

	public static String		FILE_UPLOAD;								// 采集任务文件上传文件存放路径

	public static String		FILE_FTP;									// 采集任务FTP文件存放路径

	public static String		FILE_DATABASE;								// 采集任务数据库日志文件存放路径

	public static String		TRS_TEMPLATE;								// 全文检索模板文件存放路径

	public static String		SERVICE_TARGET;								// 服务对象说明文件存放路径
	
	public static final String	LOG_TYPE_TEST				= "01";		// 日志type测试访问

	public static final String	LOG_TYPE_USER				= "02";		// 日志type用户访问

	/** 有效标记 */
	public static final String	IS_MARKUP_Y					= "Y";			// 有效

	public static final String	IS_MARKUP_N					= "N";			// 无效

	public static final String	IS_BIND_IP_Y				= "Y";			// 服务对象绑定IP地址

	public static final String	IS_BIND_IP_N				= "N";			// 服务对象不绑定IP地址

	public static final String	SERVICE_STATE_Y				= "Y";			// 服务为启用状态

	public static final String	SERVICE_STATE_N				= "N";			// 服务为停用状态

	public static final String	SERVICE_STATE_G				= "G";			// 服务为归档状态

	public static final String	BIND_IP_RIGHT				= "3000";		// 绑定ip和当前登陆地址一致

	public static final String	BIND_IP_WRONG				= "3001";		// 绑定ip和当前登陆地址不一致

	public static final String	SERVICE_NOT_FOUND			= "0000";		// 服务未找到

	public static final String	SERVICE_START				= "1000";		// 服务正常启动

	public static final String	SERVICE_END					= "1001";		// 服务停止

	public static final String	SERVICE_ARCHIVE				= "1002";		// 服务归档

	public static final String	SERVICE_RULE_NOTFOUND		= "1003";		// 服务未配置访问规则

	public static final String	LESS_AMOUNT_DAY				= "1004";		// 服务当天的总访问量小于规则阀值

	public static final String	OVER_AMOUNT_DAY				= "1005";		// 服务当前的总访问量超过规则阀值

	public static final String	LESS_COUNT_DAY				= "1006";		// 服务当天的总次数小于规则阀值

	public static final String	OVER_COUNT_DAY				= "1007";		// 服务当前的总次数超过规则阀值

	public static final String	SERVICE_NOT_TIME			= "1008";		// 服务不在配置的访问时间范围内

	public static final String	SERVICE_IN_TIME				= "1009";		// 服务在配置的访问时间范围

	public static final String	SERVICE_CHECK_SUCCESS		= "1100";		// 服务检查通过

	public static final String	SERVICE_CHECK_FAILED		= "1101";		// 服务检查通过

	public static final String	IS_EXCEPTION_Y				= "2000";		// 是例外日期

	public static final String	IS_EXCEPTION_N				= "2001";		// 不是例外日期

	public static final String	DATA_SOURCE_SHARE			= "gwssi_gxk";	// 共享库数据源

	public static final String	DATA_SOURCE_COLLECT			= "gwssi_cjk";	// 采集库

	public static final String	DATA_SOURCE_ZXK				= "gwssi";		// 中心库

	public static final String	DATA_SOURCE_SynchroTable	= "gwssi_cjk";	// 同步表数据库名字

	public static final String	SERVIVE_OBJECT_IN			= "数据中心";		// 内部用户
																			// 服务对象

	/** 数据源类型 */
	public static final String	TYPE_SJYLX_WEBSERVICE		= "00";		// webservice

	public static final String	TYPE_SJYLX_DATABASE			= "01";		// 数据库

	public static final String	TYPE_SJYLX_FTP				= "02";		// ftp

	public static final String	TYPE_SJYLX_JMS				= "03";		// jms

	public static final String	TYPE_SJYLX_SOCKET			= "04";

	public static String		INTERFACE_TIME;							// 共享接口查询的时分秒
}
