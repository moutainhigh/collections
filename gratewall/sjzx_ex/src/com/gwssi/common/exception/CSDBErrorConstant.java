package com.gwssi.common.exception;

import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;

public class CSDBErrorConstant extends ErrorConstant{
    
    public static String ERRORCODE; 
    public static String ERRORVALUE;
    private static Map ERRORVALUE_MAP = new HashMap();
    
    public static String CSDB_CODE_ERR_VARIFY = "000001:数据校验错误";
    public static String CSDB_CODE_ERR_FILE = "000002:文件读写错误";
    public static String CSDB_CODE_ERR_NETINTERRUPT = "000003:系统网络连接中断";
    public static String CSDB_CODE_ERR_SYS = "000008:系统错误";
    public static String CSDB_CODE_ERR_OTHER = "000009:系统异常";

    public static String ERR_SYS = "系统暂时无法处理本次操作，请稍候重试，或者与您的系统管理员联系。";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_MODULE = "本次操作失败，请重试一次，或者与您的系统管理员联系。";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_OTHER = "本次操作发生未知的错误，请重试一次，或者与您的系统管理员联系。";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_FILE = "本次操作的文件发生错误，请重试一次，或者与您的系统管理员联系。";//CSDBConfig.get("error.constant.syserr");
    public static String ERR_VARIFY = "本次操作的数据无法处理，请重试一次，或者与您的系统管理员联系。";
    public static String ERR_NOLOGIN = "用户已经超时，请重新登录系统。";
    public static String ERR_NOUSERNAME = "用户名不存在，请重新输入正确的用户名。如果忘记用户名请与您的系统管理员联系。";
    public static String ERR_PASSWORD = "输入的密码错误，请重新输入。";
    public static String ERR_BIZY = "系统忙，请稍候重试。";
    public static String ERR_NOPERFUME = "您没有开通本操作的权限，请于您的系统管理员联系。";
    public static String ERR_NETINTERRUPT ="系统网络连接失败，请检查您的网络连接，或者与您的系统管理员联系。"; 
    
    public static Map getErrorValue(){
        
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_VARIFY, ERR_VARIFY); //校验错误
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_FILE, ERR_FILE); //文件读写错误
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_NETINTERRUPT, ERR_NETINTERRUPT); //网络中断
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_SYS, ERR_SYS); //其他错误
        ERRORVALUE_MAP.put(CSDB_CODE_ERR_OTHER, ERR_OTHER); //其他错误
        
//        ERRORVALUE_MAP.put(TXN_REENTRY_ERROR, ERR_MODULE); //交易不允许重入
//        ERRORVALUE_MAP.put(TXN_AUTH_ERROR, ERR_NOPERFUME); //交易受权检查未通过
        ERRORVALUE_MAP.put(TXN_LOAD_PARAM_ERROR, ERR_MODULE); //下载列表参数时错误
//        ERRORVALUE_MAP.put(TXN_NOT_LOGIN, ERR_NOLOGIN); //用户还没有签到
        ERRORVALUE_MAP.put(TASK_UNKNOW_CMD, ERR_SYS); //未知的流程处理命令
//        ERRORVALUE_MAP.put(TXN_ROOTPATH_ERROR, ERR_SYS); //作为WAR包发布的应用,不能获取根目录
        ERRORVALUE_MAP.put(TXN_EXEC_ERROR, ERR_MODULE); //执行命令时错误
        
        ERRORVALUE_MAP.put(FILE_NOTEXIST, ERR_FILE); //文件不存在
        ERRORVALUE_MAP.put(FILE_READERROR, ERR_FILE); //读取文件错误
        ERRORVALUE_MAP.put(FILE_WRITEERROR, ERR_FILE); //写文件错误
        ERRORVALUE_MAP.put(FILE_ISNULL, ERR_FILE); //文件名称是空
        ERRORVALUE_MAP.put(FILE_DOWNLOAD_ERROR, ERR_FILE); //下载文件错误
        ERRORVALUE_MAP.put(FILE_UPLOAD_ERROR, ERR_FILE); //取上传文件错误
        ERRORVALUE_MAP.put(FILE_DELETE_ERROR, ERR_FILE); //删除文件时错误
        ERRORVALUE_MAP.put(FILE_CREATE_ERROR, ERR_FILE); //创建文件时错误
        ERRORVALUE_MAP.put(FILE_CLOSE_ERROR, ERR_FILE); //读取文件错误
        ERRORVALUE_MAP.put(FILE_ALREADY_EXIST, ERR_FILE); //文件已经存在
        ERRORVALUE_MAP.put(FILE_CREATEPATH_ERROR, ERR_FILE); //创建目录时错误
        ERRORVALUE_MAP.put(FILE_RENAME_ERROR, ERR_FILE); //修改文件名称时错误
        ERRORVALUE_MAP.put(FILE_CREATE_EXCEL_ERROR, ERR_FILE); //生成EXCEL文档时错误
        
        ERRORVALUE_MAP.put(LOAD_CONFIGERROR, ERR_SYS); //加载系统配置参数时错误
        ERRORVALUE_MAP.put(LOAD_DBCONF_ERROR, ERR_SYS); //加载数据表配置文件时错误
        ERRORVALUE_MAP.put(DAO_CONFIG_DUPLICATE, ERR_SYS); //配置信息重复
        ERRORVALUE_MAP.put(FORM_NOTFOUND, ERR_SYS); //没有找到FORM的定义信息
        ERRORVALUE_MAP.put(FORM_DUPLICATE, ERR_SYS); //FORM重复
        ERRORVALUE_MAP.put(ACTION_DUPLICATE, ERR_SYS); //交易码重复
        
        ERRORVALUE_MAP.put(DATABUS_NOT_INIT, ERR_VARIFY); //数据总线还没有初始化
        ERRORVALUE_MAP.put(DATABUS_NAME_ISNULL, ERR_VARIFY); //数据项名称为空
        ERRORVALUE_MAP.put(DATABUS_VALUE_ISNULL, ERR_VARIFY); //数据项内容为空
        ERRORVALUE_MAP.put(DATABUS_DATA_NOTFOUND, ERR_VARIFY); //数据项不存在
        ERRORVALUE_MAP.put(DATABUS_KEY_ISNULL, ERR_VARIFY); //关键字内容为空
        ERRORVALUE_MAP.put(DATABUS_FORMAT_ERROR, ERR_VARIFY); //数据格式错误
        ERRORVALUE_MAP.put(DATABUS_INVALID_DATATYPE, ERR_VARIFY); //不支持的数据类型
        ERRORVALUE_MAP.put(DATABUS_NOT_DETAIL, ERR_VARIFY); //没有明细数据
        ERRORVALUE_MAP.put(DATABUS_OUTOFBOUNDS, ERR_VARIFY); //记录越界       
        ERRORVALUE_MAP.put(PROPERTY_NOTEXIST, ERR_VARIFY); //属性不存在
        
        ERRORVALUE_MAP.put(CHARSET_ERROR, ERR_VARIFY); //不支持此编码类型
        ERRORVALUE_MAP.put(KEY_DATA_ERROR, ERR_VARIFY); //密钥数据错误
        ERRORVALUE_MAP.put(KEY_PADDING_ERROR, ERR_VARIFY); //数据块长度或填充信息错误
        ERRORVALUE_MAP.put(NO_DES_ALGORITHM, ERR_VARIFY); //不支持DES算法
        ERRORVALUE_MAP.put(NO_MD5_ALGORITHM, ERR_VARIFY); //不支持MD5算法
        ERRORVALUE_MAP.put(ENCRYPT_ERROR, ERR_VARIFY); //加密数据时错误
        ERRORVALUE_MAP.put(DECRYPT_ERROR, ERR_VARIFY); //解密数据时错误
        ERRORVALUE_MAP.put(GET_SIGN_ERROR, ERR_VARIFY); //生成签名时错误
        ERRORVALUE_MAP.put(CHECK_SIGN_ERROR, ERR_VARIFY); //数据已经被修改

//        ERRORVALUE_MAP.put(ACTION_TXN_INVALID, ERR_SYS); //请求信息中没有包含交易码
//        ERRORVALUE_MAP.put(ACTION_TXN_NOTFOUND, ERR_SYS); //找不到交易配置信息
//        ERRORVALUE_MAP.put(ACTION_CONF_ERROR, ERR_SYS); //系统参数没有初始化
//        ERRORVALUE_MAP.put(ACTION_NO_DATA, ERR_MODULE); //没有输入FORM数据      
//        
//        ERRORVALUE_MAP.put(ACTION_LOGIN_TIMEOUT, ERR_NOLOGIN); //用户已经超时
//        ERRORVALUE_MAP.put(ACTION_USERNAME_ERROR, ERR_NOUSERNAME); //用户名不存在
//        ERRORVALUE_MAP.put(ACTION_PASSWORD_ERROR, ERR_PASSWORD); //口令错误
//        ERRORVALUE_MAP.put(ACTION_SESSION_ACTIVE, "用户正在操作"); //用户正在操作
//        ERRORVALUE_MAP.put(ACTION_LOGIN_DISABLE, "用户已经被禁止"); //用户已经被禁止
//        ERRORVALUE_MAP.put(ACTION_ADDCODE_ERROR, "附加码校验错误"); //附加码校验错误
//        ERRORVALUE_MAP.put(ACTION_ROLE_INVALID, "用户角色不存在"); //用户角色不存在
//        ERRORVALUE_MAP.put(ACTION_ROLE_DISABLE, "用户角色已经被禁止"); //用户角色已经被禁止

        ERRORVALUE_MAP.put(VALID_VALUE_ISNULL, ERR_VARIFY); //数据项为空
        ERRORVALUE_MAP.put(VALID_VALUE_TOLONG, ERR_VARIFY); //数据项太长
        ERRORVALUE_MAP.put(VALID_FORMAT_ERROR, ERR_VARIFY); //数据内容格式错误
        ERRORVALUE_MAP.put(CONF_NOT_FOUND, ERR_SYS); //配置信息没有找到
        ERRORVALUE_MAP.put(MENU_NOT_INIT, "ERR_BIZY"); //菜单文件没有初始化
        ERRORVALUE_MAP.put(MENU_NODE_NOT_FOUND, ERR_SYS); //菜单代码没有指定
        
        ERRORVALUE_MAP.put(TXN_LOAD_PROXY_ERROR, ERR_SYS); //加载交易代理时错误
        ERRORVALUE_MAP.put(EJB_NOTFOUND, ERR_SYS); //找不到EJB容器
        ERRORVALUE_MAP.put(EJB_CONNECT_ERROR, ERR_SYS); //连接EJB容器时错误
        ERRORVALUE_MAP.put(EJB_CALL_ERROR, ERR_SYS); //调用EJB服务时错误
        ERRORVALUE_MAP.put(EJB_REGISTER_ERROR, ERR_SYS); //注册EJB服务时错误
        ERRORVALUE_MAP.put(BEAN_NOTFOUND, ERR_SYS); //业务组件不存在
        ERRORVALUE_MAP.put(BEAN_CALL_ERROR, ERR_SYS); //调用业务组件时错误
        ERRORVALUE_MAP.put(BEAN_LOAD_ERROR, ERR_SYS); //加载业务组件时错误
        ERRORVALUE_MAP.put(BEAN_TYPE_ERROR, ERR_SYS); //业务组件类型错误        
        ERRORVALUE_MAP.put(LOG_MONITOR_ERROR, ERR_SYS); //注册监控信息时错误
        ERRORVALUE_MAP.put(BEAN_LOADMODULE_ERROR, ERR_SYS); //加载服务模块时错误
        ERRORVALUE_MAP.put(BEAN_LOADCLASS_ERROR, ERR_SYS); //加载类时错误
        
//        ERRORVALUE_MAP.put(SQL_SELECT_NOROW, ERR_MODULE); //没有找到记录
//        ERRORVALUE_MAP.put(SQL_POOL_NOTFOUND, ERR_SYS); //数据库连接池不存在
//        ERRORVALUE_MAP.put(SQL_TRANSACTION_ERROR, ERR_SYS); //数据库事务错误
//        ERRORVALUE_MAP.put(SQL_GETCONN_ERROR, ERR_SYS); //取数据库连接错误
//        ERRORVALUE_MAP.put(SQL_NO_CONNECT, ERR_SYS); //数据库连接不存在
//        ERRORVALUE_MAP.put(SQL_STATEMENT_ERROR, ERR_MODULE); //获取SQL表达式时错误
//        ERRORVALUE_MAP.put(SQL_GETCLAUSE_ERROR, ERR_MODULE); //获取SQL语句时错误
//        ERRORVALUE_MAP.put(SQL_EXECUTE_ERROR, ERR_MODULE); //执行SQL语句时错误
//        ERRORVALUE_MAP.put(SQL_CLOSE_ERROR, ERR_SYS); //关闭数据库连接时错误        
//        ERRORVALUE_MAP.put(SQL_FREE_ERROR, ERR_SYS); //释放数据库资源时错误
//    
//        ERRORVALUE_MAP.put(SQL_SELECT_ERROR, ERR_MODULE); //执行查询语句返回的结果集错误
//        ERRORVALUE_MAP.put(SQL_DATA_INVALID, ERR_VARIFY); //输入数据错误
//        ERRORVALUE_MAP.put(SQL_VALUEFROM_ERROR, ERR_VARIFY); //不支持的数据来源方式
//        ERRORVALUE_MAP.put(SQL_DATA_NOTFOUND, ERR_VARIFY); //没有找到数据项
//        ERRORVALUE_MAP.put(SQL_NO_WHERECLAUSE, ERR_MODULE); //没有生成条件子句
//        ERRORVALUE_MAP.put(SQL_DUPLICATE, ERR_VARIFY); //增加数据时错误:记录重复
//        ERRORVALUE_MAP.put(SQL_DATA_TOOLONG, ERR_VARIFY); //数据项的内容太大
//        ERRORVALUE_MAP.put(SQL_READ_BLOB_ERROR, ERR_VARIFY); //获取BLOB字段时错误
//        ERRORVALUE_MAP.put(SQL_SETVALUE_ERROR, ERR_VARIFY); //设置数据时错误     
//        ERRORVALUE_MAP.put(SQL_GETVALUE_ERROR, ERR_MODULE); //从记录中取数据时错误
//        ERRORVALUE_MAP.put(SQL_NOTNULL_ERROR, ERR_MODULE); //非空字段没有赋值
//       
//        ERRORVALUE_MAP.put(SQL_CONFIG_ERROR, ERR_SYS); //数据表配置信息错误
//        ERRORVALUE_MAP.put(SQL_FUNC_NOTFOUND, ERR_SYS); //函数的配置信息没有找到
//        ERRORVALUE_MAP.put(SQL_PARAMETER_ERROR, ERR_SYS); //设置SQL的参数时错误
//        ERRORVALUE_MAP.put(JDBC_CONFIG_ERROR, ERR_SYS); //JDBC连接的配置信息错误
//        ERRORVALUE_MAP.put(DB_TABLE_NOTFOUND, ERR_SYS); //数据表不存在
//        ERRORVALUE_MAP.put(DB_FUNC_NOTFOUND, ERR_SYS); //没有找到数据库操作函数
//        ERRORVALUE_MAP.put(DB_CODETYPE_NOTFOUND, ERR_SYS); //没有找到代码名称
//        ERRORVALUE_MAP.put(DB_DATABASE_NOTFOUND, ERR_SYS); //数据库配置信息不存在
        
        ERRORVALUE_MAP.put(JAVA_METHOD_NOTFOUND, ERR_SYS); //类中没有找到该方法
        ERRORVALUE_MAP.put(JAVA_CLASS_NOTFOUND, ERR_SYS); //类没有定义
        ERRORVALUE_MAP.put(JAVA_CLASS_NOTINSTANCE, ERR_SYS); //类不能被实例化
        ERRORVALUE_MAP.put(JAVA_ACCESS_LIMIT, ERR_NOPERFUME); //没有存取权限
        ERRORVALUE_MAP.put(JAVA_INVOCATE_EXCEPTION, ERR_SYS); //调用目标异常
        ERRORVALUE_MAP.put(JAVA_THREAD_INTERRUPTED, ERR_SYS); //JAVA线程被中断
        ERRORVALUE_MAP.put(JAVA_OTHER_ERROR, ERR_SYS); //调用JAVA服务时异常错误
        ERRORVALUE_MAP.put(JAVA_COMPILE_EXCEPTION, ERR_SYS); //编译组件时错误
        ERRORVALUE_MAP.put(JAVA_PARSER_ERROR, ERR_SYS); //解析JAVA源文件时错误    

        ERRORVALUE_MAP.put(SERIAL_BEAN_NOTFOUND, ERR_SYS); //没有找到序列号的函数
        ERRORVALUE_MAP.put(SERIAL_KEYCOLUMN_ISNULL, ERR_SYS); //没有指定分类字段名称
        
//        ERRORVALUE_MAP.put(LAYOUT_NOTFOUND, ERR_SYS); //布局模板不存在

        ERRORVALUE_MAP.put(XML_PARSER_ERROR, ERR_SYS); //解析XML文档时错误
        ERRORVALUE_MAP.put(DTD_PARSER_ERROR, ERR_SYS); //解析DTD文档时错误
        ERRORVALUE_MAP.put(XML_ELEMENT_NOFOUND, ERR_SYS); //XML文件中节点不存在
        ERRORVALUE_MAP.put(XML_KEYNAME_NOFOUND, ERR_SYS); //查找节点记录时，没有指定关键字字段
        ERRORVALUE_MAP.put(XML_OUTPUT_NOFOUND, ERR_SYS); //没有指定输出数据的节点   

        ERRORVALUE_MAP.put(TXN_OTHER_ERROR, ERR_OTHER); //未知的错误  
         
        return ERRORVALUE_MAP;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
  
        Map map = CSDBErrorConstant.getErrorValue();
        System.out.println(map.get("000001:数据校验错误"));
    }

}
