package com.gwssi.report.shangshi;

import java.util.List;
import java.util.Map;

public class DengjiBanJieUtil {

	public String dengjiBanJieInfoDown(Map<String, Map> num) {
		String[] strs = new String[] { "宝安", "南山", "光明", "福田", "罗湖", "龙岗",
				"坪山", "龙华", "盐田", "大鹏", "前海", "市民中心" };
		StringBuffer sb = new StringBuffer();
		sb.append(

		"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
				+ "xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\">\n"
				+ "\n"
				+ "<head>\n"
				+ "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n"
				+ "<meta name=ProgId content=Excel.Sheet>\n"
				+ "<meta name=Generator content=\"Microsoft Excel 15\">\n"
				+ "<link rel=File-List href=\"全系统全流程网上商事登记业务办理情况表.files/filelist.xml\">\n"
				+ "<style id=\"全系统全流程网上商事登记业务办理情况表_22517_Styles\">\n"
				+ "<!--table\n"
				+ "\t{mso-displayed-decimal-separator:\"\\.\";\n"
				+ "\tmso-displayed-thousand-separator:\"\\,\";}\n"
				+ ".font522517\n"
				+ "\t{color:windowtext;\n"
				+ "\tfont-size:9.0pt;\n"
				+ "\tfont-weight:400;\n"
				+ "\tfont-style:normal;\n"
				+ "\ttext-decoration:none;\n"
				+ "\tfont-family:宋体;\n"
				+ "\tmso-generic-font-family:auto;\n"
				+ "\tmso-font-charset:134;}\n"
				+ ".xl1522517\n"
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
				+ "\tmso-number-format:General;\n"
				+ "\ttext-align:general;\n"
				+ "\tvertical-align:bottom;\n"
				+ "\tmso-background-source:auto;\n"
				+ "\tmso-pattern:auto;\n"
				+ "\twhite-space:nowrap;}\n"
				+ ".xl6322517\n"
				+ "\t{padding-top:1px;\n"
				+ "\tpadding-right:1px;\n"
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
				+ "  border-top:none;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:.5pt solid windowtext;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6422517\n"
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
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:normal;}\n"
				+ ".xl6522517\n"
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
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6622517\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:11.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:宋体;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6722517\n"
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
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6822517\n"
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
				+ "  border-top:none;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6922517\n"
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
				+ "  mso-number-format:Percent;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7022517\n"
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
				+ "  mso-number-format:Percent;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:none;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7122517\n"
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
				+ "  mso-number-format:Percent;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:none;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:.5pt solid windowtext;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7222517\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:16.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:华文中宋;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:none;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7322517\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:16.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:华文中宋;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:none;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:none;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7422517\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:none;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:none;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7522517\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:.5pt solid windowtext;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:none;\n"
				+ "  border-left:none;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7622517\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:none;\n"
				+ "  border-right:none;\n"
				+ "  border-bottom:.5pt solid windowtext;\n"
				+ "  border-left:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7722517\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:none;\n"
				+ "  border-right:none;\n"
				+ "  border-bottom:.5pt solid windowtext;\n"
				+ "  border-left:none;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl7822517\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:middle;\n"
				+ "  border-top:none;\n"
				+ "  border-right:.5pt solid windowtext;\n"
				+ "  border-bottom:.5pt solid windowtext;\n"
				+ "  border-left:none;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
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
				+ "\n"
				+ "\n"
				+ "<div id=\"全系统全流程网上商事登记业务办理情况表_22517\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=717 style='border-collapse:\n"
				+ " collapse;table-layout:fixed;width:538pt'>\n"
				+ " <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>\n"
				+ " <col width=191 style='mso-width-source:userset;mso-width-alt:6112;width:143pt'>\n"
				+ " <col width=206 style='mso-width-source:userset;mso-width-alt:6592;width:155pt'>\n"
				+ " <col width=132 style='mso-width-source:userset;mso-width-alt:4224;width:99pt'>\n"
				+ " <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>\n"
				+ " <tr height=19 style='height:14.25pt'>\n"
				+ "  <td colspan=5 rowspan=2 height=54 class=xl7222517 width=717 style='border-right:\n"
				+ "  .5pt solid black;border-bottom:.5pt solid black;height:40.5pt;width:538pt'><span\n"
				+ "  style='mso-spacerun:yes'>  </span>全系统全流程网上商事登记业务办理情况表</td>\n"
				+ " </tr>\n"
				+ " <tr height=35 style='mso-height-source:userset;height:26.25pt'>\n"
				+ " </tr>\n"
				+ " <tr height=38 style='height:28.5pt'>\n"
				+ "  <td height=38 class=xl6322517 style='height:28.5pt'>单位</td>\n"
				+ "  <td class=xl6322517 style='border-left:none'>项目</td>\n"
				+ "  <td class=xl6322517 style='border-left:none'>数据</td>\n"
				+ "  <td class=xl6422517 width=132 style='border-top:none;border-left:none;\n"
				+ "  width:99pt'>未办、待签收、签收通过未核准小计</td>\n"
				+ "  <td class=xl6422517 width=97 style='border-top:none;border-left:none;\n"
				+ "  width:73pt'>未办结/已办（%）</td>\n" + " </tr>");
		for (int i = 0; i < strs.length; i++) {
			sb.append("<tr height=19 style='height:14.25pt'>\n"
					+ "  <td rowspan=4 height=76 class=xl6722517 style='border-bottom:.5pt solid black;\n"
					+ "  height:57.0pt;border-top:none'>"
					+ strs[i]
					+ "</td>\n"
					+ "  <td class=xl6522517 style='border-top:none;border-left:none'>总数</td>\n"
					+ "  <td class=xl6522517 style='border-top:none;border-left:none'>"
					+ num.get(strs[i]).get("总计")
					+ "</td>\n"
					+ "  <td rowspan=4 class=xl6722517 style='border-bottom:.5pt solid black;\n"
					+ "  border-top:none'>"
					+ num.get(strs[i]).get("小计")
					+ "</td>\n"
					+ "  <td rowspan=4 class=xl6922517 style='border-bottom:.5pt solid black;\n"
					+ "  border-top:none'>"
					+ num.get(strs[i]).get("办结率")
					+ "</td>\n"
					+ " </tr>\n"
					+ " <tr height=19 style='height:14.25pt'>\n"
					+ "  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n"
					+ "  border-left:none'>已办</td>\n"
					+ "  <td class=xl6622517 style='border-top:none;border-left:none'>"
					+ num.get(strs[i]).get("已办结")
					+ "</td>\n"
					+ " </tr>\n"
					+ " <tr height=19 style='height:14.25pt'>\n"
					+ "  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n"
					+ "  border-left:none'>超期</td>\n"
					+ "  <td class=xl6622517 style='border-top:none;border-left:none'>"
					+ num.get(strs[i]).get("未办")
					+ "</td>\n"
					+ " </tr>\n"
					+ " <tr height=19 style='height:14.25pt'>\n"
					+ "  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n"
					+ "  border-left:none'>撤回办结</td>\n"
					+ "  <td class=xl6622517 style='border-top:none;border-left:none'>"
					+ num.get(strs[i]).get("撤回办结") + "</td>\n" + " </tr>"

			/*
			 * "<tr height=19 style='height:14.25pt'>\n" +
			 * "  <td rowspan=6 height=114 class=xl6522517 style='border-bottom:.5pt solid black;\n"
			 * + "  height:85.5pt;border-top:none'>"+strs[i]+"</td>\n" +
			 * "  <td class=xl6622517 style='border-top:none;border-left:none'>总数</td>\n"
			 * +
			 * "  <td class=xl6622517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("总计")+"</td>\n" +
			 * "  <td rowspan=6 class=xl6522517 style='border-bottom:.5pt solid black;\n"
			 * + "  border-top:none'>"+num.get(strs[i]).get("小计")+"</td>\n" +
			 * "  <td rowspan=6 class=xl6722517 style='border-bottom:.5pt solid black;\n"
			 * + "  border-top:none'>"+num.get(strs[i]).get("办结率")+"</td>\n" +
			 * " </tr>\n" + " <tr height=19 style='height:14.25pt'>\n" +
			 * "  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n"
			 * + "  border-left:none'>已办</td>\n" +
			 * "  <td class=xl6922517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("已办结")+"</td>\n" + " </tr>\n" +
			 * " <tr height=19 style='height:14.25pt'>\n" +
			 * "  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n"
			 * + "  border-left:none'>未办</td>\n" +
			 * "  <td class=xl6922517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("未办")+"</td>\n" + " </tr>\n" +
			 * " <tr height=19 style='height:14.25pt'>\n" +
			 * "  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n"
			 * + "  border-left:none'>待签收</td>\n" +
			 * "  <td class=xl6922517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("待签收")+"</td>\n" + " </tr>\n" +
			 * " <tr height=19 style='height:14.25pt'>\n" +
			 * "  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n"
			 * + "  border-left:none'>签收通过未核准</td>\n" +
			 * "  <td class=xl6922517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("签收通过未核准")+"</td>\n" + " </tr>\n" +
			 * " <tr height=19 style='height:14.25pt'>\n" +
			 * "  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n"
			 * + "  border-left:none'>撤回办结</td>\n" +
			 * "  <td class=xl6922517 style='border-top:none;border-left:none'>"
			 * +num.get(strs[i]).get("撤回办结")+"</td>\n" + " </tr>"
			 */);
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String qianHaiGangZiSl(List<Map> num) {
		StringBuffer sb = new StringBuffer();
		sb.append(

		"<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
				+ "xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
				+ "xmlns=\"http://www.w3.org/TR/REC-html40\">\n"
				+ "\n"
				+ "<head>\n"
				+ "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n"
				+ "<meta name=ProgId content=Excel.Sheet>\n"
				+ "<meta name=Generator content=\"Microsoft Excel 15\">\n"
				+ "<link rel=File-List href=\"全流程网上商事登记业务超期办理情况表.files/filelist.xml\">\n"
				+ "<style id=\"全流程网上商事登记业务超期办理情况表_13186_Styles\">\n"
				+ "<!--table\n"
				+ "  {mso-displayed-decimal-separator:\"\\.\";\n"
				+ "  mso-displayed-thousand-separator:\"\\,\";}\n"
				+ ".font513186\n"
				+ "  {color:windowtext;\n"
				+ "  font-size:9.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:等线;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;}\n"
				+ ".font613186\n"
				+ "  {color:windowtext;\n"
				+ "  font-size:9.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:宋体;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;}\n"
				+ ".xl1513186\n"
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
				+ "  text-align:general;\n"
				+ "  vertical-align:bottom;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6313186\n"
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
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6413186\n"
				+ "  {padding-top:1px;\n"
				+ "  padding-right:1px;\n"
				+ "  padding-left:1px;\n"
				+ "  mso-ignore:padding;\n"
				+ "  color:black;\n"
				+ "  font-size:16.0pt;\n"
				+ "  font-weight:400;\n"
				+ "  font-style:normal;\n"
				+ "  text-decoration:none;\n"
				+ "  font-family:华文中宋;\n"
				+ "  mso-generic-font-family:auto;\n"
				+ "  mso-font-charset:134;\n"
				+ "  mso-number-format:General;\n"
				+ "  text-align:center;\n"
				+ "  vertical-align:middle;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6513186\n"
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
				+ "  text-align:right;\n"
				+ "  vertical-align:bottom;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
				+ ".xl6613186\n"
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
				+ "  text-align:right;\n"
				+ "  vertical-align:middle;\n"
				+ "  border:.5pt solid windowtext;\n"
				+ "  mso-background-source:auto;\n"
				+ "  mso-pattern:auto;\n"
				+ "  white-space:nowrap;}\n"
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
				+ "\n"
				+ "\n"
				+ "<div id=\"全流程网上商事登记业务超期办理情况表_13186\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=695 style='border-collapse:\n"
				+ " collapse;table-layout:fixed;width:522pt'>\n"
				+ " <col width=78 style='mso-width-source:userset;mso-width-alt:2496;width:59pt'>\n"
				+ " <col width=155 style='mso-width-source:userset;mso-width-alt:4960;width:116pt'>\n"
				+ " <col width=234 style='mso-width-source:userset;mso-width-alt:7488;width:176pt'>\n"
				+ " <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>\n"
				+ " <col width=123 style='mso-width-source:userset;mso-width-alt:3936;width:92pt'>\n"
				+ " <tr height=56 style='mso-height-source:userset;height:42.0pt'>\n"
				+ "  <td colspan=5 height=56 class=xl6413186 width=695 style='height:42.0pt;\n"
				+ "  width:522pt'>全流程网上商事登记业务超期办理情况表</td>\n"
				+ " </tr>\n"
				+ " <tr height=19 style='height:14.25pt'>\n"
				+ "  <td height=19 class=xl6313186 style='height:14.25pt'>序号</td>\n"
				+ "  <td class=xl6313186 style='border-left:none'>流程号</td>\n"
				+ "  <td class=xl6313186 style='border-left:none'>大厅</td>\n"
				+ "  <td class=xl6313186 style='border-left:none'>办理时限</td>\n"
				+ "  <td class=xl6313186 style='border-left:none'>办理人员</td>\n"
				+ " </tr>");
		for (int i = 0; i < num.size(); i++) {
			sb.append(

			"<tr height=19 style='height:14.25pt'>\n"
					+ " <td height=19 class=xl6513186 style='height:14.25pt;border-top:none'>"
					+ (i + 1)
					+ "</td>\n"
					+ " <td class=xl6613186 style='border-top:none;border-left:none'>"
					+ num.get(i).get("applyNo")
					+ "</td>\n"
					+ " <td class=xl6613186 style='border-top:none;border-left:none'>"
					+ num.get(i).get("windowName")
					+ "</td>\n"
					+ " <td class=xl6613186 style='border-top:none;border-left:none'>"
					+ num.get(i).get("timeExceed")
					+ "</td>\n"
					+ " <td class=xl6613186 style='border-top:none;border-left:none'>"
					+ num.get(i).get("userName") + "</td>\n" + "</tr>");
		}
		sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
		return sb.toString();
	}

}
