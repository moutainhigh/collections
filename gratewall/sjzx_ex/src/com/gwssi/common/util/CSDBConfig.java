package com.gwssi.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ����bjais.properties��Դ�����ļ�,���Զ����ص���
 * @author lifx
 *
 */
public class CSDBConfig {
	
	private static final Log logger = LogFactory.getLog(CSDBConfig.class);
	
	private Properties props = new Properties();
	 
	private Map properties = new HashMap();

	private static CSDBConfig bjaisConfig;
	 
	private CSDBConfig(){
		 try {
			 InputStream infile = new FileInputStream(Constants.CONFIGPATH + Constants.CONFIG_FILE);			 		 
			 props.load(infile);
			 for (Iterator iter = props.keySet().iterator();iter.hasNext();){
				 String name = (String)iter.next();
				 properties.put(name,props.getProperty(name));
			 }
			 
			 infile.close();
		 }catch(Exception e){
			 logger.error(e);
		 }
	}
	
	/**
	 * ��BjaisConfig����ʵ��
	 * @return
	 */
	public static synchronized CSDBConfig getInstance(){
		if (bjaisConfig == null){
			bjaisConfig = new CSDBConfig();
		}
		return bjaisConfig;
	}
	
	public static synchronized void reload(){
		bjaisConfig = new CSDBConfig();
	}
	 
	public Map getProperties(){
	    return properties;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ����
	 * @param key
	 * @return
	 */
	public static String get(String key){
		CSDBConfig bjais = getInstance();
		return (String)bjais.getProperties().get(key);
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key ΪList �滻˳���0��ʼ
	 * @return
	 */
	public static String get(String key,List keys){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		int i = 0 ; 
		for (Iterator iter = keys.iterator();iter.hasNext();i++){
			String s = (String)iter.next();
			str = StringUtil.replace(str, StringUtil.replace(Constants.sx,Constants.x,String.valueOf(i-1)), s);
		}
		return str;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3,String s4){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		str = StringUtil.replace(str, Constants.s4, s4);
		return str;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2,String s3){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		str = StringUtil.replace(str, Constants.s3, s3);
		return str;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1,String s2){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		str = StringUtil.replace(str, Constants.s2, s2);
		return str;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0,String s1){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		str = StringUtil.replace(str, Constants.s1, s1);
		return str;
	}
	
	/**
	 * ����key�õ���Դ�ļ���Ϣ���ݣ���ͨ���滻���������Դ��Ϣ�滻����ֵ��
	 * @param key
	 * @return
	 */
	public static String get(String key,String s0){
		CSDBConfig bjais = getInstance();
		String str = (String)bjais.getProperties().get(key);
		str = StringUtil.replace(str, Constants.s0, s0);
		return str;
	}
}
