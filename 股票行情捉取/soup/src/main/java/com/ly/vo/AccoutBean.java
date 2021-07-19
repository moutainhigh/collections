package com.ly.vo;

import java.util.HashSet;
import java.util.Set;

public class AccoutBean {
	private String aid;
	private String account;
	private String name;
	private String isWeiTuo;
	private Set opts = new HashSet();
	
	public AccoutBean() {
		super();
	}
	
	
	public AccoutBean(String aid, String account, String name, String isWeiTuo, Set opts) {
		super();
		String str = "****";//301819248683
		StringBuilder sb = new StringBuilder(account);
		sb.replace(4, 8, str);
		this.aid = aid;
		this.account = sb.toString();
		this.name = name;
		this.isWeiTuo = isWeiTuo;
		this.opts = opts;
	}
	
	/*public AccoutBean(String aid, String account, String name, String isWeiTuo) {
		super();
		String str = "****";//301819248683
		StringBuilder sb = new StringBuilder(account);
		sb.replace(4, 8, str);
		this.aid = aid;
		this.account = sb.toString();
		this.name = name;
		this.isWeiTuo = isWeiTuo;
	}*/
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsWeiTuo() {
		return isWeiTuo;
	}
	public void setIsWeiTuo(String isWeiTuo) {
		this.isWeiTuo = isWeiTuo;
	}

}
