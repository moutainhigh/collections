package com.gwssi.session;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class HttpTorchSessionListener implements ServletContextListener {
	
	protected final static Logger logger = Logger.getLogger(HttpTorchSessionListener.class);
	


	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		try {
			ResourceBundle bundle=ResourceBundle.getBundle("session");
			SessionConstants.COOKIE_KEY=bundle.getString("cookieKey");
			SessionConstants.EXPIRE_SECONDS=Integer.parseInt(bundle.getString("expireSeconds"));
			SessionConstants.DB_INDEX=Integer.parseInt(bundle.getString("dbIndex"));
			
			
			logger.info("加载集群session配置信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取集群session配置信息失败");
		}
		

	}

}
