package cn.gwssi.datachange.msg_push.ftpImpl;
/**
 * @author wuyincheng ,Aug 19, 2016
 */
public class FtpSendException extends Exception{
	
	private long startTime;
	private long endTime;
	
	
	public FtpSendException(String msg) {
		super(msg);
	}
	
}
