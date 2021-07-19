package com.gwssi.dw.dq.business;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.TableOrder;

public class YouiChartFactory {
	public static XYDataset getXYSeriesDataset(String[] series,
			Double[][] values) {
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

	/**
	 * 创建折线图
	 * 
	 * @param categorydataset
	 * @param category
	 * @return
	 */
	public static JFreeChart createLineChart(CategoryDataset categorydataset,
			String category) {
		JFreeChart jfreechart = ChartFactory.createLineChart("折线图", category,
				"值", categorydataset, PlotOrientation.VERTICAL, true, true,
				true);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.white);
		categoryplot.setRangeGridlinePaint(Color.lightGray);
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
	 * 创建二维数据图
	 * 
	 * @param xydataset
	 * @return
	 */
	public static JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart(
				"Line Chart Demo 2", "X", "Y", xydataset,
				PlotOrientation.VERTICAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
				.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setBaseShapesFilled(true);
		NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jfreechart;
	}

	/**
	 * 创建柱状图
	 * 
	 * @param categorydataset
	 * @param categoryName
	 * @return
	 */
	public static JFreeChart createBarChart(CategoryDataset categorydataset,
			String categoryName) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D("柱状图", categoryName, "值", categorydataset, PlotOrientation.VERTICAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
        categoryplot.setDomainGridlinesVisible(true);
        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
        categoryaxis.setCategoryMargin(0.1D);
        BarRenderer3D barrenderer3d = (BarRenderer3D)categoryplot.getRenderer();
        barrenderer3d.setDrawBarOutline(false);
		return jfreechart;
	}

	/**
	 * 生成多饼图
	 * @param categorydataset
	 * @param categoryName
	 * @return
	 */
	public static JFreeChart createPieChart(CategoryDataset categorydataset,String categoryName) {
		JFreeChart jfreechart = ChartFactory.createMultiplePieChart3D(
				categoryName, categorydataset,
				TableOrder.BY_COLUMN, false, true, false);
		jfreechart.setBackgroundPaint(Color.white);//
		MultiplePiePlot multiplepieplot = (MultiplePiePlot) jfreechart
				.getPlot();
		JFreeChart jfreechart1 = multiplepieplot.getPieChart();
		multiplepieplot.setLimit(0.10000000000000001D);
		PiePlot pieplot = (PiePlot) jfreechart1.getPlot();
		pieplot.setForegroundAlpha(0.8F);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
		pieplot.setLabelFont(new Font("SansSerif", 0, 10));
		pieplot.setMaximumLabelWidth(0.17999999999999999D);
		return jfreechart;
	}

}

