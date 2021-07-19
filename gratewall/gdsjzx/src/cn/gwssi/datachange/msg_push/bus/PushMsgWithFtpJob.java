package cn.gwssi.datachange.msg_push.bus;

import cn.gwssi.datachange.msg_push.api.MsgSender;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpMsg;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpReceiver;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpSenderFactory;
import cn.gwssi.datachange.msg_push.service.PushServiceHelper;
import cn.gwssi.quartz.inter.JobServer;

/**
 * Quartz调度任务
 * @author wuyincheng ,Jul 15, 2016
 */
public class PushMsgWithFtpJob implements JobServer{

	//全局
	private static MsgSender sender = new FtpSenderFactory().createMsgSender();
	
	//命名
//	private final static String JOB_GROUP_NAME = "PUSH_MSG_JOB";
//	private final static String TRIGGER_GROUP_NAME = "PUSH_MSG_TRIGGER";
	
	public PushMsgWithFtpJob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String job(String paramers) { //params为订阅id
		String fwdyId = paramers;
		//构造推送&接收对象
		FtpMsg msg = new FtpMsg();
		FtpReceiver receiver = new FtpReceiver();
		//填充属性
		//注： 此serviceId为T_PT_FWDYPZJBXX表中的fwdypzjbxxid ,updete by @wuyincheng 2016/08/19
		PushServiceHelper.senderService.initContext(fwdyId, msg, receiver);
		try {
			//发送
			sender.sendMsg(msg, receiver);
			//成功需要删除文件？
			//PushServiceHelper.senderService.deleteCache(msg.getFilePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
