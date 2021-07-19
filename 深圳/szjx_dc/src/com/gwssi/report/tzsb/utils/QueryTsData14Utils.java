package com.gwssi.report.tzsb.utils;

import java.util.List;
import java.util.Map;

public class QueryTsData14Utils {
	
	
	
	
	
	
	
	public String downExcelByDw(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append( "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>");
		sb.append("<tr><td colspan='23'><h1>特种设备施工告知受理情况统计查询（按受理单位）</h1></td></tr>");
		sb.append( "<tr>");
		sb.append( "<td rowspan='2'>辖区</td>");
		sb.append( "<td colspan='2'>合计宗数</td>");
		sb.append( "<td colspan='2'>锅炉</td>");
		sb.append( "<td colspan='2'>压力容器</td>");
		sb.append( "<td colspan='2'>电梯</td>");
		sb.append( "<td colspan='2'>起重机械</td>");
		sb.append( "<td colspan='2'>厂内机动车辆</td>");
		sb.append( "<td colspan='2'>大型游乐设施</td>");
		sb.append( "<td colspan='2'>压力管道</td>");
		sb.append( "<td colspan='2'>客运索道</td>");
		sb.append( "</tr>");
		sb.append( "<tr>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "</tr>");
	
		for (int i = 0; i < num.size(); i++) {

			sb.append("<tr>");
			sb.append("<td>"+num.get(i).get("办理单位")+"</td>");
			sb.append("<td>"+num.get(i).get("合计宗数")+"</td><td>"+num.get(i).get("合计台数")+"</td>");
			sb.append("<td>"+num.get(i).get("锅炉宗数")+"</td><td>"+num.get(i).get("锅炉台数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力容器宗数")+"</td><td>"+num.get(i).get("压力容器台数")+"</td>");
			sb.append("<td>"+num.get(i).get("电梯宗数")+"</td><td>"+num.get(i).get("电梯台数")+"</td>");
			sb.append("<td>"+num.get(i).get("起重机械宗数")+"</td><td>"+num.get(i).get("起重机械台数")+"</td>");
			sb.append("<td>"+num.get(i).get("场内机动车辆宗数")+"</td><td>"+num.get(i).get("场内机动车辆台数")+"</td>");
			sb.append("<td>"+num.get(i).get("大型游乐设施宗数")+"</td><td>"+num.get(i).get("大型游乐设施台数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力管道宗数")+"</td><td>"+num.get(i).get("压力管道台数")+"</td>");
			sb.append("<td>"+num.get(i).get("客运索道宗数")+"</td><td>"+num.get(i).get("客运索道台数")+"</td>");
			
			sb.append("</tr>");
			
			
			
		}
		//sb.append("<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	
	

	public String date14ByXiaQu(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append( "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>");
		sb.append("<tr><td colspan='23'><h1>特种设备施工告知受理情况统计查询(按辖区)</h1></td></tr>");
		sb.append( "<tr>");
		sb.append( "<td rowspan='2'>辖区</td>");
		sb.append( "<td colspan='2'>合计宗数</td>");
		sb.append( "<td colspan='2'>锅炉</td>");
		sb.append( "<td colspan='2'>压力容器</td>");
		sb.append( "<td colspan='2'>电梯</td>");
		sb.append( "<td colspan='2'>起重机械</td>");
		sb.append( "<td colspan='2'>厂内机动车辆</td>");
		sb.append( "<td colspan='2'>大型游乐设施</td>");
		sb.append( "<td colspan='2'>压力管道</td>");
		sb.append( "<td colspan='2'>客运索道</td>");
		sb.append( "</tr>");
		sb.append( "<tr>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "<td>宗数</td><td>台数</td>");
		sb.append( "</tr>");
	
		for (int i = 0; i < num.size(); i++) {

			sb.append("<tr>");
			sb.append("<td>"+num.get(i).get("辖区")+"</td>");
			sb.append("<td>"+num.get(i).get("合计宗数")+"</td><td>"+num.get(i).get("合计台数")+"</td>");
			sb.append("<td>"+num.get(i).get("锅炉宗数")+"</td><td>"+num.get(i).get("锅炉台数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力容器宗数")+"</td><td>"+num.get(i).get("压力容器台数")+"</td>");
			sb.append("<td>"+num.get(i).get("电梯宗数")+"</td><td>"+num.get(i).get("电梯台数")+"</td>");
			sb.append("<td>"+num.get(i).get("起重机械宗数")+"</td><td>"+num.get(i).get("起重机械台数")+"</td>");
			sb.append("<td>"+num.get(i).get("场内机动车辆宗数")+"</td><td>"+num.get(i).get("场内机动车辆台数")+"</td>");
			sb.append("<td>"+num.get(i).get("大型游乐设施宗数")+"</td><td>"+num.get(i).get("大型游乐设施台数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力管道宗数")+"</td><td>"+num.get(i).get("压力管道台数")+"</td>");
			sb.append("<td>"+num.get(i).get("客运索道宗数")+"</td><td>"+num.get(i).get("客运索道台数")+"</td>");
			
			sb.append("</tr>");
			
			
			
			
		}
		//sb.append("<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
}
