package cn.gwssi.datachange.msg_push.api;
/**
 * 消息类型接口
 * @author wuyincheng
 * @date Jul 12, 2016
 *
 */
public enum MsgType {
	MESSAGE,       //普通推送消息
	RESULT,        //推送返回结果
	LIVE,          //心跳消息 @TODO ftp不需要
	LOCATE,        //标识位置消息  @TODO 暂时不需要
	CONFIG,        //配置消息 @TODO暂时不需要
	UPDATE,        //组件更新消息 @TODO暂时不需要
}
