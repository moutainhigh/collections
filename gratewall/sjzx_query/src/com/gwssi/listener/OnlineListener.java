package com.gwssi.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;

/**
 * 监听在线用户上线下线
 * 
 * @author 任桂明
 * 
 */
public class OnlineListener extends BaseService implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

	private static final Logger logger = Logger.getLogger(OnlineListener.class);

	private static ApplicationContext ctx = null;

	public OnlineListener() {
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	/**
	 * 向session里增加属性时调用(用户成功登陆后会调用)
	 */
	public void attributeAdded(HttpSessionBindingEvent evt) {
		String name = evt.getName();
		String sessionId = evt.getSession().getId();
		logger.debug("向session存入属性：name=" + name+";sessionId="+sessionId);
		HttpSession session = evt.getSession();
		if(session!=null){
			String userID = (String) session.getAttribute(OptimusAuthManager.LOGIN_NAME);
			String userName = (String) session.getAttribute(OptimusAuthManager.SESSION_KEY_USER_NAME);
			String IpAddr = (String) session.getAttribute(OptimusAuthManager.USERIP);
			if (userID !=null && userName !=null && IpAddr !=null) {
				/*String formatDate = null;  
				Date date = new Date();  
				// 输出格式: 2015-1-27 00:00:00 大写H为24小时制  
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				formatDate = sdf.format(date);  
				System.out.println(formatDate);  */
				//登陆门户后，插入当前用户信息到在线用户信息表（tonline）中
				IPersistenceDAO dao = getPersistenceDAO("db_query");
				StringBuilder sql = new StringBuilder();
				StringBuilder sql2 = new StringBuilder();
				StringBuilder sql3 = new StringBuilder();
				List list=new ArrayList();
				list.add(userID);
				list.add(userName);
				list.add(IpAddr);
				
				sql.append("insert into tonline (userid,username,userip) values (?,?,?) ");
				sql2.append("select count(1) from tonline where userid=? and username=? and userip=? ");
				sql3.append("insert into twebsite_traffic_statistics (userid,username,userip) values (?,?,?) ");
				try {
					int onLineTotal = dao.queryForInt(sql2.toString(), list);
					logger.debug("onLineTotal="+onLineTotal);
					if (onLineTotal == 0) {
						dao.execute(sql.toString(),list);
					}else{
						logger.debug("该用户信息已经存在AD域的session中，处于在线状态");
					}
					dao.execute(sql3.toString(),list);
				} catch (OptimusException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * 服务器初始化时调用
	 */
	public void contextInitialized(ServletContextEvent evt) {
		logger.debug("服务器启动");
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
	}

	public void sessionDidActivate(HttpSessionEvent arg0) {
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
	}

	/**
	 * session销毁(用户退出系统时会调用)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();
		if (session != null) {
			logger.debug("session销毁：" + session.getId());
			String userID = (String) session.getAttribute(OptimusAuthManager.LOGIN_NAME);
			String userName = (String) session.getAttribute(OptimusAuthManager.SESSION_KEY_USER_NAME);
			String IpAddr = (String) session.getAttribute(OptimusAuthManager.USERIP);
			//登陆门户后，插入当前用户信息到在线用户信息表（tonline）中
			
			IPersistenceDAO dao = getPersistenceDAO("db_query");
			StringBuilder sql = new StringBuilder();
			List list=new ArrayList();
			list.add(userID);
			list.add(userName);
			list.add(IpAddr);
			sql.append("delete from tonline where userid=? and username=? and userip=? ");
			try {
				dao.execute(sql.toString(),list);
			} catch (OptimusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 处理清空session逻辑
			session.removeAttribute(OptimusAuthManager.LOGIN_NAME);
			session.removeAttribute(OptimusAuthManager.USER);
			session.removeAttribute(OptimusAuthManager.USERIP);
			session.removeAttribute(session.getId());
			//session.invalidate();
			//session.invalidate();
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletRequestAttributeEvent evt) {
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}

	public void sessionWillPassivate(HttpSessionEvent arg0) {
	}

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
	}

	public void contextDestroyed(ServletContextEvent evt) {
		logger.debug("服务器关闭");
	}

	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
	}

	public void requestInitialized(ServletRequestEvent arg0) {
	}
	
}
