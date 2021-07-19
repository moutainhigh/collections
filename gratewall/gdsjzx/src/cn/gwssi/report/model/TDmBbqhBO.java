package cn.gwssi.report.model;


import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_DM_BBQH表对应的实体类
 */
@Entity
@Table(name = "T_DM_BBQH")
public class TDmBbqhBO extends AbsDaoBussinessObject {
	
	public TDmBbqhBO(){}

	private String code;	
	private String value;	
	
	@Id
	@Column(name = "code")
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
		markChange("code", code);
	}
	@Column(name = "value")
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
		markChange("value", value);
	}
}