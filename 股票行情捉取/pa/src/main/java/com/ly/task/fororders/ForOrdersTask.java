package com.ly.task.fororders;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.AccountDao;
import com.ly.dao.RestingOrdersDao;
import com.ly.dao.SellDao;
import com.ly.pojo.Account;
import com.ly.pojo.RestingOrders;
import com.ly.pojo.Sell;
import com.ly.stocktrade.trade.core.StockTradeCore;

//https://jingyan.baidu.com/article/7f41ecec0d0724593d095c19.html
//用于作为明天的卖出价，每天15点30分之后，查询每天成交的挂单信息
@Component
public class ForOrdersTask {
	private static final Logger logger = Logger.getLogger("stockrefs");
	
	@Autowired
	private StockTradeCore stockTradeBus;
	
	@Autowired
	private RestingOrdersDao restingOrdersDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private SellDao sellDao;
	
	
	//@Scheduled(cron="0/30 * * * * ?")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Scheduled(cron="0 20 15 ? * MON-FRI")
	public void taskForDoneDeal(){
		List<Account> accounts = accountDao.getAccountList();
		if (accounts != null && accounts.size() > 0) {
			for (int j = 0; j < accounts.size(); j++) {
				Account ac = accounts.get(j);
				List list = stockTradeBus.getCurrentWeiTuoList(ac);
				logger.debug("本日挂单记录====>" +list);
				Map<String,String> map = null;
				RestingOrders orders = null;
				for (int i = 0; i < list.size(); i++) {
					//[{entrust_no=18534482, report_time=103859, stock_code=600249, stock_name=两面针, bs_name=买入, status_name=全部成交, entrust_price=4.2700, business_price=4.270, entrust_amount=100, business_amount=100, position_str=null, entrust_bs=1, entrust_status=8, entrust_time=10:38:59, entrust_date=2018-12-05, business_balance=427.00, exchange_type=1, entrust_banlance=427.00}, {entrust_no=18534187, report_time=102759, stock_code=600712, stock_name=南宁百货, bs_name=买入, status_name=全部撤单, entrust_price=4.2900, business_price=0.000, entrust_amount=100, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=10:27:59, entrust_date=2018-12-05, business_balance=0.00, exchange_type=1, entrust_banlance=429.00}, {entrust_no=18533980, report_time=101752, stock_code=600712, stock_name=南宁百货, bs_name=买入, status_name=全部撤单, entrust_price=4.2500, business_price=0.000, entrust_amount=1500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=10:17:52, entrust_date=2018-12-05, business_balance=0.00, exchange_type=1, entrust_banlance=6375.00}, {entrust_no=18533634, report_time=101059, stock_code=600712, stock_name=南宁百货, bs_name=买入, status_name=全部撤单, entrust_price=4.2600, business_price=0.000, entrust_amount=700, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=10:10:59, entrust_date=2018-12-05, business_balance=0.00, exchange_type=1, entrust_banlance=2982.00}, {entrust_no=SNZZ33UH, report_time=100659, stock_code=000911, stock_name=南宁糖业, bs_name=买入, status_name=全部撤单, entrust_price=6.2100, business_price=0.000, entrust_amount=500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=10:06:59, entrust_date=2018-12-05, business_balance=0.00, exchange_type=2, entrust_banlance=3105.00}, {entrust_no=SNZZ336H, report_time=095649, stock_code=000911, stock_name=南宁糖业, bs_name=买入, status_name=全部撤单, entrust_price=6.2200, business_price=0.000, entrust_amount=500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=09:56:49, entrust_date=2018-12-05, business_balance=0.00, exchange_type=2, entrust_banlance=3110.00}, {entrust_no=18533027, report_time=095519, stock_code=600712, stock_name=南宁百货, bs_name=买入, status_name=全部撤单, entrust_price=4.2900, business_price=0.000, entrust_amount=1500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=09:55:19, entrust_date=2018-12-05, business_balance=0.00, exchange_type=1, entrust_banlance=6435.00}, {entrust_no=SNZZ2ZLL, report_time=223912, stock_code=002663, stock_name=普邦股份, bs_name=买入, status_name=全部撤单, entrust_price=2.6200, business_price=0.000, entrust_amount=2500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=22:39:12, entrust_date=2018-12-04, business_balance=0.00, exchange_type=2, entrust_banlance=6550.00}, {entrust_no=SNZZ2ZLF, report_time=222749, stock_code=000911, stock_name=南宁糖业, bs_name=买入, status_name=全部撤单, entrust_price=6.1500, business_price=0.000, entrust_amount=500, business_amount=0, position_str=null, entrust_bs=1, entrust_status=6, entrust_time=22:27:49, entrust_date=2018-12-04, business_balance=0.00, exchange_type=2, entrust_banlance=3075.00}]

					map = (Map) list.get(i);
					
					//entrust_status  6已经撤单，8买入成交，2卖出或卖出挂单之中，当前挂单没有成交的
					
					/* "entrust_no":"SNZZ370Q",
			            "report_time":"113452",
			            "stock_code":"002663",
			            "stock_name":"普邦股份",
			            "bs_name":"卖出",
			            "status_name":"未成交",
			            "entrust_price":"3.1100",
			            "business_price":"0.000",
			            "entrust_amount":"400",
			            "business_amount":"0",
			            "position_str":null,
			            "entrust_bs":"2",
			            "entrust_status":"2",
			            "entrust_time":"11:34:52",
			            "entrust_date":"2018-12-05",
			            "business_balance":"0.00",
			            "exchange_type":"2",
			            "entrust_banlance":"1244.00"*/
					
					String entrust_no = map.get("entrust_no") ;
					String code =map.get("stock_code") ;
					String name =map.get("stock_name") ;
					String bs_name =map.get("bs_name") ;
					String status_name =map.get("status_name") ;//成交，撤单等
					String entrust_price =map.get("entrust_price") ;//委托（挂单）价
					String business_price = map.get("business_price"); //实际成交价格
					String entrust_amount  = map.get("entrust_amount");//挂单量
					String business_amount  = map.get("business_amount");//实际挂单成交股票数量
					String business_balance = map.get("business_balance");//实际成交的挂单总金额
					String entrust_banlance = map.get("entrust_banlance");//挂单总金额
					String entrust_status = map.get("entrust_status");
					String entrust_bs = map.get("entrust_bs");//买卖方向
					String entrust_date = map.get("entrust_date"); //挂单日期
					String entrust_time = map.get("entrust_time");//挂单时间
				
					
					orders = new RestingOrders();
					
					orders.setEntrust_no(entrust_no);
					orders.setStock_code(code);
					orders.setStock_name(name);
					orders.setBs_name(bs_name);
					orders.setStatus_name(status_name);
					orders.setEntrust_price(entrust_price);
					orders.setBusiness_price(business_price);
					orders.setEntrust_amount(entrust_amount);
					orders.setBusiness_amount(business_amount);
					orders.setBusiness_balance(business_balance);
					orders.setEntrust_banlance(entrust_banlance);
					orders.setEntrust_bs(entrust_bs);
					orders.setEntrust_date(entrust_date);
					orders.setEntrust_time(entrust_time);
					orders.setEntrust_status(entrust_status);
					
					
					Sell sell = null;
					Integer buyNum = Integer.valueOf(business_amount);
					if(buyNum>300){
						if("全部成交".equals(status_name)&&"买入".equals(bs_name)){
							 sell  = new Sell();
							 sell.setCode(code);
							 sell.setName(name);
							 sell.setWeiTuoBuyPrice(entrust_price);
							 sell.setWeiTuoBuyNum(business_amount);
							 sell.setBuyDate(entrust_date);
							 sellDao.save(sell);
						}
					}
					restingOrdersDao.save(orders);
					
				}
			}
		}
	}
}
