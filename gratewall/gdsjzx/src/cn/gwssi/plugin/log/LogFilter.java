package cn.gwssi.plugin.log;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.resource.GetIPAddressUtil;

//import com.gwssi.optimus.core.cache.CacheBlock;
//import com.gwssi.optimus.core.cache.CacheManager;
//import com.gwssi.optimus.core.common.ConfigManager;
import cn.gwssi.plugin.auth.OptimusAuthManager;
import cn.gwssi.plugin.auth.model.User;
import cn.gwssi.resource.Conts;
import cn.gwssi.resource.DateUtil;

/**
 * Servlet Filter implementation class LogFilter
 */
public class LogFilter implements Filter {
	//日志缓存分区号
	private static int logIndex;

	FilterConfig config;  
	//private final static Logger logger = LoggerFactory.getLogger(LogFilter.class);
	private static Logger log = Logger.getLogger("kafka");
	
	/**
	 * Default constructor. 
	 */
	public LogFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.config = null;
	}

	/**
	 * 拦截系统操作日志，原来 存放于缓存redis中14区块，现在修改存放在kafka里面
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)  {
		//ServletContext context = this.config.getServletContext();  
		//long after = System.currentTimeMillis();  
		HttpServletRequest hreq=(HttpServletRequest) req;
		HttpSession session= hreq.getSession();
		TPtSysLogBO logEntity = new TPtSysLogBO();
		if (session==null){
			logEntity.setUserid(Conts.DEFAULT_USERID);
			logEntity.setUsername(Conts.DEFAULT_USERNAME);
		}else{
			User customer=(User)session.getAttribute(OptimusAuthManager.USER);
			if (customer==null){
				logEntity.setUserid(Conts.DEFAULT_USERID);
				logEntity.setUsername(Conts.DEFAULT_USERNAME);
			}else{
				logEntity.setUserid(customer.getUserId());
				logEntity.setUsername(customer.getLoginName());
			}
		}

		UUID logid = UUID.randomUUID();
		logEntity.setLogid(logid.toString());
		logEntity.setLogtype("0");
		logEntity.setOperatetime(DateUtil.DateToStr(new Date()));
		logEntity.setIp(GetIPAddressUtil.getIpAddress(hreq));

		logEntity.setSourceplatform("sourceplatform");

		/*Enumeration<String> headerNames = hreq.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String name = headerNames.nextElement();
			System.out.println(" headerNmea = " + name + ";getHeader = " + hreq.getHeader(name));
		}
		System.out.println(hreq.getMethod());
		System.out.println(hreq.getRemoteHost());
		System.out.println(hreq.getRemotePort());
		System.out.println(hreq.getRemoteUser());
		System.out.println(hreq.getServerName());
		System.out.println(hreq.getServerPort());
		System.out.println(hreq.getScheme());
		System.out.println(hreq.getProtocol());
		System.out.println(hreq.getRemoteAddr()+"[[]]"+hreq.getLocalAddr());*/

		logEntity.setStarttime(DateUtil.DateToStr(new Date()));
		logEntity.setEndtime(DateUtil.DateToStr(new Date()));
		String query = hreq.getQueryString();
		logEntity.setUrl(hreq.getRequestURL().toString()+(query==null?"":"".equals(query)?"":"?"+query));

		Enumeration enu=hreq.getParameterNames();  
		StringBuffer reqStr= new StringBuffer();
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();  
			reqStr .append(paraName);
			reqStr .append(":");
			reqStr .append(hreq.getParameter(paraName));
			reqStr .append("&");
		} 
		logEntity.setReq(reqStr.toString());
		logEntity.setIsfalg("0");

		log.info(logEntity);
		//捕获日志放入redis异常，使得系统不因redis出问题而全部不可用
		/*try{
			CacheBlock cacheBlock = CacheManager.getBlock(logIndex);
			cacheBlock.put(logid.toString(),logEntity);
		}catch(Exception e){
			try {
				throw new Exception("日志缓存出错，请联系数据中心运维人员！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}*/
		try {
			chain.doFilter(req, res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//this.logIndex = Integer.valueOf(ConfigManager.getProperty("logIndex")) ;
		//初始化系统属性配置
		//System.out.println("日志缓存分区号=========="+this.logIndex);
		this.config = config;  
	}

}