package com.gwssi.otherselect.model;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;


/**
 * DC_BL_ENTER_BLACK_INVESTMENT表对应的实体类(黑牌企业表)
 */
@Entity
@Table(name = "ENT_SELECT")
public class BlackEntBO extends AbsDaoBussinessObject{
	public BlackEntBO(){};
	
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public String getEntname() {
		return entname;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getRegno() {
		return regno;
	}

	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}

	public String getEnttype() {
		return enttype;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCertype(String certype) {
		this.certype = certype;
	}

	public String getCertype() {
		return certype;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public String getCerno() {
		return cerno;
	}



	public void setRevdate_start(Calendar revdate_start) {
		this.revdate_start = revdate_start;
	}

	public Calendar getRevdate_start() {
		return revdate_start;
	}



	public void setRevdate_end(Calendar revdate_end) {
		this.revdate_end = revdate_end;
	}

	public Calendar getRevdate_end() {
		return revdate_end;
	}



	private String entname;
	private String regno;
	private String enttype;
	private String name;
	private String certype;
	private String cerno;
	private Calendar revdate_start;
	private Calendar revdate_end;
}
