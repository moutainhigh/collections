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
	 * @throws DBException 
	 * @throws TxnDataException 
	 */
	public static List downLoadFile(Map tablepMap) throws DBException, TxnDataException {
		List fileList = new ArrayList();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			String ip=(String)tablepMap.get("DATA_SOURCE_IP");//����ip
			String access_port=(String)tablepMap.get("ACCESS_PORT");//���ʶ˿�
			int port=21;
			if(access_port!=null&&!access_port.equals("")){
				port=Integer.parseInt(access_port);
			}
			String access_url=(String)tablepMap.get("ACCESS_URL");//����URL
			String username=(String)tablepMap.get("DB_USERNAME");//�û���
			String password=(String)tablepMap.get("DB_PASSWORD");//����
			
			ftp.connect(ip,port);
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return fileList;
			}
			ftp.changeWorkingDirectory(access_url);// ת�Ƶ�FTP������Ŀ¼ (���Ŀ¼)
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
			System.out.println("��������Դʧ��!");
			throw new TxnDataException("error", "��������Դʧ��!");
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
	public static List downFtpFile(Map tablepMap, String localPath) {
		
		FTPClient ftp = new FTPClient();
		List fileList = new ArrayList();
		try {
			int reply;
			String ip=(String)tablepMap.get("DATA_SOURCE_IP");//����ip
			String access_port=(String)tablepMap.get("ACCESS_PORT");//���ʶ˿�
			int port=21;
			if(access_port!=null&&!access_port.equals("")){
				port=Integer.parseInt(access_port);
			}
			String access_url=(String)tablepMap.get("ACCESS_URL");//����URL
			String username=(String)tablepMap.get("DB_USERNAME");//�û���
			String password=(String)tablepMap.get("DB_PASSWORD");//����
			
			
			ftp.connect(ip, port);
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return fileList;
			}
			ftp.changeWorkingDirectory(access_url);// ת�Ƶ�FTP������Ŀ¼ (���Ŀ¼)
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
