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
	 * ����������ͼ����ͼ������ͼ
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
		// �������������
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
		// map.put("caption", "����ͳ��");//������
		// map.put("subCaption", "���������ͳ��");//������
		// map.put("basefontsize", "12");//�����С
		// map.put("xAxisName", "���������");//x������ֵ
		// map.put("decimalPrecision", "0");//y������ֵ
		// map.put("showValues", "0");//�Ƿ���ͼ������ʾ����
		// String[] values=new String[]{"2","1","3"};
		// String[] column=new String[]{"a","b","c"};
	}

	/**
	 * ����������ͼ����ͼ������ͼ
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
		// �������������
		Iterator it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			xml.addAttribute(chart, entry.getKey().toString(), entry.getValue()
					.toString());
		}
		Element categories = xml.addNode(chart, "categories");
		// ��������
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
		map.put("caption", "����ͳ��");// ������
		map.put("subCaption", "���������ͳ��");// ������
		map.put("basefontsize", "12");// �����С
		map.put("xAxisName", "���������");// x������ֵ
		map.put("decimalPrecision", "0");// y������ֵ
		map.put("showValues", "0");// �Ƿ���ͼ������ʾ����
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
