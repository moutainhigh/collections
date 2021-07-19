package com.gwssi.rodimus.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.rule.runner.RuleAppRuner;
import com.gwssi.rodimus.rule.runner.RuleRunner;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 规则工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RuleUtil {
	
	public static boolean isReadConfigFromCache = false;
	
	private String datasourceName = "";
	
	RuleUtil(String datasourceName){
		this.datasourceName = datasourceName;
	}
	
	/**
	 * defaultDataSource 或  approveDataSource 。
	 * 如果为空，默认为 defaultDataSource 。
	 * @param datasourceName
	 * @return
	 */
	public static RuleUtil getInstance(String datasourceName){
		if(StringUtil.isBlank(datasourceName)){
			datasourceName = "defaultDataSource";
		}
		return new RuleUtil(datasourceName);
	}
	/**
	 * 如果需要在金网库跑规则。
	 * 
	 * 通过 RuleUtil.getInstance("approveDataSource") 调用。
	 * 
	 * @return
	 */
	public static RuleUtil getInstance(){
		return RuleUtil.getInstance(null);
	}
	
	/**
	 * 执行单个规则。
	 * 
	 * @param ruleCode
	 * @return
	 */
	public ValidateMsg runRule(String ruleCode,String gid,List<String> skipedStepIdList){
		Map<String,Object> params = ParamUtil.prepareParams(gid);
		if(params==null){
			params = new HashMap<String,Object>();
		}
		ValidateMsg msg = RuleRunner.run(datasourceName,ruleCode, params, null,skipedStepIdList);
		return msg;
	}
	public ValidateMsg runRule(String ruleCode,String gid){
		ValidateMsg ret = runRule(ruleCode,gid,null);
		return ret ;
	}
	
	/**
	 * 执行单个规则。
	 * 
	 * @param ruleCode
	 * @return
	 */
	public ValidateMsg runRule(String ruleCode,Map<String,Object> params,List<String> skipedStepIdList){
		if(params==null){
			params = new HashMap<String,Object>();
		}
		ValidateMsg msg = RuleRunner.run(datasourceName,ruleCode, params, null,null);
		return msg;
	}
	public ValidateMsg runRule(String ruleCode,Map<String,Object> params){
		ValidateMsg ret = runRule(ruleCode,params,null);
		return ret ;
	}
	/**
	 * 执行规则应用。
	 * 
	 * @param ruleCode
	 * @param msg
	 * @return
	 */
	public ValidateMsg runApp(String appCode,Map<String,Object> params, List<String> skipedStepIdList){
		if(params==null){
			params = new HashMap<String,Object>();
		}
		ValidateMsg ret = RuleAppRuner.run(datasourceName,appCode, params,skipedStepIdList);
		return ret;
	}
	
	public ValidateMsg runApp(String appCode,String gid, List<String> skipedStepIdList){
		Map<String,Object> params = ParamUtil.prepareParams(gid);
		if(params==null){
			params = new HashMap<String,Object>();
		}
		ValidateMsg ret = RuleAppRuner.run(datasourceName,appCode, params,skipedStepIdList);
		return ret;
	}
	
	public ValidateMsg runApp(String appCode,String gid){
		ValidateMsg ret = runApp(appCode, gid,null);
		return ret;
	}
	public ValidateMsg runApp(String appCode,Map<String,Object> params){
		ValidateMsg ret = runApp(appCode, params,null);
		return ret;
	}
}
