/**
 * ReturnMultiGSData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.servlet.zr;

public class ReturnMultiGSData  implements java.io.Serializable {
    private java.lang.String FHDM;

    private java.lang.String ZTS;

    private java.lang.String KSJLS;

    private java.lang.String JSJLS;

    private com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] GSDJ_INFO_ARRAY;

    public ReturnMultiGSData() {
    }

    public ReturnMultiGSData(
           java.lang.String FHDM,
           java.lang.String ZTS,
           java.lang.String KSJLS,
           java.lang.String JSJLS,
           com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] GSDJ_INFO_ARRAY) {
           this.FHDM = FHDM;
           this.ZTS = ZTS;
           this.KSJLS = KSJLS;
           this.JSJLS = JSJLS;
           this.GSDJ_INFO_ARRAY = GSDJ_INFO_ARRAY;
    }


    /**
     * Gets the FHDM value for this ReturnMultiGSData.
     * 
     * @return FHDM
     */
    public java.lang.String getFHDM() {
        return FHDM;
    }


    /**
     * Sets the FHDM value for this ReturnMultiGSData.
     * 
     * @param FHDM
     */
    public void setFHDM(java.lang.String FHDM) {
        this.FHDM = FHDM;
    }


    /**
     * Gets the ZTS value for this ReturnMultiGSData.
     * 
     * @return ZTS
     */
    public java.lang.String getZTS() {
        return ZTS;
    }


    /**
     * Sets the ZTS value for this ReturnMultiGSData.
     * 
     * @param ZTS
     */
    public void setZTS(java.lang.String ZTS) {
        this.ZTS = ZTS;
    }


    /**
     * Gets the KSJLS value for this ReturnMultiGSData.
     * 
     * @return KSJLS
     */
    public java.lang.String getKSJLS() {
        return KSJLS;
    }


    /**
     * Sets the KSJLS value for this ReturnMultiGSData.
     * 
     * @param KSJLS
     */
    public void setKSJLS(java.lang.String KSJLS) {
        this.KSJLS = KSJLS;
    }


    /**
     * Gets the JSJLS value for this ReturnMultiGSData.
     * 
     * @return JSJLS
     */
    public java.lang.String getJSJLS() {
        return JSJLS;
    }


    /**
     * Sets the JSJLS value for this ReturnMultiGSData.
     * 
     * @param JSJLS
     */
    public void setJSJLS(java.lang.String JSJLS) {
        this.JSJLS = JSJLS;
    }


    /**
     * Gets the GSDJ_INFO_ARRAY value for this ReturnMultiGSData.
     * 
     * @return GSDJ_INFO_ARRAY
     */
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] getGSDJ_INFO_ARRAY() {
        return GSDJ_INFO_ARRAY;
    }


    /**
     * Sets the GSDJ_INFO_ARRAY value for this ReturnMultiGSData.
     * 
     * @param GSDJ_INFO_ARRAY
     */
    public void setGSDJ_INFO_ARRAY(com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.GSDJInfo[] GSDJ_INFO_ARRAY) {
        this.GSDJ_INFO_ARRAY = GSDJ_INFO_ARRAY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReturnMultiGSData)) return false;
        ReturnMultiGSData other = (ReturnMultiGSData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.FHDM==null && other.getFHDM()==null) || 
             (this.FHDM!=null &&
              this.FHDM.equals(other.getFHDM()))) &&
            ((this.ZTS==null && other.getZTS()==null) || 
             (this.ZTS!=null &&
              this.ZTS.equals(other.getZTS()))) &&
            ((this.KSJLS==null && other.getKSJLS()==null) || 
             (this.KSJLS!=null &&
              this.KSJLS.equals(other.getKSJLS()))) &&
            ((this.JSJLS==null && other.getJSJLS()==null) || 
             (this.JSJLS!=null &&
              this.JSJLS.equals(other.getJSJLS()))) &&
            ((this.GSDJ_INFO_ARRAY==null && other.getGSDJ_INFO_ARRAY()==null) || 
             (this.GSDJ_INFO_ARRAY!=null &&
              java.util.Arrays.equals(this.GSDJ_INFO_ARRAY, other.getGSDJ_INFO_ARRAY())));
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
        if (getFHDM() != null) {
            _hashCode += getFHDM().hashCode();
        }
        if (getZTS() != null) {
            _hashCode += getZTS().hashCode();
        }
        if (getKSJLS() != null) {
            _hashCode += getKSJLS().hashCode();
        }
        if (getJSJLS() != null) {
            _hashCode += getJSJLS().hashCode();
        }
        if (getGSDJ_INFO_ARRAY() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGSDJ_INFO_ARRAY());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGSDJ_INFO_ARRAY(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReturnMultiGSData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ReturnMultiGSData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FHDM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FHDM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ZTS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ZTS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KSJLS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "KSJLS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("JSJLS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "JSJLS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GSDJ_INFO_ARRAY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GSDJ_INFO_ARRAY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GSDJInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "GSDJInfo"));
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
