package com.gwssi.rodimus.doc.v2.core.data2html;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.rodimus.doc.v2.DocUtil;
import com.gwssi.rodimus.doc.v1.config.SysDocChapManager;
import com.gwssi.rodimus.doc.v1.domain.SysDocChapterConfig;
import com.gwssi.rodimus.exception.RodimusException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <h2>文档生成工具类</h2>
 * 
 * 
 * 根据数据生成HTML。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class HtmlBuilder {

	public static final String PAGING_SEPARATOR = "<div class='paging_separator' />";
	
	private static Configuration _cfg = null;
	protected static Configuration getTemplateConfiguration(){
		if(_cfg!=null){
			return _cfg;
		}
		try {
			String templateRootPath = DocUtil.getTemplateRootPath();
			// 模板根路径，通过 template.filePath 配置
			File templateBaseDir = new File(templateRootPath);
			// 初始化模板对象
			_cfg = new Configuration();
			_cfg.setDirectoryForTemplateLoading(templateBaseDir);
			_cfg.setObjectWrapper(new DefaultObjectWrapper());
			return _cfg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	 

	
	/**
	 * <h3>以Doc为单位。</h3>
	 * 
	 * <p>生成HTML</p>
	 * 
	 * @param docId
	 * @param params
	 * @return
	 */
	public static String buildDocHtml(String chapId, Map<String,Object> listContext){
		if(StringUtils.isBlank(chapId)){
			throw new RodimusException("生成图片出错：chapId为空。");
		}
		SysDocChapterConfig config = SysDocChapManager.instance.getConfig(chapId);
		if(config==null){
			throw new RodimusException("未找到章节配置信息。");
		}
		String templateUrl = config.getTemplateUrl();
		String htmlStr = generateHtml(templateUrl,listContext);
		//生成文件
//		String path = "C:\\DOCETST\\"+config.getChapConfigId()+".html";
//		FileUtil.createFile(path, htmlStr);
		 
		return htmlStr;
	}
	
	/**
	 * <h3>利用Freemaker根据模板生成Html语句</h3>
	 * 
	 * @param templateName
	 * @param param
	 * @return HtmlStr
	 */
	protected static String generateHtml(String templateName,Object param){
		String htmlStr = "";
		if(StringUtils.isBlank(templateName)){
			return "";
		}
		try {
			Template freemarkerTemplate = getTemplateConfiguration().getTemplate(templateName, "utf-8");
			Writer writer = new StringWriter(20480);
			freemarkerTemplate.process(param, writer);
			htmlStr = writer.toString();
		} catch (IOException e) {
			throw new RodimusException(e.getMessage(),e);
		} catch (TemplateException e) {
			throw new RodimusException(e.getMessage(),e);
		}
		return htmlStr;
	}
	
	public static void main(String[] args) {
		HtmlBuilder.buildDocHtml("",null);
	}
}
