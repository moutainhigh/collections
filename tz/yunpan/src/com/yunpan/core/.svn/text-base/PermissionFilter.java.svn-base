package com.yunpan.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.yunpan.util.StringUtils;

/**
 * 
 * 权限拦截
 * PermissionFilter<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月23日-下午11:46:59 <BR>
 * @version 1.0.0
 *
 */
public class PermissionFilter implements Filter {
	
	FilterConfig config = null;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;//在启动的时候就初始化了
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String url = request.getRequestURI();//请求URL的相对路径
		//告诉用户只要在这里定义的url我都不需要拦截
		//获取请求的方式
		String  method= request.getMethod();
//		if(isValidateUrl(url) && StringUtils.isNotEmpty(url) && url.indexOf("service")!=-1 && method.equalsIgnoreCase("get")){
//			request.getRequestDispatcher("/error.jsp").forward(request, response);
//		}else{
			chain.doFilter(request, response);
//		}
	}
	
	
	/**
	 * 过滤拦截的一个通用方法
	 * 方法名：isValidateUrl<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月28日-上午12:35:49 <BR>
	 * @param url
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	private  boolean isValidateUrl(String url){//service/logout.jsp
		String filterUrl = config.getInitParameter("filterUrls");
		boolean flag = true;
		if(StringUtils.isNotEmpty(filterUrl) && StringUtils.isNotEmpty(url)){
			String[] urls = filterUrl.split(",");
			for (String ustr: urls) {
				if(url.indexOf(ustr) !=-1){
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public void destroy() {
		
	}
}
