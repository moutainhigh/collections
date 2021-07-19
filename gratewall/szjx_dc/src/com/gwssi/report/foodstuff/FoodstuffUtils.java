package com.gwssi.report.foodstuff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gwssi.report.service.QueryFoodStuffServer;

public class FoodstuffUtils {
	@Autowired
	private QueryFoodStuffServer query;
	private String[] regs = new String[] { "食药准入处","罗湖局", "福田局", "南山局", "宝安局", "龙岗局",
			"盐田局", "光明局", "坪山局", "龙华局", "大鹏局" };

	public String downExcelShiPinCanYinXuKe(List<Map> num) {
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
						"<link rel=File-List href=\"表2.files/filelist.xml\">\n" + 
						"<style id=\"表2_31564_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font531564\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl1531564\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:11.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:general;\n" + 
						"\tvertical-align:bottom;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6331564\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:10.5pt;\n" + 
						"\tfont-weight:700;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:仿宋_GB2312, serif;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6431564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6531564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid black;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6631564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid black;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6731564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid black;\n" + 
						"  border-right:.5pt solid black;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6831564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6931564\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
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
						"<div id=\"表2_31564\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1171 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:883pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:68pt'>\n" + 
						" <col width=74 span=3 style='mso-width-source:userset;mso-width-alt:2368;\n" + 
						" width:56pt'>\n" + 
						" <col width=73 span=4 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
						" width:55pt'>\n" + 
						" <col width=130 style='mso-width-source:userset;mso-width-alt:4160;width:98pt'>\n" + 
						" <col width=74 style='mso-width-source:userset;mso-width-alt:2368;width:56pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <tr height=38 style='mso-height-source:userset;height:28.5pt'>\n" + 
						"  <td colspan=14 height=38 class=xl6531564 width=1171 style='border-right:.5pt solid black;\n" + 
						"  height:28.5pt;width:883pt'>各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl6331564 width=73 style='height:28.5pt;\n" + 
						"  border-top:none;width:55pt'>辖区</td>\n" + 
						"  <td colspan=11 class=xl6331564 width=881 style='border-left:none;width:665pt'>《餐饮服务许可证》</td>\n" + 
						"  <td rowspan=2 class=xl6331564 width=144 style='border-top:none;width:108pt'>《食品流通许可证》</td>\n" + 
						"  <td rowspan=2 class=xl6331564 width=73 style='border-top:none;width:55pt'>总计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6331564 width=90 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none;width:68pt'>特大型餐饮</td>\n" + 
						"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>大型餐饮</td>\n" + 
						"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>中型餐饮</td>\n" + 
						"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>小型餐饮</td>\n" + 
						"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>快餐店</td>\n" + 
						"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>小吃店</td>\n" + 
						"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>饮品店</td>\n" + 
						"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>食堂</td>\n" + 
						"  <td class=xl6331564 width=130 style='border-top:none;border-left:none;\n" + 
						"  width:98pt'>集体用餐配送单位</td>\n" + 
						"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>中央厨房</td>\n" + 
						"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>合计</td>\n" + 
						" </tr>");
		for (int i = 0,ii=num.size(); i <ii; i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6431564 width=73 style='height:14.25pt;border-top:none;\n" + 
							" width:55pt'>"+num.get(i).get("辖区")+"</td>\n" + 
							" <td class=xl6831564 width=90 style='border-top:none;border-left:none;\n" + 
							" width:68pt'>"+num.get(i).get("特大型餐饮")+"</td>\n" + 
							" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
							" width:56pt'>"+num.get(i).get("大型餐饮")+"</td>\n" + 
							" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
							" width:56pt'>"+num.get(i).get("中型餐饮")+"</td>\n" + 
							" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
							" width:56pt'>"+num.get(i).get("小型餐饮")+"</td>\n" + 
							" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("快餐店")+"</td>\n" + 
							" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("小吃店")+"</td>\n" + 
							" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("饮品店")+"</td>\n" + 
							" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("食堂")+"</td>\n" + 
							" <td class=xl6931564 width=130 style='border-top:none;border-left:none;\n" + 
							" width:98pt'>"+num.get(i).get("集体用餐配送单位")+"</td>\n" + 
							" <td class=xl6931564 width=74 style='border-top:none;border-left:none;\n" + 
							" width:56pt'>"+num.get(i).get("中央厨房")+"</td>\n" + 
							" <td class=xl6931564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("合计")+"</td>\n" + 
							" <td class=xl6931564 width=144 style='border-top:none;border-left:none;\n" + 
							" width:108pt'>"+num.get(i).get("食品流通")+"</td>\n" + 
							" <td class=xl6931564 width=73 style='border-top:none;border-left:none;\n" + 
							" width:55pt'>"+num.get(i).get("总计")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	public String queryShiPinXuKe(List<Map> num) {
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
						"<link rel=File-List href=\"各辖区持有效《食品经营许可证》主体分类统计表.files/filelist.xml\">\n" + 
						"<style id=\"各辖区持有效《食品经营许可证》主体分类统计表_1266_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font51266\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl151266\n" + 
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
						".xl631266\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl641266\n" + 
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
						"  border-top:.5pt solid black;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl651266\n" + 
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
						"  border-top:.5pt solid black;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl661266\n" + 
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
						"  border-top:.5pt solid black;\n" + 
						"  border-right:.5pt solid black;\n" + 
						"  border-bottom:.5pt solid black;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl671266\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl681266\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:10.5pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid black;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
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
						"<div id=\"各辖区持有效《食品经营许可证》主体分类统计表_1266\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1666 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:1255pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <col width=74 style='mso-width-source:userset;mso-width-alt:2368;width:56pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <col width=90 span=2 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
						" width:68pt'>\n" + 
						" <col width=73 span=2 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
						" width:55pt'>\n" + 
						" <col width=74 span=4 style='mso-width-source:userset;mso-width-alt:2368;\n" + 
						" width:56pt'>\n" + 
						" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
						" <col width=140 style='mso-width-source:userset;mso-width-alt:4480;width:105pt'>\n" + 
						" <col width=73 span=2 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
						" width:55pt'>\n" + 
						" <col width=123 span=2 style='mso-width-source:userset;mso-width-alt:3936;\n" + 
						" width:92pt'>\n" + 
						" <col width=73 span=3 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
						" width:55pt'>\n" + 
						" <tr height=27 style='height:20.25pt'>\n" + 
						"  <td colspan=20 height=27 class=xl641266 width=1666 style='border-right:.5pt solid black;\n" + 
						"  height:20.25pt;width:1255pt'>各辖区持有效《食品经营许可证》主体分类统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl631266 width=73 style='height:28.5pt;\n" + 
						"  border-top:none;width:55pt'>辖区</td>\n" + 
						"  <td colspan=6 class=xl631266 width=473 style='border-left:none;width:357pt'>食品销售经营者</td>\n" + 
						"  <td colspan=8 class=xl631266 width=655 style='border-left:none;width:494pt'>餐饮服务经营者</td>\n" + 
						"  <td colspan=4 class=xl631266 width=392 style='border-left:none;width:294pt'>单位食堂</td>\n" + 
						"  <td rowspan=2 class=xl631266 width=73 style='border-top:none;width:55pt'>总计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl631266 width=74 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none;width:56pt'>商场超市</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>便利店</td>\n" + 
						"  <td class=xl631266 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>食品贸易商</td>\n" + 
						"  <td class=xl631266 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>酒类批发商</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>其它</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>合计</td>\n" + 
						"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>大型餐馆</td>\n" + 
						"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>中型餐馆</td>\n" + 
						"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>小型餐馆</td>\n" + 
						"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
						"  width:56pt'>微小餐饮</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>饮品店</td>\n" + 
						"  <td class=xl631266 width=140 style='border-top:none;border-left:none;\n" + 
						"  width:105pt'>中央厨房集体配送</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>其它</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>合计</td>\n" + 
						"  <td class=xl631266 width=123 style='border-top:none;border-left:none;\n" + 
						"  width:92pt'>学校幼儿园食堂</td>\n" + 
						"  <td class=xl631266 width=123 style='border-top:none;border-left:none;\n" + 
						"  width:92pt'>机关企事业单位</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>其它</td>\n" + 
						"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
						"  width:55pt'>合计</td>\n" + 
						" </tr>");
		for (int i = 0,ii=num.size(); i < ii; i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl631266 width=73 style='height:14.25pt;border-top:none;\n" + 
							"  width:55pt'>"+num.get(i).get("辖区")+"</td>\n" + 
							"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
							"  width:56pt'>"+num.get(i).get("食品商场超市")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("食品便利店")+"</td>\n" + 
							"  <td class=xl671266 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>"+num.get(i).get("食品贸易商")+"</td>\n" + 
							"  <td class=xl671266 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>"+num.get(i).get("酒类批发商")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("食品其他")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("食品合计")+"</td>\n" + 
							"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
							"  width:56pt'>"+num.get(i).get("大型餐馆")+"</td>\n" + 
							"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
							"  width:56pt'>"+num.get(i).get("中型餐馆")+"</td>\n" + 
							"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
							"  width:56pt'>"+num.get(i).get("小型餐馆")+"</td>\n" + 
							"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
							"  width:56pt'>"+num.get(i).get("微小餐饮")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("饮品店")+"</td>\n" + 
							"  <td class=xl671266 width=140 style='border-top:none;border-left:none;\n" + 
							"  width:105pt'>"+num.get(i).get("中央")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("餐饮其他")+"</td>\n" + 
							"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("餐饮合计")+"</td>\n" + 
							"  <td class=xl681266 width=123 style='border-top:none;border-left:none;\n" + 
							"  width:92pt'>"+num.get(i).get("学校食堂")+"</td>\n" + 
							"  <td class=xl681266 width=123 style='border-top:none;border-left:none;\n" + 
							"  width:92pt'>"+num.get(i).get("机关食堂")+"</td>\n" + 
							"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("其他单位食堂")+"</td>\n" + 
							"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("食堂合计")+"</td>\n" + 
							"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
							"  width:55pt'>"+num.get(i).get("总计")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downExcelShiPinZhunRu(Map<String,Map> num,String beginTime) {
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
						"<link rel=File-List href=\"食药准入.files/filelist.xml\">\n" + 
						"<style id=\"食药准入_30409_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font530409\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1530409\n" + 
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
						".xl6330409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:14.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6430409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:黑体, monospace;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6530409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6630409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6730409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:none;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6830409\n" + 
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
						"  border-top:none;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6930409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:黑体, monospace;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:none;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl7030409\n" + 
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
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:none;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl7130409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:仿宋_GB2312, serif;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:none;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl7230409\n" + 
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
						"  border-top:none;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl7330409\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl7430409\n" + 
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
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl7530409\n" + 
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
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl7630409\n" + 
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
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:none;\n" + 
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
						"<div id=\"食药准入_30409\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=601 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:451pt'>\n" + 
						" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
						" <col width=100 span=5 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
						" width:75pt'>\n" + 
						" <tr height=27 style='height:20.25pt'>\n" + 
						"  <td colspan=6 height=27 class=xl7430409 width=601 style='border-right:.5pt solid black;\n" + 
						"  height:20.25pt;width:451pt'>食药准入业务工作完成情况报表（食品）</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td colspan=2 rowspan=4 height=76 class=xl6330409 width=201 style='height:\n" + 
						"  57.0pt;width:151pt'>单位</td>\n" + 
						"  <td colspan=2 rowspan=2 class=xl6430409 width=200 style='width:150pt'>食品生产许可</td>\n" + 
						"  <td colspan=2 class=xl6930409 width=200 style='border-right:.5pt solid black;\n" + 
						"  border-left:none;width:150pt'>食品经营许可</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td colspan=2 height=19 class=xl7130409 width=200 style='border-right:.5pt solid black;\n" + 
						"  height:14.25pt;border-left:none;width:150pt'>（不含原餐饮许可数据）</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl6730409 width=100 style='border-bottom:.5pt solid black;\n" + 
						"  height:28.5pt;border-top:none;width:75pt'>受理数(件)</td>\n" + 
						"  <td rowspan=2 class=xl6730409 width=100 style='border-bottom:.5pt solid black;\n" + 
						"  border-top:none;width:75pt'>办理数(件)</td>\n" + 
						"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
						"  width:75pt'>受理数</td>\n" + 
						"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
						"  width:75pt'>办理数</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6530409 width=100 style='height:14.25pt;border-top:\n" + 
						"  none;border-left:none;width:75pt'>(件)</td>\n" + 
						"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
						"  width:75pt'>(件)</td>\n" + 
						" </tr>");
		for (int i = 0; i < regs.length; i++) {
			sb.append(
"<tr height=19 style='height:14.25pt'>\n" +
"  <td rowspan=2 height=38 class=xl6630409 width=101 style='height:28.5pt;\n" + 
"  border-top:none;width:76pt'>"+regs[i]+"</td>\n" + 
"  <td class=xl6430409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>当月数</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"月").get("食品生产受理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"月").get("食品生产办理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"月").get("食品经营受理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"月").get("食品经营办理")+"</td>\n" + 
" </tr>\n" + 
" <tr height=19 style='height:14.25pt'>\n" + 
"  <td height=19 class=xl6430409 width=100 style='height:14.25pt;border-top:\n" + 
"  none;border-left:none;width:75pt'>本年度累计</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"年").get("食品生产受理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"年").get("食品生产办理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"年").get("食品经营受理")+"</td>\n" + 
"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
"  width:75pt'>"+num.get(regs[i]+"年").get("食品经营办理")+"</td>\n" + 
" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downExcelShiPinShenCha(List<Map> num, String beginTime) {
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
						"<link rel=File-List href=\"食品审查1表.files/filelist.xml\">\n" + 
						"<style id=\"食品审查1表_24322_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font524322\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1524322\n" + 
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
						".xl6324322\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:18.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6424322\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:18.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6524322\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:18.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:.5pt solid windowtext;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:.5pt solid windowtext;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6624322\n" + 
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
						"  white-space:normal;}\n" + 
						".xl6724322\n" + 
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
						"  white-space:normal;}\n" + 
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
						"<div id=\"食品审查1表_24322\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=888 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:667pt'>\n" + 
						" <col width=122 style='mso-width-source:userset;mso-width-alt:3904;width:92pt'>\n" + 
						" <col width=72 span=3 style='width:54pt'>\n" + 
						" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
						" <col width=118 style='mso-width-source:userset;mso-width-alt:3776;width:89pt'>\n" + 
						" <col width=72 span=4 style='width:54pt'>\n" + 
						" <tr height=31 style='height:23.25pt'>\n" + 
						"  <td colspan=10 height=31 class=xl6324322 width=888 style='border-right:.5pt solid black;\n" + 
						"  height:23.25pt;width:667pt'>食品审查中心审查状况表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=3 height=76 class=xl6624322 width=122 style='height:57.0pt;\n" + 
						"  border-top:none;width:92pt'>办理部门</td>\n" + 
						"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>首次审查工作时效</td>\n" + 
						"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>复验审查工作时效</td>\n" + 
						"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>照片上传工作时效</td>\n" + 
						"  <td rowspan=3 class=xl6724322 width=144 style='border-top:none;width:108pt'>整改率（首次审查未通过家数/审查任务总家数）</td>\n" + 
						"  <td rowspan=3 class=xl6724322 width=118 style='border-top:none;width:89pt'>通过率（审查通过家数/审查任务总家数）</td>\n" + 
						"  <td colspan=4 class=xl6724322 width=288 style='border-left:none;width:216pt'>主体业态</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td colspan=3 height=19 class=xl6724322 width=216 style='height:14.25pt;\n" + 
						"  border-left:none;width:162pt'>餐饮服务经营者</td>\n" + 
						"  <td rowspan=2 class=xl6724322 width=72 style='border-top:none;width:54pt'>食品销售经营者</td>\n" + 
						" </tr>\n" + 
						" <tr height=38 style='height:28.5pt'>\n" + 
						"  <td height=38 class=xl6724322 width=72 style='height:28.5pt;border-top:none;\n" + 
						"  border-left:none;width:54pt'>大型</td>\n" + 
						"  <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>中型</td>\n" + 
						"  <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>饮品店、糕点店</td>\n" + 
						" </tr>");
		for (int i = 0,j=num.size(); i < j; i++) {
			sb.append(
"<tr height=19 style='height:14.25pt'>\n" +
" <td height=19 class=xl6724322 width=122 style='height:14.25pt;border-top:\n" + 
" none;width:92pt'>"+num.get(i).get("部门")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("首次审查工作时效")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("复验审查工作时效")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("照片上传工作时效")+"</td>\n" + 
" <td class=xl6724322 width=144 style='border-top:none;border-left:none;\n" + 
" width:108pt'>"+num.get(i).get("整改率")+"</td>\n" + 
" <td class=xl6724322 width=118 style='border-top:none;border-left:none;\n" + 
" width:89pt'>"+num.get(i).get("通过率")+"%"+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("大型")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("中型")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("饮品店糕点店")+"</td>\n" + 
" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
" width:54pt'>"+num.get(i).get("食品销售")+"</td>\n" + 
"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}
	
}
