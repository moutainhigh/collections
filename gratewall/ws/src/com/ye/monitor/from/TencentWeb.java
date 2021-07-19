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
			str1.append("����:" + datas[1] + ",����:" + datas[2] + ",��ǰ�۸�:" + datas[3] + ",����:" + datas[4]);
			str1.append(",��:" + datas[5] + ",���� :" + datas[7] + ",���� :" + datas[8] + ",��һ :" + datas[9]);
			str1.append(",��һ�����֣�:" + datas[10] + ",��������֣�:" + datas[12] + ",����:" + datas[13] + ",���������֣�:" + datas[14]);
			str1.append("����:" + datas[15] + ",���������֣�:" + datas[16] + ",����:" + datas[17] + ",���������֣�:" + datas[18]);
			str1.append(",��һ:" + datas[19] + ",���������֣�:" + datas[22] + ",����:" + datas[23] + ",���������֣�:" + datas[24]);
			str1.append(",����:" + datas[25] + ",������:���֣�" + datas[26] + ",����:" + datas[27] + ",���������֣�:" + datas[28]);
			str1.append(",�����ʳɽ� : " + datas[29] + ",ʱ�� : " + datas[30] + ",�ǵ�  : " + datas[31] + ",�ǵ�% :" + datas[32]);
			str1.append(",��� :" + datas[33] + ",���: " + datas[34] + ",�۸�/�ɽ������֣�/�ɽ��� " + datas[35] + ",�ɽ������֣�:" + datas[36]);
			str1.append(",�ɽ����" + datas[37] + ",������:" + datas[38] + ",��ӯ��:" + datas[39] + ",���:" + datas[41] + ",���:" + datas[42]);
			str1.append(",���:" + datas[43] + ",��ͨ��ֵ: " + datas[44] + ",����ֵ:" + datas[45] + ",�о���:" + datas[46] + ",��ͣ��:" + datas[47] + ",��ͣ��:" + datas[48]);

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
			str1.append("����:" + datas[0] + ",��������:" + datas[1] + ",�������� :");
			str1.append(datas[2] + ",����������:" + datas[3]);
			str1.append(",����������/�ʽ����������ܺ�: " + datas[4] + ",ɢ������: ");
			str1.append(datas[5] + ",ɢ������: " + datas[6] + ",ɢ��������: ");
			str1.append(datas[7] + ",ɢ��������/�ʽ����������ܺ� : " + datas[8]);
			str1.append(",�ʽ����������ܺ�1+2+5+6: " + datas[9] + ",����: ");
			str1.append(datas[12] + ",���� : " + datas[13]);

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
			str1.append("���̴�:" + datas[0] + ",����С��:" + datas[1] + ",���̴�:" + datas[2] + ",����С��:" + datas[3]);

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
			str1.append("����:" + datas[1] + ",����:" + datas[2] + ",��ǰ�۸�:");
			str1.append(datas[3] + ",�ǵ�:" + datas[4] + ",�ǵ�%:" + datas[5]);
			str1.append(",�ɽ������֣�: " + datas[6] + ",�ɽ����: " + datas[7]);
			str1.append(",����ֵ: " + datas[9]);

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
		str1.append(datas[1] + ",");// ���� 0
		// str1.append(datas[2]+",");//����1
		str1.append(stock.getStockCode() + ",");// ����1
		str1.append(datas[3] + ",");// ��ǰ�۸�2
		str1.append(datas[4] + ",");// ����3
		str1.append(datas[33] + ",");// ���4
		str1.append(datas[34] + ",");// ���5
		str1.append(datas[31] + ",");// �ǵ�: 6
		str1.append(datas[43]);// ���:" 7
		//log.error("���¹�Ʊ" + stockStr);
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
		str1.append(datas[1] + ",");// ���� 0
		str1.append(datas[2]+",");//����1
		//str1.append(stock.getStockCode() + ",");// ����1
		str1.append(datas[3] + ",");// ��ǰ�۸�2
		str1.append(datas[4] + ",");// ����3
		str1.append(datas[33] + ",");// ���4
		str1.append(datas[34] + ",");// ���5
		str1.append(datas[31] + ",");// �ǵ�: 6
		str1.append(datas[43]);// ���:" 7

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
					/*str1.append("����:" + datas[1] + ",����:" + datas[2] + ",��ǰ�۸�:");
					str1.append(datas[3] + ",�ǵ�:" + datas[4] + ",�ǵ�%:" + datas[5]);
					str1.append(",�ɽ������֣�: " + datas[6] + ",�ɽ����: " + datas[7]);
					str1.append(",����ֵ: " + datas[9]);
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
