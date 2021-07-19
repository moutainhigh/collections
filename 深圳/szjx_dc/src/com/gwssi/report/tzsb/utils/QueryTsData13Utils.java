package com.gwssi.report.tzsb.utils;

import java.util.List;
import java.util.Map;

public class QueryTsData13Utils {

	public String date13yZaiYong(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append( "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>");
		sb.append( "<tr><td colspan='28'><h1>深圳市特种设备使用登记统计表</h1></td></tr>");
		sb.append( "<tr><td rowspan='2'>办理单位</td><td colspan='3'>合计</td><td colspan='3'>锅炉</td><td colspan='3'>压力容器</td><td colspan='3'>电梯</td><td colspan='3'>起重机械</td><td colspan='3'>厂内机动车辆</td><td colspan='3'>大型游乐设施</td><td colspan='3'>压力管道</td><td colspan='3'>客运索道</td></tr>");
		sb.append( "<tr><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td></tr>");

		/*Double total1 = 0.0;
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
		Double total12 = 0.0;*/
		for (int i = 0; i < num.size(); i++) {

			
			
			sb.append("<tr>");
			sb.append("<td>"+num.get(i).get("办理单位")+"</td>");
			sb.append("<td>"+num.get(i).get("合计受理宗数")+"</td><td>"+num.get(i).get("合计申请设备数")+"</td><td>"+num.get(i).get("合计设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("锅炉受理宗数")+"</td><td>"+num.get(i).get("锅炉申请设备数")+"</td><td>"+num.get(i).get("锅炉设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力容器受理宗数")+"</td><td>"+num.get(i).get("压力容器申请设备数")+"</td><td>"+num.get(i).get("压力容器设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("电梯受理宗数")+"</td><td>"+num.get(i).get("电梯申请设备数")+"</td><td>"+num.get(i).get("电梯设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("起重机械受理宗数")+"</td><td>"+num.get(i).get("起重机械申请设备数")+"</td><td>"+num.get(i).get("起重机械设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("场内机动车辆受理宗数")+"</td><td>"+num.get(i).get("场内机动车辆申请设备数")+"</td><td>"+num.get(i).get("场内机动车辆设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("大型游乐设施受理宗数")+"</td><td>"+num.get(i).get("大型游乐设施申请设备数")+"</td><td>"+num.get(i).get("大型游乐设施设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("压力管道受理宗数")+"</td><td>"+num.get(i).get("压力管道申请设备数")+"</td><td>"+num.get(i).get("压力管道设备通过数")+"</td>");
			sb.append("<td>"+num.get(i).get("客运索道受理宗数")+"</td><td>"+num.get(i).get("客运索道申请设备数")+"</td><td>"+num.get(i).get("客运索道设备通过数")+"</td>");
			
			
			//total1 += Double.valueOf(num.get(i).get("项目").toString());
			//total2 += Double.valueOf(num.get(i).get("锅炉").toString());
			
		}
		//sb.append("<tr><td>总计</td><td>"+total2+"</td><td><table><tr><td class='lay01'>"+total3+"</td><td class='lay02'>"+total4+"</td></tr></table></td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	

}
