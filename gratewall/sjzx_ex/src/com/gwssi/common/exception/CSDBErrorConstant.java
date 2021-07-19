package com.gwssi.common.exception;

import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;

public class CSDBErrorConstant extends ErrorConstant{
    
    public static String ERRORCODE; 
    public static String ERRORVALUE;
    private static Map ERRORVALUE_MAP = new HashMap();
    
    public static String CSDB_CODE_ERR_VARIFY = "000001:����У�����";
    public static String CSDB_CODE_ERR_FILE = "000002:�ļ���д����";
    public static String CSDB_CODE_ERR_NETINTERRUPT = "000003:ϵͳ���������ж�";
    public static String CSDB_CODE_ERR_SYS = "000008:ϵͳ����";
    public static String CSDB_CODE_ERR_OTHER = "000009:ϵͳ�쳣";

    public static String ERR_SYS = "ϵͳ��ʱ�޷������β��������Ժ����ԣ�����������ϵͳ����Ա��ϵ��";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_MODULE = "���β���ʧ�ܣ�������һ�Σ�����������ϵͳ����Ա��ϵ��";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_OTHER = "���β�������δ֪�Ĵ���������һ�Σ�����������ϵͳ����Ա��ϵ��";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_FILE = "���β������ļ���������������һ�Σ�����������ϵͳ����Ա��ϵ��";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_VARIFY = "���β����������޷�����������һ�Σ�����������ϵͳ����Ա��ϵ��";
    public static String ERR_NOLOGIN = "�û��Ѿ���ʱ�������µ�¼ϵͳ��";
    public static String ERR_NOUSERNAME = "�û��������ڣ�������������ȷ���û�������������û�����������ϵͳ����Ա��ϵ��";
    public static String ERR_PASSWORD = "���������������������롣";
    public static String ERR_BIZY = "ϵͳæ�����Ժ����ԡ�";
    public static String ERR_NOPERFUME = "��û�п�ͨ��������Ȩ�ޣ���������ϵͳ����Ա��ϵ��";
    public static String ERR_NETINTERRUPT ="ϵͳ��������ʧ�ܣ����������������ӣ�����������ϵͳ����Ա��ϵ��"; 
    
    public static Map getErrorValue(){
        
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_VARIFY, ERR_VARIFY); //У�����
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_FILE, ERR_FILE); //�ļ���д����
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_NETINTERRUPT, ERR_NETINTERRUPT); //�����ж�
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_SYS, ERR_SYS); //��������
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_OTHER, ERR_OTHER); //��������
        
//        ERRORVALUE_MAP.put(TXN_REENTRY_ERROR, ERR_MODULE); //���ײ���������
//        ERRORVALUE_MAP.put(TXN_AUTH_ERROR, ERR_NOPERFUME); //������Ȩ���δͨ��
        ERRORVALUE_MAP.put(TXN_LOAD_PARAM_ERROR, ERR_MODULE); //�����б����ʱ����
//        ERRORVALUE_MAP.put(TXN_NOT_LOGIN, ERR_NOLOGIN); //�û���û��ǩ��
        ERRORVALUE_MAP.put(TASK_UNKNOW_CMD, ERR_SYS); //δ֪�����̴�������
//        ERRORVALUE_MAP.put(TXN_ROOTPATH_ERROR, ERR_SYS); //��ΪWAR��������Ӧ��,���ܻ�ȡ��Ŀ¼
        ERRORVALUE_MAP.put(TXN_EXEC_ERROR, ERR_MODULE); //ִ������ʱ����
        
        ERRORVALUE_MAP.put(FILE_NOTEXIST, ERR_FILE); //�ļ�������
        ERRORVALUE_MAP.put(FILE_READERROR, ERR_FILE); //��ȡ�ļ�����
        ERRORVALUE_MAP.put(FILE_WRITEERROR, ERR_FILE); //д�ļ�����
        ERRORVALUE_MAP.put(FILE_ISNULL, ERR_FILE); //�ļ������ǿ�
        ERRORVALUE_MAP.put(FILE_DOWNLOAD_ERROR, ERR_FILE); //�����ļ�����
        ERRORVALUE_MAP.put(FILE_UPLOAD_ERROR, ERR_FILE); //ȡ�ϴ��ļ�����
        ERRORVALUE_MAP.put(FILE_DELETE_ERROR, ERR_FILE); //ɾ���ļ�ʱ����
        ERRORVALUE_MAP.put(FILE_CREATE_ERROR, ERR_FILE); //�����ļ�ʱ����
        ERRORVALUE_MAP.put(FILE_CLOSE_ERROR, ERR_FILE); //��ȡ�ļ�����
        ERRORVALUE_MAP.put(FILE_ALREADY_EXIST, ERR_FILE); //�ļ��Ѿ�����
        ERRORVALUE_MAP.put(FILE_CREATEPATH_ERROR, ERR_FILE); //����Ŀ¼ʱ����
        ERRORVALUE_MAP.put(FILE_RENAME_ERROR, ERR_FILE); //�޸��ļ�����ʱ����
        ERRORVALUE_MAP.put(FILE_CREATE_EXCEL_ERROR, ERR_FILE); //����EXCEL�ĵ�ʱ����
        
        ERRORVALUE_MAP.put(LOAD_CONFIGERROR, ERR_SYS); //����ϵͳ���ò���ʱ����
        ERRORVALUE_MAP.put(LOAD_DBCONF_ERROR, ERR_SYS); //�������ݱ������ļ�ʱ����
        ERRORVALUE_MAP.put(DAO_CONFIG_DUPLICATE, ERR_SYS); //������Ϣ�ظ�
        ERRORVALUE_MAP.put(FORM_NOTFOUND, ERR_SYS); //û���ҵ�FORM�Ķ�����Ϣ
        ERRORVALUE_MAP.put(FORM_DUPLICATE, ERR_SYS); //FORM�ظ�
        ERRORVALUE_MAP.put(ACTION_DUPLICATE, ERR_SYS); //�������ظ�
        
        ERRORVALUE_MAP.put(DATABUS_NOT_INIT, ERR_VARIFY); //�������߻�û�г�ʼ��
        ERRORVALUE_MAP.put(DATABUS_NAME_ISNULL, ERR_VARIFY); //����������Ϊ��
        ERRORVALUE_MAP.put(DATABUS_VALUE_ISNULL, ERR_VARIFY); //����������Ϊ��
        ERRORVALUE_MAP.put(DATABUS_DATA_NOTFOUND, ERR_VARIFY); //���������
        ERRORVALUE_MAP.put(DATABUS_KEY_ISNULL, ERR_VARIFY); //�ؼ�������Ϊ��
        ERRORVALUE_MAP.put(DATABUS_FORMAT_ERROR, ERR_VARIFY); //���ݸ�ʽ����
        ERRORVALUE_MAP.put(DATABUS_INVALID_DATATYPE, ERR_VARIFY); //��֧�ֵ���������
        ERRORVALUE_MAP.put(DATABUS_NOT_DETAIL, ERR_VARIFY); //û����ϸ����
        ERRORVALUE_MAP.put(DATABUS_OUTOFBOUNDS, ERR_VARIFY); //��¼Խ��       
        ERRORVALUE_MAP.put(PROPERTY_NOTEXIST, ERR_VARIFY); //���Բ�����
        
        ERRORVALUE_MAP.put(CHARSET_ERROR, ERR_VARIFY); //��֧�ִ˱�������
        ERRORVALUE_MAP.put(KEY_DATA_ERROR, ERR_VARIFY); //��Կ���ݴ���
        ERRORVALUE_MAP.put(KEY_PADDING_ERROR, ERR_VARIFY); //���ݿ鳤�Ȼ������Ϣ����
        ERRORVALUE_MAP.put(NO_DES_ALGORITHM, ERR_VARIFY); //��֧��DES�㷨
        ERRORVALUE_MAP.put(NO_MD5_ALGORITHM, ERR_VARIFY); //��֧��MD5�㷨
        ERRORVALUE_MAP.put(ENCRYPT_ERROR, ERR_VARIFY); //��������ʱ����
        ERRORVALUE_MAP.put(DECRYPT_ERROR, ERR_VARIFY); //��������ʱ����
        ERRORVALUE_MAP.put(GET_SIGN_ERROR, ERR_VARIFY); //����ǩ��ʱ����
        ERRORVALUE_MAP.put(CHECK_SIGN_ERROR, ERR_VARIFY); //�����Ѿ����޸�

//        ERRORVALUE_MAP.put(ACTION_TXN_INVALID, ERR_SYS); //������Ϣ��û�а���������
//        ERRORVALUE_MAP.put(ACTION_TXN_NOTFOUND, ERR_SYS); //�Ҳ�������������Ϣ
//        ERRORVALUE_MAP.put(ACTION_CONF_ERROR, ERR_SYS); //ϵͳ����û�г�ʼ��
//        ERRORVALUE_MAP.put(ACTION_NO_DATA, ERR_MODULE); //û������FORM����      
//        
//        ERRORVALUE_MAP.put(ACTION_LOGIN_TIMEOUT, ERR_NOLOGIN); //�û��Ѿ���ʱ
//        ERRORVALUE_MAP.put(ACTION_USERNAME_ERROR, ERR_NOUSERNAME); //�û���������
//        ERRORVALUE_MAP.put(ACTION_PASSWORD_ERROR, ERR_PASSWORD); //�������
//        ERRORVALUE_MAP.put(ACTION_SESSION_ACTIVE, "�û����ڲ���"); //�û����ڲ���
//        ERRORVALUE_MAP.put(ACTION_LOGIN_DISABLE, "�û��Ѿ�����ֹ"); //�û��Ѿ�����ֹ
//        ERRORVALUE_MAP.put(ACTION_ADDCODE_ERROR, "������У�����"); //������У�����
//        ERRORVALUE_MAP.put(ACTION_ROLE_INVALID, "�û���ɫ������"); //�û���ɫ������
//        ERRORVALUE_MAP.put(ACTION_ROLE_DISABLE, "�û���ɫ�Ѿ�����ֹ"); //�û���ɫ�Ѿ�����ֹ

        ERRORVALUE_MAP.put(VALID_VALUE_ISNULL, ERR_VARIFY); //������Ϊ��
        ERRORVALUE_MAP.put(VALID_VALUE_TOLONG, ERR_VARIFY); //������̫��
        ERRORVALUE_MAP.put(VALID_FORMAT_ERROR, ERR_VARIFY); //�������ݸ�ʽ����
        ERRORVALUE_MAP.put(CONF_NOT_FOUND, ERR_SYS); //������Ϣû���ҵ�
        ERRORVALUE_MAP.put(MENU_NOT_INIT, "ERR_BIZY"); //�˵��ļ�û�г�ʼ��
        ERRORVALUE_MAP.put(MENU_NODE_NOT_FOUND, ERR_SYS); //�˵�����û��ָ��
        
        ERRORVALUE_MAP.put(TXN_LOAD_PROXY_ERROR, ERR_SYS); //���ؽ��״���ʱ����
        ERRORVALUE_MAP.put(EJB_NOTFOUND, ERR_SYS); //�Ҳ���EJB����
        ERRORVALUE_MAP.put(EJB_CONNECT_ERROR, ERR_SYS); //����EJB����ʱ����
        ERRORVALUE_MAP.put(EJB_CALL_ERROR, ERR_SYS); //����EJB����ʱ����
        ERRORVALUE_MAP.put(EJB_REGISTER_ERROR, ERR_SYS); //ע��EJB����ʱ����
        ERRORVALUE_MAP.put(BEAN_NOTFOUND, ERR_SYS); //ҵ�����������
        ERRORVALUE_MAP.put(BEAN_CALL_ERROR, ERR_SYS); //����ҵ�����ʱ����
        ERRORVALUE_MAP.put(BEAN_LOAD_ERROR, ERR_SYS); //����ҵ�����ʱ����
        ERRORVALUE_MAP.put(BEAN_TYPE_ERROR, ERR_SYS); //ҵ��������ʹ���        
        ERRORVALUE_MAP.put(LOG_MONITOR_ERROR, ERR_SYS); //ע������Ϣʱ����
        ERRORVALUE_MAP.put(BEAN_LOADMODULE_ERROR, ERR_SYS); //���ط���ģ��ʱ����
        ERRORVALUE_MAP.put(BEAN_LOADCLASS_ERROR, ERR_SYS); //������ʱ����
        
//        ERRORVALUE_MAP.put(SQL_SELECT_NOROW, ERR_MODULE); //û���ҵ���¼
//        ERRORVALUE_MAP.put(SQL_POOL_NOTFOUND, ERR_SYS); //���ݿ����ӳز�����
//        ERRORVALUE_MAP.put(SQL_TRANSACTION_ERROR, ERR_SYS); //���ݿ��������
//        ERRORVALUE_MAP.put(SQL_GETCONN_ERROR, ERR_SYS); //ȡ���ݿ����Ӵ���
//        ERRORVALUE_MAP.put(SQL_NO_CONNECT, ERR_SYS); //���ݿ����Ӳ�����
//        ERRORVALUE_MAP.put(SQL_STATEMENT_ERROR, ERR_MODULE); //��ȡSQL���ʽʱ����
//        ERRORVALUE_MAP.put(SQL_GETCLAUSE_ERROR, ERR_MODULE); //��ȡSQL���ʱ����
//        ERRORVALUE_MAP.put(SQL_EXECUTE_ERROR, ERR_MODULE); //ִ��SQL���ʱ����
//        ERRORVALUE_MAP.put(SQL_CLOSE_ERROR, ERR_SYS); //�ر����ݿ�����ʱ����        
//        ERRORVALUE_MAP.put(SQL_FREE_ERROR, ERR_SYS); //�ͷ����ݿ���Դʱ����
//    
//        ERRORVALUE_MAP.put(SQL_SELECT_ERROR, ERR_MODULE); //ִ�в�ѯ��䷵�صĽ��������
//        ERRORVALUE_MAP.put(SQL_DATA_INVALID, ERR_VARIFY); //�������ݴ���
//        ERRORVALUE_MAP.put(SQL_VALUEFROM_ERROR, ERR_VARIFY); //��֧�ֵ�������Դ��ʽ
//        ERRORVALUE_MAP.put(SQL_DATA_NOTFOUND, ERR_VARIFY); //û���ҵ�������
//        ERRORVALUE_MAP.put(SQL_NO_WHERECLAUSE, ERR_MODULE); //û�����������Ӿ�
//        ERRORVALUE_MAP.put(SQL_DUPLICATE, ERR_VARIFY); //��������ʱ����:��¼�ظ�
//        ERRORVALUE_MAP.put(SQL_DATA_TOOLONG, ERR_VARIFY); //�����������̫��
//        ERRORVALUE_MAP.put(SQL_READ_BLOB_ERROR, ERR_VARIFY); //��ȡBLOB�ֶ�ʱ����
//        ERRORVALUE_MAP.put(SQL_SETVALUE_ERROR, ERR_VARIFY); //��������ʱ����     
//        ERRORVALUE_MAP.put(SQL_GETVALUE_ERROR, ERR_MODULE); //�Ӽ�¼��ȡ����ʱ����
//        ERRORVALUE_MAP.put(SQL_NOTNULL_ERROR, ERR_MODULE); //�ǿ��ֶ�û�и�ֵ
//       
//        ERRORVALUE_MAP.put(SQL_CONFIG_ERROR, ERR_SYS); //���ݱ�������Ϣ����
//        ERRORVALUE_MAP.put(SQL_FUNC_NOTFOUND, ERR_SYS); //������������Ϣû���ҵ�
//        ERRORVALUE_MAP.put(SQL_PARAMETER_ERROR, ERR_SYS); //����SQL�Ĳ���ʱ����
//        ERRORVALUE_MAP.put(JDBC_CONFIG_ERROR, ERR_SYS); //JDBC���ӵ�������Ϣ����
//        ERRORVALUE_MAP.put(DB_TABLE_NOTFOUND, ERR_SYS); //���ݱ�����
//        ERRORVALUE_MAP.put(DB_FUNC_NOTFOUND, ERR_SYS); //û���ҵ����ݿ��������
//        ERRORVALUE_MAP.put(DB_CODETYPE_NOTFOUND, ERR_SYS); //û���ҵ���������
//        ERRORVALUE_MAP.put(DB_DATABASE_NOTFOUND, ERR_SYS); //���ݿ�������Ϣ������
        
        ERRORVALUE_MAP.put(JAVA_METHOD_NOTFOUND, ERR_SYS); //����û���ҵ��÷���
        ERRORVALUE_MAP.put(JAVA_CLASS_NOTFOUND, ERR_SYS); //��û�ж���
        ERRORVALUE_MAP.put(JAVA_CLASS_NOTINSTANCE, ERR_SYS); //�಻�ܱ�ʵ����
        ERRORVALUE_MAP.put(JAVA_ACCESS_LIMIT, ERR_NOPERFUME); //û�д�ȡȨ��
        ERRORVALUE_MAP.put(JAVA_INVOCATE_EXCEPTION, ERR_SYS); //����Ŀ���쳣
        ERRORVALUE_MAP.put(JAVA_THREAD_INTERRUPTED, ERR_SYS); //JAVA�̱߳��ж�
        ERRORVALUE_MAP.put(JAVA_OTHER_ERROR, ERR_SYS); //����JAVA����ʱ�쳣����
        ERRORVALUE_MAP.put(JAVA_COMPILE_EXCEPTION, ERR_SYS); //�������ʱ����
        ERRORVALUE_MAP.put(JAVA_PARSER_ERROR, ERR_SYS); //����JAVAԴ�ļ�ʱ����    

        ERRORVALUE_MAP.put(SERIAL_BEAN_NOTFOUND, ERR_SYS); //û���ҵ����кŵĺ���
        ERRORVALUE_MAP.put(SERIAL_KEYCOLUMN_ISNULL, ERR_SYS); //û��ָ�������ֶ�����
        
//        ERRORVALUE_MAP.put(LAYOUT_NOTFOUND, ERR_SYS); //����ģ�岻����

        ERRORVALUE_MAP.put(XML_PARSER_ERROR, ERR_SYS); //����XML�ĵ�ʱ����
        ERRORVALUE_MAP.put(DTD_PARSER_ERROR, ERR_SYS); //����DTD�ĵ�ʱ����
        ERRORVALUE_MAP.put(XML_ELEMENT_NOFOUND, ERR_SYS); //XML�ļ��нڵ㲻����
        ERRORVALUE_MAP.put(XML_KEYNAME_NOFOUND, ERR_SYS); //���ҽڵ��¼ʱ��û��ָ���ؼ����ֶ�
        ERRORVALUE_MAP.put(XML_OUTPUT_NOFOUND, ERR_SYS); //û��ָ��������ݵĽڵ�   

        ERRORVALUE_MAP.put(TXN_OTHER_ERROR, ERR_OTHER); //δ֪�Ĵ���  
         
        return ERRORVALUE_MAP;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
  
        Map map = CSDBErrorConstant.getErrorValue();
        System.out.println(map.get("000001:����У�����"));
    }

}
