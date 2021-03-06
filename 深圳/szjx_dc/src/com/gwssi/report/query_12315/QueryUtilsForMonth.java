package com.gwssi.report.query_12315;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class QueryUtilsForMonth {

	public String getBenYue(String times) {
		return times.substring(0, 7) + "-01";
	}

	
	
	
	
	public String getBuffer(List<Map> num) throws ArithmeticException {
		StringBuffer sb = new StringBuffer();
		sb.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
				+ "xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\">\n"
				+ "\n"
				+ "<head>\n"
				+ "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n"
				+ "<meta name=ProgId content=Excel.Sheet>\n"
				+ "<meta name=Generator content=\"Microsoft Excel 15\">\n"
				+ "<link rel=File-List href=\"消费者投诉处理情况表.files/filelist.xml\">\n"
				+ "<style id=\"消费者投诉处理情况表_20970_Styles\">\n"
				+ "<!--table\n"
				+ "\t{mso-displayed-decimal-separator:\"\\.\";\n"
				+ "\tmso-displayed-thousand-separator:\"\\,\";}\n"
				+ ".xl1520970\n"
				+ "\t{padding-top:1px;\n"
				+ "\tpadding-right:1px;\n"
				+ "\tpadding-left:1px;\n"
				+ "\tmso-ignore:padding;\n"
				+ "\tcolor:black;\n"
				+ "\tfont-size:11.0pt;\n"
				+ "\tfont-weight:400;\n"
				+ "\tfont-style:normal;\n"
				+ "\ttext-decoration:none;\n"
				+ "\tfont-family:等线;\n"
				+ "\tmso-generic-font-family:auto;\n"
				+ "\tmso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:general;\n"
				+ "  vertical-align:bottom;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6320970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:10.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:宋体;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6420970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:11.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:normal;}\n"
				+ ".xl6520970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:10.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6620970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:10.0pt;\n"
				+ "  font-weight:700;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6720970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:10.5pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:right;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:normal;}\n"
				+ ".xl6820970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:10.5pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:\"\\@\";\n"
				+ "  text-align:right;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:normal;}\n"
				+ ".xl6920970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:16.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7020970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:11.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7120970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:12.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7220970\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:12.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  background:white;\n"
				+ "  mso-pattern:black none;\n"
				+ "  white-space:normal;}\n"
				+ "ruby\n"
				+ "  {ruby-align:left;}\n"
				+ "rt\n"
				+ "  {color:windowtext;\n"
				+ "  font-size:9.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-char-type:none;}\n"
				+ "-->\n"
				+ "</style>\n"
				+ "</head>\n"
				+ "\n"
				+ "<body>\n"
				+ "<div id=\"消费者投诉处理情况表_20970\" align=center x:publishsource=\"Excel\">\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=903 style='border-collapse:\n"
				+ " collapse;table-layout:fixed;width:678pt'>\n"
				+ " <col width=211 style='mso-width-source:userset;mso-width-alt:6752;width:158pt'>\n"
				+ " <col width=72 style='width:54pt'>\n"
				+ " <col width=100 span=4 style='mso-width-source:userset;mso-width-alt:3200;\n"
				+ " width:75pt'>\n"
				+ " <col width=110 span=2 style='mso-width-source:userset;mso-width-alt:3520;\n"
				+ " width:83pt'>\n"
				+ " <tr height=37 style='mso-height-source:userset;height:27.75pt'>\n"
				+ "  <td colspan=8 height=37 class=xl6920970 width=903 style='height:27.75pt;\n"
				+ "  width:678pt'>　　消费者投诉处理情况表</td>\n"
				+ " </tr>\n"
				+ " <tr height=28 style='mso-height-source:userset;height:21.0pt'>\n"
				+ "  <td rowspan=2 height=48 class=xl7020970 style='height:36.0pt;border-top:none'>消费者投诉处理情况</td>\n"
				+ "  <td rowspan=2 class=xl7120970 style='border-top:none'>单位</td>\n"
				+ "  <td colspan=2 class=xl7220970 width=200 style='border-left:none;width:150pt'>本年情况</td>\n"
				+ "  <td colspan=2 class=xl7220970 width=200 style='border-left:none;width:150pt'>上年情况</td>\n"
				+ "  <td rowspan=2 class=xl6420970 width=110 style='border-top:none;width:83pt'>本年累计比上年同期增减</td>\n"
				+ "  <td rowspan=2 class=xl6420970 width=110 style='border-top:none;width:83pt'>本年累计比上年同期增减%</td>\n"
				+ " </tr>\n"
				+ " <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n"
				+ "  <td height=20 class=xl6420970 width=100 style='height:15.0pt;border-top:none;\n"
				+ "  border-left:none;width:75pt'>本月</td>\n"
				+ "  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n"
				+ "  width:75pt'>1-本月累计</td>\n"
				+ "  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n"
				+ "  width:75pt'>本月</td>\n"
				+ "  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n"
				+ "  width:75pt'>1-本月累计</td>\n" + " </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append("<tr height=19 style='height:14.25pt'>\n"
					+ " <td height=19 class=xl6520970 style='height:14.25pt;border-top:none'>"
					+ num.get(i).get("name")
					+ "</td>\n"
					+ " <td class=xl6620970 style='border-top:none;border-left:none'>"
					+ num.get(i).get("unit")
					+ "</td>\n"
					+ " <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n"
					+ " width:75pt'>"
					+ num.get(i).get("now")
					+ "</td>\n"
					+ " <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n"
					+ " width:75pt'>"
					+ num.get(i).get("nowsum")
					+ "</td>\n"
					+ " <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n"
					+ " width:75pt'>"
					+ num.get(i).get("upyear")
					+ "</td>\n"
					+ " <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n"
					+ " width:75pt'>"
					+ num.get(i).get("upyearSum")
					+ "</td>\n"
					+ " <td class=xl6720970 width=110 style='border-top:none;border-left:none;\n"
					+ " width:83pt'>"
					+ num.get(i).get("zjshu")
					+ "</td>\n"
					+ " <td class=xl6820970 width=110 style='border-top:none;border-left:none;\n"
					+ " width:83pt'>"
					+ num.get(i).get("zjlv")
					+ "</td>\n"
					+ "</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}


}