package com.ly.http;
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

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
/**
*股票数据调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/21
*https://www.cnblogs.com/0201zcr/p/5725508.html
**/
 
public class JuheHttp {
	//private static Logger logger = Logger.getLogger(JuheDemo.class);
	  private static final Logger logger = Logger.getLogger("juhe");
	   
	   
	   
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="c0fef72d8ef0a9462ff96e9ffe9615b8";
 
    //1.沪深股市
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getRequest1(String code){
    	Map returnMap  = new HashMap();
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/hs";//请求接口地址
        Map params = new HashMap();//请求参数
           // params.put("gid","");//股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009
            params.put("gid",code);//股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009
            params.put("key",APPKEY);//APP Key
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
               // System.out.println(object.get("result"));
            	logger.debug(object.get("result"));
            	JSONArray jsArr = object.getJSONArray("result");
            	JSONObject ob = JSONObject.fromObject(jsArr.get(0));
            	JSONObject ob2 = JSONObject.fromObject(ob.get("data"));
            	//String bu = (String) ob2.get("buyFive");
            	returnMap.put("nowPri", ob2.get("nowPri"));
            	returnMap.put("name", ob2.get("name"));
            	returnMap.put("open", ob2.get("todayStartPri"));
            	returnMap.put("yestodEndPri", ob2.get("yestodEndPri"));
            	returnMap.put("todayMin", ob2.get("todayMin"));
            	returnMap.put("todayMax", ob2.get("todayMax"));
            	returnMap.put("increPer", ob2.get("increPer"));
            	
            	returnMap.put("buyOne", ob2.get("buyOne"));
            	returnMap.put("buyOnePri", ob2.get("buyOnePri"));
            	
            	returnMap.put("buyTwo", ob2.get("buyOne"));
            	returnMap.put("buyTwoPri", ob2.get("buyOnePri"));
            	
            	
            	returnMap.put("buyThree", ob2.get("buyOne"));
            	returnMap.put("buyThreePri", ob2.get("buyOnePri"));
            	
            	
            	returnMap.put("buyFour", ob2.get("buyOne"));
            	returnMap.put("buyFourPri", ob2.get("buyOnePri"));
            	
            	returnMap.put("buyFive", ob2.get("buyFive"));
            	returnMap.put("buyFivePri", ob2.get("buyFivePri"));
            }else{
                //System.out.println(object.get("error_code")+":"+object.get("reason"));
            	logger.debug(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("聚合网返回的数据====>" + returnMap);
        return returnMap;
    }
 
    
 
    //3.美国股市
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getRequest3() {
		String result = null;
		String url = "http://web.juhe.cn:8080/finance/stock/usa";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("gid", "aapl");// 股票代码，如：aapl 为“苹果公司”的股票代码
		params.put("key", APPKEY);// APP Key

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
    
 
 
    public static void main(String[] args) {
    	JuheHttp.getRequest3();
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
    @SuppressWarnings("rawtypes")
	public static String urlencode(Map<String,Object>data) {
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
