package com.ly.core;

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
import com.ly.common.http.PingAnZhengQuanHttpWithSafeReq;
import com.ly.common.util.CookieUtil;
import com.ly.common.util.MyDateUtils;
import com.ly.pojo.Account;

//https://m.stock.pingan.com/html/h5security/trade/index.html#0
//https://login.stock.pingan.com/login/index.html
//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//// https://segmentfault.com/q/1010000006014259生成指定位数的小数
@Component
public class StockTradeCore2 {

	private static Logger logger = Logger.getLogger(StockTradeCore.class);

	@Autowired
	private PingAnZhengQuanHttpWithSafeReq httpReq;

	// 获取资产
	public final static String ZI_CHANG_URL = "https://m.stock.pingan.com/pah5trade/account/common/capital?random=RANDOM";
	// 查询我的持仓
	public final static String CHI_CHANG_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/shares?random=RANDOM";

	//https://login.stg.pingan.com/login/index.html
	//https://login.stock.pingan.com/login/index.html?appChannel=LOGIN&kbChannel=J&showKH=false&showWT=false&showTitle=false&referUrl=https%3A%2F%2Fm.stock.pingan.com%2Fhtml%2Fh5security%2Ftrade%2Findex.html%231
	// 查询实时股票信息
	public final static String LASTESTD_URL = "https://m.stock.pingan.com/pah5trade/quote/stock/STOCK_CODE/real_time_data?random=RANDOM";
	
	// 查询所有的委托信息action_in查询1为未撤单的，2是所有的
	public final static String GET_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/orders?random=RANDOM&action_in=ISGETALL&start_date=START_DATE&end_date=END_DATE";

	// 申请撤单的
	public final static String WITHDRAW_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/withdraw/ENTRUST?random=RANDOM";
	// 查询可卖数量
	public final static String GET_ENABLE_SELL_TOTAL_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/sellable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=2&entrust_prop=0&prev_close=PREV_CLOSE";

	// 卖委托
	public final static String SELL_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/sell/STOCKNAME?random=RANDOM";

	// 查询可买数量
	public final static String GET_ENABLE_BUY_TOTAL_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/buyable_amount/STOCK_CODE?entrust_amount_sum=&entrust_amount=&entrust_price=ENTRUST_PRICE&stock_name=STOCK_NAME&exchange_type=EXCHANGE_TYPE&stock_account=STOCK_ACCOUNT&stock_code=STOCK_CODE&entrust_bs=1&entrust_prop=0&prev_close=PREV_CLOSE";

	// 买委托
	public final static String BUY_WEI_TUO_URL = "https://m.stock.pingan.com/pah5trade/trade/stock/buy/STOCKNAME?random=RANDOM";

	// 股票所属市场，1上海，2深圳 ,默认深圳市场2，利于判断是6开头的就是上海的
	public String EXCHANGE_TYPE = "2";


	//获取股东账号
	public final static String GET_GU_DONG_ACCOUNT = "https://m.stock.pingan.com/pah5trade/account/common/share_holder?random=RANDOM&tokenId=&chnl=&requestId=&exchange_type=EXCHANGE_TYPE&stock_code=CODE";
	
	
	
	//获取股东账号
	public List getGuDongAccount(Account ac,String code) throws Exception {
		ArrayList list = null;
		Map map1 = null;
		
		if (code.indexOf("6") == 0) {
			EXCHANGE_TYPE = "1";
		}
		String random = String.format("%.17f", Math.random());
		//System.out.println(random);
		String url = GET_GU_DONG_ACCOUNT.replace("RANDOM", random).replace("EXCHANGE_TYPE", EXCHANGE_TYPE).replace("CODE", code);
		random = String.format("%.17f", Math.random());
		
		HashMap<String, String> headers = new HashMap<String, String>();  
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive"); 
		headers.put("Cache-Control", "no-cache"); 
        // 传输的类型
		String cookie = CookieUtil.getCookie(ac);
		headers.put("Cookie", cookie);
		headers.put("Host", "m.stock.pingan.com"); 
		headers.put("Pragma", "no-cache"); 
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		String result = httpReq.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();
		if (result != null) {
			map1 = mapper.readValue(result, Map.class);
			list = (ArrayList) map1.get("results");
			logger.debug(list);
		}
		return list ;
	}	
	
	
	
	
	
	
	
	// 获取资产 
	public Map getZiChan(Account ac) {
		String random = String.format("%.17f", Math.random());
		//System.out.println(random);
		String url = ZI_CHANG_URL.replace("RANDOM", random);
		random = String.format("%.17f", Math.random());
		Map returnMap = null;
		
		HashMap<String, String> headers = new HashMap<String, String>();  
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive"); 
		headers.put("Cache-Control", "no-cache"); 
        // 传输的类型
		String cookie = CookieUtil.getCookie(ac);
		headers.put("Cookie", cookie);
		headers.put("Host", "m.stock.pingan.com"); 
		headers.put("Pragma", "no-cache"); 
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		String result = httpReq.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();
		Map resultMap;
		try {
			resultMap = mapper.readValue(result, Map.class);
			returnMap=	(Map) resultMap.get("results");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return returnMap ;
	}
	
	
	// 查询当前持有仓位
	public List getChiChang(Account ac) {
		ArrayList list = null;
		Map map1 = null;
		try {
			String random = String.format("%.17f", Math.random());
			String url = CHI_CHANG_URL.replace("RANDOM", random);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// 传输的类型
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			ObjectMapper mapper = new ObjectMapper();
			if (result != null) {
				map1 = mapper.readValue(result, Map.class);
				list = (ArrayList) map1.get("results");
				logger.debug(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取实时信息---暂时没有用到
	public Map getStockLastestInfo(String code, Account ac) {
		Map returnMap = null;
		try {
			String random = String.format("%.17f", Math.random());
			String url = LASTESTD_URL.replace("STOCK_CODE", code).replace("RANDOM", random);
			logger.debug(" 获取实时信息  === >" + url);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// 传输的类型
			// String cookie = "ps_login_token_id=" + ac.getTokenId() +
			// ";ps_login_app_name=" + ac.getAppName();
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			// System.out.println(result);
			if (result != null) {
				ObjectMapper mapper = new ObjectMapper();
				Map resultMap = mapper.readValue(result, Map.class);
				returnMap = (Map) resultMap.get("results");
				String stockName = (String) returnMap.get("stockName");
				returnMap.put("stockName", stockName.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnMap;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public Map getEnableBuyNum(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap) {
		Map map = null;
		int total = 0;
		try {
			// map = getStockLastestInfo(stockCodeStr, ac);
			map = lastestMap;
			//System.out.println("查询到的实时数据信息==>  " + map);
			logger.debug("查询到的实时数据信息==>  " + map);
			String stockCode = (String) map.get("stockCode");
			String stockName = (String) map.get("stockName");
			String newPrice = (String) map.get("newPrice");
			String exchangeType = (String) map.get("exchangeType");
			String prevClosePrice = (String) map.get("prevClosePrice");
			String upPrice = (String) map.get("upPrice");// 涨停
			String downPrice = (String) map.get("downPrice");// 跌停
			String openPrice = (String) map.get("openPrice");// 开盘价
			String minPrice = (String) map.get("minPrice");// 本日最高价
			String maxPrice = (String) map.get("maxPrice");// 本日最低价
			String gudongAccount = null;
			if (stockCode.indexOf("6") == 0) {
				//gudongAccount = ac.getTradeCode().split(",")[0];
				gudongAccount = ac.getShAccount();
			} else {
				//gudongAccount = ac.getTradeCode().split(",")[1];
				gudongAccount = ac.getSzAccount();
			}
			String tempStockName = URLEncoder.encode(stockName.toString(), "UTF-8");
			//System.out.println(stockName);
			String url = GET_ENABLE_BUY_TOTAL_URL.replace("STOCK_CODE", stockCodeStr).replace("ENTRUST_PRICE", weiTuoPrice).replace("STOCK_NAME", tempStockName).replace("EXCHANGE_TYPE", exchangeType).replace("STOCK_ACCOUNT", gudongAccount).replace("STOCK_CODE", stockCodeStr).replace("PREV_CLOSE", prevClosePrice);
			logger.debug("【查询可买数量URL】" + url);
			HashMap<String, String> headers = new HashMap<String, String>();

			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// 传输的类型
			// String cookie = "ps_login_token_id=" + ac.getTokenId() +
			// ";ps_login_app_name=" + ac.getAppName();
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			if (result != null) {
				logger.debug("查询可买数量成功==> " + result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				String resultStr = (String) restultMap.get("results");
				logger.debug("可买量===》" + resultStr);
				if (resultStr.length() != 0 || resultStr != null) {
					map.put("total", Integer.valueOf(resultStr));//默认全仓
				} else {
					map.put("total", 0);
				}
			}
			logger.debug("当前股票名称【" + stockName + "】,【代码】" + stockCode + " ,可买数量" + total + "股,共 " + total / 100 + "手");
			logger.debug(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public String doBuy(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap,int cangWei) {
		String random = String.format("%.17f", Math.random());
		Map mapInfos = this.getEnableBuyNum(stockCodeStr, weiTuoPrice, ac, lastestMap);
		int num = (int) mapInfos.get("total");
		
		int changWei2 = num/2/100*100;
		int changWei3 = num/3/100*100;
		int changWei4 = num/4/100*100;
		int changWei5 = num/5/100*100;
		logger.debug("当前可买数量===> " + num  + "   买入半仓数量为 "+ changWei2 + "   买入1/3仓数量为 "+ changWei3 + "，买入1/4仓位为"+changWei4+ "，买入1/5仓位为"+changWei5);
		
		//int  calcbuySum = num/cangWei/100*100;
		
		int calcbuySum =  num * cangWei/10/100*100;
		
		logger.info("当前系统买入仓位为===> " + cangWei +",买入数量为===> " + calcbuySum);
		
		String buyNum = String.valueOf(calcbuySum);
		if (num > 0) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("tokenId", "");
			paraMap.put("chnl", "");
			paraMap.put("requestId", "");
			paraMap.put("entrust_amount_sum", "0");// 持仓数量
			//paraMap.put("entrust_amount", mapInfos.get("total").toString());// 委托数量
			paraMap.put("entrust_amount", buyNum);// 委托数量
			paraMap.put("entrust_price", weiTuoPrice);
			paraMap.put("stock_name", (String) mapInfos.get("stockName"));
			if (stockCodeStr.indexOf("6") == 0) {
				EXCHANGE_TYPE = "1";
			}
			paraMap.put("exchange_type", EXCHANGE_TYPE);
			String gudongAccount = null;
			
			if (stockCodeStr.indexOf("6") == 0) {
				gudongAccount = ac.getShAccount();
			} else {
				gudongAccount = ac.getSzAccount();
			}

			paraMap.put("stock_account", gudongAccount);
			paraMap.put("stock_code", stockCodeStr);
			paraMap.put("entrust_bs", "1");
			paraMap.put("entrust_prop", "0");
			paraMap.put("prev_close", (String) mapInfos.get("prevClosePrice"));

			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// headers.put("Content-Length", "15");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");

			String url = BUY_WEI_TUO_URL.replace("STOCKNAME", (String) mapInfos.get("stockName")).replace("RANDOM", random);
			String result = httpReq.httpPost(url, headers, paraMap);
			logger.debug("股票代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】，买入委托成功,买入价为 【" +weiTuoPrice+"】，买入量为【"+buyNum+"】，券商返回的委托数据： " + result);
		} else {
			logger.debug("买入委托失败，当前资金不足,当前代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】,买入价为 【" +weiTuoPrice+"】,可买量为" + num);
		}
		return buyNum;
	}
	
	
	public String doBuyForJiJing(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap,int cangWei) {
		String random = String.format("%.17f", Math.random());
		Map mapInfos = this.getEnableBuyNum(stockCodeStr, weiTuoPrice, ac, lastestMap);
		int num = (int) mapInfos.get("total");
		
		int  calcbuySum = num/cangWei/1000*1000;
		logger.info("当前系统买入仓位为===> " + cangWei +",买入数量为===> " + calcbuySum);
		
		String buyNum = String.valueOf(calcbuySum);
		if (num > 0) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("tokenId", "");
			paraMap.put("chnl", "");
			paraMap.put("requestId", "");
			paraMap.put("entrust_amount_sum", "0");// 持仓数量
			//paraMap.put("entrust_amount", mapInfos.get("total").toString());// 委托数量
			paraMap.put("entrust_amount", buyNum);// 委托数量
			paraMap.put("entrust_price", weiTuoPrice);
			paraMap.put("stock_name", (String) mapInfos.get("stockName"));
			if (stockCodeStr.indexOf("6") == 0) {
				EXCHANGE_TYPE = "1";
			}
			paraMap.put("exchange_type", EXCHANGE_TYPE);
			String gudongAccount = null;
			
			if (stockCodeStr.indexOf("6") == 0) {
				gudongAccount = ac.getShAccount();
			} else {
				gudongAccount = ac.getSzAccount();
			}

			paraMap.put("stock_account", gudongAccount);
			paraMap.put("stock_code", stockCodeStr);
			paraMap.put("entrust_bs", "1");
			paraMap.put("entrust_prop", "0");
			paraMap.put("prev_close", (String) mapInfos.get("prevClosePrice"));

			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// headers.put("Content-Length", "15");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");

			String url = BUY_WEI_TUO_URL.replace("STOCKNAME", (String) mapInfos.get("stockName")).replace("RANDOM", random);
			String result = httpReq.httpPost(url, headers, paraMap);
			logger.debug("股票代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】，买入委托成功,买入价为 【" +weiTuoPrice+"】，买入量为【"+buyNum+"】，券商返回的委托数据： " + result);
		} else {
			logger.debug("买入委托失败，当前资金不足,当前代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】,买入价为 【" +weiTuoPrice+"】,可买量为" + num);
		}
		return buyNum;
	}

	// 获取可卖数量
	@SuppressWarnings("unchecked")
	public Map getEnableSellNum(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap) {
		int total = 0;
		Map map = null;
		try {
			// map = getStockLastestInfo(stockCodeStr, ac);
			map = lastestMap;
			//System.out.println("查询到的实时数据信息==>  " + map);
			logger.debug("查询到的实时数据信息==>  " + map);
			String stockCode = (String) map.get("stockCode");
			String stockName = (String) map.get("stockName");
			String newPrice = (String) map.get("newPrice");
			String exchangeType = (String) map.get("exchangeType");
			String prevClosePrice = (String) map.get("prevClosePrice");
			String upPrice = (String) map.get("upPrice");// 涨停
			String downPrice = (String) map.get("downPrice");// 跌停
			String openPrice = (String) map.get("openPrice");// 开盘价
			String minPrice = (String) map.get("minPrice");// 本日最高价
			String maxPrice = (String) map.get("maxPrice");// 本日最低价
			String gudongAccount = null;
			/*if (stockCode.indexOf("6") == 0) {
				gudongAccount = ac.getTradeCode().split(",")[0];
			} else {
				gudongAccount = ac.getTradeCode().split(",")[1];
			}*/
			if (stockCodeStr.indexOf("6") == 0) {
				//gudongAccount = ac.getTradeCode().split(",")[0];
				gudongAccount = ac.getShAccount();
			} else {
				//gudongAccount = ac.getTradeCode().split(",")[1];
				gudongAccount = ac.getSzAccount();
			}
			
			String tempStockName = URLEncoder.encode(stockName.toString(), "UTF-8");
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
			// String cookie = "ps_login_token_id=" + ac.getTokenId() +
			// ";ps_login_app_name=" + ac.getAppName();
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url, headers);
			if (result != null) {
				logger.debug("查询可卖数量成功==> " + result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				String resultStr = (String) restultMap.get("results");
				logger.debug("可卖量===》" + resultStr);
				if (resultStr != null || resultStr.length() != 0) {
					//total = Integer.valueOf(resultStr)-100;//永远放着100股在站着，方便看盘
					total = Integer.valueOf(resultStr);//永远放着100股在站着，方便看盘
					map.put("total", total);
				} else {
					map.put("total", 0);
				}

			}
			logger.debug("当前股票名称【" + stockName + "】,【代码】" + stockCode + " ,可卖数量" + total + "股,共 " + total / 100 + "手");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	//public int doSell(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap,Setting setting) {
	public int doSell(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap,int selfCangWei) throws UnsupportedEncodingException {
		String random = String.format("%.17f", Math.random());
		Map mapInfos = this.getEnableSellNum(stockCodeStr, weiTuoPrice, ac, lastestMap);
		int num = (int) mapInfos.get("total");
		
		int defaults =  num * selfCangWei/10/100*100;
		
		if (num > 100 && defaults>0) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("tokenId", "");
			paraMap.put("chnl", "");
			paraMap.put("requestId", "");
			//paraMap.put("entrust_amount_sum", (String) mapInfos.get("num"));// 持仓量
		//	paraMap.put("entrust_amount_sum", num+"");// 持仓量
			paraMap.put("entrust_amount_sum",defaults+"");// 持仓量---匹配424行的
			//paraMap.put("entrust_amount", mapInfos.get("total").toString());// 委托卖的数量
		//	paraMap.put("entrust_amount",  num+"");// 委托卖的数量
			paraMap.put("entrust_amount",  defaults+"");// 委托卖的数量
			paraMap.put("entrust_price", weiTuoPrice);
			paraMap.put("stock_name", (String) mapInfos.get("stockName"));
			if (stockCodeStr.indexOf("6") == 0) {
				EXCHANGE_TYPE = "1";
			}
			paraMap.put("exchange_type", EXCHANGE_TYPE);
			String gudongAccount = null;
		/*	if (stockCodeStr.indexOf("6") == 0) {
				gudongAccount = ac.getTradeCode().split(",")[0];
			} else {
				gudongAccount = ac.getTradeCode().split(",")[1];
			}*/
			
			if (stockCodeStr.indexOf("6") == 0) {
				//gudongAccount = ac.getTradeCode().split(",")[0];
				gudongAccount = ac.getShAccount();
			} else {
				//gudongAccount = ac.getTradeCode().split(",")[1];
				gudongAccount = ac.getSzAccount();
			}

			paraMap.put("stock_account", gudongAccount);
			paraMap.put("stock_code", stockCodeStr);
			paraMap.put("entrust_bs", "2");
			paraMap.put("entrust_prop", "0");
			paraMap.put("prev_close", (String) mapInfos.get("prevClosePrice"));

			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			// headers.put("Content-Length", "15");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			// 传输的类型
			// String cookie = "ps_login_token_id=" + ac.getTokenId() +
			// ";ps_login_app_name=" + ac.getAppName();
			String cookie = CookieUtil.getCookie(ac);
			headers.put("Cookie", cookie);
			headers.put("Host", "m.stock.pingan.com");
			headers.put("Pragma", "no-cache");
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String stockName = (String) mapInfos.get("stockName");
			String tempStockName = URLEncoder.encode(stockName.toString(), "UTF-8");
			String url = SELL_WEI_TUO_URL.replace("STOCKNAME", tempStockName).replace("RANDOM", random);
			logger.debug("委托请求的URL===> " + url);
			String result = httpReq.httpPost(url, headers, paraMap);
			logger.debug("股票代码【"+stockCodeStr+"】 ，名称【"+mapInfos.get("stockName")+"】，卖出委托价为【"+weiTuoPrice+"】,卖出量为" + num+"，卖出委托成功，券商返回的委托数据： " + result);
		} else {
			logger.debug("卖出委托失败，当前股票数量不足,当前代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】,卖出委托价为【"+weiTuoPrice+"】,可卖量为" + num);
		}
		return num;
	}

	// 从券商处得到所有的委托记录,用于撤单的或者得到成交的，如果买入成交，则更新委托表的买入价
	public List getCurrentWeiTuoList(Account ac,boolean isGetAllWeiTuo) {
		// action_in =1 是没有撤单的，2是所有的
		Date date = new Date();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(date);*/
		
		String dateStr = MyDateUtils.getTimeString(date, "yyyyMMdd");
		
		
		String random = String.format("%.17f", Math.random());
		// String url =
		// "https://m.stock.pingan.com/pah5trade/trade/stock/orders?random=0.6654674235981979&action_in=1&start_date=20181124&end_date=20181124";
		String url = GET_WEI_TUO_URL.replace("RANDOM", random).replace("START_DATE", dateStr).replace("END_DATE", dateStr);
		
		if(isGetAllWeiTuo){
			url = url.replace("ISGETALL","2");
		}else{
			url = url.replace("ISGETALL","1");
		}
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		// 传输的类型
		// String cookie = "ps_login_token_id=" + ac.getTokenId() +
		// ";ps_login_app_name=" + ac.getAppName();
		String cookie = CookieUtil.getCookie(ac);
		headers.put("Cookie", cookie);
		headers.put("Host", "m.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		String result = httpReq.httpGet(url, headers);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList list = null;
		Map map1;
		try {
			if (result != null) {
				map1 = mapper.readValue(result, Map.class);
				logger.debug("券商返回撤单数据" + map1);
				list = (ArrayList) map1.get("results");
				logger.debug("加工券商撤单返回数据 " + list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	/***
	 * 
	 * @param isCheDan 是否撤单
	 * @param ac 账号
	 * @param isGetAllWeiTuo 是否需要得到所有的委托纪录
	 */
	public void doCheDan(boolean isCheDan, Account ac,boolean isGetAllWeiTuo) {
		Map infoMap = null;
		if (isCheDan) {
			List list = this.getCurrentWeiTuoList(ac,isGetAllWeiTuo);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					EXCHANGE_TYPE = "2";
					String random = String.format("%.17f", Math.random());
					infoMap = (Map) list.get(i);
					String entrust = (String) infoMap.get("entrust_no");
					String stockCode = (String) infoMap.get("stock_code");
					// String url =
					// "https://m.stock.pingan.com/pah5trade/trade/stock/withdraw/"
					// + entrust + "?random="+ random;
					String url = WITHDRAW_URL.replace("ENTRUST", entrust).replace("RANDOM", random);
					//System.out.println(url + "===  >  " + ",所属市场 代码===> " + EXCHANGE_TYPE);
					logger.debug(url + "===  >  " + ",所属市场 代码===> " + EXCHANGE_TYPE);
					Map<String, String> paraMap = new HashMap<String, String>();
					// System.out.println(stockCode + "=========> 6开始位置 " +
					// stockCode.indexOf("6"));
					if (stockCode.indexOf("6") == 0) {
						EXCHANGE_TYPE = "1";
					}
					paraMap.put("exchange_type", EXCHANGE_TYPE);
					HashMap<String, String> headers = new HashMap<String, String>();
					headers.put("accept", "application/json");
					headers.put("Accept-Charset", "utf-8");
					headers.put("Accept-Language", "zh-CN,zh;q=0.9");
					headers.put("Accept-Encoding", "gzip, deflate, br");
					headers.put("Connection", "keep-alive");
					headers.put("Cache-Control", "no-cache");
					// headers.put("Content-Length", "15");
					headers.put("Content-Type", "application/x-www-form-urlencoded");
					// 传输的类型
					// String cookie = "ps_login_token_id=" + ac.getTokenId() +
					// ";ps_login_app_name=" + ac.getAppName();
					String cookie = CookieUtil.getCookie(ac);
					headers.put("Cookie", cookie);
					headers.put("Host", "m.stock.pingan.com");
					headers.put("Pragma", "no-cache");
					headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
					headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
					headers.put("X-Requested-With", "XMLHttpRequest");
					String result = httpReq.httpPost(url, headers, paraMap);
					logger.debug("代码" + stockCode + "撤单数据返回结果  " + result);
				}

			}
		}

	}

	
	public void buyCheDan(Account ac, boolean isGetAllWeiTuo) {
		Map infoMap = null;
		List list = this.getCurrentWeiTuoList(ac, isGetAllWeiTuo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				infoMap = (Map) list.get(i);
				if(infoMap.get("bs_name")!=null){
					String types = (String) infoMap.get("bs_name");
					if(types.equals("买入")){
						EXCHANGE_TYPE = "2";
						String random = String.format("%.17f", Math.random());
					
						String entrust = (String) infoMap.get("entrust_no");
						String stockCode = (String) infoMap.get("stock_code");
						String url = WITHDRAW_URL.replace("ENTRUST", entrust).replace("RANDOM", random);
						logger.debug(url + "===  >  " + ",所属市场 代码===> " + EXCHANGE_TYPE);
						Map<String, String> paraMap = new HashMap<String, String>();
						if (stockCode.indexOf("6") == 0) {
							EXCHANGE_TYPE = "1";
						}
						paraMap.put("exchange_type", EXCHANGE_TYPE);
						HashMap<String, String> headers = new HashMap<String, String>();
						headers.put("accept", "application/json");
						headers.put("Accept-Charset", "utf-8");
						headers.put("Accept-Language", "zh-CN,zh;q=0.9");
						headers.put("Accept-Encoding", "gzip, deflate, br");
						headers.put("Connection", "keep-alive");
						headers.put("Cache-Control", "no-cache");
						headers.put("Content-Type", "application/x-www-form-urlencoded");
						String cookie = CookieUtil.getCookie(ac);
						headers.put("Cookie", cookie);
						headers.put("Host", "m.stock.pingan.com");
						headers.put("Pragma", "no-cache");
						headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html");
						headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
						headers.put("X-Requested-With", "XMLHttpRequest");
						String result = httpReq.httpPost(url, headers, paraMap);
						logger.debug("代码" + stockCode + "撤单数据返回结果  " + result);
					}
				}
			
			}
		}

	}

}
