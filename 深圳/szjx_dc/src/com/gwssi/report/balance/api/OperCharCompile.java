package com.gwssi.report.balance.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 平衡表达式解析成List<DataItem>结构
 * 
 * @author wuyincheng ,Nov 2, 2016
 */
public class OperCharCompile extends ExpressionDecorator{
	
	private final static HashSet<Character> OPERS = new HashSet<Character>(){
		private static final long serialVersionUID = -8376104252956473995L;
		{
			add('=');add('>');add('<');add('+');add('-');add('*');add('/');add('x');
		}
	};
	//单元格匹配符
	private final static String GIRD_REGEX = "\\(\\d*,\\d*\\)";

	protected OperCharCompile(Expression expression) {
		super(expression);
	}

	@Override
	public Object before(Object expression) {
		String exp = null;
		if(expression == null || (exp = expression.toString()).trim().equals(""))
			return null;
		exp = exp.replaceAll("\\s+", "");//去空
		final Pattern p = Pattern.compile(GIRD_REGEX);
		final Matcher m = p.matcher(exp);
		final List<String> grids = new ArrayList<String>(8);
		while (m.find()) {
			grids.add(m.group());
		}
		//先将平衡关系式中所有单元格字符串转换成 "?"，再拆分成List<DataItem>
		//防止有些括号内包含单元格，造成解析出错
		exp = exp.replaceAll(GIRD_REGEX, "?");//占位符，防止单元格括号类型影响
		List<DataItem> items = new ArrayList<DataItem>(8);
		int s = 0;
		int i = 0;
		boolean flag = false;
		for( ; i < exp.length(); i ++){
			if(exp.charAt(i) == '\''){//常量，常量都是用单引号包含
				s = i + 1;
				while(++i < exp.length() && exp.charAt(i) != '\'');
				if(s >= exp.length()){
					return "";
				}
				DataItem data = new DataItem(exp.substring(s, i));
				data.isConstant = true;
				items.add(data);
				s = ++ i;
				flag = true;
			}
			if(i < exp.length() && exp.charAt(i) == '['){//中括号内的所有数据为一个DataItem
				while(++i < exp.length() && exp.charAt(i) != ']');
				if(s >= exp.length()){
					return "";
				}
				DataItem data = new DataItem(exp.substring(s, i + 1));
				items.add(data);
				s = i++;
				flag = true;
			}
			if(i < exp.length() && OPERS.contains(exp.charAt(i))) {//加减乘除等运算符
				if(i + 1 < exp.length() && OPERS.contains(exp.charAt(i + 1))){//运算符
					if(!flag){
						items.add(new DataItem(exp.substring(s, i)));
					}
					items.add(DataItem.operChar(exp.substring(i, i + 2)));
					s = i + 2;
					i ++;
				}else{
					if(!flag){
						items.add(new DataItem(exp.substring(s, i)));
					}
					items.add(DataItem.operChar(String.valueOf(exp.charAt(i))));
					s = i + 1;
				}
				flag = false;
			}
		}
		if(s < exp.length() && exp.charAt(s) != ']'){
			items.add(new DataItem(exp.substring(s, exp.length())));
		}
		//占位符（问号）还原成坐标值
		s = 0;
		final StringBuilder ts = new StringBuilder();
		String temp = null;
		for(DataItem item : items) {
			if(!(item.isConstant || item.isOperChar) && (temp = item.item).indexOf('?') != -1) {
				for(i = 0; i < temp.length(); i ++) {
					if(temp.charAt(i) == '?'){
						ts.append(grids.get(s ++));
					}else{
						ts.append(temp.charAt(i));
					}
				}
				item.item = ts.toString();
				ts.delete(0, ts.length());
			}
		}
		System.out.println(items);
		return items;
	}
											
	//DB_DC
	public static void main(String[] args) {
//		CognosService service = new CognosService();
//		TCognosReportBO bo = new TCognosReportBO();
//		bo.setId("fe8b455d666342229f731bb2800ba055");
//		service.getReport(bo);
		OperCharCompile exp = new OperCharCompile(new FillData(new SimpleJSEngineExpression(), null, null));
//		System.out.println(exp.execute("内资3表 [(2,3) + (2,4)] = 内资1表 (2,5)"));
//		System.out.println(exp.execute("3>=5+6+7+8+'500'+9"));
//		System.out.println(exp.execute("(6,6)>=[(6,4) - (6,5)] / (6,5) x '100'"));
//		System.out.println(exp.before("人事1表 [(4,5)+(4,10)+(4,15)+(4,20)]==综合5表(5,3)"));
		System.out.println(exp.before("内资1表(2,10)==内资3表 [(2,1)+(3,1)]"));
//		System.out.println(exp.execute("(3,35)/(3,34)<='500'"));
//		System.out.println(exp.execute("(13,5)>=(13,12)"));
	}

}
