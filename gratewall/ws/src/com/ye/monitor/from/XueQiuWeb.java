package com.ye.monitor.from;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bo.domain.Stock;
import com.bo.domain.StockEveryDayDeal;
import com.ye.monitor.YeStockHttpUtil;
import com.ye.service.StockService;

@Component
public class XueQiuWeb {
	public static Logger log = LogManager.getLogger(XueQiuWeb.class);

	@Autowired
	private YeStockHttpUtil yeStockHttpUtil;

	@Autowired
	private StockService stockSevice;

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
			String url = "https://xueqiu.com/stock/pankou.json?symbol=" + stockStr.trim().toUpperCase();
			result = yeStockHttpUtil.httpGet(url);

			str1 = new StringBuffer();
			JSONObject jsonObj = JSON.parseObject(result);
			str1.append("����" + jsonObj.get("symbol"));
			str1.append("����  " + stock.getStockName());
			str1.append(",��ǰ�� " + jsonObj.get("current"));
			str1.append(",��һ�� " + jsonObj.get("bp1") + ",��һ�� " + jsonObj.get("bc1"));
			str1.append(",����� " + jsonObj.get("bp2") + ",����� " + jsonObj.get("bc2"));
			str1.append(",������ " + jsonObj.get("bp3") + ",������ " + jsonObj.get("bc3"));
			str1.append(",���ļ� " + jsonObj.get("bp4") + ",������ " + jsonObj.get("bc4"));
			str1.append(",����� " + jsonObj.get("bp5") + ",������ " + jsonObj.get("bc5"));

			str1.append(",��һ�� " + jsonObj.get("sp1") + ",������ " + jsonObj.get("sc1"));
			str1.append(",������ " + jsonObj.get("sp2") + ",������ " + jsonObj.get("sc2"));
			str1.append(",������ " + jsonObj.get("sp3") + ",������ " + jsonObj.get("sc3"));
			str1.append(",���ļ� " + jsonObj.get("sp4") + ",������ " + jsonObj.get("sc4"));
			str1.append(",����� " + jsonObj.get("sp5") + ",������ " + jsonObj.get("sc5"));
			if(stock.getMostImp()!=0){
				log.log(Level.INFO, "xueQiu =====>" + str1.toString());
			}

		}
		str1 = null;

	}

	/**
	 * ����ǵ�������ǰ��
	 * 
	 * @param stockList
	 */
	public String mointorDesAll(Stock stock) {
		String result = null;
		String stockStr = null;
		if(stock.getStockCode().indexOf("6")==0){
			stockStr = "sh" +stock.getStockCode();
		}else{
			stockStr = "sz" +stock.getStockCode();
		}
		String url = "https://xueqiu.com/v4/stock/quotec.json?code=" + stockStr.trim().toUpperCase();
		result = yeStockHttpUtil.httpGet(url);

		JSONObject jsonObj = JSON.parseObject(result);
		String temp = jsonObj.getString(stockStr.toUpperCase());
		JSONArray jsonArray = JSONArray.parseArray(temp);

		String name = stock.getStockName();
		String currentPrice = (String) jsonArray.get(0);
		String rates = (String) jsonArray.get(2);
		String zhangDieFu = (String) jsonArray.get(1);

		double rate = Double.valueOf(rates);
		double price = Double.valueOf(currentPrice);

		StringBuffer str1 = new StringBuffer();
		str1.append(stock.getStockCode().toLowerCase() + "," + name + "," + price + "," + rate+","+zhangDieFu);
		if(stock.getIsconsole()!=0){
			log.log(Level.OFF, "xueQiu =====>" + str1.toString());
		}
		return str1.toString();
	}

}
