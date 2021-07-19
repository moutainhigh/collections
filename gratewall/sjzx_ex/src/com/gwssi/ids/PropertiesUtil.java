package com.gwssi.ids;
import java.io.File;  
import java.io.FileInputStream;    
import java.io.FileOutputStream;    
import java.io.IOException;    
import java.io.InputStream;  
import java.io.OutputStream;    
import java.net.URI;  
import java.util.Enumeration;    
import java.util.HashMap;    
import java.util.Map;    
import java.util.Properties;    
import java.util.ResourceBundle;  
  
  
/**
 * Properties ����������
 * @author chaihw
 */
public class PropertiesUtil {    
    
    private Properties props;    
    private URI uri;  
      
    
    
    
    public PropertiesUtil(String fileName){    
        readProperties(fileName);    
    }    
    private void readProperties(String fileName) {    
        try {    
            props = new Properties();    
            InputStream fis =getClass().getResourceAsStream(fileName);    
            props.load(fis);    
            uri = this.getClass().getResource("/usernamedual.properties").toURI();  
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
    /**  
     * ��ȡĳ������  
     */    
    public String getProperty(String key){    
        return props.getProperty(key);    
    }    
    /**  
     * ��ȡ�������ԣ�����һ��map,������  
     * ��������props.putAll(t)  
     */    
    public Map getAllProperty(){    
        Map map=new HashMap();    
        Enumeration enu = props.propertyNames();    
        while (enu.hasMoreElements()) {    
            String key = (String) enu.nextElement();    
            String value = props.getProperty(key);    
            map.put(key, value);    
        }    
        return map;    
    }    
    /**  
     * �ڿ���̨�ϴ�ӡ���������ԣ�����ʱ�á�  
     */    
    public void printProperties(){    
        props.list(System.out);    
    }    
    /**  
     * д��properties��Ϣ  
     */    
    public void writeProperties(String key, String value) {    
        try {    
        OutputStream fos = new FileOutputStream(new File(uri));    
            props.setProperty(key, value);    
            // ���� Properties ���е������б�����Ԫ�ضԣ�д�������    
            props.store(fos, "��comments��Update key��" + key);    
        } catch (Exception e) {    
        e.printStackTrace();  
        }    
    }       
    public static void main(String[] args) {    
     //   PropertiesUtil util=new PropertiesUtil("src/dbConfig.properties");    
      //  util.writeProperties("dbtype", "MSSQL");    
    }        
}    