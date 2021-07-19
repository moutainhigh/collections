package cn.gwssi.trs.model;

import java.util.Map;

public class TrsEntity {
	
	//记录号
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer("TrsEntity [regNo=").append(regNo);
		sbf.append(", id=").append(id);
		sbf.append(", priPid=").append(priPid);
		//sbf.append(", estDate=").append(estDate.getTRSFormat().replace(".", "-"));
		sbf.append(", estDate=").append(estDate);
		sbf.append(", entName=").append(entName);
		sbf.append(", entType=").append(entType);
		sbf.append(", dom=").append(dom);
		sbf.append(", addr=").append(addr);
		sbf.append(", industryPhy=").append(industryPhy);
		sbf.append(", regOrg=").append(regOrg);
		sbf.append(", opState=").append(opState);
		sbf.append(", opScope=").append(opScope);
		sbf.append(", entState=").append(entState);
		sbf.append(", leRep=").append(leRep);
		sbf.append(", postalCode=").append(postalCode);
		sbf.append(", tel=").append(tel);
		sbf.append(", opScoAndForm=").append(opScoAndForm);
		sbf.append(", localAdm=").append(localAdm);
		sbf.append(", insAuth=").append(insAuth);
		sbf.append(", oldRegNo=").append(oldRegNo);
		sbf.append(", inv=").append(inv);
		sbf.append(", cerNo=").append(cerNo);
		sbf.append(", url=").append(url);
		sbf.append(", industryCo=").append(industryCo);
		if(map!=null){
			for (String key : map.keySet()) {
				//System.out.println("key= "+ key + " and value= " + map.get(key));
				sbf.append(", ").append(key).append("=").append(map.get(key));
			}
		}
		sbf.append("]");
		System.out.println(sbf.toString()+"------");
		return   sbf.toString();
	}
	
	private String id;
	private String priPid;
	private String regNo;
	//private Date estDate;
	private String estDate;
	private String entNameColor;
	private String entName;
	private String entType;
	private String dom;
	private String addr;
	private String industryPhy;
	private String regOrg;
	private String opState;
	private String opScope;
	private String entState;
	private String leRep;
	private String postalCode;
	private String tel;
	private String opScoAndForm;
	private String localAdm;
	private String insAuth;
	private String oldRegNo;
	private String inv;
	private String cerNo;
	private String url;
	private String industryCo;
	private String empNum;
	private String sourceFlag;
	private String bgsx;
	private String uniscid;
	
	private Map<String,String> map;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPriPid() {
		return priPid;
	}

	public void setPriPid(String priPid) {
		this.priPid = priPid;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getEstDate() {
		return estDate;
	}

	public void setEstDate(String estDate) {
		this.estDate = estDate;
	}

	public String getEntNameColor() {
		return entNameColor;
	}

	public void setEntNameColor(String entNameColor) {
		this.entNameColor = entNameColor;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntType() {
		return entType;
	}

	public void setEntType(String entType) {
		this.entType = entType;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIndustryPhy() {
		return industryPhy;
	}

	public void setIndustryPhy(String industryPhy) {
		this.industryPhy = industryPhy;
	}

	public String getRegOrg() {
		return regOrg;
	}

	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}

	public String getOpState() {
		return opState;
	}

	public void setOpState(String opState) {
		this.opState = opState;
	}

	public String getOpScope() {
		return opScope;
	}

	public void setOpScope(String opScope) {
		this.opScope = opScope;
	}

	public String getEntState() {
		return entState;
	}

	public void setEntState(String entState) {
		this.entState = entState;
	}

	public String getLeRep() {
		return leRep;
	}

	public void setLeRep(String leRep) {
		this.leRep = leRep;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOpScoAndForm() {
		return opScoAndForm;
	}

	public void setOpScoAndForm(String opScoAndForm) {
		this.opScoAndForm = opScoAndForm;
	}

	public String getLocalAdm() {
		return localAdm;
	}

	public void setLocalAdm(String localAdm) {
		this.localAdm = localAdm;
	}

	public String getInsAuth() {
		return insAuth;
	}

	public void setInsAuth(String insAuth) {
		this.insAuth = insAuth;
	}

	public String getOldRegNo() {
		return oldRegNo;
	}

	public void setOldRegNo(String oldRegNo) {
		this.oldRegNo = oldRegNo;
	}

	public String getInv() {
		return inv;
	}

	public void setInv(String inv) {
		this.inv = inv;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIndustryCo() {
		return industryCo;
	}

	public void setIndustryCo(String industryCo) {
		this.industryCo = industryCo;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getBgsx() {
		return bgsx;
	}

	public void setBgsx(String bgsx) {
		this.bgsx = bgsx;
	}

	public String getUniscid() {
		return uniscid;
	}

	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}