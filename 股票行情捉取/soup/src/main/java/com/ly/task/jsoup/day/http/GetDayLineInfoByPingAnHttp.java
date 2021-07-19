package com.ly.task.jsoup.day.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ly.common.http.PingAnZhengQuanHttpWithSafeReq;

//https://blog.csdn.net/sinat_29774479/article/details/78730359
@Component
public class GetDayLineInfoByPingAnHttp {
	private static Logger logger = Logger.getLogger(GetDayLineInfoByPingAnHttp.class);

	@Autowired
	private PingAnZhengQuanHttpWithSafeReq httpReq;
	
	//public static final String DAY_URL= "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=RANDOM&tokenId=&chnl=&requestId=&stockCode=STOCKCODE&codeType=CODETYPE&period=day&day=30"; //最近30交易日
	public static final String DAY_URL= "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=RANDOM&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=STOCKCODE&codeType=CODETYPE&period=day&day=50"; //最近30交易日

	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getDayLineInfo(String code,String markeType){
		List<Map<String,String>> returnList  = null;
		try {
			String random = String.format("%.17f", Math.random());
			returnList = new ArrayList<Map<String,String>>();
			String url = DAY_URL.replace("RANDOM", random).replace("STOCKCODE", code).replace("CODETYPE", markeType);
			logger.debug("获取从平安证券中日线信息  === >" + url);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			if (result != null) {
				Map<?, ?> resultMap = null;
					resultMap = JSONObject.parseObject(result,Map.class);  
					JSONArray arrays = (JSONArray) resultMap.get("results");
					Map<String,String> maps = null;
					if(arrays!=null&&arrays.size()>0){
						for (int i = 0; i < arrays.size(); i++) {
							maps  = (Map<String, String>) arrays.get(i);
							returnList.add(maps);
						}
					}
					logger.info("处理从平安证券返回的数据===>"  + returnList);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return returnList;
	}
	
	

	
}
