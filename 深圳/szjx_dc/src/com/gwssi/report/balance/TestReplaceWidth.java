package com.gwssi.report.balance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestReplaceWidth {

	
	public static void main(String[] args) {
		/*String s = "<style>table {width:100px}</style><html><body><table width=\"100\" style=\"width: 75pt;\"></table><body></html>";
		String s1 = "<table width=\"100\" style=\"width: 75pt;\">";
		String regex = "<a.*href=\".*\">(.+?)</a>";
		String regex1 = "<table.*width=\".*\"*style=\".*\">";
		System.out.println(s.replaceAll(regex1, "<table>"));
		System.out.println(s.matches(regex1));*/
//		String s = "个体1表    的咖啡店经费";
//		System.out.println(Arrays.toString(s.split("\\s+")));
//		String str = "12+ 3+ 3>=4+532-2";
		String str = "(23,43)+(21,4)>='500'";
		//String str = "xxx第47297章33";
	//	String regex = "\\d*";
		String regex1 = "[+-/*/>=<!']*";
		String regex2 = "\\d*";
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(str);
		while (m.find()) {
			if (!"".equals(m.group()))
				System.out.println("come here:" + m.group());
		}
	}
}
