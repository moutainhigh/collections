package cn.gwssi.datachange.msg_push.component;

import java.util.UUID;

import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.resource.ServiceConstants;
import cn.gwssi.datachange.msg_push.api.Msg;
import cn.gwssi.datachange.msg_push.api.MsgSender;
import cn.gwssi.datachange.msg_push.api.Result;
import cn.gwssi.datachange.msg_push.api.receiver.Receiver;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpReceiver;
import cn.gwssi.datachange.msg_push.service.PushServiceHelper;

/**
 * 失败重试包装类,出现除Null之外的异常则默认失败重试，（Null说明配置错误）
 * 所有Exception在此层捕获完毕，失败返回null
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class RetrySender implements MsgSender {

	private MsgSender sender = null;
	
	
	private int count ;    //尝试次数
	private long time ; //尝试间隔时间
	
	public RetrySender(MsgSender sender) {
		this.sender = sender;
	}
	
	public RetrySender(MsgSender sender,int count, int time) {
		this.count = count;
		this.time = time * 1000;
		this.sender = sender;
	}
	
	@Override
	public Result sendMsg(Msg msg, Receiver receiver) throws Exception {
		Result r = null;
		int c = this.count;
		final TPtFwrzjbxxBO logEntity = new TPtFwrzjbxxBO();
		final FtpReceiver ftp = (FtpReceiver) receiver;
		//final String ipInfo = ftp.getIp() + ":" + ftp.getPort();
		
		logEntity.setExecutecase("1");//1代表订阅
		logEntity.setExecutetype("订阅");
		logEntity.setCallerip(ftp.getIp());
		logEntity.setCallername(ftp.getUserName());//调用者
		logEntity.setCalleer(ServiceConstants.DATA_CENTER_NAME);//提供者
		
		long startTime = 0L;
		long endTime = 0L;
		do{
			logEntity.setFwrzjbid(UUID.randomUUID().toString());
			try {
				c = 0;
				startTime = System.currentTimeMillis();
				logEntity.setCallertime(startTime + "");//开始
				r = sender.sendMsg(msg, receiver);
				endTime = System.currentTimeMillis();
				logEntity.setCallerenttime(endTime + "");//结束
				logEntity.setExecutetime((endTime-startTime)+"");//时间差
				
				logEntity.setExecuteway(c+"");
				logEntity.setCallerparameter("Send Success:[" + ftp.toString() + "]");//参数
				logEntity.setExecuteresult("成功");//执行结果
				PushServiceHelper.log(logEntity);
			} catch (NullPointerException e) {
				endTime = System.currentTimeMillis();
				logEntity.setCallerenttime(endTime + "");//结束
				logEntity.setExecutetime((endTime-startTime)+"");//时间差
				
				logEntity.setCallerparameter("Config Error:[" + ftp.toString() + "] " + e.getMessage());//参数
				logEntity.setExecuteway(c+"");
				logEntity.setExecuteresult("失败");//执行结果
				PushServiceHelper.log(logEntity);
				e.printStackTrace();
				return r;
			} catch (Exception e) {
				endTime = System.currentTimeMillis();
				logEntity.setCallerenttime(endTime + "");//结束
				logEntity.setExecutetime((endTime-startTime)+"");//时间差
				
				logEntity.setCallerparameter("Retry :[" + ftp.toString() + "] " + e.getMessage());//参数
				logEntity.setExecuteresult("失败");//执行结果
				c --;
				logEntity.setExecuteway(c+"");
				PushServiceHelper.log(logEntity);
				e.printStackTrace();
				if(c > 0)
					Thread.sleep(time);
			}
		}while(c > 0);
		return r;
	}



}
