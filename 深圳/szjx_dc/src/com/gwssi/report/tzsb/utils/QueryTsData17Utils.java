package com.gwssi.report.tzsb.utils;

import java.util.List;
import java.util.Map;

public class QueryTsData17Utils {

	public String date17ByZaiYong(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>" + "<tr><td colspan='10'><h1>深圳市在用特种设备统计表</h1></td></tr>");
		sb.append("<tr><td>项目</td><td>锅炉</td><td width='250'><table><tr><td colspan='2'>压力容器</td></tr><tr><td class='lay01'>合计</td><td  class='lay02'>非简单压力容器</td></tr></table></td><td>电梯</td><td>起重机械</td><td>场车</td><td>游乐设施</td><td>压力管道</td><td>客运索道</td><td>合计</td></tr>");
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
			sb.append("<tr><td>" + num.get(i).get("项目")+ "</td><td>" + num.get(i).get("锅炉") + "</td><td><table><tr><td class='lay01'>"+num.get(i).get("压力容器合计")+"</td><td class='lay02'>"+num.get(i).get("非简单压力容器")+"</td></tr></table></td><td>" + num.get(i).get("电梯") + "</td><td>"+ num.get(i).get("起重机械") + "</td><td>" + num.get(i).get("场车") + "</td><td>" + num.get(i).get("游乐设施") + "</td><td>" + num.get(i).get("压力管道") + "</td><td>" + num.get(i).get("客运索道") + "</td><td>"+ num.get(i).get("合计") + "</td></tr>");

			//total1 += Double.valueOf(num.get(i).get("项目").toString());
			total2 += Double.valueOf(num.get(i).get("锅炉").toString());
			total3 += Double.valueOf(num.get(i).get("压力容器合计").toString());
			
			total4 += Double.valueOf(num.get(i).get("非简单压力容器").toString());
			total5 += Double.valueOf(num.get(i).get("电梯").toString());
			total6 += Double.valueOf(num.get(i).get("起重机械").toString());
			total7 += Double.valueOf(num.get(i).get("场车").toString());
			total8 += Double.valueOf(num.get(i).get("游乐设施").toString());
			total9 += Double.valueOf(num.get(i).get("压力管道").toString());
			total10 += Double.valueOf(num.get(i).get("客运索道").toString());
			total11 += Double.valueOf(num.get(i).get("合计").toString());
			
		}
	//	sb.append("<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	public String date17ByDanWei(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>" + "<tr><td colspan='10'><h1>深圳市在用设备按使用单位统计表</h1></td></tr>");
		sb.append("<tr><td>辖区</td><td>锅炉</td><td width='250'><table><tr><td colspan='2'>压力容器</td></tr><tr><td class='lay01'>合计</td><td  class='lay02'>非简单压力容器</td></tr></table></td><td>电梯</td><td>起重机械</td><td>场车</td><td>游乐设施</td><td>压力管道</td><td>客运索道</td><td>特种设备</td></tr>");
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
			sb.append("<tr><td>" + num.get(i).get("项目")+ "</td><td>" + num.get(i).get("锅炉") + "</td><td><table><tr><td class='lay01'>"+num.get(i).get("压力容器合计")+"</td><td class='lay02'>"+num.get(i).get("非简单压力容器")+"</td></tr></table></td><td>" + num.get(i).get("电梯") + "</td><td>"
					+ num.get(i).get("起重机械") + "</td><td>" + num.get(i).get("场车") + "</td><td>" + num.get(i).get("游乐设施") + "</td><td>" + num.get(i).get("压力管道") + "</td><td>" + num.get(i).get("客运索道") + "</td><td>"
					+ num.get(i).get("合计") + "</td></tr>");
			
			//total1 += Double.valueOf(num.get(i).get("项目").toString());
			total2 += Double.valueOf(num.get(i).get("锅炉").toString());
			total3 += Double.valueOf(num.get(i).get("压力容器合计").toString());
			total4 += Double.valueOf(num.get(i).get("非简单压力容器").toString());
			total5 += Double.valueOf(num.get(i).get("电梯").toString());
			total6 += Double.valueOf(num.get(i).get("起重机械").toString());
			total7 += Double.valueOf(num.get(i).get("场车").toString());
			total8 += Double.valueOf(num.get(i).get("游乐设施").toString());
			total9 += Double.valueOf(num.get(i).get("压力管道").toString());
			total10 += Double.valueOf(num.get(i).get("客运索道").toString());
			total11 += Double.valueOf(num.get(i).get("合计").toString());
		}

		//sb.append("<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}
