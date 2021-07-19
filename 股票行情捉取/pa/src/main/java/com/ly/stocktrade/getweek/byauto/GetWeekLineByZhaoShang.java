package com.ly.stocktrade.getweek.byauto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.common.utils.MyDateUtils;
import com.ly.http.zhaoshang.ZhaoShangHttp;

@Component
public class GetWeekLineByZhaoShang {
	
	private static Logger logger = Logger.getLogger(GetWeekLineByZhaoShang.class);
	
	
	@Autowired
	private ZhaoShangHttp zhaoShangHttp;
	
	
	//private static final String ZHAO_SHANG_WEEK_URL = "https://hq.cmschina.com/market/json?funcno=20033&version=1&stock_list=SZ%253A000002&type=week&count=339&date=20181203&timeStamp=1543809386991";
	private static final String ZHAO_SHANG_WEEK_URL = "https://hq.cmschina.com/market/json?funcno=20033&version=1&stock_list=CODE&type=week&count=30&date=DATE&timeStamp=TIMESTAMP";//count 50 为最近50周
	
	
	public List  getWeekLineInfo(String code,String name){
		Date date = new Date();
		String dateStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		long timeStampLong = date.getTime();
		String tempCode = null;
		if(code.indexOf("6")==0){
			tempCode = "sh:"+code;
		}else{
			tempCode = "sz:" + code;
		}
		try {
			tempCode = URLEncoder.encode(tempCode, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String url = ZHAO_SHANG_WEEK_URL.replace("CODE", tempCode).replace("DATE", dateStr).replace("TIMESTAMP", String.valueOf(timeStampLong));
		
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("cookie", "isFirstLoad=true; localZxgArr=%5B%5B%22SH:000001%22,%2015%5D,%20%5B%22SZ:399001%22,%207%5D,%20%5B%22SZ:399006%22,%207%5D,%5B%22SH:600999%22,%209%5D,%5B%22HK:06099%22,%2099%5D,%5B%22SK:06099%22,%2098%5D%5D; isTrade=false; isHKTrade=true");
		headers.put("Cache-Control", "no-cache");
		headers.put("Host", "hq.cmschina.com");
		headers.put("Pragma", "no-cache");
		//headers.put("X-Requested-With", "XMLHttpRequest");
		//headers.put("Referer", "https://hq.cmschina.com/web/hq/views/hq/hqCenter.html?stockInfo={%22stockType%22:18,%22stockName%22:%22%E6%B7%B1%E4%BF%A1%E6%9C%8D%22,%22market%22:%22SZ%22,%22stockCode%22:%22300454%22}");
		
		
		//{"stockType":0,"stockName":"西山煤电","market":"SZ","stockCode":"000983"}
		StockInfo sb = new StockInfo();
		sb.setStockCode(code);
		sb.setStockName(name);
		sb.setMarket("SZ");
		if(code.indexOf("6")==0){
			sb.setMarket("SH");
			sb.setStockType("9");
		}
		//https://blog.csdn.net/xx5595480/article/details/82113865
		/*指数名字前的“G”是指“贡”字，也就是“贡献”的意思。你点一下可以看见所有股票对该指数的涨跌贡献度。
		股票名字前面“L”是指“联”，也就是指关联品种，是指该股可能有B股、H股，或者是债券、权证什么的。
		股票名称中的英文含义：
		分红类：
		XR,表示该股已除权，购买这样的股票后将不再享有分红的权利;
		DR，表示除权除息，购买这样的股票不再享有送股派息的权利;
		XD，表示股票除息，购买这样的股票后将不再享有派息的权利。
		其他类：
		ST，这是对连续两个会计年度都出现亏损的公司施行的特别处理。ST即为亏损股。
		*ST，是连续三年亏损，有退市风险的意思，购买这样的股票要有比较好的基本面分析能力。
		N，新股上市首日的名称前都会加一个字母N，即英文NEW的意思。
		S*ST，指公司经营连续三年亏损，进行退市预警和还没有完成股改。
		SST，指公司经营连续二年亏损进行的特别处理和还没有完成股改。
		S，还没有进行或完成股改的股票。
		NST，经过重组或股改重新恢复上市的ST股*/
		
		
		 
		 
		
		if(code.indexOf("000")==0||code.indexOf("001")==0){
			sb.setStockType("0");
		}
		if(code.indexOf("002")==0){
			sb.setStockType("2");
		}
		if(code.indexOf("300")==0){
			sb.setStockType("18");
		}
		
		
		 String stockJsonStr = JSON.toJSONString(sb);
		 logger.debug("处理形成的股票对像数据" + stockJsonStr);
		 logger.info("【referer】来源的:  https://hq.cmschina.com/web/hq/views/hq/hqCenter.html?stockInfo="+ stockJsonStr);
		 
		//headers.put("Referer", "https://hq.cmschina.com/web/hq/views/hq/hqCenter.html?stockInfo={%22stockType%22:0,%22stockName%22:%22%E8%A5%BF%E5%B1%B1%E7%85%A4%E7%94%B5%22,%22market%22:%22SZ%22,%22stockCode%22:%22000983%22}");
		headers.put("Referer", "https://hq.cmschina.com/web/hq/views/hq/hqCenter.html?stockInfo="+ stockJsonStr);
		headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		String result = zhaoShangHttp.httpGet(url, headers);
		logger.debug("获取从招商证券中周线信息  === >" + url);
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> resultMap = null;
		List weekInfoList = null;
		if(result!=null){
			try {
				 resultMap = mapper.readValue(result, Map.class);
				 weekInfoList = (ArrayList<Map<String, String>>) resultMap.get("results");
				logger.info("招商证券返回的数据======> " +  weekInfoList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return weekInfoList;
	}
	
	//{"stockType":0,"stockName":"西山煤电","market":"SZ","stockCode":"000983"}
	class StockInfo{
		private String stockType;
		private String stockName;
		private String market;
		private String stockCode;
		
		
		public StockInfo() {
			super();
		}


		public String getStockType() {
			return stockType;
		}
		public void setStockType(String stockType) {
			this.stockType = stockType;
		}
		public String getStockName() {
			return stockName;
		}
		public void setStockName(String stockName) {
			this.stockName = stockName;
		}
		public String getMarket() {
			return market;
		}
		public void setMarket(String market) {
			this.market = market;
		}


		public String getStockCode() {
			return stockCode;
		}


		public void setStockCode(String stockCode) {
			this.stockCode = stockCode;
		}
		
		
	}
}
