package com.gwssi.ftp.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import cn.gwssi.common.component.exception.TxnDataException;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.util.CalendarUtil;

public class DownloadFile {

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
	 * @throws DBException 
	 * @throws TxnDataException 
	 */
	public static List downLoadFile(Map tablepMap) throws DBException, TxnDataException {
		List fileList = new ArrayList();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			String ip=(String)tablepMap.get("DATA_SOURCE_IP");//访问ip
			String access_port=(String)tablepMap.get("ACCESS_PORT");//访问端口
			int port=21;
			if(access_port!=null&&!access_port.equals("")){
				port=Integer.parseInt(access_port);
			}
			String access_url=(String)tablepMap.get("ACCESS_URL");//访问URL
			String username=(String)tablepMap.get("DB_USERNAME");//用户名
			String password=(String)tablepMap.get("DB_PASSWORD");//密码
			
			ftp.connect(ip,port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return fileList;
			}
			ftp.changeWorkingDirectory(access_url);// 转移到FTP服务器目录 (相对目录)
			FTPFile[] fs = ftp.listFiles();
			Map fileMap = new HashMap();
			
			for (FTPFile ff : fs) {
				fileMap = new HashMap();
				System.out.println("filename==="+ff.getName());
				fileMap.put("filename", ff.getName());
				fileList.add(fileMap);
			}
			ftp.logout();
		} catch (IOException e) {
			System.out.println("连接数据源失败!");
			throw new TxnDataException("error", "连接数据源失败!");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return fileList;
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
	public static List downFtpFile(Map tablepMap, String localPath) {
		
		FTPClient ftp = new FTPClient();
		List fileList = new ArrayList();
		try {
			int reply;
			String ip=(String)tablepMap.get("DATA_SOURCE_IP");//访问ip
			String access_port=(String)tablepMap.get("ACCESS_PORT");//访问端口
			int port=21;
			if(access_port!=null&&!access_port.equals("")){
				port=Integer.parseInt(access_port);
			}
			String access_url=(String)tablepMap.get("ACCESS_URL");//访问URL
			String username=(String)tablepMap.get("DB_USERNAME");//用户名
			String password=(String)tablepMap.get("DB_PASSWORD");//密码
			
			
			ftp.connect(ip, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return fileList;
			}
			ftp.changeWorkingDirectory(access_url);// 转移到FTP服务器目录 (相对目录)
			Map fileMap = new HashMap();
			FTPFile[] fs = ftp.listFiles();
			String nowDate=CalendarUtil.getCurrentDateTime();
			if(nowDate!=null&&!"".equals(nowDate)){
				nowDate=nowDate.substring(0,10);
			}
			for (FTPFile ff : fs) {
					fileMap = new HashMap();
					System.out.println("filename==="+ff.getName());
					fileMap.put("filename", nowDate+"_"+ff.getName());
					fileList.add(fileMap);
					File localFile = new File(localPath + File.separator +nowDate+"_"+ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
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
		return fileList;
	}
}
