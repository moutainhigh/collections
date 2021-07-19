package com.ye.monitor.from;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;









import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.bo.domain.Stock;
import com.ye.monitor.YePingAnHttpUtil;

@Component
public class PingAnWeb {
	public static Logger log = LogManager.getLogger(TencentWeb.class);
	@Autowired
	private YePingAnHttpUtil yePingAnHttpUtil;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,String> getStockInfo(Stock stock){
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
        // System.out.println(df.format(decimal));
         String random = df.format(decimal);
		String marketType = "";
		//…œ∫£603848   4353
		//…Ó€⁄ 000983  4609
		//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=603848&codeType=4353&type=shsz";
		//url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=000983&codeType=4609&type=shsz";
     	if(stock.getStockCode().indexOf("6")==0){
     		marketType = "4353";
		}else{
			marketType = "4609";
		}
		String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getStockCode()+"&codeType="+marketType+"&type=shsz";
		String result = null;
		result = yePingAnHttpUtil.httpGet(url);
		//System.out.println(result);
		JSONObject jsonObj = JSON.parseObject(result);
		//System.out.println(jsonObj.get("results"));
		JSONObject json  =  JSON.parseObject(jsonObj.getString("results"));
		//System.out.println(json);
		//System.out.println(jsonObj);
		//System.out.println(jsonObj.getJSONObject("results").get("totalHand"));
		//System.out.println(json);
		HashMap  map = new HashMap();
		map.put("code", json.getString("code"));
		map.put("stockName", json.getString("stockName"));
		map.put("currentPrice", json.getString("newPrice"));
		map.put("prevClose", json.getString("prevClose"));
		map.put("open", json.getString("open"));
		map.put("minPrice", json.getString("minPrice"));
		map.put("maxPrice", json.getString("maxPrice"));
		map.put("weiBi", json.getString("weiBi"));
		return map;
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,String> getDaPanZhiShu(){
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
         String random = df.format(decimal);
     	//https://m.stock.pingan.com/h5quote/quote/getIndexData?random=0.2779566904218944&marketType=shsz
		String url = "https://m.stock.pingan.com/h5quote/quote/getIndexData?random="+random+"&marketType=shsz";
		String result = null;
		result = yePingAnHttpUtil.httpGet(url);
		JSONObject jsonObj = JSON.parseObject(result);
		//System.out.println(jsonObj);
		JSONArray json  =  JSON.parseArray(jsonObj.getString("results"));
		String stockName = json.getJSONObject(0).getString("stockName");
		String rise = json.getJSONObject(0).getString("rise");
		String newPrice = json.getJSONObject(0).getString("newPrice");
		System.out.println(stockName + "===>  "   + rise + "  ==> "  +newPrice);
		
		String stockName1 = json.getJSONObject(1).getString("stockName");
		String rise1 = json.getJSONObject(1).getString("rise");
		String newPrice1 = json.getJSONObject(1).getString("newPrice");
		System.out.println(stockName1 + ""   + rise1 + "  ==> "  +newPrice1);
		
		String stockName2 = json.getJSONObject(2).getString("stockName");
		String rise2 = json.getJSONObject(2).getString("rise");
		String newPrice2 = json.getJSONObject(2).getString("newPrice");
		System.out.println(stockName2 + ""   + rise2 + "  ==> "  +newPrice2);
		
		/*System.out.println(json.get(0));
		System.out.println(json.get(1));
		System.out.println(json.get(2));*/
		Map map = new HashMap();
		return map;
	}
}
