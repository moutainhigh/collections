/**
 * 
 */
package com.gwssi.dw.dq.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.gwssi.dw.dq.ChartDataFactory;

/**
 * <p>
 * Title: 灵活查询数据提供类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) Sep 17, 2008
 * </p>
 * <p>
 * Company: 长城软件
 * </p>
 * 
 * @author zhouyi
 * @version 1.0
 */
public class ChartAction extends DispatchAction
{

	public ActionForward line(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, ActionException
	{
		String fileName = "";
		String imgPath = "";
		String categoryName = getParameter(request, "categoryName");
		String title = getParameter(request, "title");
		System.out.println("line--------------------------");
		System.out.println("categoryName:" + categoryName);
		System.out.println("title:" + title);
		System.out.println("series:" + request.getParameter("series"));
		System.out.println("categorys:" + request.getParameter("categorys"));
		System.out.println("values:" + request.getParameter("values"));

		JFreeChart chart = ChartDataFactory.createLineChart(
				parseRequestDataset(request), title, "" , categoryName);
		fileName = ServletUtilities.saveChartAsPNG(chart, 800, 375, request
				.getSession());
		imgPath = request.getContextPath() + "/servlet/DisplayChart?filename="
				+ fileName;
		response.getOutputStream().write(imgPath.getBytes());
		// request.setAttribute("xmlString", imgPath);
		return null;// mapping.findForward("success");
	}

	public ActionForward bar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, ActionException
	{
		String fileName = "";
		String imgPath = "";
		String categoryName = getParameter(request, "categoryName");
		String title = getParameter(request, "title");
		System.out.println("user.language:"
				+ System.getProperty("user.language"));
		System.out.println("user.region:" + System.getProperty("user.region"));
		System.out.println("file.encoding:"
				+ System.getProperty("file.encoding"));
		System.out.println("os.version:" + System.getProperty("os.version"));
		System.out.println("bar------------------------");
		System.out.println("categoryName:" + categoryName);
		System.out.println("title:" + title);
		System.out.println("series:" + request.getParameter("series"));
		System.out.println("categorys:" + request.getParameter("categorys"));
		System.out.println("values:" + request.getParameter("values"));

		JFreeChart chart = ChartDataFactory.createBarChart(
				parseRequestDataset(request), title, categoryName);
		fileName = ServletUtilities.saveChartAsPNG(chart, 800, 375, request
				.getSession());
		imgPath = request.getContextPath() + "/servlet/DisplayChart?filename="
				+ fileName;
		response.getOutputStream().write(imgPath.getBytes());
		// request.setAttribute("xmlString", imgPath);
		return null;// mapping.findForward("success");
	}

	public ActionForward pie(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, ActionException
	{
		String fileName = "";
		String imgPath = "";
		String categoryName = getParameter(request, "categoryName");
		System.out.println("pie------------------------");
		System.out.println("categoryName:" + categoryName);
		System.out.println("title:" + request.getParameter("title"));
		System.out.println("series:" + request.getParameter("series"));
		System.out.println("categorys:" + request.getParameter("categorys"));
		System.out.println("values:" + request.getParameter("values"));

		JFreeChart chart = ChartDataFactory.createPieChart(
				parseRequestDataset(request), categoryName);
		fileName = ServletUtilities.saveChartAsPNG(chart, 800, 375, request
				.getSession());
		imgPath = request.getContextPath() + "/servlet/DisplayChart?filename="
				+ fileName;
		response.getOutputStream().write(imgPath.getBytes());
		// request.setAttribute("xmlString", imgPath);
		return null;// mapping.findForward("success");
	}

	/**
	 * @param request
	 * @param paramter
	 * @return
	 */
	private String getParameter(HttpServletRequest request, String paramter)
	{
		String value = null;
		value = request.getParameter(paramter);
		return value;
	}

	private CategoryDataset parseRequestDataset(HttpServletRequest request)
			throws ActionException
	{
		String seriesParamter = getParameter(request, "series");
		String categorysParamter = getParameter(request, "categorys");
		String valuesParamter = getParameter(request, "values");
		// System.out.println("图形参数:"+seriesParamter+":"+categorysParamter+":"+valuesParamter);
		String[] series = seriesParamter.split(",");
		String[] categorys = categorysParamter.split(",");
		String allValue[] = valuesParamter.split(",");

		int serieCount = series.length;
		int categoryCount = categorys.length;
		Double[][] values = new Double[serieCount][categoryCount];
		int valueIndex = 0;
		for (int i = 0; i < serieCount; i++) {
			for (int j = 0; j < categoryCount; j++) {
				valueIndex = j * serieCount + i;
				try {
					values[i][j] = new Double(allValue[valueIndex]);
				} catch (Exception e) {
					values[i][j] = new Double(0);
				}
			}
		}

		return createDataset(series, categorys, values);
	}

	private CategoryDataset createDataset(String[] series, String[] categorys,
			Double[][] values)
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (int i = 0; i < series.length; i++) {
			for (int j = 0; j < categorys.length; j++) {
				defaultcategorydataset.addValue(values[i][j], series[i],
						categorys[j]);
			}
		}
		return defaultcategorydataset;
	}

	public static void main(String[] args)
	{
		String[] series = new String[] { "序列1", "序列2" };
		String[] categorys = new String[] { "序列1"};
		Double[][] values = { { new Double(1) },
				{ new Double(3)} };
		ChartAction chartAction = new ChartAction();
//		JFreeChart chart =ChartDataFactory.bulidBarChart(chartAction.createDataset(series,
//				categorys, values), "测试报表", "x轴","y轴",true);
		JFreeChart chart =ChartDataFactory.createPieChart(chartAction.createDataset(series,
				categorys, values), "测试");
		
		FileOutputStream fos_jpg;
		try {
			fos_jpg = new FileOutputStream("D:\\temp\\test.png");
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 300, 200);
			//ChartUtilities.writeChartAsJPEG(fos_jpg, chart, 300, 200);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
	}
}
