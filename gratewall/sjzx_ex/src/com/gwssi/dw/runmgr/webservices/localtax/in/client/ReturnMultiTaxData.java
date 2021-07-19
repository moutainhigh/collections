/**
 * ReturnMultiTaxData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.in.client;

public class ReturnMultiTaxData  implements java.io.Serializable {
    private java.lang.String ZTS;

    private java.lang.String FHDM;

    private java.lang.String JSJLS;

    private SWDJInfo[] SWDJ_INFO_ARRAY;

    private java.lang.String KSJLS;

    public ReturnMultiTaxData() {
    }

    public ReturnMultiTaxData(
           java.lang.String ZTS,
           java.lang.String FHDM,
           java.lang.String JSJLS,
           SWDJInfo[] SWDJ_INFO_ARRAY,
           java.lang.String KSJLS) {
           this.ZTS = ZTS;
           this.FHDM = FHDM;
           this.JSJLS = JSJLS;
           this.SWDJ_INFO_ARRAY = SWDJ_INFO_ARRAY;
           this.KSJLS = KSJLS;
    }


    /**
     * Gets the ZTS value for this ReturnMultiTaxData.
     * 
     * @return ZTS
     */
    public java.lang.String getZTS() {
        return ZTS;
    }


    /**
     * Sets the ZTS value for this ReturnMultiTaxData.
     * 
     * @param ZTS
     */
    public void setZTS(java.lang.String ZTS) {
        this.ZTS = ZTS;
    }


    /**
     * Gets the FHDM value for this ReturnMultiTaxData.
     * 
     * @return FHDM
     */
    public java.lang.String getFHDM() {
        return FHDM;
    }


    /**
     * Sets the FHDM value for this ReturnMultiTaxData.
     * 
     * @param FHDM
     */
    public void setFHDM(java.lang.String FHDM) {
        this.FHDM = FHDM;
    }


    /**
     * Gets the JSJLS value for this ReturnMultiTaxData.
     * 
     * @return JSJLS
     */
    public java.lang.String getJSJLS() {
        return JSJLS;
    }


    /**
     * Sets the JSJLS value for this ReturnMultiTaxData.
     * 
     * @param JSJLS
     */
    public void setJSJLS(java.lang.String JSJLS) {
        this.JSJLS = JSJLS;
    }


    /**
     * Gets the SWDJ_INFO_ARRAY value for this ReturnMultiTaxData.
     * 
     * @return SWDJ_INFO_ARRAY
     */
    public SWDJInfo[] getSWDJ_INFO_ARRAY() {
        return SWDJ_INFO_ARRAY;
    }


    /**
     * Sets the SWDJ_INFO_ARRAY value for this ReturnMultiTaxData.
     * 
     * @param SWDJ_INFO_ARRAY
     */
    public void setSWDJ_INFO_ARRAY(SWDJInfo[] SWDJ_INFO_ARRAY) {
        this.SWDJ_INFO_ARRAY = SWDJ_INFO_ARRAY;
    }


    /**
     * Gets the KSJLS value for this ReturnMultiTaxData.
     * 
     * @return KSJLS
     */
    public java.lang.String getKSJLS() {
        return KSJLS;
    }


    /**
     * Sets the KSJLS value for this ReturnMultiTaxData.
     * 
     * @param KSJLS
     */
    public void setKSJLS(java.lang.String KSJLS) {
        this.KSJLS = KSJLS;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReturnMultiTaxData)) return false;
        ReturnMultiTaxData other = (ReturnMultiTaxData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ZTS==null && other.getZTS()==null) || 
             (this.ZTS!=null &&
              this.ZTS.equals(other.getZTS()))) &&
            ((this.FHDM==null && other.getFHDM()==null) || 
             (this.FHDM!=null &&
              this.FHDM.equals(other.getFHDM()))) &&
            ((this.JSJLS==null && other.getJSJLS()==null) || 
             (this.JSJLS!=null &&
              this.JSJLS.equals(other.getJSJLS()))) &&
            ((this.SWDJ_INFO_ARRAY==null && other.getSWDJ_INFO_ARRAY()==null) || 
             (this.SWDJ_INFO_ARRAY!=null &&
              java.util.Arrays.equals(this.SWDJ_INFO_ARRAY, other.getSWDJ_INFO_ARRAY()))) &&
            ((this.KSJLS==null && other.getKSJLS()==null) || 
             (this.KSJLS!=null &&
              this.KSJLS.equals(other.getKSJLS())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getZTS() != null) {
            _hashCode += getZTS().hashCode();
        }
        if (getFHDM() != null) {
            _hashCode += getFHDM().hashCode();
        }
        if (getJSJLS() != null) {
            _hashCode += getJSJLS().hashCode();
        }
        if (getSWDJ_INFO_ARRAY() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSWDJ_INFO_ARRAY());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSWDJ_INFO_ARRAY(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getKSJLS() != null) {
            _hashCode += getKSJLS().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReturnMultiTaxData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "ReturnMultiTaxData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ZTS");
        elemField.setXmlName(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "ZTS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FHDM");
        elemField.setXmlName(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "FHDM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("JSJLS");
        elemField.setXmlName(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "JSJLS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SWDJ_INFO_ARRAY");
        elemField.setXmlName(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "SWDJ_INFO_ARRAY"));
        elemField.setXmlType(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "SWDJInfo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KSJLS");
        elemField.setXmlName(new javax.xml.namespace.QName("java:com.syax.bjtax.gs.exchange.businessbean", "KSJLS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
