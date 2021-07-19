package com.gwssi;
/**
 * 系统常量
 * @author chaihw, liuhailong
 *
 */
public class AppConstants {
	
	/**
	 * 请求参数名：Controller。
	 */
	public final static String REQUEST_PARAM_NAME_CONTROLLER = "c";
	/**
	 * 请求参数名：Method。
	 */
	public final static String REQUEST_PARAM_NAME_METHOD = "m";
	

	
	
	
	
	/**
	 * SESSION KEY ： 用户ID，对应值形如LINQY1@SZAIC。
	 */
	public final static String SESSION_KEY_USER_ID = "gwssi_userId"; 
	/**
	 * SESSION KEY ： 用户姓名，对应值形如“林其友”。
	 */
	public final static String SESSION_KEY_USER_NAME = "gwssi_userName"; 
	
	/**
	 * SESSION KEY ： 用户IP，对应值形如“10.1.32.70”。
	 */
	public final static String SESSION_KEY_USER_IP = "gwssi_userIp"; 
	/**
	 * SESSION KEY ：SSO用户对象。
	 */
	public final static String SESSION_KEY_SSO_USER = "gwssi_ssoUser"; 
	
	
	
	
	/**
	 * 数据源名：应用集成。
	 */
	public final static String DATASOURCE_KEY_WCMOPTION = "sjw_wcm_option";
	
	/**
	 * 数据源名：应用集成。
	 */
	public final static String DATASOURCE_KEY_YYJC = "yyjc";
	/**
	 * 数据源名：门户新闻投稿。
	 */
	public final static String DATASOURCE_KEY_WCM = "wcm_db";
	/**
	 * 数据源名：财务待办。
	 */
	public final static String DATASOURCE_KEY_CW = "cwdaiban";
	/**
	 * 数据源名：浪潮待办（食品、人事、登记许可、特种设备等）。
	 */
	public final static String DATASOURCE_KEY_DJXK = "djxk_db";

	
	/**
	 * 数据源名：门户后台数据同步数据库。
	 */
	public final static String DATASOURCE_KEY_WCM_SYNC = "sjw_wcm1201";
	
	/***
	 * 当前页
	 */
	public final static String CURR_PAGE = "page";
	/***
	 * 页显示个数
	 */
	public final static String PAGE_SIZE = "rows";
	/***
	 * 栏目id
	 */
	public final static String DOC_CHANNEL_ID = "docChannelId";
	/***
	 * 标题
	 */
	public final static String TITLE = "title";
	/***
	 * 查询开始时间
	 */
	public final static String START_CREATE_TIME = "startCreateTime";
	/***
	 * 查询开始时间
	 */
	public final static String END_CREATE_TIME = "endCreateTime";
	/***
	 * 创建人
	 */
	public final static String CREATE_NAME = "createName";
	
}
