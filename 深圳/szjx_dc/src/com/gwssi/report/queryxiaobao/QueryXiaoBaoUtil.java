package com.gwssi.report.queryxiaobao;

import java.util.List;
import java.util.Map;

public class QueryXiaoBaoUtil {

	@SuppressWarnings("rawtypes")
	public String downDuBan(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
						"xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n" + 
						"xmlns=\"http://www.w3.org/TR/REC-html40\">\n" + 
						"\n" + 
						"<head>\n" + 
						"<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" + 
						"<meta name=ProgId content=Excel.Sheet>\n" + 
						"<meta name=Generator content=\"Microsoft Excel 15\">\n" + 
						"<link rel=File-List href=\"督办统计报表.files/filelist.xml\">\n" + 
						"<style id=\"督办统计报表_13574_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font513574\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl1513574\n" + 
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
						".xl6413574\n" + 
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
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl6513574\n" + 
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
						"\twhite-space:normal;}\n" + 
						".xl6613574\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:16.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl6713574\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:16.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6813574\n" + 
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
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6913574\n" + 
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
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl7013574\n" + 
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
						"\ttext-align:center;\n" + 
						"\tvertical-align:bottom;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						"ruby\n" + 
						"\t{ruby-align:left;}\n" + 
						"rt\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-char-type:none;}\n" + 
						"-->\n" + 
						"</style>\n" + 
						"</head>\n" + 
						"<body>\n" + 
						"<div id=\"督办统计报表_13574\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1050 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:790pt'>\n" + 
						" <col class=xl6513574 width=203 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 6496;width:152pt'>\n" + 
						" <col class=xl6513574 width=197 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 6304;width:148pt'>\n" + 
						" <col width=65 span=10 style='mso-width-source:userset;mso-width-alt:2080;\n" + 
						" width:49pt'>\n" + 
						" <tr height=47 style='mso-height-source:userset;height:35.25pt'>\n" + 
						"  <td colspan=12 height=47 class=xl6613574 width=1050 style='height:35.25pt;\n" + 
						"  width:790pt'>督办（件数）统计报表</td>\n" + 
						" </tr>\n" + 
						" <tr class=xl6413574 height=59 style='mso-height-source:userset;height:44.25pt'>\n" + 
						"  <td height=59 class=xl6913574 width=203 style='height:44.25pt;border-top:\n" + 
						"  none;width:152pt'>被督办处室</td>\n" + 
						"  <td class=xl6913574 width=197 style='border-top:none;border-left:none;\n" + 
						"  width:148pt'>被督办部门</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>被督办总数</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>被督办一次件数</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>被督办两次件数</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>被督办三件级以上件数</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>环节超时（件/次）</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>受理反馈超时（件/次）</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>调查反馈超时（件/次）</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>办结反馈超时（件/次）</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>自动督办</td>\n" + 
						"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
						"  width:49pt'>人工督办</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl7013574 width=203 style='height:14.25pt;border-top:\n" + 
							" none;width:152pt'>"+num.get(i).get("处室")+"</td>\n" + 
							" <td class=xl6913574 width=197 style='border-top:none;border-left:none;\n" + 
							" width:148pt'>"+num.get(i).get("单位")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("总数")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("一次")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("两次")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("三次")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("环节超时")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("受理超时")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("调查超时")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("办结超时")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("自动")+"</td>\n" + 
							" <td class=xl6813574 style='border-top:none;border-left:none'>"+num.get(i).get("人工")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downFenPai(List<Map> num) {
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
						"<link rel=File-List href=\"分派至监管所信息件数据统计.files/filelist.xml\">\n" + 
						"<style id=\"分派至监管所信息件数据统计_14439_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font514439\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".font614439\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl1514439\n" + 
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
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6314439\n" + 
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
						".xl6414439\n" + 
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
						".xl6514439\n" + 
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
						".xl6614439\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6714439\n" + 
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
						"<body>\n" + 
						"<div id=\"分派至监管所信息件数据统计_14439\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1086 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:815pt'>\n" + 
						" <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>\n" + 
						" <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>\n" + 
						" <col width=72 span=10 style='width:54pt'>\n" + 
						" <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>\n" + 
						" <col width=72 style='width:54pt'>\n" + 
						" <tr height=44 style='mso-height-source:userset;height:33.0pt'>\n" + 
						"  <td colspan=14 height=44 class=xl6614439 width=1086 style='height:33.0pt;\n" + 
						"  width:815pt'>分派至监管所信息件数据统计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl6414439 style='height:28.5pt;border-top:none'>辖区局</td>\n" + 
						"  <td rowspan=2 class=xl6414439 style='border-top:none'>监管所</td>\n" + 
						"  <td colspan=2 class=xl6414439 style='border-left:none'>咨询</td>\n" + 
						"  <td colspan=2 class=xl6414439 style='border-left:none'>举报</td>\n" + 
						"  <td colspan=2 class=xl6414439 style='border-left:none'>市场监管投诉</td>\n" + 
						"  <td colspan=2 class=xl6414439 style='border-left:none'>建议</td>\n" + 
						"  <td colspan=2 class=xl6414439 style='border-left:none'>其他</td>\n" + 
						"  <td rowspan=2 class=xl6414439 style='border-top:none'>监管所总计</td>\n" + 
						"  <td rowspan=2 class=xl6514439 width=72 style='border-top:none;width:54pt'>各分局所有信息件总计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6314439 width=72 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none;width:54pt'>汇总</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>办结量</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>汇总</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>办结量</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>汇总</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>办结量</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>汇总</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>办结量</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>汇总</td>\n" + 
						"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>办结量</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6314439 width=99 style='height:14.25pt;border-top:none;\n" + 
							"  width:74pt'>"+num.get(i).get("辖区局")+"</td>\n" + 
							"  <td class=xl6314439 width=109 style='border-top:none;border-left:none;\n" + 
							"  width:82pt'>"+num.get(i).get("监管所")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("咨询汇总")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("咨询办结量")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("举报汇总")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("举报办结量")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("市场监管投诉汇总")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("市场监管投诉办结量")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("建议汇总")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("建议办结量")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("其他汇总")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("其他办结量")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("监管所总计")+"</td>\n" + 
							"  <td class=xl6714439 style='border-top:none;border-left:none'>"+num.get(i).get("分局总计")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downFenJuBanLi(List<Map> num) {
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
						"<link rel=File-List href=\"各分局科所投诉举报登记办理情况.files/filelist.xml\">\n" + 
						"<style id=\"各分局科所投诉举报登记办理情况_326_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font5326\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".font6326\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl15326\n" + 
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
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl63326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
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
						".xl64326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
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
						".xl65326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:none;\n" + 
						"  border-right:none;\n" + 
						"  border-bottom:none;\n" + 
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl66326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border-top:none;\n" + 
						"  border-right:.5pt solid windowtext;\n" + 
						"  border-bottom:none;\n" + 
						"  border-left:none;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl67326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
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
						".xl68326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:windowtext;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
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
						".xl69326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl70326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl71326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl72326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl73326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						"  border-left:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl74326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl75326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl76326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl77326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:11.0pt;\n" + 
						"  font-weight:700;\n" + 
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
						".xl78326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:20.0pt;\n" + 
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
						".xl79326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:20.0pt;\n" + 
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
						".xl80326\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:20.0pt;\n" + 
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
						".xl81326\n" + 
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
						".xl82326\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl83326\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:left;\n" + 
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
						"<div id=\"各分局科所投诉举报登记办理情况_326\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1121 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:841pt'>\n" + 
						" <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>\n" + 
						" <col width=87 style='mso-width-source:userset;mso-width-alt:2784;width:65pt'>\n" + 
						" <col width=72 span=13 style='width:54pt'>\n" + 
						" <tr height=65 style='mso-height-source:userset;height:48.75pt'>\n" + 
						"  <td colspan=15 height=65 class=xl78326 width=1121 style='border-right:.5pt solid black;\n" + 
						"  height:48.75pt;width:841pt'>各分局科所投诉举报登记办理情况</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td colspan=2 rowspan=3 height=57 class=xl63326 width=185 style='border-right:\n" + 
						"  .5pt solid black;border-bottom:.5pt solid black;height:42.75pt;width:139pt'>承办单位</td>\n" + 
						"  <td colspan=4 class=xl69326 style='border-right:.5pt solid black;border-left:\n" + 
						"  none'>登记情况</td>\n" + 
						"  <td colspan=9 class=xl69326 style='border-right:.5pt solid black;border-left:\n" + 
						"  none'>办结情况</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl72326 style='height:14.25pt;border-top:none;border-left:\n" + 
						"  none'>申诉</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>举报</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>总计</td>\n" + 
						"  <td rowspan=2 class=xl73326 width=72 style='border-bottom:.5pt solid black;\n" + 
						"  border-top:none;width:54pt'>登记比例（%）</td>\n" + 
						"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
						"  none'>申诉</td>\n" + 
						"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
						"  none'>举报</td>\n" + 
						"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
						"  none'>总计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl72326 style='height:14.25pt;border-top:none;border-left:\n" + 
						"  none'>登记量</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>登记量</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>登记量</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl81326 width=98 style='height:14.25pt;border-top:none;\n" + 
							" width:74pt'>"+num.get(i).get("处室")+"</td>\n" + 
							" <td class=xl81326 width=87 style='border-top:none;border-left:none;\n" + 
							" width:65pt'>"+num.get(i).get("单位")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("投诉登记")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("举报登记")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("总登记量")+"</td>\n" + 
							" <td class=xl83326 style='border-top:none;border-left:none'>"+num.get(i).get("登记比例")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("投诉分派")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("投诉完成")+"</td>\n" + 
							" <td class=xl83326 style='border-top:none;border-left:none'>"+num.get(i).get("投诉办结率")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("举报分派")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("举报完成")+"</td>\n" + 
							" <td class=xl83326 style='border-top:none;border-left:none'>"+num.get(i).get("举报办结率")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("分派总计")+"</td>\n" + 
							" <td class=xl82326 style='border-top:none;border-left:none'>"+num.get(i).get("完成总计")+"</td>\n" + 
							" <td class=xl83326 style='border-top:none;border-left:none'>"+num.get(i).get("总完成率")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downHuiFang(List<Map> num) {
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
						"<link rel=File-List href=\"回访统计表.files/filelist.xml\">\n" + 
						"<style id=\"回访统计表_4496_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font54496\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl154496\n" + 
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
						".xl644496\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl654496\n" + 
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
						".xl664496\n" + 
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
						".xl674496\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl684496\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
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
						"<div id=\"回访统计表_4496\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=846 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:635pt'>\n" + 
						" <col width=141 span=2 style='mso-width-source:userset;mso-width-alt:4512;\n" + 
						" width:106pt'>\n" + 
						" <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>\n" + 
						" <col width=180 style='mso-width-source:userset;mso-width-alt:5760;width:135pt'>\n" + 
						" <col width=72 span=4 style='width:54pt'>\n" + 
						" <tr height=31 style='height:23.25pt'>\n" + 
						"  <td colspan=8 height=31 class=xl644496 width=846 style='height:23.25pt;\n" + 
						"  width:635pt'>回访统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl654496 style='height:14.25pt;border-top:none'>处室</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>处理部门</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>回访件数</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>占信息类型总量（%）</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>属实</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>不属实</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>无法联系</td>\n" + 
						"  <td class=xl654496 style='border-top:none;border-left:none'>其他</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl664496 width=141 style='height:14.25pt;border-top:none;\n" + 
							"  width:106pt'>"+num.get(i).get("处局")+"</td>\n" + 
							"  <td class=xl664496 width=141 style='border-top:none;border-left:none;\n" + 
							"  width:106pt'>"+num.get(i).get("处理单位")+"</td>\n" + 
							"  <td class=xl674496 style='border-top:none;border-left:none'>"+num.get(i).get("回访数")+"</td>\n" + 
							"  <td class=xl684496 style='border-top:none;border-left:none'>"+num.get(i).get("占比")+"</td>\n" + 
							"  <td class=xl674496 style='border-top:none;border-left:none'>"+num.get(i).get("属实")+"</td>\n" + 
							"  <td class=xl674496 style='border-top:none;border-left:none'>"+num.get(i).get("不属实")+"</td>\n" + 
							"  <td class=xl674496 style='border-top:none;border-left:none'>"+num.get(i).get("无法联系")+"</td>\n" + 
							"  <td class=xl674496 style='border-top:none;border-left:none'>"+num.get(i).get("其他")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downJuBao(List<Map> num) {
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
						"<link rel=File-List href=\"举报件处理情况统计表.files/filelist.xml\">\n" + 
						"<style id=\"举报件处理情况统计表_31449_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font531449\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1531449\n" + 
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
						".xl6331449\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6431449\n" + 
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
						".xl6531449\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6631449\n" + 
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
						".xl6731449\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6831449\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
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
						"<div id=\"举报件处理情况统计表_31449\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=828 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:624pt'>\n" + 
						" <col width=144 span=2 style='mso-width-source:userset;mso-width-alt:4608;\n" + 
						" width:108pt'>\n" + 
						" <col width=90 span=6 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
						" width:68pt'>\n" + 
						" <tr height=49 style='mso-height-source:userset;height:36.75pt'>\n" + 
						"  <td colspan=8 height=49 class=xl6331449 width=828 style='height:36.75pt;\n" + 
						"  width:624pt'>举报件处理情况统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
						"  <td height=40 class=xl6431449 style='height:30.0pt;border-top:none'>上级单位</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>处理部门</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>信息件数</td>\n" + 
						"  <td class=xl6631449 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>去年同期信息件数</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>办结率</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>立案数</td>\n" + 
						"  <td class=xl6431449 style='border-top:none;border-left:none'>立案率</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6531449 width=144 style='height:14.25pt;border-top:\n" + 
							"  none;width:108pt'>"+num.get(i).get("处室")+"</td>\n" + 
							"  <td class=xl6531449 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+num.get(i).get("处理部门")+"</td>\n" + 
							"  <td class=xl6731449 style='border-top:none;border-left:none'>"+num.get(i).get("信息件")+"</td>\n" + 
							"  <td class=xl6831449 style='border-top:none;border-left:none'>"+num.get(i).get("同比")+"</td>\n" + 
							"  <td class=xl6731449 style='border-top:none;border-left:none'>"+num.get(i).get("已办结")+"</td>\n" + 
							"  <td class=xl6831449 style='border-top:none;border-left:none'>"+num.get(i).get("办结率")+"</td>\n" + 
							"  <td class=xl6731449 style='border-top:none;border-left:none'>"+num.get(i).get("立案数")+"</td>\n" + 
							"  <td class=xl6831449 style='border-top:none;border-left:none'>"+num.get(i).get("立案率")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downTouSu(List<Map> num) {
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
						"<link rel=File-List href=\"市场监管投诉件处理情况统计表.files/filelist.xml\">\n" + 
						"<style id=\"市场监管投诉件处理情况统计表_25747_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font525747\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl6325747\n" + 
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
						".xl6425747\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:18.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6525747\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6625747\n" + 
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
						".xl6725747\n" + 
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
						".xl6825747\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6925747\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl7025747\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
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
						"<div id=\"市场监管投诉件处理情况统计表_25747\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=830 class=xl6325747\n" + 
						" style='border-collapse:collapse;table-layout:fixed;width:624pt'>\n" + 
						" <col class=xl6325747 width=150 span=2 style='mso-width-source:userset;\n" + 
						" mso-width-alt:4800;width:113pt'>\n" + 
						" <col class=xl6325747 width=72 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 2304;width:54pt'>\n" + 
						" <col class=xl6325747 width=89 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 2848;width:67pt'>\n" + 
						" <col class=xl6325747 width=72 span=4 style='width:54pt'>\n" + 
						" <col class=xl6325747 width=81 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 2592;width:61pt'>\n" + 
						" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
						"  <td colspan=9 height=40 class=xl6425747 width=830 style='height:30.0pt;\n" + 
						"  width:624pt'>市场监管投诉件处理情况统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
						"  <td height=36 class=xl6625747 style='height:27.0pt;border-top:none'>上级单位</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>处理部门</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>信息件数</td>\n" + 
						"  <td class=xl6725747 width=89 style='border-top:none;border-left:none;\n" + 
						"  width:67pt'>去年同期信息件数</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>办结率</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>调解数</td>\n" + 
						"  <td class=xl6625747 style='border-top:none;border-left:none'>调解成功</td>\n" + 
						"  <td class=xl6725747 width=81 style='border-top:none;border-left:none;\n" + 
						"  width:61pt'>调解成功率</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6825747 width=150 style='height:14.25pt;border-top:\n" + 
							"  none;width:113pt'>"+num.get(i).get("处室")+"</td>\n" + 
							"  <td class=xl6825747 width=150 style='border-top:none;border-left:none;\n" + 
							"  width:113pt'>"+num.get(i).get("处理部门")+"</td>\n" + 
							"  <td class=xl6925747 style='border-top:none;border-left:none'>"+num.get(i).get("信息件")+"</td>\n" + 
							"  <td class=xl7025747 style='border-top:none;border-left:none'>"+num.get(i).get("同比")+"</td>\n" + 
							"  <td class=xl6925747 style='border-top:none;border-left:none'>"+num.get(i).get("已办结")+"</td>\n" + 
							"  <td class=xl7025747 style='border-top:none;border-left:none'>"+num.get(i).get("办结率")+"</td>\n" + 
							"  <td class=xl6925747 style='border-top:none;border-left:none'>"+num.get(i).get("调解数")+"</td>\n" + 
							"  <td class=xl6925747 style='border-top:none;border-left:none'>"+num.get(i).get("调解成功数")+"</td>\n" + 
							"  <td class=xl7025747 style='border-top:none;border-left:none'>"+num.get(i).get("调解成功率")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downZiXun(List<Map> num) {
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
						"<link rel=File-List href=\"咨询件处理情况统计表.files/filelist.xml\">\n" + 
						"<style id=\"咨询件处理情况统计表_22260_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font522260\n" + 
						"\t{color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl6322260\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6422260\n" + 
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
						".xl6522260\n" + 
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
						".xl6622260\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6722260\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6822260\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
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
						"<body>\n" + 
						"<div id=\"咨询件处理情况统计表_22260\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=648 class=xl6422260\n" + 
						" style='border-collapse:collapse;table-layout:fixed;width:486pt'>\n" + 
						" <col class=xl6422260 width=144 span=2 style='mso-width-source:userset;\n" + 
						" mso-width-alt:4608;width:108pt'>\n" + 
						" <col class=xl6422260 width=72 style='width:54pt'>\n" + 
						" <col class=xl6422260 width=144 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 4608;width:108pt'>\n" + 
						" <col class=xl6422260 width=72 span=2 style='width:54pt'>\n" + 
						" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
						"  <td colspan=6 height=40 class=xl6322260 width=648 style='height:30.0pt;\n" + 
						"  width:486pt'>咨询件处理情况统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6522260 style='height:14.25pt;border-top:none'>上级部门</td>\n" + 
						"  <td class=xl6522260 style='border-top:none;border-left:none'>处理部门</td>\n" + 
						"  <td class=xl6522260 style='border-top:none;border-left:none'>信息件数</td>\n" + 
						"  <td class=xl6522260 style='border-top:none;border-left:none'>去年同期信息件数</td>\n" + 
						"  <td class=xl6522260 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl6522260 style='border-top:none;border-left:none'>办结率</td>\n" + 
						" </tr>");
		for (int i = 0; i <num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6622260 width=144 style='height:14.25pt;border-top:\n" + 
							"  none;width:108pt'>"+num.get(i).get("处室")+"</td>\n" + 
							"  <td class=xl6622260 width=144 style='border-top:none;border-left:none;\n" + 
							"  width:108pt'>"+num.get(i).get("处理部门")+"</td>\n" + 
							"  <td class=xl6722260 style='border-top:none;border-left:none'>"+num.get(i).get("信息件")+"</td>\n" + 
							"  <td class=xl6822260 style='border-top:none;border-left:none'>"+num.get(i).get("同比")+"</td>\n" + 
							"  <td class=xl6722260 style='border-top:none;border-left:none'>"+num.get(i).get("已办结")+"</td>\n" + 
							"  <td class=xl6822260 style='border-top:none;border-left:none'>"+num.get(i).get("办结率")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downHuanJie(List<Map> num) {
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
						"<link rel=File-List href=\"环节工作量统计表.files/filelist.xml\">\n" + 
						"<style id=\"环节工作量统计表_15134_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font515134\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl1515134\n" + 
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
						".xl6415134\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:10.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:宋体;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:center;\n" + 
						"\tvertical-align:top;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl6515134\n" + 
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
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6615134\n" + 
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
						"\ttext-align:right;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						".xl6715134\n" + 
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
						"\ttext-align:left;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl6815134\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:windowtext;\n" + 
						"\tfont-size:11.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:宋体;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:general;\n" + 
						"\tvertical-align:top;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl6915134\n" + 
						"\t{padding-top:1px;\n" + 
						"\tpadding-right:1px;\n" + 
						"\tpadding-left:1px;\n" + 
						"\tmso-ignore:padding;\n" + 
						"\tcolor:black;\n" + 
						"\tfont-size:18.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-number-format:General;\n" + 
						"\ttext-align:left;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:nowrap;}\n" + 
						"ruby\n" + 
						"\t{ruby-align:left;}\n" + 
						"rt\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;\n" + 
						"\tmso-char-type:none;}\n" + 
						"-->\n" + 
						"</style>\n" + 
						"</head>\n" + 
						"\n" + 
						"<body>\n" + 
						"<div id=\"环节工作量统计表_15134\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=4747 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:3561pt'>\n" + 
						" <col width=130 span=2 style='mso-width-source:userset;mso-width-alt:4160;\n" + 
						" width:98pt'>\n" + 
						" <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>\n" + 
						" <col width=72 span=61 style='width:54pt'>\n" + 
						" <tr height=71 style='mso-height-source:userset;height:53.25pt'>\n" + 
						"  <td colspan=64 height=71 class=xl6915134 width=4747 style='height:53.25pt;\n" + 
						"  width:3561pt'><span\n" + 
						"  style='mso-spacerun:yes'>                                                                           \n" + 
						"  </span>环节工作量统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl6515134 style='height:28.5pt;border-top:none'>单位名称</td>\n" + 
						"  <td rowspan=2 class=xl6515134 style='border-top:none'>处理人</td>\n" + 
						"  <td colspan=2 class=xl6515134 style='border-left:none'>预登记</td>\n" + 
						"  <td colspan=9 class=xl6415134 dir=LTR width=648 style='border-left:none;\n" + 
						"  width:486pt'>待分派</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>待决策</td>\n" + 
						"  <td colspan=4 class=xl6415134 dir=LTR width=288 style='border-left:none;\n" + 
						"  width:216pt'>待审批</td>\n" + 
						"  <td colspan=5 class=xl6415134 dir=LTR width=360 style='border-left:none;\n" + 
						"  width:270pt'>待受理</td>\n" + 
						"  <td colspan=6 class=xl6415134 dir=LTR width=432 style='border-left:none;\n" + 
						"  width:324pt'>办理中</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>办结确认</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>中止审批</td>\n" + 
						"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
						"  width:108pt'>挂起审批</td>\n" + 
						"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
						"  width:108pt'>回收审批</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>延时审批</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>办结审核</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>不受理审核</td>\n" + 
						"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
						"  width:108pt'>已挂起</td>\n" + 
						"  <td colspan=6 class=xl6415134 dir=LTR width=432 style='border-left:none;\n" + 
						"  width:324pt'>待归档</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>驳回审批</td>\n" + 
						"  <td colspan=2 class=xl6415134 dir=LTR width=144 style='border-left:none;\n" + 
						"  width:108pt'>重办审批</td>\n" + 
						"  <td colspan=3 class=xl6415134 dir=LTR width=216 style='border-left:none;\n" + 
						"  width:162pt'>已归档</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6515134 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none'>提交到分派</td>\n" + 
						"  <td class=xl6515134 style='border-top:none;border-left:none'>直接回复</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>呈报上级</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>回退</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>裁定</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>请求审批</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>消费通回退</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>直接交办</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>逐级分派</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>批示</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>审批</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>直接交办</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不受理</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>回退</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>受理</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请办结</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请挂起</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请结案</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请延时</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请中止</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤销</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>驳回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>归档</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请重办</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>自动归档</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>不同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>撤回</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>同意</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>强制回收</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>消费通回退</td>\n" + 
						"  <td class=xl6415134 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>消费通结案</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6715134 width=130 style='height:14.25pt;border-top:\n" + 
							"  none;width:98pt'>"+num.get(i).get("单位")+"</td>\n" + 
							"  <td class=xl6715134 width=130 style='border-top:none;border-left:none;\n" + 
							"  width:98pt'>"+num.get(i).get("处理人")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("预登记1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("预登记2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派4")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派5")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派6")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派7")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派8")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待分派9")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待决策")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待审批3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待审批4")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待受理1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待受理2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待受理3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待受理4")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待受理5")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中4")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中5")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办理中6")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结确认1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结确认2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结确认3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("中止审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("中止审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("中止审批3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("挂起审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("挂起审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("回收审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("回收审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("延时审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("延时审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("延时审批3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结审核1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结审核2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("办结审核3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("不受理审核1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("不受理审核2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("不受理审核3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("已挂起1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("已挂起2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档4")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档5")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("待归档6")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("驳回审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("驳回审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("驳回审批3")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("重办审批1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("重办审批2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("已归档1")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("已归档2")+"</td>\n" + 
							"  <td class=xl6615134 style='border-top:none;border-left:none'>"+num.get(i).get("已归档3")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downXinXiDengJi(List<Map> num) {
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
						"<link rel=File-List href=\"信息件登记量统计表.files/filelist.xml\">\n" + 
						"<style id=\"信息件登记量统计表_2175_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font52175\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl152175\n" + 
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
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl632175\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl642175\n" + 
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
						".xl652175\n" + 
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
						".xl662175\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl672175\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl682175\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
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
						"<div id=\"信息件登记量统计表_2175\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1104 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:829pt'>\n" + 
						" <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>\n" + 
						" <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>\n" + 
						" <col width=72 span=9 style='width:54pt'>\n" + 
						" <col width=94 style='mso-width-source:userset;mso-width-alt:3008;width:71pt'>\n" + 
						" <col width=72 style='width:54pt'>\n" + 
						" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
						" <tr height=43 style='mso-height-source:userset;height:32.25pt'>\n" + 
						"  <td colspan=14 height=43 class=xl632175 width=1104 style='height:32.25pt;\n" + 
						"  width:829pt'>信息件登记量统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl642175 style='height:28.5pt;border-top:none'>姓名</td>\n" + 
						"  <td rowspan=2 class=xl642175 style='border-top:none'>直接回复数</td>\n" + 
						"  <td rowspan=2 class=xl642175 style='border-top:none'>预登记数</td>\n" + 
						"  <td colspan=6 class=xl642175 style='border-left:none'>正式登记数</td>\n" + 
						"  <td rowspan=2 class=xl642175 style='border-top:none'>登记量</td>\n" + 
						"  <td rowspan=2 class=xl652175 width=72 style='border-top:none;width:54pt'>上一时段数据</td>\n" + 
						"  <td rowspan=2 class=xl652175 width=94 style='border-top:none;width:71pt'>环比增减</td>\n" + 
						"  <td rowspan=2 class=xl652175 width=72 style='border-top:none;width:54pt'>去年同期数据</td>\n" + 
						"  <td rowspan=2 class=xl652175 width=100 style='border-top:none;width:75pt'>同比增减</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl642175 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none'>工商</td>\n" + 
						"  <td class=xl642175 style='border-top:none;border-left:none'>价检</td>\n" + 
						"  <td class=xl642175 style='border-top:none;border-left:none'>质监</td>\n" + 
						"  <td class=xl642175 style='border-top:none;border-left:none'>知识产权</td>\n" + 
						"  <td class=xl642175 style='border-top:none;border-left:none'>消委会</td>\n" + 
						"  <td class=xl642175 style='border-top:none;border-left:none'>合计</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
						" <td height=19 class=xl662175 style='height:14.25pt;border-top:none'>"+num.get(i).get("姓名")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("直接回复数")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("预登记")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("工商")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("价检")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("质监")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("知识")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("消委会")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("合计")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("登记量")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("上一时段")+"</td>\n" + 
						" <td class=xl682175 style='border-top:none;border-left:none'>"+num.get(i).get("环比增减")+"</td>\n" + 
						" <td class=xl672175 style='border-top:none;border-left:none'>"+num.get(i).get("去年同期")+"</td>\n" + 
						" <td class=xl682175 style='border-top:none;border-left:none'>"+num.get(i).get("同比增减")+"</td>\n" + 
						"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downGuiDang(List<Map> num) {
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
						"<link rel=File-List href=\"归档审核统计表.files/filelist.xml\">\n" + 
						"<style id=\"归档审核统计表_3084_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font53084\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl153084\n" + 
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
						".xl633084\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl643084\n" + 
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
						".xl653084\n" + 
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
						".xl663084\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl673084\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl683084\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
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
						"<div id=\"归档审核统计表_3084\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1080 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:816pt'>\n" + 
						" <col width=90 span=12 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
						" width:68pt'>\n" + 
						" <tr height=50 style='mso-height-source:userset;height:37.5pt'>\n" + 
						"  <td colspan=12 height=50 class=xl633084 width=1080 style='height:37.5pt;\n" + 
						"  width:816pt'>归档审核统计报表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=57 class=xl643084 width=90 style='height:42.75pt;\n" + 
						"  border-top:none;width:68pt'>被驳回部门上级单位</td>\n" + 
						"  <td rowspan=2 class=xl653084 style='border-top:none'>被驳回部门</td>\n" + 
						"  <td rowspan=2 class=xl653084 style='border-top:none'>归档审核数</td>\n" + 
						"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>归档审核通过数</td>\n" + 
						"  <td colspan=4 class=xl653084 style='border-left:none'>被驳回数</td>\n" + 
						"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>上一时段数据</td>\n" + 
						"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>环比增减</td>\n" + 
						"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>去年同期数据</td>\n" + 
						"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>同比增减</td>\n" + 
						" </tr>\n" + 
						" <tr height=38 style='height:28.5pt'>\n" + 
						"  <td height=38 class=xl643084 width=90 style='height:28.5pt;border-top:none;\n" + 
						"  border-left:none;width:68pt'>使用法律法则有误</td>\n" + 
						"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>证据不充分</td>\n" + 
						"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>反馈信息不完整</td>\n" + 
						"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
						"  width:68pt'>需转派而未转派</td>\n" + 
						" </tr>");
		for (int i = 0; i <num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl663084 width=90 style='height:14.25pt;border-top:none;\n" + 
							" width:68pt'>"+num.get(i).get("单位")+"</td>\n" + 
							" <td class=xl663084 width=90 style='border-top:none;border-left:none;\n" + 
							" width:68pt'>"+num.get(i).get("部门")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("归档")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("通过数")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("有误")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("证据不充分")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("不完整")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("转派")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("上一时段")+"</td>\n" + 
							" <td class=xl683084 style='border-top:none;border-left:none'>"+num.get(i).get("环比")+"</td>\n" + 
							" <td class=xl673084 style='border-top:none;border-left:none'>"+num.get(i).get("去年")+"</td>\n" + 
							" <td class=xl683084 style='border-top:none;border-left:none'>"+num.get(i).get("同比")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();	
	}

	@SuppressWarnings("rawtypes")
	public String downBeiDuBan(List<Map> num) {
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
						"<link rel=File-List href=\"被督办部门类型统计表.files/filelist.xml\">\n" + 
						"<style id=\"被督办部门类型统计表_6905_Styles\">\n" + 
						"<!--table\n" + 
						"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
						"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font56905\n" + 
						"\t{color:windowtext;\n" + 
						"\tfont-size:9.0pt;\n" + 
						"\tfont-weight:400;\n" + 
						"\tfont-style:normal;\n" + 
						"\ttext-decoration:none;\n" + 
						"\tfont-family:等线;\n" + 
						"\tmso-generic-font-family:auto;\n" + 
						"\tmso-font-charset:134;}\n" + 
						".xl636905\n" + 
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
						".xl646905\n" + 
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
						"\ttext-align:center;\n" + 
						"\tvertical-align:middle;\n" + 
						"\tborder:.5pt solid windowtext;\n" + 
						"\tmso-background-source:auto;\n" + 
						"\tmso-pattern:auto;\n" + 
						"\twhite-space:normal;}\n" + 
						".xl656905\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl666905\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl676905\n" + 
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
						".xl686905\n" + 
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
						".xl696905\n" + 
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
						".xl706905\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl716905\n" + 
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
						"<div id=\"被督办部门类型统计表_6905\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1530 class=xl636905\n" + 
						" style='border-collapse:collapse;table-layout:fixed;width:1148pt'>\n" + 
						" <col class=xl636905 width=90 style='mso-width-source:userset;mso-width-alt:\n" + 
						" 2880;width:68pt'>\n" + 
						" <col class=xl636905 width=72 span=20 style='width:54pt'>\n" + 
						" <tr height=31 style='height:23.25pt'>\n" + 
						"  <td colspan=21 height=31 class=xl706905 width=1530 style='height:23.25pt;\n" + 
						"  width:1148pt'>被督办部门类型统计报表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=57 class=xl646905 width=90 style='height:42.75pt;\n" + 
						"  border-top:none;width:68pt'>被督办部门上级单位</td>\n" + 
						"  <td rowspan=2 class=xl646905 width=72 style='border-top:none;width:54pt'>被督办部门</td>\n" + 
						"  <td rowspan=2 class=xl686905 style='border-top:none'>督办件数</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>受理督办</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>调查督办</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>办结督办</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>分派</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>审批</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>决策</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>申请办结</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>办结确认</td>\n" + 
						"  <td colspan=2 class=xl676905 style='border-left:none'>再次办结</td>\n" + 
						" </tr>\n" + 
						" <tr height=38 style='height:28.5pt'>\n" + 
						"  <td height=38 class=xl646905 width=72 style='height:28.5pt;border-top:none;\n" + 
						"  border-left:none;width:54pt'>督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未督办数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>分派数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未分派数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>审批数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未审批数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>决策数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未决策数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>申请数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未申请数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>确认数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未确认数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>再次办结数</td>\n" + 
						"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
						"  width:54pt'>未再次办结数</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl656905 width=90 style='height:14.25pt;border-top:none;\n" + 
							" width:68pt'>"+num.get(i).get("上级单位")+"</td>\n" + 
							" <td class=xl656905 width=72 style='border-top:none;border-left:none;\n" + 
							" width:54pt'>"+num.get(i).get("单位")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("督办件数")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("受理")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("调查")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("办结")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("分派")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("审批")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("决策")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("申请办结")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("办结确认")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>"+num.get(i).get("再次督办")+"</td>\n" + 
							" <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downZhiJieJieDa(List<Map> num) {
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
						"<link rel=File-List href=\"直接解答数量统计.files/filelist.xml\">\n" + 
						"<style id=\"直接解答数量统计_5068_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font55068\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl155068\n" + 
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
						".xl635068\n" + 
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
						".xl645068\n" + 
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
						".xl655068\n" + 
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
						".xl665068\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:12.0pt;\n" + 
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
						"<div id=\"直接解答数量统计_5068\" align=center x:publishsource=\"Excel\">\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=264 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:198pt'>\n" + 
						" <col width=132 span=2 style='mso-width-source:userset;mso-width-alt:4224;\n" + 
						" width:99pt'>\n" + 
						" <tr height=27 style='height:37pt'>\n" + 
						"  <td colspan=2 height=27 class=xl655068 width=264 style='height:37pt;\n" + 
						"  width:198pt'>直接解答数量统计</td>\n" + 
						" </tr>\n" + 
						" <tr height=21 style='height:15.75pt'>\n" + 
						"  <td height=21 class=xl665068 style='height:15.75pt;border-top:none'>类型</td>\n" + 
						"  <td class=xl665068 style='border-top:none;border-left:none'>数量</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl635068 style='height:14.25pt;border-top:none'>"+num.get(i).get("infotype")+"</td>\n" + 
							" <td class=xl645068 style='border-top:none;border-left:none'>"+num.get(i).get("count")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downZiXunJianYiYeWuFanWei(List<Map> num) {
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
						"<link rel=File-List href=\"咨询建议业务范围分类统计.files/filelist.xml\">\n" + 
						"<style id=\"咨询建议业务范围分类统计_21642_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font521642\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1521642\n" + 
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
						".xl6321642\n" + 
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
						".xl6421642\n" + 
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
						".xl6521642\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6621642\n" + 
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
						"<div id=\"咨询建议业务范围分类统计_21642\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=654 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:490pt'>\n" + 
						" <col width=495 style='mso-width-source:userset;mso-width-alt:15840;width:371pt'>\n" + 
						" <col width=159 style='mso-width-source:userset;mso-width-alt:5088;width:119pt'>\n" + 
						" <tr height=41 style='mso-height-source:userset;height:30.75pt'>\n" + 
						"  <td colspan=2 height=41 class=xl6321642 width=654 style='height:30.75pt;\n" + 
						"  width:490pt'>咨询建议业务范围分类统计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6421642 style='height:14.25pt;border-top:none'>业务范围</td>\n" + 
						"  <td class=xl6421642 style='border-top:none;border-left:none'>登记量</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6521642 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
							" <td class=xl6621642 style='border-top:none;border-left:none'>"+num.get(i).get("sums")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downShenSuJuBaoJiBenWenTi(List<Map> num) {
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
						"<link rel=File-List href=\"申诉举报基本问题分类统计.files/filelist.xml\">\n" + 
						"<style id=\"申诉举报基本问题分类统计_8190_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font58190\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl158190\n" + 
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
						".xl638190\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:700;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl648190\n" + 
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
						".xl658190\n" + 
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
						".xl668190\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl678190\n" + 
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
						"<div id=\"申诉举报基本问题分类统计_8190\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=485 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:364pt'>\n" + 
						" <col width=368 style='mso-width-source:userset;mso-width-alt:11776;width:276pt'>\n" + 
						" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
						" <tr height=37 style='mso-height-source:userset;height:27.75pt'>\n" + 
						"  <td colspan=2 height=37 class=xl638190 width=485 style='height:27.75pt;\n" + 
						"  width:364pt'>申诉举报基本问题分类统计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl658190 style='height:14.25pt;border-top:none'>申诉举报基本问题</td>\n" + 
						"  <td class=xl658190 style='border-top:none;border-left:none'>数量</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append("<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl668190 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
							" <td class=xl678190 style='border-top:none;border-left:none'>"+num.get(i).get("nums")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downShenSuJuBaoSheJiKeTi(List<List<Map>> num) {
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
						"<link rel=File-List href=\"申诉举报涉及客体类型统计.files/filelist.xml\">\n" + 
						"<style id=\"申诉举报涉及客体类型统计_7528_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font57528\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl157528\n" + 
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
						".xl637528\n" + 
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
						".xl647528\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl657528\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl667528\n" + 
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
						"\n" + 
						"<div id=\"申诉举报涉及客体类型统计_7528\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=650 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:488pt'>\n" + 
						" <col width=504 style='mso-width-source:userset;mso-width-alt:16128;width:378pt'>\n" + 
						" <col width=146 style='mso-width-source:userset;mso-width-alt:4672;width:110pt'>\n" + 
						" <tr height=34 style='mso-height-source:userset;height:25.5pt'>\n" + 
						"  <td colspan=2 height=34 class=xl667528 width=650 style='height:25.5pt;\n" + 
						"  width:488pt'>申诉举报涉及客体类型统计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl637528 style='height:14.25pt;border-top:none'>涉及客体类别</td>\n" + 
						"  <td class=xl637528 style='border-top:none;border-left:none'>数量</td>\n" + 
						" </tr>");
		for (int i = 0,t=num.size(); i < t; i++) {
			for (int j = 0; j < num.get(i).size(); j++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
						" <td height=19 class=xl647528 style='height:14.25pt;border-top:none'>"+num.get(i).get(j).get("name")+"</td>\n" + 
						" <td class=xl657528 style='border-top:none;border-left:none'>"+num.get(i).get(j).get("nums")+"</td>\n" + 
						"</tr>");
			}
			if (i+1<t) {
				sb.append("<tr height=19 style='height:14.25pt'>\n" +
						" <td height=19 class=xl647528 style='height:14.25pt;border-top:none;border-left:none;border-right:none'> </td>\n" + 
						" <td class=xl657528 style='border-top:none;border-left:none;border-right:none'> </td>\n" + 
						"</tr>");
			}
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downDengJiXinXiSheJiJinE(List<List<Map>> num) {
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
						"<link rel=File-List href=\"登记信息涉及金额统计表.files/filelist.xml\">\n" + 
						"<style id=\"登记信息涉及金额统计表_22237_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font522237\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1522237\n" + 
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
						".xl6322237\n" + 
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
						".xl6422237\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:normal;}\n" + 
						".xl6522237\n" + 
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
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6622237\n" + 
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
						"<div id=\"登记信息涉及金额统计表_22237\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=793 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:594pt'>\n" + 
						" <col width=363 style='mso-width-source:userset;mso-width-alt:11616;width:272pt'>\n" + 
						" <col width=143 span=2 style='mso-width-source:userset;mso-width-alt:4576;\n" + 
						" width:107pt'>\n" + 
						" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
						" <tr height=47 style='mso-height-source:userset;height:35.25pt'>\n" + 
						"  <td colspan=4 height=47 class=xl6622237 width=793 style='height:35.25pt;\n" + 
						"  width:594pt'>登记信息涉及金额统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6322237 style='height:14.25pt;border-top:none'>涉及客体类型</td>\n" + 
						"  <td class=xl6322237 style='border-top:none;border-left:none'>涉及金额</td>\n" + 
						"  <td class=xl6322237 style='border-top:none;border-left:none'>案值</td>\n" + 
						"  <td class=xl6322237 style='border-top:none;border-left:none'>经济损失</td>\n" + 
						" </tr>");
		for (int i = 0,t=num.size(); i < t; i++) {
			for (int j = 0; j < num.get(i).size(); j++) {
				sb.append("<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6422237 width=363 style='height:14.25pt;border-top:\n" + 
							" none;width:272pt'>"+num.get(i).get(j).get("name")+"</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>"+num.get(i).get(j).get("invoam")+"</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>"+num.get(i).get(j).get("caseval")+"</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>"+num.get(i).get(j).get("ecoloval")+"</td>\n" + 
							"</tr>");
			}
			if (i+1<t) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								" <td height=19 class=xl6422237 width=363 style='height:14.25pt;border-top:\n" + 
								" none;width:272pt;border-left:none;border-right:none'>　</td>\n" + 
								" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"</tr>");
			}
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downFuWuZhanXiaoFeiZhe(List<Map> num) {
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
						"<link rel=File-List href=\"消费者权益服务站统计表.files/filelist.xml\">\n" + 
						"<style id=\"消费者权益服务站统计表_2978_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font52978\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl152978\n" + 
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
						".xl632978\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl642978\n" + 
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
						".xl652978\n" + 
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
						".xl662978\n" + 
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
						"<div id=\"消费者权益服务站统计表_2978\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1081 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:811pt'>\n" + 
						" <col width=289 style='mso-width-source:userset;mso-width-alt:9248;width:217pt'>\n" + 
						" <col width=72 span=11 style='width:54pt'>\n" + 
						" <tr height=50 style='mso-height-source:userset;height:37.5pt'>\n" + 
						"  <td colspan=12 height=50 class=xl632978 width=1081 style='height:37.5pt;\n" + 
						"  width:811pt'>消费者权益服务站统计表<span style='mso-spacerun:yes'> </span></td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td rowspan=2 height=38 class=xl642978 style='height:28.5pt;border-top:none'>服务站类别</td>\n" + 
						"  <td colspan=10 class=xl642978 style='border-left:none'>服务站数量</td>\n" + 
						"  <td rowspan=2 class=xl642978 style='border-top:none'>合计</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl642978 style='height:14.25pt;border-top:none;\n" + 
						"  border-left:none'>福田</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>罗湖</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>南山</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>盐田</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>宝安</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>龙岗</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>光明</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>坪山</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>龙华</td>\n" + 
						"  <td class=xl642978 style='border-top:none;border-left:none'>大鹏</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl652978 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("福田")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("罗湖")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("南山")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("盐田")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("宝安")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("龙岗")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("光明")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("坪山")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("龙华")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("大鹏")+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+num.get(i).get("合计")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String downFuWuZhanXinXiJian(List<Map> num) {
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
						"<link rel=File-List href=\"信息件统计表.files/filelist.xml\">");
		sb.append(
				"<style id=\"信息件统计表_30581_Styles\"><!--table{mso-displayed-decimal-separator:\"\\.\";mso-displayed-thousand-separator:\"\\,\"}.font530581{color:windowtext;font-size:9pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134}.xl6330581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:bottom;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6430581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:18pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6530581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:18pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6630581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6730581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6830581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:right;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:normal}.xl6930581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:\"Short Date\";text-align:right;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl7030581{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:#000;font-size:11pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:right;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}ruby{ruby-align:left}rt{color:windowtext;font-size:9pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-char-type:none}\n" +
						"-->\n" + 
						"</style>");
		sb.append(
					"</head>\n" +
					"\n" + 
					"<body>\n" + 
					"\n" + 
					"<div id=\"信息件统计表_30581\" align=center x:publishsource=\"Excel\">\n" + 
					"\n" + 
					"<table border=0 cellpadding=0 cellspacing=0 width=1029 class=xl6330581\n" + 
					" style='border-collapse:collapse;table-layout:fixed;width:774pt'>\n" + 
					" <col class=xl6330581 width=58 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 1856;width:44pt'>\n" + 
					" <col class=xl6330581 width=80 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 2560;width:60pt'>\n" + 
					" <col class=xl6330581 width=120 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 3840;width:90pt'>\n" + 
					" <col class=xl6330581 width=72 style='width:54pt'>\n" + 
					" <col class=xl6330581 width=90 span=4 style='mso-width-source:userset;\n" + 
					" mso-width-alt:2880;width:68pt'>\n" + 
					" <col class=xl6330581 width=103 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 3296;width:77pt'>\n" + 
					" <col class=xl6330581 width=109 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 3488;width:82pt'>\n" + 
					" <col class=xl6330581 width=127 style='mso-width-source:userset;mso-width-alt:\n" + 
					" 4064;width:95pt'>\n" + 
					" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
					"  <td colspan=11 height=40 class=xl6430581 width=1029 style='height:30.0pt;\n" + 
					"  width:774pt'>服务站-信息件统计表</td>\n" + 
					" </tr>\n" + 
					" <tr height=19 style='height:14.25pt'>\n" + 
					"  <td height=19 class=xl6630581 style='height:14.25pt;border-top:none'>编号</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉人</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉对象</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>申诉内容</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>派发时间</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>接单时间</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>回复时间</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>办结时间</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>服务站经办人</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>是否调解成功</td>\n" + 
					"  <td class=xl6630581 style='border-top:none;border-left:none'>涉及消费金额</td>\n" + 
					" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
					"  <td height=19 class=xl6730581 style='height:14.25pt;border-top:none'>"+num.get(i).get("regino")+"</td>\n" + 
					"  <td class=xl6730581 style='border-top:none;border-left:none'>"+num.get(i).get("persname")+"</td>\n" + 
					"  <td class=xl6830581 width=120 style='border-top:none;border-left:none;\n" + 
					"  width:90pt'>"+num.get(i).get("invname")+"</td>\n" + 
					"  <td class=xl6830581 width=72 style='border-top:none;border-left:none;\n" + 
					"  width:54pt'>"+num.get(i).get("content")+"</td>\n" + 
					"  <td class=xl6930581 style='border-top:none;border-left:none'>"+num.get(i).get("transtime")+"</td>\n" + 
					"  <td class=xl6930581 style='border-top:none;border-left:none'>"+num.get(i).get("receivetime")+"</td>\n" + 
					"  <td class=xl6930581 style='border-top:none;border-left:none'>"+num.get(i).get("replytime")+"</td>\n" + 
					"  <td class=xl6930581 style='border-top:none;border-left:none'>"+num.get(i).get("finishtime")+"</td>\n" + 
					"  <td class=xl7030581 style='border-top:none;border-left:none'>"+num.get(i).get("operator")+"</td>\n" + 
					"  <td class=xl7030581 style='border-top:none;border-left:none'>"+num.get(i).get("success")+"</td>\n" + 
					"  <td class=xl7030581 style='border-top:none;border-left:none'>"+num.get(i).get("amountmoney")+"</td>\n" + 
					" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	public String downGongZuoLianXiJian(List<Map> num) {
		StringBuffer sb=new StringBuffer();
		sb.append(
				"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"xmlns:x=\"urn:schemas-microsoft-com:office:excel\"xmlns=\"http://www.w3.org/TR/REC-html40\"><head><meta http-equiv=Content-Type content=\"text/html; charset=utf-8\"><meta name=ProgId content=Excel.Sheet><meta name=Generator content=\"Microsoft Excel 15\"><link rel=File-List href=\"工作联系件统计表.files/filelist.xml\"><style id=\"工作联系件统计表_14322_Styles\">");
		sb.append(
				"<!--table{mso-displayed-decimal-separator:\"\\.\";mso-displayed-thousand-separator:\"\\,\"}.xl1514322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:11.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:bottom;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6314322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:10.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6414322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6514322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:normal}.xl6614322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6714322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:normal}.xl6814322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:10.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:left;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}.xl6914322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:10.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:bottom;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:normal}.xl7014322{padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:black;font-size:18.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:center;vertical-align:middle;border:.5pt solid windowtext;mso-background-source:auto;mso-pattern:auto;white-space:nowrap}ruby{ruby-align:left}rt{color:windowtext;font-size:9.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:等线;mso-generic-font-family:auto;mso-font-charset:134;mso-char-type:none}-->");
		sb.append(
				"</style></head><body><div id=\"工作联系件统计表_14322\" align=center x:publishsource=\"Excel\"><table border=0 cellpadding=0 cellspacing=0 width=1254 style='border-collapse: collapse;table-layout:fixed;width:943pt'><col width=72 style='width:54pt'><col width=173 style='mso-width-source:userset;mso-width-alt:5536;width:130pt'><col width=445 style='mso-width-source:userset;mso-width-alt:14240;width:334pt'><col width=85 span=2 style='mso-width-source:userset;mso-width-alt:2720; width:64pt'><col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'><col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'><col width=106 style='mso-width-source:userset;mso-width-alt:3392;width:80pt'><col width=126 style='mso-width-source:userset;mso-width-alt:4032;width:95pt'><tr height=51 style='mso-height-source:userset;height:38.25pt'><td colspan=9 height=51 class=xl7014322 width=1254 style='height:38.25pt;  width:943pt'>工作联系件统计表</td></tr><tr height=19 style='height:14.25pt'><td height=19 class=xl6414322 style='height:14.25pt;border-top:none'>编号</td><td class=xl6414322 style='border-top:none;border-left:none'>登记部门</td><td class=xl6514322 width=445 style='border-top:none;border-left:none;  width:334pt'>主要内容</td><td class=xl6614322 style='border-top:none;border-left:none'>转派时间</td><td class=xl6614322 style='border-top:none;border-left:none'>接单时间</td><td class=xl6714322 width=77 style='border-top:none;border-left:none;  width:58pt'>回复时间</td><td class=xl6614322 style='border-top:none;border-left:none'>办结时间</td><td class=xl6614322 style='border-top:none;border-left:none'>服务站经办人</td><td class=xl6614322 style='border-top:none;border-left:none'>监管部门跟踪人</td></tr>");
		for (int i = 0,j=num.size(); i < j; i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6814322 style='height:14.25pt;border-top:none'>"+num.get(i).get("workno")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("登记部门")+"</td>\n" + 
							"  <td class=xl6914322 width=445 style='border-top:none;border-left:none;\n" + 
							"  width:334pt'>"+num.get(i).get("主要内容")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("转发时间")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("接单时间")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("回复时间")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("办结时间")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("服务站经办人")+"</td>\n" + 
							"  <td class=xl6314322 style='border-top:none;border-left:none'>"+num.get(i).get("监管部门跟踪人")+"</td>\n" + 
							" </tr>");
			
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	public String downChuLiQingKuang(List<Map> num) {
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
						"<link rel=File-List href=\"工作联系件处理情况表.files/filelist.xml\">\n" + 
						"<style id=\"工作联系件处理情况表_29107_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font529107\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1529107\n" + 
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
						".xl6329107\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6429107\n" + 
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
						".xl6529107\n" + 
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
						".xl6629107\n" + 
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
						".xl6729107\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:right;\n" + 
						"  vertical-align:bottom;\n" + 
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
						"\n" + 
						"<div id=\"工作联系件处理情况表_29107\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=809 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:608pt'>\n" + 
						" <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>\n" + 
						" <col width=117 span=2 style='mso-width-source:userset;mso-width-alt:3744;\n" + 
						" width:88pt'>\n" + 
						" <col width=72 style='width:54pt'>\n" + 
						" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
						" <col width=72 style='width:54pt'>\n" + 
						" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
						" <col width=72 style='width:54pt'>\n" + 
						" <tr height=31 style='height:23.25pt'>\n" + 
						"  <td colspan=8 height=31 class=xl6329107 width=809 style='height:23.25pt;\n" + 
						"  width:608pt'>工作联系件处理情况表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6429107 style='height:14.25pt;border-top:none'>编号</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>发送对象数量</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>已接收到服务站</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>接收率</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>已回复的服务站</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>回复率</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>已办结的服务站</td>\n" + 
						"  <td class=xl6429107 style='border-top:none;border-left:none'>办结率</td>\n" + 
						" </tr>");
				for (int i = 0,j=num.size(); i <j; i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6529107 style='height:14.25pt;border-top:none'>"+num.get(i).get("编号")+"</td>\n" + 
							"  <td class=xl6629107 style='border-top:none;border-left:none'>"+num.get(i).get("发送数量")+"</td>\n" + 
							"  <td class=xl6629107 style='border-top:none;border-left:none'>"+num.get(i).get("接收数量")+"</td>\n" + 
							"  <td class=xl6729107 style='border-top:none;border-left:none'>"+num.get(i).get("接收率")+"</td>\n" + 
							"  <td class=xl6629107 style='border-top:none;border-left:none'>"+num.get(i).get("回复数量")+"</td>\n" + 
							"  <td class=xl6729107 style='border-top:none;border-left:none'>"+num.get(i).get("回复率")+"</td>\n" + 
							"  <td class=xl6629107 style='border-top:none;border-left:none'>"+num.get(i).get("办结数量")+"</td>\n" + 
							"  <td class=xl6729107 style='border-top:none;border-left:none'>"+num.get(i).get("办结率")+"</td>\n" + 
							" </tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	public String downXinXiJianGongZuoJian(List<Map> num) {
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
						"<link rel=File-List href=\"信息件工作联系件登记处理情况统计表.files/filelist.xml\">\n" + 
						"<style id=\"信息件工作联系件登记处理情况统计表_16211_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font516211\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".font616211\n" + 
						"  {color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:宋体;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1516211\n" + 
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
						".xl6316211\n" + 
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
						".xl6416211\n" + 
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
						".xl6516211\n" + 
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
						".xl6616211\n" + 
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
						"  white-space:normal;}\n" + 
						".xl6716211\n" + 
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
						".xl6816211\n" + 
						"  {padding-top:1px;\n" + 
						"  padding-right:1px;\n" + 
						"  padding-left:1px;\n" + 
						"  mso-ignore:padding;\n" + 
						"  color:black;\n" + 
						"  font-size:16.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:\"Times New Roman\", serif;\n" + 
						"  mso-font-charset:0;\n" + 
						"  mso-number-format:General;\n" + 
						"  text-align:center;\n" + 
						"  vertical-align:middle;\n" + 
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6916211\n" + 
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
						"\n" + 
						"<div id=\"信息件工作联系件登记处理情况统计表_16211\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=1063 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:797pt'>\n" + 
						" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
						" <col width=303 style='mso-width-source:userset;mso-width-alt:9696;width:227pt'>\n" + 
						" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
						" <col width=80 span=7 style='mso-width-source:userset;mso-width-alt:2560;\n" + 
						" width:60pt'>\n" + 
						" <tr height=46 style='mso-height-source:userset;height:34.5pt'>\n" + 
						"  <td colspan=10 height=46 class=xl6816211 width=1063 style='height:34.5pt;\n" + 
						"  width:797pt'><a name=\"RANGE!A1\"><span style='mso-spacerun:yes'> </span><font\n" + 
						"  class=\"font616211\">信息件工作联系件登记处理情况统计表</font></a></td>\n" + 
						" </tr>\n" + 
						" <tr height=38 style='height:28.5pt'>\n" + 
						"  <td height=38 class=xl6316211 style='height:28.5pt;border-top:none'>工单类型</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>登记部门</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>登记数量</td>\n" + 
						"  <td class=xl6416211 width=80 style='border-top:none;border-left:none;\n" + 
						"  width:60pt'>比上年同期增长率</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>已办结</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>办结率</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>成功调解</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>成功调解率</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>未处理</td>\n" + 
						"  <td class=xl6316211 style='border-top:none;border-left:none'>未处理率</td>\n" + 
						" </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6516211 style='height:14.25pt;border-top:none'>"+num.get(i).get("waretype")+"</td>\n" + 
							" <td class=xl6616211 width=303 style='border-top:none;border-left:none;\n" + 
							" width:227pt'>"+num.get(i).get("bumen")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("dengjishu")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("tongzeng")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("banjie")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("banjielv")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("tiaojie")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("tiaojielv")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("weichuli")+"</td>\n" + 
							" <td class=xl6716211 style='border-top:none;border-left:none'>"+num.get(i).get("weichulilv")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	public String downZiXingDengJi(List<Map> num) {
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
						"<link rel=File-List href=\"自行登记消费申诉处理情况统计表.files/filelist.xml\">\n" + 
						"<style id=\"自行登记消费申诉处理情况统计表_28187_Styles\">\n" + 
						"<!--table\n" + 
						"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
						"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
						".font528187\n" + 
						"  {color:windowtext;\n" + 
						"  font-size:9.0pt;\n" + 
						"  font-weight:400;\n" + 
						"  font-style:normal;\n" + 
						"  text-decoration:none;\n" + 
						"  font-family:等线;\n" + 
						"  mso-generic-font-family:auto;\n" + 
						"  mso-font-charset:134;}\n" + 
						".xl1528187\n" + 
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
						".xl6328187\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6428187\n" + 
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
						".xl6528187\n" + 
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
						"  border:.5pt solid windowtext;\n" + 
						"  mso-background-source:auto;\n" + 
						"  mso-pattern:auto;\n" + 
						"  white-space:nowrap;}\n" + 
						".xl6628187\n" + 
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
						"  mso-number-format:\"\\@\";\n" + 
						"  text-align:general;\n" + 
						"  vertical-align:bottom;\n" + 
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
						"\n" + 
						"<div id=\"自行登记消费申诉处理情况统计表_28187\" align=center x:publishsource=\"Excel\">\n" + 
						"\n" + 
						"<table border=0 cellpadding=0 cellspacing=0 width=665 style='border-collapse:\n" + 
						" collapse;table-layout:fixed;width:500pt'>\n" + 
						" <col width=300 style='mso-width-source:userset;mso-width-alt:9600;width:225pt'>\n" + 
						" <col width=85 span=3 style='mso-width-source:userset;mso-width-alt:2720;\n" + 
						" width:64pt'>\n" + 
						" <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>\n" + 
						" <tr height=45 style='mso-height-source:userset;height:33.75pt'>\n" + 
						"  <td colspan=5 height=45 class=xl6328187 width=665 style='height:33.75pt;\n" + 
						"  width:500pt'>自行登记消费申诉处理情况统计表</td>\n" + 
						" </tr>\n" + 
						" <tr height=19 style='height:14.25pt'>\n" + 
						"  <td height=19 class=xl6428187 style='height:14.25pt;border-top:none'>登记部门</td>\n" + 
						"  <td class=xl6428187 style='border-top:none;border-left:none'>数量</td>\n" + 
						"  <td class=xl6428187 style='border-top:none;border-left:none'>和解数</td>\n" + 
						"  <td class=xl6428187 style='border-top:none;border-left:none'>和解率</td>\n" + 
						"  <td class=xl6428187 style='border-top:none;border-left:none'>涉及消费金额</td>\n" + 
						" </tr>");
		for (int i = 0,j=num.size(); i < j; i++) {
			sb.append(
					"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6528187 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
							" <td class=xl6528187 style='border-top:none;border-left:none'>"+num.get(i).get("cnt")+"</td>\n" + 
							" <td class=xl6528187 style='border-top:none;border-left:none'>"+num.get(i).get("success")+"</td>\n" + 
							" <td class=xl6628187 style='border-top:none;border-left:none'>"+num.get(i).get("tiaojielv")+"</td>\n" + 
							" <td class=xl6528187 style='border-top:none;border-left:none'>"+num.get(i).get("jine")+"</td>\n" + 
							"</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

}
