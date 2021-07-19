package com.gwssi.dw.aic.download.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwssi.common.util.DownloadUtil;

/**
 * ��������
 * 
 * @author BarryWei
 * @data 2008-09-09
 */
public class DownloadFileServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig config){

	}

	// ͨ��doPost���������ļ�
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		try{
			DownloadUtil.downloadFile(request, response);
		}catch(Exception e){
			response.setContentType("text/html; charset=GBK");			
			PrintWriter pw = response.getWriter();
			pw.print("<script> alert('�ļ�������'); \n");
			pw.print(" window.history.back()");
			pw.print("</script>");
			pw.close();
		}
	}

	// ͨ��doGet���������ļ�
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		doPost(request,response);
		
	}

}
