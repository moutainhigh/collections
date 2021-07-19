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
 * Exception��չ�� ����
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

        // ��ȡ���̨������Ϣ���ձ�
        Map map = CSDBErrorConstant.getErrorValue();

        // ����CSDBException�쳣
        if (e instanceof CSDBException) {
            CSDBException temp = (CSDBException) e;
            errCode = temp.getErrCode() + ":" + temp.getErrDesc();
            txnE = new TxnErrorException(errCode, temp.getMessage());
        }
        
        // ����TxnException�쳣
        else if ((e instanceof TxnException)
                 || (e instanceof TxnErrorException)
                 || (e instanceof TxnDataException)
                 || (e instanceof TxnWarnException)) {

            TxnException txnErr = (TxnException) e;
            errCode = txnErr.getErrCode() + ":" + txnErr.getErrDesc();;
            message = (String) map.get(errCode);
            txnE = new TxnErrorException(errCode, message);
        }
       
        // ����IOException�쳣
        else if (e instanceof IOException) {
            // log.error("[IOException] "+e.fillInStackTrace());
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_FILE;
        }
        
        else if (e instanceof java.net.ConnectException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.BindException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.NoRouteToHostException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.UnknownHostException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.UnknownServiceException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.net.SocketException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        }

        else if (e instanceof java.lang.NullPointerException) {
            // �쳣������Ϣ ��ָ��
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ClassCastException) {
            // �쳣������Ϣ �����ʹ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ArrayIndexOutOfBoundsException) {
            // �쳣������Ϣ ����Խ�����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.IndexOutOfBoundsException) {
            // �쳣������Ϣ ָ��Խ�����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.ArrayStoreException) {
            // �쳣������Ϣ ����洢����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.lang.UnsupportedOperationException) {
            // �쳣������Ϣ ��֧�ֵĲ�������
            errCode = CSDBErrorConstant.TXN_EXEC_ERROR;
        } 
        
        else if (e instanceof java.lang.ArithmeticException) {
            // �쳣������Ϣ �㷨����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        } 
        
        else if (e instanceof java.lang.NumberFormatException) {
            // �쳣������Ϣ ���ָ�ʽ����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
//        else if (e instanceof javax.naming.NameNotFoundException) {
//            // �쳣������Ϣ JNDINameû���ҵ�
//            errCode = CSDBErrorConstant.SQL_POOL_NOTFOUND;
//        } 
        
        else if (e instanceof java.rmi.AccessException) {
            // �쳣������Ϣ Զ�̷����ܾ�����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.rmi.ConnectException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.rmi.ConnectIOException) {
            // �쳣������Ϣ �������Ӵ���
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
//        else if (e instanceof com.matrixflow.api.MFException) {
//            // �쳣������Ϣ ���������д���
//            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
//        } 
        
        else if (e instanceof javax.servlet.ServletException) {
            // �쳣������Ϣ ServLet����ʱ����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        } 
        
        else if (e instanceof java.lang.RuntimeException) {
            // �쳣������Ϣ ����ʱ����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        } 
        
//        else if (e instanceof javax.transaction.TransactionRolledbackException) {
//            // �쳣������Ϣ ����ع��쳣��������ݿ�����
//            errCode = CSDBErrorConstant.SQL_TRANSACTION_ERROR;
//        } 
        
        else if (e instanceof java.io.FileNotFoundException) {
            // �쳣������Ϣ �ļ�δ���֣�����ļ��Ƿ����
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_FILE;
        } 
        
        else if (e instanceof java.io.NotSerializableException) {
            // �쳣������Ϣ �ļ�δ���֣�����ļ��Ƿ����
            errCode = CSDBErrorConstant.JAVA_OTHER_ERROR;
        } 
        
        else if (e instanceof java.io.CharConversionException) {
            // �쳣������Ϣ ������������ַ�ת���쳣
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_VARIFY;
        } 
        
        else if (e instanceof java.io.InterruptedIOException) {
            // �쳣������Ϣ ���жϵ��������
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.io.InvalidClassException) {
            // �쳣������Ϣ ��Ч�������
            errCode = CSDBErrorConstant.JAVA_OTHER_ERROR;
        }
        
        else if (e instanceof java.io.InvalidObjectException) {
            // �쳣������Ϣ ��Ч�Ķ������
            errCode = CSDBErrorConstant.JAVA_CLASS_NOTINSTANCE;
        } 
        
        else if (e instanceof java.io.NotActiveException) {
            // �쳣������Ϣ �����������ϵͳ����ʧ��
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        } 
        
        else if (e instanceof java.io.SyncFailedException) {
            // �쳣������Ϣ �����������ͬ��ʧ��
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.io.IOException) {
            // �쳣������Ϣ �����������
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.lang.ClassNotFoundException) {
            // �쳣������Ϣ ���Ҳ���
            errCode = CSDBErrorConstant.JAVA_CLASS_NOTFOUND;
        } 
        
        else if (e instanceof java.lang.InterruptedException) {
            // �쳣������Ϣ �ж��쳣
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_NETINTERRUPT;
        } 
        
        else if (e instanceof java.lang.InstantiationException) {
            // �쳣������Ϣ ʵ��������
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_SYS;
        }
        
        else {
            // �����쳣�׳�Ĭ��ϵͳ������Ϣ
            errCode = CSDBErrorConstant.CSDB_CODE_ERR_OTHER;
        }
        
        // ��TxnExceptin������������TxnException���ڷ��̨��ת
        if (txnE == null) {
            message = (String) CSDBErrorConstant.getErrorValue().get(errCode);
            txnE = new TxnErrorException(errCode, message);
        }

        // �����쳣��Ϣ����־�ļ��ڴ�ӡ�����ڸ��ٵ���
        StringBuffer logMessage = getExceptionMsg(e);
        logMessage.insert(0, e.getMessage() + "\n" + txnE);

        log.error(logMessage.toString());

        throw txnE;
    }

    // ��ȡ������־��Ϣ
    private static StringBuffer getExceptionMsg(Exception e) {
        StackTraceElement[] traces = e.getStackTrace();
        StringBuffer logMessage = new StringBuffer();

        // ��ӡ���еĴ��������־
        for (int i = 0; i < traces.length; i++) {
            logMessage.append("\n").append("  at ")
                    .append(traces[i].toString());
        }
        return logMessage;
    }
    
    public static void printErrLog(Exception e){
        log.error(" ����ʱ�쳣��Ϣ��"+ e.getClass() + getExceptionMsg(e));
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
            System.out.println(" ����ʱ�쳣��Ϣ��"+ ex.getClass() + getExceptionMsg(ex));
        }
    }
}