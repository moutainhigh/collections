package com.ly.task.jsoup.tim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ly.common.util.SystemSoupConstrant;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.pojo.Stock;
import com.ly.pojo.Stock_K_line_Day_DataList;
import com.ly.task.jsoup.day.cal.CalDayLineUpAsync;
import com.ly.task.jsoup.day.http.GetDayLineInfoByPingAnHttpV2;

@Component
public class JsoupLatestInfo {
	private static Logger day = Logger.getLogger("day");

	@Autowired
	private GetLatestInfoAndTimByPingAnHttpWrap getLatestInfoAndTimByPingAnHttpWrap;

	@Autowired
	private StockDao stockImpl;
	
	
	//@Scheduled(cron="*/10 * * * * ?")
	public void task() throws Exception {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		// stock_K_line_Day_Data_List_Dao.truncateTable(Stock_K_line_Day_DataList.class);
		Long total = stockImpl.findCount(Stock.class);
		int rows = SystemSoupConstrant.rows;
		int seconds = SystemSoupConstrant.seconds;
		int step = (int) (total / rows + 1);
		Stock sb = null;
		Stock_K_line_Day_DataList weekInfo = null;
		List<Stock_K_line_Day_DataList> infoParamList = null;
		for (int i = 1; i <= step; i++) {
			int pageNo = i;
			int pageSize = rows;

			if (i > 1) {
				Thread.sleep(seconds);

			}
			day.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");
			List<Stock> sbLists = stockImpl.findByPage("from Stock", pageNo, pageSize);
			if (sbLists != null && sbLists.size() > 0) {

				for (int j = 0; j < sbLists.size(); j++) {
					infoParamList = new ArrayList<Stock_K_line_Day_DataList>();
					sb = sbLists.get(j);
					day.debug("需要获取MA5数据的股票代码【 " + sb.getCode() + " 】，名称 【" + sb.getName() + "】,所属市场编码 【 "+ sb.getMarketType() + " 】");
					try {
						String result = getLatestInfoAndTimByPingAnHttpWrap.getDayLineInfo(sb.getCode());
						if(result!=null){
							Map<?, ?> resultMap =  JSONObject.parseObject(result,Map.class);  
							Map  keys = (Map) resultMap.get("results");
							JSONArray arrays = (JSONArray)keys.get("buyingAndSellings") ;
							System.out.println("===========>" +arrays);
							String hs = (String) keys.get("hs");
							String jtsyl = (String) keys.get("jtsyl");//静态市盈率
							String sjl = (String) keys.get("sjl");//市静率
							String syl = (String) keys.get("syl");//市盈率
							String timsyl = (String) keys.get("timsyl"); //市盈率tim
							String turnoverVolume = (String) keys.get("turnoverVolume");
							
							
							
							
						}
					} catch (Exception e) {
					}
				}
			}

		}
	}
}
