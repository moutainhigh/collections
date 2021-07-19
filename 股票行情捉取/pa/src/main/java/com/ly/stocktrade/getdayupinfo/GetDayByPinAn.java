package com.ly.stocktrade.getdayupinfo;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.http.pingan.PingAnHttp;

//每日涨列表

//@Component
public class GetDayByPinAn {
	private static Logger logger = Logger.getLogger(GetDayByPinAn.class);

	@Autowired
	private PingAnHttp httpReq;
	
	private static final String PINAN_DAY_URL = "https://m.stock.pingan.com/h5quote/quote/getReportData?random=RANDOM&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&marketType=shsz&count=100&begin=0&columnId=risePrice&descOrAsc=desc";

	public void getInfo() {
		String random = String.format("%.17f", Math.random());
		String url = PINAN_DAY_URL.replace("RANDOM", random);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		headers.put("Host", "m.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/quote/main.html");
		headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		String result = httpReq.httpGet(url, headers);
		logger.info(result);
	}
}
