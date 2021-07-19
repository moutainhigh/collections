package com.gwssi.common.util;

import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.Config;

/**
 * Framework统一公用静态最终变量
 * 
 * @author lifx
 */
public class Constants {

    public static CacheManage cacheManage;

    /**
     * 系统工作路径
     */
    public static String ROOTPATH = Config.getRootPath();

    /**
     * classes绝对路径
     */
    public static String CONFIGPATH = ROOTPATH
                                      + "/WEB-INF/classes/";

    // config file
    public static String CONFIG_FILE = "app.properties";

    // config file
    public static String SQL_CONFIG_FILE = "sql-config.xml";
    
    /**
     * 数据规则操作描述文件路径
     */
    public static String DATA_RULE_FILE = "data-rule.xml";
    
    // config file
    public static String PACK1_CONFIG_FILE = "pack1-config.xml";
    
    // config file
    public static String PACK2_CONFIG_FILE = "pack2-config.xml";
    
    // config file
    public static String ROLE_CONFIG_FILE = "role-config.xml";

    // config file
    public static String ZBJD_CONFIG_FILE = "zbjd-config.xml";

    // config file
    public static String SSJD_CONFIG_FILE = "ssjd-config.xml";

    // config file
    public static String ZJJD_CONFIG_FILE = "zjjd-config.xml";

    // config file
    public static String JXJD_CONFIG_FILE = "jxjd-config.xml";

    // gbk config file
    public static String CONFIG_FILE_GBK = "bjais.properties.GBK";
    
    //最大查询记录数
    public static String MAX_COUNT = "maxcount";

    // 文件
    public static String file = "FILE";

    // 文件夹
    public static String folder = "FOLDER";

    // 大小
    public static String size = "SIZE";

    // file对象类型
    public static String file_type = "TYPE";

    // 文件名称
    public static String file_name = "NAME";

    // 文件映射编号pk
    public static String file_id = "ID";

    // 文件最后修改时间
    public static String file_lastModified = "LASTMODIFIED";

    // 组织机构有效状态
    public static String status_inuse = "0"; // 有效

    public static String status_offuse = "1"; // 无效

    // 系统用户ID
    public static String USER_ID = "userID";

    // 系统用户主角色ID
    public static String MAIN_ROLE_ID = "mainRoleID";

    // 系统用户所属机构ID
    public static String ORG_ID = "orgID";

    // 系统用户根机构ID
    public static String ROOT_ORG_ID = "rootID";

    // 系统用户根机构name
    public static String ROOT_ORG_NAME = "rootNAME";

    // 系统用户根机构type
    public static String ROOT_ORG_TYPE = "rootTYPE";

    // 系统根机构默认sjjgid_fk值 空值表示根节点
    public static String ROOT_SJJG_ID = "";

    // 系统用户ID
    public static String LOGIN_NAME = "username";

    // 系统用户ID
    public static String LOGIN_PASSWORD = "password";

    public static String WF_SESSIONID = "wf_sessionid";

    // 项目id
    public static String XM_PK = "xm_pk";
    public static String XM_FK = "xm_fk";
    public static String XM_MC = "xmmc";
    public static String XMCXID = "xmcxid";
    public static String XMZT = "xmzt";
    
    //weboffice版本
    public static String WEBOFFICE_VERSION = "weboffice.version";

    /**
     * velocity的临时处理目录
     */
    public static String VM_TEMP_PATH = "vm.temp.path";

    /**
     * Sql语句定义的站位字符Sx
     */
    public static String sx = "${x}";

    /**
     * Sql语句定义的站位字符x 用来替换用
     */
    public static String x = "x";

    /**
     * Sql语句定义的站位字符S0
     */
    public static String s0 = "${0}";

    /**
     * Sql语句定义的站位字符S1
     */
    public static String s1 = "${1}";

    /**
     * Sql语句定义的站位字符S1
     */
    public static String s2 = "${2}";

    /**
     * Sql语句定义的站位字符S3
     */
    public static String s3 = "${3}";

    /**
     * Sql语句定义的站位字符S4
     */
    public static String s4 = "${4}";
    
    /**
     * Sql语句定义的站位字符S5
     */
    public static String s5 = "${5}";
    
    /**
     * Sql语句定义的站位字符S6
     */
    public static String s6 = "${6}";
    
    /**
     * Sql语句定义的站位字符S7
     */
    public static String s7 = "${7}";
    
    
    /**
     * Sql语句条件起始符
     */
    public static String WHERE = " WHERE ";

    /**
     * 路径分隔符
     */
    public static final String PATH_SEPERATOR = "/";

    /**
     * 多个ID值分隔符
     */
    public static final String ID_SEPERATOR = ",";
    
    /**
     * zip格式
     */
    public static final String FORMAT_ZIP = ".zip";
    
    /**
     * zip格式
     */
    public static final String FORMAT_XML = ".xml";    
    /**
     * temp
     */
    public static final String FORMAT_TEMP = "temp"; 
    
    /**
     * 开业
     */
    public static final String ENT_STATE_OPEN = "1";
    /**
     * 吊销
     */
    public static final String ENT_STATE_DX = "2";
    /**
     * 注销
     */
    public static final String ENT_STATE_ZX = "3";
    /**
     * 迁往市外
     */
    public static final String ENT_STATE_MOVED = "4";
    
    /**
     * 业务日志
     */
    public static final String BIZLOG_NAME = "biz_log";
    public static final String VALUE_NAME = "desc";
    
    /**
     * 默认角色ID
     */   
    public static final String ROLEID = "102";
    
    /**
     * 默认角色名称
     */   
    public static final String ROLENAME = "user";    
    
    public static final Map weeks = new HashMap();
    
    static{
    	weeks.put("SUN", "日");
    	weeks.put("MON", "一");
    	weeks.put("TUE", "二");
    	weeks.put("WED", "三");
    	weeks.put("THU", "四");
    	weeks.put("FRI", "五");
    	weeks.put("SAT", "六");
    }
    
    /** 运行管理用户状态-启用 */
    public static final String RUNMGR_USER_STATE_START = "0";
    /** 运行管理用户状态-停用 */
    public static final String RUNMGR_USER_STATE_STOP = "1";
    
    /** 首页查询任务服务运行情况的时间间隔 */
    public static final float RUNNING_INFO_TIME = 30;
    
    /** 首页警报，查询当前时间前2小时数据 */
    public static final int RUNNING_ALARM_TIME = 1;
}
