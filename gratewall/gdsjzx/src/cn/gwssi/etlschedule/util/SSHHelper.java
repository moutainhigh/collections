package cn.gwssi.etlschedule.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.management.RuntimeErrorException;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH工具类
 * @author 赵聪慧
 * 2013-4-7
 */
public class SSHHelper {



	private String ip;
	private String user;
	private String pwd;
	private int port ;



	public SSHHelper(String ip, String user, String pwd, int port) {
		this.ip = ip;
		this.user = user;
		this.pwd = pwd;
		this.port = port;
	}

	//	public    Session getSession() throws JSchException{
	//		JSch jsch=new JSch();
	//		Session  session = jsch.getSession(user,ip, 23);
	//		java.util.Properties config = new java.util.Properties();
	//		config.put("StrictHostKeyChecking", "no");
	//		session.setConfig(config);
	//		session.setPassword(pwd);
	//		return session;
	//
	//	}




	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * @param host	主机名
	 * @param user	用户名
	 * @param psw	密码
	 * @param port	端口
	 * @param command	命令
	 * @return
	 * @throws Exception 
	 */
	public  String exec(String command) throws Exception{
		String result="";
		Session session =null;
		ChannelExec openChannel =null;
		try {
			JSch jsch=new JSch();
			session = jsch.getSession(user, ip, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(pwd);
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			int exitStatus = openChannel.getExitStatus();
			InputStream in = openChannel.getInputStream();  
			openChannel.connect();  
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br>\r\n";  
			}  
		} catch (Exception e) {
			result+=e.getMessage();
		}finally{
			if(openChannel!=null&&!openChannel.isClosed()){
				openChannel.disconnect();
			}
			if(session!=null&&session.isConnected()){
				session.disconnect();
			}
		}
		return result;	}
}
