package cn.gwssi.datachange.msg_push.api;

import cn.gwssi.datachange.msg_push.api.receiver.Receiver;

/**
 * 消息发送者
 * @author wuyincheng
 * @date Jul 12, 2016
 *
 */
public interface MsgSender {
	
	Result sendMsg(Msg msg, Receiver receiver) throws Exception;
	
}
