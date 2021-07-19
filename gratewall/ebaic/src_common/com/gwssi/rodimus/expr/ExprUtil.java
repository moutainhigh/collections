package com.gwssi.rodimus.expr;

import java.util.Map;

import com.gwssi.expression.Expression;
import com.gwssi.expression.component.context.SimpleContext;
import com.gwssi.expression.config.ConfigurationFactory;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.ThreadLocalManager;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 * @author chaiyoubing
 */
public class ExprUtil {
	
	protected static final String EXPR_CONFIG_PATH = "ebaic-expr-config.xml";
	/**
	 * 表达式引擎实例。
	 * @throws Exception 
	 */
	protected static Expression EXPR_ENGINE ;
	
	static {
		init();
	}
	/**
	 * 幂等性。
	 */
	protected static void init(){
		// 初始化表达式引擎
		try {
			//初次加载时，加载表达式配置文件。
			ConfigurationFactory.addConfigLocation("classpath:"+EXPR_CONFIG_PATH);
			EXPR_ENGINE = Expression.getInstance("ebaic");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行表达式。
	 * 
	 * @param expr
	 * @param context
	 * @return
	 */
	public static Object run(String expr, Map<String,Object> context){
		if(StringUtil.isBlank(expr)){
			return null;
		}
		expr = expr.trim();
		expr = expr.replaceAll("`", "\"");
		SimpleContext exprContext = new SimpleContext();
		
		if(context!=null && !context.isEmpty()){
			for (Map.Entry<String, Object> entry : context.entrySet()){
				exprContext.put(entry.getKey(), entry.getValue());
			}
		}
		
		Object ret = EXPR_ENGINE.run(expr,exprContext);
		return ret;
	}

	public static Object run(String datasourceName, String expr, Map<String, Object> context) {
		String datasourceKey = "datasourceName";
		String preDatasourceName = (String)ThreadLocalManager.get(datasourceKey);
		ThreadLocalManager.add(datasourceKey, datasourceName);
		Object ret = run(expr,context);
		ThreadLocalManager.add("datasourceName", preDatasourceName);
		return ret;
	}
	
}
