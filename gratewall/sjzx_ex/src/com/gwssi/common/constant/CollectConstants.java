package com.gwssi.common.constant;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：CollectConstants 类描述： 创建时间：Mar 27, 2013 1:40:56 PM
 * 修改人：lizheng 修改时间：Mar 27, 2013 1:40:56 PM 修改备注：暂时未修改保留二期的代码结构
 * 
 * @version
 * 
 */

public class CollectConstants
{

	public static int			COUNT						= 1;

	/** 是否主键 */
	public static final String	IS_KEY						= "1";							// 是主键

	/** 数据项类型 */
	public static final String	TYPE_CHAR					= "01";						// CHAR

	public static final String	TYPE_VARCHAR2				= "02";						// VARCHAR2

	public static final String	TYPE_INT					= "03";						// INT

	public static final String	TYPE_DATE					= "04";						// DATE

	/** 参数类型 */
	public static final String	TYPE_PARM_STRING			= "01";						// STRING

	public static final String	TYPE_PARM_BOOLEAN			= "02";						// BOOLEAN

	public static final String	TYPE_PARM_INT				= "03";						// INT

	public static final String	TYPE_PARM_DOUBLE			= "04";						// DOUBLE

	public static final String	TYPE_PARM_MAP				= "05";						// MAP

	/** 表类型 */
	public static final String	TYPE_TABLE_YW				= "00";						// 业务表

	public static final String	TYPE_TABLE_DM				= "01";						// 代码表

	/** 是否代码表 */
	public static final String	IS_CODE_TABLE_Y				= "1";							// 代码表

	public static final String	IS_CODE_TABLE_N				= "0";							// 非代码表

	/** 采集来源 */
	public static final String	TYPE_CJLY_OUT				= "1";							// 外部采集库

	public static final String	TYPE_CJLY_IN				= "2";							// 内部采集库

	/** 采集来源名称 */
	public static final String	TYPE_CJLY_OUT_NAME			= "外部采集库";						// 外部采集库

	public static final String	TYPE_CJLY_IN_NAME			= "内部采集库";						// 内部采集库

	/** 采集库是否生成数据表 */
	public static final String	TYPE_IF_CREAT_YES			= "1";							// 已生成表

	public static final String	TYPE_IF_CREAT_NO			= "0";							// 未生成表

	/** 采集库是否生成数据表 */
	public static final String	TYPE_IF_CREAT_YES_NAME		= "已生成";						// 已生成表

	public static final String	TYPE_IF_CREAT_NO_NAME		= "未生成";						// 未生成表

	/** 采集类型 */
	public static final String	TYPE_CJLX_WEBSERVICE		= "00";						// webservice

	public static final String	TYPE_CJLX_FILEUPLOAD		= "01";						// 文件上传

	public static final String	TYPE_CJLX_FTP				= "02";						// ftp

	public static final String	TYPE_CJLX_DATABASE			= "03";						// 数据库

	public static final String	TYPE_CJLX_JMS				= "04";						// JMS

	public static final String	TYPE_CJLX_SOCKET			= "05";						// socket

	/** 采集类型名称 */
	public static final String	TYPE_CJLX_WEBSERVICE_NAME	= "WebService";				// webservice

	public static final String	TYPE_CJLX_FTP_NAME			= "FTP";						// ftp

	/** 采集任务表名 */
	public static final String	TYPE_WEBSERVICE_TABLE		= "collect_webservice_task";	// webservice

	// 表名

	public static final String	TYPE_FTP_TABLE				= "collect_ftp_task";			// ftp

	// 表名

	/** 启用停用标记 */
	public static final String	TYPE_QY						= "1";							// 启用

	public static final String	TYPE_TY						= "0";							// 停用

	/** 客户端参数 */
	public static final String	ClIENT_BACK_CODE			= "BACK_CODE";					// 返回值

	public static final String	CLIENT_STATE				= "CLIENT_STATE";				// 客户端状态

	public static final String	CLIENT_STATE_YES			= "Y";							// 能连接

	public static final String	CLIENT_STATE_NO				= "N";							// 不能连接

	public static final String	WSDL_URL					= "WSDL_URL";					// 访问路径

	public static final String	WSDL_IP						= "WSDL_IP";					// 访问IP

	public static final String	WSDL_PORT					= "WSDL_PORT";					// 访问端口

	public static final String	QNAME						= "QNAME";						// 方法名称

	public static String		WSDL_URL_TEST;												// 测试访问地址

	public static final String	QNAME_ERQI					= "query";						// 二期方法

	public static final String	QNAME_NEW					= "queryData";					// 新方法

	public static final String	WEB_NAME_SPACE				= "WEB_NAME_SPACE";			// 命名空间

	public static final String	PARAM_LIST					= "PARAM_LIST";				// 参数列表

	public static final String	PARAM_VALUE					= "PARAM_VALUE";				// 参数值

	public static final String	CJ_REQ_NAME					= "CJ_REQ_NAME";				// 请求队列名称

	public static final String	CJ_RES_NAME					= "CJ_RES_NAME";				// 接收队列名称

	/** 客户端参数 */

	public static final String	WSDL_TASK_URL				= "WSDL_TASK_URL";				// webservice任务访问URL

	public static final String	LOGIN_NAME					= "LOGIN_NAME";				// 登录名称

	public static final String	PASSWORD					= "PASSWORD";					// 密码

	public static final String	KSJLS						= "KSJLS";						// 开始记录数

	public static final String	JSJLS						= "JSJLS";						// 结束记录数

	public static final String	ZTS							= "ZTS";						// 总条数

	public static final String	WEBSERVICE_TASK_ID			= "WEBSERVICE_TASK_ID";		// webservice任务ID

	public static final String	METHOD_NAME					= "METHOD_NAME_EN";			// 方法名称

	public static final String	COLLECT_TABLE_ID			= "COLLECT_TABLE";				// 表ID

	public static final String	COLLECT_TABLE_NAME			= "TABLE_NAME_EN";				// 表名称

	public static final String	COLLECT_DATAITEM_NAME		= "DATAITEM_NAME_EN";			// 数据项名称

	public static final String	EXLIMP_TABLE_STATUS_Y		= "1";							// 有效

	public static final String	EXLIMP_TABLE_STATUS_N		= "0";							// 无效

	/** 采集文件内容分隔符 */
	public static final String	COLLECT_FILE_SPEARTOR		= "\\|";						// 采集文件分隔符

	public static final String	COLLECT_MODE				= "COLLECT_MODE";				// 数据采集方式

	public static final String	COLLECT_MODE_ADD			= "1";							// 数据采集方式增量

	public static final String	COLLECT_MODE_ALL			= "2";							// 数据采集方式全量

	/** 返回代码 */
	public static final String	CLIENT_COLLECT_PARAM_FHDM	= "FHDM";						// 客户端返回代码

	/** 采集数据成功 */
	public static final String	CLIENT_FHDM_SUCCESS			= "BAIC0000";					// 采集数据成功

	/** 系统错误 */
	public static final String	CLIENT_FHDM_SYS_ERROR		= "CODE0001";					// 系统错误

	/** 执行SQL语句错误 */
	public static final String	CLIENT_FHDM_SQL_ERROR		= "CODE0002";					// 执行SQL语句错误

	/** 采集表错误 */
	public static final String	CLIENT_FHDM_COL_TBL_ERROR	= "CODE0003";					// 采集表错误

	/** 采集数据项错误 */
	public static final String	CLIENT_FHDM_DATAITEM_ERROR	= "CODE0004";					// 采集数据项错误

	/** 采集任务错误 */
	public static final String	CLIENT_FHDM_TASK_ERROR		= "CODE0005";					// 采集任务错误

	/** webService服务错误 */
	public static final String	CLIENT_FHDM_WS_ERROR		= "CODE0006";					// webService服务错误

	/** webService服务URL错误 */
	public static final String	CLIENT_FHDM_WS_URL_ERROR	= "CODE0007";					// webService服务URL错误

	/** 调用服务错误 */
	public static final String	CLIENT_FHDM_INVOKE_ERROR	= "CODE0008";					// 调用服务错误

	/** 将数据转化成dom格式错误 */
	public static final String	CLIENT_FHDM_TODOM_ERROR		= "CODE0009";					// 将数据转化成dom格式错误

	/** 结果为空 */
	public static final String	CLIENT_FHDM_FAIL			= "CODE0010";					// 结果为空

	/** 采集状态 */
	public static final String	COLLECT_STATUS_NOT			= "00";						// 未采集

	public static final String	COLLECT_STATUS_ING			= "01";						// 正在采集

	public static final String	COLLECT_STATUS_SUCCESS		= "02";						// 采集成功

	public static final String	COLLECT_STATUS_FAIL			= "03";						// 采集失败

	public static final String	DATASOURCE_CJK				= "5";							// 采集库数据源key

	public static final String	DATASOURCE_GXK				= "6";							// 采集库数据源key

	public static final String	DATASOURCE_DEFAULT			= "2";							// 平台数据源key
	
	
	//字符
	public static final String	TYPE_PARAM_STYLE_01			= "01";						// 采集任务_参数格式字符串
	//XML
	public static final String	TYPE_PARAM_STYLE_02			= "02";						// 采集任务_参数格式xml
	
	//字符串
	public static final String	PARAM_STYLE_00				= "00";						// 采集任务_参数类型字符串
	
	//数字
	public static final String	PARAM_STYLE_01				= "01";						// 采集任务_参数类型数字
	
	//日期
	public static final String	PARAM_STYLE_02				= "02";						// 采集任务_参数类型日期型

	public static final String	DATE_STYLE_00				= "00";						// 采集任务_日期格式无

	public static final String	DATE_STYLE_01				= "01";						// 采集任务_日期格式yyyymmdd

	public static final String	DATE_STYLE_02				= "02";						// 采集任务_日期格式yyyy/mm/dd

	public static final String	DATE_STYLE_03				= "03";						// 采集任务_日期格式yyyy-mm-dd

	public static final String	TODAY						= "TODAY";						// 当天

	public static final String	JMS_SERVER_URL				=  "http://160.99.2.2:8089";	// JMS //http://172.30.18.50:8089

	// MQ服务器地址

	public static final String	CJ_RES_GSJ					= "CjResGsj";					// 返回队列名称

}
