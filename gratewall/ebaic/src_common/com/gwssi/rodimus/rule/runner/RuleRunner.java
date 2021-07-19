package com.gwssi.rodimus.rule.runner;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.RuleException;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.rule.config.RuleManager;
import com.gwssi.rodimus.rule.config.RuleStepManager;
import com.gwssi.rodimus.rule.domain.SysRule;
import com.gwssi.rodimus.rule.domain.SysRuleStep;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 执行单条规则。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 * @author chaiyoubing
 */
public class RuleRunner {
	/**
	 * 执行单条规则。
	 * 
	 * @param ruleCode 规则编码
	 * @param ruleCode2 
	 * @param context  规则查询结果上下文
	 * @param valMsg 可以为空
	 * 
	 * @throws OptimusException 
	 */
	public static ValidateMsg run(String datasourceName,String ruleCode, Map<String, Object> context,ValidateMsg valMsg,List<String> skipedStepIdList) {
		// 0. 参数校验
		if(valMsg==null){
			valMsg = new ValidateMsg();
		}
		if(StringUtil.isBlank(ruleCode)){
			return valMsg;
		}
		SysRule rule = RuleManager.getConfig(ruleCode);
		if(rule==null){
			valMsg.addRuleResult(ruleCode, "1", "", String.format("没有编码为%s的规则，请联系系统管理员。", ruleCode));
			return valMsg;
		}
		List<SysRuleStep> stepList = RuleStepManager.getConfig(rule.getRuleId());
		if(stepList==null || stepList.isEmpty()){
			valMsg.addRuleResult(ruleCode, "1", rule.getRuleName(), String.format("规则“[%s]%s”没有配置步骤，请联系系统管理员。", ruleCode, rule.getRuleName()));
			return valMsg;
		}
		
		// 2. 逐个步骤执行
		String expr = ""; // 表达式
		Object exprResult = null; // 表达式执行结果
		String msg = "";
		Boolean exprBooleanResult ;
		for(SysRuleStep step : stepList){
			
			// 略过
			if(skipedStepIdList!=null){
				if(skipedStepIdList.contains(step.getStepId())){
					continue ;
				}
			}
			
			// 表达式为空，略过
			expr = step.getExpr();
			if(StringUtil.isBlank(expr)){
				continue ;
			}
			
			// 执行表达式
			exprResult = ExprUtil.run(datasourceName, expr, context);
			
			// 将表达式结果放入上下文
			if(StringUtil.isNotBlank(step.getVar())){
				context.put(step.getVar(), exprResult);
			}
			
			if("2".equals(step.getStepType())){// 步骤类型，1-求值，2-输出
				if(StringUtil.isBlank(step.getMsg())){
					throw new RuleException(String.format("规则%s的步骤%s未设置msg属性。", rule.getRuleCode(),step.getSn()));
				}
				// 判断当前表达式值是否为布尔，且为真
				if(exprResult==null){
					throw new RuleException(String.format("规则%s的步骤%s步骤类型设为2（输出），则对应表达式执行结果应该为布尔值，当前为null。", rule.getRuleCode(),step.getSn()));
				}
				if(!(exprResult instanceof Boolean)){
					throw new RuleException(String.format("规则%s的步骤%s步骤类型设为2（输出），则对应表达式执行结果应该为布尔值，当前为%s。", rule.getRuleCode(),step.getSn(),JSON.toJSONString(exprResult, true)));
				}
				
				// 添加返回信息
				exprBooleanResult = (Boolean)exprResult ;
				if(exprBooleanResult){ // 如果为 true ,则返回消息（一般认为：返回消息为规则不通过）
					msg = step.getMsg();
					msg = fillMsgValue(msg,context);
					valMsg.addRuleResult(rule.getRuleCode(), // ruleCode
							step.getMsgType(), //type
							String.format("%s:%s", rule.getRuleName(),step.getStepName()),//name
							msg,//msg
							step.getErrCode() ,//errCode
							step.getRuleId(),//ruleId
							step.getStepId());//ruleStepId
					
					// 根据RuleMode判断是否终止规则执行
					// 如果是警觉型，而且当前消息类型为锁定，则终止执行
					if("1".equals(rule.getRuleMode()) && "1".equals(step.getMsgType())){ // msgType : 0 - 懒惰型，继续执行规则步骤 ； 1-警觉型
						return valMsg ;
					}
				}
			}
			
		}//end for
		return valMsg;
	}

	protected static final Pattern EXPR_PATTERN = Pattern.compile("(\\$\\{.*?\\})"); //"(\\{.*?\\})"
	/**
	 * 解析msg中的表达式。
	 * 
	 * @param msg
	 * @param context
	 * @return
	 */
	private static String fillMsgValue(String msg, Map<String, Object> context) {
		if(StringUtil.isBlank(msg)){
			return "";
		}
		
		msg = msg.replaceAll("`", "\"");
		
		Matcher matcher = EXPR_PATTERN.matcher(msg);
		String paramPlaceHolder = null;
		String expr = null;
		Object exprValueObj = null;
		String exprValue = null;
		String ret = msg;
		
		while(matcher.find()){
			paramPlaceHolder = matcher.group(); // 如 ： {entName}
			paramPlaceHolder = paramPlaceHolder.trim();
			if(paramPlaceHolder!=null && paramPlaceHolder.length()>3){//至少包含${和}
				expr = paramPlaceHolder.substring(2, paramPlaceHolder.length()-1);
				if(StringUtil.isBlank(expr)){
					ret = ret.replace(paramPlaceHolder, "");
					continue ;
				}
				
				expr = expr.trim();
				exprValueObj = ExprUtil.run(expr, context); //EXPR_ENGINE.run(expr,context);
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
}
