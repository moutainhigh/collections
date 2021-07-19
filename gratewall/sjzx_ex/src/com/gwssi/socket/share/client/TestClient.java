package com.gwssi.socket.share.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.XmlToMapUtil;

public class TestClient {
	
	public void createSocketClient(String host, int port) {
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			
			Map param = new HashMap();
			param.put(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE, "service301");//service119 service148
			param.put("LOGIN_NAME", "W_DS");
			param.put("PASSWORD", "15abdb76");
			param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, "1");
			param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, "5");
			//param.put("USER_TYPE", "TEST");
			param.put("UPDATE_DATE", "2012-06-01,2013-07-01");
			String dom = XmlToMapUtil.map2Dom(param);
			dom=dom.replace("\n", "");
			System.out.println("客户端dom============="+dom);
			socket = new Socket(host, port);
			pw = new PrintWriter(new OutputStreamWriter(socket
					.getOutputStream()));
			pw.println(dom);//客户端传递参数给服务器端
			pw.flush();
			//br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			AnalyCollectFile acf = new AnalyCollectFile();
			String result=br.readLine().toString().replace("<row>", "\n<row>");
			result=result.replace("</row>", "\n</row>");
			acf.appendContext("E://share.txt", result);
			System.out.println("查询数据为============="+result);
			System.out.println("查询数据成功!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestClient client = new TestClient();
		String ipaddr = "160.98.3.153";//172.30.18.50  172.30.7.179
		client.createSocketClient(ipaddr, 5880);
	}

}
