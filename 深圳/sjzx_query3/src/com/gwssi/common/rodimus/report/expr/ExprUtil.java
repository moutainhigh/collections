package com.gwssi.common.rodimus.report.expr;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gwssi.common.rodimus.report.entity.SysmgrOrg;
import com.gwssi.common.rodimus.report.entity.SysmgrUser;
import com.gwssi.common.rodimus.report.expr.func.service.DateManager;
import com.gwssi.common.rodimus.report.expr.func.service.DictManager;
import com.gwssi.common.rodimus.report.util.HttpUtil;
import com.gwssi.common.rodimus.report.util.SessionUtil;
import com.gwssi.common.rodimus.report.util.StringUtil;
import com.gwssi.common.rodimus.report.util.ThreadLocalManager;
import com.gwssi.expression.Expression;
import com.gwssi.expression.component.context.SimpleContext;
import com.gwssi.expression.config.ConfigurationFactory;

/**
 * <h2>表达式工具类</h2>
 * <pre>
 * 表达式相关代码演示：
	SimpleContext context = getContenxt();
		
	// 简单表达式演示
	context.put("a", 1);
	context.put("b", 2);
	Object result = EXPR_ENGINE.run("a+b", context);
	System.out.println("a+b    =    "+result);
	
	// 点操作符演示
	SysmgrUser user = new SysmgrUser();
	user.setLoginName("liuhailong");
	SysmgrOrg org = new SysmgrOrg();
	org.setOrgName("海淀区");
	user.setOrg(org);
	context.put("user", user);
	
	result = EXPR_ENGINE.run("user.\"loginName\"", context);
	System.out.println("user.\"loginName\"    =    "+result);
	result = EXPR_ENGINE.run("user.\"org\".\"orgName\"", context);
	System.out.println("user.\"org\".\"orgName\"    =    "+result);
	result = EXPR_ENGINE.run("user.\"org.orgName\"", context);
	System.out.println("user.\"org.orgName\"    =    "+result);
	
	
	// 查询候选项演示（查询码表）
	String expr = "dict.\"regOrg\"" ;
	result = EXPR_ENGINE.run(expr, context);
	System.out.println(expr+"    =    "+result);
	
	// 日期相关
	expr = "date.\"today\"" ;
	result = EXPR_ENGINE.run(expr, context);
	System.out.println(expr+"    =    "+result);
	
	// Http请求相关，Web环境运行，通过ReportServlet入口进入才能看到效果，Session相关类似。
	expr = "request.\"id\"" ;
	result = EXPR_ENGINE.run(expr, context);
	System.out.println(expr+"    =    "+result);
	
	// Session相关
	expr = "session.\"USER\"" ;
	result = EXPR_ENGINE.run(expr, context);
	System.out.println(expr+"    =    "+result);
	
	// 当前登录用户相关信息
	expr = "currentUser.\"userId\"" ;
	result = EXPR_ENGINE.run(expr, context);
	System.out.println(expr+"    =    "+result);
	</pre>
 * 
 * @author liuhailong
 */
public class ExprUtil {
	
	static{	
		try {
			//初次加载时，加载表达式配置文件。
			ConfigurationFactory.addConfigLocation("classpath:com/gwssi/common/rodimus/report/expr/config/bjaic-expr-config.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 表达式引擎实例。
	 * @throws Exception 
	 */
	public static Expression EXPR_ENGINE = Expression.getInstance("bjaic");
	/**
	 * 表达式配置模式。
	 */
	protected static final Pattern EXPR_PATTERN = Pattern.compile("(#.*?#)"); //"(\\{.*?\\})"
	
	
	/**
	 * 清除表达式上下文。
	 */
	public static void initContext(){
		ThreadLocalManager.add(ThreadLocalManager.EXPR_CONTEXT, null);
		//ThreadLocalManager.clear();
	}
	/**
	 * 清除表达式上下文。
	 */
	public static void clearContext(){
		ThreadLocalManager.add(ThreadLocalManager.EXPR_CONTEXT, null);
		//ThreadLocalManager.clear();
	}
	/**
	 * <h3>获取表达式上下文</h3>
	 * 
	 * 表达式上下文中已经包含了Http请求中的参数。
	 * 增加，保存部分Session信息。
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static SimpleContext getContenxt(){
		
//		SimpleContext ret = (SimpleContext)SessionUtil.get(ThreadLocalManager.EXPR_CONTEXT);
		SimpleContext ret = (SimpleContext)ThreadLocalManager.get(ThreadLocalManager.EXPR_CONTEXT);
		if(ret!=null){
			Map<String, String> tmp = (Map<String, String>)ret.get("request");
			if("1".equals(tmp.get("isDefaultValueSetted"))){
				//设置的是默认值，重取
				ret=null;
			}
		}
		if(ret == null ){
			SimpleContext context = new SimpleContext();
			
			// 将Http请求中的参数放入上下文
			Map<String, String> httpRequestParameterMap = HttpUtil.getParameterMap();
			if(httpRequestParameterMap!=null && !httpRequestParameterMap.isEmpty()){
				context.put("request", httpRequestParameterMap);
			}
			
			// 将session属性放入上下文
			Map<String, Object> sessionAttrMap = SessionUtil.getAttributeMap();
			if(sessionAttrMap!=null && !sessionAttrMap.isEmpty()){
				context.put("session", sessionAttrMap);
			}
			
			// 日期相关
			DateManager date = new DateManager();
			context.put("date", date);
			
			// 码表相关
			DictManager dict = new DictManager();
			context.put("dict", dict);
			
			// 当前登录用户相关信息
//			SysmgrUser currentUser = CurrentUserUtil.get();
			context.put("currentUser", "LXB");
			
//			SessionUtil.set(ThreadLocalManager.EXPR_CONTEXT, context);
			ThreadLocalManager.add(ThreadLocalManager.EXPR_CONTEXT, context);
			ret = context;
		}
		return ret;
	}
	
	public static void putVarIntoContext(String key,Object value){
		SimpleContext context = getContenxt();
		context.put(key, value);
	}
	
	public static Object getVarFromContext(String key){
		SimpleContext context = getContenxt();
		Object value = context.get(key);
		return value;
	}
	
	public static void main(String[] args) throws Exception{ 
		processDemo();
	}
	public static void exprDemo(){
		SimpleContext context = getContenxt();
		
		// 简单表达式演示
		context.put("a", 1);
		context.put("b", 2);
		Object result = EXPR_ENGINE.run("a+b", context);
		System.out.println("a+b    =    "+result);
		
		// 点操作符演示
		SysmgrUser user = new SysmgrUser();
		user.setLoginName("liuhailong");
		SysmgrOrg org = new SysmgrOrg();
		org.setOrgName("海淀区");
		user.setOrg(org);
		context.put("user", user);
		
		result = EXPR_ENGINE.run("user.\"loginName\"", context);
		System.out.println("user.\"loginName\"    =    "+result);
		result = EXPR_ENGINE.run("user.\"org\".\"orgName\"", context);
		System.out.println("user.\"org\".\"orgName\"    =    "+result);
		result = EXPR_ENGINE.run("user.\"org.orgName\"", context);
		System.out.println("user.\"org.orgName\"    =    "+result);
		
		
		// 查询候选项演示（查询码表）
		String expr = "dict.\"regOrg\"" ;
		result = EXPR_ENGINE.run(expr, context);
		System.out.println(expr+"    =    "+result);
		
		// 日期相关
		expr = "date.\"today\"" ;
		result = EXPR_ENGINE.run(expr, context);
		System.out.println(expr+"    =    "+result);
		
		// Http请求相关，Web环境运行，通过ReportServlet入口进入才能看到效果，Session相关类似。
		expr = "request.\"id\"" ;
		result = EXPR_ENGINE.run(expr, context);
		System.out.println(expr+"    =    "+result);
		
		// Session相关
		expr = "session.\"USER\"" ;
		result = EXPR_ENGINE.run(expr, context);
		System.out.println(expr+"    =    "+result);
		
		// 当前登录用户相关信息
		expr = "currentUser.\"userId\"" ;
		result = EXPR_ENGINE.run(expr, context);
		System.out.println(expr+"    =    "+result);
		
	}
	
	public static void processDemo(){
		String string = "";
		ExprUtil.putVarIntoContext("stat_type", "stat_type_busiType");
		

		string =  " #dict.`regOrg`# ";
		System.out.println("\r\nbefore :" + string);
		string = ExprUtil.processExpr(string);
		System.out.println("after :" + string);
		
		string =  " #GetStatType(stat_type)# ";
		System.out.println("\r\nbefore :" + string);
		string = ExprUtil.processExpr(string);
		System.out.println("after :" + string);
		
		string =  "{ #GetStatType(stat_type).\"field\"# , #GetStatType(stat_type).\"label\"# ";
		System.out.println("\r\nbefore :" + string);
		string = ExprUtil.processExpr(string);
		System.out.println("after :" + string);
		
		string =  "#GetStatType(stat_type).\"field\"#  #dict.`regOrg`#  ";
		System.out.println("\r\nbefore :" + string);
		string = ExprUtil.processExpr(string);
		System.out.println("after :" + string);
		
		string =  "#GetStatType(stat_type).\"field\"#  #dict.`regOrg`# #date.\"today\"# ";
		System.out.println("\r\nbefore :" + string);
		string = ExprUtil.processExpr(string);
		System.out.println("after :" + string);
		
	}
	/**
	 * 执行字符串中的 表达式。
	 * 
	  	
	 * 
	 * @param string
	 * @return
	 */
	public static String processExpr(String string){
		
		if(StringUtils.isEmpty(string)){
			return string;
		}
		
		string = string.replaceAll("`", "\"");
		
		SimpleContext context = ExprUtil.getContenxt();
		Matcher matcher = EXPR_PATTERN.matcher(string);
		String paramPlaceHolder = null;
		String expr = null;
		Object exprValueObj = null;
		String exprValue = null;
		String ret = string;
		
		while(matcher.find()){
			paramPlaceHolder = matcher.group(); // 如 ： {entName}
			if(paramPlaceHolder!=null && paramPlaceHolder.length()>2){//至少包含{和}
				expr = paramPlaceHolder.substring(1, paramPlaceHolder.length()-1);
				if(StringUtils.isEmpty(expr)){
					ret = ret.replace(paramPlaceHolder, "");
					continue ;
				}
				// TODO 丰富语法，支持表达式元信息，如：是否缓存结果
				
				expr = expr.trim();
				exprValueObj = EXPR_ENGINE.run(expr,context);
				if(exprValueObj==null){
					exprValue = "";
				}else if((exprValueObj instanceof List) || (exprValueObj instanceof Map)){
					exprValue = JSON.toJSONString(exprValueObj, false);
				}else{
					exprValue = StringUtil.safe2String(exprValueObj);
				}
				if("NaN".equals(exprValue)){
					exprValue="";
				}
				ret = ret.replace(paramPlaceHolder, exprValue);
			}
		}
		
		return ret;
	}
	
	/**
	 * 执行表达式。
	 * 
	 * @param expr
	 * @param context
	 * @return
	 */
	public static Object process(String expr,Map<String, Object> context){
		if(context==null || context.isEmpty()){
			return null;
		}
		if(StringUtils.isEmpty(expr)){
			return "";
		}
		SimpleContext ctx = new SimpleContext();
		String key;Object val;
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			key = entry.getKey();
			val = entry.getValue();
			ctx.put(key, val);
		} 
		Object ret = EXPR_ENGINE.run(expr, ctx);
		
		if("NaN".equals(ret)  
				|| (ret instanceof Double && ((Double)ret).isNaN())){
			ret="";
		}
		
		return ret;
	}
}
