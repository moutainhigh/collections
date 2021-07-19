package com.gwssi.trs.model;

import java.util.Date;
import java.util.Map;
//案件主题的实体类

public class TrscacheEntity {
	private String pid; //主键
	private String a;//案件名称
	private String b; //案件编号
	private String b_;//案件编号代码
	private String c; //案件状态
	private String d; //立案机关
	private String d_;//立案机关代码
	private String e;//立案日期
	private String f;//销案日期
	private String g;//当事人类型
	private String h;//当事人名称
	private String i; //注册号
	private String j;//统一社会信用代码
	
	private String k;//证件号码
	private	Date o;
	private Date p;
	private Date q;
	private String url; //跳转url参数
	private String changeType;//转换后类型
	private	String r_enttype;//企业类型；
	private String r_cn_enttype;//企业类型中文
	private String w;//案件的判断
	
	
	private Map<String,String> map;

	//需要拼接的url 访问路径
	private Map<String,String> SearchValue;
	
	public TrscacheEntity(){}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getB_() {
		return b_;
	}
	public void setB_(String b_) {
		this.b_ = b_;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public String getD_() {
		return d_;
	}
	public void setD_(String d_) {
		this.d_ = d_;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getG() {
		return g;
	}
	public void setG(String g) {
		this.g = g;
	}
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i = i;
	}
	public String getJ() {
		return j;
	}
	public void setJ(String j) {
		this.j = j;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public Date getO() {
		return o;
	}
	public void setO(Date o) {
		this.o = o;
	}
	public Date getP() {
		return p;
	}
	public void setP(Date p) {
		this.p = p;
	}
	public Date getQ() {
		return q;
	}
	public void setQ(Date q) {
		this.q = q;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public Map<String, String> getSearchValue() {
		return SearchValue;
	}
	public void setSearchValue(Map<String, String> searchValue) {
		SearchValue = searchValue;
	}
	public String getR_enttype() {
		return r_enttype;
	}
	public void setR_enttype(String r_enttype) {
		this.r_enttype = r_enttype;
	}
	public String getR_cn_enttype() {
		return r_cn_enttype;
	}
	public void setR_cn_enttype(String r_cn_enttype) {
		this.r_cn_enttype = r_cn_enttype;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	
	
	
	
	
	
}