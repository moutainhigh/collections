package com.gwssi.pojo;

/**
 * 用户信息
 * 
 * @author Administrator
 *
 */
public class User {

	private String id; // 每个银行的系统id主键
	private String username; // 用户名
	private String password; // 密码
	private String bankname; // 银行的名称
	private String bankaddress; // 银行的地址
	private String remark; // 备注信息
	private Integer orderNo; // 序号排序
	private String createId; // 创建人的id
	private String createName; // 创建人的姓名
	private String createTime; // 创建人的时间
	private String modifierId; // 修改人的id
	private String modifierName; // 最后修改人的姓名
	private String modifierTime; // 最后修改的时间
	private String phone; // 电话
	private String fax; // 传真
	private String flag; // 状态，1-启用，0-停用
	private String ipset; // 存储具有访问权限的用户ip的集合
	private String keys; // 每个银行访问的密钥

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankaddress() {
		return bankaddress;
	}

	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(String modifierTime) {
		this.modifierTime = modifierTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIpset() {
		return ipset;
	}

	public void setIpset(String ipset) {
		this.ipset = ipset;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", bankname=" + bankname
				+ ", bankaddress=" + bankaddress + ", remark=" + remark + ", orderNo=" + orderNo + ", createId="
				+ createId + ", createName=" + createName + ", createTime=" + createTime + ", modifierId=" + modifierId
				+ ", modifierName=" + modifierName + ", modifierTime=" + modifierTime + ", phone=" + phone + ", fax="
				+ fax + ", flag=" + flag + ", ipset=" + ipset + "]";
	}

}
