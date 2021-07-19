/**
 * SWDJInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.in.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.webservices.localtax.in.TaxRegChgProcess;

public class SWDJInfo implements java.io.Serializable
{
	private static Log log =  LogFactory.getLog(SWDJInfo.class);
	
	private java.lang.String	DJZCLXDM;

	private java.lang.String	SWDJLX;

	private java.lang.String	FRXM;

	private java.lang.String	RQ;

	private java.lang.String	JSJDM;

	private java.lang.String	JYFW;

	private java.lang.String	ZZJGDM;

	private java.lang.String	SWDJZH;

	private java.lang.String	YYZZH;

	private java.lang.String	NSRZT;

	private java.lang.String	ZJLXDM;

	private java.lang.String	NSRMC;

	private java.lang.String	GJBZHYDM;

	private java.lang.String	BGHNR;

	private java.lang.String	JYDZYB;

	private java.lang.String	FRSFZHM;

	private java.lang.String	BGSJ;

	private java.lang.String	BGXM;

	private java.lang.String	JYDZ;

	private java.lang.String	ZCDZ;

	private java.lang.String	JYDZLXDH;

	private java.lang.String	ZGSWJGDM;

	private java.lang.String	ZZCRQ;

	public SWDJInfo()
	{
	}

	public SWDJInfo(java.lang.String DJZCLXDM, java.lang.String SWDJLX,
			java.lang.String FRXM, java.lang.String RQ, java.lang.String JSJDM,
			java.lang.String JYFW, java.lang.String ZZJGDM,
			java.lang.String SWDJZH, java.lang.String YYZZH,
			java.lang.String NSRZT, java.lang.String ZJLXDM,
			java.lang.String NSRMC, java.lang.String GJBZHYDM,
			java.lang.String BGHNR, java.lang.String JYDZYB,
			java.lang.String FRSFZHM, java.lang.String BGSJ,
			java.lang.String BGXM, java.lang.String JYDZ,
			java.lang.String ZCDZ, java.lang.String JYDZLXDH,
			java.lang.String ZGSWJGDM, java.lang.String ZZCRQ)
	{
		this.DJZCLXDM = DJZCLXDM;
		this.SWDJLX = SWDJLX;
		this.FRXM = FRXM;
		this.RQ = RQ;
		this.JSJDM = JSJDM;
		this.JYFW = JYFW;
		this.ZZJGDM = ZZJGDM;
		this.SWDJZH = SWDJZH;
		this.YYZZH = YYZZH;
		this.NSRZT = NSRZT;
		this.ZJLXDM = ZJLXDM;
		this.NSRMC = NSRMC;
		this.GJBZHYDM = GJBZHYDM;
		this.BGHNR = BGHNR;
		this.JYDZYB = JYDZYB;
		this.FRSFZHM = FRSFZHM;
		this.BGSJ = BGSJ;
		this.BGXM = BGXM;
		this.JYDZ = JYDZ;
		this.ZCDZ = ZCDZ;
		this.JYDZLXDH = JYDZLXDH;
		this.ZGSWJGDM = ZGSWJGDM;
		this.ZZCRQ = ZZCRQ;
	}

	/**
	 * Gets the DJZCLXDM value for this SWDJInfo.
	 * 
	 * @return DJZCLXDM
	 */
	public java.lang.String getDJZCLXDM()
	{
		return DJZCLXDM;
	}

	/**
	 * Sets the DJZCLXDM value for this SWDJInfo.
	 * 
	 * @param DJZCLXDM
	 */
	public void setDJZCLXDM(java.lang.String DJZCLXDM)
	{
		this.DJZCLXDM = DJZCLXDM;
	}

	/**
	 * Gets the SWDJLX value for this SWDJInfo.
	 * 
	 * @return SWDJLX
	 */
	public java.lang.String getSWDJLX()
	{
		return SWDJLX;
	}

	/**
	 * Sets the SWDJLX value for this SWDJInfo.
	 * 
	 * @param SWDJLX
	 */
	public void setSWDJLX(java.lang.String SWDJLX)
	{
		this.SWDJLX = SWDJLX;
	}

	/**
	 * Gets the FRXM value for this SWDJInfo.
	 * 
	 * @return FRXM
	 */
	public java.lang.String getFRXM()
	{
		return FRXM;
	}

	/**
	 * Sets the FRXM value for this SWDJInfo.
	 * 
	 * @param FRXM
	 */
	public void setFRXM(java.lang.String FRXM)
	{
		this.FRXM = FRXM;
	}

	/**
	 * Gets the RQ value for this SWDJInfo.
	 * 
	 * @return RQ
	 */
	public java.lang.String getRQ()
	{
		return RQ;
	}

	/**
	 * Sets the RQ value for this SWDJInfo.
	 * 
	 * @param RQ
	 */
	public void setRQ(java.lang.String RQ)
	{
		this.RQ = RQ;
	}

	/**
	 * Gets the JSJDM value for this SWDJInfo.
	 * 
	 * @return JSJDM
	 */
	public java.lang.String getJSJDM()
	{
		return JSJDM;
	}

	/**
	 * Sets the JSJDM value for this SWDJInfo.
	 * 
	 * @param JSJDM
	 */
	public void setJSJDM(java.lang.String JSJDM)
	{
		this.JSJDM = JSJDM;
	}

	/**
	 * Gets the JYFW value for this SWDJInfo.
	 * 
	 * @return JYFW
	 */
	public java.lang.String getJYFW()
	{
		return JYFW;
	}

	/**
	 * Sets the JYFW value for this SWDJInfo.
	 * 
	 * @param JYFW
	 */
	public void setJYFW(java.lang.String JYFW)
	{
		this.JYFW = JYFW;
	}

	/**
	 * Gets the ZZJGDM value for this SWDJInfo.
	 * 
	 * @return ZZJGDM
	 */
	public java.lang.String getZZJGDM()
	{
		return ZZJGDM;
	}

	/**
	 * Sets the ZZJGDM value for this SWDJInfo.
	 * 
	 * @param ZZJGDM
	 */
	public void setZZJGDM(java.lang.String ZZJGDM)
	{
		this.ZZJGDM = ZZJGDM;
	}

	/**
	 * Gets the SWDJZH value for this SWDJInfo.
	 * 
	 * @return SWDJZH
	 */
	public java.lang.String getSWDJZH()
	{
		return SWDJZH;
	}

	/**
	 * Sets the SWDJZH value for this SWDJInfo.
	 * 
	 * @param SWDJZH
	 */
	public void setSWDJZH(java.lang.String SWDJZH)
	{
		this.SWDJZH = SWDJZH;
	}

	/**
	 * Gets the YYZZH value for this SWDJInfo.
	 * 
	 * @return YYZZH
	 */
	public java.lang.String getYYZZH()
	{
		return YYZZH;
	}

	/**
	 * Sets the YYZZH value for this SWDJInfo.
	 * 
	 * @param YYZZH
	 */
	public void setYYZZH(java.lang.String YYZZH)
	{
		this.YYZZH = YYZZH;
	}

	/**
	 * Gets the NSRZT value for this SWDJInfo.
	 * 
	 * @return NSRZT
	 */
	public java.lang.String getNSRZT()
	{
		return NSRZT;
	}

	/**
	 * Sets the NSRZT value for this SWDJInfo.
	 * 
	 * @param NSRZT
	 */
	public void setNSRZT(java.lang.String NSRZT)
	{
		this.NSRZT = NSRZT;
	}

	/**
	 * Gets the ZJLXDM value for this SWDJInfo.
	 * 
	 * @return ZJLXDM
	 */
	public java.lang.String getZJLXDM()
	{
		return ZJLXDM;
	}

	/**
	 * Sets the ZJLXDM value for this SWDJInfo.
	 * 
	 * @param ZJLXDM
	 */
	public void setZJLXDM(java.lang.String ZJLXDM)
	{
		this.ZJLXDM = ZJLXDM;
	}

	/**
	 * Gets the NSRMC value for this SWDJInfo.
	 * 
	 * @return NSRMC
	 */
	public java.lang.String getNSRMC()
	{
		return NSRMC;
	}

	/**
	 * Sets the NSRMC value for this SWDJInfo.
	 * 
	 * @param NSRMC
	 */
	public void setNSRMC(java.lang.String NSRMC)
	{
		this.NSRMC = NSRMC;
	}

	/**
	 * Gets the GJBZHYDM value for this SWDJInfo.
	 * 
	 * @return GJBZHYDM
	 */
	public java.lang.String getGJBZHYDM()
	{
		return GJBZHYDM;
	}

	/**
	 * Sets the GJBZHYDM value for this SWDJInfo.
	 * 
	 * @param GJBZHYDM
	 */
	public void setGJBZHYDM(java.lang.String GJBZHYDM)
	{
		this.GJBZHYDM = GJBZHYDM;
	}

	/**
	 * Gets the BGHNR value for this SWDJInfo.
	 * 
	 * @return BGHNR
	 */
	public java.lang.String getBGHNR()
	{
		return BGHNR;
	}

	/**
	 * Sets the BGHNR value for this SWDJInfo.
	 * 
	 * @param BGHNR
	 */
	public void setBGHNR(java.lang.String BGHNR)
	{
		this.BGHNR = BGHNR;
	}

	/**
	 * Gets the JYDZYB value for this SWDJInfo.
	 * 
	 * @return JYDZYB
	 */
	public java.lang.String getJYDZYB()
	{
		return JYDZYB;
	}

	/**
	 * Sets the JYDZYB value for this SWDJInfo.
	 * 
	 * @param JYDZYB
	 */
	public void setJYDZYB(java.lang.String JYDZYB)
	{
		this.JYDZYB = JYDZYB;
	}

	/**
	 * Gets the FRSFZHM value for this SWDJInfo.
	 * 
	 * @return FRSFZHM
	 */
	public java.lang.String getFRSFZHM()
	{
		return FRSFZHM;
	}

	/**
	 * Sets the FRSFZHM value for this SWDJInfo.
	 * 
	 * @param FRSFZHM
	 */
	public void setFRSFZHM(java.lang.String FRSFZHM)
	{
		this.FRSFZHM = FRSFZHM;
	}

	/**
	 * Gets the BGSJ value for this SWDJInfo.
	 * 
	 * @return BGSJ
	 */
	public java.lang.String getBGSJ()
	{
		return BGSJ;
	}

	/**
	 * Sets the BGSJ value for this SWDJInfo.
	 * 
	 * @param BGSJ
	 */
	public void setBGSJ(java.lang.String BGSJ)
	{
		this.BGSJ = BGSJ;
	}

	/**
	 * Gets the BGXM value for this SWDJInfo.
	 * 
	 * @return BGXM
	 */
	public java.lang.String getBGXM()
	{
		return BGXM;
	}

	/**
	 * Sets the BGXM value for this SWDJInfo.
	 * 
	 * @param BGXM
	 */
	public void setBGXM(java.lang.String BGXM)
	{
		this.BGXM = BGXM;
	}

	/**
	 * Gets the JYDZ value for this SWDJInfo.
	 * 
	 * @return JYDZ
	 */
	public java.lang.String getJYDZ()
	{
		return JYDZ;
	}

	/**
	 * Sets the JYDZ value for this SWDJInfo.
	 * 
	 * @param JYDZ
	 */
	public void setJYDZ(java.lang.String JYDZ)
	{
		this.JYDZ = JYDZ;
	}

	/**
	 * Gets the ZCDZ value for this SWDJInfo.
	 * 
	 * @return ZCDZ
	 */
	public java.lang.String getZCDZ()
	{
		return ZCDZ;
	}

	/**
	 * Sets the ZCDZ value for this SWDJInfo.
	 * 
	 * @param ZCDZ
	 */
	public void setZCDZ(java.lang.String ZCDZ)
	{
		this.ZCDZ = ZCDZ;
	}

	/**
	 * Gets the JYDZLXDH value for this SWDJInfo.
	 * 
	 * @return JYDZLXDH
	 */
	public java.lang.String getJYDZLXDH()
	{
		return JYDZLXDH;
	}

	/**
	 * Sets the JYDZLXDH value for this SWDJInfo.
	 * 
	 * @param JYDZLXDH
	 */
	public void setJYDZLXDH(java.lang.String JYDZLXDH)
	{
		this.JYDZLXDH = JYDZLXDH;
	}

	/**
	 * Gets the ZGSWJGDM value for this SWDJInfo.
	 * 
	 * @return ZGSWJGDM
	 */
	public java.lang.String getZGSWJGDM()
	{
		return ZGSWJGDM;
	}

	/**
	 * Sets the ZGSWJGDM value for this SWDJInfo.
	 * 
	 * @param ZGSWJGDM
	 */
	public void setZGSWJGDM(java.lang.String ZGSWJGDM)
	{
		this.ZGSWJGDM = ZGSWJGDM;
	}

	/**
	 * Gets the ZZCRQ value for this SWDJInfo.
	 * 
	 * @return ZZCRQ
	 */
	public java.lang.String getZZCRQ()
	{
		return ZZCRQ;
	}

	/**
	 * Sets the ZZCRQ value for this SWDJInfo.
	 * 
	 * @param ZZCRQ
	 */
	public void setZZCRQ(java.lang.String ZZCRQ)
	{
		this.ZZCRQ = ZZCRQ;
	}

	private java.lang.Object	__equalsCalc	= null;

	public synchronized boolean equals(java.lang.Object obj)
	{
		if (!(obj instanceof SWDJInfo))
			return false;
		SWDJInfo other = (SWDJInfo) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.DJZCLXDM == null && other.getDJZCLXDM() == null) || (this.DJZCLXDM != null && this.DJZCLXDM
						.equals(other.getDJZCLXDM())))
				&& ((this.SWDJLX == null && other.getSWDJLX() == null) || (this.SWDJLX != null && this.SWDJLX
						.equals(other.getSWDJLX())))
				&& ((this.FRXM == null && other.getFRXM() == null) || (this.FRXM != null && this.FRXM
						.equals(other.getFRXM())))
				&& ((this.RQ == null && other.getRQ() == null) || (this.RQ != null && this.RQ
						.equals(other.getRQ())))
				&& ((this.JSJDM == null && other.getJSJDM() == null) || (this.JSJDM != null && this.JSJDM
						.equals(other.getJSJDM())))
				&& ((this.JYFW == null && other.getJYFW() == null) || (this.JYFW != null && this.JYFW
						.equals(other.getJYFW())))
				&& ((this.ZZJGDM == null && other.getZZJGDM() == null) || (this.ZZJGDM != null && this.ZZJGDM
						.equals(other.getZZJGDM())))
				&& ((this.SWDJZH == null && other.getSWDJZH() == null) || (this.SWDJZH != null && this.SWDJZH
						.equals(other.getSWDJZH())))
				&& ((this.YYZZH == null && other.getYYZZH() == null) || (this.YYZZH != null && this.YYZZH
						.equals(other.getYYZZH())))
				&& ((this.NSRZT == null && other.getNSRZT() == null) || (this.NSRZT != null && this.NSRZT
						.equals(other.getNSRZT())))
				&& ((this.ZJLXDM == null && other.getZJLXDM() == null) || (this.ZJLXDM != null && this.ZJLXDM
						.equals(other.getZJLXDM())))
				&& ((this.NSRMC == null && other.getNSRMC() == null) || (this.NSRMC != null && this.NSRMC
						.equals(other.getNSRMC())))
				&& ((this.GJBZHYDM == null && other.getGJBZHYDM() == null) || (this.GJBZHYDM != null && this.GJBZHYDM
						.equals(other.getGJBZHYDM())))
				&& ((this.BGHNR == null && other.getBGHNR() == null) || (this.BGHNR != null && this.BGHNR
						.equals(other.getBGHNR())))
				&& ((this.JYDZYB == null && other.getJYDZYB() == null) || (this.JYDZYB != null && this.JYDZYB
						.equals(other.getJYDZYB())))
				&& ((this.FRSFZHM == null && other.getFRSFZHM() == null) || (this.FRSFZHM != null && this.FRSFZHM
						.equals(other.getFRSFZHM())))
				&& ((this.BGSJ == null && other.getBGSJ() == null) || (this.BGSJ != null && this.BGSJ
						.equals(other.getBGSJ())))
				&& ((this.BGXM == null && other.getBGXM() == null) || (this.BGXM != null && this.BGXM
						.equals(other.getBGXM())))
				&& ((this.JYDZ == null && other.getJYDZ() == null) || (this.JYDZ != null && this.JYDZ
						.equals(other.getJYDZ())))
				&& ((this.ZCDZ == null && other.getZCDZ() == null) || (this.ZCDZ != null && this.ZCDZ
						.equals(other.getZCDZ())))
				&& ((this.JYDZLXDH == null && other.getJYDZLXDH() == null) || (this.JYDZLXDH != null && this.JYDZLXDH
						.equals(other.getJYDZLXDH())))
				&& ((this.ZGSWJGDM == null && other.getZGSWJGDM() == null) || (this.ZGSWJGDM != null && this.ZGSWJGDM
						.equals(other.getZGSWJGDM())))
				&& ((this.ZZCRQ == null && other.getZZCRQ() == null) || (this.ZZCRQ != null && this.ZZCRQ
						.equals(other.getZZCRQ())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean	__hashCodeCalc	= false;

	public synchronized int hashCode()
	{
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getDJZCLXDM() != null) {
			_hashCode += getDJZCLXDM().hashCode();
		}
		if (getSWDJLX() != null) {
			_hashCode += getSWDJLX().hashCode();
		}
		if (getFRXM() != null) {
			_hashCode += getFRXM().hashCode();
		}
		if (getRQ() != null) {
			_hashCode += getRQ().hashCode();
		}
		if (getJSJDM() != null) {
			_hashCode += getJSJDM().hashCode();
		}
		if (getJYFW() != null) {
			_hashCode += getJYFW().hashCode();
		}
		if (getZZJGDM() != null) {
			_hashCode += getZZJGDM().hashCode();
		}
		if (getSWDJZH() != null) {
			_hashCode += getSWDJZH().hashCode();
		}
		if (getYYZZH() != null) {
			_hashCode += getYYZZH().hashCode();
		}
		if (getNSRZT() != null) {
			_hashCode += getNSRZT().hashCode();
		}
		if (getZJLXDM() != null) {
			_hashCode += getZJLXDM().hashCode();
		}
		if (getNSRMC() != null) {
			_hashCode += getNSRMC().hashCode();
		}
		if (getGJBZHYDM() != null) {
			_hashCode += getGJBZHYDM().hashCode();
		}
		if (getBGHNR() != null) {
			_hashCode += getBGHNR().hashCode();
		}
		if (getJYDZYB() != null) {
			_hashCode += getJYDZYB().hashCode();
		}
		if (getFRSFZHM() != null) {
			_hashCode += getFRSFZHM().hashCode();
		}
		if (getBGSJ() != null) {
			_hashCode += getBGSJ().hashCode();
		}
		if (getBGXM() != null) {
			_hashCode += getBGXM().hashCode();
		}
		if (getJYDZ() != null) {
			_hashCode += getJYDZ().hashCode();
		}
		if (getZCDZ() != null) {
			_hashCode += getZCDZ().hashCode();
		}
		if (getJYDZLXDH() != null) {
			_hashCode += getJYDZLXDH().hashCode();
		}
		if (getZGSWJGDM() != null) {
			_hashCode += getZGSWJGDM().hashCode();
		}
		if (getZZCRQ() != null) {
			_hashCode += getZZCRQ().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc	typeDesc	= new org.apache.axis.description.TypeDesc(
																			SWDJInfo.class,
																			true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "SWDJInfo"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DJZCLXDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "DJZCLXDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SWDJLX");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "SWDJLX"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FRXM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "FRXM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("RQ");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "RQ"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JSJDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "JSJDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JYFW");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "JYFW"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZJGDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "ZZJGDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SWDJZH");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "SWDJZH"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("YYZZH");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "YYZZH"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NSRZT");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "NSRZT"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZJLXDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "ZJLXDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("NSRMC");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "NSRMC"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("GJBZHYDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "GJBZHYDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BGHNR");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "BGHNR"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JYDZYB");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "JYDZYB"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("FRSFZHM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "FRSFZHM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BGSJ");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "BGSJ"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("BGXM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "BGXM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JYDZ");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "JYDZ"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZCDZ");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "ZCDZ"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("JYDZLXDH");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "JYDZLXDH"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZGSWJGDM");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "ZGSWJGDM"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ZZCRQ");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"java:com.syax.bjtax.gs.exchange.businessbean", "ZZCRQ"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc()
	{
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType)
	{
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
				_xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType)
	{
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
				_xmlType, typeDesc);
	}

	
	public String toSWDJSql()
	{
		StringBuffer sql = new StringBuffer(
				"insert into TAX_SWDJ(JSJDM,NSRMC,ZCDZ,JYDZ,JYDZLXDH,JYDZYB,YYZZH,SWDJZH,RQ,DJZCLXDM,ZZJGDM,GJBZHYDM,ZGSWJGDM,ZJLXDM,FRSFZHM,FRXM,JYFW,SWDJLX,NSRZT) values(");
		sql.append("'").append(StringUtil.nullToEmpty(JSJDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(NSRMC)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(ZCDZ)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(JYDZ)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(JYDZLXDH)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(JYDZYB)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(YYZZH)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(SWDJZH)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(RQ)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(DJZCLXDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(ZZJGDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(GJBZHYDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(ZGSWJGDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(ZJLXDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(FRSFZHM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(FRXM).replaceAll("&amp;#8226;", "¡¤")).append("',")
		   .append("'").append(StringUtil.nullToEmpty(JYFW)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(SWDJLX)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(NSRZT)).append("'")
		   .append(")");
		log.debug(sql.toString());
		return sql.toString();
	}
	
	public String toSWBGSql()
	{
		StringBuffer sql = new StringBuffer(
				"insert into TAX_SWDJ_BG(JSJDM,NSRMC,YYZZH,DJZCLXDM,SWDJLX,NSRZT,BGXM,BGHNR,BGSJ) values(");
		sql.append("'").append(StringUtil.nullToEmpty(JSJDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(NSRMC)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(YYZZH)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(DJZCLXDM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(SWDJLX)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(NSRZT)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(BGXM)).append("',")
		   .append("'").append(StringUtil.nullToEmpty(BGHNR).replaceAll("&amp;#8226;", "¡¤")).append("',")
		   .append("'").append(StringUtil.nullToEmpty(BGSJ)).append("'")
		   .append(")");
		log.debug(sql.toString());
		return sql.toString();
	}

	private static Map map = new HashMap();
	
	static{
		map.put("101", "NSRMC");
		map.put("102", "ZZJGDM");
		map.put("103", "YYZZH");
		map.put("104", "ZCDZ");
		map.put("107", "JYDZ");
		map.put("108", "JYDZYB");
		map.put("109", "JYDZLXDH");
		map.put("110", "JYFW");
		map.put("116", "FRXM");
		map.put("121", "ZGSWJGDM");
		map.put("123", "DJZCLXDM");
		map.put("125", "GJBZHYDM");
		map.put("129", "SWDJLX");
		map.put("132", "SWDJZH");
		map.put("133", "NSRZT");
		map.put("203", "ZJLXDM");
		map.put("204", "FRSFZHM");
	}
	
	public String toUpdateSWDJByBGSql(){
		String str = (String)map.get(BGXM);
		if (str != null && !"".equals(str)) {
			StringBuffer sql = new StringBuffer("update TAX_SWDJ set ");
			if ("116".equals(BGXM)) {
				String[] newBGHNR = StringUtil.nullToEmpty(BGHNR).replaceAll("[<>]", "").split(",");
				if(newBGHNR.length>2){
					try{
						Integer.parseInt(newBGHNR[1]);					
					}catch(Exception e){
						log.error(YYZZH,e);
						return null;
					}
				}else{
					return null;
				}
				sql.append("FRXM='").append(StringUtil.nullToEmpty(newBGHNR[0]).replaceAll("&amp;#8226;", "¡¤")).append("',")
				   .append("ZJLXDM='").append(StringUtil.nullToEmpty(newBGHNR[1])).append("',")
				   .append("FRSFZHM='").append(StringUtil.nullToEmpty(newBGHNR[2])).append("'")
				   .append(" where YYZZH = '").append(StringUtil.nullToEmpty(YYZZH)).append("'");
			} else {
				sql.append(str).append("=").append("'").append(
						StringUtil.nullToEmpty(BGHNR)).append("'").append(
						" where YYZZH = '").append(
						StringUtil.nullToEmpty(YYZZH)).append("'");
			}
			log.debug(sql.toString());
			return sql.toString();
		} else {
			return str;
		}
	}

	public static void main(String[] args){
		String s = "<ÉÌ¾ü,01,130223720317551,67872271,13911901969,,>";
		String s1 = s.replaceAll("[<>]", "");
		System.out.println(s1);
	}	
}
