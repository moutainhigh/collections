package com.yunpan.bean;

import java.util.Date;

/**
 * 
 * 用户bean User<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月27日-下午10:57:32 <BR>
 * 
 * @version 1.0.0
 * 
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// 主键id
	private Integer id;
	// 用户账号
	private String account;
	// 用户昵称
	private String username;
	// 用户密码
	private String password;
	// 创建时间
	private Date createtime;
	// 更新时间
	private Date updatetime;
	// 删除状态 0未删除1删除
	private Integer isDelete;

	public User() {

	}

	public User(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
