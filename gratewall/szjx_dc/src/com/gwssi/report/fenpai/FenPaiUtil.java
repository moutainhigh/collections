package com.gwssi.report.fenpai;

import java.util.List;
import java.util.Map;

/**
 * 页面下载
 * @author lokn
 *
 */
public class FenPaiUtil {
	
	/**
	 * 下载
	 * 	  --xxx分派数年度月份对比统计报表
	 * @param month 月份
	 * @param query 查询的统计数据
	 * @param name  分派数类型   如：登记部门
	 * @return 
	 */
	public String downReport(List<Map> month, List<Map> query, String name) {
		if (month == null || month.size() == 0) {
			return null;
		}
		if (query == null || query.size() == 0) {
			return null;
		}
		StringBuffer str = new StringBuffer();
		str.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\""
				+ "xmlns:x=\"urn:schemas-microsoft-com:office:excel\""
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\">"
				+ "<head>"
				+ "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">"
				+ "<meta name=ProgId content=Excel.Sheet>"
				+ "<meta name=Generator content=\"Microsoft Excel 15\">"
				+ "<link rel=File-List href=\"" + name + ".files/filelist.xml\">"
				+ "<style id=\"" + name + "_2360_Styles\">"
				+ "<!--table"
				+ "	{mso-displayed-decimal-separator:\"\\.\";"
				+ "	mso-displayed-thousand-separator:\"\\,\";}"
				+ ".font52360"
				+ "	{color:windowtext;"
				+ "	font-size:9.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:等线;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;}"
				+ ".xl152360"
				+ "	{padding-top:1px;"
				+ "	padding-right:1px;"
				+ "	padding-left:1px;"
				+ "	mso-ignore:padding;"
				+ "	color:black;"
				+ "	font-size:11.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:等线;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-number-format:General;"
				+ "	text-align:general;"
				+ "	vertical-align:bottom;"
				+ "	mso-background-source:auto;"
				+ "	mso-pattern:auto;"
				+ "	white-space:nowrap;}"
				+ ".xl632360"
				+ "	{padding-top:1px;"
				+ "	padding-right:1px;"
				+ "	padding-left:1px;"
				+ "	mso-ignore:padding;"
				+ "	color:black;"
				+ "	font-size:14.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:等线;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-number-format:General;"
				+ "	text-align:center;"
				+ "	vertical-align:middle;"
				+ "	border:.5pt solid windowtext;"
				+ "	mso-background-source:auto;"
				+ "	mso-pattern:auto;"
				+ "	white-space:nowrap;}"
				+ ".xl642360"
				+ "	{padding-top:1px;"
				+ "	padding-right:1px;"
				+ "	padding-left:1px;"
				+ "	mso-ignore:padding;"
				+ "	color:black;"
				+ "	font-size:12.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:宋体;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-number-format:General;"
				+ "	text-align:left;"
				+ "	vertical-align:bottom;"
				+ "	border:.5pt solid windowtext;"
				+ "	mso-background-source:auto;"
				+ "	mso-pattern:auto;"
				+ "	white-space:nowrap;}"
				+ ".xl652360"
				+ "	{padding-top:1px;"
				+ "	padding-right:1px;"
				+ "	padding-left:1px;"
				+ "	mso-ignore:padding;"
				+ "	color:black;"
				+ "	font-size:12.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:宋体;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-number-format:General;"
				+ "	text-align:center;"
				+ "	vertical-align:bottom;"
				+ "	border:.5pt solid windowtext;"
				+ "	mso-background-source:auto;"
				+ "	mso-pattern:auto;"
				+ "	white-space:nowrap;}"
				+ ".xl662360"
				+ "	{padding-top:1px;"
				+ "	padding-right:1px;"
				+ "	padding-left:1px;"
				+ "	mso-ignore:padding;"
				+ "	color:black;"
				+ "	font-size:12.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:宋体;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-number-format:General;"
				+ "	text-align:right;"
				+ "	vertical-align:bottom;"
				+ "	border:.5pt solid windowtext;"
				+ "	mso-background-source:auto;"
				+ "	mso-pattern:auto;"
				+ "	white-space:nowrap;}"
				+ "ruby"
				+ "	{ruby-align:left;}"
				+ "rt"
				+ "	{color:windowtext;"
				+ "	font-size:9.0pt;"
				+ "	font-weight:400;"
				+ "	font-style:normal;"
				+ "	text-decoration:none;"
				+ "	font-family:等线;"
				+ "	mso-generic-font-family:auto;"
				+ "	mso-font-charset:134;"
				+ "	mso-char-type:none;}"
				+ "-->"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div id=\"" + name + "_2360\" align=center x:publishsource=\"Excel\">"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=406 style='border-collapse:"
				+ " collapse;table-layout:fixed;width:305pt'>"
				+ " <col width=252 style='mso-width-source:userset;mso-width-alt:8064;width:189pt'>"
				+ " <col width=76 style='mso-width-source:userset;mso-width-alt:2432;width:57pt'>"
				//+ " <col width=78 style='mso-width-source:userset;mso-width-alt:2496;width:59pt'>"
				+ " <tr height=40 style='mso-height-source:userset;height:30.0pt'>"
				+ "  <td colspan="+ (2 + month.size()) +" height=40 class=xl632360 width=406 style='height:30.0pt;"
				+ "  width:305pt'>" + name + "分派数年度月份对比统计报表</td>"
				+ " </tr>"
				+ " <tr height=20 style='height:15.0pt'>"
				+ "  <td height=20 class=xl652360 style='height:15.0pt;border-top:none'>　</td>");
		
		for (Map map : month) {
			str.append(" <td class=xl652360 style='border-top:none;border-left:none'>"+ map.get("regtime") +"</td>");
		}
				
		str.append("  <td class=xl652360 style='border-top:none;border-left:none'>总计</td></tr>");
		
		for (int i = 0; i < query.size(); i++) {
			str.append(" <tr height=20 style='height:15.0pt'>");
			str.append("  <td height=20 class=xl642360 style='height:15.0pt;border-top:none'>"+ query.get(i).get("name") +"</td>");
			for (Map map : month) {
				str.append(" <td class=xl662360 style='border-top:none;border-left:none'>"+ query.get(i).get(map.get("regtime")) +"</td>");
			}
			str.append("  <td class=xl662360 style='border-top:none;border-left:none'>"+ query.get(i).get("num") +"</td></tr>");
		}
		
		str.append("</table></div></body></html>");
		
		return str.toString();
	}
	@SuppressWarnings("rawtypes")
	public String getExcelFile(Map<String, List<Map>> num,String reportName) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
						"xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n" + 
						"xmlns=\"http://www.w3.org/TR/REC-html40\">\n" + 
						"\n" + 
						"<head>\n" + 
						"<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" + 
						"<meta name=ProgId content=Excel.Sheet>\n" + 
						"<meta name=Generator content=\"Microsoft Excel 15\">\n" + 
						"<link rel=File-List href=\"登记部门.files/filelist.xml\">\n" + 
						"<style id=\"登记部门_14426_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font514426\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1514426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6314426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6414426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:left;\n" + 
						"  vertical-align:bottom;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6514426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:bottom;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6614426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						"ruby\n" + 
						"  {ruby-align:left;}\n" + 
						"rt\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-char-type:none;}\n" + 
						"-->\n" + 
						"</style>\n" + 
						"</head>\n" + 
						"\n" + 
						"<body>\n" + 
						"\n" + 
						"<div id=\"登记部门_14426\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=612 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:459pt'>\n" + 
						" <col width=324 style='mso-width-source:userset;mso-width-alt:10368;width:243pt'>\n" + 
						" <col width=144 span=2 style='mso-width-source:userset;mso-width-alt:4608;\n" + 
						" width:108pt'>\n" + 
						" <tr height=42 style='mso-height-source:userset;height:31.5pt'>");
		List<Map> order = num.get("order");
		List<Map> result = num.get("result");
		sb.append(
				"<td colspan="+(order.size()+2)+" height=42 class=xl6614426 width=612 style='height:31.5pt;\n" +
						"  width:459pt'>"+reportName+"分派数年度月份对比表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6314426 style='height:14.25pt;border-top:none'>"+reportName+"</td>");
		for (int i = 0; i < order.size(); i++) {
			sb.append("<td class=xl6314426 style='border-top:none;border-left:none'>"+order.get(i).get("a")+"</td>");
		}
		
		sb.append("<td class=xl6314426 style='border-top:none;border-left:none'>总计</td>\n" +" </tr>");
		for (int i = 0; i < result.size(); i++) {
			sb.append("<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6414426 style='height:14.25pt;border-top:none'>"+result.get(i).get("regdepname")+"</td>");
			for (int j = 0; j < order.size(); j++) {
				sb.append("<td class=xl6514426 style='border-top:none;border-left:none'>"+result.get(i).get(order.get(j).get("a"))+"</td>");
			}
			sb.append("<td class=xl6514426 style='border-top:none;border-left:none'>"+result.get(i).get("num")+"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}
	public String downSheJiKeTi(Map<String, List<List<Map>>> num) {
		StringBuffer sb=new StringBuffer();
		List<Map> order = num.get("order").get(0);
		List<List<Map>> result=num.get("result");
		sb.append(
				"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
						"xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n" + 
						"xmlns=\"http://www.w3.org/TR/REC-html40\">\n" + 
						"\n" + 
						"<head>\n" + 
						"<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" + 
						"<meta name=ProgId content=Excel.Sheet>\n" + 
						"<meta name=Generator content=\"Microsoft Excel 15\">\n" + 
						"<link rel=File-List href=\"登记部门.files/filelist.xml\">\n" + 
						"<style id=\"登记部门_14426_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font514426\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1514426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6314426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6414426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:left;\n" + 
						"  vertical-align:bottom;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6514426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:bottom;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6614426\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						"ruby\n" + 
						"  {ruby-align:left;}\n" + 
						"rt\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-char-type:none;}\n" + 
						"-->\n" + 
						"</style>\n" + 
						"</head>\n" + 
						"\n" + 
						"<body>\n" + 
						"\n" + 
						"<div id=\"登记部门_14426\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=612 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:459pt'>\n" + 
						" <col width=324 style='mso-width-source:userset;mso-width-alt:10368;width:243pt'>\n" + 
						" <col width=144 span=2 style='mso-width-source:userset;mso-width-alt:4608;\n" + 
						" width:108pt'>\n" + 
						" <tr height=42 style='mso-height-source:userset;height:31.5pt'>");
		sb.append(
				"<td colspan="+(order.size()+2)+" height=42 class=xl6614426 width=612 style='height:31.5pt;\n" +
						"  width:459pt'>涉及客体分派数年度月份对比表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6314426 style='height:14.25pt;border-top:none'>涉及客体</td>");
		for (int i = 0; i < order.size(); i++) {
			sb.append("<td class=xl6314426 style='border-top:none;border-left:none'>"+order.get(i).get("a")+"</td>");
		}
		sb.append("<td class=xl6314426 style='border-top:none;border-left:none'>总计</td>\n" +" </tr>");
		
		for (int i = 0,o=result.size(); i < o; i++) {
			for (int j = 0, k = result.get(i).size(); j < k; j++) {
				sb.append("<tr height=19 style='height:14.25pt'>\n"
						+ "  <td height=19 class=xl6414426 style='height:14.25pt;border-top:none'>"
						+ result.get(i).get(j).get("regdepname") + "</td>");
				for (int f = 0; f < order.size(); f++) {
					sb.append("<td class=xl6514426 style='border-top:none;border-left:none'>"
							+ result.get(i).get(j).get(order.get(f).get("a"))
							+ "</td>");
				}
				sb.append("<td class=xl6514426 style='border-top:none;border-left:none'>"
						+ result.get(i).get(j).get("num") + "</td>");
				sb.append("</tr>");
			}
			if (o!=1&&i!=o-1) {
				sb.append("<td colspan="+(order.size()+2)+" height=42 style='border-right:none;border-left:none' class=xl6614426 width=612 style='height:31.5pt;\n" +
						"  width:459pt'></td>\n");
			}
		}
		
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}
}
