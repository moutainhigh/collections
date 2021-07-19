package cn.gwssi.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.annotation.Lob;

/**
 * T_PT_URLZY表对应的实体类
 */
@Entity
@Table(name = "T_PT_URLZY")
public class TPtUrlzyBO extends AbsDaoBussinessObject {
	
	public TPtUrlzyBO(){}

	private String urlId;	
	private String gnId;	
	private String urlMc;	
	private String url;	
	private String lx;	
	private String sfjqlj;	
	
	@Id
	@Column(name = "URL_ID")
	public String getUrlId(){
		return urlId;
	}
	public void setUrlId(String urlId){
		this.urlId = urlId;
		markChange("urlId", urlId);
	}
	@Column(name = "GN_ID")
	public String getGnId(){
		return gnId;
	}
	public void setGnId(String gnId){
		this.gnId = gnId;
		markChange("gnId", gnId);
	}
	@Column(name = "URL_MC")
	public String getUrlMc(){
		return urlMc;
	}
	public void setUrlMc(String urlMc){
		this.urlMc = urlMc;
		markChange("urlMc", urlMc);
	}
	@Column(name = "URL")
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
		markChange("url", url);
	}
	@Column(name = "LX")
	public String getLx(){
		return lx;
	}
	public void setLx(String lx){
		this.lx = lx;
		markChange("lx", lx);
	}
	@Column(name = "SFJQLJ")
	public String getSfjqlj(){
		return sfjqlj;
	}
	public void setSfjqlj(String sfjqlj){
		this.sfjqlj = sfjqlj;
		markChange("sfjqlj", sfjqlj);
	}
}