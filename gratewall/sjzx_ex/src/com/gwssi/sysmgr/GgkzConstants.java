package com.gwssi.sysmgr;

import com.gwssi.common.util.Constants;

/**
*公共控制子系统全局变量
*@author lifx
*/
public class GgkzConstants extends Constants
{
	// 单个用户信息DataBus节点名，用于登陆用户使用
	public static final String USER_INFO = "user-info";
	
	// 单个机构信息DataBus节点名，用于登陆用户使用
	public static final String ORG_INFO = "org-info"; 
	
	// 根机构信息DataBus节点名，用于登陆用户使用
	public static final String ROOT_ORG_INFO = "root-info";
    
    // 系统组织机构用户表
    public static final String XT_ZZJG_YH = "xt_zzjg_yh";

    // 系统组织机构机构表
    public static final String XT_ZZJG_JG = "xt_zzjg_jg";
    
    //错误提示信息
    public static final String LOG_FINDXX = "hxyw.jhgl.jhbz.findXx";//查询用于修改出错提示信息
    
    //审计署指标版本是当前版本
    public static final String ZBBB_CURR = "01";
    
    //审计署指标版本不是当前版本
    public static final String ZBBB_NOTCURR = "02";
    
    //统计指标有效状态
    public static final String TJZB_YX = "01";
    
    //统计指标无效状态
    public static final String TJZB_WX = "02";
    
    //统计指标是方案引用状态
    public static final String TJZB_YY_YES = "01";
    
    //统计指标否方案引用状态
    public static final String TJZB_YY_NO = "02";
    
    //统计指标是叶子节点状态
    public static final String TJZB_YJD_YES = "01";
    
    //统计指标否叶子节点状态
    public static final String TJZB_YJD_NO = "02";
    
}
