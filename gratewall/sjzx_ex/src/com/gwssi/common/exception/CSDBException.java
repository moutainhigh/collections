package com.gwssi.common.exception;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.exception.TxnWarnException;
import cn.gwssi.common.component.logger.TxnLogger;

/**
 * Exception扩展类 基本
 * 
 * @author lifx
 */

// Referenced classes of package com.heer.framework.exception:
// AppErrorResource, NoSuchErrorException
public class CSDBException extends TxnException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger log = TxnLogger.getLogger(CSDBException.class
            .getName());

    private String key = "";

    private String dispatcherUrl = "";

    public CSDBException(String errCode) {
        super(errCode);
    }

    public CSDBException(String key, String message) {
        super(key, message);
        this.key = key;
    }

    public CSDBException(String key, String message, String url) {
        super(key, message);
        this.key = key;
        dispatcherUrl = url;
    }

    public CSDBException(String key, Throwable e) {
        super(key, e);
    }

    public CSDBException(Throwable e) {
        this("", e);
    }

    public static void throwCSDBException(Exception e) throws TxnException {
        String errCode = "";
        String message = "";
        TxnException txnE = null;

        // 获取烽火台错误信息对照表
        Map map = CSDBErrorConstant.getErrorValue();

        // 处理CSDBException异常
        if (e instanceof CSDBException) {
            CSDBException temp = (CSDBException) e;
            errCode = temp.getErrCode() + ":" + temp.getErrDesc();
            txnE = new TxnErrorException(errCode, temp.getMessage());
        }
        
        // 处理TxnException异常
        else if ((e instanceof TxnException)
                 || (e instanceof TxnErrorException)
                 || (e instanceof TxnDataException)
                 || (e instanceof TxnWarnException)) {

            TxnException txnErr = (TxnException) e;
            errCode = txnErr.getErrCode() + ":" + txnErr.getErrDesc();;
            message = (String) map.get(errCode);
            txnE = new TxnErrorException(errCode, message);
        }
       
        // 处理IOException异常
        else if (e instanceof IOException) {
            // log.error("[IOException] "+e.fillInStackTrace());
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_FILE;
        }
        
        else if (e instanceof java.net.ConnectException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.BindException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.NoRouteToHostException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.UnknownHostException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.UnknownServiceException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.SocketException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        }

        else if (e instanceof java.lang.NullPointerException) {
            // 异常附加信息 空指针
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ClassCastException) {
            // 异常附加信息 类造型错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ArrayIndexOutOfBoundsException) {
            // 异常附加信息 数组越界错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.IndexOutOfBoundsException) {
            // 异常附加信息 指针越界错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ArrayStoreException) {
            // 异常附加信息 数组存储错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.UnsupportedOperationException) {
            // 异常附加信息 不支持的操作错误
            errCode = CSDBErrorConstant.TXN_EXEC_ERROR;
        } 
        
        else if (e instanceof java.lang.ArithmeticException) {
            // 异常附加信息 算法错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        } 
        
        else if (e instanceof java.lang.NumberFormatException) {
            // 异常附加信息 数字格式错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
//        else if (e instanceof javax.naming.NameNotFoundException) {
//            // 异常附加信息 JNDIName没有找到
//            errCode = CSDBErrorConstant.SQL_POOL_NOTFOUND;
//        } 
        
        else if (e instanceof java.rmi.AccessException) {
            // 异常附加信息 远程方法拒绝请求
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.rmi.ConnectException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.rmi.ConnectIOException) {
            // 异常附加信息 网络连接错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
//        else if (e instanceof com.matrixflow.api.MFException) {
//            // 异常附加信息 工作流运行错误
//            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
//        } 
        
        else if (e instanceof javax.servlet.ServletException) {
            // 异常附加信息 ServLet运行时错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        } 
        
        else if (e instanceof java.lang.RuntimeException) {
            // 异常附加信息 运行时错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        } 
        
//        else if (e instanceof javax.transaction.TransactionRolledbackException) {
//            // 异常附加信息 事务回滚异常，检查数据库连接
//            errCode = CSDBErrorConstant.SQL_TRANSACTION_ERROR;
//        } 
        
        else if (e instanceof java.io.FileNotFoundException) {
            // 异常附加信息 文件未发现，检查文件是否存在
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_FILE;
        } 
        
        else if (e instanceof java.io.NotSerializableException) {
            // 异常附加信息 文件未发现，检查文件是否存在
            errCode = CSDBErrorConstant.JAVA_OTHER_ERROR;
        } 
        
        else if (e instanceof java.io.CharConversionException) {
            // 异常附加信息 输入输出错误，字符转化异常
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.io.InterruptedIOException) {
            // 异常附加信息 被中断的输入输出
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.io.InvalidClassException) {
            // 异常附加信息 无效的类调用
            errCode = CSDBErrorConstant.JAVA_OTHER_ERROR;
        }
        
        else if (e instanceof java.io.InvalidObjectException) {
            // 异常附加信息 无效的对象调用
            errCode = CSDBErrorConstant.JAVA_CLASS_NOTINSTANCE;
        } 
        
        else if (e instanceof java.io.NotActiveException) {
            // 异常附加信息 输入输出错误，系统激活失败
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        } 
        
        else if (e instanceof java.io.SyncFailedException) {
            // 异常附加信息 输入输出错误，同步失败
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.io.IOException) {
            // 异常附加信息 输入输出错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.lang.ClassNotFoundException) {
            // 异常附加信息 类找不到
            errCode = CSDBErrorConstant.JAVA_CLASS_NOTFOUND;
        } 
        
        else if (e instanceof java.lang.InterruptedException) {
            // 异常附加信息 中断异常
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.lang.InstantiationException) {
            // 异常附加信息 实例化错误
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        }
        
        else {
            // 其他异常抛出默认系统错误信息
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        }
        
        // 非TxnExceptin类须重新生成TxnException用于烽火台跳转
        if (txnE == null) {
            message = (String) CSDBErrorConstant.getErrorValue().get(errCode);
            txnE = new TxnErrorException(errCode, message);
        }

        // 处理异常信息在日志文件内打印，用于跟踪调试
        StringBuffer logMessage = getExceptionMsg(e);
        logMessage.insert(0, e.getMessage() + "\n" + txnE);

        log.error(logMessage.toString());

        throw txnE;
    }

    // 获取错误日志信息
    private static StringBuffer getExceptionMsg(Exception e) {
        StackTraceElement[] traces = e.getStackTrace();
        StringBuffer logMessage = new StringBuffer();

        // 打印所有的错误跟踪日志
        for (int i = 0; i < traces.length; i++) {
            logMessage.append("\n").append("  at ")
                    .append(traces[i].toString());
        }
        return logMessage;
    }
    
    public static void printErrLog(Exception e){
        log.error(" 运行时异常信息："+ e.getClass() + getExceptionMsg(e));
    }

    public Throwable getRootCause() {
        return ((Throwable) (getCause() != null ? getCause() : this));
    }

    public String getKey() {
        return key;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream ps) {
        printStackTrace(ps);
    }

    public void printStackTrace(PrintWriter ps) {
        printStackTrace(ps);
    }

    public String getDispatcherURL() {
        return dispatcherUrl;
    }

    public static void main(String args[]) {
        try {

//                 throw new CSDBException("000001","THIS IS A CSDB EXCEPTION");
                throw new TxnErrorException(ErrorConstant.FILE_NOTEXIST,"test error");
//                 throw new Exception();

                // File file = new File("e:/tttttttt/t.txt");
                // file.createNewFile();
        } catch (Exception ex) {
            System.out.println(" 运行时异常信息："+ ex.getClass() + getExceptionMsg(ex));
        }
    }
}