package com.ly.task.jsoup.tim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ly.common.http.PingAnZhengQuanHttpWithSafeReq;

//https://blog.csdn.net/sinat_29774479/article/details/78730359
@Component
public class GetLatestInfoAndTimByPingAnHttpWrap {
	private static Logger logger = Logger.getLogger(GetLatestInfoAndTimByPingAnHttpWrap.class);

	@Autowired
	private PingAnZhengQuanHttpWithSafeReq httpReq;
	
	//public static final String DAY_URL= "https://m.stock.pingan.com/h5quote/quote/getDayDataBigInt?random=RANDOM&tokenId=&chnl=&requestId=&stockCode=STOCKCODE&codeType=CODETYPE&period=day&day=30"; //最近30交易日
	//public static final String DAY_URL= "https://quote.stock.pingan.com/restapi/nodeserver/quote/realTimeData?_=1593745584207";
	public static final String DAY_URL= "https://quote.stock.pingan.com/restapi/nodeserver/quote/realTimeData?_=RANDOM";
	//https://m.stock.pingan.com/static/quote/quote/detail.html?code=SZ000983
	
	@SuppressWarnings("unchecked")
	//public List<Map<String,String>> getDayLineInfo(String code){
	public String getDayLineInfo(String code){
		if(code.indexOf("6")==0){
			code = "SH"+code;
		}else{
			code = "SZ"+code;
		}
		String result = null;
		//List<Map<String,String>> returnList  = null;
		try {
			String random = String.format("%.17f", Math.random());
			//returnList = new ArrayList<Map<String,String>>();
			String url = DAY_URL.replace("RANDOM", random);
			logger.debug("获取从平安证券中日线信息  === >" + url);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("accept", "application/json");
			headers.put("Accept-Charset", "utf-8");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Accept-Encoding", "gzip, deflate, br");
			headers.put("Connection", "keep-alive");
			headers.put("Cache-Control", "no-cache");
			headers.put("Host", "quote.stock.pingan.com");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			headers.put("Pragma", "no-cache");
			//headers.put("Cookie", "WEBTRENDS_ID=4.0.4.43-2195348448.30779637; WT-FPC=id=4.0.4.43-2195348448.30779637:lv=1575281725774:ss=1575280049801:fs=1575280049801:pn=4:vn=1; connect.sid=s%3AM4w-ACWSObsaJ1zORBb9xEMwibJnBKMd.bhDfWhsDqAuIGBuN1otm%2FyGtThaS5Bms1aQHHlfOAuU");
			//headers.put("Referer", "https://m.stock.pingan.com/static/quote/quote/detail.html?code=SZ000983");
			headers.put("Referer", "https://m.stock.pingan.com/static/quote/quote/detail.html?code="+code);
			headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
			headers.put("X-Requested-With", "XMLHttpRequest");
			headers.put("Sec-Fetch-Mode", "cors");
			headers.put("Sec-Fetch-Site", "same-site");
			
			
			
			Map map = new HashMap();
			map.put("version","2.0");
			map.put("channel"," mobileH5");
			map.put("requestId","340f9a27-519a-42e2-dfe3-22ddc973db32");
			map.put("cltplt","h5");
			map.put("cltver","1.0");
			map.put("aid"," ");
			map.put("sid"," ");
			map.put("ouid"," ");
			map.put("source"," ");
			//map.put("body[code]","SZ000983");
			map.put("body[code]",code);
		//	map.put("body[kLineType]","7");
			//map.put("body[endDate]","20191203");
		//	Date now = new Date(); // 创建一个Date对象，获取当前时间
		//	SimpleDateFormat myformator = new SimpleDateFormat("yyyyMMdd");
		//	map.put("body[endDate]",myformator.format(now));
			//System.out.println(myformator.format(now));
			//map.put("body[count]","300");
		//	map.put("body[cqTag]","2");
			
			result = httpReq.httpPost(url, headers, map);
			/*if (result != null) {
				Map<?, ?> resultMap = null;
				resultMap = JSONObject.parseObject(result,Map.class);  
				Map  keys = (Map) resultMap.get("results");
				JSONArray arrays = (JSONArray)keys.get("buyingAndSellings") ;
				System.out.println("==> " +arrays);
				Map<String,String> maps = null;
				if(arrays!=null&&arrays.size()>0){
					for (int i = 0; i < arrays.size(); i++) {
						maps  = (Map<String, String>) arrays.get(i);
						returnList.add(maps);
					}
				}
				logger.info("处理从平安证券返回的数据===>"  + returnList);
			}*/
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		return result;
	}
	
}
