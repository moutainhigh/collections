package com.ly.task.getdayinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.common.utils.Constrant;
import com.ly.dao.BuyDao;

@Component
public class GetRecentDayTradeInfoBySina {

	private static final Logger logger = Logger.getLogger("days");
	
	//https://www.cnblogs.com/xiaofoyuan/p/6525214.html
	//https://www.shenjian.io/
	//public static final String url="https://api.shenjian.io/?appid=6837bf3c0f62b1b5bb24d09f4a352005";
	//https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/
	//https://blog.csdn.net/deng214/article/details/80400984
	//http://www.open-open.com/jsoup/selector-syntax.htm
	
	@Autowired
	private BuyDao monitorDao;
	
	
	public List<Map<String,String>> soupSinaInfo(){
		//Monitor mo = monitorDao.getMonitor();
		//String url = Constrant.SIN_DAY_INFO_LIST_URL.replace("STOCKCODE", mo.getCode());
		List returnList = new ArrayList();
		Map map = null;
		String url = Constrant.SIN_DAY_INFO_LIST_URL.replace("STOCKCODE","600000");
		  try {
			Document document=Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36").get();
			Elements trsElements = document.select("#FundHoldSharesTable tbody tr");
			if(trsElements!=null){
				for (int i = 1; i < 15; i++) {
					String date = trsElements.eq(i).select("a").html();
					String open =trsElements.eq(i).select("div").eq(1).html();
					String max = trsElements.eq(i).select("div").eq(2).html();
					String close = trsElements.eq(i).select("div").eq(3).html();
					String min = trsElements.eq(i).select("div").eq(4).html();
					
					map = new HashMap();
					map.put("date", date);
					map.put("open", open);
					map.put("max", max);
					map.put("close", close);
					map.put("min", min);
					returnList.add(map);
				}
			}
			return returnList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		  
	}
	
	
	public static void main(String[] args) {
		GetRecentDayTradeInfoBySina t = new GetRecentDayTradeInfoBySina();
		List<Map<String,String>> list = t.soupSinaInfo();
		//System.out.println(list);
		double total = 0.0;
		Map map = null;
		for (int i = 0; i < 14; i++) {
			map = list.get(i);
			String close = (String) map.get("close");
			System.out.println(close);
			total += Double.valueOf(close);
		}
		
		System.out.println(total/14);
	}
	
}
