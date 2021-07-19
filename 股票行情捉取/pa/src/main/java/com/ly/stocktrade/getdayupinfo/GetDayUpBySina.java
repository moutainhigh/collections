package com.ly.stocktrade.getdayupinfo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.http.CommHttpUtil;

//http://tool.oschina.net/codeformat/json
@Component
public class GetDayUpBySina {
	private static Logger logger = Logger.getLogger("days"); 
	
	// String sh = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=40&sort=changepercent&asc=0&node=sh_a&symbol=&_s_r_a=init";
	// String sz = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=40&sort=changepercent&asc=0&node=sh_a&symbol=&_s_r_a=init";
	// http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=sh_a&symbol=&_s_r_a=init

	// http://vip.stock.finance.sina.com.cn/mkt/#stock_sz_up  //深A涨幅
	// http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=init

	
	
	@Autowired
	private CommHttpUtil httpUtil;


	public String getUp(int page){
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page="+(page+1)+"&num=100&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=init";
		Map headers = new HashMap<String,String>();
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Cache-Control", "no-cache");
		headers.put("Connection", "keep-alive");
		headers.put("Content-type", "application/x-www-form-urlencoded");
		headers.put("Pragma", "no-cache");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		headers.put("Host", "vip.stock.finance.sina.com.cn");
		headers.put("Referer", "http://vip.stock.finance.sina.com.cn/mkt/");
		
		String str = httpUtil.httpGet(url, headers);
		logger.debug("新浪网返回第【"+(page+1)+"】页数据===> " + str);

		return str;
	}

}
