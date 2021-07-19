package com.ly.stock.trade.core;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.dao.AccountDao;
import com.ly.http.PingAnHttp;
import com.ly.pojo.Account;


//https://m.stock.pingan.com/html/h5security/trade/index.html#0
//https://login.stock.pingan.com/login/index.html
//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//// https://segmentfault.com/q/1010000006014259生成指定位数的小数
//@Component
public class StockTradeCenter{
	
	private static Logger logger = Logger.getLogger(StockTradeCenter.class);
	
	
	@Autowired
	private PingAnHttp httpReq;
	

	// 获取资产
	public final static String ZI_CHANG_URL = "https://m.stock.pingan.com/pah5trade/account/common/capital?random=RANDOM";
	// 查询我的持仓
	public final static String CHI_CHANG_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/shares?random=RANDOM";
	// 查询股东账号成功===传入股票代码，查询是否是该股票的股东
	public final static String GU_DONG_URL = "https://m.stock.pingan.com/pah5trade/account/common/share_holder?random=RANDOM&exchange_type=EXCHANGE_TYPE&stock_code=STOCK_CODE";
	
	//查询实时股票信息
	public final static String LASTESTD_URL = "https://m.stock.pingan.com/pah5trade/quote/stock/STOCK_CODE/real_time_data?random=RANDOM";
	
	//查询所有的委托信息action_in查询1为未撤单的，2是所有的
	public final static String GET_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/orders?random=RANDOM&action_in=1&start_date=START_DATE&end_date=END_DATE";

	//申请撤单的 
	public final static String WITHDRAW_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/withdraw/ENTRUST?random=RANDOM";
	
	
	//查询可卖数量
	public final static String GET_ENABLE_SELL_TOTAL_URL="https://m.stock.pingan.com/pah5trade/trade/stock/sellable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=2&entrust_prop=0&prev_close=PREV_CLOSE";
	
	//卖委托
	public final static String SELL_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/sell/STOCKNAME?random=RANDOM";
	
	//查询可买数量
	public final static String GET_ENABLE_BUY_TOTAL_URL="https://m.stock.pingan.com/pah5trade/trade/stock/buyable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=1&entrust_prop=0&prev_close=PREV_CLOSE";
	
	//买委托
	public final static String BUY_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/buy/STOCKNAME?random=RANDOM";
	
	//股票所属市场，1上海，2深圳 ,默认深圳市场2，利于判断是6开头的就是上海的
	public String EXCHANGE_TYPE= "2";
	
	// 获取实时信息
	public Map getStockLastestInfo(String code,Account ac) {
		Map returnMap = null;
		try {
			String random = String.format("%.17f", Math.random());
			String url =LASTESTD_URL.replace("STOCK_CODE", code).replace("RANDOM", random);
			logger.debug(" 获取实时信息  === >" +url);
			HashMap<String, String> headers = new HashMap<String, String>();  
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
	        // 传输的类型
			String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url,headers);
			System.out.println(result);
			if(result!=null){
				ObjectMapper mapper = new ObjectMapper();
				Map resultMap = mapper.readValue(result, Map.class);
				returnMap = (Map) resultMap.get("results");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnMap;
	}

	public List getCurrentWeiTuoList(Account ac) {
		// action_in =1 是没有撤单的，2是所有的
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(date);
		String random = String.format("%.17f", Math.random());
		//String url = "https://m.stock.pingan.com/pah5trade/trade/stock/orders?random=0.6654674235981979&action_in=1&start_date=20181124&end_date=20181124";
		String url = GET_WEI_TUO_URL.replace("RANDOM", random).replace("START_DATE", dateStr).replace("END_DATE", dateStr);
		HashMap<String, String> headers = new HashMap<String, String>();  
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive"); 
		headers.put("Cache-Control", "no-cache"); 
        // 传输的类型
		String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
		headers.put("Cookie", cookie);
		headers.put("Host", "m.stock.pingan.com"); 
		headers.put("Pragma", "no-cache"); 
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		String result = httpReq.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList list = null;
		Map map1;
		try {
			if (result != null) {
				map1 = mapper.readValue(result, Map.class);
				System.out.println("[]====>   " + map1);
				list = (ArrayList) map1.get("results");
				System.out.println("=== >" + list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void doCheDan(boolean isCheDan,Account ac) {
		Map infoMap = null;
		if (isCheDan) {
			List list = this.getCurrentWeiTuoList(ac);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					EXCHANGE_TYPE= "2";
					String random = String.format("%.17f", Math.random());
					infoMap = (Map) list.get(i);
					String entrust = (String) infoMap.get("entrust_no");
					String stockCode = (String) infoMap.get("stock_code");
					//String url = "https://m.stock.pingan.com/pah5trade/trade/stock/withdraw/" + entrust + "?random="+ random;
					String url = WITHDRAW_URL.replace("ENTRUST", entrust).replace("RANDOM", random);
					System.out.println(url +"===  >  " + ",所属市场 代码===> " +EXCHANGE_TYPE);
					logger.debug(url +"===  >  " + ",所属市场 代码===> " +EXCHANGE_TYPE);
					Map<String, String> paraMap = new HashMap<String, String>();
					//System.out.println(stockCode + "=========> 6开始位置  " + stockCode.indexOf("6"));
					if (stockCode.indexOf("6") == 0) {
						EXCHANGE_TYPE= "1";
					}
					paraMap.put("exchange_type", EXCHANGE_TYPE);
					HashMap<String, String> headers = new HashMap<String, String>();  
					headers.put("accept", "application/json");
			        headers.put("Accept-Charset", "utf-8");
			        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			        headers.put("Accept-Encoding", "gzip, deflate, br");
			        headers.put("Connection", "keep-alive"); 
					headers.put("Cache-Control", "no-cache"); 
					//headers.put("Content-Length", "15"); 
					headers.put("Content-Type", "application/x-www-form-urlencoded"); 
			        // 传输的类型
					String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
					headers.put("Cookie", cookie);
					headers.put("Host", "m.stock.pingan.com"); 
					headers.put("Pragma", "no-cache"); 
					headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
					headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
					headers.put("X-Requested-With", "XMLHttpRequest");
			     	String result = httpReq.httpPost(url,headers,paraMap);
				}

			}
		}

	}

	//获取可卖数量
	public int getEnableSellNum(String stockCodeStr,String weiTuoPrice,Account ac){
		int total=0;
		try {
			Map map = getStockLastestInfo(stockCodeStr,ac);
			System.out.println("查询到的实时数据信息==>  " +map);
			String stockCode = (String) map.get("stockCode");
			String stockName = (String) map.get("stockName");
			String newPrice = (String) map.get("newPrice");
			String exchangeType = (String) map.get("exchangeType");
			String prevClosePrice = (String) map.get("prevClosePrice");
			String upPrice = (String) map.get("upPrice");//涨停
			String downPrice = (String) map.get("downPrice");//跌停
			String openPrice = (String) map.get("openPrice");//开盘价
			String minPrice = (String) map.get("minPrice");//本日最高价
			String maxPrice = (String) map.get("maxPrice");//本日最低价
			String gudongAccount = null;
			if(stockCode.indexOf("6")==0){
				gudongAccount = ac.getTradeCode().split(",")[0];
			}else{
				gudongAccount = ac.getTradeCode().split(",")[1];
			}
			//public final static String GET_ENABLE_SELL_TOTAL_URL="https://m.stock.pingan.com/pah5trade/trade/stock/sellable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=2&entrust_prop=0&prev_close=PREV_CLOSE";
			String tempStockName = URLEncoder.encode(stockName.toString(),"UTF-8");
			System.out.println(stockName);
			String url = GET_ENABLE_SELL_TOTAL_URL.replace("STOCK_CODE", stockCodeStr).replace("ENTRUST_PRICE", weiTuoPrice).replace("STOCK_NAME", tempStockName).replace("EXCHANGE_TYPE", exchangeType).replace("STOCK_ACCOUNT", gudongAccount).replace("STOCK_CODE", stockCodeStr).replace("PREV_CLOSE", prevClosePrice);
			logger.debug("【查询可卖数量URL】" + url);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
	        // 传输的类型
			String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest"); 
			String result = httpReq.httpGet(url,headers);
			if(result!=null){
				logger.debug("查询可卖数量成功==> " +result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				String resultStr=  (String) restultMap.get("results");
				total = Integer.valueOf(resultStr);
			}
			logger.debug("当前股票名称【"+stockName+"】,【代码】"+stockCode+" ,可卖数量" + total+"股,共 "+total/100+"手");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}
	

	public void doSell(String stockCodeStr, String weiTuoPrice,Account ac) {
		String random = String.format("%.17f", Math.random());
		//int allvaliableBuyNum = this.getEnableSellNum(stockCodeStr, weiTuoPrice);
		int allvaliableBuyNum = 1;
		if (allvaliableBuyNum > 0) {
			String url = SELL_WEI_TUO_URL.replace("STOCK_CODE", stockCodeStr).replace("RANDOM", random);
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("tokenId", "");
			paraMap.put("chnl", "");
			paraMap.put("requestId","");
			paraMap.put("entrust_amount_sum", "100");//持仓量
			paraMap.put("entrust_amount", "100");//委托卖的数量
			paraMap.put("entrust_price", "7.67");
			paraMap.put("stock_name", "广东甘化");
			paraMap.put("exchange_type", "2");
			paraMap.put("stock_account", "0137752409");
			paraMap.put("stock_code", "000576");
			paraMap.put("entrust_bs", "2");
			paraMap.put("entrust_prop", "0");
			paraMap.put("prev_close", "7.61");
			
			HashMap<String, String> headers = new HashMap<String, String>(); 
			headers.put("accept", "application/json");
	        headers.put("Accept-Charset", "utf-8");
	        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
	        headers.put("Accept-Encoding", "gzip, deflate, br");
	        headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
			//headers.put("Content-Length", "15"); 
			headers.put("Content-Type", "application/x-www-form-urlencoded"); 
	        // 传输的类型
			String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			
			
			String result = httpReq.httpPost(url,headers, paraMap);
			System.out.println("=======================\n");
			System.out.println(result);
		}
		

	}
	
	
	
	
	public int getEnableBuyNum(String stockCodeStr,String weiTuoPrice,Account ac){
		int total=0;
		try {
			Map map = getStockLastestInfo(stockCodeStr,ac);
			System.out.println("查询到的实时数据信息==>  " +map);
			String stockCode = (String) map.get("stockCode");
			String stockName = (String) map.get("stockName");
			String newPrice = (String) map.get("newPrice");
			String exchangeType = (String) map.get("exchangeType");
			String prevClosePrice = (String) map.get("prevClosePrice");
			String upPrice = (String) map.get("upPrice");//涨停
			String downPrice = (String) map.get("downPrice");//跌停
			String openPrice = (String) map.get("openPrice");//开盘价
			String minPrice = (String) map.get("minPrice");//本日最高价
			String maxPrice = (String) map.get("maxPrice");//本日最低价
			String gudongAccount =null;
			if(stockCode.indexOf("6")==0){
				gudongAccount = ac.getTradeCode().split(",")[0];
			}else{
				gudongAccount = ac.getTradeCode().split(",")[1];
			}
			//public final static String GET_ENABLE_SELL_TOTAL_URL="https://m.stock.pingan.com/pah5trade/trade/stock/sellable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=2&entrust_prop=0&prev_close=PREV_CLOSE";
			String tempStockName = URLEncoder.encode(stockName.toString(),"UTF-8");
			System.out.println(stockName);
			String url = GET_ENABLE_BUY_TOTAL_URL.replace("STOCK_CODE", stockCodeStr).replace("ENTRUST_PRICE", weiTuoPrice).replace("STOCK_NAME", tempStockName).replace("EXCHANGE_TYPE", exchangeType).replace("STOCK_ACCOUNT", gudongAccount).replace("STOCK_CODE", stockCodeStr).replace("PREV_CLOSE", prevClosePrice);
			logger.debug("【查询可卖数量URL】" + url);
			HashMap<String, String> headers = new HashMap<String, String>();  
			
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
	        // 传输的类型
			String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest"); 
			String result = httpReq.httpGet(url,headers);
			if(result!=null){
				logger.debug("查询可卖数量成功==> " +result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				String resultStr=  (String) restultMap.get("results");
				total = Integer.valueOf(resultStr);
			}
			logger.debug("当前股票名称【"+stockName+"】,【代码】"+stockCode+" ,可卖数量" + total+"股,共 "+total/100+"手");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	



	public void doBuy(String stockCodeStr, String weiTuoPrice,Account ac) {
		String random = String.format("%.17f", Math.random());
		//int allvaliableBuyNum = this.getEnableBuyNum(stockCodeStr, weiTuoPrice);
		int allvaliableBuyNum = 1;
		if (allvaliableBuyNum > 0) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("tokenId", "");
			paraMap.put("chnl", "");
			paraMap.put("requestId","");
			paraMap.put("entrust_amount_sum", "0");//持仓数量
			paraMap.put("entrust_amount", "100");//委托数量
			paraMap.put("entrust_price", "6.22");
			paraMap.put("stock_name", "西山煤电");
			paraMap.put("exchange_type", "2");
			paraMap.put("stock_account", "0137752409");
			paraMap.put("stock_code", "000983");
			paraMap.put("entrust_bs", "1");
			paraMap.put("entrust_prop", "0");
			paraMap.put("prev_close", "6.27");
			
			HashMap<String, String> headers = new HashMap<String, String>(); 
			headers.put("accept", "application/json");
	        headers.put("Accept-Charset", "utf-8");
	        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
	        headers.put("Accept-Encoding", "gzip, deflate, br");
	        headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
			//headers.put("Content-Length", "15"); 
			headers.put("Content-Type", "application/x-www-form-urlencoded"); 
	        // 传输的类型
			String cookie = "ps_login_token_id="+ac.getTokenId()+";ps_login_app_name="+ac.getAppName();
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			
			
			String url = BUY_WEI_TUO_URL.replace("STOCKNAME", "西山煤电").replace("RANDOM", random);
			String result = httpReq.httpPost(url,headers, paraMap);
			System.out.println("=======================\n");
			System.out.println(result);
		}
	}
	

	
}



