package com.gwssi.datacenter.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.annotation.Lob;

/**
 * DC_STANDARD_CODEDATA表对应的实体类
 */
@Entity
@Table(name = "DC_STANDARD_CODEDATA")
public class DcStandardCodedataBO extends AbsDaoBussinessObject {
	
	public DcStandardCodedataBO(){}

	private String standardCodeindex;	
	private String codeindexCode;	
	private String codeindexValue;	
	private String illustrate;	
	
	@Id
	
	@Column(name = "STANDARD_CODEINDEX")
	public String getStandardCodeindex(){
		return standardCodeindex;
	}
	public void setStandardCodeindex(String standardCodeindex){
		this.standardCodeindex = standardCodeindex;
		markChange("standardCodeindex", standardCodeindex);
	}
	@Column(name = "CODEINDEX_CODE")
	public String getCodeindexCode(){
		return codeindexCode;
	}
	public void setCodeindexCode(String codeindexCode){
		this.codeindexCode = codeindexCode;
		markChange("codeindexCode", codeindexCode);
	}
	@Column(name = "CODEINDEX_VALUE")
	public String getCodeindexValue(){
		return codeindexValue;
	}
	public void setCodeindexValue(String codeindexValue){
		this.codeindexValue = codeindexValue;
		markChange("codeindexValue", codeindexValue);
	}
	@Column(name = "ILLUSTRATE")
	public String getIllustrate(){
		return illustrate;
	}
	public void setIllustrate(String illustrate){
		this.illustrate = illustrate;
		markChange("illustrate", illustrate);
	}
}