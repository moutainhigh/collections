package com.gwssi.common.constant;

import cn.gwssi.common.txn.param.ParamHelp;

/**
 * 
 *     
 * 项目名称：bjgs_exchange    
 * 类名称：ConstUploadFileType    
 * 类描述：文件类型名称定义静态变量，扩展ParamHelp类
 * 	   本类维护系统内所有附件类型字段定义
 * 	   每组变量定义两个名称：一个为类型中文名称，一个为类型英文字母名称
 *     中文名称用于系统文件类别维护中唯一确定类别的字段
 *     英文字母名称用于现场相应模块打包生成相应路径 
 *     
 *        新增文件类型必须在此定义文件名称
 *    如：public static final String XINZENG = "新增";
 *       public static final String XINZENG_EN = "xinzeng";
 * 创建人：dongwn    
 * 创建时间：Mar 27, 2013 2:35:12 PM    
 * 修改人：dongwn   
 * 修改时间：Mar 27, 2013 2:35:12 PM    
 * 修改备注：    
 * @version     
 *
 */
public class ConstUploadFileType extends ParamHelp
{
    
    /**
     * 采集备案文件存放路径
     */
    public static final String COLRECORD="collect";
    public static final String COLRECORD_EN="collect_en";
    
    /**
     * 共享服务备案文件存放路径
     */
    public static final String SHARECORD="share";
    public static final String SHARECORD_EN="share_en";
    
    /**
     * 报告文件存放路径
     */
    public static final String REPORT="report";
    public static final String REPORT_EN="report_en";
    
    /**
     * 共享xml文件存放路径
     */
    public static final String SHARE_XML="share_xml";
    public static final String SHARE_XML_EN="share_xml_en";
    
    /**
     * 采集xml文件存放路径
     */
    public static final String COLLECT_XML="collect_xml";
    public static final String COLLECT_XML_EN="collect_xml_en";
    
    /**
     * 采集资源管理导入excel文件存放路径
     */
    public static final String RES_TBL="res_tbl";
    
    /**
     * 采集任务文件上传协议的采集文件存放路径
     */
    public static final String FILE_UPLOAD="file_upload";
    
    /**
     * 服务对象说明文件存放路径
     */
    public static final String SERVICE_TARGET_PATH="service_target";
}
