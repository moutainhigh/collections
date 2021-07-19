package com.ye.monitor.from;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bo.domain.SMSStock;
import com.bo.domain.Stock;
import com.ye.monitor.YeStockHttpUtil;

@Component
public class SinaWeb {
	public static Logger log = LogManager.getLogger(SinaWeb.class);

	@Autowired
	private YeStockHttpUtil yeStockHttpUtil;

	public void mointorAll(List<Stock> stockList) {
		StringBuffer str1 = null;
		String result = null;
		for (Stock stock : stockList) {
			String stockStr = null;
			if (stock.getStockCode().indexOf("6") == 0) {
				stockStr = "sh" + stock.getStockCode();
			} else {
				stockStr = "sz" + stock.getStockCode();
			}

			String url = "http://hq.sinajs.cn/list=" + stockStr.trim();

			result = yeStockHttpUtil.httpGet(url);
			String[] stocks = result.split(";");
			for (int j = 0; j < stocks.length - 1; j++) {
				String[] datas = stocks[j].split(",");

				str1 = new StringBuffer();
				double currentRate = (Double.valueOf(datas[3]) - Double.valueOf(datas[2])) / Double.valueOf(datas[2]);

				str1.append("市场代码:" + datas[0].split("var hq_str_")[1]);
				str1.append(" ,当前:" + datas[3] + " ,昨收:" + datas[2]);
				str1.append(",今日最高:" + datas[4] + ",今日最低:" + datas[5]);
				str1.append(" ,幅度:" + currentRate + ",买一:" + datas[6]);
				str1.append(",卖一:" + datas[7] + ",成交总手:");
				str1.append(Double.valueOf(datas[8]) / 1000000 + "【万】");
				str1.append(",成交金额:" + datas[9] + " 元" + ",买一:");
				str1.append(datas[11] + ",量:" + datas[10] + ",买二:");
				str1.append(datas[13] + ",量 :" + datas[12] + ",买三:");
				str1.append(datas[15] + ",量:" + datas[14] + ",买四:");
				str1.append(datas[17] + ",量:" + datas[16] + ",买五:");
				str1.append(datas[19] + ",量:" + datas[18] + ",卖一:");
				str1.append(datas[21] + ",量:" + datas[20] + ",卖二:");
				str1.append(datas[23] + ",量:" + datas[22] + ",卖三:");
				str1.append(datas[25] + ",量:" + datas[24] + ",卖四:");
				str1.append(datas[27] + ",量:" + datas[26] + ",卖五:");
				str1.append(datas[29] + ",量:" + datas[28] + ",日期：");
				str1.append(datas[30] + ",时间：" + datas[31]);

				if(stock.getMostImp()!=0){
					log.log(Level.INFO, "sina=====>" + str1.toString());
				}
				str1 = null;
			}

		}
	}

	// 每天的短信股
	public String getSMSEmailSendInfo(SMSStock stock) {
		String stockStr = null;
		if (stock.getStockCode().indexOf("6") == 0) {
			stockStr = "sh" + stock.getStockCode();
		} else {
			stockStr = "sz" + stock.getStockCode();
		}
		String url = "http://hq.sinajs.cn/list=" + stockStr.trim();
		String result = yeStockHttpUtil.httpGet(url);
		StringBuffer str1 = null;
		String[] stocks = result.split(";");
		for (int j = 0; j < stocks.length - 1; j++) {
			String[] datas = stocks[j].split(",");
			str1 = new StringBuffer();
			double currentRate = (Double.valueOf(datas[3]) - Double.valueOf(datas[2])) / Double.valueOf(datas[2]);
			System.out.println("++++++++++++++++++++++++++++++++++" + currentRate);
			str1.append(datas[0].split("var hq_str_")[1].split("=\"")[0]);// 代码0
			str1.append("," + datas[0].split("var hq_str_")[1].split("=\"")[1]);// 名称
																				// 1
			str1.append("," + datas[3]);// 当前价 2
			str1.append("," + datas[4]);// 最高3
			str1.append("," + datas[5]);// 最低4
			str1.append("," + currentRate);// 幅度5
			str1.append("," + datas[2]);// 昨收6
			log.log(Level.INFO, "sina=====>" + str1.toString());
		}
		return str1.toString();
	}

	// 正常的，用户更新股票名称和按周发邮件, 以及按分钟，按天发邮件功能
	public String getEmailSendInfo(Stock stock) {
		String stockStr = null;
		if (stock.getStockCode().indexOf("6") == 0) {
			stockStr = "sh" + stock.getStockCode();
		} else {
			stockStr = "sz" + stock.getStockCode();
		}
		System.out.println("===========>更新代码出差"  +stockStr);
		String url = "http://hq.sinajs.cn/list=" + stockStr.trim();
		String result = yeStockHttpUtil.httpGet(url);
		StringBuffer str1 = null;
		String[] stocks = result.split(";");
		for (int j = 0; j < stocks.length - 1; j++) {
			String[] datas = stocks[j].split(",");
			str1 = new StringBuffer();
			//DecimalFormat df = new DecimalFormat("#0.00");
			// String rates = df.format(stocks.get(i).getRates());
			double currentRate = (Double.valueOf(datas[3]) - Double.valueOf(datas[2])) / Double.valueOf(datas[2]);
			str1.append(datas[0].split("var hq_str_")[1].split("=\"")[0]);// 代码0
			str1.append("," + datas[0].split("var hq_str_")[1].split("=\"")[1]);// 名称1
			str1.append("," + datas[3]);// 当前价2
			str1.append("," + datas[2]);// 昨收3
			str1.append("," + datas[4]);// 最高4
			str1.append("," + datas[5]);// 最低5
			str1.append("," + currentRate);// 幅度6
			if(stock.getMostImp()!=0){
				log.log(Level.INFO, "sina=====>" + str1.toString());
			}

		}
		return str1.toString();
	}
}
