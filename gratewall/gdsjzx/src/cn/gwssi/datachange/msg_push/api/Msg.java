package cn.gwssi.datachange.msg_push.api;

import java.io.Serializable;

/**
 * 消息接口
 * @author wuyincheng
 * @date Jul 12, 2016
 *
 */
public interface Msg extends Serializable {
	
	//消息类型
	MsgType getType();
	
}
