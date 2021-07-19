package com.ly.task.expire.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.common.http.PingAnZhengQuanHttpWithSafeReq;
import com.ly.common.util.CookieUtil;
import com.ly.pojo.Account;



@Component
public class DefenderCookieExpire {
	private static Logger logger = Logger.getLogger(DefenderCookieExpire.class);
	
	public final static String LASTESTD_URL = "https://m.stock.pingan.com/pah5trade/quote/stock/STOCK_CODE/real_time_data?random=RANDOM";
	public final static String LASTESTD_URL2 = "https://quote.stock.pingan.com/restapi/nodeserver/quote/realTimeData?_=RANDOM";
	@Autowired
	private PingAnZhengQuanHttpWithSafeReq httpReq;

	
	
	/*public Map getStockLastestInfo(String code, Account ac)  throws Exception{
		Map returnMap = new HashMap();
			String random = String.format("%.17f", Math.random());
			String url = LASTESTD_URL.replace("STOCK_CODE", code).replace("RANDOM", random);
			logger.debug(" 获取实时信息  === >" + url);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// 传输的类型
			//String cookie = "ps_login_token_id=" + ac.getTokenId() + ";ps_login_app_name=" + ac.getAppName();
			
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			logger.debug("====> " +result);
			//System.out.println(result);
			if (result != null) {
				ObjectMapper mapper = new ObjectMapper();
				Map resultMap = mapper.readValue(result, Map.class);
				returnMap = (Map) resultMap.get("results");
				if(resultMap!=null){
					String stockName =(String) returnMap.get("stockName");
					returnMap.put("stockName", stockName.trim());
				}
			}

			return returnMap;
	}*/
	
	public Map getStockLastestInfo(String code, Account ac)  throws Exception{
		//https://www.cnblogs.com/carlo/p/10333283.html
		Map returnMap = new HashMap();
		String random = String.format("%.17f", Math.random());
		String url = LASTESTD_URL2.replace("RANDOM", random);
		logger.debug(" 获取实时信息  === >" + url);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		// 传输的类型
		//String cookie = "ps_login_token_id=" + ac.getTokenId() + ";ps_login_app_name=" + ac.getAppName();
		
		String cookie = CookieUtil.getCookie(ac);
		headers.put("Cookie", cookie);
		headers.put("Host", "m.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
		headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		Map map = new HashMap();
		map.put("version","2.0");
		map.put("channel", "mobileH5");
		map.put("requestId", "d06bcf76-c149-9581-faf4-f6d3a951e4f5");
		map.put("cltplt", "h5");
		map.put("cltver", "1.0");
		map.put("aid","");
		map.put("sid", "");
		map.put("ouid",  "");
		map.put("source",  "");
		//map.put("body[code]",  "SZ000725");
		map.put("body[code]", code);
		
		String result = httpReq.httpPost(url, headers,map);
		logger.debug("====> " +result);
		//System.out.println(result);
		if (result != null) {
			ObjectMapper mapper = new ObjectMapper();
			Map resultMap = mapper.readValue(result, Map.class);
			returnMap = (Map) resultMap.get("results");
			/*if(resultMap!=null){
				String stockName =(String) returnMap.get("stockName");
				returnMap.put("stockName", stockName.trim());
			}*/
		}

		return returnMap;
		
	}
	
}
