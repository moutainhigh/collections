package com.ly.stocktrade.getweek.byauto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.common.utils.MyDateUtils;
import com.ly.http.tencent.TenCentHttp;

//https://ask.csdn.net/questions/481
//https://zhidao.baidu.com/question/681287181725847492.html
//https://blog.csdn.net/u010527630/article/details/52371046
@Component
public class GetWeekLineInfoByTencent {
	private static Logger logger = Logger.getLogger(GetWeekLineInfoByTencent.class);
	
	@Autowired
	private TenCentHttp tenCentHttp;
	
	//private static final String TENCENT_URL = "http://data.gtimg.cn/flashdata/hushen/latest/weekly/STOCK.js?maxage=43201&visitDstTime=1";
	//private static final String TENCENT_URL = "http://data.gtimg.cn/flashdata/hushen/latest/daily/sz000983.js?maxage=43201&visitDstTime=1";
	private static final String TENCENT_URL = "http://data.gtimg.cn/flashdata/hushen/latest/weekly/STOCK.js?maxage=43201";
	
	public  String getCurrentYear(){
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String time =sdf.format(date);
        */
		Date date = new Date();
		String time = MyDateUtils.getTimeString(date, "yyyy");
		
        time = time.substring(0,2);
        
        return time;
	}
	
	public List<Map<String,String>> getWeekLineInfo(String code){
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Map<String,String> maps = null;
		
		String tempCode = null;
		if(code.indexOf("6")==0){
			tempCode = "sh" + code;
		}else{
			tempCode = "sz" + code;
		}
		
		String url = TENCENT_URL.replace("STOCK", tempCode);
		HashMap<String, String> headers = new HashMap<String, String>();
		
		
		headers.put("User-Agent", "Mozilla/5、0 (Windows; U; Windows NT 5、1; zh-CN; rv:1、8、1、14) Gecko/20080404 Firefox/2、0、0、14");
		headers.put("Cache-Control", "no-cache");
		
		
		String str = tenCentHttp.httpGet(url, headers);
		logger.info(str);
		str = str.replace("\\n\\", "_a");
		
		String [] temp = str.split("_a");
		for (int i = 2; i < temp.length-1; i++) {
			
			String yearStr = temp[i].split(" ")[0].trim();
			String open = temp[i].split(" ")[1].trim();
			String current = temp[i].split(" ")[2].trim();
			String max = temp[i].split(" ")[3].trim();
			String min = temp[i].split(" ")[4].trim();
			
		    yearStr = this.getCurrentYear()+ yearStr;
			maps = new HashMap<String,String>();
			maps.put("date", yearStr);
			maps.put("openPrice", open);
			maps.put("closePrice", current);
			maps.put("maxPrice", max);
			maps.put("minPrice", min);
			
			resultList.add(maps);
		}
		
		
		
		return resultList;
	}

}
