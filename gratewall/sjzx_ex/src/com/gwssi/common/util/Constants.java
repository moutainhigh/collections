package com.gwssi.common.util;

import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.Config;

/**
 * Frameworkͳһ���þ�̬���ձ���
 * 
 * @author lifx
 */
public class Constants {

    public static CacheManage cacheManage;

    /**
     * ϵͳ����·��
     */
    public static String ROOTPATH = Config.getRootPath();

    /**
     * classes����·��
     */
    public static String CONFIGPATH = ROOTPATH
                                      + "/WEB-INF/classes/";

    // config file
    public static String CONFIG_FILE = "app.properties";

    // config file
    public static String SQL_CONFIG_FILE = "sql-config.xml";
    
    /**
     * ���ݹ�����������ļ�·��
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
    
    //����ѯ��¼��
    public static String MAX_COUNT = "maxcount";

    // �ļ�
    public static String file = "FILE";

    // �ļ���
    public static String folder = "FOLDER";

    // ��С
    public static String size = "SIZE";

    // file��������
    public static String file_type = "TYPE";

    // �ļ�����
    public static String file_name = "NAME";

    // �ļ�ӳ����pk
    public static String file_id = "ID";

    // �ļ�����޸�ʱ��
    public static String file_lastModified = "LASTMODIFIED";

    // ��֯������Ч״̬
    public static String status_inuse = "0"; // ��Ч

    public static String status_offuse = "1"; // ��Ч

    // ϵͳ�û�ID
    public static String USER_ID = "userID";

    // ϵͳ�û�����ɫID
    public static String MAIN_ROLE_ID = "mainRoleID";

    // ϵͳ�û���������ID
    public static String ORG_ID = "orgID";

    // ϵͳ�û�������ID
    public static String ROOT_ORG_ID = "rootID";

    // ϵͳ�û�������name
    public static String ROOT_ORG_NAME = "rootNAME";

    // ϵͳ�û�������type
    public static String ROOT_ORG_TYPE = "rootTYPE";

    // ϵͳ������Ĭ��sjjgid_fkֵ ��ֵ��ʾ���ڵ�
    public static String ROOT_SJJG_ID = "";

    // ϵͳ�û�ID
    public static String LOGIN_NAME = "username";

    // ϵͳ�û�ID
    public static String LOGIN_PASSWORD = "password";

    public static String WF_SESSIONID = "wf_sessionid";

    // ��Ŀid
    public static String XM_PK = "xm_pk";
    public static String XM_FK = "xm_fk";
    public static String XM_MC = "xmmc";
    public static String XMCXID = "xmcxid";
    public static String XMZT = "xmzt";
    
    //weboffice�汾
    public static String WEBOFFICE_VERSION = "weboffice.version";

    /**
     * velocity����ʱ����Ŀ¼
     */
    public static String VM_TEMP_PATH = "vm.temp.path";

    /**
     * Sql��䶨���վλ�ַ�Sx
     */
    public static String sx = "${x}";

    /**
     * Sql��䶨���վλ�ַ�x �����滻��
     */
    public static String x = "x";

    /**
     * Sql��䶨���վλ�ַ�S0
     */
    public static String s0 = "${0}";

    /**
     * Sql��䶨���վλ�ַ�S1
     */
    public static String s1 = "${1}";

    /**
     * Sql��䶨���վλ�ַ�S1
     */
    public static String s2 = "${2}";

    /**
     * Sql��䶨���վλ�ַ�S3
     */
    public static String s3 = "${3}";

    /**
     * Sql��䶨���վλ�ַ�S4
     */
    public static String s4 = "${4}";
    
    /**
     * Sql��䶨���վλ�ַ�S5
     */
    public static String s5 = "${5}";
    
    /**
     * Sql��䶨���վλ�ַ�S6
     */
    public static String s6 = "${6}";
    
    /**
     * Sql��䶨���վλ�ַ�S7
     */
    public static String s7 = "${7}";
    
    
    /**
     * Sql���������ʼ��
     */
    public static String WHERE = " WHERE ";

    /**
     * ·���ָ���
     */
    public static final String PATH_SEPERATOR = "/";

    /**
     * ���IDֵ�ָ���
     */
    public static final String ID_SEPERATOR = ",";
    
    /**
     * zip��ʽ
     */
    public static final String FORMAT_ZIP = ".zip";
    
    /**
     * zip��ʽ
     */
    public static final String FORMAT_XML = ".xml";    
    /**
     * temp
     */
    public static final String FORMAT_TEMP = "temp"; 
    
    /**
     * ��ҵ
     */
    public static final String ENT_STATE_OPEN = "1";
    /**
     * ����
     */
    public static final String ENT_STATE_DX = "2";
    /**
     * ע��
     */
    public static final String ENT_STATE_ZX = "3";
    /**
     * Ǩ������
     */
    public static final String ENT_STATE_MOVED = "4";
    
    /**
     * ҵ����־
     */
    public static final String BIZLOG_NAME = "biz_log";
    public static final String VALUE_NAME = "desc";
    
    /**
     * Ĭ�Ͻ�ɫID
     */   
    public static final String ROLEID = "102";
    
    /**
     * Ĭ�Ͻ�ɫ����
     */   
    public static final String ROLENAME = "user";    
    
    public static final Map weeks = new HashMap();
    
    static{
    	weeks.put("SUN", "��");
    	weeks.put("MON", "һ");
    	weeks.put("TUE", "��");
    	weeks.put("WED", "��");
    	weeks.put("THU", "��");
    	weeks.put("FRI", "��");
    	weeks.put("SAT", "��");
    }
    
    /** ���й����û�״̬-���� */
    public static final String RUNMGR_USER_STATE_START = "0";
    /** ���й����û�״̬-ͣ�� */
    public static final String RUNMGR_USER_STATE_STOP = "1";
    
    /** ��ҳ��ѯ����������������ʱ���� */
    public static final float RUNNING_INFO_TIME = 30;
    
    /** ��ҳ��������ѯ��ǰʱ��ǰ2Сʱ���� */
    public static final int RUNNING_ALARM_TIME = 1;
}
