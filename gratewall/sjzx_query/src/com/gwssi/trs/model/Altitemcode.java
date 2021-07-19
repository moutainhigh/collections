package com.gwssi.trs.model;

/**
 * Altitemcode枚举类 为字符串的switch
 * @author yangzihao
 */
public enum Altitemcode {
	A3,A8,A9,B2,B7,B8,B9,C3,C7,C9,D1,D2,D6,D7,D9,E3,E7,E9,F4,G4;
	
	public static Altitemcode getValue(String str){
		return valueOf(str);
	}
}
