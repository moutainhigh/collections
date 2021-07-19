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
			System.out.println("���������...��ѯ��������");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String xml = br.readLine(); // �����ж�ȡ������Ϣ����
			String ip = socket.getLocalAddress().getHostAddress();
			System.out.println("�ͻ���ip================"+ip);
			// ִ���Զ�����������������������Ӧresponse
			
			System.out.println("�ͻ�������================"+xml);
			if(xml!=null&&xml.equals("socket")){
				pw.println("1");//�������� ���ӳɹ�!
				pw.flush(); // ˢ�»�����
			}else{
			
			Map param = XmlToMapUtil.dom2Map(xml);// ������ת����Map
			String methodName=(String)param.get(CollectConstants.QNAME);
			ServerServiceCj serviceCj= new ServerServiceCj();
			String result=null;
			if(methodName!=null&&methodName.equals("queryData")){
			////�ɼ�������������
				result=serviceCj.queryData(xml);
				result=result.replace("\n", "");
				System.out.println("�������˷��ز�ѯ���"+result);
				pw.println(result);//��������
				pw.flush(); // ˢ�»�����
				
			////�ɼ�������������
			}else{
			////////����
			ServerService service= new ServerService();
			result=service.queryData(xml,ip);//��ѯ��������
			result=result.replace("\n", "");
			pw.println(result);//��������
			pw.flush(); // ˢ�»�����
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
