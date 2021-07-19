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
		// Ҫ����ģ���������ļ�
		Map dataMap = new HashMap();
		getData(dataMap);
		// ����ģ��װ�÷�����·��,FreeMarker֧�ֶ���ģ��װ�ط�����������servlet��classpath�����ݿ�װ�أ�
		// �������ǵ�ģ���Ƿ���com.havenliu.document.template������
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
			// test.ftlΪҪװ�ص�ģ��
			t = configuration.getTemplate("test.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ����ĵ�·��������
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
		dataMap.put("author", "����");
		dataMap.put("remark", "���ǲ��Ա�ע��Ϣ");
	}

	public static void main(String[] args) {
		DocumentHandler d = new DocumentHandler();
		d.createDoc();
	}
}
