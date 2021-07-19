package com.gwssi.report.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.util.NestedServletException;

public class NonlicetFilter implements Filter {
	private Logger log = Logger.getLogger(this.getClass());
	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	{
		System.out.println("已加载过滤器");
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public final void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = ((HttpServletResponse) servletResponse);

		if (!nonlicetFilter(request)) {
			redirectNoRight(request, response);
			return;
		} else {
			UUID errorID = UUID.randomUUID();
			try {
				request = filterRequestParams(request);
				chain.doFilter(request, response);
			} catch (NestedServletException e) {
				log.error("filer NestedServletException err::" + errorID, e);
				throw new ServletException("数据操作异常[" + errorID + "]");

			} catch (Exception e) {
				if (e instanceof SQLException) {
					log.error("filer SQLException err::" + errorID, e);
					throw new ServletException("数据操作异常[" + errorID + "]");
				} else {
					log.error("filer err:else:" + errorID, e);
					try {
						throw e;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			return;
		}

	}

	/**
	 * 过滤参数值特殊字符
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HttpServletRequest filterRequestParams(HttpServletRequest request) {
		String param = "";
		String paramValue = "";

		// 通过继承HttpServletRequestWrapper类装饰HttpServletRequest
		ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(
				request, new HashMap(request.getParameterMap()));
		Map requestParams = wrapRequest.getParameterMap();

		Enumeration<String> params = request.getParameterNames();

		while (params.hasMoreElements()) {

			param = (String) params.nextElement();
			String[] values = request.getParameterValues(param);// 获得每个参数的value

			for (int i = 0; i < values.length; i++) {
				paramValue = values[i];
				paramValue = paramValue.replaceAll("<\\s*(?i)script\\s*>", "");
				paramValue = paramValue.replaceAll("</\\s*(?i)script\\s*>", "");
				// paramValue = paramValue.replaceAll("%", "");
				paramValue = paramValue.replaceAll("(?i)select ", "");
				paramValue = paramValue.replaceAll("(?i)insert ", "");
				paramValue = paramValue.replaceAll("(?i)update ", "");
				paramValue = paramValue.replaceAll("(?i) or ", "");
				values[i] = paramValue.trim();

			}

			// 把转义后的参数放回wrapRequest中
			requestParams.put(param, values);

		}
		return wrapRequest;
	}

	/**
	 * 过滤参数特殊字符 存在非法参数则返回
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean nonlicetFilter(HttpServletRequest request) {
		boolean isTag = true;
		Enumeration<String> params = request.getParameterNames();
		String param = "";
		String[] blankStr = { "<", ">", "'", "\"", ":", "alert", "<script>" };

		while (params.hasMoreElements()) {
			param = (String) params.nextElement();
			for (int i = 0; i < blankStr.length; i++) {
				if (param.indexOf(blankStr[i])>=0||request.getParameter(param).indexOf(blankStr[i]) >= 0) {
					isTag = false;
					return isTag;
				}
			}
		}

		return isTag;
	}
	
	
	private void redirectNoRight(HttpServletRequest req, HttpServletResponse res){
		try {
			res.sendRedirect(req.getContextPath()+"/page/report/query_report_byname.jsp?reportType=0&reportName=safe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
		String regExp = "<\\s*(?i)script\\s*>";
		String regExp2 = "</\\s*(?i)script\\s*>";
		String testString1 = "<script>";
		String testString2 = "<Script>";
		String testString3 = "< Script>";
		String testString4 = "< script>";
		String testString5 = "< script >";
		String testString6 = "< script >";
		String testString7 = "< s cript >";
		String testString8 = "</ Script >";
		System.out.println(testString1.matches(regExp));
		System.out.println(testString2.matches(regExp));
		System.out.println(testString3.matches(regExp));
		System.out.println(testString4.matches(regExp));
		System.out.println(testString5.matches(regExp));
		System.out.println(testString6.matches(regExp));
		System.out.println(testString7.matches(regExp));
		System.out.println(testString8.matches(regExp2));
	}*/
}
