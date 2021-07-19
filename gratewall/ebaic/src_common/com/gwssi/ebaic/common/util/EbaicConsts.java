package com.gwssi.ebaic.common.util;

/**
 * 业务状态。
 * 
 * 业务状态值以码表为准，当前类只是为了写代码方便。
 * 
 * @author shigaozhan
 */
public class EbaicConsts {
    
    // 申请业务类型、
	/**
	 * 10 - 普通设立
	 */
    public static String SQYW_PTSL          = "10";    

    public static String SQYW_FLSL          = "11";    // 分立设立

    public static String SQYW_HBSL          = "12";    // 合并设立
    
    public static String SQYW_QYZGT         = "13";    // 企业转个体开业
    
    public static String SQYW_GTZQY         = "14";    // 个体转企业开业

    public static String SQYW_PTBG          = "20";    // 普通变更

    public static String SQYW_FLBG          = "21";    // 分立变更

    public static String SQYW_HBBG          = "22";    // 合并变更

    public static String SQYW_BA            = "23";    // 备案

    public static String SQYW_BGQYLX        = "24";    // 变更企业类型

    public static String SQYW_BGHZ          = "25";    // 变更+增减补换证照

    public static String SQYW_KSHZ          = "26";    // 2014版企业快速换照

    public static String SQYW_ZX            = "30";    // 注销

    public static String SQYW_ZJBH          = "40";    // 增减补换证照

    public static String SQYW_SNQR          = "50";    // 市内迁入

    public static String SQYW_SWQR          = "51";    // 市外迁入

    public static String SQYW_SNQC          = "52";    // 市内迁出

    public static String SQYW_SWQC          = "53";    // 市外迁出

    public static String SQYW_SNQRYS        = "54";    // 市内迁入预审

    public static String SQYW_SWQRYS        = "55";    // 市外迁入预审

    public static String SQYW_QCCX          = "59";    // 迁出撤消

    public static String SQYW_JTSL          = "61";    // 集团设立

    public static String SQYW_JTBG          = "62";    // 集团变更

    public static String SQYW_JTZX          = "63";    // 集团注销

    public static String SQYW_JTBA          = "64";    // 集团备案

    public static String SQYW_JTBHZ         = "65";    // 集团补换照
    
    public static String SQYW_GTSL          = "81";    // 个体设立
    
    public static String SQYW_GTBG          = "82";    // 个体变更
    
    public static String SQYW_GTZX          = "83";    // 个体注销
    
    public static String SQYW_GTBA          = "84";    // 个体备案
    
    public static String SQYW_GTBHZ         = "85";    // 个体补换照

    public static String SQYW_GTQRYS        = "86";    //个体市内迁入预审
    
    public static String SQYW_GTQR          = "87";    //个体市内迁入
    
    public static String SQYW_GTQC          = "88";    //个体市内迁出
    
    public static String SQYW_GTDLHZ        = "89";    //个体独立换照
    
    public static String SQYW_CXDJ          = "90";    // 撤消登记
    
    public static String SQYW_SJQY          = "91";    //数据迁移
    
    public static String SQYW_GQCZ          = "92";    //股权出质
    
    public static String SQYW_SJXG          = "93";    //企业数据修改
    
    public static String SQYW_GTSJXG          = "94";    //个体数据修改
    
    public static String SQYW_JTSJXG          = "95";    //集团数据修改
    
    public static String SQYW_GTCXDJ          = "96";    // 个体撤消登记
    
    public static String SQYW_JTCXDJ          = "97";    // 集团撤消登记
    
    public static String GTNX_GA            = "9720";  //个体类型_港澳
    

    // 企业登记分类
    public static String QYDJFL_YXZR        = "11";    // 有限责任公司

    public static String QYDJFL_GFYX        = "12";    // 股份有限公司

    public static String QYDJFL_JTGFHZ      = "21";    // 集体所有制（股份合作）企业

    public static String QYDJFL_JT          = "22";    // 集体所有制

    public static String QYDJFL_QM          = "23";    // 全民所有制

    public static String QYDJFL_HHQY        = "24";    // 合伙企业

    public static String QYDJFL_GRDZ        = "25";    // 个人独资企业

    public static String QYDJFL_NMZYHZS     = "26";    // 农民专业合作社

    public static String QYDJFL_YXZRFGS     = "27";    // 有限责任公司分公司

    public static String QYDJFL_JTYYDW      = "28";    // 集体所有制企业营业单位

    public static String QYDJFL_HHQYFZJG    = "41";    // 合伙企业分支机构

    public static String QYDJFL_GRDZFZJG    = "42";    // 个人独资企业分支机构

    public static String QYDJFL_NMZYHZSFZJG = "43";    // 农民专业合作社分支机构

    public static String QYDJFL_WSTZ        = "50";    // 外商投资企业

    public static String QYDJFL_WSTZFZJG    = "51";    // 外商投资企业分支机构

    public static String QYDJFL_WQCSJYHD    = "52";    // 外国（地区）企业在中国境内从事生产经营活动

    public static String QYDJFL_WQCZDDJG    = "53";    // 外国（地区）企业常驻代表机构
    
    public static String QYDJFL_WSTZHH      = "54";    // 外商投资合伙企业
    
    public static String QYDJFL_WSTZHHFZ    = "55";    // 外商投资合伙企业分支机构
    
    /**
     * 110000 - 北京局编码
     */
    public static String BJAIC              = "110000";
    /**
     * 100 - 注册号文档模板编号
     */
    public static String BHMB_ZCH           = "100";  

    public static String JTDJ_DJH           = "101";   // 集团登记号文档模板编号
    
    public static String GTBHMB_ZCH         = "102";   // 个体文档模板编号
    /**
     * 103 - 外资注册编号
     */
    public static String BHMB_WZZCH         = "103";   
    /**
     * 4 - 外资注册编号的第七位 
     */
    public static String ZCH_WZ             = "4";   
    
    public static String WZDBZH             = "wzdbjgdbzh";   // 外资代表证号
    
    public static String GQZYTZSWH          = "gqzytzs";      // 股权质押通知书文号
}