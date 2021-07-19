package com.ly.task.jsoup.day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.common.dateutil.GetWeekOfDay;
import com.ly.common.util.CalcUtil;
import com.ly.common.util.SystemSoupConstrant;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.pojo.Stock;
import com.ly.pojo.Stock_K_line_Day_DataList;
import com.ly.task.jsoup.day.cal.CalDayLineUpAsync;
import com.ly.task.jsoup.day.http.GetDayLineInfoByPingAnHttpV2;

@Component
public class JsoupDayInfoByPinAnV2 {
	private static Logger day = Logger.getLogger("day");

	@Autowired
	private GetDayLineInfoByPingAnHttpV2 stockGetDayDataBigInt;

	@Autowired
	private StockDao stockImpl;

	@Autowired
	private Stock_K_line_Day_Data_List_Dao stock_K_line_Day_Data_List_Dao;

	@Autowired
	private CalDayLineUpAsync caldaylineup;

	// @Scheduled(cron="*/10 * * * * ?")
	//
	//@Scheduled(cron = "30 11 14 ? * *")
	@Scheduled(cron="0 33 10,11,13,14,15 ? * MON-FRI")
	public void task() throws Exception {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		stock_K_line_Day_Data_List_Dao.truncateTable(Stock_K_line_Day_DataList.class);
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
					day.debug("需要获取MA5数据的股票代码【 " + sb.getCode() + " 】，名称 【" + sb.getName() + "】,所属市场编码 【 " + sb.getMarketType() + " 】");
					try {
						List<Map<String, String>> resultList = stockGetDayDataBigInt.getDayLineInfo(sb.getCode());

						day.debug("当前爬虫到第" + (j + 1) + "条数据,共" + sbLists.size() + "条数据，还有" + (sbLists.size() - (j + 1)) + "条数据没爬虫到，当前代码【" + sb.getCode() + "】,名称【" + sb.getName() + "】返回信息" + resultList);
						if (resultList != null && resultList.size() >= 30) {
							//for(int m=0;m<3;m++){
							for(int m=0;m<30;m++){
								
								int daoXu = resultList.size()-1;
								Map temp =  resultList.get(daoXu-m);
								weekInfo = new Stock_K_line_Day_DataList();
							
								

								double ma5Double = 0.0;
								for(int k=0;k<5;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									Map<String, String>  ma5StrMap = temp2;
									String closePriceStr = ma5StrMap.get("closePrice");
									double price = Double.valueOf(closePriceStr);
									
									ma5Double += price;
								}
								String  ma5Str = CalcUtil.formatDouble(ma5Double/5.0);
								
								
								
								double ma5DoubleVol = 0.0;
								for(int k=0;k<5;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									Map  ma5StrMap = temp2;
									String closePriceStr =   ma5StrMap.get("volume") +"";
									double price = Double.valueOf(closePriceStr);
									
									ma5DoubleVol += price;
								}
								String  ma5StrVol = CalcUtil.formatDouble(ma5DoubleVol/5.0);
								
								
								double ma10Double = 0.0;
								for(int k=0;k<10;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									String closePriceStr = temp2.get("closePrice");
									double price = Double.valueOf(closePriceStr);
									
									ma10Double += price;
								}
								
								String  ma10Str = CalcUtil.formatDouble(ma10Double/10.0);
								
								
								double ma10DoubleVol = 0.0;
								for(int k=0;k<10;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									Map ma5StrMap = temp2;
									String closePriceStr = ma5StrMap.get("volume") +"";
									double price = Double.valueOf(closePriceStr);
									
									ma10DoubleVol += price;
								}
								String  ma10StrVol = CalcUtil.formatDouble(ma10DoubleVol/10.0);
								
								
								double ma20Double = 0.0;
								for(int k=0;k<20;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									String closePriceStr = temp2.get("closePrice");
									double price = Double.valueOf(closePriceStr);
									
									ma20Double += price;
								}
								String  ma20Str = CalcUtil.formatDouble(ma20Double/20.0);
								
								
								double ma30Double = 0.0;
								for(int k=0;k<30;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									String closePriceStr = temp2.get("closePrice");
									double price = Double.valueOf(closePriceStr);
									
									ma30Double += price;
								}
								String  ma30Str = CalcUtil.formatDouble(ma30Double/30.0);
								
								/*double ma60Double = 0.0;
								for(int k=0;k<60;k++){
									Map<String ,String> temp2 =  resultList.get(daoXu-m-k);
									String closePriceStr = temp2.get("closePrice");
									double price = Double.valueOf(closePriceStr);
									
									ma60Double += price;
								}
								String  ma60Str = CalcUtil.formatDouble(ma60Double/60.0);*/
								
								Map<String,String> preMap = resultList.get(daoXu-(m+1));
								String preClose = 	preMap.get("closePrice");
								
								weekInfo.setPreClose(preClose);
								weekInfo.setMa5(ma5Str);
								weekInfo.setMa10(ma10Str);
								weekInfo.setMa20(ma20Str);
								weekInfo.setMa30(ma30Str);
								//weekInfo.setMa60(ma60Str);
								weekInfo.setMa5Vol(ma5StrVol);
								weekInfo.setMa10Vol(ma10StrVol);
								
								weekInfo.setCode(sb.getCode());
							//	weekInfo.setName(sb.getName());
								//weekInfo.setPinyin(sb.getPinyin());

								//weekInfo.setExchangeType(sb.getExchangeType());
								weekInfo.setMarketType(sb.getMarketType());
								weekInfo.setOpenPrice(temp.get("openingPrice")+"");
								weekInfo.setClosePrice(temp.get("closePrice")+"");
								weekInfo.setMinPrice(temp.get("highPrice")+"");
								weekInfo.setMaxPrice(temp.get("lowPrice")+"");
								
							
								//weekInfo.setHangye(sb.getHangye());
								//weekInfo.setLiuTongGu(sb.getLiuTongGu());
								//weekInfo.setDiQu(sb.getDiQu());
								//weekInfo.setZongGuBen(sb.getZongGuBen());
								//weekInfo.setShiyinglvJing(sb.getShiyinglvJing());
								
								weekInfo.setTotalHand(temp.get("volume")+"");
								//weekInfo.setPrefix(sb.getPrefix());
								
								String date = (String) temp.get("date");
								
								date = date.replace("/", "");
								
								
								
								Map<String,String> mapTime = GetWeekOfDay.getMondayAndFridayTime(date);
								String montime = mapTime.get("mondayDate");
								String fritime = mapTime.get("fridayDate");
								
								weekInfo.setDate(date);
								
								
								
								weekInfo.setMontime(montime);
								weekInfo.setFritime(fritime);
								
								infoParamList.add(weekInfo);

							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if (infoParamList != null && infoParamList.size() > 0) {
						stock_K_line_Day_Data_List_Dao.batchSave(infoParamList);
					}
				}
				
				
			}
		}
  

		caldaylineup.taskMaInfoCal();
		caldaylineup.taskOneDayCal();
		caldaylineup.taskTwoDayCalc();
		caldaylineup.taskDaYang();
		caldaylineup.taskGuanZhu();
		caldaylineup.taskClosePriceMa5();
		caldaylineup.taskClosePriceMa20();
		
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		day.debug("系统运行完成,程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
          
		
		
	}

}
