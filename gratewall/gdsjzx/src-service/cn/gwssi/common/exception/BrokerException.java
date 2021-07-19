package cn.gwssi.common.exception;

/**
 * 自定义异常类
 * @author xue
 * @version 1.0
 * @since 2016/4/28
 */
@SuppressWarnings("serial")
public class BrokerException extends Exception {
	private String code ;  //异常对应的返回码
	private String msg ;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息
    
    public BrokerException() {
        super();
    }
 
    public BrokerException(String msg) {
        super(msg);
        this.msg = msg;
    }
 
    public BrokerException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
    
    public BrokerException(String code, String msg,String msgDes) {
        super();
        this.code = code;
        this.msg = msg;
        this.msgDes = msgDes;
    }

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getMsgDes() {
		return msgDes;
	}
	
}
