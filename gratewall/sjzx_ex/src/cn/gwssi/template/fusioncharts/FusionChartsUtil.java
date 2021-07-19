package cn.gwssi.template.fusioncharts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class FusionChartsUtil
{
	/**
	 * 单序列柱形图、饼图、曲线图
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getSimpleBarXml(Map param, String[] values, String[] column)
	{
		XMLUtil xml = new XMLUtil();
		Element chart = xml.addRoot("chart");
		// 设置整体的属性
		Iterator it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			xml.addAttribute(chart, entry.getKey().toString(), entry.getValue()
					.toString());
		}
		for (int i = 0; i < values.length; i++) {
			Element set = xml.addNode(chart, "set");
			set.addAttribute("name", column[i]);
			set.addAttribute("value", values[i]);
		}
		return xml.getXML();
		// HashMap map=new HashMap();
		// map.put("caption", "访问统计");//主标题
		// map.put("subCaption", "浏览器类型统计");//副标题
		// map.put("basefontsize", "12");//字体大小
		// map.put("xAxisName", "浏览器类型");//x轴坐标值
		// map.put("decimalPrecision", "0");//y轴坐标值
		// map.put("showValues", "0");//是否在图形上显示数字
		// String[] values=new String[]{"2","1","3"};
		// String[] column=new String[]{"a","b","c"};
	}

	/**
	 * 多序列柱形图、饼图、曲线图
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getMsBarXml(Map param, List values, String[] column,String[] yName)
	{
		XMLUtil xml = new XMLUtil();
		Element chart = xml.addRoot("chart");
		// 设置整体的属性
		Iterator it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			xml.addAttribute(chart, entry.getKey().toString(), entry.getValue()
					.toString());
		}
		Element categories = xml.addNode(chart, "categories");
		// 设置序列
		for (int i = 0; i < column.length; i++) {
			Element set = xml.addNode(categories, "category");
			set.addAttribute("label", column[i]);
		}

		for (int i = 0; i < values.size(); i++) {
			Element dataset = xml.addNode(chart, "dataset");
			dataset.addAttribute("seriesName", yName[i]);
			String[] value = (String[]) values.get(i);
			for (int j = 0; j < value.length; j++) {
				Element set = xml.addNode(dataset, "set");
				set.addAttribute("value", value[j]);
			}
		}
		return xml.getXML();
	}

	public static void main(String[] args)
	{
		FusionChartsUtil xml = new FusionChartsUtil();
		HashMap map = new HashMap();
		map.put("caption", "访问统计");// 主标题
		map.put("subCaption", "浏览器类型统计");// 副标题
		map.put("basefontsize", "12");// 字体大小
		map.put("xAxisName", "浏览器类型");// x轴坐标值
		map.put("decimalPrecision", "0");// y轴坐标值
		map.put("showValues", "0");// 是否在图形上显示数字
		map.put("rotateYAxisName", "0");
		map.put("rotateLabels", "0");
		map.put("baseFontColor", "004B97");
		map.put("useRoundEdges", "1");
		String[] values1 = new String[] { "2", "1", "3" };
		String[] values2 = new String[] { "5", "2", "1" };
		String[] values3 = new String[] { "4", "3", "5" };
		List list=new ArrayList();
		list.add(values1);
		list.add(values2);
		list.add(values3);
		String[] column = new String[] { "a", "b", "c" };
		String[] yName = new String[] { "a1", "b1", "c1" };
		//System.out.println("xml:"+xml.getSimpleBarXml(map, list, column));
		System.out.println("xml:"+xml.getMsBarXml(map, list, column,yName));
	}
}
