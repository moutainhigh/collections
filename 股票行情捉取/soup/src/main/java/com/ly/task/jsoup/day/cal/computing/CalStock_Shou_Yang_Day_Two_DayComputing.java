package com.ly.task.jsoup.day.cal.computing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.common.util.SystemSoupConstrant;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.dao.impl.Stock_Shou_Yang_Two_DayDao;
import com.ly.pojo.Stock;
import com.ly.pojo.Stock_K_line_Day_DataList;
import com.ly.pojo.Stock_Shou_Yang_Two_Day;
import com.ly.task.sendemail.TraddingSendStockDayInfoEmail;
//https://www.cnblogs.com/ios9/p/7475979.html
//https://blog.csdn.net/hwt_211/article/details/15505349
//https://blog.csdn.net/jrn1012/article/details/70255552
//https://blog.csdn.net/hexiaoli666/article/details/83109451
@Component
public class CalStock_Shou_Yang_Day_Two_DayComputing {
	
	private static Logger calday = Logger.getLogger("twoday");
	
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private Stock_K_line_Day_Data_List_Dao stock_k_line_day_data_list_dao;
	
	@Autowired
	private Stock_Shou_Yang_Two_DayDao stock_Shou_Yang_One_DayDao;
	

	@Autowired
	private TraddingSendStockDayInfoEmail sendStockDayInfoEmail;
	
	
	//@Scheduled(cron="*/10 * * * * ?")
	//@Scheduled(cron="30 02 23 ? * *")
	public void getStockInfo() {
		long startTime = System.currentTimeMillis();    //获取开始时间
		List<Stock_Shou_Yang_Two_Day> entitys =  null;
		stock_Shou_Yang_One_DayDao.truncateTable(Stock_Shou_Yang_Two_Day.class);
		Stock_Shou_Yang_Two_Day stock_Shou_Yang_Day_One_Day = null;
		long total = stockDao.findCount(Stock.class);
		int rows = SystemSoupConstrant.rows;
		int seconds = SystemSoupConstrant.seconds;
		int step = (int) (total / rows + 1);
		
		for (int i = 1; i <= step; i++) {
			entitys = new ArrayList<Stock_Shou_Yang_Two_Day>(); 
			List<Stock> list = stockDao.findByPage("from Stock", i, rows);
			
			for (int j = 0; j < list.size(); j++) {
				Stock sts = list.get(j);
				String code =sts.getCode();
				calday.info("当前迭代次数==========>  " + i + " ,还需要" + ((step - i) + 1) + " 次迭代爬虫");

				//Object [] parasm = new Object[]{code} ;
				List<Stock_K_line_Day_DataList> datas = stock_k_line_day_data_list_dao.findByPage("from Stock_K_line_Day_DataList  where code = ?  ORDER BY date desc",0,15, code);
				
				calday.info("datas   =>> " +datas);
				if(datas!=null&&datas.size()>=3){
					Stock_K_line_Day_DataList day1 = datas.get(0);
					Stock_K_line_Day_DataList day2 = datas.get(1);
					Stock_K_line_Day_DataList day3 = datas.get(2);
					
					
					
					Double day1Open  =Double.valueOf( day1.getOpenPrice());
					Double day1Close =Double.valueOf(day1.getClosePrice());
					Double day1Ma5 = Double.valueOf(day1.getMa5());
					Double day1Ma10 = Double.valueOf(day1.getMa10());
					
					
					
					Double day2Open  =Double.valueOf( day2.getOpenPrice());
					Double day2Close =Double.valueOf(day2.getClosePrice());
					Double day2Ma5 = Double.valueOf(day2.getMa5());
					Double day2Ma10 = Double.valueOf(day2.getMa10());
					
					Double day3Open  =Double.valueOf( day3.getOpenPrice());
					Double day3Close =Double.valueOf(day3.getClosePrice());
					Double day3Ma5 = Double.valueOf(day3.getMa5());
					Double day3Ma10 = Double.valueOf(day3.getMa10());
					
					double day1IsZhang = day1Close - day1Open;
				
					double day1Ma5IsBigThanyesterMa5 = day1Ma5 - day2Ma5;
					double day1Ma5IsBigThanyesterMa10 = day1Close - day1Ma10;
					
					double day2IsZhang = day2Close - day2Open;
					double day2IsBigThanMa5 = day2Close - day2Ma5;
					double day2Ma5IsBigThanyesterMa5 = day2Ma5 - day3Ma5;
					//double openIsBig= day1Open - day2Open;
					
					double day3IsZhang = day3Close - day3Open;
					double day3IsBigThanMa5 = day3Close - day3Ma5;
					Double rates = (day1Close - day2Close)/day2Close*100;
					Double rates2 = (day2Close - day3Close)/day3Close*100;
					double day1IsBigThanMa5 = (day1Close - day1Ma5 )/day1Ma5*100;
					calday.info(day2IsZhang + " =====  " + day2IsBigThanMa5 + " ===>" + sts.getCode() + "  ==> "+ sts.getName() +"涨跌幅度：" + rates+"  rates2  ===> "  +rates2); 
					
					
					if(day1IsZhang>0&&day2IsZhang>0&&rates>=2&&rates2>=2&&day1IsBigThanMa5>=1.2) {
						stock_Shou_Yang_Day_One_Day = new Stock_Shou_Yang_Two_Day();
						
						stock_Shou_Yang_Day_One_Day.setCode(code);
						stock_Shou_Yang_Day_One_Day.setName(sts.getName());
						stock_Shou_Yang_Day_One_Day.setPinyin(sts.getPinyin());
						
						stock_Shou_Yang_Day_One_Day.setPrevClose(day2.getClosePrice());
						stock_Shou_Yang_Day_One_Day.setExchangeType(sts.getExchangeType());
						stock_Shou_Yang_Day_One_Day.setMarketType(sts.getMarketType());
						stock_Shou_Yang_Day_One_Day.setOpenPrice(day1.getOpenPrice());
						stock_Shou_Yang_Day_One_Day.setClosePrice(day1.getClosePrice());
						stock_Shou_Yang_Day_One_Day.setMinPrice(day1.getMinPrice());
						stock_Shou_Yang_Day_One_Day.setMaxPrice(day1.getMaxPrice());
						stock_Shou_Yang_Day_One_Day.setOpenPrice(day1.getOpenPrice());
						//stock_Shou_Yang_Day_One_Day.setBenRiTotalHand(day1.getTotalHand());
						//stock_Shou_Yang_Day_One_Day.setZuoRiTotalHand(day2.getTotalHand());
						//stock_Shou_Yang_Day_One_Day.setQianRiTotalHand(day3.getTotalHand());
						stock_Shou_Yang_Day_One_Day.setQianRiClose(day3.getClosePrice());
						stock_Shou_Yang_Day_One_Day.setTotalHand(day1.getTotalHand());
						stock_Shou_Yang_Day_One_Day.setHangye(sts.getHangye());
						stock_Shou_Yang_Day_One_Day.setLiuTongGu(sts.getLiuTongGu());
						stock_Shou_Yang_Day_One_Day.setDiQu(sts.getDiQu());
						stock_Shou_Yang_Day_One_Day.setZongGuBen(sts.getZongGuBen());
						stock_Shou_Yang_Day_One_Day.setShiyinglvJing(sts.getShiyinglvJing());
						stock_Shou_Yang_Day_One_Day.setTime(new Date().toLocaleString());
						stock_Shou_Yang_Day_One_Day.setPrefix(sts.getPrefix());
						entitys.add(stock_Shou_Yang_Day_One_Day);
					}
					
				}
			}
			stock_Shou_Yang_One_DayDao.batchSave(entitys);
			
		}
		//sendStockDayInfoEmail.taskTwoDayEail();
		
		long endTime = System.currentTimeMillis();    //获取结束时间
		long time = endTime - startTime;
		double hour = time/1000/60.0/60.0;
		calday.info("系统运行完成,程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
	}
}
