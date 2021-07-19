package com.gwssi.rodimus.expr.func;


import org.apache.log4j.Logger;

import com.gwssi.expression.core.ast.INode;
import com.gwssi.expression.core.engine.IEngineContext;
import com.gwssi.expression.core.function.AbstractASTFunction;
import com.gwssi.expression.core.interpreter.IInterpreter;

public abstract class BaseExprFunction extends AbstractASTFunction{
	
	protected final static Logger logger = Logger.getLogger(BaseExprFunction.class);

	public Object run(IEngineContext context, INode... argruments) {
		Object[] args = null;
		
		if(argruments!=null && argruments.length>0){
			args = new Object[argruments.length];
			IInterpreter interpreter = context.getEngine().getInterpreter();
			
			for(int i=0; i<argruments.length;++i){
				args[i] = argruments[i].run(context, interpreter);
			}
		}

		Object ret = run(args);
		return ret;
		
	}
	/**
	 * 执行表达式逻辑。
	 * 
	 * @param args
	 * @return
	 */
	public abstract Object run(Object...args);
}
