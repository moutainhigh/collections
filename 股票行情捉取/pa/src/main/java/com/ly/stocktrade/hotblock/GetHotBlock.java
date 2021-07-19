package com.ly.stocktrade.hotblock;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.http.pingan.PingAnHttp;

@Component
public class GetHotBlock {
	
	
	private static Logger logger = Logger.getLogger(GetHotBlock.class);
	@Autowired
	private PingAnHttp httpReq;
	
	private static final String HOT_BLOCK_URL = "https://m.stock.pingan.com/h5quote/quote/getBlockData?random=RANDOM&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&count=50&begin=0";

	public void getInfo() {
		String random = String.format("%.17f", Math.random());
		String url = HOT_BLOCK_URL.replace("RANDOM", random);
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
		
		System.out.println("===>" +result);
		logger.info(result);
	}
}
