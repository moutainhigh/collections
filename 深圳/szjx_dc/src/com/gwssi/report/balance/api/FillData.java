package com.gwssi.report.balance.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.report.model.TCognosReportBO;

/**
 * @author wuyincheng ,Nov 8, 2016
 * 将DataItem实体根据报表模型填充对应报表数据
 * List<DataItem>填充数据
 * @ThreadNotSafe线程不安全
 */
public class FillData extends ExpressionDecorator{
	
	//单元格匹配符
	private final static String SINGLE_DATA = "\\(\\d*,\\d*\\)";
	
	private ReportModel model;
	private ReportSource source;
	
	//二级Cache,缓存当前校验所需模型
	private Map<String, ReportModel> secondCache = new HashMap<String, ReportModel>(4);
	
	protected FillData(Expression expression) {
		super(expression);
	}
	
	protected FillData(Expression expression, ReportModel model, ReportSource source) {
		super(expression);
		this.model = model;
		this.source = source;
	}
	
	//默认expression为List<DataItem>结构
	@SuppressWarnings("unchecked")
	@Override
	public Object before(Object expression) {
		if(!(expression instanceof List<?>))
			return null;
		List<DataItem> items = (List<DataItem>) expression;
		final StringBuilder s = new StringBuilder(64);
		String data = null;
		String temp = null;
		for(DataItem item : items){
			//常量或运算符不做处理
			if(item.isConstant || item.isOperChar){
				s.append(item.item);
				continue ;
			}
			data = item.item;
			//表间平衡关系式，有别名的
			if(data.charAt(0) != '(' && data.charAt(0) != '[' ){//中括号表名是一个合集，如：[(3,3)+(4,4)]
				int index = data.indexOf('[') == -1 ? data.indexOf('(') : data.indexOf('[');
				String reportName = data.substring(0, index);
				temp = data.substring(index, data.length());
				if(temp.matches(SINGLE_DATA)){//带别名-单个单元格
					s.append(getData(temp, reportName));
					continue ;
				}else{ //带中括号
					temp = temp.substring(1, temp.length() - 1);
					int start = 0;
					for(int i = 0; i < temp.length(); i ++) {
						if(isOper(temp.charAt(i))) {
							s.append(getData(temp.substring(start, i), reportName));
							s.append(temp.charAt(i));
							start = i + 1;
						}
					}
					s.append(getData(temp.substring(start, temp.length()), reportName));
					continue ;
				}
			}
			if(item.item.matches(SINGLE_DATA)){//默认report中的单点
				s.append(getData(item.item));
				continue ;
			}
			if(item.item.charAt(0) == '['){//表名称的单点
				temp = item.item;
				temp = temp.substring(1, temp.length() - 1);
				int start = 0;
				s.append('(');
				for(int i = 0; i < temp.length(); i ++) {
					if(isOper(temp.charAt(i))) {
						s.append(getData(temp.substring(0, i)));
						s.append(temp.charAt(i));
						start = i + 1;
					}
				}
				s.append(getData(temp.substring(start, temp.length())));
				s.append(')');
			}
		}
		System.out.println(s.toString());
		return s.toString();
	}
	private static boolean isOper(char c){
		return c == '+' || c == '-' || c == '*' || c =='x' || c =='/';
	}
	
	private String getData(String point) {
		int [] p = getPoint(point);
		if(model == null)
			return "0";
		return model.getPoint(p[0], p[1]);
	}
	
	//根据单元格坐标值以及报表名获取对应单元格值
	private String getData(String point, String reportName){
		if(model == null)
			return "0";
		final int [] p = getPoint(point);
		ReportModel temp = null;
		if((temp = secondCache.get(reportName)) != null){//缓存命中
			return temp.getPoint(p[0], p[1]);
		}else{//未命中则在ReportSource中查找，并存入cache
			final TCognosReportBO query = this.model.getReportInfo().clone();
			query.setReportname(reportName);
			temp = source.getReport(query);
			secondCache.put(reportName, temp);
			return temp.getPoint(p[0], p[1]);
		}
	}
	//(32,43)==>{32,43}
	private int [] getPoint(String point){
		final int index = point.indexOf(',');
		return new int []{Integer.parseInt(point.substring(1, index)) - 1,
				           Integer.parseInt(point.substring(index + 1, point.length() - 1)) - 1};
	}
	
	public static void main(String[] args) {
		String s = "(6,4)-(6,5)";
		System.out.println(s.substring(6, 11));
	}
	
	
}
