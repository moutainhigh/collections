package cn.gwssi.datachange.msg_push.ftpImpl;

import cn.gwssi.datachange.msg_push.api.receiver.Receiver;
import cn.gwssi.datachange.msg_push.api.receiver.ReceiverProtocol;

public class FtpReceiver implements Receiver{
	
	private static final long serialVersionUID = -8267845466367083657L;
	
	private String ip = "";
	private int port = 21;
	private String userName;
	private String pwd;
	private String savePath = "";  
	
	public FtpReceiver() {
		// TODO Auto-generated constructor stub
	}
	
	public FtpReceiver(String ip, int port, String userName, String pwd, String savePath) {
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.pwd = pwd;
		this.savePath = savePath;
	}
	
	@Override
	public ReceiverProtocol getProtocol() {
		return ReceiverProtocol.FTP;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	@Override
	public String toString() {
		return "FtpReceiver [ip=" + ip + ", port=" + port + ", userName="
				+ userName + ", pwd=" + pwd + ", savePath=" + savePath + "]";
	}
	
}
