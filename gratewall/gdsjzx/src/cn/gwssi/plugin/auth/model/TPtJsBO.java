package cn.gwssi.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_JS表对应的实体类
 */
@Entity
@Table(name = "T_PT_JS")
public class TPtJsBO extends AbsDaoBussinessObject {
	
	public TPtJsBO(){}

	private String jsId;	
	private String jsMc;	
	private String defUrl;	
	
	@Id
	@Column(name = "JS_ID")
	public String getJsId(){
		return jsId;
	}
	public void setJsId(String jsId){
		this.jsId = jsId;
		markChange("jsId", jsId);
	}
	@Column(name = "JS_MC")
	public String getJsMc(){
		return jsMc;
	}
	public void setJsMc(String jsMc){
		this.jsMc = jsMc;
		markChange("jsMc", jsMc);
	}
	@Column(name = "DEF_URL")
	public String getDefUrl(){
		return defUrl;
	}
	public void setDefUrl(String defUrl){
		this.defUrl = defUrl;
		markChange("defUrl", defUrl);
	}
}