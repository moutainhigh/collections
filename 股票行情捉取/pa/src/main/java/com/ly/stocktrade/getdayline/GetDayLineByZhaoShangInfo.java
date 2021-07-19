package com.ly.stocktrade.getdayline;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.http.zhaoshang.ZhaoShangHttp;
import com.ly.pojo.Stocks;

@Component
public class GetDayLineByZhaoShangInfo {

	private static Logger logger = Logger.getLogger(GetDayLineByZhaoShangInfo.class);
	
	@Autowired
	private ZhaoShangHttp zhaoShangHttp;
	
	public List getBatchList(List<Stocks> sts){
		Date date = new Date();
		//String dateStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		long timeStampLong = date.getTime();
		StringBuffer sb = new StringBuffer();
		StringBuffer cookieBf = new StringBuffer();
		String codeList = null;
		String cookieCodeList = null;
		try {
			cookieBf.append("[");
			for (int i = 0; i < sts.size(); i++) {
				String code =  sts.get(i).getCode();
				String cookieStr = sts.get(i).getCode() ;
				if(code.indexOf("6")==0){
					code = "sh:"+code;
					cookieStr =  "\"sh:"+cookieStr+"\",9";
				}else{
					code = "sz:" + code;
					cookieStr =  "\"sz:"+cookieStr+"\",0";
				}
				
				if(i<sts.size()-1){
					sb.append(code+"|");
					cookieBf.append("["+cookieStr+"],");
				}else{
					sb.append(code);
					cookieBf.append("["+cookieStr+"]");
				}
			}
			cookieBf.append("]");
			codeList= URLEncoder.encode(sb.toString().toUpperCase(), "UTF8");
			cookieCodeList= URLEncoder.encode(cookieBf.toString().toUpperCase(), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		
		String url = "https://hq.cmschina.com/market/json";
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		headers.put("Host", "hq.cmschina.com");
		headers.put("Origin", "https://hq.cmschina.com");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Referer", "https://hq.cmschina.com/web/hq/views/hq/index.html");
		//headers.put("Pragma", "no-cache");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("funcno", "20000");
		params.put("version", "1");
		params.put("field", "1%3A2%3A3%3A21%3A22%3A23%3A24%3A14%3A8%3A13%3A4%3A5%3A9%3A12%3A10%3A11%3A16%3A58%3A6%3A7%3A15%3A17%3A18%3A19%3A25%3A27%3A31%3A28%3A48");
		params.put("timeStamp", timeStampLong+"");
		params.put("stock_list", codeList);
		headers.put("Cookie", "isFirstLoad=true; localZxgArr="+cookieCodeList+"; isTrade=false; isHKTrade=false");
		//params.put("stock_list","SZ%3A000008%7CSZ%3A000001%7CSZ%3A000002%7CSZ%3A000004");
		//headers.put("Cookie", "isFirstLoad=true; localZxgArr=%5B%5B%22SZ:000008%22,0%5D,%5B%22SZ:000001%22,0%5D,%5B%22SZ:000002%22,0%5D,%5B%22SZ:000004%22,0%5D%5D; isTrade=false; isHKTrade=false");
		
		String result = zhaoShangHttp.httpPost(url, headers, params);
		System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> resultMap = null;
		List dayLineInfoList = null;
		if(result!=null){
			try {
				 resultMap = mapper.readValue(result, Map.class);
				 dayLineInfoList = (ArrayList<Map<String, String>>) resultMap.get("results");
				 logger.info("招商证券返回的数据======> " +  dayLineInfoList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dayLineInfoList;
	}
}
