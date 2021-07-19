package cn.gwssi.trs.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWDXJBXX表对应的实体类
 */
@Entity
@Table(name = "T_TASK_MEASURETOHTML")
public class TTaskMeasuretohtmlBO extends AbsDaoBussinessObject {
	
	public TTaskMeasuretohtmlBO(){}

	private String transdt;	
	private String regorg;	
	private Double valueioc;      
	private Double valuemission; 
	private Double valuesum; 
	
	@Column(name = "transdt")
	public String getTransdt(){
		return transdt;
	}
	public void setTransdt(String transdt){
		this.transdt = transdt;
		markChange("transdt", transdt);
	}
	
	@Column(name = "regorg")
	public String getRegorg() {
		return regorg;
	}
	public void setRegorg(String regorg) {
		this.regorg = regorg;
		markChange("regorg", regorg);
	}
	
	@Column(name = "valueioc")
	public Double getValueioc() {
		return valueioc;
	}
	public void setValueioc(Double valueioc) {
		this.valueioc = valueioc;
		markChange("valueioc", valueioc);
	}
	
	@Column(name = "valuemission")
	public Double getValuemission() {
		return valuemission;
	}
	public void setValuemission(Double valuemission) {
		this.valuemission = valuemission;
		markChange("valuemission", valuemission);
	}
	
	@Column(name = "valuesum")
	public Double getValuesum() {
		return valuesum;
	}
	public void setValuesum(Double valuesum) {
		this.valuesum = valuesum;
		markChange("valuesum", valuesum);
	}
	
}