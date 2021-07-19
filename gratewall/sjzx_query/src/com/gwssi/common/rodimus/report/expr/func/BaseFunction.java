package com.gwssi.common.rodimus.report.expr.func;

import org.apache.log4j.Logger;

import com.gwssi.expression.core.ast.INode;
import com.gwssi.expression.core.engine.IEngineContext;
import com.gwssi.expression.core.function.AbstractASTFunction;
import com.gwssi.expression.core.interpreter.IInterpreter;

/**
 * 自定义函数基类。
 * 
 * @author liuhailong
 */
public abstract class BaseFunction extends AbstractASTFunction{

	protected final static Logger logger = Logger.getLogger(BaseFunction.class);
	
	/**
	 * 通过实现此方法，实现自定义函数功能。
	 * 
	 * @param args
	 * @return
	 */
	public abstract Object run(Object...args);

	public Object run(IEngineContext context, INode... args) {
		
		Object[] params = null;
		if(args!=null && args.length>0){
			params = new Object[args.length];
			
			IInterpreter interpreter = context.getEngine().getInterpreter();
			for (int i = 0; i < args.length; i++) {
				params[i] = args[i].run(context, interpreter);
			}
		}
		
		Object ret = this.run(params);
		return ret;
	}
}
