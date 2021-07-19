package com.ly.task.jsoup.day.cal.computing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.ly.dao.impl.Important_Stock_Dao;
import com.ly.dao.impl.Important_Stock_Up_Dao;
import com.ly.dao.impl.StockDao;
import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.pojo.Important_Stock;
import com.ly.pojo.Important_Stock_Up;
import com.ly.pojo.Stock;
import com.ly.pojo.Stock_K_line_Day_DataList;
import com.ly.pojo.Stock_Shou_Yang_One_Day;
import com.ly.task.sendemail.TraddingSendStockDayInfoEmail;

@Component
public class Guan_Zhu_Stock_One_Day_UpCalc {
	
	private static Logger calday = Logger.getLogger("calday");

	@Autowired
	private Important_Stock_Dao impDao;
	
	@Autowired
	private Important_Stock_Up_Dao impStockUpDao;
	
	
	@Autowired
	private StockDao stockDao;

	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;
	
	@Autowired
	private TraddingSendStockDayInfoEmail sendStockDayInfoEmail;
	
	//@Scheduled(cron="*/10 * * * * ?")
	//@Scheduled(cron="30 2 0 ? * *")
	public void getStockInfo(){
		long startTime = System.currentTimeMillis(); // 获取开始时间
		impStockUpDao.truncateTable(Important_Stock_Up.class);
		
		Important_Stock_Up stock_Shou_Yang_Day_One_Day = null;
		List<Important_Stock> list = impDao.find("from Important_Stock");
		List<Important_Stock_Up> entitys =   null;
		
		
		
		for (int j = 0; j < list.size(); j++) {
			
			entitys = new ArrayList<Important_Stock_Up>();
			Important_Stock impStock = list.get(j);
			String code = impStock.getCode();
			List<Stock_K_line_Day_DataList> datas = datasDao.findByPage("from Stock_K_line_Day_DataList  where code = ?  ORDER BY date desc",0,15, code);
			Stock sts = stockDao.find(" from Stock where code = ? ", code).get(0);
			
			if(datas!=null&&datas.size()>=3){
				Stock_K_line_Day_DataList day1 = datas.get(0);
				Stock_K_line_Day_DataList day2 = datas.get(1);
				Stock_K_line_Day_DataList day3 = datas.get(2);
				
				
				Double day1Open  =Double.valueOf( day1.getOpenPrice());
				Double day1Close =Double.valueOf(day1.getClosePrice());
				Double day1Ma5 = Double.valueOf(day1.getMa5());
				Double day1Ma10 = Double.valueOf(day1.getMa10());
				Double day1Ma20 = Double.valueOf(day1.getMa20());
				Double day1Ma30 = Double.valueOf(day1.getMa30());
				
				
				
				Double day2Open  =Double.valueOf( day2.getOpenPrice());
				Double day2Close =Double.valueOf(day2.getClosePrice());
				Double day2Ma5 = Double.valueOf(day2.getMa5());
				Double day2Ma10 = Double.valueOf(day2.getMa10());
				
				Double day3Open  =Double.valueOf( day3.getOpenPrice());
				Double day3Close =Double.valueOf(day3.getClosePrice());
				Double day3Ma5 = Double.valueOf(day3.getMa5());
				
				double day1IsZhang = day1Close - day1Open;
				
				Double rates = (day1Close - day2Close)/day2Close*100;
				
				double currentIsBigMa30 =  day1Close - day1Ma30;
				double openIsBigMa30 =  day1Open - day1Ma30;
				
				calday.info(day1IsZhang + " =====  "  + " ===>" + sts.getCode() + "  ==> "+ sts.getName()+" ，涨跌幅度：" + rates);
				
				//if(rates>1.5&&currentIsBigMa30>0&&openIsBigMa30>0){
				if(rates>1.5&&currentIsBigMa30>0&&openIsBigMa30>0){
					
					stock_Shou_Yang_Day_One_Day = new Important_Stock_Up();
					
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
					stock_Shou_Yang_Day_One_Day.setTotalHand(day1.getTotalHand());
					//stock_Shou_Yang_Day_One_Day.setBenRiTotalHand(day1.getTotalHand());
					//stock_Shou_Yang_Day_One_Day.setZuoRiTotalHand(day2.getTotalHand());
					//stock_Shou_Yang_Day_One_Day.setQianRiTotalHand(day3.getTotalHand());
					
					stock_Shou_Yang_Day_One_Day.setQianRiClose(day3.getClosePrice());
					
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
			impStockUpDao.batchSave(entitys);
			
		}
		
		//sendStockDayInfoEmail.guanZhuEmail();
		long endTime = System.currentTimeMillis(); // 获取结束时间
		long time = endTime - startTime;
		double hour = time / 1000 / 60.0 / 60.0;
		calday.debug("系统运行完成,程序运行时间：" + (time) + "毫秒 , 共 " + (time) / 1000 / 60.0 + "分,共" + hour + "小时"); // 输出程序运行时间
	}
}
