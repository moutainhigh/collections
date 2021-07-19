package com.gwssi.trs;

import java.io.IOException;
import java.util.Properties;

import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

/**
 * trs 连接工具类
 * @author zhixiong
 * @version 1.0
 * @since 2015-11-16
 */
public class TrsConnectionUtil {
	
	private static Properties prop = new Properties();
	//private static TRSConnection trscon = null;
	
	static {
		//加载trs.properties配置文件
        try {
			prop.load(TrsConnectionUtil.class.getClassLoader().getResourceAsStream("trs.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//设置常量
	public static final String SERVER_IP = prop.getProperty("trs.serverIp");
	public static final String SERVER_PORT = prop.getProperty("trs.serverPort");
    public static final String USERNAME = prop.getProperty("trs.userName");
    public static final String PASSWORD = prop.getProperty("trs.passWord");
    public static final String STR_OPTIONS = prop.getProperty("trs.strOptions");
    
    /**
     * 获取trs连接
     * @return
     * @throws Exception 
     * @throws IOException 
     * @throws TRSException 
     */
	public static TRSConnection GetTrsConnection() throws TRSException, IOException, Exception {
		TRSConnection trscon = null;
		trscon = new TRSConnection();
		trscon.connect(KeysUtil.desDecrypt(SERVER_IP), KeysUtil.desDecrypt(SERVER_PORT), KeysUtil.desDecrypt(USERNAME), KeysUtil.desDecrypt(PASSWORD), KeysUtil.desDecrypt(STR_OPTIONS));
		return trscon;
	}
	
	
	public static void main(String[] args) throws TRSException, IOException, Exception {
		TrsConnectionUtil.GetTrsConnection();
	}
	
	
	/**
	 * 关闭trs连接
	 * @param trscon
	 */
	public static void release(TRSConnection trscon) {
		// 关闭连接
		if (trscon != null) trscon.close();
		trscon = null;
	}

	/**
	 * 关闭trs连接
	 * @param trsrs
	 * @param trscon
	 */
	public static void release(TRSResultSet trsrs,TRSConnection trscon) {
		// 关闭结果集
		if (trsrs != null) {
			trsrs.close();
			trsrs = null;
		}
		// 关闭连接
		if (trscon != null) {
			trscon.close();
			trscon = null;
		}
	}
	
}
