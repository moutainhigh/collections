package cn.gwssi.datachange.msg_push.ftpImpl;

/**
 * FTP配置
 * @author wuyincheng
 * @TODO 配置端口一定要加上!
 */
public class FtpServerConfig {
	
	private String ip = "";
	private int port = 21;
	private String userName;
	private String pwd;
	private String url;
	private String filePath = "";  //包含文件名(文件名已被格式化)
	

	public FtpServerConfig(String ftpUrl) {
//		String ftpUrl = "ftp://iLoveNetResource:!@)*(#!_@(#*help@10.22.0.89:21/a/b/c.png";
		this.url = ftpUrl;
		init();
	}
	
	private void init(){
		final int length = url.length();
		int temp = url.lastIndexOf('@');
		final String ipPort = url.substring(temp + 1, length);//获取ip&还有端口
		final String userPwd = url.substring(6, temp);
		//ip & port
		if((temp = ipPort.indexOf(':')) != -1){
			this.ip = ipPort.substring(0, temp);
			int t = ipPort.indexOf('/');
			this.port = Integer.parseInt(ipPort.substring(temp + 1, t));
			this.filePath = ipPort.substring(t, ipPort.length());
		}else{
			int t = ipPort.indexOf('/');
			this.ip = ipPort.substring(0, t);
			this.filePath = ipPort.substring(t, ipPort.length());
		}
		temp = userPwd.indexOf(':');
		this.userName = userPwd.substring(0, temp);
		this.pwd = userPwd.substring(temp + 1, userPwd.length());
		if(this.userName == null || "".equals(this.userName.trim()))
			this.userName = "anonymous";
		if(this.pwd == null || "".equals(this.pwd.trim()))
			this.pwd = "anonymous";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		return url.equals(((FtpServerConfig)obj).url);
	}
	
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder(ip);
		s.append(':').append(port).append(',').append(userName).append(',').append(pwd)
		 .append(',').append(filePath);
		return s.toString();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static void main(String[] args) {
		FtpServerConfig c = new FtpServerConfig("ftp://:@192.168.1.1:21/pub/a/{yyyy}{mm}{dd}/{time}{rand:8}");
		System.out.println(c.getIp());
		System.out.println(c.getPort());
		System.out.println(new FtpServerConfig("ftp://:@192.168.1.1:21/pub/a/{yyyy}{mm}{dd}/{time}{rand:8}").getFilePath());
		
		
	}
	
	
}