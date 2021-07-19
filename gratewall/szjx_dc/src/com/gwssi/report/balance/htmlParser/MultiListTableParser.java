package com.gwssi.report.balance.htmlParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

/**
 * @author wuyincheng ,Nov 1, 2016
 * @ThreadNotSafe
 * 多列表解析(RE CREATE EACH TIME~)
 */
public class MultiListTableParser extends SimpleTableParser{
	
	//缓存table节点，为填充数据做准备
	private List<Element> tables = new ArrayList<Element>();

	@Override
	protected void html2Java(Element table, Grid[][] context) {
		int [] tempSize = null;
		Grid [][] tempContext = null;
		int indexRow = 0;
		for(Element e : tables) {
			tempSize = super.getSize(e);
			tempContext = new Grid[tempSize[0]][tempSize[1]];
			super.html2Java(e, tempContext);
			for(Grid [] t : tempContext){
				context[indexRow ++] = t;
			}
		}
	}
	
	
	@Override
	protected int[] getSize(Element element) {
		//并列table解析, 分两种:1.同<td>标签下table并列，2.同<tr><td>下table并列
		tables.clear();
		tables.add(element);
		Element temp = element;
		//同级table,空指针catch
		try{
			while((temp = temp.nextElementSibling()) != null && 
					"table".equalsIgnoreCase(temp.nodeName()) &&
					temp.getElementsByTag("tr") != null){
				tables.add(temp);
			}
		}catch(Exception ex){//DO NOTHING!
		}
		//不同级的,空指针d直接catch不处理
		temp = element;
		try{
			while((temp = temp.parent().parent().nextElementSibling().child(0).child(0)) != null &&
					"table".equalsIgnoreCase(temp.nodeName())){
				tables.add(temp);
			}
		}catch(Exception ex){
			//DO NOTHING!
		}
		int size [] = super.getSize(element);//列只需计算一次
		int rows = size[0];
		for(int i = 1; i < tables.size(); i ++){
			rows += tables.get(i).getElementsByTag("tr").size(); 
		}
		return new int[]{rows, size[1]};
	}
	
	public static void main(String[] args) throws Exception {
		HTMLTableParser parser = new MultiListTableParser();
		StringBuilder context = new StringBuilder();
		String temp = null;
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/wu/Documents/changcheng/report/平衡关系/竞争执法2表")));
		while((temp = br.readLine()) != null){
			context.append(temp);
		}
		SimpleReportModel re = (SimpleReportModel) parser.parse(context.toString());
		re.print();
	}

}
