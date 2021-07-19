package com.ly.task.forchicang;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.AccountDao;
import com.ly.dao.ChiCangDao;
import com.ly.pojo.Account;
import com.ly.pojo.ChiCang;
import com.ly.stocktrade.trade.core.StockTradeCore;

@Component
public class ChiChangTask {
	//private static Logger logger = Logger.getLogger(ChiChangTask.class);
	private static final Logger logger = Logger.getLogger("stockrefs");

	@Autowired
	private ChiCangDao chiCangDao;

	@Autowired
	private StockTradeCore stockTradeBus;
	@Autowired
	private AccountDao accountDao;

	//"0 15 10 * * ?"
	//每日凌晨更新持仓数量
	@Scheduled(cron = "0 30 23 ? * MON-FRI")
	public void task() {
		logger.debug("定时更新当前账户的持仓信息");
		List<Account> accounts = accountDao.getAccountList();
		if (accounts != null && accounts.size() > 0) {
			for (int i = 0; i < accounts.size(); i++) {
				Account ac = accounts.get(i);
				List listsMap = stockTradeBus.getChiChang(ac);
				logger.debug(listsMap);
				ChiCang cc  = null;
				if(listsMap!=null&&listsMap.size()>0){
					for (int j = 0; j < listsMap.size(); j++) {
						Map<String,String> temp= (Map) listsMap.get(j);
						cc = new ChiCang();
						String income_balance_ratio = temp.get("income_balance_ratio");
						String stock_code = temp.get("stock_code");
						String stock_name = temp.get("stock_name");
						String current_amount = temp.get("current_amount");
						String enable_amount = temp.get("enable_amount");
						String currentPrice = temp.get("last_price");
						String cost_price = temp.get("cost_price");
						String income_balance = temp.get("income_balance");
						String market_value = temp.get("market_value");
						String enroute_amount = temp.get("enroute_amount");
						
						cc.setCode(stock_code);
						cc.setName(stock_name);
						cc.setCurrent_amount(current_amount);
						cc.setIncome_balance_ratio(income_balance_ratio);
						
						cc.setEnable_amount(enable_amount);
						cc.setLast_price(currentPrice);
						cc.setCost_price(cost_price);
						cc.setIncome_balance(income_balance);
						cc.setMarket_value(market_value);
						cc.setEnroute_amount(enroute_amount);
						
						chiCangDao.save(cc);
						
					}
				
				}
			}
		}
		
	}

	
	/*
	{"errmsg":"查询 我的持仓成功","results":[{"income_balance_ratio":"-31.33","stock_code":"000576","stock_name":"广东甘化","current_amount":"100","enable_amount":"100","last_price":"7.190","cost_price":"10.471","keep_cost_price":null,"income_balance":"-328.05","market_value":"719.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-94.38","stock_code":"000758","stock_name":"中色股份","current_amount":"100","enable_amount":"100","last_price":"3.990","cost_price":"71.034","keep_cost_price":null,"income_balance":"-6704.39","market_value":"399.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-72.19","stock_code":"000979","stock_name":"中弘退","current_amount":"64500","enable_amount":"64500","last_price":"0.260","cost_price":"0.935","keep_cost_price":null,"income_balance":"-43525.07","market_value":"16770.00","position_str":null,"hand_flag":null,"enroute_amount":"64500"},{"income_balance_ratio":"-92.18","stock_code":"000983","stock_name":"西山煤电","current_amount":"100","enable_amount":"100","last_price":"6.150","cost_price":"78.664","keep_cost_price":null,"income_balance":"-7251.38","market_value":"615.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-49.85","stock_code":"002143","stock_name":"印纪传媒","current_amount":"100","enable_amount":"100","last_price":"3.430","cost_price":"6.840","keep_cost_price":null,"income_balance":"-340.99","market_value":"343.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-64.04","stock_code":"002184","stock_name":"海得控制","current_amount":"100","enable_amount":"100","last_price":"10.860","cost_price":"30.197","keep_cost_price":null,"income_balance":"-1933.73","market_value":"1086.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-84.24","stock_code":"002329","stock_name":"皇氏集团","current_amount":"100","enable_amount":"100","last_price":"3.980","cost_price":"25.249","keep_cost_price":null,"income_balance":"-2126.88","market_value":"398.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-84.94","stock_code":"002388","stock_name":"新亚制程","current_amount":"100","enable_amount":"100","last_price":"5.840","cost_price":"38.772","keep_cost_price":null,"income_balance":"-3293.23","market_value":"584.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-28.05","stock_code":"002663","stock_name":"普邦股份","current_amount":"400","enable_amount":"0","last_price":"2.750","cost_price":"3.822","keep_cost_price":null,"income_balance":"-428.72","market_value":"1100.00","position_str":null,"hand_flag":null,"enroute_amount":"400"},{"income_balance_ratio":"2.12","stock_code":"600127","stock_name":"金健米业","current_amount":"0","enable_amount":"0","last_price":"3.230","cost_price":"3.163","keep_cost_price":null,"income_balance":"127.88","market_value":"6137.00","position_str":null,"hand_flag":null,"enroute_amount":"1900"},{"income_balance_ratio":"-72.27","stock_code":"600209","stock_name":"*ST罗顿","current_amount":"100","enable_amount":"100","last_price":"3.710","cost_price":"13.378","keep_cost_price":null,"income_balance":"-966.81","market_value":"371.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-57.90","stock_code":"600249","stock_name":"两面针","current_amount":"100","enable_amount":"100","last_price":"4.280","cost_price":"10.167","keep_cost_price":null,"income_balance":"-1177.40","market_value":"856.00","position_str":null,"hand_flag":null,"enroute_amount":"200"},{"income_balance_ratio":"-62.29","stock_code":"600335","stock_name":"国机汽车","current_amount":"100","enable_amount":"100","last_price":"8.120","cost_price":"21.534","keep_cost_price":null,"income_balance":"-1341.40","market_value":"812.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-48.58","stock_code":"600712","stock_name":"南宁百货","current_amount":"100","enable_amount":"100","last_price":"4.260","cost_price":"8.284","keep_cost_price":null,"income_balance":"-402.42","market_value":"426.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-73.94","stock_code":"600731","stock_name":"湖南海利","current_amount":"100","enable_amount":"100","last_price":"4.820","cost_price":"18.497","keep_cost_price":null,"income_balance":"-1367.71","market_value":"482.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-45.61","stock_code":"601068","stock_name":"中铝国际","current_amount":"200","enable_amount":"200","last_price":"5.670","cost_price":"10.425","keep_cost_price":null,"income_balance":"-951.04","market_value":"1134.00","position_str":null,"hand_flag":null,"enroute_amount":"200"},{"income_balance_ratio":"-84.43","stock_code":"601330","stock_name":"绿色动力","current_amount":"100","enable_amount":"100","last_price":"13.480","cost_price":"86.574","keep_cost_price":null,"income_balance":"-7309.35","market_value":"1348.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-65.15","stock_code":"603336","stock_name":"宏辉果蔬","current_amount":"100","enable_amount":"100","last_price":"18.560","cost_price":"53.254","keep_cost_price":null,"income_balance":"-3469.35","market_value":"1856.00","position_str":null,"hand_flag":null,"enroute_amount":"100"}],"status":1}{"errmsg":"查询 我的持仓成功","results":[{"income_balance_ratio":"-31.33","stock_code":"000576","stock_name":"广东甘化","current_amount":"100","enable_amount":"100","last_price":"7.190","cost_price":"10.471","keep_cost_price":null,"income_balance":"-328.05","market_value":"719.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-94.38","stock_code":"000758","stock_name":"中色股份","current_amount":"100","enable_amount":"100","last_price":"3.990","cost_price":"71.034","keep_cost_price":null,"income_balance":"-6704.39","market_value":"399.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-72.19","stock_code":"000979","stock_name":"中弘退","current_amount":"64500","enable_amount":"64500","last_price":"0.260","cost_price":"0.935","keep_cost_price":null,"income_balance":"-43525.07","market_value":"16770.00","position_str":null,"hand_flag":null,"enroute_amount":"64500"},{"income_balance_ratio":"-92.18","stock_code":"000983","stock_name":"西山煤电","current_amount":"100","enable_amount":"100","last_price":"6.150","cost_price":"78.664","keep_cost_price":null,"income_balance":"-7251.38","market_value":"615.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-49.85","stock_code":"002143","stock_name":"印纪传媒","current_amount":"100","enable_amount":"100","last_price":"3.430","cost_price":"6.840","keep_cost_price":null,"income_balance":"-340.99","market_value":"343.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-64.04","stock_code":"002184","stock_name":"海得控制","current_amount":"100","enable_amount":"100","last_price":"10.860","cost_price":"30.197","keep_cost_price":null,"income_balance":"-1933.73","market_value":"1086.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-84.24","stock_code":"002329","stock_name":"皇氏集团","current_amount":"100","enable_amount":"100","last_price":"3.980","cost_price":"25.249","keep_cost_price":null,"income_balance":"-2126.88","market_value":"398.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-84.94","stock_code":"002388","stock_name":"新亚制程","current_amount":"100","enable_amount":"100","last_price":"5.840","cost_price":"38.772","keep_cost_price":null,"income_balance":"-3293.23","market_value":"584.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-28.05","stock_code":"002663","stock_name":"普邦股份","current_amount":"400","enable_amount":"0","last_price":"2.750","cost_price":"3.822","keep_cost_price":null,"income_balance":"-428.72","market_value":"1100.00","position_str":null,"hand_flag":null,"enroute_amount":"400"},{"income_balance_ratio":"2.12","stock_code":"600127","stock_name":"金健米业","current_amount":"0","enable_amount":"0","last_price":"3.230","cost_price":"3.163","keep_cost_price":null,"income_balance":"127.88","market_value":"6137.00","position_str":null,"hand_flag":null,"enroute_amount":"1900"},{"income_balance_ratio":"-72.27","stock_code":"600209","stock_name":"*ST罗顿","current_amount":"100","enable_amount":"100","last_price":"3.710","cost_price":"13.378","keep_cost_price":null,"income_balance":"-966.81","market_value":"371.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-57.90","stock_code":"600249","stock_name":"两面针","current_amount":"100","enable_amount":"100","last_price":"4.280","cost_price":"10.167","keep_cost_price":null,"income_balance":"-1177.40","market_value":"856.00","position_str":null,"hand_flag":null,"enroute_amount":"200"},{"income_balance_ratio":"-62.29","stock_code":"600335","stock_name":"国机汽车","current_amount":"100","enable_amount":"100","last_price":"8.120","cost_price":"21.534","keep_cost_price":null,"income_balance":"-1341.40","market_value":"812.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-48.58","stock_code":"600712","stock_name":"南宁百货","current_amount":"100","enable_amount":"100","last_price":"4.260","cost_price":"8.284","keep_cost_price":null,"income_balance":"-402.42","market_value":"426.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-73.94","stock_code":"600731","stock_name":"湖南海利","current_amount":"100","enable_amount":"100","last_price":"4.820","cost_price":"18.497","keep_cost_price":null,"income_balance":"-1367.71","market_value":"482.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-45.61","stock_code":"601068","stock_name":"中铝国际","current_amount":"200","enable_amount":"200","last_price":"5.670","cost_price":"10.425","keep_cost_price":null,"income_balance":"-951.04","market_value":"1134.00","position_str":null,"hand_flag":null,"enroute_amount":"200"},{"income_balance_ratio":"-84.43","stock_code":"601330","stock_name":"绿色动力","current_amount":"100","enable_amount":"100","last_price":"13.480","cost_price":"86.574","keep_cost_price":null,"income_balance":"-7309.35","market_value":"1348.00","position_str":null,"hand_flag":null,"enroute_amount":"100"},{"income_balance_ratio":"-65.15","stock_code":"603336","stock_name":"宏辉果蔬","current_amount":"100","enable_amount":"100","last_price":"18.560","cost_price":"53.254","keep_cost_price":null,"income_balance":"-3469.35","market_value":"1856.00","position_str":null,"hand_flag":null,"enroute_amount":"100"}],"status":1}
	 */
}
