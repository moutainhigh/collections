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

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(FtpUtil.class
												.getName());
	
	
	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP服务器连接失败");
				return success;
			}
			if(!ftp.changeWorkingDirectory(path)){
				logger.error("配置的FTP路径错误 目录不存在或无访问权限 path="+path);
				
				
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
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
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
			
			//设置编码  
	        //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件  
	        ftp.setControlEncoding("UTF-8");  
	        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
	        conf.setServerLanguageCode("zh"); 
	        
			//System.out.println("url="+url+"--port="+port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			//System.out.println("username="+username+"--password="+password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP服务器连接失败");
				return success;
			}
			//System.out.println("localPath="+localPath);
			
			//System.out.println("remotePath="+remotePath);
			
			if(!ftp.changeWorkingDirectory(remotePath)){
				// 转移到FTP服务器目录 (相对目录)
				logger.error("配置的FTP路径错误 目录不存在或无访问权限 remotePath="+remotePath);
				return false;
			}
			//System.out.println("workDir="+ftp.printWorkingDirectory());
					
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			FTPFile[] fs = ftp.listFiles();
			//System.out.println(remotePath);
			//System.out.println("length="+fs.length);
			//System.out.println(fileName);
			//logger.info("路径中的文件个数："+fs.length);
			boolean find=false;
			for (FTPFile ff : fs) {
				//logger.info("当前文件："+ff.getName()+"----要下载文件:"+fileName);
				//System.out.println(ff.getName());
				if (ff.isFile() && ff.getName().equals(fileName)) {
					logger.info("找到指定文件，准备下载 "+fileName);
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
				logger.info("目录中不存在文件:"+fileName);
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
				logger.info("默认字符集="+Charset.defaultCharset().name());
				String line;
				line = reader.readLine();
				System.out.println(line);
		}
		//boolean flag = downFile("127.0.0.1", 21, "test", "test",
		//		 "/file/", "DATA_MZ_BGS_20140403.txt", "D:\\temp\\datafiles");
		
	}

}
