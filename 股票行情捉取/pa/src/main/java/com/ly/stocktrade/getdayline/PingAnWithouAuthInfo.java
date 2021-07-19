package com.ly.stocktrade.getdayline;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.http.pingan.PingAnHttp;

//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//单个  https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.6314712335455435&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=300193&codeType=4621&type=shsz
//批量  https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random=0.9797709232149874&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&codes=000983%2C002552&codeTypes=4609%2C4614

//https://login.stock.pingan.com/login/index.html?&referUrl=https%3A%2F%2Fm.stock.pingan.com%2Fstatic%2Faccount%2Fresetpwd%2FmodifyPwd.html

@Component
public class PingAnWithouAuthInfo {
	
	private static Logger logger = Logger.getLogger("myhttp");
	public final static String SINGEL_STOCK_URL ="https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=RANDOM&stockCode=STOCKCODE&codeType=CODETYPE&type=shsz";
	public final static String MUTI_STOCK_URL ="https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=RANDOM&stockCode=STOCKCODE&codeType=CODETYPE&type=shsz";
	public final static String DAPAN_INDEX_URL ="https://m.stock.pingan.com/h5quote/quote/getIndexData?random=RANDOM&marketType=shsz";
	
	@Autowired
	private PingAnHttp pingAnHttp;
	
	
	//单个
	public Map getInfo(Map<String,String> codeMap) throws JsonParseException, JsonMappingException, IOException{
		
		
		
		String random = String.format("%.17f", Math.random());
      //  String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getCode()+"&codeType="+stock.getMarketType()+"&type=shsz";
		String code = codeMap.get("code");
		String marketType = codeMap.get("marketType");
		
        String url = SINGEL_STOCK_URL.replace("RANDOM", random).replace("STOCKCODE", code).replace("CODETYPE", marketType);
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
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/quote/main.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		logger.debug("单个的====》 "  +url);
		
		
        String result = pingAnHttp.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 =mapper.readValue(result, Map.class);
		Map map2 = (Map) map1.get("results");
		logger.debug("本日开盘返回的最新的个股票信息===> " + map2);
		return map2;
	}
	
	//自选多只
	@SuppressWarnings({ "rawtypes"})
	public List getStockInfoByBatch(List<Map<String,String>> stocks) {
		String random = String.format("%.17f", Math.random());
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
 		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/quote/main.html"); 
 		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
 		headers.put("X-Requested-With", "XMLHttpRequest");
         
     	StringBuffer stockCodes  = new StringBuffer();
     	StringBuffer stockMarketType  = new StringBuffer();
     	for (int i = 0; i < stocks.size(); i++) {
     		if(i<stocks.size()-1){
     			String code = stocks.get(i).get("code");
     			stockCodes.append(code+",");
     		}else{
     			String code = stocks.get(i).get("code");
     			stockCodes.append(code);
     		}
		}
     	
    	for (int i = 0; i < stocks.size(); i++) {
     		if(i<stocks.size()-1){
     			String marketType = stocks.get(i).get("marketType");
     			stockMarketType.append(marketType+",");
     		}else{
     			String marketType = stocks.get(i).get("marketType");
     			stockMarketType.append(marketType);
     		}
		}
    	logger.info("\n请求的URL开始");
     	//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random="+random+"&&codes=000983%2C002552&codeTypes=4609%2C4614";
     	String url;
    	String result = null;
    	Map map1 = null;
    	List list  = null;
		try {
			url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random="+random+"&&codes="+ URLEncoder.encode(stockCodes.toString(),"UTF-8")+"&codeTypes="+URLEncoder.encode(stockMarketType.toString(),"UTF-8");
			logger.info("URL===>" +url);
			logger.info("\n请求的URL结束");
			result = pingAnHttp.httpGet(url,headers);
			if(result!=null){
				ObjectMapper mapper = new ObjectMapper();  
				map1=mapper.readValue(result, Map.class);
				list = (List) map1.get("results");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//大盘指数
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getDaPanZhiShu() throws JsonParseException, JsonMappingException, IOException{
		String random = String.format("%.17f", Math.random());
		String url = "https://m.stock.pingan.com/h5quote/quote/getIndexData?random="+random+"&marketType=shsz";
		String result = null;
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
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/quote/main.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		result = pingAnHttp.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 = mapper.readValue(result, Map.class);
		ArrayList array = (ArrayList) map1.get("results");
		return array;
	}
}
