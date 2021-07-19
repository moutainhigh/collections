package com.gwssi.report.query_jiean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class QueryJieAnUtils {
	public static Calendar cal = Calendar.getInstance();
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取同比时间
	 * */
		public static String getTongBiDate(String beforDate){
			String str=beforDate;
			try {
				cal.setTime(sdf.parse(str));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.YEAR, -1);//
			str = sdf.format(cal.getTime());
			return str;
		}
		
		/**
		 * 获取环比时间
		 *
		 * */
		public static String getHuanBiDate(String beforDate){
			String str=beforDate;
			try {
				cal.setTime(sdf.parse(str));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cal.add(Calendar.MONTH, -1);//
			str = sdf.format(cal.getTime());
			return str;
		}
		
		/**
		 * 将前台参数转化为sql中的参数
		 *
		 * *//*
		public static String splitStringByComma (String str) {
			String[] split = str.split(",");
			str = "";
			for (int i = 0; i < split.length; i++) {
				if (i == 0) {
					str += "'" + split[i] + "'";
				} else {
					str += ",'" + split[i] + "'";
				}
			}
			return str;
		}*/
		
		/**
		 * 将前台参数转化为sql中的参数（对超过1000个的参数进行额外处理）
		 * 
		 * */
		public static String splitStringByComma (String str, String column) {
			String[] split = str.split(",");
			StringBuffer sb=new StringBuffer();
			if (split.length<=1000) {
				sb.append("and "+column+" in (");
				for (int i = 0; i < split.length; i++) {
					if (i == 0) {
						sb.append( "'" + split[i] + "'");
					} else {
						sb.append(",'" + split[i] + "'");
					}
				}
				sb.append(") \n");
			}else{
				List<List<String>> list = splitToList(split,1000);
				sb.append(" and (");
				for (int i = 0,j=list.size(); i < j; i++) {
					List<String> list2 = list.get(i);
					if (i==0) {
						sb.append(column+" in (");
						for (int j2 = 0; j2 < list2.size(); j2++) {
							if (j2==0) {
								sb.append("'" + list2.get(j2) + "'");
							}else{
								sb.append(",'" + list2.get(j2) + "'");
							}
						}
						sb.append(") \n");
					}else{
						sb.append(" or "+column +" in (");
						for (int k = 0; k <list2.size() ; k++) {
							if (k==0) {
								sb.append("'" + list2.get(k) + "'");
							}else{
								sb.append(",'" + list2.get(k) + "'");
							}
						}
						sb.append(") \n ");
					}
				}
				sb.append(") \n");
			}
			return sb.toString();
		}
		
		
		
		/**
		 * 将前台参数转化为sql中的参数(客体类型涉及到5个码表，需要根据codetable来进行分类),
		 * 该函数为码表处理
		 *
		 * */
		public static String UtilOfObjectTypeS (String str,String column) {
			String[] split = str.split(",");
			StringBuffer sb=new StringBuffer();
			if (split.length<=1000) {
				sb.append("and "+column+" in (");
				for (int i = 0; i < split.length; i++) {
					if (i == 0) {
						sb.append( "'" + split[i].substring(4) + "'");
					} else {
						sb.append(",'" + split[i].substring(4) + "'");
					}
				}
				sb.append(") \n");
			}else{
				List<List<String>> list = splitToList(split,1000);
				sb.append(" and (");
				for (int i = 0,j=list.size(); i < j; i++) {
					List<String> list2 = list.get(i);
					if (i==0) {
						sb.append(column+" in (");
						for (int j2 = 0; j2 < list2.size(); j2++) {
							if (j2==0) {
								sb.append("'" + list2.get(j2).substring(4) + "'");
							}else{
								sb.append(",'" + list2.get(j2).substring(4) + "'");
							}
						}
						sb.append(") \n");
					}else{
						sb.append(" or "+column +" in (");
						for (int k = 0; k <list2.size() ; k++) {
							if (k==0) {
								sb.append("'" + list2.get(k).substring(4) + "'");
							}else{
								sb.append(",'" + list2.get(k).substring(4) + "'");
							}
						}
						sb.append(") \n ");
					}
				}
				sb.append(") \n");
			}
			return sb.toString();
		}
		/**
		 * 将前台参数转化为sql中的参数(客体类型涉及到5个码表，需要根据codetable来进行分类)
		 * 该函数为codetable处理
		 * */
		public static String UtilOfObjectTypeB(String str,String column) {
			String[] split = str.split(",");
			StringBuffer sb=new StringBuffer();
			if (split.length<=1000) {
				sb.append("and "+column+" in (");
				for (int i = 0; i < split.length; i++) {
					if (i == 0) {
						sb.append( "'" + split[i].substring(0,4) + "'");
					} else {
						sb.append(",'" + split[i].substring(0,4) + "'");
					}
				}
				sb.append(") \n");
			}else{
				List<List<String>> list = splitToList(split,1000);
				sb.append(" and (");
				for (int i = 0,j=list.size(); i < j; i++) {
					List<String> list2 = list.get(i);
					if (i==0) {
						sb.append(column+" in (");
						for (int j2 = 0; j2 < list2.size(); j2++) {
							if (j2==0) {
								sb.append("'" + list2.get(j2).substring(0,4) + "'");
							}else{
								sb.append(",'" + list2.get(j2).substring(0,4) + "'");
							}
						}
						sb.append(") \n");
					}else{
						sb.append(" or "+column +" in (");
						for (int k = 0; k <list2.size() ; k++) {
							if (k==0) {
								sb.append("'" + list2.get(k).substring(0,4) + "'");
							}else{
								sb.append(",'" + list2.get(k).substring(0,4) + "'");
							}
						}
						sb.append(") \n ");
					}
				}
				sb.append(") \n");
			}
			return sb.toString();
		}
		
		/**
		 * 将超过长度1000个的数组转化为ArrayList
		 * 可根据subSize调整拆分部分的大小。
		 * */
		private static  List<List<String>> splitToList(String[] ary, int subSize) {
	         int count = ary.length % subSize == 0 ? ary.length / subSize: ary.length / subSize + 1;
	         List<List<String>> subAryList = new ArrayList<List<String>>();
	         for (int i = 0; i < count; i++) {
	          int index = i * subSize;
	          List<String> list = new ArrayList<String>();
	          int j = 0;
	              while (j < subSize && index < ary.length) {
	                  list.add(ary[index++]);
	                  j++;
	              }
	          subAryList.add(list);
	         }
	        
	         return subAryList;
	         }

		
		public String tableUtilByName(List<Map> num,String tableName) {
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
							"<link rel=File-List href=\"处罚种类统计.files/filelist.xml\">\n" + 
							"<style id=\"处罚种类统计_19941_Styles\">\n" + 
							"<!--table\n" + 
							"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
							"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font519941\n" + 
							"\t{color:windowtext;\n" + 
							"\tfont-size:9.0pt;\n" + 
							"\tfont-weight:400;\n" + 
							"\tfont-style:normal;\n" + 
							"\ttext-decoration:none;\n" + 
							"\tfont-family:等线;\n" + 
							"\tmso-generic-font-family:auto;\n" + 
							"\tmso-font-charset:134;}\n" + 
							".xl1519941\n" + 
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
							".xl6319941\n" + 
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
							".xl6419941\n" + 
							"\t{padding-top:1px;\n" + 
							"\tpadding-right:1px;\n" + 
							"\tpadding-left:1px;\n" + 
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
							".xl6519941\n" + 
							"  {padding-top:1px;\n" + 
							"  padding-right:1px;\n" + 
							"  padding-left:1px;\n" + 
							"  mso-ignore:padding;\n" + 
							"  color:black;\n" + 
							"  font-size:10.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:宋体;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;\n" + 
							"  mso-number-format:General;\n" + 
							"  text-align:left;\n" + 
							"  vertical-align:middle;\n" + 
							"  border:.5pt solid windowtext;\n" + 
							"  mso-background-source:auto;\n" + 
							"  mso-pattern:auto;\n" + 
							"  white-space:nowrap;}\n" + 
							".xl6619941\n" + 
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
							"\n" + 
							"\n" + 
							"<div id=\"处罚种类统计_19941\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1800 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:1350pt'>\n" + 
							" <col width=288 style='mso-width-source:userset;mso-width-alt:9216;width:216pt'>\n" + 
							" <col width=72 span=21 style='width:54pt'>\n" + 
							" <tr height=27 style='height:20.25pt'>\n" + 
							"  <td colspan=22 height=27 class=xl6319941 width=1800 style='height:20.25pt;\n" + 
							"  width:1350pt'>结案信息申诉举报"+tableName+"统计报表</td>\n" + 
							" </tr>\n" + 
							" <tr height=38 style='height:28.5pt'>\n" + 
							"  <td height=38 class=xl6419941 width=288 style='height:28.5pt;border-top:none;\n" + 
							"  width:216pt'>项目</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>办结数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>属实数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>调解成功数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>欺诈数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>争议金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>挽回经济损失</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>加倍赔偿金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>精神赔偿金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>立案数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>案值</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>违法所得</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>处罚金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>没收金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>变价金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>经济损失值</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>捣毁窝点</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>出动人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>出动车辆数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>受伤人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>受害人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>牺牲人数</td>\n" + 
							" </tr>");
			for (int i = 0,j=num.size(); i < j; i++) {
				sb.append(
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6519941 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("subnum")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("shushi")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("tiaojie")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("qizha")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("zhengyijine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("wanhuijingjisunshi")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("jiabeipeichangjine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("jingshenpeichangjine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("lianshu")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("anzhi")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("weifasuode")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("chufajine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("moshoujine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("bianjiajine")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("jingjisunshizhi")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("daohuiwodian")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("chudongrenshu")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("chudongcheliang")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("shoushangrenshu")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("shouhairenshu")+"</td>\n" + 
							"  <td class=xl6619941 style='border-top:none;border-left:none'>"+num.get(i).get("xishengrenshu") +"</td>\n" + 
							" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}
		/**
		 * 涉及客体的文件生成需要单独的函数。
		 * */
		public String excelFileStreamForObject(List<List<Map>> num) {
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
							"<link rel=File-List href=\"处罚种类统计.files/filelist.xml\">\n" + 
							"<style id=\"处罚种类统计_19941_Styles\">\n" + 
							"<!--table\n" + 
							"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
							"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font519941\n" + 
							"\t{color:windowtext;\n" + 
							"\tfont-size:9.0pt;\n" + 
							"\tfont-weight:400;\n" + 
							"\tfont-style:normal;\n" + 
							"\ttext-decoration:none;\n" + 
							"\tfont-family:等线;\n" + 
							"\tmso-generic-font-family:auto;\n" + 
							"\tmso-font-charset:134;}\n" + 
							".xl1519941\n" + 
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
							".xl6319941\n" + 
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
							".xl6419941\n" + 
							"\t{padding-top:1px;\n" + 
							"\tpadding-right:1px;\n" + 
							"\tpadding-left:1px;\n" + 
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
							".xl6519941\n" + 
							"  {padding-top:1px;\n" + 
							"  padding-right:1px;\n" + 
							"  padding-left:1px;\n" + 
							"  mso-ignore:padding;\n" + 
							"  color:black;\n" + 
							"  font-size:10.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:宋体;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;\n" + 
							"  mso-number-format:General;\n" + 
							"  text-align:left;\n" + 
							"  vertical-align:middle;\n" + 
							"  border:.5pt solid windowtext;\n" + 
							"  mso-background-source:auto;\n" + 
							"  mso-pattern:auto;\n" + 
							"  white-space:nowrap;}\n" + 
							".xl6619941\n" + 
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
							"\n" + 
							"\n" + 
							"<div id=\"处罚种类统计_19941\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1800 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:1350pt'>\n" + 
							" <col width=288 style='mso-width-source:userset;mso-width-alt:9216;width:216pt'>\n" + 
							" <col width=72 span=21 style='width:54pt'>\n" + 
							" <tr height=27 style='height:20.25pt'>\n" + 
							"  <td colspan=22 height=27 class=xl6319941 width=1800 style='height:20.25pt;\n" + 
							"  width:1350pt'>结案信息申诉举报涉及客体统计报表</td>\n" + 
							" </tr>\n" + 
							" <tr height=38 style='height:28.5pt'>\n" + 
							"  <td height=38 class=xl6419941 width=288 style='height:28.5pt;border-top:none;\n" + 
							"  width:216pt'>项目</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>办结数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>属实数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>调解成功数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>欺诈数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>争议金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>挽回经济损失</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>加倍赔偿金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>精神赔偿金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>立案数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>案值</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>违法所得</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>处罚金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>没收金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>变价金额</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>经济损失值</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>捣毁窝点</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>出动人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>出动车辆数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>受伤人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>受害人数</td>\n" + 
							"  <td class=xl6419941 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>牺牲人数</td>\n" + 
							" </tr>");
			for (int i = 0,j=num.size(); i < j; i++) {
				List<Map> list=num.get(i);
				for (int i2 = 0 ,j2=list.size(); i2 < j2; i2++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6519941 style='height:14.25pt;border-top:none'>"+list.get(i2).get("name")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("subnum")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("shushi")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("tiaojie")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("qizha")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("zhengyijine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("wanhuijingjisunshi")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("jiabeipeichangjine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("jingshenpeichangjine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("lianshu")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("anzhi")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("weifasuode")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("chufajine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("moshoujine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("bianjiajine")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("jingjisunshizhi")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("daohuiwodian")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("chudongrenshu")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("chudongcheliang")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("shoushangrenshu")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("shouhairenshu")+"</td>\n" + 
								"  <td class=xl6619941 style='border-top:none;border-left:none'>"+list.get(i2).get("xishengrenshu") +"</td>\n" + 
								" </tr>");
				}
				if (i==j-1) {
					
				}else{
				sb.append("<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6519941 style='height:14.25pt;border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						"  <td class=xl6619941 style='border-right:none;border-left:none'></td>\n" + 
						" </tr>");
				}
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String tableUtilByNameForZiXunJianYi(List<Map> num, String fileName){
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
							"<link rel=File-List href=\"办结部门.files/filelist.xml\">\n" + 
							"<style id=\"办结部门_28238_Styles\">\n" + 
							"<!--table\n" + 
							"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
							"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font528238\n" + 
							"\t{color:windowtext;\n" + 
							"\tfont-size:9.0pt;\n" + 
							"\tfont-weight:400;\n" + 
							"\tfont-style:normal;\n" + 
							"\ttext-decoration:none;\n" + 
							"\tfont-family:等线;\n" + 
							"\tmso-generic-font-family:auto;\n" + 
							"\tmso-font-charset:134;}\n" + 
							".xl6328238\n" + 
							"\t{padding-top:1px;\n" + 
							"\tpadding-right:1px;\n" + 
							"\tpadding-left:1px;\n" + 
							"\tmso-ignore:padding;\n" + 
							"\tcolor:black;\n" + 
							"\tfont-size:11.0pt;\n" + 
							"\tfont-weight:400;\n" + 
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
							".xl6428238\n" + 
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
							".xl6528238\n" + 
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
							".xl6628238\n" + 
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
							".xl6728238\n" + 
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
							".xl6828238\n" + 
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
							".xl6928238\n" + 
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
							"<div id=\"办结部门_28238\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1052 class=xl6328238\n" + 
							" style='border-collapse:collapse;table-layout:automatic;width:794pt'>\n" + 
							" <col class=xl6328238 width=152 style='mso-width-source:userset;mso-width-alt:\n" + 
							" 4864;width:114pt'>\n" + 
							" <col class=xl6328238 width=90 span=10 style='mso-width-source:userset;\n" + 
							" mso-width-alt:2880;width:68pt'>\n" + 
							" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
							"  <td colspan=11 height=36 class=xl6428238 width=1052 style='height:27.0pt;\n" + 
							"  width:794pt'>咨询建议信息件"+fileName+"统计表</td>\n" + 
							" </tr>\n" + 
							" <tr height=38 style='height:28.5pt'>\n" + 
							"  <td height=38 class=xl6628238 width=152 style='height:28.5pt;border-top:none;\n" + 
							"  width:114pt'>"+fileName+"</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>未回复</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>已回复</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>回访属实数</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>回访不属实数</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>合计</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>占总量（%）</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>上时段数据</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>比上时段同期增减（%）</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>去年同期数据</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>比去年同期增减（%）</td>\n" + 
							" </tr>");
			for (int i = 0; i < num.size(); i++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6828238 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("weihuifu")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("yihuifu")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("shushi")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("bushushi")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("heji")+"</td>\n" + 
								"  <td class=xl6928238 style='border-top:none;border-left:none'>"+num.get(i).get("zhanbi")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("huanbishu")+"</td>\n" + 
								"  <td class=xl6928238 style='border-top:none;border-left:none'>"+num.get(i).get("huanbi")+"</td>\n" + 
								"  <td class=xl6728238 style='border-top:none;border-left:none'>"+num.get(i).get("tongbishu")+"</td>\n" + 
								"  <td class=xl6928238 style='border-top:none;border-left:none'>"+num.get(i).get("tongbi")+"</td>\n" + 
								" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}
		
		
		//结案信息咨询建议涉及客体
		public String excelFileStreamForZiXunJianYiObject(List<List<Map>> num) {
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
							"<link rel=File-List href=\"办结部门.files/filelist.xml\">\n" + 
							"<style id=\"办结部门_28238_Styles\">\n" + 
							"<!--table\n" + 
							"\t{mso-displayed-decimal-separator:\"\\.\";\n" + 
							"\tmso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font528238\n" + 
							"\t{color:windowtext;\n" + 
							"\tfont-size:9.0pt;\n" + 
							"\tfont-weight:400;\n" + 
							"\tfont-style:normal;\n" + 
							"\ttext-decoration:none;\n" + 
							"\tfont-family:等线;\n" + 
							"\tmso-generic-font-family:auto;\n" + 
							"\tmso-font-charset:134;}\n" + 
							".xl6328238\n" + 
							"\t{padding-top:1px;\n" + 
							"\tpadding-right:1px;\n" + 
							"\tpadding-left:1px;\n" + 
							"\tmso-ignore:padding;\n" + 
							"\tcolor:black;\n" + 
							"\tfont-size:11.0pt;\n" + 
							"\tfont-weight:400;\n" + 
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
							".xl6428238\n" + 
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
							".xl6528238\n" + 
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
							".xl6628238\n" + 
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
							".xl6728238\n" + 
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
							".xl6828238\n" + 
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
							".xl6928238\n" + 
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
							"<div id=\"办结部门_28238\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1052 class=xl6328238\n" + 
							" style='border-collapse:collapse;table-layout:automatic;width:794pt'>\n" + 
							" <col class=xl6328238 width=152 style='mso-width-source:userset;mso-width-alt:\n" + 
							" 4864;width:114pt'>\n" + 
							" <col class=xl6328238 width=90 span=10 style='mso-width-source:userset;\n" + 
							" mso-width-alt:2880;width:68pt'>\n" + 
							" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
							"  <td colspan=11 height=36 class=xl6428238 width=1052 style='height:27.0pt;\n" + 
							"  width:794pt'>咨询建议信息件涉及客体统计表</td>\n" + 
							" </tr>\n" + 
							" <tr height=38 style='height:28.5pt'>\n" + 
							"  <td height=38 class=xl6628238 width=152 style='height:28.5pt;border-top:none;\n" + 
							"  width:114pt'>涉及客体</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>未回复</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>已回复</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>回访属实数</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>回访不属实数</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>合计</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>占总量（%）</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>上时段数据</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>比上时段同期增减（%）</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>去年同期数据</td>\n" + 
							"  <td class=xl6628238 width=90 style='border-top:none;border-left:none;\n" + 
							"  width:68pt'>比去年同期增减（%）</td>\n" + 
							" </tr>");
			for (int i = 0,j=num.size(); i < j; i++) {
				List<Map> list=num.get(i);
				for (int i2 = 0 ,j2=list.size(); i2 < j2; i2++) {
					sb.append(
							"<tr height=19 style='height:14.25pt'>\n" +
									"  <td height=19 class=xl6828238 style='height:14.25pt;border-top:none'>"+list.get(i2).get("name")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("weihuifu")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("yihuifu")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("shushi")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("bushushi")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("heji")+"</td>\n" + 
									"  <td class=xl6928238 style='border-top:none;border-left:none'>"+list.get(i2).get("zhanbi")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("huanbishu")+"</td>\n" + 
									"  <td class=xl6928238 style='border-top:none;border-left:none'>"+list.get(i2).get("huanbi")+"</td>\n" + 
									"  <td class=xl6728238 style='border-top:none;border-left:none'>"+list.get(i2).get("tongbishu")+"</td>\n" + 
									"  <td class=xl6928238 style='border-top:none;border-left:none'>"+list.get(i2).get("tongbi")+"</td>\n" + 
									" </tr>");
					}
				if (i==j-1) {
					
				}else{
					sb.append(
							"<tr height=19 style='height:14.25pt'>\n" +
									"  <td height=19 class=xl6828238 style='height:14.25pt;border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6728238 style='border-right:none;border-left:none'></td>\n" + 
									"  <td class=xl6928238 style='border-right:none;border-left:none'></td>\n" + 
									" </tr>");
				}
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downXiaoWeiHuiQianShi(String tongjinianfen,
				String shangnian, List<Map> num) {
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
							"<link rel=File-List href=\"消委会投诉增长或下降居前十位的商品和服务.files/filelist.xml\">\n" + 
							"<style id=\"消委会投诉增长或下降居前十位的商品和服务_24518_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font524518\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl1524518\n" + 
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
							".xl6324518\n" + 
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
							".xl6424518\n" + 
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
							".xl6524518\n" + 
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
							".xl6624518\n" + 
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
							".xl6724518\n" + 
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
							".xl6824518\n" + 
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
							"\n" + 
							"\n" + 
							"<div id=\"消委会投诉增长或下降居前十位的商品和服务_24518\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=594 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:446pt'>\n" + 
							" <col width=216 style='mso-width-source:userset;mso-width-alt:6912;width:162pt'>\n" + 
							" <col width=100 span=2 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
							" width:75pt'>\n" + 
							" <col width=178 style='mso-width-source:userset;mso-width-alt:5696;width:134pt'>\n" + 
							" <tr height=34 style='mso-height-source:userset;height:25.5pt'>\n" + 
							"  <td colspan=4 height=34 class=xl6324518 width=594 style='height:25.5pt;\n" + 
							"  width:446pt'>消委会投诉增长或下降居前十位的商品和服务</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6524518 style='height:14.25pt;border-top:none'>商品和服务名称</td>\n" + 
							"  <td class=xl6524518 style='border-top:none;border-left:none'>"+shangnian+"</td>\n" + 
							"  <td class=xl6524518 style='border-top:none;border-left:none'>"+tongjinianfen+"</td>\n" + 
							"  <td class=xl6524518 style='border-top:none;border-left:none'>变化幅度</td>\n" + 
							" </tr>");
			for (int i = 0; i < num.size(); i++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6624518 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
								"  <td class=xl6724518 style='border-top:none;border-left:none'>"+num.get(i).get(shangnian)+"</td>\n" + 
								"  <td class=xl6724518 style='border-top:none;border-left:none'>"+num.get(i).get(tongjinianfen)+"</td>\n" + 
								"  <td class=xl6824518 style='border-top:none;border-left:none'>"+num.get(i).get("cnt")+"</td>\n" + 
								" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downDianZiShangWuReDian(
				Map<String, Map<String, String>> num) {
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
							"<link rel=File-List href=\"电子商务投诉热点情况统计表.files/filelist.xml\">\n" + 
							"<style id=\"电子商务投诉热点情况统计表_6034_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font56034\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl156034\n" + 
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
							".xl636034\n" + 
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
							"  white-space:normal;}\n" + 
							".xl646034\n" + 
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
							"  text-align:left;\n" + 
							"  vertical-align:middle;\n" + 
							"  border:.5pt solid windowtext;\n" + 
							"  mso-background-source:auto;\n" + 
							"  mso-pattern:auto;\n" + 
							"  white-space:nowrap;}\n" + 
							".xl656034\n" + 
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
							".xl666034\n" + 
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
							".xl676034\n" + 
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
							"  white-space:normal;\n" + 
							"  layout-flow:vertical-ideographic;}\n" + 
							".xl686034\n" + 
							"  {padding-top:1px;\n" + 
							"  padding-right:1px;\n" + 
							"  padding-left:1px;\n" + 
							"  mso-ignore:padding;\n" + 
							"  color:black;\n" + 
							"  font-size:15.0pt;\n" + 
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
							".xl696034\n" + 
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
							".xl706034\n" + 
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
							"  border-top:.5pt solid windowtext;\n" + 
							"  border-right:.5pt solid windowtext;\n" + 
							"  border-bottom:none;\n" + 
							"  border-left:.5pt solid windowtext;\n" + 
							"  mso-background-source:auto;\n" + 
							"  mso-pattern:auto;\n" + 
							"  white-space:nowrap;\n" + 
							"  layout-flow:vertical-ideographic;}\n" + 
							".xl716034\n" + 
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
							"  border-top:none;\n" + 
							"  border-right:.5pt solid windowtext;\n" + 
							"  border-bottom:none;\n" + 
							"  border-left:.5pt solid windowtext;\n" + 
							"  mso-background-source:auto;\n" + 
							"  mso-pattern:auto;\n" + 
							"  white-space:nowrap;\n" + 
							"  layout-flow:vertical-ideographic;}\n" + 
							".xl726034\n" + 
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
							"  white-space:nowrap;\n" + 
							"  layout-flow:vertical-ideographic;}\n" + 
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
							"<div id=\"电子商务投诉热点情况统计表_6034\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=850 style='border-collapse:\n" + 
							" collapse;table-layout:fixed;width:638pt'>\n" + 
							" <col width=33 style='mso-width-source:userset;mso-width-alt:1056;width:25pt'>\n" + 
							" <col width=169 style='mso-width-source:userset;mso-width-alt:5408;width:127pt'>\n" + 
							" <col width=72 span=9 style='width:54pt'>\n" + 
							" <tr height=46 style='mso-height-source:userset;height:34.5pt'>\n" + 
							"  <td colspan=11 height=46 class=xl686034 width=850 style='height:34.5pt;\n" + 
							"  width:638pt'><a name=\"RANGE!A1\">电子商务投诉热点情况统计表</a></td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td colspan=2 rowspan=2 height=63 class=xl636034 width=202 style='height:\n" + 
							"  47.25pt;width:152pt'>项目</td>\n" + 
							"  <td colspan=5 class=xl636034 width=360 style='border-left:none;width:270pt'>涉及主体网站类型（本地/外地）</td>\n" + 
							"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>占投诉登记总量百分比（%）</td>\n" + 
							"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>上一时间段数据</td>\n" + 
							"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>比上一时间段增减（%）</td>\n" + 
							"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>去年同期数据</td>\n" + 
							" </tr>\n" + 
							" <tr height=42 style='height:31.5pt'>\n" + 
							"  <td height=42 class=xl636034 width=72 style='height:31.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>交易平台类</td>\n" + 
							"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>应用类</td>\n" + 
							"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>服务类</td>\n" + 
							"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>互联网门户网站</td>\n" + 
							"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>其它</td>\n" + 
							" </tr>");
			sb.append(
					"<tr height=21 style='height:15.75pt'>\n" +
							"  <td rowspan=7 height=147 class=xl706034 style='border-bottom:.5pt solid black;\n" + 
							"  height:110.25pt;border-top:none'>商品</td>\n" + 
							"  <td class=xl646034 style='border-top:none;border-left:none'>电子电器商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("电子电器商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>机械类商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("机械类商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>烟酒饮料食品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("烟酒饮料食品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>建材装饰商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("建材装饰商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>珠宝首饰商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("珠宝首饰商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>日用百货商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("日用百货商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>其他商品</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他商品").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td rowspan=8 height=168 class=xl676034 width=33 style='height:126.0pt;\n" + 
							"  border-top:none;width:25pt'>服务</td>\n" + 
							"  <td class=xl646034 style='border-top:none;border-left:none'>游戏娱乐服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("游戏娱乐服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>中介服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("中介服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>物流快递服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("物流快递服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>旅游服务（订票业务）</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("旅游服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>金融支付服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("金融支付服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>软件服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("软件服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>即时通信服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("即时通信服务").get("同比数")+    "</td>\n" + 
							" </tr>\n" + 
							" <tr height=21 style='height:15.75pt'>\n" + 
							"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
							"  border-left:none'>其他服务</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("交易平台类")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("应用类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("服务类")+    "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("互联网门户")+"</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("其它")+      "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("占比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("环比数")+    "</td>\n" + 
							"  <td class=xl666034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("环比")+      "</td>\n" + 
							"  <td class=xl656034 style='border-top:none;border-left:none'>"+num.get("其他服务类").get("同比数")+    "</td>\n" + 
							" </tr>");
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downDianZiShangWuTouSu(List<List<Map>> num) {
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
							"<link rel=File-List href=\"电子商务投诉情况统计报表.files/filelist.xml\">\n" + 
							"<style id=\"电子商务投诉情况统计报表_17275_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font517275\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl1517275\n" + 
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
							".xl6317275\n" + 
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
							".xl6417275\n" + 
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
							".xl6517275\n" + 
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
							".xl6617275\n" + 
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
							".xl6717275\n" + 
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
							"<div id=\"电子商务投诉情况统计报表_17275\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1219 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:915pt'>\n" + 
							" <col width=221 style='mso-width-source:userset;mso-width-alt:7072;width:166pt'>\n" + 
							" <col width=72 span=9 style='width:54pt'>\n" + 
							" <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>\n" + 
							" <col width=72 style='width:54pt'>\n" + 
							" <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>\n" + 
							" <col width=72 style='width:54pt'>\n" + 
							" <tr height=41 style='mso-height-source:userset;height:30.75pt'>\n" + 
							"  <td colspan=14 height=41 class=xl6617275 width=1219 style='height:30.75pt;\n" + 
							"  width:915pt'>电子商务投诉情况统计报表</td>\n" + 
							" </tr>\n" + 
							" <tr height=38 style='height:28.5pt'>\n" + 
							"  <td height=38 class=xl6517275 width=221 style='height:28.5pt;border-top:none;\n" + 
							"  width:166pt'>涉及客体类型</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>电子商务</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>邮购</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>电话购物</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>电视购物</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>交易平台类</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>应用类</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>服务类</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>互联网门户</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>其他</td>\n" + 
							"  <td class=xl6517275 width=110 style='border-top:none;border-left:none;\n" + 
							"  width:83pt'>占投诉登记总量百分比（%）</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>上一时间段数据</td>\n" + 
							"  <td class=xl6517275 width=96 style='border-top:none;border-left:none;\n" + 
							"  width:72pt'>比上一时间段增减（%）</td>\n" + 
							"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>去年同期数据</td>\n" + 
							" </tr>");
			for (int i = 0,j=num.size(); i < j; i++) {
				List<Map> list=num.get(i);
				for (int i2=0,j2=list.size(); i2 < j2; i2++) {
					sb.append(
							"<tr height=19 style='height:14.25pt'>\n" +
									"  <td height=19 class=xl6717275 style='height:14.25pt;border-top:none'>"+list.get(i2).get("name")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("电子商务")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("邮购")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("电话购物")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("电视购物")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("交易平台类")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("应用类")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("服务类")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("互联网门户")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("其他")+"</td>\n" + 
									"  <td class=xl6417275 style='border-top:none;border-left:none'>"+list.get(i2).get("占比")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("环比数")+"</td>\n" + 
									"  <td class=xl6417275 style='border-top:none;border-left:none'>"+list.get(i2).get("环比")+"</td>\n" + 
									"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list.get(i2).get("同比数")+"</td>\n" + 
									" </tr>");
					}
					if (i==j-1) {
						
					}else{
						sb.append("<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6717275 style='height:14.25pt;border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6417275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6417275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
								" </tr>");
					}
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downDianZiShangWuXiaoFei(List<Map> num) {
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
							"<link rel=File-List href=\"电子商务消费投诉情况统计报表.files/filelist.xml\">\n" + 
							"<style id=\"电子商务消费投诉情况统计报表_26225_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font526225\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl1526225\n" + 
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
							".xl6326225\n" + 
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
							".xl6426225\n" + 
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
							".xl6526225\n" + 
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
							".xl6626225\n" + 
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
							"<div id=\"电子商务消费投诉情况统计报表_26225\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=1181 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:886pt'>\n" + 
							" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
							" <col width=72 span=5 style='width:54pt'>\n" + 
							" <col width=84 style='mso-width-source:userset;mso-width-alt:2688;width:63pt'>\n" + 
							" <col width=72 span=2 style='width:54pt'>\n" + 
							" <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>\n" + 
							" <col width=72 span=5 style='width:54pt'>\n" + 
							" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
							"  <td colspan=15 height=36 class=xl6326225 width=1181 style='height:27.0pt;\n" + 
							"  width:886pt'>电子商务消费投诉情况统计报表</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6426225 style='height:14.25pt;border-top:none'>项目</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>登记量</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>电子商务</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>邮购</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>电话购物</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>电视购物</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>交易平台类</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>应用类</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>服务类</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>互联网门户</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>其他</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>立案处理</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>调解处理</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>没收金额</td>\n" + 
							"  <td class=xl6426225 style='border-top:none;border-left:none'>罚款金额</td>\n" + 
							" </tr>");
			for (int i = 0; i < num.size(); i++) {
				sb.append("<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6526225 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("登记量")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("电子商务")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("邮购")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("电话购物")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("电视购物")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("交易平台类")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("应用类")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("服务类")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("互联网门户")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("其他")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("立案处理")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("调解处理")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("没收金额")+"</td>\n" + 
						"  <td class=xl6626225 style='border-top:none;border-left:none'>"+num.get(i).get("罚款金额")+"</td>\n" + 
						" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downZhiLiangJianDuJuBao(List<Map> num) {
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
							"<link rel=File-List href=\"全市质量监督案件统计表（举报）.files/filelist.xml\">\n" + 
							"<style id=\"全市质量监督案件统计表（举报）_8629_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font58629\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl158629\n" + 
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
							".xl638629\n" + 
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
							".xl648629\n" + 
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
							".xl658629\n" + 
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
							".xl668629\n" + 
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
							"<div id=\"全市质量监督案件统计表（举报）_8629\" align=center x:publishsource=\"Excel\">\n" + 
							"\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=720 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:540pt'>\n" + 
							" <col width=120 span=6 style='mso-width-source:userset;mso-width-alt:3840;\n" + 
							" width:90pt'>\n" + 
							" <tr height=27 style='height:20.25pt'>\n" + 
							"  <td colspan=6 height=27 class=xl668629 width=720 style='height:20.25pt;\n" + 
							"  width:540pt'>全市质量监督案件统计表（举报）</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl638629 style='height:14.25pt;border-top:none'>产品名称</td>\n" + 
							"  <td class=xl638629 style='border-top:none;border-left:none'>申诉总数</td>\n" + 
							"  <td class=xl638629 style='border-top:none;border-left:none'>处理总数</td>\n" + 
							"  <td class=xl638629 style='border-top:none;border-left:none'>移送司法机关数</td>\n" + 
							"  <td class=xl638629 style='border-top:none;border-left:none'>涉及产品价值</td>\n" + 
							"  <td class=xl638629 style='border-top:none;border-left:none'>挽回经济损失</td>\n" + 
							" </tr>");
			for (int i = 0; i < num.size(); i++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl648629 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
								"  <td class=xl658629 style='border-top:none;border-left:none'>"+num.get(i).get("cnt")+"</td>\n" + 
								"  <td class=xl658629 style='border-top:none;border-left:none'>"+num.get(i).get("chulishu")+"</td>\n" + 
								"  <td class=xl658629 style='border-top:none;border-left:none'>"+num.get(i).get("sifa")+"</td>\n" + 
								"  <td class=xl658629 style='border-top:none;border-left:none'>"+num.get(i).get("jiazhi")+"</td>\n" + 
								"  <td class=xl658629 style='border-top:none;border-left:none'>"+num.get(i).get("sunshi")+"</td>\n" + 
								" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}

		public String downZhiLiangJianDuJuBaoShenSu(List<Map> num) {
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
							"<link rel=File-List href=\"全市质量监督案件统计表（举报及申诉）.files/filelist.xml\">\n" + 
							"<style id=\"全市质量监督案件统计表（举报及申诉）_12950_Styles\">\n" + 
							"<!--table\n" + 
							"  {mso-displayed-decimal-separator:\"\\.\";\n" + 
							"  mso-displayed-thousand-separator:\"\\,\";}\n" + 
							".font512950\n" + 
							"  {color:windowtext;\n" + 
							"  font-size:9.0pt;\n" + 
							"  font-weight:400;\n" + 
							"  font-style:normal;\n" + 
							"  text-decoration:none;\n" + 
							"  font-family:等线;\n" + 
							"  mso-generic-font-family:auto;\n" + 
							"  mso-font-charset:134;}\n" + 
							".xl1512950\n" + 
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
							".xl6312950\n" + 
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
							".xl6412950\n" + 
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
							".xl6512950\n" + 
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
							".xl6612950\n" + 
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
							"<div id=\"全市质量监督案件统计表（举报及申诉）_12950\" align=center x:publishsource=\"Excel\">\n" + 
							"<table border=0 cellpadding=0 cellspacing=0 width=513 style='border-collapse:\n" + 
							" collapse;table-layout:automatic;width:384pt'>\n" + 
							" <col width=180 style='mso-width-source:userset;mso-width-alt:5760;width:135pt'>\n" + 
							" <col width=111 span=3 style='mso-width-source:userset;mso-width-alt:3552;\n" + 
							" width:83pt'>\n" + 
							" <tr height=27 style='height:20.25pt'>\n" + 
							"  <td colspan=4 height=27 class=xl6312950 width=513 style='height:20.25pt;\n" + 
							"  width:384pt'>全市质量监督案件统计表（举报及申诉）</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6412950 style='height:14.25pt;border-top:none'>产品名称</td>\n" + 
							"  <td class=xl6412950 style='border-top:none;border-left:none'>登记量</td>\n" + 
							"  <td class=xl6412950 style='border-top:none;border-left:none'>挽回损失</td>\n" + 
							"  <td class=xl6412950 style='border-top:none;border-left:none'>受理数</td>\n" + 
							" </tr>");
			for (int i = 0; i < num.size(); i++) {
				sb.append(
						"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6512950 style='height:14.25pt;border-top:none'>"+num.get(i).get("name")+"</td>\n" + 
								"  <td class=xl6612950 style='border-top:none;border-left:none'>"+num.get(i).get("cnt")+"</td>\n" + 
								"  <td class=xl6612950 style='border-top:none;border-left:none'>"+num.get(i).get("sunshi")+"</td>\n" + 
								"  <td class=xl6612950 style='border-top:none;border-left:none'>"+num.get(i).get("shouli")+"</td>\n" + 
								" </tr>");
			}
			sb.append("</table>\n" + "</div>\n" + "</body>\n" + "</html>");
			return sb.toString();
		}
		
}
