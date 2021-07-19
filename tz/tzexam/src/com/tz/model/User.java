package com.tz.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TzUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tz_user")
public class User implements java.io.Serializable {

	/*主键*/
	private Integer id;
	//用户名
	private String username;
	//密码
	private String password;
	//账号
	private String account;
	//手机
	private String telephone;
	//身份证
	private String idcard;
	//性别：0女1男
	private Integer male;
	//生日
	private Date birthday;
	//邮箱
	private String email;
	//地址
	private String address;
	//年龄
	private Integer age;
	//创建时间
	private Date createTime;
	//删除状态0未删除1删除
	private Integer isDelete;
	//更新时间
	private Date updateTime;
	//报名老师
	private Integer teacherId;
	//学科ID
	private Integer subjectId;
	//状态 0未发布1发布
	private Integer status;
	//1老师2学生
	private Integer type;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Date createTime) {
		this.createTime = createTime;
	}

	/** full constructor */
	public User(String username, String password, String account,
			String telephone, String idcard, Date createTime, Integer isDelete,
			Integer male, Date birthday, String email, String address,
			Date updateTime, Integer teacherId, Integer subjectId, Integer age,
			Integer status, Integer type) {
		this.username = username;
		this.password = password;
		this.account = account;
		this.telephone = telephone;
		this.idcard = idcard;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.male = male;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.updateTime = updateTime;
		this.teacherId = teacherId;
		this.subjectId = subjectId;
		this.age = age;
		this.status = status;
		this.type = type;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", length = 80)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 80)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "account", length = 60)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "telephone", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "idcard", length = 20)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_delete")
	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "male")
	public Integer getMale() {
		return this.male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "teacher_id")
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "subject_id")
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}