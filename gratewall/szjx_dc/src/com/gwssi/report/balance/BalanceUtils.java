package com.gwssi.report.balance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 工具类
 * @author wuyincheng ,Oct 17, 2016
 */
public class BalanceUtils {
	
	private final static String REGEX_NUM =  "\\d*";;
	
	private final static String REGEX_OPER = "[+-/*/>=<!]*";
	
	private final static Boolean FALSE = new Boolean(false);
	
	private final static HashSet<String> OPERS = new HashSet<String>(){
		private static final long serialVersionUID = -8376104252956473995L;
		{
			add("==");add(">=");add("<=");add(">");add("<");
		}
	};
	
	//校验
	public static void checkBalance(StringBuilder result,String htmlContext, BalanceInfo balance){
		if(htmlContext == null || "".equals(htmlContext.trim()) || result == null)
		    return ;
		final Grid[][] con = checkOut(htmlContext);
		final int [] p = getStartPoint(con);
		if(p[0] == -1 || p[1] == -1){
			System.out.println("Can't find the start point...");
			return ;
		}
//		System.out.println(Arrays.toString(p));
		//行平衡（横向）
		checkRowBalance(result, balance.getRowBalanceInfo(), p, con);
		checkColumnBalance(result, balance.getColumnBalanceInfo(), p, con);
		checkGridBalance(result, balance.getGridBalanceInfo(), p ,con);
		//return result.toString();
	}
	
	//单元格
	private static void checkGridBalance(StringBuilder result, List<String> gridBalanceInfo, int[] startPoint, Grid[][] con) {
		if(gridBalanceInfo == null)
			return ;
		final ScriptEngineManager sem = new ScriptEngineManager();
		final ScriptEngine engine = sem.getEngineByName("JavaScript");
		Pattern p = null;
		Matcher m = null;
		StringBuilder exp = null;
		int index;
		for(String r : gridBalanceInfo) {
			exp = new StringBuilder();
			index = 0;
			p = Pattern.compile(REGEX_NUM);
			m = p.matcher(r);
			List<Integer> values = new ArrayList<Integer>();
			while (m.find()) {//获取所有坐标点
				if (!"".equals(m.group())){
					values.add(Integer.parseInt(m.group()));
				}
			}
			p = Pattern.compile(REGEX_OPER);
			m = p.matcher(r);
			List<String> opers = new ArrayList<String>();
			while (m.find()) {//获取所有操作符
				if (!"".equals(m.group()) && !",".equals(m.group())){
					opers.add(m.group());
				}
			}
//			System.out.println(values);
//			System.out.println(opers);
			exp.append("parseFloat((");
			exp.append(con[values.get(0) + startPoint[0] - 1][values.get(1) + startPoint[1] - 1]);
			for(int i = 2; i < values.size(); i += 2) {
				exp.append(isOper(opers.get(index)) ? ").toFixed(4))" + opers.get(index ++) + "parseFloat((": opers.get(index ++));
				exp.append(con[values.get(i) + startPoint[0] - 1][values.get(i + 1) - 1 + startPoint[1]]);
			}
			exp.append(").toFixed(4))");
			try {
				if(FALSE.equals(engine.eval(exp.toString()))){
					//result.append("Grid:[" + r + "] Checked Error!\n");
					result.append("单元格:[" + r + "]校验失败!\n");
				}
//				result.append(temp);
//				System.out.println("[" + r + "][" + exp.toString() + "]["+ engine.eval(exp.toString()) + "]");
			} catch (ScriptException e) {
				//e.printStackTrace();ch
				//System.out.println(r + ": " + e.getMessage());
			}
		}
	}

	//纵向
	private static void checkColumnBalance(StringBuilder result, List<String> columnBalance, int [] startPoint, Grid[][] con) {
		if(columnBalance == null)
			return ;
		final ScriptEngineManager sem = new ScriptEngineManager();
		final ScriptEngine engine = sem.getEngineByName("JavaScript");
		int column = 0;
		int index = 0;
		StringBuilder exp = null;//转换成数字运算表达式
		Pattern p = null;
		Matcher m = null;
		for(String r : columnBalance) {
			p = Pattern.compile(REGEX_NUM);
			m = p.matcher(r);
			List<Integer> indexs = new ArrayList<Integer>();
			while (m.find()) {//获取所有坐标点
				if (!"".equals(m.group())){
					column = Integer.parseInt(m.group().trim());
					indexs.add(column - 1 + startPoint[0]);
				}
			}
			p = Pattern.compile(REGEX_OPER);
			m = p.matcher(r);
			List<String> opers = new ArrayList<String>();
			while (m.find()) {//获取所有操作符
				if (!"".equals(m.group())){
					opers.add(m.group());
				}
			}
//			System.out.println(opers);
//			System.out.println(indexs);
			//替换坐标点值并校验
			for(int j = startPoint[1]; j < con[0].length; j ++){
				exp = new StringBuilder();
				index = 0;
				exp.append("parseFloat((");
				exp.append(con[indexs.get(0)][j]);
				for(int i = 1; i < indexs.size(); i ++) {
					exp.append(isOper(opers.get(index)) ? ").toFixed(4))" + opers.get(index ++) + "parseFloat((": opers.get(index ++));
					//exp.append(opers.get(index ++));
					exp.append(con[indexs.get(i)][j]);
				}
				exp.append(").toFixed(4))");
				try {
					if(FALSE.equals(engine.eval(exp.toString()))){
//						result.append("Column:[" + (j - startPoint[1] + 1) + "] Checked Error! Balanced Relation:["+r+"]	\n");
						result.append("纵向关系:[" + (j - startPoint[1] + 1) + "]校验失败! 平衡关系:["+r+"]	\n");
					}
//					result.append(temp);
//					System.out.println("[" + r + "][" + exp.toString() + "]["+ engine.eval(exp.toString()) + "]");
				} catch (ScriptException e) {
					//e.printStackTrace();ch
					//System.out.println(r + ": " + e.getMessage());
				}
			}
		}
	}
	
	//横向
	private static void checkRowBalance(StringBuilder result, List<String> rowBalance, int [] startPoint, Grid[][] con) {
		if(rowBalance == null)
			return ;
		
		int row = 0;
		int index = 0;
		StringBuilder exp = null;//转换成数字运算表达式
		Pattern p = null;
		Matcher m = null;
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("JavaScript");
		for(String r : rowBalance) {
			p = Pattern.compile(REGEX_NUM);
			m = p.matcher(r);
			List<Integer> indexs = new ArrayList<Integer>();
			while (m.find()) {//获取所有坐标点
				if (!"".equals(m.group())){
					row = Integer.parseInt(m.group().trim());
					indexs.add(row - 1 + startPoint[1]);
				}
			}
			p = Pattern.compile(REGEX_OPER);
			m = p.matcher(r);
			List<String> opers = new ArrayList<String>();
			while (m.find()) {//获取所有操作符
				if (!"".equals(m.group())){
					opers.add(m.group());
				}
			}
//			System.out.println(opers);
//			System.out.println(indexs);
			//替换坐标点值并校验
			for(int j = startPoint[0]; j < con.length; j ++){
				exp = new StringBuilder();
				index = 0;
				exp.append("parseFloat((");
				exp.append(con[j][indexs.get(0)]);
				for(int i = 1; i < indexs.size(); i ++) {
//					exp.append(opers.get(index ++));
					exp.append(isOper(opers.get(index)) ? ").toFixed(4))" + opers.get(index ++) + "parseFloat((": opers.get(index ++));
					exp.append(con[j][indexs.get(i)]);
				}
				exp.append(").toFixed(4))");
				try {
					if(FALSE.equals(engine.eval(exp.toString()))){
//						result.append("Row:[" + (j - startPoint[0] + 1) + "] Checked Error! Balanced Relation:["+r+"]	\n");
						result.append("横向关系:[" + (j - startPoint[0] + 1) + "]校验失败! 平衡关系:["+r+"]	\n");
					}
//					result.append(temp);
//					System.out.println("[" + r + "][" + exp.toString() + "]["+ engine.eval(exp.toString()) + "]");
				} catch (ScriptException e) {
					//e.printStackTrace();
					//System.out.println(r + ": " + e.getMessage());
				}
			}
		}
	}
	
	//定位数据项起始位置
	private static int [] getStartPoint(Grid[][] con){
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


	//html table -> string array []
	private static Grid[][] checkOut(String htmlContext) {
		//定位table
		int row = 0;  
		int col = 0;  
		Grid temp = null;
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
		int size[] = getSize(table);
		Grid context[][] =  new Grid [size[0]][size[1]];
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
		return context;
	}
	
	private static int[] getSize(Element element) {
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
	
	//单元格
	private static class Grid{
		//单元格中内容
		String content = ""; 
		//单元格是否为填充类型(详情见 Line: 186 & 210)
		boolean isFill = false;
		public Grid() {}
		public Grid(boolean isFill) { this.isFill = isFill;}
		@Override
		public String toString() {return content;}
	}
	
	private static boolean isOper(String oper){
		if(oper == null || oper.trim().equals(""))
			return false;
		return OPERS.contains(oper);
	}
	
	public static void main(String[] args) throws IOException {
		//File f = new File("/home/wu/Documents/changcheng/source/htmlbody2");
		File f = new File("/home/wu/Documents/changcheng/report/外资3表.html");
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		StringBuilder s = new StringBuilder((int)f.length());
		String temp = null;
		while((temp = br.readLine()) != null){
			s.append(temp);
		}
		Grid context [][] = checkOut(s.toString());
		BalanceInfo balance = new BalanceInfo();
		balance.setRowBalance("1>=5;2>=6;2>=3;3>=4;6>=7;7>=8;9<=10;11<=12");
		balance.setColumnBalance("1==2+9+15+21+26+31+36");
//		balance.setGridBalance("(1,17)==(24,8)+(25,8)");
		StringBuilder result = new StringBuilder(1024);
		checkBalance(result,s.toString(), balance);
		System.out.println(result);
	}
	
	public static void print(Grid[][] context){
		for(int i = 0; i < context.length; i ++) {
			for(int j = 0; j < context[i].length ; j ++) {
				System.out.print("[" + i + "," + j + "--" +  context[i][j] + "]" );
			}
			System.out.println();
		}
	}
}
