package com.ye.monitor.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bo.domain.SMSStock;
import com.bo.domain.Stock;
import com.ye.monitor.StockMonitor;
import com.ye.monitor.YeStockHttpUtil;

@Component
public class TencentWeb {
	public static Logger log = LogManager.getLogger(StockMonitor.class);

	@Autowired
	private YeStockHttpUtil yeStockHttpUtil;

	public void mointorAll(List<Stock> stockList) {
		StringBuffer str1 = null;
		String result = null;
		for (Stock stock : stockList) {
			String stockStr = null;
			if(stock.getStockCode().indexOf("6")==0){
				stockStr = "sh" +stock.getStockCode();
			}else{
				stockStr = "sz" +stock.getStockCode();
			}
			String url = "http://qt.gtimg.cn/q=" + stockStr.trim();
			result = yeStockHttpUtil.httpGet(url);

			String[] datas = result.split("~");
			str1 = new StringBuffer();
			str1.append("名字:" + datas[1] + ",代码:" + datas[2] + ",当前价格:" + datas[3] + ",昨收:" + datas[4]);
			str1.append(",今开:" + datas[5] + ",外盘 :" + datas[7] + ",内盘 :" + datas[8] + ",买一 :" + datas[9]);
			str1.append(",买一量（手）:" + datas[10] + ",买二量（手）:" + datas[12] + ",买三:" + datas[13] + ",买三量（手）:" + datas[14]);
			str1.append("买四:" + datas[15] + ",买四量（手）:" + datas[16] + ",买五:" + datas[17] + ",买五量（手）:" + datas[18]);
			str1.append(",卖一:" + datas[19] + ",卖二量（手）:" + datas[22] + ",卖三:" + datas[23] + ",卖三量（手）:" + datas[24]);
			str1.append(",卖四:" + datas[25] + ",卖四量:（手）" + datas[26] + ",卖五:" + datas[27] + ",卖五量（手）:" + datas[28]);
			str1.append(",最近逐笔成交 : " + datas[29] + ",时间 : " + datas[30] + ",涨跌  : " + datas[31] + ",涨跌% :" + datas[32]);
			str1.append(",最高 :" + datas[33] + ",最低: " + datas[34] + ",价格/成交量（手）/成交额 " + datas[35] + ",成交量（手）:" + datas[36]);
			str1.append(",成交额（万）" + datas[37] + ",换手率:" + datas[38] + ",市盈率:" + datas[39] + ",最高:" + datas[41] + ",最低:" + datas[42]);
			str1.append(",振幅:" + datas[43] + ",流通市值: " + datas[44] + ",总市值:" + datas[45] + ",市净率:" + datas[46] + ",涨停价:" + datas[47] + ",跌停价:" + datas[48]);

			log.error(str1.toString());
		}

	}

	public void mointorZiJin(List<Stock> stockList) {
		StringBuffer str1 = null;
		String result = null;
		for (Stock stock : stockList) {
			String stockStr = null;
			if(stock.getStockCode().indexOf("6")==0){
				stockStr = "sh" +stock.getStockCode();
			}else{
				stockStr = "sz" +stock.getStockCode();
			}
			String url = "http://qt.gtimg.cn/q=" + stockStr.trim();
			result = yeStockHttpUtil.httpGet(url);
			String[] datas = result.split("~");

			str1 = new StringBuffer();
			str1.append("代码:" + datas[0] + ",主力流入:" + datas[1] + ",主力流出 :");
			str1.append(datas[2] + ",主力净流入:" + datas[3]);
			str1.append(",主力净流入/资金流入流出总和: " + datas[4] + ",散户流入: ");
			str1.append(datas[5] + ",散户流出: " + datas[6] + ",散户净流入: ");
			str1.append(datas[7] + ",散户净流入/资金流入流出总和 : " + datas[8]);
			str1.append(",资金流入流出总和1+2+5+6: " + datas[9] + ",名字: ");
			str1.append(datas[12] + ",日期 : " + datas[13]);

			log.error(str1.toString());
		}
	}

	public void mointorPanKou(List<Stock> stockList) {
		StringBuffer str1 = null;
		String result = null;
		for (Stock stock : stockList) {
			String stockStr = null;
			if(stock.getStockCode().indexOf("6")==0){
				stockStr = "sh" +stock.getStockCode();
			}else{
				stockStr = "sz" +stock.getStockCode();
			}
			String url = "http://qt.gtimg.cn/q=" + stockStr.trim();
			result = yeStockHttpUtil.httpGet(url);
			String[] datas = result.split("~");
			str1 = new StringBuffer();
			str1 = new StringBuffer();
			str1.append("买盘大单:" + datas[0] + ",买盘小单:" + datas[1] + ",卖盘大单:" + datas[2] + ",卖盘小单:" + datas[3]);

			log.error(str1.toString());
		}
	}

	public void mointorJianYao(List<Stock> stockList) {
		StringBuffer str1 = null;
		String result = null;
		for (Stock stock : stockList) {
			String stockStr = null;
			if(stock.getStockCode().indexOf("6")==0){
				stockStr = "sh" +stock.getStockCode();
			}else{
				stockStr = "sz" +stock.getStockCode();
			}
			String url = "http://qt.gtimg.cn/q=s_" + stockStr.trim();
			result = yeStockHttpUtil.httpGet(url);
			String[] datas = result.split("~");
			str1 = new StringBuffer();
			str1.append("名字:" + datas[1] + ",代码:" + datas[2] + ",当前价格:");
			str1.append(datas[3] + ",涨跌:" + datas[4] + ",涨跌%:" + datas[5]);
			str1.append(",成交量（手）: " + datas[6] + ",成交额（万）: " + datas[7]);
			str1.append(",总市值: " + datas[9]);

			log.error(str1.toString());
		}
	}

	public String smsStockJianYao(SMSStock stock) {
		StringBuffer str1 = null;
		String result = null;
		String stockStr = null;
		if(stock.getStockCode().indexOf("6")==0){
			stockStr = "sh" +stock.getStockCode();
		}else{
			stockStr = "sz" +stock.getStockCode();
		}
		
		String url = "http://qt.gtimg.cn/q=" + stockStr.trim();
		result = yeStockHttpUtil.httpGet(url);
		String[] datas = result.split("~");
		str1 = new StringBuffer();
		str1.append(datas[1] + ",");// 名字 0
		// str1.append(datas[2]+",");//代码1
		str1.append(stock.getStockCode() + ",");// 代码1
		str1.append(datas[3] + ",");// 当前价格2
		str1.append(datas[4] + ",");// 昨收3
		str1.append(datas[33] + ",");// 最高4
		str1.append(datas[34] + ",");// 最低5
		str1.append(datas[31] + ",");// 涨跌: 6
		str1.append(datas[43]);// 振幅:" 7
		//log.error("更新股票" + stockStr);
		//log.error(str1.toString());
		return str1.toString();
	}
	
	
	
	public String stockJianYao(Stock stock) {
		StringBuffer str1 = null;
		String result = null;
		String stockStr = null;
		if(stock.getStockCode().indexOf("6")==0){
			stockStr = "sh" +stock.getStockCode();
		}else{
			stockStr = "sz" +stock.getStockCode();
		}
		String url = "http://qt.gtimg.cn/q=" + stockStr.trim();
		result = yeStockHttpUtil.httpGet(url);
		String[] datas = result.split("~");
		str1 = new StringBuffer();
		str1.append(datas[1] + ",");// 名字 0
		str1.append(datas[2]+",");//代码1
		//str1.append(stock.getStockCode() + ",");// 代码1
		str1.append(datas[3] + ",");// 当前价格2
		str1.append(datas[4] + ",");// 昨收3
		str1.append(datas[33] + ",");// 最高4
		str1.append(datas[34] + ",");// 最低5
		str1.append(datas[31] + ",");// 涨跌: 6
		str1.append(datas[43]);// 振幅:" 7

		log.error(str1.toString());
		return str1.toString();
	}
	
	
	
	
	
	
	
	
	public Map  getStockRelatInfo(String stockCode) {
		String result = null;
			String stockStr = null;
			if(stockCode.indexOf("6")==0){
				stockStr = "sh" +stockCode;
			}else{
				stockStr = "sz" +stockCode;
			}
			try {
				String url = "http://qt.gtimg.cn/q=s_" + stockStr.trim();
				result = yeStockHttpUtil.httpGet(url);
				System.out.println(result);
				if(!result.contains("pv_none_match")){
					String[] datas = result.split("~");
					Map map = new HashMap();
					/*str1.append("名字:" + datas[1] + ",代码:" + datas[2] + ",当前价格:");
					str1.append(datas[3] + ",涨跌:" + datas[4] + ",涨跌%:" + datas[5]);
					str1.append(",成交量（手）: " + datas[6] + ",成交额（万）: " + datas[7]);
					str1.append(",总市值: " + datas[9]);
					*/
					
					map.put("name", datas[1]);
					map.put("code", datas[2]);
					map.put("cprice", datas[3]);
					map.put("crates", datas[4]);
					return map;
				}else{
					return null;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
}
