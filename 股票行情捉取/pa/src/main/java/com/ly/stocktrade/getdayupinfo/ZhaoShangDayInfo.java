package com.ly.stocktrade.getdayupinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.common.utils.MyDateUtils;
import com.ly.http.zhaoshang.ZhaoShangHttp;
import com.ly.stocktrade.getweek.byauto.GetWeekLineByZhaoShang;

@Component
public class ZhaoShangDayInfo {
	
	private static Logger logger = Logger.getLogger(ZhaoShangDayInfo.class);
	
	private static final String ZHAO_SHANG_DAY_URL = "https://hq.cmschina.com/market/json?funcno=21000&version=1&sort=1&order=0&type=9%253A0%253A2%253A18&curPage=1&rowOfPage=200&field=1%253A2%253A3%253A21%253A22%253A23%253A24%253A14%253A8%253A13%253A4%253A5%253A9%253A12%253A10%253A11%253A16%253A58%253A6%253A7%253A15%253A17%253A18%253A19%253A25%253A27%253A31%253A28%253A48&timeStamp=TIMESTAMP";

	@Autowired
	private ZhaoShangHttp zhaoShangHttp;
	
	
	public String getInfo() {
		Date date = new Date();
		String dateStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		long timeStampLong = date.getTime();
		String url = ZHAO_SHANG_DAY_URL.replace("TIMESTAMP", String.valueOf(timeStampLong));
		
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("cookie", "isFirstLoad=true; localZxgArr=%5B%5B%22SH:000001%22,%2015%5D,%20%5B%22SZ:399001%22,%207%5D,%20%5B%22SZ:399006%22,%207%5D,%5B%22SH:600999%22,%209%5D,%5B%22HK:06099%22,%2099%5D,%5B%22SK:06099%22,%2098%5D%5D; isTrade=false; isHKTrade=false");
		headers.put("Cache-Control", "no-cache");
		headers.put("Host", "hq.cmschina.com");
		headers.put("Referer", "https://hq.cmschina.com/web/hq/views/hq/index.html");
		headers.put("Pragma", "no-cache");
		
		
		String result = zhaoShangHttp.httpGet(url, headers);
		List infoList = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> resultMap = null;
		if(result!=null){
			 try {
				resultMap = mapper.readValue(result, Map.class);
				 infoList = (ArrayList<Map<String, String>>) resultMap.get("results");
				 logger.info("招商证券返回的数据======> " +  infoList);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return url;
	}
}
