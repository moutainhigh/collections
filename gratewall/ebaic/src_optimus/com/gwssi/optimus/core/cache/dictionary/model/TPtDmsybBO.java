package com.gwssi.optimus.core.cache.dictionary.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

@Entity
@Table(name="T_PT_DMSYB")
public class TPtDmsybBO extends AbsDaoBussinessObject
{
  private String dmbId;
  private String sjccLx;
  private String dmbLx;
  private String hcLx;
  private String dmbMc;
  private String dmccb;
  private String dmlMc;
  private String wblMc;
  private String fdmlMc;
  private String qthclMc;
  private String pxlMc;
  private String jxBj;
  private String bz;
  private String qyBj;
  private String ccm;
  private String sfdc;
  private String sql;

  @Id
  @Column(name="DMB_ID")
  public String getDmbId()
  {
    return this.dmbId; }

  public void setDmbId(String paramString) {
    this.dmbId = paramString;
    markChange("dmbId", paramString); }

  @Column(name="SJCC_LX")
  public String getSjccLx() {
    return this.sjccLx; }

  public void setSjccLx(String paramString) {
    this.sjccLx = paramString;
    markChange("sjccLx", paramString); }

  @Column(name="DMB_LX")
  public String getDmbLx() {
    return this.dmbLx; }

  public void setDmbLx(String paramString) {
    this.dmbLx = paramString;
    markChange("dmbLx", paramString); }

  @Column(name="HC_LX")
  public String getHcLx() {
    return this.hcLx; }

  public void setHcLx(String paramString) {
    this.hcLx = paramString;
    markChange("hcLx", paramString); }

  @Column(name="DMB_MC")
  public String getDmbMc() {
    return this.dmbMc; }

  public void setDmbMc(String paramString) {
    this.dmbMc = paramString;
    markChange("dmbMc", paramString); }

  @Column(name="DMCCB")
  public String getDmccb() {
    return this.dmccb; }

  public void setDmccb(String paramString) {
    this.dmccb = paramString;
    markChange("dmccb", paramString); }

  @Column(name="DML_MC")
  public String getDmlMc() {
    return this.dmlMc; }

  public void setDmlMc(String paramString) {
    this.dmlMc = paramString;
    markChange("dmlMc", paramString); }

  @Column(name="WBL_MC")
  public String getWblMc() {
    return this.wblMc; }

  public void setWblMc(String paramString) {
    this.wblMc = paramString;
    markChange("wblMc", paramString); }

  @Column(name="FDML_MC")
  public String getFdmlMc() {
    return this.fdmlMc; }

  public void setFdmlMc(String paramString) {
    this.fdmlMc = paramString;
    markChange("fdmlMc", paramString); }

  @Column(name="QTHCL_MC")
  public String getQthclMc() {
    return this.qthclMc; }

  public void setQthclMc(String paramString) {
    this.qthclMc = paramString;
    markChange("qthclMc", paramString); }

  @Column(name="PXL_MC")
  public String getPxlMc() {
    return this.pxlMc; }

  public void setPxlMc(String paramString) {
    this.pxlMc = paramString;
    markChange("pxlMc", paramString); }

  @Column(name="JX_BJ")
  public String getJxBj() {
    return this.jxBj; }

  public void setJxBj(String paramString) {
    this.jxBj = paramString;
    markChange("jxBj", paramString); }

  @Column(name="BZ")
  public String getBz() {
    return this.bz; }

  public void setBz(String paramString) {
    this.bz = paramString;
    markChange("bz", paramString); }

  @Column(name="QY_BJ")
  public String getQyBj() {
    return this.qyBj; }

  public void setQyBj(String paramString) {
    this.qyBj = paramString;
    markChange("qyBj", paramString); }

  @Column(name="CCM")
  public String getCcm() {
    return this.ccm; }

  public void setCcm(String paramString) {
    this.ccm = paramString;
    markChange("ccm", paramString); }

  @Column(name="SFDC")
  public String getSfdc() {
    return this.sfdc; }

  public void setSfdc(String paramString) {
    this.sfdc = paramString;
    markChange("sfdc", paramString); }

  @Column(name="SQL")
  public String getSql() {
    return this.sql; }

  public void setSql(String paramString) {
    this.sql = paramString;
    markChange("sql", paramString);
  }
}