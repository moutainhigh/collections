package com.gwssi.dw.runmgr.services.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtil
{
	private static final String BETWEENANDPATTERN = "^\\s*\\w+\\.\\w+\\s+(between|BETWEEN)\\s+\\?\\s+(and|AND)\\+\\?\\s*$";
	private ParamUtil(){
		
	}
	
	public static boolean isBetweenAndParam(String v){
		Pattern p = Pattern.compile(BETWEENANDPATTERN);
		Matcher m = p.matcher(v);
		
		return m.matches();
	}
	
	public static String replaceOneValue(String from, String value){
		System.out.println("replace one value: ===>"+from+" == "+value);
		return Pattern.compile("\\?").matcher(from).replaceFirst("'"+value+"'");
	}
	
	public static String replaceTwoValueParam(String from, String v1, String v2){
		String s = replaceOneValue(from, v1);
		
		return replaceOneValue(s, v2);
	}
	
	/**
	 * 
	 * @param target
	 * @param c
	 * @return String 
	 */
	public static String replaceAllRegexKeySymbol(String target, char c){
		return Pattern.compile("\\"+c).matcher(target).replaceAll("\\\\"+c);
	}
	
	public static void main(String[] args)
	{
//		System.out.println(replaceTwoValueParam("select * from aa where a.a between ? and ?", "111", "222"));
//		System.out.println(replaceAllRegexKeySymbol("aa.aa between ? ", '?'));
		String s = "select * from aa where AA.AA=? AND AA.BB>? AND AA.CC<? AND AA.DD<>? AND AA.EE BETWEEN ? AND ? AND AA.DD>? AND AA.DD<? ";
		System.out.println(s);
		System.out.println(s.replaceAll("AA.EE BETWEEN \\? AND \\?", "AA.EE BETWEEN 2008-09-22 AND 2008-09-29"));
	}
}
