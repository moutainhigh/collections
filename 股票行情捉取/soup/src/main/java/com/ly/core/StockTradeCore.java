package com.ly.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.common.http.PingAnZhengQuanHttpWithSafeReq;
import com.ly.common.util.CookieUtil;
import com.ly.pojo.Account;

//https://m.stock.pingan.com/html/h5security/trade/index.html#0
//https://login.stock.pingan.com/login/index.html
//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//// https://segmentfault.com/q/1010000006014259生成指定位数的小数

@Component
public class StockTradeCore {

	private static Logger logger = Logger.getLogger(StockTradeCore.class);
	
	
	@Autowired
	private PingAnZhengQuanHttpWithSafeReq httpReq;
	//https://login.stg.pingan.com/login/index.html
	
	public   String CHI_CHANG_URL = "https://m.stock.pingan.com/restapi/nodeserver/z/trade/shares?_=RANDOM";
	
	//股东账号URL
	public   String GET_GU_DONG_ACCOUNT =  "https://m.stock.pingan.com/restapi/nodeserver/z/trade/share_holder?_=RANDOM";
	
	// 查询当前未撤单的委托列表
	public   String ORDER_LIST = "https://m.stock.pingan.com/restapi/nodeserver/z/trade/orders_list?_=RANDOM";

	// 撤单委托请求
	public   String CANCEL_ORDER = "https://m.stock.pingan.com/restapi/nodeserver/z/trade/cancel_order?_=RANDOM";

	// 股票所属市场，1上海，2深圳 ,默认深圳市场2，利于判断是6开头的就是上海的
	public String EXCHANGE_TYPE = "2";

	private String GET_ENABLE_BUY_AMOUNT_URL ="https://m.stock.pingan.com/restapi/nodeserver/z/trade/buyable_amount?_=RANDOM";

	private String BUY_AND_SELL_WEI_TUO="https://m.stock.pingan.com/restapi/nodeserver/z/trade/entrust?_=RANDOM";

	private String GET_ENABLE_SELL_AMOUNT_URL ="https://m.stock.pingan.com/restapi/nodeserver/z/trade/sellable_amount?_=RANDOM";
	
	//当日委托
	private String CURRENT_ORDER ="https://m.stock.pingan.com/restapi/nodeserver/z/trade/curr_order?_=RANDOM";
	//当日成功
	private String CURRENT_DEAL ="https://m.stock.pingan.com/restapi/nodeserver/z/trade/curr_deals?_=RANDOM";

	//当前资产
	private String ZI_CHANG = "https://m.stock.pingan.com/restapi/nodeserver/z/trade/capital?_=RANDOM";
	public Map<String, String> getHeaderMap(Account ac){
		Map<String, String> headers = new HashMap<String, String>();
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
		return headers;
	}
	
	/**
	 * 
	 * @param code
	 * @param gudongAccount
	 * @param weiTuoPrice
	 * @param isGetAmout
	 * @param direction
	 * @param num
	 * @return
	 */
	public Map jsonParamsMapBodyHasTradeInfo(String code,String gudongAccount,String weiTuoPrice,boolean isGetAmout,String direction,String num){
		
		
		if (code.indexOf("6") == 0) {
			EXCHANGE_TYPE = "1";
		}
		
		Map params = new HashMap();
		params.put("version","2.0");
		params.put("channel","mobileH5");
		params.put("requestId","11c02693-e76c-273d-d2d6-4d78756b2e8f");
		params.put("cltplt","h5");
		params.put("cltver","1.0");
		params.put("aid","null");
		params.put("sid","null");
		params.put("ouid","null");
		params.put("source","null");
		
		Map childMap = new HashMap();
		childMap.put("chnl", "H5");
		childMap.put("op_entrust_way", "W");
		//childMap.put("exchange_type", "2");
		childMap.put("stock_account",gudongAccount);
		childMap.put("exchange_type", EXCHANGE_TYPE);
		//childMap.put("stock_account", "0137752409");
		//childMap.put("stock_code", "000725");
		childMap.put("stock_code", code);
		//childMap.put("entrust_prop", "0");
		childMap.put("entrust_prop", "0");
		//childMap.put("entrust_price", "3.89");
		childMap.put("entrust_price", weiTuoPrice);
		
		if(isGetAmout){
			if("BUY".equals(direction)){
				childMap.put("entrust_bs","1");
				childMap.put("entrust_amount",num);
			}else{
				childMap.put("entrust_bs","2");
				childMap.put("entrust_amount",num);
			}
		}
		
		params.put("body",childMap);
		
		return params;
	}
	
	public Map jsonParamsMapForCheDan(String stockCode,String no){
		if (stockCode.indexOf("6") == 0) {
			EXCHANGE_TYPE = "1";
		}
		
		Map params = new HashMap();
		params.put("version","2.0");
		params.put("channel","mobileH5");
		params.put("requestId","11c02693-e76c-273d-d2d6-4d78756b2e8f");
		params.put("cltplt","h5");
		params.put("cltver","1.0");
		params.put("aid","null");
		params.put("sid","null");
		params.put("ouid","null");
		params.put("source","null");
		
		Map childMap = new HashMap();
		childMap.put("chnl", "H5");
		childMap.put("op_entrust_way", "W");
		//childMap.put("exchange_type", "2");
		childMap.put("exchange_type", EXCHANGE_TYPE);
		//childMap.put("stock_account", "0137752409");
		childMap.put("entrust_no", no);
		params.put("body",childMap);
		
		return params;
	}
	
	
	
	public Map jsonParamsMapBodyNotTradeInfo(){
		Map params = new HashMap();
		params.put("version","2.0");
		params.put("channel","mobileH5");
		params.put("requestId","11c02693-e76c-273d-d2d6-4d78756b2e8f");
		params.put("cltplt","h5");
		params.put("cltver","1.0");
		params.put("aid","null");
		params.put("sid","null");
		params.put("ouid","null");
		params.put("source","null");
		
		Map childMap = new HashMap();
		childMap.put("chnl", "H5");
		params.put("body",childMap);
		
		return params;
	}
	//获取股东账号
	public List getGuDongAccount(Account ac,String code) throws Exception {
		ArrayList list = null;
		Map map1 = null;
		
		if (code.indexOf("6") == 0) {
			EXCHANGE_TYPE = "1";
		}
		String random = String.format("%.17f", Math.random());
		//System.out.println(random);
		String url = GET_GU_DONG_ACCOUNT.replace("RANDOM", random);
		random = String.format("%.17f", Math.random());
		
		Map<String, String> headers = this.getHeaderMap(ac);
        // 传输的类型
		String result = httpReq.httpGet(url,headers);
		ObjectMapper mapper = new ObjectMapper();
		if (result != null) {
			map1 = mapper.readValue(result, Map.class);
			list = (ArrayList) map1.get("results");
			logger.debug(list);
		}
		return list ;
	}	
	
	
	
	
	
	public Map<String, String> getZiChan(Account ac) {
		Map returnMap = null;
		ArrayList list = null;
		Map map1 = null;
		try {
			String random = String.format("%.17f", Math.random());
			String url = ZI_CHANG.replace("RANDOM", random);
			Map<String, String> headers = this.getHeaderMap(ac);
			Map params = this.jsonParamsMapBodyNotTradeInfo();
			String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
			System.out.println("===>" +result);
			ObjectMapper mapper = new ObjectMapper();
			if (result != null) {
				map1 = mapper.readValue(result, Map.class);
				list = (ArrayList) map1.get("results");
				logger.debug(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		returnMap = (Map) list.get(0);
		return returnMap;
	}
	
	// 查询当前持有仓位
	public List getChiChang(Account ac) {
		ArrayList list = null;
		Map map1 = null;
		try {
			String random = String.format("%.17f", Math.random());
			String url = CHI_CHANG_URL.replace("RANDOM", random);
			Map<String, String> headers = this.getHeaderMap(ac);
			
			Map params = this.jsonParamsMapBodyNotTradeInfo();
			
			String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
			System.out.println("===>" +result);
			
			
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


	@SuppressWarnings({ "unused", "unchecked" })
	public Map getEnableBuyNum(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap) {
		Map map = null;
		int total = 0;
		String random = String.format("%.17f", Math.random());
		
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
		//	String tempStockName = URLEncoder.encode(stockName.toString(), "UTF-8");
			//System.out.println(stockName);
			String url = GET_ENABLE_BUY_AMOUNT_URL.replace("RANDOM", random);
			logger.debug("【查询可买数量URL】" + url);
			Map<String, String> headers = this.getHeaderMap(ac);
			headers.put("content-type", "application/json");
			
			
			Map params = this.jsonParamsMapBodyHasTradeInfo(stockCode,gudongAccount,weiTuoPrice,false,"BUY","0");
			
			String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
			if (result != null) {
				logger.debug("查询可买数量成功==> " + result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				ArrayList resultStr =  (ArrayList) restultMap.get("results");
				System.out.println(resultStr);
				logger.debug("可买量===》" + resultStr);
				Map returnMapWrap = (Map) resultStr.get(0);
				String amout = (String) returnMapWrap.get("enable_amount");
				map.put("total",amout);//默认全仓
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
		
		String amout = (String) mapInfos.get("total");
		
		int num = Integer.valueOf(amout) ;
		
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
			if (stockCodeStr.indexOf("6") == 0) {
				EXCHANGE_TYPE = "1";
			}
			String gudongAccount = null;
			
			if (stockCodeStr.indexOf("6") == 0) {
				gudongAccount = ac.getShAccount();
			} else {
				gudongAccount = ac.getSzAccount();
			}

			
			Map map = lastestMap;
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
			
			Map params = this.jsonParamsMapBodyHasTradeInfo(stockCode,gudongAccount,weiTuoPrice,true,"BUY",buyNum);
			
			
			System.out.println(params);
			
			Map<String, String> headers = this.getHeaderMap(ac);
			String url = BUY_AND_SELL_WEI_TUO.replace("RANDOM", random);
			String result = httpReq.httpPost(url, headers, JSON.toJSONString(params));
			logger.debug("股票代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】，买入委托成功,买入价为 【" +weiTuoPrice+"】，买入量为【"+buyNum+"】，券商返回的委托数据： " + result);
		} else {
			logger.debug("买入委托失败，当前资金不足,当前代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】,买入价为 【" +weiTuoPrice+"】,可买量为" + num);
		}
		return buyNum;
	}
	
	
	
	public void doBuy(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap) {
		String random = String.format("%.17f", Math.random());
		
		//String amout = (String) mapInfos.get("total");
		
		//int num = Integer.valueOf(amout) ;
		
			String buyNum = "100";
			if (stockCodeStr.indexOf("6") == 0) {
				EXCHANGE_TYPE = "1";
			}
			String gudongAccount = null;
			
			if (stockCodeStr.indexOf("6") == 0) {
				gudongAccount = ac.getShAccount();
			} else {
				gudongAccount = ac.getSzAccount();
			}

			
			Map map = lastestMap;
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
			
			Map params = this.jsonParamsMapBodyHasTradeInfo(stockCode,gudongAccount,weiTuoPrice,true,"BUY",buyNum);
			
			
			System.out.println(params);
			
			Map<String, String> headers = this.getHeaderMap(ac);
			String url = BUY_AND_SELL_WEI_TUO.replace("RANDOM", random);
			String result = httpReq.httpPost(url, headers, JSON.toJSONString(params));
			logger.debug("委托成功===》"  +result);
			logger.info("委托成功===》"  +result);
			//logger.debug("股票代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】，买入委托成功,买入价为 【" +weiTuoPrice+"】，买入量为【"+buyNum+"】，券商返回的委托数据： " + result);
	}


	// 获取可卖数量
	@SuppressWarnings("unchecked")
	public Map getEnableSellNum(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap) {
		int total = 0;
		Map map = null;
		String random = String.format("%.17f", Math.random());
		try {
			// map = getStockLastestInfo(stockCodeStr, ac);
			map = lastestMap;
			//System.out.println("查询到的实时数据信息==>  " + map);
			logger.debug("查询到的实时数据信息==>  " + map);
			String stockCode = (String) map.get("stockCode");
			String stockName = (String) map.get("stockName");
			//System.out.println("查询到的实时数据信息==>  " + map);
			String newPrice = (String) map.get("newPrice");
			String exchangeType = (String) map.get("exchangeType");
			String prevClosePrice = (String) map.get("prevClosePrice");
			String upPrice = (String) map.get("upPrice");// 涨停
			String downPrice = (String) map.get("downPrice");// 跌停
			String openPrice = (String) map.get("openPrice");// 开盘价
			String minPrice = (String) map.get("minPrice");// 本日最高价
			String maxPrice = (String) map.get("maxPrice");// 本日最低价
			String gudongAccount = null;
			if (stockCodeStr.indexOf("6") == 0) {
				//gudongAccount = ac.getTradeCode().split(",")[0];
				gudongAccount = ac.getShAccount();
			} else {
				//gudongAccount = ac.getTradeCode().split(",")[1];
				gudongAccount = ac.getSzAccount();
			}
			
			
			String url = GET_ENABLE_SELL_AMOUNT_URL.replace("RANDOM", random);
			Map headers = this.getHeaderMap(ac);
			headers.put("content-type", "application/json");
			
			
			Map params = this.jsonParamsMapBodyHasTradeInfo(stockCode,gudongAccount,weiTuoPrice,false,"SALE","0");
			
			
			String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
			if (result != null) {
				logger.debug("查询可买数量成功==> " + result);
				ObjectMapper mapper = new ObjectMapper();
				Map restultMap = mapper.readValue(result, Map.class);
				ArrayList resultStr =  (ArrayList) restultMap.get("results");
				System.out.println(resultStr);
				logger.debug("可买量===》" + resultStr);
				Map returnMapWrap = (Map) resultStr.get(0);
				String amout = (String) returnMapWrap.get("enable_amount");
				map.put("total",amout);//默认全仓
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public int doSell(String stockCodeStr, String weiTuoPrice, Account ac, Map lastestMap,int cangWei) throws UnsupportedEncodingException {
			String random = String.format("%.17f", Math.random());
			Map mapInfos = this.getEnableSellNum(stockCodeStr, weiTuoPrice, ac, lastestMap);
			
			String amout = (String) mapInfos.get("total");
			
			int num = Integer.valueOf(amout) ;
			int defaults =  num * cangWei/10/100*100;
			
			
			if (num > 0) {
				if (stockCodeStr.indexOf("6") == 0) {
					EXCHANGE_TYPE = "1";
				}
				String gudongAccount = null;
				
				if (stockCodeStr.indexOf("6") == 0) {
					gudongAccount = ac.getShAccount();
				} else {
					gudongAccount = ac.getSzAccount();
				}

				
				Map map = lastestMap;
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
				
				
				Map params = this.jsonParamsMapBodyHasTradeInfo(stockCode,gudongAccount,weiTuoPrice,true,"SELL",defaults+"");
				
				
				Map<String, String> headers = this.getHeaderMap(ac);
				String url = BUY_AND_SELL_WEI_TUO.replace("RANDOM", random);
				String result = httpReq.httpPost(url, headers, JSON.toJSONString(params));
				logger.debug("股票代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】，卖出委托成功,卖出价为 【" +weiTuoPrice+"】，卖出量为【"+defaults+"】，券商返回的委托数据： " + result);
			} else {
				logger.debug("卖出委托失败，当前股票数量不足,当前代码【" + stockCodeStr + "】，名称【" + mapInfos.get("stockName") + "】,卖出价为 【" +weiTuoPrice+"】,可卖量为" + num);
			}
			return defaults;
	}

	// 从券商处得到所有的委托记录,用于撤单的或者得到成交的，如果买入成交，则更新委托表的买入价
	public List getCurrentWeiTuoList(Account ac) throws Exception {
		String random = String.format("%.17f", Math.random());
		String url = ORDER_LIST.replace("RANDOM", random);
		logger.debug("【当前挂单位列表】" + url);
		Map<String, String> headers = this.getHeaderMap(ac);
		headers.put("content-type", "application/json");
		Map params = this.jsonParamsMapBodyNotTradeInfo();
		List resultStr  = null;
		
		String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
		if (result != null) {
			logger.debug("查询可买数量成功==> " + result);
			ObjectMapper mapper = new ObjectMapper();
			Map restultMap = mapper.readValue(result, Map.class);
			resultStr=  (ArrayList) restultMap.get("results");
		}
		return resultStr;
	}

	/***
	 * 
	 * @param isCheDan 是否撤单
	 * @param ac 账号
	 * @param isGetAllWeiTuo 是否需要得到所有的委托纪录
	 * @throws Exception 
	 */
	public void cheDan(String stockCodeStr, Account ac) throws Exception {
		Map infoMap = null;
		String random = String.format("%.17f", Math.random());
		List list = this.getCurrentWeiTuoList(ac);
		String url = CANCEL_ORDER.replace("RANDOM", random);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				Map map = (Map) list.get(i);
				String entrust_no = (String) map.get("entrust_no");
				String stockCheDan = (String) map.get("stock_code");
				
				
				Map<String, String> headers = this.getHeaderMap(ac);
				headers.put("content-type", "application/json");

				String gudongAccount = null;

				if (stockCodeStr.indexOf("6") == 0) {
					gudongAccount = ac.getShAccount();
				} else {
					gudongAccount = ac.getSzAccount();
				}
				
				if(stockCodeStr.equals(stockCheDan)){
					Map params = this.jsonParamsMapForCheDan(stockCodeStr,entrust_no);
					String result = httpReq.httpPost(url, headers, JSON.toJSONString(params));
				}
			}

		}

	}
	
	
	public List currentOrder(Account ac) throws Exception {
		String random = String.format("%.17f", Math.random());
		String url = CURRENT_ORDER.replace("RANDOM", random);
		logger.debug("【当前委托列表】" + url);
		Map<String, String> headers = this.getHeaderMap(ac);
		headers.put("content-type", "application/json");
		Map params = this.jsonParamsMapBodyNotTradeInfo();
		List resultStr  = null;
		String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
		logger.info("查询委托列表成功==> " + result);
		logger.info("查询委托列表成功==> " + result);
		if (result != null) {
			ObjectMapper mapper = new ObjectMapper();
			Map restultMap = mapper.readValue(result, Map.class);
			resultStr=  (ArrayList) restultMap.get("results");
		}
		return resultStr;
	}

	
	public List currentDeal(Account ac) throws Exception {
		String random = String.format("%.17f", Math.random());
		String url = CURRENT_DEAL.replace("RANDOM", random);
		logger.debug("【当日成交列表】" + url);
		Map<String, String> headers = this.getHeaderMap(ac);
		headers.put("content-type", "application/json");
		Map params = this.jsonParamsMapBodyNotTradeInfo();
		List resultStr  = null;
		String result = httpReq.httpPost(url, headers,JSON.toJSONString(params));
		if (result != null) {
			logger.debug("查询可买数量成功==> " + result);
			ObjectMapper mapper = new ObjectMapper();
			Map restultMap = mapper.readValue(result, Map.class);
			resultStr=  (ArrayList) restultMap.get("results");
		}
		return resultStr;
	}

	

}
