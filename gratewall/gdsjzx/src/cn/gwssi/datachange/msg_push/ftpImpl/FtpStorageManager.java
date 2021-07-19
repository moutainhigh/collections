package cn.gwssi.datachange.msg_push.ftpImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;


/**
 * 
 * FTP上传实体
 * @author wuyincheng
 * @date Jul 12, 2016
 * @TODO ftpclient实例是否需要复用?
 */
public class FtpStorageManager {
	
	private static Logger log = Logger.getLogger(FtpStorageManager.class);
	/**
	 * 
	 * @param config
	 * @param is 自动关闭流
	 * @param savePath 包含文件名的路径 EG:/pub/a/b/c/20150505/23/12313232312.jpg
	 * @throws IOException 
	 */
	public static boolean upload(FtpReceiver config, InputStream is, String savePath) throws Exception{
		final FTPClient ftp = new FTPClient();
		try{
			savePath = PathFormat.parse(savePath);
			log.info("FTP Start:[" + config.getIp() + ":" + config.getPort() + "][" + savePath+"]");
			ftp.connect(config.getIp(), config.getPort());
			ftp.login(config.getUserName(), config.getPwd());
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setControlEncoding("utf8");
			ftp.enterLocalPassiveMode();
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new IOException("Connection refused!");
			}
			//获取文件路径信息,先格式化
			
			final int t = savePath.lastIndexOf('/');
			final String dirPath = savePath.substring(0, t);
			final String fileName = savePath.substring(t + 1, savePath.length());
			//查看文件目录是否存在,不存在则创建
			if(!ftp.changeWorkingDirectory(dirPath)){
				creatDirectory(ftp, dirPath);
				ftp.changeWorkingDirectory(dirPath);
			}
			//上传文件
			boolean result = ftp.storeFile(fileName, is);
			return result;
		}catch(IOException e){
			throw e;
		}finally{
			try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * 创建ftp目录,
	 * @param path 不包含文件名 EG:/pub/a/b/c
	 * @throws IOException 
	 */
	private static synchronized void creatDirectory(FTPClient client, String path) throws IOException{
		if(client.changeWorkingDirectory(path)){ //重新判断文件夹是否存在
			return ;
		}
		String temp;
		for(int i = 0; i < path.length(); i++){
			if(path.charAt(i) == '/'){
				if(i == 0) continue ;
				temp = path.substring(0, i);
				if(!client.changeWorkingDirectory(temp)){
					client.makeDirectory(temp);
				}
			}
		}
		//最后一级
		if(!client.changeWorkingDirectory(path)){ //重新判断文件夹是否存在
			client.makeDirectory(path);
		}
	}
	
	public static void main(String[] args) throws IOException {
//		String s  = PathFormat.parse("/b/c/{time}{rand:8}", "a.jpb");
//		System.out.println(s);
		FTPClient ftp = new FTPClient();
		int reply;
		ftp.connect("10.1.2.124", 21);
		ftp.login("anonymous", "Hello");
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.setControlEncoding("utf8");
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
		}
//		boolean result = ftp.storeFile("HelloWorld.txt", new FileInputStream("/home/wu/t1.txt"));
//		System.out.println(result);
		creatDirectory(ftp, "/pub/b/c/d");
	}
}
