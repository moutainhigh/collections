package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.math.BigDecimal;

/**
 * T_PT_GXZYLXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_GXZYLXX")
public class TPtGxzylxxBO extends AbsDaoBussinessObject {
	
	public TPtGxzylxxBO(){}

	private String gxzylid;	
	private String tablename;	
	private String tablecode;	
	private String tablepkid;	
	private String columncode;	
	private String columnname;	
	private String fieldtype;	
	private BigDecimal fieldlength;	
	private String isflagpk;	
	
	@Id
	@Column(name = "gxzylId")
	public String getGxzylid(){
		return gxzylid;
	}
	public void setGxzylid(String gxzylid){
		this.gxzylid = gxzylid;
		markChange("gxzylid", gxzylid);
	}
	@Column(name = "tableName")
	public String getTablename(){
		return tablename;
	}
	public void setTablename(String tablename){
		this.tablename = tablename;
		markChange("tablename", tablename);
	}
	@Column(name = "tableCode")
	public String getTablecode(){
		return tablecode;
	}
	public void setTablecode(String tablecode){
		this.tablecode = tablecode;
		markChange("tablecode", tablecode);
	}
	@Column(name = "tablePkId")
	public String getTablepkid(){
		return tablepkid;
	}
	public void setTablepkid(String tablepkid){
		this.tablepkid = tablepkid;
		markChange("tablepkid", tablepkid);
	}
	@Column(name = "columnCode")
	public String getColumncode(){
		return columncode;
	}
	public void setColumncode(String columncode){
		this.columncode = columncode;
		markChange("columncode", columncode);
	}
	@Column(name = "columnName")
	public String getColumnname(){
		return columnname;
	}
	public void setColumnname(String columnname){
		this.columnname = columnname;
		markChange("columnname", columnname);
	}
	@Column(name = "fieldType")
	public String getFieldtype(){
		return fieldtype;
	}
	public void setFieldtype(String fieldtype){
		this.fieldtype = fieldtype;
		markChange("fieldtype", fieldtype);
	}
	@Column(name = "fieldLength")
	public BigDecimal getFieldlength(){
		return fieldlength;
	}
	public void setFieldlength(BigDecimal fieldlength){
		this.fieldlength = fieldlength;
		markChange("fieldlength", fieldlength);
	}
	@Column(name = "isFlagPk")
	public String getIsflagpk(){
		return isflagpk;
	}
	public void setIsflagpk(String isflagpk){
		this.isflagpk = isflagpk;
		markChange("isflagpk", isflagpk);
	}
}