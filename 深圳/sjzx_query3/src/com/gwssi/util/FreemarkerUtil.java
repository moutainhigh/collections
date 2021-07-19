package com.gwssi.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板工具
 * jprofiler 
 * @author zhixiong
 */
public class FreemarkerUtil {
	private static  Logger log=Logger.getLogger(FreemarkerUtil.class);
	private static Configuration configuration	= null;
	private static String temeplatePath = null;
	
	static{
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		temeplatePath = FreemarkerUtil.class.getClassLoader().getResource("com/gwssi/ftl").getPath();
		if(StringUtils.isNotEmpty(temeplatePath)){
        	temeplatePath = temeplatePath.substring(1,temeplatePath.length());
        }
	}
	
	public FreemarkerUtil()	{
		log.info("构造函数freemarker的模板地址："+this.getClass().getResource("/ftl").toString());
	}
	
	/**
	 * 获取文件路径
	 * @return
	 */
	private String getTemeplatePath(){
		return java.util.ResourceBundle.getBundle("log4j").getString("log4j.rootLogger");
	}
	
	/**
	 * 根据模板文件名获取Template
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public static Template getTemplate(String name) throws IOException {
        // 通过Freemaker的Configuration读取相应的ftl
        //Configuration cfg = new Configuration();
        // 设定去哪里读取相应的ftl模板文件
        //cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
        //System.out.println(FreemarkerUtil.class.getClassLoader().getResource("/ftl").getPath().toString());
        log.info("freemarker的模板地址："+temeplatePath);
		configuration.setDirectoryForTemplateLoading(new File(temeplatePath));
        // 在模板文件目录中找到名称为name的文件
        Template temp = configuration.getTemplate(name);
        return temp;
    }
	
	/**
	 * 根据模板文件名和传入的需要处理的map替换输出为字符串
	 * @param name
	 * @param root
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static String returnString(String name, Map<String, Object> root) throws IOException, TemplateException {
        Writer out = null;
		try {
			Template temp = getTemplate(name);
			out = new StringWriter(2048);  
			temp.process(root, out);
		} catch (IOException e) {
			throw e;
		} catch (TemplateException e) {
			throw e;
		}finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return out.toString();
	}

    /**
     * 控制台输出
     * 
     * @param name
     * @param root
     */
     public static void print(String name, Map<String, Object> root1) {
        try {
            // 通过Template可以将模板文件输出到相应的流
        	Map<String, Object> root = new HashMap();  
            root.put("username","李鑫龙");  
            root.put("address","合肥市望江西路666号");  
            root.put("age", 23); 
            root = new HashMap();  
            root.put("username","李鑫龙12");  
            root.put("address","合肥市望江西路666号");  
            root.put("age", 23);
            
            Map<String, Object> weaponMap = new HashMap<String, Object>();  
            weaponMap.put("first", "轩辕剑");  
            weaponMap.put("second", "崆峒印");  
            weaponMap.put("third", "女娲石");  
            weaponMap.put("fourth", "神农鼎");  
            weaponMap.put("fifth", "伏羲琴");  
            weaponMap.put("sixth", "昆仑镜");   
            weaponMap.put("seventh", null); 
            Map<String, Object> paramMap = new HashMap<String, Object>();  
            paramMap.put("weaponMap", weaponMap);  
            paramMap.put("name", "昆仑镜44"); 
            Template temp = getTemplate(name);
            Writer out = new StringWriter(2048);  
            temp.process(paramMap, new PrintWriter(System.out));
            temp.process(paramMap, out);
            System.out.println("============");
            System.out.println(out.toString());
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	//print("01.ftl",null);
    	//new FreemarkerUtil().getTemplate("01.ftl");
    	//System.out.println(new FreemarkerUtil().getTemeplatePath());
    	Map<String, Object> root = new HashMap<String, Object>();
    	root.put("postalcode", "528500");
    	root.put("exenum", "已注销");
    	Writer out = null;
		try {
			configuration.setDirectoryForTemplateLoading(new File("D:/tool/apache-tomcat-7.0.57/wtpwebapps/gdsjzx/WEB-INF/classes/ftl"));
	        // 在模板文件目录中找到名称为name的文件
	        Template temp = configuration.getTemplate("scztjbxx.ftl");
			out = new StringWriter(2048);  
			temp.process(root, out);
			System.out.println("-------------"+out.toString());
		} catch (IOException e) {
			e.getStackTrace();
		} catch (TemplateException e) {
			e.getStackTrace();
		}finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		System.out.println("-=====-"+out.toString());
	}

    /**
     * 输出HTML文件
     * 
     * @param name
     * @param root
     * @param outFile
     * @throws IOException 
     * @throws TemplateException 
     */
    public static void fprint(String name, Map<String, Object> root, String outFile) throws IOException, TemplateException {
        FileWriter out = null;
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            out = new FileWriter(new File("E:/workspace/freemarkprj/page/" + outFile));
            Template temp = getTemplate(name);
            temp.process(root, out);
        } catch (IOException e) {
        	throw e;
        } catch (TemplateException e) {
        	throw e;
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
