package com.ly.stocktrade.getdayupinfo.sina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.ly.common.utils.Constrant;

@Component
public class GetDayPriceBySystem {
	
	private static final Logger logger = Logger.getLogger("days");
	
	
	public List<Map<String, String>> soupSinaInfo(String code) {
		List returnList = new ArrayList();
		Map map = null;
		
		String url = Constrant.SIN_DAY_INFO_LIST_URL.replace("STOCKCODE", code);
		/*Date dNow = new Date(); // 当前时间
		Date dBefore = null;
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		System.out.println(year);
		System.out.println(month);

		if (month <= 3) {
			calendar.add(Calendar.MONTH, -3); // 设置为前3月
			dBefore = calendar.getTime(); // 得到前3月的时间
			calendar.setTime(dBefore);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
			System.out.println(year);
			System.out.println(month);
			url =url + "?year="+year+"&jidu=4"; 
		} else if (month > 3 && month <= 6) {
			url =url + "?year="+year+"&jidu=4"; 
		} else if (month > 6 && month <= 9) {

		} else {

		}
		*/
		
		
		
		try {
			Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36").get();
			Elements trsElements = document.select("#FundHoldSharesTable tbody tr");
			logger.debug("====本季度的tr共长====>  " +trsElements.size());
			int num = 60;//最近60个交易日
			if(num>trsElements.size()){
				num = trsElements.size();
			}
			if (trsElements != null) {
				for (int i = 1; i < num; i++) {
					String date = trsElements.eq(i).select("a").html();
					String open = trsElements.eq(i).select("div").eq(1).html();
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
			logger.debug("系统自动从新浪网采集最近60个交易日共约最近三个月的数据===> " + returnList);
			return returnList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
