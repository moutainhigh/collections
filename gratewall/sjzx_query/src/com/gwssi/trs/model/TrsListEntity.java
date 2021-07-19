package com.gwssi.trs.model;

import java.util.Map;

public class TrsListEntity {
	
	private String pid_pripid; //主键
	private String a_entName;//企业名称 对应trs a
	private String b_regno; //注册号  对应 b
	private String c_uniscid ;//统一社会信用代码 对应c
	private String d_regstate; //登记状态 对应 d
	private String d_cn_regstate; //登记状态中文
	private String e_industryphy; //行业门类
	private String e_cn_industryphy;//行业门类中文
	private String f_industryco;//行业代码
	private String f_cn_industryco;//行业代码中文
	private String g_name;//法定代表人
	private String h_estdate;//成立日期
	private String i_regorg; //登记机关
	private String i_cn_regorg;//等级机关中文
	
	private String j;//该项为空
	private	String k_opscope;//经营范围
	private String l_dom;//住址
	private String m; //主题？ reg 写死
	private String n_opetype;//opetype
	private	String o;//同步时间
	private	String p;//同步时间	
	private	String q;//同步时间
	private	String r_enttype;//企业类型；
	private String r_cn_enttype;//企业类型中文
	private	String s_entid;
	private String url; //跳转url参数
	private String changeType;//转换后类型
	
	
	private Map<String,String> map;

	//需要拼接的url 访问路径
	private Map<String,String> SearchValue;
	
	public TrsListEntity(){}
	
	

	public String getChangeType() {
		return changeType;
	}



	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getPid_pripid() {
		return pid_pripid;
	}

	public void setPid_pripid(String pid_pripid) {
		this.pid_pripid = pid_pripid;
	}

	public String getA_entName() {
		return a_entName;
	}

	public void setA_entName(String a_entName) {
		this.a_entName = a_entName;
	}

	public String getB_regno() {
		return b_regno;
	}

	public void setB_regno(String b_regno) {
		this.b_regno = b_regno;
	}

	public String getC_uniscid() {
		return c_uniscid;
	}

	public void setC_uniscid(String c_uniscid) {
		this.c_uniscid = c_uniscid;
	}

	public String getD_regstate() {
		return d_regstate;
	}

	public void setD_regstate(String d_regstate) {
		this.d_regstate = d_regstate;
	}

	public String getE_industryphy() {
		return e_industryphy;
	}

	public void setE_industryphy(String e_industryphy) {
		this.e_industryphy = e_industryphy;
	}

	public String getE_cn_industryphy() {
		return e_cn_industryphy;
	}

	public void setE_cn_industryphy(String e_cn_industryphy) {
		this.e_cn_industryphy = e_cn_industryphy;
	}

	public String getF_industryco() {
		return f_industryco;
	}

	public void setF_industryco(String f_industryco) {
		this.f_industryco = f_industryco;
	}

	public String getF_cn_industryco() {
		return f_cn_industryco;
	}

	public void setF_cn_industryco(String f_cn_industryco) {
		this.f_cn_industryco = f_cn_industryco;
	}

	public String getG_name() {
		return g_name;
	}

	public void setG_name(String g_name) {
		this.g_name = g_name;
	}

	public String getH_estdate() {
		return h_estdate;
	}

	public void setH_estdate(String h_estdate) {
		this.h_estdate = h_estdate;
	}

	public String getI_regorg() {
		return i_regorg;
	}

	public void setI_regorg(String i_regorg) {
		this.i_regorg = i_regorg;
	}

	public String getI_cn_regorg() {
		return i_cn_regorg;
	}

	public void setI_cn_regorg(String i_cn_regorg) {
		this.i_cn_regorg = i_cn_regorg;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public String getK_opscope() {
		return k_opscope;
	}

	public void setK_opscope(String k_opscope) {
		this.k_opscope = k_opscope;
	}

	public String getL_dom() {
		return l_dom;
	}

	public void setL_dom(String l_dom) {
		this.l_dom = l_dom;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
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



	public void setD_cn_regstate(String d_cn_regstate) {
		this.d_cn_regstate = d_cn_regstate;
	}



	public String getD_cn_regstate() {
		return d_cn_regstate;
	}



	public void setN_opetype(String n_opetype) {
		this.n_opetype = n_opetype;
	}



	public String getN_opetype() {
		return n_opetype;
	}



	public void setS_entid(String s_entid) {
		this.s_entid = s_entid;
	}



	public String getS_entid() {
		return s_entid;
	}

}