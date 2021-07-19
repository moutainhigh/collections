package com.gwssi.report.tzsb.utils;

import java.util.List;
import java.util.Map;

public class QueryTsData24Utils {

	public String excelByXiaQu(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta charset='UTF-8'>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'><tr><td colspan='8'><h1>特种设备统计(区域)</h1></td></tr>");
		sb.append("<tr><td>区域</td><td>在用</td><td>停用</td><td>在建</td><td>注销</td><td>报废</td><td>待核实</td><td>合计</td></tr>");
		
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
			sb.append("<tr><td>" + num.get(i).get("区域") + "</td><td>" +num.get(i).get("在用") + "</td><td>" + num.get(i).get("停用") + "</td><td>" + num.get(i).get("在建") + "</td><td>" + num.get(i).get("注销") + "</td><td>" + num.get(i).get("报废") + "</td><td>" +num.get(i).get("待核实") + "</td><td>" + num.get(i).get("合计") + "</td></tr>");

			//total1 += Integer.valueOf(num.get(i).get("区域").toString());
			total2 += Double.valueOf(num.get(i).get("在用").toString());
			total3 += Double.valueOf(num.get(i).get("停用").toString());
			total4 += Double.valueOf(num.get(i).get("在建").toString());
			total5 += Double.valueOf(num.get(i).get("注销").toString());
			total6 += Double.valueOf(num.get(i).get("报废").toString());
			total7 += Double.valueOf(num.get(i).get("待核实").toString());
			total8 += Double.valueOf(num.get(i).get("合计").toString());
		}

		sb.append("<tr><td>总计</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	
	//品种
	public String ExcelByPingZhong(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'><tr><td colspan='8'><h1>特种设备统计（品种）</h1></td></tr>");
		sb.append("<tr><td>设备品种</td><td>在用</td><td>停用</td><td>在建</td><td>注销</td><td>报废</td><td>待核实</td><td>合计</td></tr>");
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
			sb.append("<tr><td>"+num.get(i).get("设备品种")+"</td>"+
					"				<td>"+num.get(i).get("在用")+"</td>"+
					"				<td>"+num.get(i).get("停用")+"</td>"+
					"				<td>"+num.get(i).get("在建")+"</td>"+
					"				<td>"+num.get(i).get("注销")+"</td>"+
					"				<td>"+num.get(i).get("报废")+"</td>"+
					"				<td>"+num.get(i).get("待核实")+"</td>"+
					"				<td>"+num.get(i).get("合计")+"</td>"+
					"			</tr>"); 
					
					total1+=Double.valueOf(num.get(i).get("在用").toString());
					total2+=Double.valueOf(num.get(i).get("停用").toString());
					total3+=Double.valueOf(num.get(i).get("在建").toString());
					total4+=Double.valueOf(num.get(i).get("注销").toString());
					total5+=Double.valueOf(num.get(i).get("报废").toString());
					total6+=Double.valueOf(num.get(i).get("待核实").toString());
					total7+=Double.valueOf(num.get(i).get("合计").toString());
		}

		sb.append("<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td></tr>");

		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}
