package com.gwssi.report.tzsb.utils;

import java.util.List;
import java.util.Map;

public class QueryTsData22Utils {

	public String excelByXiaQu(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='13'><h1>设备所在场所的统计查询(按设备所在区域统计)</h1></td></tr>");
		sb.append("<tr><td>区域</td><td>车站</td><td>口岸</td><td>客运码头</td><td>商场</td><td>学校</td><td>幼儿园</td><td>医院</td><td>体育场馆</td><td>展览馆</td><td>公园</td><td>地铁</td><td>合计</td></tr>");
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
			sb.append("<tr><td>" + num.get(i).get("equAddrCountyCode") + "</td><td>" + num.get(i).get("车站") + "</td><td>" + num.get(i).get("口岸") + "</td><td>" + num.get(i).get("客运码头") + "</td>" + "<td>"
					+ num.get(i).get("商场") + "</td><td>" + num.get(i).get("学校") + "</td><td>" + num.get(i).get("幼儿园") + "</td><td>" + num.get(i).get("医院") + "</td><td>" + num.get(i).get("体育场馆") + "</td><td>"
					+ num.get(i).get("展览馆") + "</td><td>" + num.get(i).get("公园") + "</td><td>" + num.get(i).get("地铁") + "</td><td>" + num.get(i).get("合计") + "</td></tr>");

			total1 += Double.valueOf(num.get(i).get("车站").toString());
			total2 += Double.valueOf(num.get(i).get("口岸").toString());
			total3 += Double.valueOf(num.get(i).get("客运码头").toString());
			total4 += Double.valueOf(num.get(i).get("商场").toString());
			total5 += Double.valueOf(num.get(i).get("学校").toString());
			total6 += Double.valueOf(num.get(i).get("幼儿园").toString());
			total7 += Double.valueOf(num.get(i).get("医院").toString());
			total8 += Double.valueOf(num.get(i).get("体育场馆").toString());
			total9 += Double.valueOf(num.get(i).get("展览馆").toString());
			total10 += Double.valueOf(num.get(i).get("公园").toString());
			total11 += Double.valueOf(num.get(i).get("地铁").toString());
			total12 += Double.valueOf(num.get(i).get("合计").toString());
		}

		sb.append("<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td><td>"+total12+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	public String ExcelByType(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<table   border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + "<tr><td colspan='13'><h1>设备所属类别统计查询</h1></td></tr>");
		sb.append("<tr><td>设备类型</td><td>车站</td><td>口岸</td><td>客运码头</td><td>商场</td><td>学校</td><td>幼儿园</td><td>医院</td><td>体育场馆</td><td>展览馆</td><td>公园</td><td>地铁</td><td>合计</td></tr>");
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
			sb.append("<tr><td>" + num.get(i).get("equAddrCountyCode") + "</td><td>" + num.get(i).get("车站") + "</td><td>" + num.get(i).get("口岸") + "</td><td>" + num.get(i).get("客运码头") + "</td>" + "<td>"
					+ num.get(i).get("商场") + "</td><td>" + num.get(i).get("学校") + "</td><td>" + num.get(i).get("幼儿园") + "</td><td>" + num.get(i).get("医院") + "</td><td>" + num.get(i).get("体育场馆") + "</td><td>"
					+ num.get(i).get("展览馆") + "</td><td>" + num.get(i).get("公园") + "</td><td>" + num.get(i).get("地铁") + "</td><td>" + num.get(i).get("合计") + "</td></tr>");

			total1 += Double.valueOf(num.get(i).get("车站").toString());
			total2 += Double.valueOf(num.get(i).get("口岸").toString());
			total3 += Double.valueOf(num.get(i).get("客运码头").toString());
			total4 += Double.valueOf(num.get(i).get("商场").toString());
			total5 += Double.valueOf(num.get(i).get("学校").toString());
			total6 += Double.valueOf(num.get(i).get("幼儿园").toString());
			total7 += Double.valueOf(num.get(i).get("医院").toString());
			total8 += Double.valueOf(num.get(i).get("体育场馆").toString());
			total9 += Double.valueOf(num.get(i).get("展览馆").toString());
			total10 += Double.valueOf(num.get(i).get("公园").toString());
			total11 += Double.valueOf(num.get(i).get("地铁").toString());
			total12 += Double.valueOf(num.get(i).get("合计").toString());
			
			
		}

		sb.append("<tr><td>总计</td><td>"+total1+"</td><td>"+total2+"</td><td>"+total3+"</td><td>"+total4+"</td><td>"+total5+"</td><td>"+total6+"</td><td>"+total7+"</td><td>"+total8+"</td><td>"+total9+"</td><td>"+total10+"</td><td>"+total11+"</td><td>"+total12+"</td></tr>");

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}
