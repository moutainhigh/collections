package com.ly.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

//https://blog.csdn.net/wh_forever/article/details/45914231

@Entity
@DynamicUpdate(true)
@Table(indexes = { @Index(name = "aid", columnList = "aid") })
public class Account implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String aid;
	private String account;
	private String name;
	private String tokenId;
	private String appName;
	private String updateTime;
	private String isWeiTuo;
	private String szAccount;
	private String shAccount;
	private String keyong;
	
	
	@Column
	public String getKeyong() {
		return keyong;
	}



	public void setKeyong(String keyong) {
		this.keyong = keyong;
	}

	//https://edision.iteye.com/blog/2213424
	private List<Account_Operation> options = new ArrayList<Account_Operation>(0);
	
	private List<Account_Ji_Jing> optionsJiJing = new ArrayList<Account_Ji_Jing>(0);
	
	
	

	public Account() {
		super();
	}

	

	@Column
	public String getAccount() {
		return account;
	}

	@Id
	public String getAid() {
		return aid;
	}

	@Column
	public String getAppName() {
		return appName;
	}

	@Column
	public String getIsWeiTuo() {
		return isWeiTuo;
	}

	@Column
	public String getName() {
		return name;
	}

	@Column
	public String getShAccount() {
		return shAccount;
	}

	@Column
	public String getSzAccount() {
		return szAccount;
	}

	@Column
	public String getTokenId() {
		return tokenId;
	}

	@Column
	public String getUpdateTime() {
		return updateTime;
	}

	// https://www.cnblogs.com/darkchii/p/8758960.html
	//https://blog.csdn.net/kd_bright/article/details/79528818
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	public List<Account_Operation> getOptions() {
		return options;
	}
	

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	public List<Account_Ji_Jing> getOptionsJiJing() {
		return optionsJiJing;
	}



	public void setOptionsJiJing(List<Account_Ji_Jing> optionsJiJing) {
		this.optionsJiJing = optionsJiJing;
	}



	public void setAccount(String account) {
		this.account = account;
	}

	public void setOptions(List<Account_Operation> options) {
		this.options = options;
	}



	public void setAid(String aid) {
		this.aid = aid;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setIsWeiTuo(String isWeiTuo) {
		this.isWeiTuo = isWeiTuo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShAccount(String shAccount) {
		this.shAccount = shAccount;
	}

	public void setSzAccount(String szAccount) {
		this.szAccount = szAccount;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Account [aid=" + aid + ", account=" + account + ", name=" + name + ", tokenId=" + tokenId + ", appName="
				+ appName + ", updateTime=" + updateTime + ", isWeiTuo=" + isWeiTuo + ", szAccount=" + szAccount
				+ ", shAccount=" + shAccount + ", options=" + options + "]";
	}

}
