package com.gwssi.report.balance.api;
/**
 * @author wuyincheng ,Nov 2, 2016
 * Expression Decorator
 * 解析式装饰器
 */
public abstract class ExpressionDecorator implements Expression {
	
	protected Expression expression;
	
	protected ExpressionDecorator(Expression expression){
		this.expression = expression;
	}
	
	@Override
	public Object execute(Object expression) { //执行下一层前操作
		Object bef = null;
		try{
			bef = before(expression);
		}catch(Exception e){
			return e.getMessage();
		}
		return this.expression.execute(bef);
	}
	
	public void setExpression(Expression expression){//执行下一层
		this.expression = expression;
	}

	public abstract Object before(Object expression);
	
}
