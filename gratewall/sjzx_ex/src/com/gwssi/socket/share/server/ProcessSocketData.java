package com.gwssi.socket.share.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import javax.servlet.ServletContext;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.socket.server.ServerServiceCj;





public class ProcessSocketData extends Thread {
	private Socket socket;
	private ServletContext servletContext;

	public ProcessSocketData() {
		super();
	}

	public ProcessSocketData(Socket socket, ServletContext servletContext) {
		this.socket = socket;
		this.servletContext = servletContext;
	}

	public void run()  {
		try {
			System.out.println("进入服务器...查询共享数据");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String xml = br.readLine(); // 从流中读取请求消息报文
			String ip = socket.getLocalAddress().getHostAddress();
			System.out.println("客户端ip================"+ip);
			// 执行自定义的请求解析方法，生成响应response
			
			System.out.println("客户端请求================"+xml);
			if(xml!=null&&xml.equals("socket")){
				pw.println("1");//返回数据 连接成功!
				pw.flush(); // 刷新缓冲区
			}else{
			
			Map param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
			String methodName=(String)param.get(CollectConstants.QNAME);
			ServerServiceCj serviceCj= new ServerServiceCj();
			String result=null;
			if(methodName!=null&&methodName.equals("queryData")){
			////采集暂且留在这里
				result=serviceCj.queryData(xml);
				result=result.replace("\n", "");
				System.out.println("服务器端返回查询结果"+result);
				pw.println(result);//返回数据
				pw.flush(); // 刷新缓冲区
				
			////采集暂且留在这里
			}else{
			////////共享
			ServerService service= new ServerService();
			result=service.queryData(xml,ip);//查询共享数据
			result=result.replace("\n", "");
			pw.println(result);//返回数据
			pw.flush(); // 刷新缓冲区
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		finally {
//			try {
//				if (socket != null && !socket.isClosed())
//					socket.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

}
