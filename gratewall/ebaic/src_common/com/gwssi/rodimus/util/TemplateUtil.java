package com.gwssi.rodimus.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.exception.RodimusException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author liuhailong
 */
public class TemplateUtil {
	
	private static Configuration cfg = null;
	
	static {
		// 模板配置
		cfg = new Configuration();
	}
	
	/**
	 * 获得模板对象。
	 * 
	 * @param tplName
	 * @return
	 */
	protected static Template getTemplate(String tplPath){
		if(StringUtil.isBlank(tplPath)){
			throw new RodimusException("tplName不能为空。");
		}
		String rootTplPath = ConfigUtil.get("doc.rootTplPath");
		if(StringUtil.isBlank(rootTplPath)){
			throw new RodimusException("请在sys_config表中配置doc.rootTplPath。");
		}
		String fullTplPath = rootTplPath + tplPath;//String.format("%s%s", tplRootPath,tplName);
		try {
			Template ret = cfg.getTemplate(fullTplPath, "utf-8");
			return ret;
		} catch (IOException e) {
			throw new RodimusException(e.getMessage(),e);
		}
	}
	
	/**
	 * 根据模板生成字符串。
	 * 
	 * @param tplName
	 * @param params
	 * @return
	 */
	public static String process(String tplName,Map<String, Object> params){
		Writer writer = new StringWriter(20480);
		Template template = getTemplate(tplName);
		try {
			template.process(params, writer);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		String ret = writer.toString();
		return ret;
	}
	/**
	 * 根据模板生成字符串。
	 * 
	 * @param tplName
	 * @param params
	 * @param targetFilePath
	 */
	public static void process(String tplName,Map<String, Object> params,String targetFilePath){
		String content = process(tplName, params);
		FileUtil.saveString2File(targetFilePath, content);
	}
}
