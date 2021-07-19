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

				str1.append("�г�����:" + datas[0].split("var hq_str_")[1]);
				str1.append(" ,��ǰ:" + datas[3] + " ,����:" + datas[2]);
				str1.append(",�������:" + datas[4] + ",�������:" + datas[5]);
				str1.append(" ,����:" + currentRate + ",��һ:" + datas[6]);
				str1.append(",��һ:" + datas[7] + ",�ɽ�����:");
				str1.append(Double.valueOf(datas[8]) / 1000000 + "����");
				str1.append(",�ɽ����:" + datas[9] + " Ԫ" + ",��һ:");
				str1.append(datas[11] + ",��:" + datas[10] + ",���:");
				str1.append(datas[13] + ",�� :" + datas[12] + ",����:");
				str1.append(datas[15] + ",��:" + datas[14] + ",����:");
				str1.append(datas[17] + ",��:" + datas[16] + ",����:");
				str1.append(datas[19] + ",��:" + datas[18] + ",��һ:");
				str1.append(datas[21] + ",��:" + datas[20] + ",����:");
				str1.append(datas[23] + ",��:" + datas[22] + ",����:");
				str1.append(datas[25] + ",��:" + datas[24] + ",����:");
				str1.append(datas[27] + ",��:" + datas[26] + ",����:");
				str1.append(datas[29] + ",��:" + datas[28] + ",���ڣ�");
				str1.append(datas[30] + ",ʱ�䣺" + datas[31]);

				if(stock.getMostImp()!=0){
					log.log(Level.INFO, "sina=====>" + str1.toString());
				}
				str1 = null;
			}

		}
	}

	// ÿ��Ķ��Ź�
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
			str1.append(datas[0].split("var hq_str_")[1].split("=\"")[0]);// ����0
			str1.append("," + datas[0].split("var hq_str_")[1].split("=\"")[1]);// ����
																				// 1
			str1.append("," + datas[3]);// ��ǰ�� 2
			str1.append("," + datas[4]);// ���3
			str1.append("," + datas[5]);// ���4
			str1.append("," + currentRate);// ����5
			str1.append("," + datas[2]);// ����6
			log.log(Level.INFO, "sina=====>" + str1.toString());
		}
		return str1.toString();
	}

	// �����ģ��û����¹�Ʊ���ƺͰ��ܷ��ʼ�, �Լ������ӣ����췢�ʼ�����
	public String getEmailSendInfo(Stock stock) {
		String stockStr = null;
		if (stock.getStockCode().indexOf("6") == 0) {
			stockStr = "sh" + stock.getStockCode();
		} else {
			stockStr = "sz" + stock.getStockCode();
		}
		System.out.println("===========>���´������"  +stockStr);
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
			str1.append(datas[0].split("var hq_str_")[1].split("=\"")[0]);// ����0
			str1.append("," + datas[0].split("var hq_str_")[1].split("=\"")[1]);// ����1
			str1.append("," + datas[3]);// ��ǰ��2
			str1.append("," + datas[2]);// ����3
			str1.append("," + datas[4]);// ���4
			str1.append("," + datas[5]);// ���5
			str1.append("," + currentRate);// ����6
			if(stock.getMostImp()!=0){
				log.log(Level.INFO, "sina=====>" + str1.toString());
			}

		}
		return str1.toString();
	}
}
