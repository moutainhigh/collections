package com.ly.task;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
//https://blog.csdn.net/qq_34337272/article/details/81556772
//https://blog.csdn.net/csdn_xuexiaoqiang/article/details/73730649
//https://www.cnblogs.com/lzxianren/p/4754168.html
//https://www.cnblogs.com/leechenxiang/p/6367432.html
//短信http://www.api51.cn/user/sms/add.html

public class JuheSMSDemo {
	
	//import org.apache.log4j.Logger;
	
	
	/*  private static final Logger file = Logger.getLogger("file");
	    private static final Logger register = Logger.getLogger("register");
	    private static final Logger login = Logger.getLogger("login");
	    private static final Logger goldcoin = Logger.getLogger("goldcoin");
	    private static final Logger recharge = Logger.getLogger("recharge");
	    private static final Logger jjj = Logger.getLogger(LoggerUtil.class.getName());
	    private static final Logger FILE = Logger.getLogger("appender1");
	    private static org.apache.log4j.Logger log = Logger.getLogger(LoggerUtil.class);
	*/
	
	
	
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	public static void mobileQuery() throws UnsupportedEncodingException{
		
		String str = URLEncoder.encode("#app#=000983&#code#=6p20","UTF-8");
		
		System.out.println(str);
		String result =null;
		String url ="http://v.juhe.cn/sms/send";//请求接口地址
		Map params = new HashMap();//请求参数
			params.put("mobile","18269215167");//接受短信的用户手机号码
                        params.put("tpl_id","120216");//您申请的短信模板ID，根据实际情况修改
                        params.put("tpl_value",str);//您设置的模板变量，根据实际情况修改
			params.put("key","29862ed83b5c6c17a746870e122193a2");//应用APPKEY(应用详细页查询)
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if(object.getInt("error_code")==0){
				System.out.println(object.get("result"));
			}else{
				System.out.println(object.get("error_code")+":"+object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		//uheDemo.mobileQuery();
		
		String str = "【技术撸公众号】尊敬的会员金健米业，您申请修改密保问题答案【13.11】修改为【0.65】，操作码【31.00】买，请不要泄漏或转发他人。";
		System.out.println(str.length());
		
	}
	
	/**
	 *
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return  网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map params,String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params!= null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	public static String urlencode(Map<String,String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
	
} 