package com.gwssi.application.common;

/**
 * 系统常量类
 */
public class AppConstants {

	/**
	 * 是否有效--表示该条数据是否有效用作逻辑删除
	 */
	public final static String EFFECTIVE_Y = "Y"; // 有效
	public final static String EFFECTIVE_N = "N"; // 无效

	/**
	 * 系统运行状态--表示该系统是否启用
	 */
	public final static String SYSTEM_STATE_ON = "0"; // 启用
	public final static String SYSTEM_STATE_OFF = "1"; // 停止

	/**
	 * 角色类型--表示该角色类型
	 */
	public final static String ROLE_TYPE_SUPER = "00"; // 超级管理员
	public final static String ROLE_TYPE_SYS = "01"; // 系统管理员
	public final static String ROLE_TYPE_DEFAULT = "02"; // 普通用户

	/**
	 * 角色状态--表示该角色是否启用
	 */
	public final static String ROLE_STATE_ON = "0"; // 启用
	public final static String ROLE_STATE_OFF = "1"; // 停止

	/**
	 * 功能状态--表示该功能是否启用
	 */
	public final static String FUNC_STATE_ON = "0"; // 启用
	public final static String FUNC_STATE_OFF = "1"; // 停止

	/**
	 * 功能类型--表示该功能是功能包还是功能
	 */
	public final static String FUNC_TYPE_0 = "0"; // 首页或公共页面
	public final static String FUNC_TYPE_1 = "1"; // 功能模块
	public final static String FUNC_TYPE_2 = "2"; // 功能
	
	/**
	 * 服务类型--表示该服务是同步还是异步
	 */
	public final static String SERVICES_TYPE_SYNC = "0"; // 同步
	public final static String SERVICES_TYPE_ASYNC = "1"; // 异步	
	
	/**
	 * 提醒类型--表示该提醒是哪种类型
	 */
	public final static String REMIND_TYPE_1 = "01"; // 分钟
	public final static String REMIND_TYPE_2 = "02"; // 小时	
	public final static String REMIND_TYPE_3 = "03"; // 天
}
