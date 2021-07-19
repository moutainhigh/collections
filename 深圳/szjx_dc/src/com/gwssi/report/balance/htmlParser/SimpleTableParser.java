package com.gwssi.report.balance.htmlParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gwssi.report.balance.api.ReportModel;

/**
 * @author wuyincheng ,Oct 31, 2016
 * 单TABLE节点解析，
 * 参考jsoup:  http://www.open-open.com/jsoup/
 */
public class SimpleTableParser implements HTMLTableParser{

	@Override
	public ReportModel parse(String htmlContext) {
		if(StringUtils.isBlank(htmlContext))
			return null;
		final Grid [][] tables = checkOut(htmlContext);
		String [][] result = null;
		int [] size = getStartPoint(tables);
		if(tables != null && tables.length > 0 ){
			Grid temp = null;
			result = new String[tables.length - size[0]][tables[0].length - size[1]];
			int i,j;
			for(i = 0; i < result.length; i ++){
				for(j = 0; j < result[0].length; j ++){
					temp = tables[i + size[0]][j + size[1]];
					result[i][j] = temp == null || temp.content == null ? "" : temp.content.replaceAll(",", "");
				}
			}
		}
		return new SimpleReportModel(result);
	}
	
	//定位数据项起始位置
	protected int [] getStartPoint(Grid[][] con){
		int p [] = new int[]{-1,-1};
		for(int i =1; i < con.length; i ++){
			for(int j = 1; j < con[i].length; j ++){
				if(con[i - 1][j] != null && con[i - 1][j].content != null 
				   && (con[i - 1][j].content.trim().equals("01") || con[i - 1][j].content.trim().equals("1")) &&
				   con[i][j - 1] != null && con[i][j - 1].content != null 
				   && (con[i][j - 1].content.trim().equals("01") || con[i][j - 1].content.trim().equals("1")) ){
				    p[0] = i;
					p[1] = j;
					return p;
				}
			}
		}
		return p;
	}
	
	//获取table大小
	protected int[] getSize(Element element) {
		final Elements trs = element.getElementsByTag("tr"); 
		Elements tds = null;
		int rows = trs.size();
		int columns = 0;
		int temp = -1;
		for(Element e : trs){
			tds = e.getElementsByTag("td");
			temp = 0;
			for(Element td : tds){
				try{ temp += (Integer.parseInt(td.attr("colspan")) > 1 ?
							 Integer.parseInt(td.attr("colspan")) - 1 : 0); 
				} catch(Exception exce){}
			}
			temp += tds.size();
			columns = temp > columns ? temp : columns;
		}
		return new int[]{rows, columns};
	}
	
	
	//定位table
	protected Grid[][] checkOut(String htmlContext) {
		
		Document doc = Jsoup.parseBodyFragment(htmlContext);
		Elements ea = doc.getElementsContainingText("甲");
		Elements eb = doc.getElementsContainingText("乙");//某些附表是 "甲,丙" @TODO
		Element tableA = ea.get(ea.size() - 1).parent();
		Element tableB = eb.get(eb.size() - 1).parent();
		while(tableA != null && !tableA.nodeName().equalsIgnoreCase("table")){
			tableA = tableA.parent();
		}
		while(tableB != null && !tableB.nodeName().equalsIgnoreCase("table")){
			tableB = tableB.parent();
		}
		//Element table = doc.getElementsByTag("table").get(0);//获取table节点
		Element table = null;
		if(tableA == tableB) //定位table节点
			table = tableA;
		//多个列表
		int size[] = getSize(table);//获取表格大小
		Grid context[][] =  new Grid [size[0]][size[1]];
		html2Java(table, context);
		return context;
	}

	//单个table建立二维模型
	protected void html2Java(Element table, Grid [][] context){
		int row = 0;
		int col = 0;
		Grid temp = null;
		for(Element tr : table.getElementsByTag("tr")) {
			col = 0;
			for(Element td : tr.getElementsByTag("td")) {
				//get first not fill grid ；
				//找到与td对应的二维数组中位置(rowspan影响会产生填充格)
				do{
					if(col == context[row].length)
						break ;
					temp = context[row][col ++];
				}while(temp != null && temp.isFill);
				col --;
				temp = context[row][col] == null ? new Grid() : context[row][col];
				//colspan & rowspan (both)
				int width = 1;
				int height = 1;
				try{width = Integer.parseInt(td.attr("colspan"));}catch(Exception e){}
				try{height = Integer.parseInt(td.attr("rowspan"));}catch(Exception e){}
				if(width > 1 ||  height > 1){
					String content = "";
					for(int j = 0; j < height; j ++){
						for(int i = 0; i < width; i ++){
							if(i + col < context[row].length && j + row < context.length){
								Grid g = new Grid(true);
								if(i == 0 && j == 0){
									g.isFill = false;
									content = (td.text() == null ? "" : td.text().replaceAll("<p>", "").replace("</p>", ""));
//									System.out.println(content);
								}
								//set content -> center
								if( j == ((height + 1) / 2 - 1) &&  i == ((width + 1) / 2 - 1)){
									if(content != null){
										content.replaceAll(",", "").replaceAll("%", "");
									}
									g.content = content;
								}
								context[row + j][col + i] = g;
							}
						}
					}
				}else{
					temp.content = (td.text() == null ? "" : td.text().replaceAll("<p>", "").replace("</p>", ""));
					context[row][col] = temp;
				}
				col ++;
			}
			row ++;
		}
	}
	
	public static void main(String[] args) throws Exception{
		HTMLTableParser parser = new SimpleTableParser();
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
