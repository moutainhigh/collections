package com.gwssi.report.balance.api;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * js引擎计算表达式,支持带精度浮点运算（报表中资金数值都为精确到两位）,仅验证表达式是否正确(true or false)
 * 没有去空操作;
 * 
 * @author wuyincheng ,Nov 2, 2016
 */
public class SimpleJSEngineExpression implements Expression{
	
	private final static String [] MIDDLE_OPERS = 
			new String []{"==", ">=", "<=", ">", "<"};
	
	//@TODO 在开发机器上引擎为线程安全的，故仅实例化一个(JDK:1.7.0_75).
	//engine.getFactory().getParameter("THREADING") == MULTITHREAD;
	private final ScriptEngineManager sem = new ScriptEngineManager();
	private final ScriptEngine engine = sem.getEngineByName("JavaScript");
	
	//精度
	private int num = 4;
	
	private static final String NO_DATA = "NO_DATA";
	

	@Override
	public Object execute(Object exp) {
		if(exp == null || "".equals(exp.toString().trim()))
			return NO_DATA;
		final String expression = exp.toString();
		int i = 0;
		int index = -1;
		//将表达式转化成高精度执行
		//如：(3+5)>=8 => parseFloat(((3+5)).toFixed(4))>=parseFloat((8).toFixed(4))
		//参考：javascript 浮点行运算
		while(i < MIDDLE_OPERS.length && (index = expression.indexOf(MIDDLE_OPERS[i ++])) == -1);
		if(index == -1)
			return FALSE;
		final String left = expression.substring(0, index);
		final String right = expression.substring(index + (MIDDLE_OPERS[i - 1].length()), expression.length());
		final StringBuilder s = new StringBuilder(expression.length() + 56);
		s.append("parseFloat((").append(left).append(").toFixed(").append(num).append("))")
		 .append(MIDDLE_OPERS[i - 1]).append("parseFloat((").append(right).append(").toFixed(").append(num).append("))");
		try {
			System.out.println(s.toString());
			final Object result = engine.eval(s.toString());
			if(result.equals(TRUE))
				return TRUE;
			return FALSE;
		} catch (ScriptException e) {
			//异常暂不做处理
			return e.getMessage();
		}
	}
	
	public void setNum(int num) {
		if(num >= 1)
			this.num = num;
	}
	
	public static void main(String[] args) {
		Expression engine = new SimpleJSEngineExpression();
		System.out.println(engine.execute("(3+5)>=8"));
	}

	
}
