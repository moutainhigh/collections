package com.ly.stocktrade.getweek.byauto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.http.pingan.PingAnHttp;
import com.ly.stocktrade.trade.core.StockTradeCore;

@Component
public class GetWeekLineInfoByPingAn {
	private static Logger logger = Logger.getLogger(GetWeekLineInfoByPingAn.class);

	@Autowired
	private PingAnHttp httpReq;
	
	public static final String WEEK_URL= "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=RANDOM&tokenId=&chnl=&requestId=&stockCode=STOCKCODE&codeType=CODETYPE&period=week&day=30";

	public static final String MONTH_URL= "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=RANDOM&tokenId=&chnl=&requestId=&stockCode=STOCKCODE&codeType=CODETYPE&period=month&day=30";
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getWeekLineInfo(String code,String codeType){
		String random = String.format("%.17f", Math.random());
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		String url = WEEK_URL.replace("RANDOM", random).replace("STOCKCODE", code).replace("CODETYPE", codeType);
		logger.debug("获取从平安证券中周线信息  === >" + url);
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
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> resultMap = null;
			try {
				resultMap = mapper.readValue(result, Map.class);
				returnList	 = (ArrayList<Map<String, String>>) resultMap.get("results");
				logger.info("处理从平安证券返回的数据===>"  + returnList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}
	
	
	
	public void getMonthInfo(String code,String codeType){
		String random = String.format("%.17f", Math.random());
		String url = WEEK_URL.replace("RANDOM", random).replace("STOCKCODE", code).replace("CODETYPE", codeType);
		logger.debug(" 获取月线信息  === >" + url);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		// 传输的类型
		headers.put("Host", "m.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		String result = httpReq.httpGet(url, headers);
		if (result != null) {
			ObjectMapper mapper = new ObjectMapper();
			Map resultMap;
			try {
				resultMap = mapper.readValue(result, Map.class);
				Map	resultMapWrap = (Map) resultMap.get("results");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
