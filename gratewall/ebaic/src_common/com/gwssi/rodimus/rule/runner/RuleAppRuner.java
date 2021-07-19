package com.gwssi.rodimus.rule.runner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.RuleException;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.rule.config.RuleAppManager;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 规则应用校验入口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 * @author chaiyoubing
 */
public class RuleAppRuner {
	
	/**
	 * 执行规则场景。
	 * @param appCode2 
	 * 
	 * @param ruleCode 应用场景
	 * @param context  规则查询结果上下文
	 * @throws OptimusException 
	 */
	public static ValidateMsg run(String datasourceName,String appCode, Map<String, Object> params,List<String> skipedStepIdList) {
		// 0. 参数检查
		if(StringUtil.isBlank(appCode)){
			throw new RuleException("请适配规则需要应用的场景。");
		}
		
		// 1. 定义返回值
		ValidateMsg ret = new ValidateMsg();
		
		// 2. 查询配置信息 
		List<Map<String, Object>> list = RuleAppManager.getConfig(appCode);
		if(list == null ||list.isEmpty()){
			return ret;
		}
		
		// 3. 执行
		//    循环需要的变量
		Map<String, Object> row = null; // 每行配置信息
		String ruleCode = null;			// 规则编号
		String trigerExpr = null;		// 判断规则是否运行的表达式
		Boolean isRunRule = Boolean.TRUE;
		
		Iterator<Map<String,Object>> it = list.iterator();
		while(it.hasNext()){
			row = it.next();
			
			ruleCode = StringUtil.safe2String(row.get("ruleCode"));
			if(StringUtil.isBlank(ruleCode)){
				continue;
			}
			
			trigerExpr = StringUtil.safe2String(row.get("trigerExpr"));
			isRunRule = determineRuleIsRun(trigerExpr,params);
			if(Boolean.FALSE.equals(isRunRule)){
				continue ;
			}
			// 执行规则
			ret = RuleRunner.run(datasourceName,ruleCode, params,ret,skipedStepIdList);
		}// end of while
		
		
		return ret ;
	}
	
	/**
	 * 判断规则是否需要被执行。
	 * 
	 * @param trigerExpr
	 * @param params
	 * @return
	 */
	private static Boolean determineRuleIsRun(String trigerExpr, Map<String,Object> params){
		// 如果表达式为空，默认为执行规则
		if(StringUtil.isBlank(trigerExpr)){
			return Boolean.TRUE;
		}else{
			Object exprResult = ExprUtil.run(trigerExpr, params);
			if(exprResult==null){
				throw new RuleException(
						String.format("表达式%s执行结果为null，应该为true或false。上下文：%s。",
								trigerExpr,JSON.toJSONString(params)));
			}
			if(!(exprResult instanceof Boolean)){
				throw new RuleException(
						String.format("表达式%s执行结果为%s，应该为true或false。上下文：%s。", 
								trigerExpr,JSON.toJSONString(exprResult),
								JSON.toJSONString(params)));
			}
			return (Boolean)exprResult;
		}
	}
}
