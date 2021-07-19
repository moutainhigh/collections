package com.gwssi.optimus.core.cache.dictionary.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.math.BigDecimal;

@Entity
@Table(name="T_PT_DMSJB")
public class TPtDmsjbBO extends AbsDaoBussinessObject
{
  private String sjId;
  private String dmbId;
  private String dm;
  private String fdm;
  private String wb;
  private String qyBj;
  private BigDecimal xh;
  private String bz;
  private String cj;

  @Id
  @Column(name="SJ_ID")
  public String getSjId()
  {
    return this.sjId; }

  public void setSjId(String paramString) {
    this.sjId = paramString;
    markChange("sjId", paramString); }

  @Column(name="DMB_ID")
  public String getDmbId() {
    return this.dmbId; }

  public void setDmbId(String paramString) {
    this.dmbId = paramString;
    markChange("dmbId", paramString); }

  @Column(name="DM")
  public String getDm() {
    return this.dm; }

  public void setDm(String paramString) {
    this.dm = paramString;
    markChange("dm", paramString); }

  @Column(name="FDM")
  public String getFdm() {
    return this.fdm; }

  public void setFdm(String paramString) {
    this.fdm = paramString;
    markChange("fdm", paramString); }

  @Column(name="WB")
  public String getWb() {
    return this.wb; }

  public void setWb(String paramString) {
    this.wb = paramString;
    markChange("wb", paramString); }

  @Column(name="QY_BJ")
  public String getQyBj() {
    return this.qyBj; }

  public void setQyBj(String paramString) {
    this.qyBj = paramString;
    markChange("qyBj", paramString); }

  @Column(name="XH")
  public BigDecimal getXh() {
    return this.xh; }

  public void setXh(BigDecimal paramBigDecimal) {
    this.xh = paramBigDecimal;
    markChange("xh", paramBigDecimal); }

  @Column(name="BZ")
  public String getBz() {
    return this.bz; }

  public void setBz(String paramString) {
    this.bz = paramString;
    markChange("bz", paramString); }

  @Column(name="CJ")
  public String getCj() {
    return this.cj; }

  public void setCj(String paramString) {
    this.cj = paramString;
    markChange("cj", paramString);
  }
}