package cn.gwssi.template.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import sun.misc.BASE64Encoder;

import com.gwssi.dw.dq.ChartDataFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil
{
	private Configuration	configuration	= null;

	private String getTemeplatePath()
	{
		return java.util.ResourceBundle.getBundle("app").getString(
				"freemarkerPath");
	}

	private String getWordPath()
	{
		return java.util.ResourceBundle.getBundle("app").getString(
				"docFilePath");
	}

	public FreemarkerUtil()
	{
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	/**
	 * 获取模版
	 * 
	 * @return
	 */
	public Template geTemplate(String templateName)
	{

		Template template = null;
		try {
			File file = new File(this.getTemeplatePath());
			// 设置路径
			configuration.setDirectoryForTemplateLoading(file);
			// 装载模板
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
			System.out.println("获取模版失败");
			e.printStackTrace();
		}
		return template;
	}

	public Template geTemplateFromDB(String templateName)
	{
		Template template = null;
		try {
			TemplateLoader loader = new TemplateDBLoader();
			configuration = new Configuration();
			configuration.setTemplateLoader(loader);
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;

	}

	/**
	 * 根据模版导出word，并放到指定位置
	 * 
	 * @param fileName
	 *            文件名称
	 * @param dataMap
	 *            要替换的参数map
	 */
	public void exportWord(String templateName, String fileName, Map dataMap)
	{
		Template template = this.geTemplate(templateName);
		String path = this.getWordPath() + fileName;
		File outFile = new File(path);
		Writer out = null;
		// 设置导出的代码集
		OutputStreamWriter oWriter;
		try {
			oWriter = new OutputStreamWriter(new FileOutputStream(outFile),
					"UTF-8");
			out = new BufferedWriter(oWriter);
			template.process(dataMap, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String bulidXml(String temp_param, Map dataMap)
	{
		Template template = this.geTemplateFromDB(temp_param);
		Writer out = null;
		// 设置导出的代码集
		try {
			out = new StringWriter(10000);  
			template.process(dataMap, out);
			//System.out.println("替换结果:"+out.toString());
			return 	out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 /*
	  * 根据字符串生成结果
	  */
	 public String exportTemplateString(String templateString, Map dataMap)
	 {
		
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", templateString);
		configuration.setTemplateLoader(stringLoader);
		Template template = null;
		try {
				template = configuration.getTemplate("myTemplate","utf-8");
		} catch (IOException e1) {
			System.out.println("生成模版失败");
			e1.printStackTrace();
		}
		Writer out = null;
		// 设置导出的代码集
		try {
			out = new StringWriter(100000);  
			template.process(dataMap, out);
			return 	out.toString().replaceAll("\r\n", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
		
	 
	 
	 
   /*
    * 根据XML文件生成结果
    */
	public String exportXmlString(String templateName, Map dataMap)
	{
		Template template = null;
		try {
			File file = new File(this.getTemeplatePath()+"chart");
			// 设置路径
			configuration.setDirectoryForTemplateLoading(file);
			// 装载模板
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
			System.out.println("获取模版失败");
			e.printStackTrace();
		}
		Writer out = null;
		// 设置导出的代码集
		try {
			out = new StringWriter(100000);  
			template.process(dataMap, out);
			return 	out.toString().replaceAll("\r\n", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	/**
	 * 根据图片路径获取图片的编码
	 * 
	 * @param imagePath
	 * @return
	 */
	public String getImageStr(String imagePath)
	{
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imagePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * 柱形图
	 * 
	 * @param categorydataset
	 * @param title
	 *            报表标题
	 * @param categoryName
	 *            x轴坐标
	 * @param xName
	 *            x轴名称
	 * @param yName
	 *            y轴名称
	 * @param fileName
	 *            保存的文件名称
	 * @param vertical
	 *            true 垂直显示 false 水平显示
	 * @return
	 */
	public String createBarChart(CategoryDataset categorydataset, String title,
			String xName, String yName, String fileName, boolean vertical)
	{
		JFreeChart chart = ChartDataFactory.bulidBarChart(categorydataset,
				title, xName, yName, vertical);
		FileOutputStream fos_jpg;
		String path = "";
		try {
			path = this.getWordPath() + "temp_image/" + fileName;
			fos_jpg = new FileOutputStream(path);
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 630, 290);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public String getTempImagePath(String fileName)
	{
		return this.getWordPath() + "temp_image/" + fileName;
	}

	/**
	 * 饼图
	 * 
	 * @param categorydataset
	 * @param title
	 *            报表标题
	 * @param categoryName
	 *            x轴坐标
	 * @param xName
	 *            x轴名称
	 * @param yName
	 *            y轴名称
	 * @param fileName
	 *            保存的文件名称
	 * @param vertical
	 *            true 垂直显示 false 水平显示
	 * @return
	 */
	public String createPieChart(CategoryDataset categorydataset, String title,
			String fileName)
	{
		JFreeChart chart = ChartDataFactory.createPieChart(categorydataset,
				title);
		FileOutputStream fos_jpg;
		String path = "";
		try {
			path = this.getWordPath() + "temp_image/" + fileName;
			fos_jpg = new FileOutputStream(path);
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 630, 260);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public String createLineChart(CategoryDataset categorydataset,
			String title, String categoryName, String fileName)
	{
		JFreeChart chart = ChartDataFactory.createLineChart(categorydataset,
				title, "", categoryName);
		FileOutputStream fos_jpg;
		String path = "";
		try {
			path = this.getWordPath() + "temp_image/" + fileName;
			fos_jpg = new FileOutputStream(path);
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 630, 260);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static void main(String[] args)
	{
		FreemarkerUtil f = new FreemarkerUtil();
		//f.geTemplateFromDB("2222");
//		Map dataMap = new HashMap();
//		dataMap.put("day1", "14");
//		List list=new ArrayList();
//		Map map=new HashMap();
//		map.put("name", "abc");
//		map.put("name1", "abcd");
//		list.add(map);
//		dataMap.put("list", list);
		
		Map resultMap = new HashMap();
		List DataList=new ArrayList();
		Map dataMap = new HashMap();
		dataMap.put("reg_bus_ent_id", "111");
		dataMap.put("name", "111");
		dataMap.put("update_date", "111");
		dataMap.put("type", "111");
		dataMap.put("dom", "111");
		dataMap.put("corp_rpt", "111");
		dataMap.put("reg_no", "111");
		dataMap.put("name_hs", "111");
		DataList.add(dataMap);
		resultMap.put("results", DataList);
		resultMap.put("ResultSize", 20);
		resultMap.put("StartIndex", 1);
		resultMap.put("EndIndex", 10);
		resultMap.put("UsedTime", 200);
		resultMap.put("CurPage", 1);
		resultMap.put("PageCount", 10);
		resultMap.put("CollId", "13");
		resultMap.put("Rows", 10);
		resultMap.put("Query", "迪信通");
		
		System.out.println(f.exportXmlString("236fd6d78d74465dad78c86fb52e2119.dat", resultMap));
//		dataMap.put("resultSize", new Integer(1));
//		dataMap.put("startIndex", new Integer(1));
//		dataMap.put("endIndex", new Integer(5));
//		dataMap.put("searchTime", new Date().toLocaleString());
//		dataMap.put("current", new Integer(1));
//		dataMap.put("total", new Integer(10));
//		dataMap.put("collId", new Integer(1));
//		dataMap.put("rows", new Integer(10));
//		dataMap.put("rows", new Integer(10));
//		dataMap.put("query", "aaaa");
//		List lst_result=new ArrayList();
//		for (int i = 0; i < 5; i++) {
//			ListResult result=new ListResult();
//			result.setParam1(i+"");
//			result.setParam2(i*2+"");
//			result.setParam3(i*3+"");
//			result.setParam4(i*4+"");
//			result.setParam5(i*5+"");
//			result.setParam6(i*6+"");
//			result.setParam7(i*7+"");
//			lst_result.add(result);
//		}
		//dataMap.put("lst_result", lst_result);
		
		//f.bulidXml("svr222", dataMap);
		

	}

}
