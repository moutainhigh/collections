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
			System.out.println("���������...");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String xml = br.readLine(); // �����ж�ȡ������Ϣ����
			// ִ���Զ�����������������������Ӧresponse
			
			System.out.println("�ͻ�������"+xml);
			if(xml!=null&&xml.equals("socket")){
				pw.println("1");//�������� ���ӳɹ�!
				pw.flush(); // ˢ�»�����
			}else{
				Map param = XmlToMapUtil.dom2Map(xml);// ������ת����Map
				String methodName=(String)param.get(CollectConstants.QNAME);
				ServerServiceCj service= new ServerServiceCj();
				String result=null;
				if(methodName!=null&&methodName.equals("queryData")){
					result=service.queryData(xml);
				}
				result=result.replace("\n", "");
				System.out.println("�������˷��ز�ѯ���"+result);
				pw.println(result);//��������
				pw.flush(); // ˢ�»�����
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
