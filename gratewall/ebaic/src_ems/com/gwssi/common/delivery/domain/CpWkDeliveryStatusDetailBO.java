package com.gwssi.common.delivery.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * CP_WK_DELIVERY_STATUS_DETAIL表对应的实体类
 */
@Entity
@Table(name = "CP_WK_DELIVERY_STATUS_DETAIL")
public class CpWkDeliveryStatusDetailBO extends AbsDaoBussinessObject {
	
	public CpWkDeliveryStatusDetailBO(){}
	
	private String detailId;	
	private String ddhm;	
	private String yjhm;	
	private String yjzt;	
	private String clsj;	
	private String cljg;	
	private String bz;	
	private String gid;	
	private Calendar timestamp;	
	
	
	@Id
	@Column(name = "DETAIL_ID")
	public String getDetailId(){
		return detailId;
	}
	public void setDetailId(String detailId){
		this.detailId = detailId;
		markChange("detailId", detailId);
	}
	@Column(name = "DDHM")
	public String getDdhm(){
		return ddhm;
	}
	public void setDdhm(String ddhm){
		this.ddhm = ddhm;
		markChange("ddhm", ddhm);
	}
	@Column(name = "YJHM")
	public String getYjhm(){
		return yjhm;
	}
	public void setYjhm(String yjhm){
		this.yjhm = yjhm;
		markChange("yjhm", yjhm);
	}
	@Column(name = "YJZT")
	public String getYjzt(){
		return yjzt;
	}
	public void setYjzt(String yjzt){
		this.yjzt = yjzt;
		markChange("yjzt", yjzt);
	}
	@Column(name = "CLSJ")
	public String getClsj(){
		return clsj;
	}
	public void setClsj(String clsj){
		this.clsj = clsj;
		markChange("clsj", clsj);
	}
	@Column(name = "CLJG")
	public String getCljg(){
		return cljg;
	}
	public void setCljg(String cljg){
		this.cljg = cljg;
		markChange("cljg", cljg);
	}
	@Column(name = "BZ")
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz = bz;
		markChange("bz", bz);
	}
	@Column(name = "GID")
	public String getGid(){
		return gid;
	}
	public void setGid(String gid){
		this.gid = gid;
		markChange("gid", gid);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	
}