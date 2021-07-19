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
	 * connect(����ftp�����Ƿ�ͨ��) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param hostname
	 * @param port
	 * @param uid
	 * @param pwd
	 * @return boolean true:���ӳɹ���false:����ʧ��
	 * @throws Exception
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
			logger.info("��½����:" + hostname + "ʧ��!�����û����������Ƿ���ȷ��" + e);
			isSuccess = false;
		} catch (IOException e) {
			logger.info("��������:" + hostname + "ʧ��!��������Ip��˿��Ƿ���ȷ��" + e);
			isSuccess = false;
		} catch (SecurityException e) {
			logger.info("��Ȩ��������:" + hostname + "����!�����Ƿ��з���Ȩ�ޣ�" + e);
			isSuccess = false;
		} finally{
			if( null != aftp){
				aftp.closeServer();
			}
		}
		return isSuccess;
	}
	
	/**
	 * stop(��ֹftp����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)            
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
		
		//����ftp������
		ConnFtpUtil fp = new ConnFtpUtil();
		System.out.println("befor connect aftp="+fp.aftp);
		//fp.connect(hostname1, port, uid, pwd);
		System.out.println("after connect ״̬="+fp.connect(hostname1, port, uid, pwd));
	}
	
	public static void main(String[] args) throws Exception {
		ConnFtpUtil testFTP = new ConnFtpUtil();
		testFTP.test();
	}
}
