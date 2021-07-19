package cn.gwssi.datachange.msg_push.ftpImpl;

import java.io.File;
import java.io.FileInputStream;

import cn.gwssi.datachange.msg_push.api.Msg;
import cn.gwssi.datachange.msg_push.api.MsgSender;
import cn.gwssi.datachange.msg_push.api.Result;
import cn.gwssi.datachange.msg_push.api.receiver.Receiver;
import cn.gwssi.datachange.msg_push.api.receiver.ReceiverProtocol;
import cn.gwssi.datachange.msg_push.component.AbstractSenderFactory;

/**
 * FTP推送构造
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class FtpSenderFactory extends AbstractSenderFactory{
	
	public FtpSenderFactory() {
	}
	
	public FtpSenderFactory(boolean isFixedPool, int poolSize, int timeout) {
		this.isFixedPool = isFixedPool;
		this.poolSize = poolSize;
		this.timeout = timeout;
	}
	

	@Override
	protected MsgSender getMsgSender() {
		return new FtpSender();
	}
	//ftp sender
	private static class FtpSender implements MsgSender{
		@Override
		public Result sendMsg(Msg msg, Receiver receiver) throws Exception {
			if(msg == null || receiver == null)
				throw new NullPointerException(" FTP Msg || FTP Receiver can't be null");
			if(receiver.getProtocol() != ReceiverProtocol.FTP)
				throw new NullPointerException("Protocol not match");
			FtpMsg fMsg = (FtpMsg) msg;
			if(fMsg.getFilePath() == null || "".equals(fMsg.getFilePath().trim()))
				throw new NullPointerException("Data can't be null");
			File f = new File(fMsg.getFilePath().trim());
			if(!f.exists())
				throw new NullPointerException("File is not exist: " + fMsg.getFilePath());
			FtpReceiver rec = (FtpReceiver) receiver;
			final boolean r = FtpStorageManager.upload(rec, new FileInputStream(f), rec.getSavePath());
			return r ? FtpResult.SUCCESS : FtpResult.FAILED;
		}
		
	}
	
}
