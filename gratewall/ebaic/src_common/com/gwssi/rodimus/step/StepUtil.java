package com.gwssi.rodimus.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.gwssi.expression.core.lang.UndefinedObjectException;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.step.config.StepConfigManager;
import com.gwssi.rodimus.step.domain.SysStepConfigBO;
import com.gwssi.rodimus.util.StringUtil;
/**
 * 环节配置的一些共用方法
 * @author LXB
 *
 */
public class StepUtil {
	
	/**
	 * 在传入的list中，每行增加opr列，内容为下一步操作的按钮。
	 * 
	 * @param configId
	 * @param list
	 * @return
	 * @throws OptimusException
	 */
	public static List<Map<String,Object>> getList(String configId , List<Map<String,Object>> list) {
		if(list==null || list.isEmpty()){
			return list;
		}
		if(StringUtil.isBlank(configId)){
			throw new RodimusException("configId不能为空。");
		}
		
		for(Map<String,Object> row : list){
			List<Map<String,String>> opr = getStepListByConfig(configId,row);
			if(opr==null || opr.isEmpty()){
				row.put("opr", "[]");
			}else{
				row.put("opr", opr);
			}
			
		}
		return list;
	}

	/**
	 * @param configId
	 * @param row
	 * @return
	 */
	private static List<Map<String, String>> getStepListByConfig(String configId, Map<String, Object> row) {
		if(StringUtil.isBlank(configId)){
			throw new RodimusException("configId不能为空。");
		}
		if(row==null || row.isEmpty()){
			return null;
		}
		String state = StringUtil.safe2String((row.get("state")));
		if(StringUtil.isBlank(state)){
			row.put("state", "1");
		}
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		
		List<SysStepConfigBO> configList = StepConfigManager.instance.getConfig(configId);
		if(configList==null){
			throw new RodimusException(String.format("没有配置下一步操作：%s。", configId));
		}
		
		String expr = "";
		Boolean result = null;
		String url = "";
		for(SysStepConfigBO config : configList){
			// 执行表达式，判断是否为true
			expr = config.getTrigerExpr();
			if(!StringUtil.isBlank(expr)){//表达式为空，视为真
				Object o = null;
				try{
					o = ExprUtil.run(expr, row);
				}catch(UndefinedObjectException e){
					e.printStackTrace();
					o = Boolean.FALSE;
				}
				if(o==null || !(o instanceof Boolean)){
					throw new RodimusException(String.format("表达式%s应返回布尔值，返回值为%s。", expr,JSON.toJSONString(o)));
				}
				result = (Boolean)o;
				if(result.equals(Boolean.FALSE)){
					continue ;
				}
				// 封装返回结果
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("code", config.getStepCode());
				resultMap.put("label", config.getStepName());
				url = config.getStepUrl();
				url = fillUrlValue(url,row);
				resultMap.put("url", url);
				ret.add(resultMap);
			}
		}
		
		return ret;
	}
	
	protected static final Pattern EXPR_PATTERN = Pattern.compile("(\\$\\{.*?\\})"); //"(\\{.*?\\})"
	
	private static String fillUrlValue(String msg, Map<String, Object> context) {
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
	

//	static {
//		init();
//	}
//	public StepUtil() {
//		
//	}
//	
//	//配置表信息，提前取出缓存，以function_id为key
//	@SuppressWarnings("rawtypes")
//	private static Map<String,List> STEP_CONFIG; 
//	
//	//初始化，从数据库中读取配置表信息缓存到变量中
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	protected static void init() {
//		STEP_CONFIG=new HashMap<String, List>();
//		String sql = "select n.function_id,n.judge_type,n.regex,n.format,n.nexturl,n.params from sys_next_config n";
//		List<Map<String,Object>> nextList = DaoUtil.queryForList(sql);
//		if(nextList==null || nextList.size()==0){
//			//没有配置项
//			return;
//		}
//		int len = nextList.size();
//		//System.out.println(JSONObject.toJSONString(nextList));
//		//按function_id分组
//		for (int i = 0; i < len; i++) {
//			Map tmp = nextList.get(i);
//			String function_id = (String)tmp.get("functionId");
//			List list = STEP_CONFIG.get(function_id);
//			if(list==null){
//				list = new ArrayList();
//				STEP_CONFIG.put(function_id, list);
//			}
//			list.add(tmp);
//			
//		}
//	}
//	/**
//	 * 通过配置表判断流程分支
//	 * 根据指定条件判断后续的执行环节
//	 * @throws OptimusException
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static void getNextListByConfig(String funcId,List<Map> list) throws OptimusException{
//		if(StringUtils.isNotBlank(funcId) && list!=null && list.size()>0){
//			//System.out.println("NextList="+JSONObject.toJSONString(list));
//			List<Map<String,Object>> nextList = STEP_CONFIG.get(funcId);
//			if(nextList==null || nextList.size()==0){
//				//没有配置项
//				return;
//			}
//			long start = System.currentTimeMillis();
//			int count = nextList.size();
//			int len = list.size();
//			for (int i = 0; i < len; i++) {
//				Map record = list.get(i);//一条业务记录
//				List tmp = new ArrayList();//按钮列表
//				for (int j = 0; j < count; j++) {
//					Map tmpMap = nextList.get(j);//一条下一步记录
//					//取正则表达式
//					String regex = (String)tmpMap.get("regex");
//					//取格式化字串
//					String format = (String)tmpMap.get("format");
//					String [] param = format.split(",");
//					//将指定的字符串变量名替换为值
//					for (String a : param) {
//						format = format.replace(a, (String)record.get(a));
//					}
//					//如果匹配 加入下一步列表
//					if(format.matches(regex)){
//						Map t = new HashMap();
//						t.putAll(tmpMap);
//						t.remove("regex");
//						t.remove("format");
//						t.remove("judgeType");
//						tmp.add(t);
//					}
//					
//				}
//				record.put("nextList", tmp);
//			}
//			//System.out.println("After="+JSONObject.toJSONString(list));
//			System.out.println("解析耗时"+(System.currentTimeMillis()-start));
//		}else{
//			
//		}
//		
//	}
//	
//	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//
//
//	}

}
