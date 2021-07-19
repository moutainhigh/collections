package cn.gwssi.monitor.collect.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;

public class WLS8RpcServlet extends HttpServlet {
	
	private XmlRpcServletServer server;
	 
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			server  = new XmlRpcServletServer();
			PropertyHandlerMapping phm = new PropertyHandlerMapping();
			phm.addHandler("cn.gwssi.monitor.collect.server.WLS8Collector", cn.gwssi.monitor.collect.server.WLS8CollectorImpl.class);
			  server.setHandlerMapping(phm);
			  // more config of XmlRpcServletServer object     
			  XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl)server.getConfig();
			  serverConfig.setEnabledForExtensions(true);
			  serverConfig.setContentLengthOptional(false);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor of the object.
	 */
	public WLS8RpcServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		server.execute(request, response);
	}



}
