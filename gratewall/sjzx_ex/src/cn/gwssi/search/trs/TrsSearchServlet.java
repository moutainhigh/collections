package cn.gwssi.search.trs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.webservice.server.GSGeneralWebService;

public class TrsSearchServlet extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	protected static Logger		logger				= TxnLogger
															.getLogger(TrsSearchServlet.class
																	.getName());	// 日志

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	/**
	 * Constructor of the object.
	 */
	public TrsSearchServlet()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		GSGeneralWebService gWebService = new GSGeneralWebService();
		String svr_code = request.getParameter("svr_code");
		String login_name = request.getParameter("login_name");
		String password = request.getParameter("password");
		String queryStr = request.getParameter("query");
		String rows = request.getParameter("rows");
		String nPage = request.getParameter("nPage");
		Map paraMap = new HashMap();
		if (StringUtils.isNotBlank(svr_code)) {
			paraMap.put("SVR_CODE", svr_code);
		}
		if (StringUtils.isNotBlank(login_name)) {
			paraMap.put("LOGIN_NAME", login_name);
		}
		if (StringUtils.isNotBlank(password)) {
			paraMap.put("PASSWORD", password);
		}
		if (StringUtils.isNotBlank(queryStr)) {
			paraMap.put("queryStr", queryStr);
		}
		if (StringUtils.isNotBlank(rows)) {
			paraMap.put("rows", rows);
		}
		if (StringUtils.isNotBlank(nPage)) {
			paraMap.put("nPage", nPage);
		}
		String xml = XmlToMapUtil.map2Dom(paraMap);
      
		try {
			String ip = this.getClientIp(request);
			String result = gWebService.queryTrsDataByHttp(xml, ip);
			Document doc=null;
			if(result.indexOf("UTF-8")!=-1){
				doc=DocumentHelper.parseText(result);
				response.setCharacterEncoding("UTF-8");
				doc.setXMLEncoding("UTF-8");
			}else if(result.indexOf("GBK")!=-1){
				doc=DocumentHelper.parseText(result);
				response.setCharacterEncoding("GBK");
				doc.setXMLEncoding("GBK");
			}else{
				doc=DocumentHelper.parseText(result);
				response.setCharacterEncoding("UTF-8");
				doc.setXMLEncoding("UTF-8");
			}
			PrintWriter pw = response.getWriter();
			pw.print(doc.asXML());
			pw.close();

		} catch (DBException e) {
			e.printStackTrace();
			this.sendMsg(request, response, "未知错误");
		} catch (TxnException e) {
			e.printStackTrace();
			this.sendMsg(request, response, "未知错误");
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public String getClientIp(HttpServletRequest request)
	{

		String clientIP = request.getHeader("x-forwarded-for");
		if (clientIP == null || clientIP.length() == 0
				|| "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0
				|| "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0
				|| "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}

	private void sendMsg(HttpServletRequest request,
			HttpServletResponse response, String msg)
	{
		String str = "<?xml version='1.0' encoding='gbk'?>";
		response.setContentType("text/xml;charset=GBK");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(str);
			pw.print("<Results>");
			pw.print("<Error>");
			pw.print("<code>");
			pw.print(msg);
			pw.print("</code>");
			pw.print("<desp><![CDATA[");
			pw.print(msg);
			pw.print("]]></desp>");
			pw.print("</Error>");
			pw.print("</Results>");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
