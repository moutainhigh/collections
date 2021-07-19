package cn.gwssi.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Ftp 连接工具类
 * @author zhixiong
 * @version 1.0
 * @since 2016-12-02
 */
public class FtpUtil {
	
	private static Properties prop = new Properties();
	private  static FTPClient ftpClient  = null;  
	
	static {
		//加载trs.properties配置文件
        try {
			prop.load(FtpUtil.class.getClassLoader().getResourceAsStream("ftp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//设置常量
	private static final String FtpHost = prop.getProperty("ftp.host");
    //端口号  
	private static final String FtpPort =prop.getProperty("ftp.port");  
    //ftp用户名  
	private static final String FtpUser = prop.getProperty("ftp.user"); 
    //ftp密码  
	private static final String FtpPassword = prop.getProperty("ftp.pwd"); 
    //ftp中的目录  
	private static final String FtpPath = prop.getProperty("ftp.path"); 
	//本地上传目录  
	public static final String LocalPath = prop.getProperty("local.path");
	
	public static FTPClient GetFtpConnection() throws NumberFormatException, SocketException, IOException, Exception { 
		ftpClient = new FTPClient();      
		ftpClient.connect(KeysUtil.desDecrypt(FtpHost),Integer.parseInt(KeysUtil.desDecrypt(FtpPort)));      
		ftpClient.login(KeysUtil.desDecrypt(FtpUser),KeysUtil.desDecrypt(FtpPassword));      
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
		ftpClient.setBufferSize(1024);//设置1M缓冲
		ftpClient.setControlEncoding("utf8");
		ftpClient.enterLocalPassiveMode();
        int reply = ftpClient.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
        	ftpClient.disconnect();      
            throw new IOException("Connection refused!");
        }      
        ftpClient.changeWorkingDirectory(KeysUtil.desDecrypt(FtpPath));      
        return ftpClient;      
    } 
	
	/**
	 * 关闭Ftp连接
	 * @param ftp
	 */
	public static void release(FTPClient ftp) {// 关闭连接
		if(ftp!=null){
			if(ftp.isConnected()){
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ftp = null;
	}
	
	public static void main(String[] args) {
		FTPClient ftp = null;
		FileInputStream in = null;
		String fileName = "";
		try {
			ftp = FtpUtil.GetFtpConnection();
			File srcDir = FileUtil.createFileOrDir(FtpUtil.LocalPath,false);
			if(srcDir!=null){
				File[] allFile = srcDir.listFiles();
				if(allFile!=null && allFile.length>0){
					for(int currentFile = 0; currentFile < allFile.length; currentFile++){
						fileName = allFile[currentFile].getName();//获取文件名
						in = new FileInputStream(allFile[currentFile]);
						boolean result = ftp.storeFile(fileName, in);
						if(result){//如果传完就删除这个文件
							System.out.println(fileName);
							in.close();
							in = null;
							allFile[currentFile].delete();
							//FileUtil.delFile(srcName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					in = null;
				}
			}
			FtpUtil.release(ftp);
		}
	}
}
