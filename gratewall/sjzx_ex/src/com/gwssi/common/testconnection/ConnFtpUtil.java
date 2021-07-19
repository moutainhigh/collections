package com.gwssi.common.testconnection;

import java.io.IOException;

import org.apache.log4j.Logger;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;
import cn.gwssi.common.component.logger.TxnLogger;

public class ConnFtpUtil
{
	public String			a;

	protected static Logger	logger		= TxnLogger.getLogger(ConnFtpUtil.class
												.getName());

	String					hostname	= "";

	FtpClient				aftp;
	
	
	/**
	 * 
	 * connect(测试ftp连接是否通畅) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param hostname
	 * @param port
	 * @param uid
	 * @param pwd
	 * @return boolean true:连接成功；false:连接失败
	 * @throws Exception
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public boolean connect(String hostname, int port, String uid, String pwd)
			throws Exception
	{
		this.hostname = hostname;
		boolean isSuccess = false;
		try {
			aftp = new FtpClient();
			aftp.setConnectTimeout(5000);
			aftp.openServer(hostname, port);
			aftp.login(uid, pwd);
			aftp.binary();
			isSuccess = true;
		} catch (FtpLoginException e) {
			logger.info("登陆主机:" + hostname + "失败!请检查用户名或密码是否正确：" + e);
			isSuccess = false;
		} catch (IOException e) {
			logger.info("连接主机:" + hostname + "失败!请检查主机Ip或端口是否正确：" + e);
			isSuccess = false;
		} catch (SecurityException e) {
			logger.info("无权限与主机:" + hostname + "连接!请检查是否有访问权限：" + e);
			isSuccess = false;
		} finally{
			if( null != aftp){
				aftp.closeServer();
			}
		}
		return isSuccess;
	}
	
	/**
	 * stop(终止ftp连接)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)            
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void stop()
	{
		String message="";
		try {
			if(aftp!=null){
				aftp.closeServer();
				message = "Disconnect success!";
				System.out.print(message);
			}
			 } catch (IOException e) {
				message = "Disconnect fail!";
				System.out.println(message);
		}
	}

	public void test() throws Exception
	{
		String hostname1 = "172.30.18.18";
		int port = 21;
		String uid = "lizheng898";
		String pwd = "lizheng";
		
		//连接ftp服务器
		ConnFtpUtil fp = new ConnFtpUtil();
		System.out.println("befor connect aftp="+fp.aftp);
		//fp.connect(hostname1, port, uid, pwd);
		System.out.println("after connect 状态="+fp.connect(hostname1, port, uid, pwd));
	}
	
	public static void main(String[] args) throws Exception {
		ConnFtpUtil testFTP = new ConnFtpUtil();
		testFTP.test();
	}
}
