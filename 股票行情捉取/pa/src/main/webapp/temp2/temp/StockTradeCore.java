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
import com.ly.dat.util.ObjectDatUtil;
import com.ly.http.PingAnHttp;
import com.ly.pojo.Stock;


//https://m.stock.pingan.com/html/h5security/trade/index.html#0
//https://login.stock.pingan.com/login/index.html
//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//// https://segmentfault.com/q/1010000006014259生成指定位数的小数
//本类先不用的
public class StockTradeCore {
	
	private static Logger logger = Logger.getLogger(StockTradeCore.class);
	@Autowired
	private PingAnHttp httpReq;

	public final static String COOKIE_FILE_PATH ="c://_____a_config//cookie.dat";
	public final static String SHEN_ZHEN_ZHANG_HU_FILEPATH ="c://_____a_config//sz.dat";
	public final static String SHANG_HAI_ZHANG_HU_FILEPATH ="c://_____a_config//sh.dat";
	
	//进行登录
	public final static String LOGIN_URL = "https://login.stock.pingan.com/loginservice/getPublicKeyModulusAndExponent";
	public final static String LOGIN_LOGIN_SERVICE_URL = "https://login.stock.pingan.com/loginservice/login";
	
	
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
	
	
	
	//生成cookie信息，因为网站上只能使用1天
	public void createSettingInfo(String cookie,String shenZhen,String shangHai){
		try {
			ObjectDatUtil.object2Dat(cookie, COOKIE_FILE_PATH);
			ObjectDatUtil.object2Dat(shenZhen, SHEN_ZHEN_ZHANG_HU_FILEPATH);
			ObjectDatUtil.object2Dat(shangHai, SHANG_HAI_ZHANG_HU_FILEPATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SettingInfo getSettingInfo(){
		String cookie = ObjectDatUtil.dat2Object(COOKIE_FILE_PATH, String.class);
		String shen_Zhen = ObjectDatUtil.dat2Object(SHEN_ZHEN_ZHANG_HU_FILEPATH, String.class);
		String shang_hai = ObjectDatUtil.dat2Object(SHANG_HAI_ZHANG_HU_FILEPATH, String.class);
		SettingInfo setting = new SettingInfo(cookie, shen_Zhen, shang_hai);
		System.out.println(setting.getCookie());
		System.out.println(setting.getShen_Zhen());
		System.out.println(setting.getShang_hai());
		return setting;
	}
	
	
	
	//登录https://blog.csdn.net/rise511/article/details/79234539  https://www.cnblogs.com/proli/p/8630590.html
	public void doLogin(){
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		
		headers.put("Cache-Control", "no-cache");
		
		headers.put("Connection", "keep-alive"); 
		headers.put("Origin", "https://login.stock.pingan.com"); 
		headers.put("Content-Type", "application/json;charset=UTF-8"); 
		headers.put("Cookie", "pa_stock_client_id=cnsz045160|19633900988279615|f951e044-ddd2-407a-8de4-b91bbc03235d; WEBTRENDS_ID=4.0.4.16-1913616288.30704847; paid_test=d0c82966-ab72-34ae-59ab-cac9cb1794ee; PHONEX_OMM_INTF_ESB_TICKET=M15ea33bc82f6481793aeff8a6c5fd2a6; BIGipServersis-tradesso-login-com_30075_PrdPool=!O2pVmNLKV6J4mou3WPIL4ff4Z7fFeaI4p9vfb7GcS54bqi2GfcPizCACTZT0UsCQR6IddVIwDt3iQAA=; inner_media=https://stock.pingan.com/-; WT-FPC=id=4.0.4.16-1913616288.30704847:lv=1543235430447:ss=1543233666699:fs=1543157961597:pn=16:vn=2; ps_login_app_name=PA18; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; WT-FPC=id=4.0.4.16-1913616288.30704847:lv=1543235465053:ss=1543233666699:fs=1543157961597:pn=18:vn=2; ps_login_token_id=N_2924A603023E47F92E3B6673565EC9FF0C8CB5A1305BD2D421E36EF02F53357388561EB09BF0ABEAC3200DA156140E07B758ABD981D1B137B64F175749C0C54F8CCD1A704F00CB9A");
		headers.put("Host", "login.stock.pingan.com");
		headers.put("Pragma", "no-cache");
		headers.put("Referer", "https://login.stock.pingan.com/login/index_pc.html?referUrl=https%3A%2F%2Fstock.pingan.com%2F&kbChannel=J&accountType=1|2|3|4");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		
		String url = "https://login.stock.pingan.com/loginservice/getPublicKeyModulusAndExponent";
		Map<String,String> paraMap = new HashMap<String,String>();
	 	paraMap.put("appChannel", "H5");
		String result = httpReq.httpPostDoLogin(url, headers, paraMap);
		url =LOGIN_LOGIN_SERVICE_URL;
		
		
		httpReq.httpPostDoLoginRedirect(url, headers, paraMap);
	}
	
	
	
	// 获取资产
	public void getZiChan() {
		String random = String.format("%.17f", Math.random());
		System.out.println(random);
		String url = ZI_CHANG_URL.replace("RANDOM", random);
		random = String.format("%.17f", Math.random());
		
		HashMap<String, String> headers = new HashMap<String, String>();  
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive"); 
		headers.put("Cache-Control", "no-cache"); 
        // 传输的类型
		headers.put("Cookie", this.getSettingInfo().getCookie());
		//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
		headers.put("Host", "m.stock.pingan.com"); 
		headers.put("Pragma", "no-cache"); 
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		
		String str = httpReq.httpGet(url,headers);
		System.out.println(str);

	}

	// 持仓
	public void getChiChang() {
		String random = String.format("%.17f", Math.random());
		System.out.println(random);
		String url = CHI_CHANG_URL.replace("RANDOM", random);
		HashMap<String, String> headers = new HashMap<String, String>();  
		headers.put("accept", "application/json");
		headers.put("Accept-Charset", "utf-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive"); 
		headers.put("Cache-Control", "no-cache"); 
        // 传输的类型
		headers.put("Cookie", this.getSettingInfo().getCookie());
		//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
		headers.put("Host", "m.stock.pingan.com"); 
		headers.put("Pragma", "no-cache"); 
		headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		String result = httpReq.httpGet(url,headers);
		System.out.println(result);
	}

	// 获取股东账号
	public Map  getGudong(String stock_code) {
		Map returnMap = null;
		try {
			String random = String.format("%.17f", Math.random());
			if (stock_code.indexOf("6")== 0) {
				EXCHANGE_TYPE= "1";
			}
			String url = GU_DONG_URL.replace("RANDOM", random).replace("EXCHANGE_TYPE", EXCHANGE_TYPE).replace("STOCK_CODE", stock_code);;
			HashMap<String, String> headers = new HashMap<String, String>(); 
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive"); 
			headers.put("Cache-Control", "no-cache"); 
	        // 传输的类型
			headers.put("Cookie", this.getSettingInfo().getCookie());
			//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
			headers.put("Host", "m.stock.pingan.com"); 
			headers.put("Pragma", "no-cache"); 
			headers.put("Referer", "https://m.stock.pingan.com/html/h5security/wechat/trade/index.html"); 
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			String result = httpReq.httpGet(url,headers);
			ObjectMapper mapper = new ObjectMapper();
			Map resultMap = mapper.readValue(result, Map.class);
			ArrayList list = null;
			list = (ArrayList) resultMap.get("results");
			returnMap = (Map) list.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	// 获取实时信息
	public Map getStockLastestInfo(String code) {
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
			//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
			headers.put("Cookie", this.getSettingInfo().getCookie());
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

	public List getCurrentWeiTuoList() {
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
		//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
		headers.put("Cookie", this.getSettingInfo().getCookie());
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

	public void doCheDan(boolean isCheDan) {
		Map infoMap = null;
		if (isCheDan) {
			List list = this.getCurrentWeiTuoList();
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
					//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
					headers.put("Cookie", this.getSettingInfo().getCookie());
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
	public int getEnableSellNum(String stockCodeStr,String weiTuoPrice){
		int total=0;
		try {
			Map map = getStockLastestInfo(stockCodeStr);
			System.out.println("查询到的实时数据信息==>  " +map);
			Map  guDongMap=  this.getGudong(stockCodeStr);
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
			String gudongAccount= (String) guDongMap.get("stock_account");
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
			//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
			headers.put("Cookie", this.getSettingInfo().getCookie());
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
	

	public void doSell(String stockCodeStr, String weiTuoPrice) {
		/*Map<String, String> map = new HashMap<String, String>();
		String url = "https://m.stock.pingan.com/pah5trade/account/common/share_holder?random=0.5602111297973305&exchange_type=2&stock_code=002552";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				EXCHANGE_TYPE= "2";
				Stock sts = list.get(i);
				if (sts.getCode().indexOf("6") == 0) {
					EXCHANGE_TYPE= "1";
				}
				map.put("exchange_type", EXCHANGE_TYPE);
				httpReq.httpPost(url, map);
			}

		}*/
		
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
		//	headers.put("Cookie", "BIGipServersmt-hs-html5_30074_PrdPool=1107693996.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; WEBTRENDS_ID=117.182.145.81-4133786192.30704773; pa_stock_client_id=cnsz045160|19602341534444147|6d85bdea-cfad-46de-9338-4fc0e89d44f3; ps_login_token_id=N_991E12071035B29B5EF555E1A54D228481509C78018FDD628F736661C252A70EB7AD3518B5F77C8C136A7576CCEE052B0D1FD70D5A75A689B71EBA4CFC7EA62CB95C6162D3983CBD; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; WT-FPC=id=28be0bd651daddda41e1543126400536:");
			headers.put("Cookie", this.getSettingInfo().getCookie());
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
	
	
	
	
	public int getEnableBuyNum(String stockCodeStr,String weiTuoPrice){
		int total=0;
		try {
			Map map = getStockLastestInfo(stockCodeStr);
			System.out.println("查询到的实时数据信息==>  " +map);
			Map  guDongMap=  this.getGudong(stockCodeStr);
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
			String gudongAccount= (String) guDongMap.get("stock_account");
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
		//	headers.put("Cookie", "WEBTRENDS_ID=4.0.4.20-3912647088.30704422; pa_stock_client_id=cnsz045162|19451640254239497|94dab926-e74a-400a-a54f-d3308fe67e05; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; __utma=33891555.1296176584.1543019349.1543019349.1543019349.1; __utmz=33891555.1543019349.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543019717916:ss=1543019348628:fs=1542975627051:pn=1:vn=1; BIGipServersmt-hs-html5_30074_PrdPool=1124471212.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; paid_test=56ca049e-75fe-880a-4a66-05f056030f53; ps_login_token_id=N_C2FEDB427F4809E19EB217B2E1A3196398939B319977F8C3026E95958E39BFD24865DAECC0997CBB386EE60C7B089F7546F3EA25F4318489AB2EB2D1E86CDB06417B58BB7A0FA4F9; WT-FPC=id=4.0.4.20-3912647088.30704422:lv=1543049829115:ss=1543049694585:fs=1542975627051:pn=3:vn=2");
			headers.put("Cookie", this.getSettingInfo().getCookie());
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
	
	public void doBuy(String stockCodeStr, String weiTuoPrice) {
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
		//	headers.put("Cookie", "BIGipServersmt-hs-html5_30074_PrdPool=1107693996.31349.0000; BIGipServersis-stock-frontend-m-static_30075_PrdPool=554045868.31605.0000; WEBTRENDS_ID=117.182.145.81-4133786192.30704773; pa_stock_client_id=cnsz045160|19602341534444147|6d85bdea-cfad-46de-9338-4fc0e89d44f3; ps_login_token_id=N_991E12071035B29B5EF555E1A54D228481509C78018FDD628F736661C252A70EB7AD3518B5F77C8C136A7576CCEE052B0D1FD70D5A75A689B71EBA4CFC7EA62CB95C6162D3983CBD; ps_login_app_name=AYLCH5; ps_login_union_id=aae15dba1c2b4d5faceaf303e92428a9; WT-FPC=id=28be0bd651daddda41e1543126400536:");
			headers.put("Cookie", this.getSettingInfo().getCookie());
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
	

	
	
	class SettingInfo{
		String cookie = null;
		String shen_Zhen=null;
		String shang_hai = null;
		
		public SettingInfo() {
			super();
		}

		public SettingInfo(String cookie, String shen_Zhen, String shang_hai) {
			super();
			this.cookie = cookie;
			this.shen_Zhen = shen_Zhen;
			this.shang_hai = shang_hai;
		}

		public String getCookie() {
			return cookie;
		}

		public void setCookie(String cookie) {
			this.cookie = cookie;
		}

		public String getShen_Zhen() {
			return shen_Zhen;
		}

		public void setShen_Zhen(String shen_Zhen) {
			this.shen_Zhen = shen_Zhen;
		}

		public String getShang_hai() {
			return shang_hai;
		}

		public void setShang_hai(String shang_hai) {
			this.shang_hai = shang_hai;
		}
		
		
	}
	
}



