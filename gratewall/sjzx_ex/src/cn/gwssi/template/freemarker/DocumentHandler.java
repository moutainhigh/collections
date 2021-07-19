package cn.gwssi.template.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocumentHandler {
	private Configuration configuration = null;

	public DocumentHandler() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	
	
	
	public void createDoc() {
		// 要填入模本的数据文件
		Map dataMap = new HashMap();
		getData(dataMap);
		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// 这里我们的模板是放在com.havenliu.document.template包下面
		System.out.println(this.getClass().getResource("/"));
		//configuration.setClassForTemplateLoading(this.getClass(), "");
		//File file = new File("E:\\workspace\\webTest\\WebRoot\\freemarker");
//		try {
//			//configuration.setDirectoryForTemplateLoading(file);
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("test.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出文档路径及名称
		File outFile = new File("D:/temp/outFile.doc");
		Writer out = null;
		try {
			OutputStreamWriter oWriter = new OutputStreamWriter(
					new FileOutputStream(outFile), "UTF-8");
			out = new BufferedWriter(oWriter);
		} catch (UnsupportedEncodingException e2) {
		} catch (FileNotFoundException e2) {
		}//
		//		
		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getData(Map dataMap) {
		dataMap.put("author", "张三");
		dataMap.put("remark", "这是测试备注信息");
	}

	public static void main(String[] args) {
		DocumentHandler d = new DocumentHandler();
		d.createDoc();
	}
}
