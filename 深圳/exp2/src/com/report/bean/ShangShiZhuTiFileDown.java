package com.report.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aa_yuan_ye_shangShiZhuTi")
public class ShangShiZhuTiFileDown {
	//select t.persname,t.bgtype	 from dc_ra_mer_persons_ext t where t.id in (select id   from dc_ra_mer_invest_ext    where main_tb_id = ?     AND REGINO = ?    and exCaffsign = '1'     and invatt <> '0'), 参数：["b53c78fab61240689d65dd20e3fd28c3","21902600670"]>
	//
	private String id;
	private String createTime;
	private String fileId;

	@Column
	public String getCreateTime() {
		return createTime;
	}

	@Column
	public String getFileId() {
		return fileId;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
