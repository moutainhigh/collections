package cn.gwssi.datachange.msg_push.api.receiver;

import java.io.Serializable;
/**
 * 接收者
 * @author wuyincheng Jul 14, 2016
 */
public interface Receiver extends Serializable{

	//消息传输使用协议(HTTP,FTP及以后扩展等)
	ReceiverProtocol getProtocol();
	
	
}
