package com.gwssi.common.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwssi.common.util.SQLConfig;

//import cn.gwssi.bjais.txn.gg.sjbcl.xml.DataRuleXML;
/**
 * �Զ�����һЩ�ض�����,·����tp.properties��
 */

public class LoaderListener extends HttpServlet
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig sce) throws javax.servlet.ServletException
	{
		try {
			// �õ�WEBӦ�÷�������·��
			// String rootpath = sce.getServletContext().getRealPath("/");
			// if (rootpath != null) {
			// rootpath = rootpath.replaceAll("\\\\", "/");
			// }
			// else {
			// rootpath = "/";
			// }
			// if (!rootpath.endsWith("/")) {
			// rootpath = rootpath + "/";
			// }
			// try{
			// //TxnClassFactory.getClassLoader().loadClass("cn.gwssi.bjais.common.Constants");
			// }catch(Exception e){
			// e.printStackTrace();
			// }
			// Constants.ROOTPATH = rootpath;
			//		   
			// //classes����·��
			// Constants.CONFIGPATH = rootpath + "WEB-INF/classes/";
			//
			// PropertyConfigurator.configure(Constants.CONFIGPATH+"log4j.properties");
//			System.out.println("rootpath path is:" + Constants.ROOTPATH);
//			System.out.println("Application Run ConfigPath:"
//					+ Constants.CONFIGPATH);
			//	    
			// // //����������Ϣ
			// BJAISConfig.getInstance();
			 SQLConfig.getInstance();
			//            
			// //����XML�����߼���ϵ����
			// System.out.println("===========================");
			// DataRuleXML.getInstance();

			// //ʵ����ȫ�ֱ���Cache����
//			Constants.cacheManage = new CacheManage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy()
	{
		System.out.println("LoaderListener successful shutdown.");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
