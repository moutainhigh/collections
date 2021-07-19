package cn.gwssi.datachange.msg_push.ftpImpl;

import cn.gwssi.datachange.msg_push.api.Msg;
import cn.gwssi.datachange.msg_push.api.MsgType;
/**
 * ftp方式传输
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class FtpMsg implements Msg{
	
	private static final long serialVersionUID = 4373535176452186038L;
	//需要传送的本地数据文件路径
	private String filePath;
	
	public FtpMsg() {
		// TODO Auto-generated constructor stub
	}
	
	public FtpMsg(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public MsgType getType() {
		return MsgType.MESSAGE;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
