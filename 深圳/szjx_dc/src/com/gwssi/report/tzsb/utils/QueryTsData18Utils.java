package com.gwssi.report.tzsb.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class QueryTsData18Utils {

	public String date18ByDingDian(List<Map> num,String type,Map map) {
		
		
		
		StringBuffer sb = new StringBuffer();
		if(type==null) {
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='15'><h1>特种设备定检率统计表</h1></td></tr>");
			sb.append("<tr><td>设备种类</td><td>统计类型</td><td>福田局</td><td>罗湖局</td><td>南山局</td><td>盐田局</td><td>宝安局</td><td>龙岗局</td><td>光明局</td><td>坪山局</td><td>龙华局</td><td>大鹏局</td><td>深汕监管局</td><td>未分派</td><td>合计</td></tr>");
			Double total1 = 0.0;
			Double total2 = 0.0;
			Double total3 = 0.0;
			Double total4 = 0.0;
			Double total5 = 0.0;
			Double total6 = 0.0;
			Double total7 = 0.0;
			Double total8 = 0.0;
			Double total9 = 0.0;
			Double total10 = 0.0;
			Double total11 = 0.0;
			Double total12 = 0.0;
			
			for (int i = 0; i < num.size(); i++) {
				
			
			sb.append("<tr><td>" + num.get(i).get("品种种类")+ "</td><td>" + num.get(i).get("统计类型") + "</td><td>" + num.get(i).get("福田局") + "</td><td>" + num.get(i).get("罗湖局")+"</td><td>" +num.get(i).get("南山局") + "</td><td>"
					+ num.get(i).get("盐田局") + "</td><td>" + num.get(i).get("宝安局") + "</td><td>" + num.get(i).get("龙岗局") + "</td><td>" + num.get(i).get("光明局") + "</td><td>" +num.get(i).get("坪山局")
				+ "</td><td>" + num.get(i).get("龙华局")+ "</td><td>" +num.get(i).get("大鹏局") + "</td><td>" + num.get(i).get("深汕监管局") + "</td><td>" + num.get(i).get("未分派") + "</td><td>" + num.get(i).get("合计") + "</td></tr>");

			total1 += Double.valueOf(num.get(i).get("福田局").toString());
			total2 +=  Double.valueOf(num.get(i).get("罗湖局").toString());
			total3 +=  Double.valueOf(num.get(i).get("南山局").toString());
			total4 += Double.valueOf(num.get(i).get("盐田局").toString()) ;
			total5 += Double.valueOf(num.get(i).get("宝安局").toString()) ;
			total6 +=  Double.valueOf(num.get(i).get("龙岗局").toString());
			total7 += Double.valueOf(num.get(i).get("光明局").toString());
			total8 += Double.valueOf(num.get(i).get("坪山局").toString()) ;
			total9 +=  Double.valueOf(num.get(i).get("龙华局").toString());
			total10 += Double.valueOf(num.get(i).get("大鹏局").toString());
			total10 += Double.valueOf(num.get(i).get("深汕监管局").toString());
			total10 += Double.valueOf(num.get(i).get("未分派").toString());
			total12 += Double.valueOf(num.get(i).get("合计").toString());
			
			}
			/*sb.append("<tr><td>总计</td><td></td><td>" + total1 + "</td><td>" + total2 + "</td><td>" + total3 + "</td><td>" + total4 + "</td><td>" + total5 + "</td><td>" + total6
					+ "</td><td>" + total7 + "</td><td>" + total8 + "</td><td>" + total9 + "</td><td>" + total10 + "</td><td>" + total12
					+ "</td></tr>");*/
			
			
			sb.append("</table>");

			sb.append("</body>");
			sb.append("</html>");
		}else {
			
			Map<String, String> codeMap = new HashMap<String, String>();

			codeMap.put("福田局", "440304");
			codeMap.put("罗湖局", "440303");
			codeMap.put("南山局", "440305");
			codeMap.put("盐田局", "440308");
			codeMap.put("宝安局", "440306");
			codeMap.put("龙岗局", "440307");
			codeMap.put("光明局", "440309");
			codeMap.put("坪山局", "440310");
			codeMap.put("龙华局", "440342");
			codeMap.put("大鹏局", "440343");
			codeMap.put("深汕监管局", "440344");


			Map<String, String> keyMap = new HashMap<String, String>();

			keyMap.put("4000", "福田局");
			keyMap.put("4100", "罗湖局");
			keyMap.put("4200", "南山局");
			keyMap.put("4300", "盐田局");
			keyMap.put("4400", "宝安局");
			keyMap.put("4500", "龙岗局");
			keyMap.put("4600", "光明局");
			keyMap.put("4700", "坪山局");
			keyMap.put("4800", "龙华局");
			keyMap.put("4900", "大鹏局");
			keyMap.put("4A00", "深汕监管局");
			
			
			String regCode = (String) map.get("regCode");
			String adminbrancode = (String) map.get("adminbrancode");
			String gongshangJu = keyMap.get(regCode);
			String codeOld = codeMap.get(gongshangJu);
			StringBuffer sb2 = new StringBuffer();
			String  jianGuanSuo  =  (String) map.get("jianguasuo");
		
			
			
			String title = "";
			if(!StringUtils.isEmpty(gongshangJu)) {
				title = gongshangJu;
			}
			
			if(!StringUtils.isEmpty(jianGuanSuo)) {
				title = jianGuanSuo;
			}
			
			
			
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'><tr><td colspan='15'><h1>特种设备定检率统计表</h1></td></tr>");
			sb.append("<tr><td>设备种类</td><td>统计类型</td><td>"+title+"</td><td>合计</td></tr>");
			Double total1 = 0.0;
			Double total2 = 0.0;
			Double total3 = 0.0;
			Double total4 = 0.0;
			Double total5 = 0.0;
			Double total6 = 0.0;
			Double total7 = 0.0;
			Double total8 = 0.0;
			Double total9 = 0.0;
			Double total10 = 0.0;
			Double total11 = 0.0;
			Double total12 = 0.0;
			
			for (int i = 0; i < num.size(); i++) {
				System.out.println(type);
				sb.append("<tr><td>" + num.get(i).get("品种种类")+ "</td><td>" + num.get(i).get("统计类型") + "</td><td>" + num.get(i).get(title) + "</td><td>" + num.get(i).get("合计") + "</td></tr>");
	
				total1 += Double.valueOf(num.get(i).get(title).toString());
				total12 += Double.valueOf(num.get(i).get("合计").toString());
				
				}
				//sb.append("<tr><td>总计</td><td></td><td>" + total2 + "</td><td>" + total3 + "</td></tr>");
				sb.append("</table>");
	
				sb.append("</body>");
				sb.append("</html>");
		}
		
		
		
		
		return sb.toString();
	}


}
