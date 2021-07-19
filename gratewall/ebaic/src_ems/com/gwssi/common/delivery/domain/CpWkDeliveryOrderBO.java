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
 * CP_WK_DELIVERY_ORDER表对应的实体类
 */
@Entity
@Table(name = "CP_WK_DELIVERY_ORDER")
public class CpWkDeliveryOrderBO extends AbsDaoBussinessObject {
	
	public CpWkDeliveryOrderBO(){}

	private String orderId;
	private String ddhm;	
	private String ddmc;	
	private String ddywlx;	
	private String jjrXm;	
	private String jjrDw;	
	private String jjrDz;	
	private String jjrDh;	
	private String sfdYb;	
	private String sfdCsdm;	
	private String sjrXm;	
	private String sjrDw;	
	private String sjrDz;	
	private String sjrDh;	
	private String sjrSj;	
	private String jddYb;	
	private String jddCsdm;	
	private String dshk;	
	private String psyxj;	
	private String ghsMc;	
	private String ghsDz;	
	private String ghsDh;	
	private String bz;	
	private String zt;	
	private String gid;	
	private Calendar timestamp;	
	private String sentStatus;	
	private String fetchStatus;	
	private String ddzt;
	private String ddsljg;
	private String smqf;
	private String jjrSj;
	private String dsje;
	private String yjhm;
	private String kdgs;
	private String deliveryFrom;
	private String ylzd1;
	private String ylzd2;
	private String ylzd3;
	private String ylzd4;
	private String ylzd5;
	@Id
	@Column(name = "ORDER_ID")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
		markChange("orderId", orderId);
	}
	
	@Column(name = "DDHM")
	public String getDdhm(){
		return ddhm;
	}
	public void setDdhm(String ddhm){
		this.ddhm = ddhm;
		markChange("ddhm", ddhm);
	}
	
	//TODO EMS 增加数据库字段
	@Column(name = "YJHM")
	public String getYjhm(){
		return yjhm;
	}
	public void setYjhm(String yjhm){
		this.yjhm = yjhm;
		markChange("yjhm", yjhm);
	}
	
	@Column(name = "DDMC")
	public String getDdmc(){
		return ddmc;
	}
	public void setDdmc(String ddmc){
		this.ddmc = ddmc;
		markChange("ddmc", ddmc);
	}
	@Column(name = "DDYWLX")
	public String getDdywlx(){
		return ddywlx;
	}
	public void setDdywlx(String ddywlx){
		this.ddywlx = ddywlx;
		markChange("ddywlx", ddywlx);
	}
	@Column(name = "JJR_XM")
	public String getJjrXm(){
		return jjrXm;
	}
	public void setJjrXm(String jjrXm){
		this.jjrXm = jjrXm;
		markChange("jjrXm", jjrXm);
	}
	@Column(name = "JJR_DW")
	public String getJjrDw(){
		return jjrDw;
	}
	public void setJjrDw(String jjrDw){
		this.jjrDw = jjrDw;
		markChange("jjrDw", jjrDw);
	}
	@Column(name = "JJR_DZ")
	public String getJjrDz(){
		return jjrDz;
	}
	public void setJjrDz(String jjrDz){
		this.jjrDz = jjrDz;
		markChange("jjrDz", jjrDz);
	}
	@Column(name = "JJR_DH")
	public String getJjrDh(){
		return jjrDh;
	}
	public void setJjrDh(String jjrDh){
		this.jjrDh = jjrDh;
		markChange("jjrDh", jjrDh);
	}
	@Column(name = "SFD_YB")
	public String getSfdYb(){
		return sfdYb;
	}
	public void setSfdYb(String sfdYb){
		this.sfdYb = sfdYb;
		markChange("sfdYb", sfdYb);
	}
	@Column(name = "SFD_CSDM")
	public String getSfdCsdm(){
		return sfdCsdm;
	}
	public void setSfdCsdm(String sfdCsdm){
		this.sfdCsdm = sfdCsdm;
		markChange("sfdCsdm", sfdCsdm);
	}
	@Column(name = "SJR_XM")
	public String getSjrXm(){
		return sjrXm;
	}
	public void setSjrXm(String sjrXm){
		this.sjrXm = sjrXm;
		markChange("sjrXm", sjrXm);
	}
	@Column(name = "SJR_DW")
	public String getSjrDw(){
		return sjrDw;
	}
	public void setSjrDw(String sjrDw){
		this.sjrDw = sjrDw;
		markChange("sjrDw", sjrDw);
	}
	@Column(name = "SJR_DZ")
	public String getSjrDz(){
		return sjrDz;
	}
	public void setSjrDz(String sjrDz){
		this.sjrDz = sjrDz;
		markChange("sjrDz", sjrDz);
	}
	@Column(name = "SJR_DH")
	public String getSjrDh(){
		return sjrDh;
	}
	public void setSjrDh(String sjrDh){
		this.sjrDh = sjrDh;
		markChange("sjrDh", sjrDh);
	}
	@Column(name = "SJR_SJ")
	public String getSjrSj(){
		return sjrSj;
	}
	public void setSjrSj(String sjrSj){
		this.sjrSj = sjrSj;
		markChange("sjrSj", sjrSj);
	}
	@Column(name = "JDD_YB")
	public String getJddYb(){
		return jddYb;
	}
	public void setJddYb(String jddYb){
		this.jddYb = jddYb;
		markChange("jddYb", jddYb);
	}
	@Column(name = "JDD_CSDM")
	public String getJddCsdm(){
		return jddCsdm;
	}
	public void setJddCsdm(String jddCsdm){
		this.jddCsdm = jddCsdm;
		markChange("jddCsdm", jddCsdm);
	}
	@Column(name = "DSHK")
	public String getDshk(){
		return dshk;
	}
	public void setDshk(String dshk){
		this.dshk = dshk;
		markChange("dshk", dshk);
	}
	@Column(name = "PSYXJ")
	public String getPsyxj(){
		return psyxj;
	}
	public void setPsyxj(String psyxj){
		this.psyxj = psyxj;
		markChange("psyxj", psyxj);
	}
	@Column(name = "GHS_MC")
	public String getGhsMc(){
		return ghsMc;
	}
	public void setGhsMc(String ghsMc){
		this.ghsMc = ghsMc;
		markChange("ghsMc", ghsMc);
	}
	@Column(name = "GHS_DZ")
	public String getGhsDz(){
		return ghsDz;
	}
	public void setGhsDz(String ghsDz){
		this.ghsDz = ghsDz;
		markChange("ghsDz", ghsDz);
	}
	@Column(name = "GHS_DH")
	public String getGhsDh(){
		return ghsDh;
	}
	public void setGhsDh(String ghsDh){
		this.ghsDh = ghsDh;
		markChange("ghsDh", ghsDh);
	}
	@Column(name = "BZ")
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz = bz;
		markChange("bz", bz);
	}
	@Column(name = "ZT")
	public String getZt(){
		return zt;
	}
	public void setZt(String zt){
		this.zt = zt;
		markChange("zt", zt);
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
	@Column(name = "SENT_STATUS")
	public String getSentStatus(){
		return sentStatus;
	}
	public void setSentStatus(String sentStatus){
		this.sentStatus = sentStatus;
		markChange("sentStatus", sentStatus);
	}
	@Column(name = "FETCH_STATUS")
	public String getFetchStatus(){
		return fetchStatus;
	}
	public void setFetchStatus(String fetchStatus){
		this.fetchStatus = fetchStatus;
		markChange("fetchStatus", fetchStatus);
	}
	@Column(name = "KDGS")
	public String getKdgs(){
		return kdgs;
	}
	public void setKdgs(String kdgs){
		this.kdgs = kdgs;
		markChange("kdgs", kdgs);
	}
	@Column(name = "DDZT")
	public String getDdzt() {
		return ddzt;
	}
	public void setDdzt(String ddzt) {
		this.ddzt = ddzt;
		markChange("ddzt", ddzt);
	}
	@Column(name = "DDSLJG")
	public String getDdsljg() {
		return ddsljg;
	}
	public void setDdsljg(String ddsljg) {
		this.ddsljg = ddsljg;
		markChange("ddsljg", ddsljg);
	}
	@Column(name = "SMQF")
	public String getSmqf() {
		return smqf;
	}
	public void setSmqf(String smqf) {
		this.smqf = smqf;
		markChange("smqf", smqf);
	}
	@Column(name = "JJR_SJ")
	public String getJjrSj() {
		return jjrSj;
	}
	public void setJjrSj(String jjrSj) {
		this.jjrSj = jjrSj;
		markChange("jjrSj", jjrSj);
	}
	@Column(name = "DSJE")
	public String getDsje() {
		return dsje;
	}
	public void setDsje(String dsje) {
		this.dsje = dsje;
		markChange("dsje", dsje);
	}
	@Column(name = "DELIVERY_FROM")
	public String getDeliveryFrom() {
		return deliveryFrom;
	}
	public void setDeliveryFrom(String deliveryFrom) {
		this.deliveryFrom = deliveryFrom;
		markChange("deliveryFrom", deliveryFrom);
	}
	@Column(name = "YLZD1")
	public String getYlzd1() {
		return ylzd1;
	}
	public void setYlzd1(String ylzd1) {
		this.ylzd1 = ylzd1;
		markChange("ylzd1", ylzd1);
	}
	@Column(name = "YLZD2")
	public String getYlzd2() {
		return ylzd2;
	}
	public void setYlzd2(String ylzd2) {
		this.ylzd2 = ylzd2;
		markChange("ylzd2", ylzd2);
	}
	@Column(name = "YLZD3")
	public String getYlzd3() {
		return ylzd3;
	}
	public void setYlzd3(String ylzd3) {
		this.ylzd3 = ylzd3;
		markChange("ylzd3", ylzd3);
	}
	@Column(name = "YLZD4")
	public String getYlzd4() {
		return ylzd4;
	}
	public void setYlzd4(String ylzd4) {
		this.ylzd4 = ylzd4;
		markChange("ylzd4", ylzd4);
	}
	@Column(name = "YLZD5")
	public String getYlzd5() {
		return ylzd5;
	}
	public void setYlzd5(String ylzd5) {
		this.ylzd5 = ylzd5;
		markChange("ylzd5", ylzd5);
	}
}