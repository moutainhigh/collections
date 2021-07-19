package com.ly.task.getdayinfo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.common.utils.CalcUtil;
import com.ly.dao.RecentStockDao;
import com.ly.pojo.RecentStock;
import com.ly.stocktrade.getdayupinfo.GetDayUpBySina;
import com.ly.stocktrade.getdayupinfo.sina.GetDayPriceBySystem;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class GetDayUpBySystem {
	//https://baike.baidu.com/item/%E6%8C%87%E6%95%B0%E5%B9%B3%E5%9D%87%E6%95%B0/3334858?fr=aladdin
	//https://zhidao.baidu.com/question/106841176.html
	private static final Logger logger = Logger.getLogger("days");
 
	
	
	@Autowired
	private GetDayUpBySina getDayUp;
	@Autowired
	private GetDayPriceBySystem getAvgPriceBySystem;
	@Autowired
	private RecentStockDao recentPriceDao;
	
	
	//@Scheduled(cron = "0/40 * * * * ?")
	//@Scheduled(cron="0 */10 * * * ?")
	//@Scheduled(cron="11 18 16 ? * MON-FRI")
	//@Scheduled(cron="11 33 16 ? * MON-FRI")
	@Scheduled(cron="11 18 16 ? * MON-FRI")
	public void task01() throws InterruptedException {
		
		for(int i = 0 ;i<2;i++){
			String str = getDayUp.getUp(i);
			JSONArray jsonarray = JSONArray.fromObject(str);
			logger.debug("需要解析的数据长度为 【" +jsonarray.size() + "】");
			for (int index = 0; index < jsonarray.size(); index++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonarray.get(index));
				String code = (String) jsonObject.get("code");
				String name = (String) jsonObject.get("name");
				String trade = (String) jsonObject.get("trade");
				
				/*System.out.println(jsonObject.get("code"));
				System.out.println(jsonObject.get("name"));
				System.out.println(jsonObject.get("trade"));
				System.out.println(jsonObject.get("changepercent"));
				System.out.println(jsonObject.get("high"));
				System.out.println(jsonObject.get("low"));
				System.out.println(jsonObject.get("open"));*/
				
				//System.out.println(code  + "" + name+ " " + trade);
				if(index%50==0){
					Thread.sleep(1000*60*4);
				}
				List list = getAvgPriceBySystem.soupSinaInfo(code);
				
				
				for(int m=1;m<13;m++){
					calcInfo(m*5,code,name,list,index,jsonarray);
				}
				logger.debug("当前 【"+ (index+1)+"】条，共" + jsonarray.size() + " 条数据,还剩下 【"+(jsonarray.size() - (index+1))+ " 】条");
			
			}
		}
	}
	
	
	public void calcInfo(int days,String code,String name,List resultList,int index,JSONArray jsonarray){
		double total = 0.0;
		Map map = null;
		if(days>resultList.size()){
			days = resultList.size();
		}
		for (int k = 0; k < days; k++) {
			map = (Map) resultList.get(k);
			String close = (String) map.get("close");
			String date = (String) map.get("date");
			String open = (String) map.get("open");
			//k=0 则是当天
			
			logger.debug("最近"+days+"交易日，共"+days/5+"周，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
			total += Double.valueOf(close);
		}
		//System.out.println("最近"+days+"交易日平均价====>" + CalcUtil.formatDouble(total/days));
		logger.debug("最近"+days+"交易日平均价====>" + CalcUtil.formatDouble(total/days));
		
		RecentStock recentPrice = new RecentStock();
		recentPrice.setCode(code);
		recentPrice.setName(name);
		
		if(days==5){
			recentPrice.setDay5(CalcUtil.formatDouble(total/days));
		}else if(days==10){
			recentPrice.setDay10(CalcUtil.formatDouble(total/days));
		}else if(days==15){
			recentPrice.setDay15(CalcUtil.formatDouble(total/days));
		}else if(days==20){
			recentPrice.setDay20(CalcUtil.formatDouble(total/days));
		}else if(days==25){
			recentPrice.setDay25(CalcUtil.formatDouble(total/days));
		}else if(days==30){
			recentPrice.setDay30(CalcUtil.formatDouble(total/days));
		}else if(days==35){
			recentPrice.setDay35(CalcUtil.formatDouble(total/days));
		}else if(days==40){
			recentPrice.setDay40(CalcUtil.formatDouble(total/days));
		}else if(days==45){
			recentPrice.setDay45(CalcUtil.formatDouble(total/days));
		}else if(days==50){
			recentPrice.setDay50(CalcUtil.formatDouble(total/days));
		}else if(days==55){
			recentPrice.setDay55(CalcUtil.formatDouble(total/days));
		}else {
			recentPrice.setDay60(CalcUtil.formatDouble(total/days));
		}
		
		
		
		recentPriceDao.save(recentPrice);
		
		//logger.debug("当前 【"+ (index+1)+"】条，共" + jsonarray.size() + " 条数据,还剩下 【"+(jsonarray.size() - (index+1))+ " 】条");
	}
	
	
	
}



/*double total = 0.0;
Map map = null;
int n5 = 5;
int n10 = 10;
int n15  = 15;
int n20  = 20;
int n30  = 30;
int n45  = 45;
int n60  = 60;

if(n5>list.size()){
	n5 = list.size();
}

if(n10>list.size()){
	n10 = list.size();
}

if(n15>list.size()){
	n15 = list.size();
}

if(n20>list.size()){
	n20 = list.size();
}
if(n30>list.size()){
	n30 = list.size();
}


if(n45>list.size()){
	n45 = list.size();
}

if(n60>list.size()){
	n60 = list.size();
}
//最近5交易日
for (int k = 0; k < n5; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近5交易日，共一周，收盘===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date + ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("最近5交易日平均价====>" + total/5);
logger.debug("==============================================================================================================================");

total = 0.0;
//最近10天
for (int k = 0; k < n10; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	
	logger.debug("最近10交易日，共两周，收盘===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("最近10交易日平均价====>" + total/10);
logger.debug("==============================================================================================================================");


//最近15交易日
total = 0.0;
for (int k = 0; k < n15; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近15交易日，共三周，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("15交易日平均价====>" + total/15);
logger.debug("==============================================================================================================================");


//最近20交易日
total = 0.0;
for (int k = 0; k < n20; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近20交易日，共4周，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date + ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("20交易日平均价====>" + total/20);
logger.debug("==============================================================================================================================");


//最近30交易日
total = 0.0;
for (int k = 0; k < n30; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近30交易日，共6周，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("30交易日平均价====>" + total/30);
logger.debug("==============================================================================================================================");



//最近45交易日
total = 0.0;
for (int k = 0; k < n45; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近45交易日，共9周，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("45交易日平均价====>" + total/45);
logger.debug("==============================================================================================================================");



//最近60交易日
total = 0.0;
for (int k = 0; k < n60; k++) {
	map = (Map) list.get(k);
	String close = (String) map.get("close");
	String date = (String) map.get("date");
	String open = (String) map.get("open");
	//k=0 则是当天
	
	logger.debug("最近60交易日，共3个月，收盘数据===> 代码【" + code + "】,名称 ：【" + name + "】,开盘【" + open + "】,收盘：【" + close + " 】,日期:" + date+ ",第【  " + (k+1) + " 】天");
	total += Double.valueOf(close);
}
System.out.println("60交易日平均价====>" + total/60);
logger.debug("==============================================================================================================================");
logger.debug("当前 【" + (index+1)+" 】，共" + jsonarray.size() + " ,还剩下 【"+(jsonarray.size() - (index+1))+ " 】 个");*/
