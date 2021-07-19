package com.gwssi.dw.dq;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.TableOrder;

public class ChartDataFactory
{
	public static XYDataset getXYSeriesDataset(String[] series,
			Double[][] values)
	{
		XYSeries xyseries;
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		for (int i = 0; i < series.length; i++) {
			xyseries = new XYSeries(series[i]);
			for (int j = 0; j < values[i].length; j++) {
				xyseries.add(j, values[i][j]);
			}
			xyseriescollection.addSeries(xyseries);
		}
		return xyseriescollection;
	}

	public static CategoryDataset createDataset(String[] series,
			String[] categorys, Double[][] values)
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

	public static JFreeChart createLineChart(CategoryDataset categorydataset,
			String title, String xName,String yName)
	{
		JFreeChart jfreechart = ChartFactory.createLineChart(title,
				xName, yName, categorydataset, PlotOrientation.VERTICAL,
				true, true, true);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.white);
		categoryplot.setRangeGridlinePaint(Color.lightGray);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.57));//
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		int seriesCount = categorydataset.getRowCount();

		CategoryURLGenerator generator = null;
		generator = new StandardCategoryURLGenerator();

		for (int i = 0; i < seriesCount; i++) {
			lineandshaperenderer.setSeriesShapesVisible(i, true);
			lineandshaperenderer.setSeriesShapesFilled(i, true);
			lineandshaperenderer.setSeriesItemURLGenerator(i, generator);
		}
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);

		lineandshaperenderer.setBaseFillPaint(Color.white);

		return jfreechart;
	}

	/**
	 * 生成柱状图
	 * 
	 * @param categorydataset
	 * @param categoryName
	 * @return
	 */
	public static JFreeChart createBarChart(CategoryDataset categorydataset,
			String title, String categoryName)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart3D(title,
				categoryName, "值", categorydataset, PlotOrientation.VERTICAL,
				true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setDomainGridlinesVisible(true);
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.57));
		categoryaxis.setCategoryMargin(0.1D);
		BarRenderer3D barrenderer3d = (BarRenderer3D) categoryplot
				.getRenderer();
		barrenderer3d.setDrawBarOutline(false);
		return jfreechart;

	}

	/**
	 * 生成柱状图
	 * 
	 * @param categorydataset
	 * @param categoryName
	 * @return
	 */
	public static JFreeChart bulidBarChart(CategoryDataset categorydataset,
			String title, String xName, String yName, boolean vertical)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart3D(title, xName,
				yName, categorydataset,
				vertical == true ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setDomainGridlinesVisible(true);
		CategoryAxis domainAxis  = categoryplot.getDomainAxis();
		ValueAxis numberaxis = categoryplot.getRangeAxis();

		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.57));
		domainAxis.setCategoryMargin(0.1D);
		/*------设置X轴坐标上的文字-----------*/   
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));   
		/*------设置X轴的标题文字------------*/   
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));  
		/*------设置Y轴坐标上的文字-----------*/   
		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12)); 
		/*------设置Y轴的标题文字------------*/   
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
		jfreechart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12)); 
		
		BarRenderer3D barrenderer3d = (BarRenderer3D) categoryplot
				.getRenderer();
		barrenderer3d.setDrawBarOutline(false);
		return jfreechart;

	}

	/**
	 * 生成多饼图
	 * 
	 * @param categorydataset
	 * @param categoryName
	 * @return
	 */
	public static JFreeChart createPieChart(CategoryDataset categorydataset,
			String categoryName)
	{
		JFreeChart jfreechart = ChartFactory.createMultiplePieChart3D(
				categoryName, categorydataset, TableOrder.BY_COLUMN, false,
				true, false);
		jfreechart.setBackgroundPaint(Color.white);//
		MultiplePiePlot multiplepieplot = (MultiplePiePlot) jfreechart
				.getPlot();
		JFreeChart jfreechart1 = multiplepieplot.getPieChart();
		multiplepieplot.setLimit(0.00999999999999D);
		PiePlot pieplot = (PiePlot) jfreechart1.getPlot();
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} ({2})", NumberFormat.getNumberInstance(), NumberFormat
						.getPercentInstance()));
		pieplot.setLabelFont(new Font("SansSerif", 0, 8));
		pieplot.setMaximumLabelWidth(0.17999999999999999D);
		pieplot.setCircular(true);
		pieplot.setIgnoreNullValues(true);
		pieplot.setIgnoreZeroValues(true);
		pieplot.setForegroundAlpha(0.75F);
		return jfreechart;
	}

	private static Paint[] createPaint()
	{
		Paint apaint[] = new Paint[5];
		apaint[0] = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F,
				Color.white);
		apaint[1] = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F,
				Color.white);
		apaint[2] = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F,
				Color.white);
		apaint[3] = new GradientPaint(0.0F, 0.0F, Color.orange, 0.0F, 0.0F,
				Color.white);
		apaint[4] = new GradientPaint(0.0F, 0.0F, Color.magenta, 0.0F, 0.0F,
				Color.white);
		return apaint;
	}

	static class CustomCylinderRenderer extends CylinderRenderer
	{

		public Paint getItemPaint(int i, int j)
		{
			return colors[j % colors.length];
		}

		private Paint	colors[];

		public CustomCylinderRenderer(Paint apaint[])
		{
			colors = apaint;
		}
	}

}
