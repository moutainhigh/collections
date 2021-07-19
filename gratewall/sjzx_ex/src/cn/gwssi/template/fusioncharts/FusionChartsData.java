package cn.gwssi.template.fusioncharts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FusionChartsData{
	/**
	 * �õ��������������FushionChart����
	 * 
	 * @param param
	 * @param online_num
	 * @param scsj
	 */
	public String getMaxOnlineData(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("bgColor", "ffffff,3373C5");
		map.put("bgAngle", "30");
		map.put("numberSuffix", "��");
		map.put("baseFontColor", "004B97");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showValues", "0");
		map.put("showShadow", "0");
		map.put("divLineIsDashed", "1");
		map.put("useRoundEdges", "1");
		map.put("canvasBorderThickness", "0");
		map.put("showBorder", "0");
		map.put("canvasBgAngle", "45");
		map.put("use3DLighting", "1");
		map.put("lineColor", "FF5809");
		map.put("numDivLines", "3");
		map.put("showLabels", "1");
		map.put("canvasBorderColor", "A3D1D1");
		map.put("chartLeftMargin", "30");
		map.put("outCnvBaseFontSize", "12");

		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * �õ��������������FushionChart����
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getMaxOnline24Data(String[] online_num, String[] scsj)
	{
		int y, m, d;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH) + 1;
		d = cal.get(Calendar.DATE);
		String title = y + "��" + m + "��" + d + "��" + "��������ͳ�����";
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("caption", title);
		map.put("bgColor", "ffffff,3373C5");
		map.put("bgAngle", "30");
		map.put("numberSuffix", "��");
		map.put("baseFontColor", "004B97");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showValues", "0");
		map.put("showShadow", "0");
		map.put("divLineIsDashed", "1");
		map.put("useRoundEdges", "1");
		map.put("canvasBorderThickness", "0");
		map.put("showBorder", "0");
		map.put("canvasBgAngle", "45");
		map.put("use3DLighting", "1");
		map.put("lineColor", "FF5809");
		map.put("numDivLines", "3");
		// map.put("showLabels", "0");
		map.put("canvasBorderColor", "A3D1D1");
		map.put("chartLeftMargin", "30");
		map.put("outCnvBaseFontSize", "12");

		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * ���10��ʹ�ô���ǰ5�������ƣ�
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getTop5(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("rotateYAxisName", "0");
		map.put("rotateLabels", "1");
		map.put("baseFontColor", "004B97");
		map.put("bgColor", "ffffff,E5EEF9");
		map.put("bgAngle", "45");
		map.put("showAlternateVGridColor", "1");
		map.put("slantLabels", "1");
		map.put("numDivLines", "10");
		map.put("numbersuffix", "��");
		map.put("showValues", "0");
		map.put("divLineIsDashed", "1");
		map.put("useRoundEdges", "1");
		map.put("canvasBorderThickness", "0");
		map.put("showBorder", "0");
		map.put("outCnvBaseFontSize", "12");
		map.put("formatNumberScale", "0");
		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * �õ����10��ϵͳʹ���������ǰ5��
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getOrganization(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
	
		map.put("rotateYAxisName", "0");
		map.put("rotateLabels", "1");
		map.put("baseFontColor", "004B97");
		map.put("bgColor", "ffffff,E5EEF9");
		map.put("bgAngle", "45");
		map.put("showAlternateVGridColor", "1");
		map.put("slantLabels", "1");
		map.put("numDivLines", "10");
		map.put("numbersuffix", "��");
		map.put("showValues", "0");
		map.put("divLineIsDashed", "1");
		map.put("useRoundEdges", "1");
		map.put("canvasBorderThickness", "0");
		map.put("showBorder", "0");
		map.put("outCnvBaseFontSize", "12");
		map.put("formatNumberScale", "0");
		
		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * �õ����10��ϵͳʹ�����������
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getFunc(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("rotateYAxisName", "0");
		map.put("baseFontColor", "004B97");
		map.put("bgColor", "ffffff");
		map.put("bgAngle", "60");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showBorder", "0");
		map.put("showValues", "0");
		map.put("baseFontSize", "12");

		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * �õ����10��ϵͳʹ�����
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getSystemUse(String[] login_count, String[] query_count,
			String[] download_count, String[] scsj)
	{
		FusionChartsUtil xml = new FusionChartsUtil();
		HashMap map = new HashMap();
		map.put("rotateYAxisName", "0");
		map.put("baseFontColor", "004B97");
		map.put("bgAngle", "60");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showBorder", "0");
		map.put("showValues", "0");
		map.put("chartLeftMargin", "30");
		map.put("borderColor", "A3D1D1");
		map.put("canvasBorderColor", "A3D1D1");
		map.put("borderThickness ", "0");
		map.put("outCnvBaseFontSize", "12");
		map.put("formatNumberScale", "0");
		map.put("divLineIsDashed", "1");
		map.put("bgColor", "ffffff,3373C5");
		map.put("bgAngle", "30");

		List list = new ArrayList();
		list.add(login_count);
		list.add(query_count);
		list.add(download_count);

		String[] yName = new String[] { "��½����", "��ѯ����", "���ش���" };

		String chartXML = xml.getMsBarXml(map, list, scsj, yName);
		return chartXML;
	}

	/**
	 * ���10��ϵͳ�������
	 * 
	 * @param param
	 * @param online_num
	 * @param scsj
	 */
	public String getDataIncre(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("bgColor", "ffffff,E5EEF9");
		map.put("bgAngle", "60");
		map.put("baseFontColor", "004B97");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("labelDisplay", "STAGGER");
		map.put("showValues", "0");
		map.put("showShadow", "0");
		map.put("divLineIsDashed", "1");
		map.put("useRoundEdges", "1");
		map.put("canvasBorderThickness", "0");
		map.put("showBorder", "0");
		map.put("canvasBgAngle", "45");
		//map.put("lineColor", "8E8E8E");
		map.put("lineColor", "FF5809");
		map.put("numDivLines", "3");
		map.put("showLabels", "1");
		map.put("canvasBorderColor", "E5EEF9");
		map.put("chartLeftMargin", "30");
		//map.put("lineThickness ", "3");
		map.put("outCnvBaseFontSize", "12");

		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;

	}

	/**
	 * ���10�������ǰ10��
	 * 
	 * @param param
	 * @param online_num
	 * @param scsj
	 */
	public String getDataIncreTop10(String[] online_num, String[] scsj)
	{
		XMLUtil xml = new XMLUtil();
		HashMap map = new HashMap();
		map.put("showBorder", "0");
		map.put("showValues", "0");
		map.put("bgColor", "ffffff,E5EEF9");
		map.put("imageSave", "1");
		map.put("useRoundEdges", "1");
		map.put("imageSaveURL", "");
		map.put("baseFontColor", "004B97");
		map.put("canvasBorderColor", "E5EEF9");
		map.put("plotBorderColor", "BEBEBE");
		map.put("bgColor", "ffffff,E5EEF9");
		map.put("bgAngle", "60");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("outCnvBaseFontSize", "12");
	
		map.put("canvasBottomMargin", "5");

		String chartXML = xml.getSimpleBarXml(map, online_num, scsj);
		return chartXML;
	}

	/**
	 * �õ����10��ϵͳʹ�����������
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getMarketEntityFull(String[] nz_count, String[] wz_count,
			String[] jg_count, String[] gt_count, String[] scsj)
	{
		FusionChartsUtil xml = new FusionChartsUtil();
		HashMap map = new HashMap();
		map.put("rotateYAxisName", "0");
		map.put("baseFontColor", "004B97");
		map.put("bgColor", "ffffff");
		map.put("bgAngle", "60");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showBorder", "0");
		map.put("showValues", "0");
		map.put("chartLeftMargin", "30");
		map.put("borderColor", "A3D1D1");
		map.put("canvasBorderColor", "E5EEF9");
		map.put("borderThickness ", "0");
		map.put("outCnvBaseFontSize", "12");

		List list = new ArrayList();
		list.add(nz_count);
		list.add(wz_count);
		list.add(jg_count);
		list.add(gt_count);

		String[] yName = new String[] { "����", "����", "�������", "���幤�̻�" };

		String chartXML = xml.getMsBarXml(map, list, scsj, yName);
		return chartXML;
	}

	/**
	 * �õ����10��ϵͳʹ�����������
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return Column3D.swf
	 */
	public String getMarketEntityIncre(String[] nz_count, String[] wz_count,
			String[] jg_count, String[] gt_count, String[] scsj)
	{
		FusionChartsUtil xml = new FusionChartsUtil();
		HashMap map = new HashMap();
		map.put("baseFontColor", "004B97");
		map.put("bgColor", "ffffff");
		map.put("bgAngle", "60");
		map.put("rotateLabels", "1");
		map.put("slantLabels", "1");
		map.put("showBorder", "0");
		map.put("showValues", "0");
		map.put("chartLeftMargin", "30");
		map.put("borderColor", "A3D1D1");
		map.put("canvasBorderColor", "E5EEF9");
		map.put("borderThickness ", "0");
		map.put("outCnvBaseFontSize", "12");

		List list = new ArrayList();
		list.add(nz_count);
		list.add(wz_count);
		list.add(jg_count);
		list.add(gt_count);

		String[] yName = new String[] { "����", "����", "�������", "���幤�̻�" };

		String chartXML = xml.getMsBarXml(map, list, scsj, yName);
		return chartXML;
	}
}
