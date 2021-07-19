package com.gwssi.common.constant;

/**
 * 计划任务调度类型参数
 * @author elvislee
 * 
 */

public class TaskSchedulingConstants
{

	public static final String	JHRWLX_YC = "04";    // 仅执行一次
	public static final String	JHRWLX_CF = "05";    // 重复执行
	public static final String	JHRWLX_MR = "01";    // 每日执行
	public static final String	JHRWLX_MZ = "02";    // 每周执行
	public static final String	JHRWLX_MY = "03";    // 每月执行
//	public static final String	JHRWLX_YC = "01";    // 仅执行一次
	
	public static final String EXLIMP_TABLE_STATUS_Y = "1";  //有效
	public static final String EXLIMP_TABLE_STATUS_N = "0";  //无效
	
	public static final String JOB_CLASS_NAME = "com.gwssi.common.task.SimpleJob";  
	
	public static final String SRV_JOB_CLASS_NAME = "com.gwssi.common.servicejob.ServiceJob";
	

	public static final String	JHRW_ZT[] = {"MON","TUE","WED","THU","FRI","SAT","SUN"};    // 每月执行
	
	public static final String	JHRW_EXP[] = {"一","二","三","四","五","六","日"};    
}
