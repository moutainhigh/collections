package com.gwssi.common.constant;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ShareConstants 类描述：webservice接口公共参数 创建人：lizheng
 * 创建时间：Mar 27, 2013 1:40:56 PM 修改人：lizheng 修改时间：Mar 27, 2013 1:40:56 PM
 * 修改备注：暂时未修改保留二期的代码结构
 * 
 * @version
 * 
 */

public class ShareConstants
{
	/** 自定义服务规定包结构 */
	public static final String	SELF_SERVICE_PACKAGE				= "com.gwssi.dw.runmgr.services.impl.";

	/** 多个共享字段分隔符 */
	public static final String	SERVICE_COLUMN_SEPARATOR			= ",";

	/** 相同字段有多个查询条件时，值的分隔符 */
	public static final String	MULTI_COLUMN_CONDITION_SEPARATOR	= ",";

	/** 字段查询条件为IN时，值的分隔符 */
	public static final String	MULTI_COLUMN_IN_SEPARATOR			= "\\|";

	/** 内部用户 */
	public static final int		USER_TYPE_INNER						= 0;

	/** 外部用户 */
	public static final int		USER_TYPE_OUTTER					= 1;

	/** 共享服务种类：一般服务 */
	public static final String	SERVICE_TYPE_GENERAL				= "一般服务";

	/** 共享服务种类：自定义服务 */
	public static final String	SERVICE_TYPE_SELF					= "自定义服务";

	/** web service（主要针对地税用户）的日期格式 */
	public static final String	WS_QUERY_DATE_FORMAT				= "yyyyMMdd";

	/** 我方数据库中存储的日期格式 */
	public static final String	DB_DATE_FORMAT						= "yyyy-MM-dd";

	/** 企业状态：开业 */
	public static final int		SERVICE_QY_STATE_OPEN				= 12;

	/** 企业状态：注销 */
	public static final int		SERVICE_QY_STATE_ZX					= 13;

	/** 企业状态：吊销 */
	public static final int		SERVICE_QY_STATE_DX					= 14;

	/** 企业状态：变更 */
	public static final int		SERVICE_QY_STATE_BG					= 15;

	/** 字段类型：数字型 */
	public static final String	SERVICE_COLUMN_TYPE_NUMBER			= "N";

	/** 字段类型：日期 */
	public static final String	SERVICE_COLUMN_TYPE_DATE			= "T";

	/** */
	public static final String	COLUMN_TYPE_DATE					= "2";

	/** 默认每次访问开始数 */
	public static final int		SERVICE_DEFAULT_STAR_RECORDS		= 1;

	/** 默认每次访问最大数 */
	public static final int		SERVICE_DEFAULT_MAX_RECORDS			= 2000;

	// 客户端调用的必须参数声明
	/** 登录名 */
	public static final String	SERVICE_IN_PARAM_LOGIN_NAME			= "LOGIN_NAME";

	/** 登录密码 */
	public static final String	SERVICE_IN_PARAM_LOGIN_PASSWORD		= "PASSWORD";

	/** 最大记录数 */
	public static final String	SERVICE_IN_PARAM_MAX_RECORDS		= "MAX_RECORDS";

	/** 服务代码 */
	public static final String	SERVICE_IN_PARAM_SERVICE_CODE		= "SVR_CODE";

	/** 开始 */
	public static final String	SERVICE_IN_PARAM_START_PARAM		= "KS_";

	/** 结束 */
	public static final String	SERVICE_IN_PARAM_END_PARAM			= "JS_";

	/** webservice客户端提供的日期格式 */
	public static final String	SERVICE_IN_PARAM_DATE_FORMAT		= "yyyyMMdd";

	/** 服务最终执行的SQL */
	public static final String	SERVICE_QUERY_SQL					= "QUERY_SQL";

	/** 共享服务最大查询天数限制 */
	public static final long	SERVICE_IN_PARAM_MAX_QUERY_DATE		= 7
																			* 24
																			* 60
																			* 60
																			* 1000;

	// 客户端调用的必须参数声明

	// 服务返回的参数名称
	/** 服务是否能被使用 */
	public static final String	SERVICE_CAN_BE_USED					= "IS_USED";

	/** 返回代码 */
	public static final String	SERVICE_OUT_PARAM_FHDM				= "FHDM";

	/** 开始记录数 */
	public static final String	SERVICE_OUT_PARAM_KSJLS				= "KSJLS";

	/** 结束记录数 */
	public static final String	SERVICE_OUT_PARAM_JSJLS				= "JSJLS";

	/** 查询得到的总记录数 */
	public static final String	SERVICE_OUT_PARAM_ZTS				= "ZTS";

	/** RN */
	public static final String	SERVICE_OUT_RN						= "RN";

	/** ETL_TIMESTAMP */
	public static final String	SERVICE_OUT_ETL_TIMESTAMP			= "ETL_TIMESTAMP";

	/** 记录列表在Map中的key值 */
	public static final String	SERVICE_OUT_PARAM_ARRAY				= "GSDJ_INFO_ARRAY";

	/** 总条数数据项名 */
	public static final String	SERVICE_OUT_TOTALS					= "totals";

	// 服务返回的参数名称

	// 服务返回代码声明
	/** 超过最大记录数限制 */
	public static final String	SERVICE_FHDM_OVER_MAX				= "BAIC0020";

	/** 成功返回 */
	public static final String	SERVICE_FHDM_SUCCESS				= "BAIC0000";

	/** 无符合条件的记录 */
	public static final String	SERVICE_FHDM_NO_RESULT				= "BAIC0010";

	/** 用户提供的参数错误 */
	public static final String	SERVICE_FHDM_INPUT_PARAM_ERROR		= "BAIC0030";

	/** 超过最大日期查询限制 */
	public static final String	SERVICE_FHDM_OVER_DATE_RANGE		= "BAIC0040";

	/** 系统错误 */
	public static final String	SERVICE_FHDM_SYSTEM_ERROR			= "BAIC0050";

	/** 连接测试返回代码 */
	public static final String	SERVICE_FHDM_LJ_QUERY				= "BAIC0060";

	/** 登录失败 */
	public static final String	SERVICE_FHDM_LOGIN_FAIL				= "BAIC0070";

	/** 用户名检查失败 */
	public static final String	SERVICE_FHDM_USER_ERROR				= "BAIC0071";

	/** 密码检查失败 */
	public static final String	SERVICE_FHDM_PWD_ERROR				= "BAIC0072";

	/** 用户无效 */
	public static final String	SERVICE_FHDM_USER_FAIL				= "BAIC0073";
	
	/** 访问时间不在本月范围内 */
	public static final String	SERVICE_FHDM_OVER_MONTH				= "BAIC0074";
	
	/** 查询超过最大天数设置 */
	public static final String	SERVICE_FHDM_OVER_DATE				= "BAIC0075";

	/** 未知错误 */
	public static final String	SERVICE_FHDM_UNKNOWN_ERROR			= "BAIC9999";

	/** 服务已暂停 */
	public static final String	SERVICE_FHDM_SERVICE_PAUSE			= "BAIC0200";

	/** 用户IP错误 */
	public static final String	SERVICE_FHDM_ERROR_IP				= "BAIC0101";

	/** 用户某个服务的访问权限信息错误 */
	public static final String	SERVICE_FHDM_ERROR_LIMIT			= "BAIC0100";

	/** 当日用户某个服务访问受限 */
	public static final String	SERVICE_FHDM_LOCK_WEEK				= "BAIC0102";

	/** 当日用户某个服务访问时间受限 */
	public static final String	SERVICE_FHDM_LOCK_TIME				= "BAIC0103";

	/** 当日用户某个服务访问次数受限 */
	public static final String	SERVICE_FHDM_LOCK_NUMBER			= "BAIC0104";

	/** 当日用户某个服务访问数据总条数受限 */
	public static final String	SERVICE_FHDM_LOCK_TOTAL				= "BAIC0105";
	
	/** 当日访问条数受限 */
	public static final String	SERVICE_FHDM_LOCK_TIME_COUNT		= "BAIC0106";

	/** 当日用户某个服务访问已加锁 */
	public static final String	SERVICE_FHDM_LOCKED_TODAY			= "BAIC0109";

	/** 服务未找到 */
	public static final String	SERVICE_FHDM_SERVICE_NOT_FOUND		= "BAIC0110";

	/** 单项检查通过 */
	public static final String	SERVICE_FHDM_SERVICE_PASS			= "BAIC0111";

	/** 查询SQL错误 */
	public static final String	SERVICE_FHDM_SQL_ERROR				= "BAIC2020";
	
	/** 验证失败 */
	public static final String	SERVICE_FHDM_VERIFY_ERROR			= "BAIC2030";
	// 服务返回代码声明
	
	//获取接口测试数据文件
	public static final String GET_INTERFACE_DATA_FAIL = "00";
	public static final String GET_INTERFACE_DATA_SUCCESS = "01";
	
	public static final String LIMIT_MONTH_DATA_YES = "Y";
	public static final String LIMIT_MONTH_DATA_NO = "N";
}
