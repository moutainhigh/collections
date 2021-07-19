package com.gwssi.ftp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.util.UnicodeReader;
import com.gwssi.webservice.client.TaskInfo;
import sun.util.logging.resources.logging;

public class FtpUtil {

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(FtpUtil.class
												.getName());
	
	
	/**
	 * Description: ��FTP�������ϴ��ļ�
	 * 
	 * @param url
	 *            FTP������hostname
	 * @param port
	 *            FTP�������˿�
	 * @param username
	 *            FTP��¼�˺�
	 * @param password
	 *            FTP��¼����
	 * @param path
	 *            FTP����������Ŀ¼
	 * @param filename
	 *            �ϴ���FTP�������ϵ��ļ���
	 * @param input
	 *            ������
	 * @return �ɹ�����true�����򷵻�false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// ����FTP������
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP����������ʧ��");
				return success;
			}
			if(!ftp.changeWorkingDirectory(path)){
				logger.error("���õ�FTP·������ Ŀ¼�����ڻ��޷���Ȩ�� path="+path);
				
				
			}else{
				ftp.enterLocalPassiveMode();
				success = ftp.storeFile(filename, input);
			}
			

			
			//success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					input.close();
					ftp.logout();
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * Description: ��FTP�����������ļ�
	 * 
	 * @param url
	 *            FTP������hostname
	 * @param port
	 *            FTP�������˿�
	 * @param username
	 *            FTP��¼�˺�
	 * @param password
	 *            FTP��¼����
	 * @param remotePath
	 *            FTP�������ϵ����·��
	 * @param fileName
	 *            Ҫ���ص��ļ���
	 * @param localPath
	 *            ���غ󱣴浽���ص�·��
	 * @return
	 */
	public static boolean downFile(String url, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			
			//���ñ���  
	        //�������д������Ҫ�����Ҳ��ܸı�����ʽ����������ȷ���������ļ�  
	        ftp.setControlEncoding("UTF-8");  
	        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
	        conf.setServerLanguageCode("zh"); 
	        
			//System.out.println("url="+url+"--port="+port);
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			//System.out.println("username="+username+"--password="+password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP����������ʧ��");
				return success;
			}
			//System.out.println("localPath="+localPath);
			
			//System.out.println("remotePath="+remotePath);
			
			if(!ftp.changeWorkingDirectory(remotePath)){
				// ת�Ƶ�FTP������Ŀ¼ (���Ŀ¼)
				logger.error("���õ�FTP·������ Ŀ¼�����ڻ��޷���Ȩ�� remotePath="+remotePath);
				return false;
			}
			//System.out.println("workDir="+ftp.printWorkingDirectory());
					
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			FTPFile[] fs = ftp.listFiles();
			//System.out.println(remotePath);
			//System.out.println("length="+fs.length);
			//System.out.println(fileName);
			//logger.info("·���е��ļ�������"+fs.length);
			boolean find=false;
			for (FTPFile ff : fs) {
				//logger.info("��ǰ�ļ���"+ff.getName()+"----Ҫ�����ļ�:"+fileName);
				//System.out.println(ff.getName());
				if (ff.isFile() && ff.getName().equals(fileName)) {
					logger.info("�ҵ�ָ���ļ���׼������ "+fileName);
					File localFile = new File(localPath + File.separator + ff.getName());
					//System.out.println(localPath + File.separator + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					success=ftp.retrieveFile(ff.getName(), is);
					//System.out.println(ff.getName()+"-----"+success);
					is.close();
					find=true;
					break;
				}
			}
			if(!find){
				logger.info("Ŀ¼�в������ļ�:"+fileName);
			}

			ftp.logout();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		//System.out.println("over="+success);
		return success;
	}

	public static void main(String[] args) throws IOException {

/*		FileInputStream in;
		try {
			in = new FileInputStream(new File("D:/temp/datafiles/file_ftp/DATA_MZ_BGS_20140415.txt"));
			boolean flag = uploadFile("127.0.0.1", 21, "test", "test",
					"/fil/", "DATA_MZ_BGS_20140415.txt", in);
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/

//		 downFile("172.30.7.250", 21, "administrator", "abc123!",
//		 "FTP", "a.txt", "d:/temp1/");
		
		
		boolean flag = downFile("160.99.1.4", 21, "W_MZJ", "ada9e081",
				 "/mz_data/", "DATA_MZ_20140418.txt", "D:\\temp\\datafiles");
		
		System.out.println(flag);
		
		File f = new File("D:\\temp\\datafiles\\DATA_MZ_20140418.txt");
		if (f.isFile() && f.exists()) {
			
				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read,"GBK"));
				logger.info("Ĭ���ַ���="+Charset.defaultCharset().name());
				String line;
				line = reader.readLine();
				System.out.println(line);
		}
		//boolean flag = downFile("127.0.0.1", 21, "test", "test",
		//		 "/file/", "DATA_MZ_BGS_20140403.txt", "D:\\temp\\datafiles");
		
	}

}
