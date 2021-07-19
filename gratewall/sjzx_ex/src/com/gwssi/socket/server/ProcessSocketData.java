package com.gwssi.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import javax.servlet.ServletContext;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;

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

	public void run() {
		try {
			System.out.println("进入服务器...");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String xml = br.readLine(); // 从流中读取请求消息报文
			// 执行自定义的请求解析方法，生成响应response
			
			System.out.println("客户端请求"+xml);
			if(xml!=null&&xml.equals("socket")){
				pw.println("1");//返回数据 连接成功!
				pw.flush(); // 刷新缓冲区
			}else{
				Map param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
				String methodName=(String)param.get(CollectConstants.QNAME);
				ServerServiceCj service= new ServerServiceCj();
				String result=null;
				if(methodName!=null&&methodName.equals("queryData")){
					result=service.queryData(xml);
				}
				result=result.replace("\n", "");
				System.out.println("服务器端返回查询结果"+result);
				pw.println(result);//返回数据
				pw.flush(); // 刷新缓冲区
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
