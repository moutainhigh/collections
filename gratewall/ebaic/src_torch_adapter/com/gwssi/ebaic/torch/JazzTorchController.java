package com.gwssi.ebaic.torch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.RequestMethodValidateUtil;
import com.gwssi.rodimus.util.SpringUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.api.Val;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.TorchRequest;
import com.gwssi.torch.domain.edit.EditColumnConfig;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.fun.FunctionConfigBo;
import com.gwssi.torch.domain.query.QueryConditionConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.TorchUtils;
import com.gwssi.torch.web.TorchController;

/**
 * 火炬框架入口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping(value = "/torch")
public class JazzTorchController extends TorchController {
	
	public static final String STRING_ERROR_MSG_KEY = "STRING_ERROR_MSG_KEY";
	
	/**
	 * 火炬框架入口。
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/service")
	public void service(OptimusRequest optimusRequest, OptimusResponse optimusResponse)throws OptimusException {
    	RequestMethodValidateUtil.validate(optimusRequest.getHttpRequest()); 
//		try{
			//创建参数
			TorchRequest params = new TorchRequest();
			//执行方法
			super.service(optimusRequest.getHttpRequest(),optimusResponse.getHttpResponse(),params);
//		}catch(RuntimeException e){
//			if(null!=e.getMessage()){
//				throw new OptimusException(e.getMessage());
//			}else{
//				throw new OptimusException("执行方法内部异常.");
//			}
//			
//		}
		
	}
	
	/**
	 * 
	 * @param event
	 * @param editConfigBo
	 */
	protected void checkData(Object configBo,Map<String, String> params,
			Object result,String... events) {
		if(events==null || events.length==0 || configBo==null){
			return ;
		}
		
		for(String event : events){
			if("after".equals(event) || "before".equals(event)){
				execEventListener(event,configBo,params,result);
				continue;
			}
			if("val".equals(event)){
				execEventVal(configBo,params);
				continue;
			}
		}
	}
	
	/**
	 * 执行字段校验
	 * 
	 * @param configBo
	 * @param formData
	 */
	private void execEventVal(Object configBo,Map<String,String> formData){
		String rule,name,label,value;
		if(configBo instanceof EditConfigBo){
			EditConfigBo editConfigBo = (EditConfigBo)configBo;
			List<EditColumnConfig> columns = editConfigBo.getColumns();
			if(columns==null || columns.isEmpty()){
				return ;
			}
			if(formData==null){
				formData = new HashMap<String,String>();
			}
			for(EditColumnConfig column : columns){
				if(column==null){
					continue ;
				}
				rule = column.getColumnRule();
				name = column.getColumnName();
				label = column.getColumnTitle();
				value = formData.get(name);
				validateVal(rule,value,label);
			}
		}
		if(configBo instanceof QueryConfigBo){
			QueryConfigBo queryConfigBo = (QueryConfigBo)configBo;
			List<QueryConditionConfigBo> conditions = queryConfigBo.getConditions();
			if(conditions==null || conditions.isEmpty()){
				return ;
			}
			if(formData==null){
				formData = new HashMap<String,String>();
			}
			for(QueryConditionConfigBo condition : conditions){
				if(condition==null){
					continue ;
				}
				rule = condition.getConditionRule();
				name = condition.getConditionName();
				label = condition.getConditionTitle();
				value = formData.get(name);
				validateVal(rule,value,label);
			}
		}
		//打印全部错误信息
		List<ValidateMsgEntity> msgList = Val.getMsg().getAllMsg();
		StringBuffer errorMsg = new StringBuffer();
		if(msgList!=null && msgList.size()>0){
			for(int i=0;i<msgList.size();i++){
				ValidateMsgEntity msg = msgList.get(i);
				errorMsg.append(msg.getMsg());
				errorMsg.append("；\n");
			}
			msgList.clear();
			throw new RuntimeException(errorMsg.toString());
		}
	}
	
	/**
	 * 执行字段校验
	 * 
	 * @param rule notNull;eq:111
	 * @param value xxxxx
	 * @param label
	 */
	private void validateVal(String rules,String value,String label){
		if(StringUtil.isBlank(rules)){
			return;
		}
		rules = rules.replaceAll(" ", ""); // format :  eq:111,xxx
		String[] ruleArray ;
		//JAZZ规则“_”表示“且”，“|”表示“或”
		if(rules.indexOf("_")!=-1){
			ruleArray = rules.split("_");
			validateAndRule(ruleArray,value,label);
		}else if(rules.indexOf("|")!=-1){
			ruleArray = rules.split("|");
			validateOrRule(ruleArray,value,label);
		}else{
			ruleArray = new String[]{rules};
			validateRule(rules,value,label);
		}
	}
	
	/**
	 * 执行“且”关系校验
	 * 
	 * @param rules
	 * @param value
	 * @param label
	 */
	public void validateAndRule(String[] rules,String value,String label){
		for (String rule : rules) {
			validateRule(rule,value,label);
			if(!Val.getMsg().isEmpty()){
				//有错误信息就返回
				return;
			}
		}
	}
	
	/**
	 * 执行“或”关系校验
	 * 
	 * @param rules
	 * @param value
	 * @param label
	 */
	public void validateOrRule(String[] rules,String value,String label){
		int ruleCnt = rules.length;
		String rule;
		for(int i=0;i<ruleCnt-1;i++){
			rule = rules[i];
			validateRule(rule,value,label);
			if(Val.getMsg().isEmpty()){
				break;
			}else{
				Val.getMsg().clear();
			}
		}
		if(Val.getMsg().isEmpty()){
			return;
		}
		rule = rules[ruleCnt];
		validateRule(rule,value,label);
	}
	/**
	 * 执行规则校验
	 * 
	 * @param rule
	 * @param value
	 * @param label
	 */
	public void validateRule(String rule,String value,String label){
		if(StringUtils.isBlank(rule)){return;}
		String valMethodName = "";
		String valStringPamras = null;
		int index = rule.indexOf(";");
		if(index!=-1){
			valMethodName = rule.substring(0,index);
			valStringPamras = rule.substring(index+1);
		}else{
			valMethodName = rule;
		}
		Val.field.validate(valMethodName, label, value, valStringPamras);
	}
	/**
	 * 
	 * @param event
	 * @param editConfigBo
	 * @param params 执行参数
	 * @param result 执行结果
	 */
	private void execEventListener(String event, Object configBo,Map<String,String> params,Object result) {
		String eventBeanName = null;
		
		if(configBo instanceof EditConfigBo){
			EditConfigBo editConfigBo = (EditConfigBo)configBo;
			if("after".equals(event) ){
				eventBeanName = editConfigBo.getAfterExecute();
			}
			if("before".equals(event)){
				eventBeanName = editConfigBo.getBeforeExecute();
			}
			if(StringUtils.isNotBlank(eventBeanName)){
				OnEventListener eventBean = (OnEventListener)SpringUtil.getBean(eventBeanName);
				if(eventBean!=null){
					eventBean.execEdit(this,params,editConfigBo,result);
				}
			}
		}
		if(configBo instanceof QueryConfigBo){
			QueryConfigBo queryConfigBo = (QueryConfigBo)configBo;
			if("after".equals(event) ){
				eventBeanName = queryConfigBo.getAfterExecute();
			}
			if("before".equals(event)){
				eventBeanName = queryConfigBo.getBeforeExecute();
			}
			if(StringUtils.isNotBlank(eventBeanName)){
				OnEventListener eventBean = (OnEventListener)SpringUtil.getBean(eventBeanName);
				if(eventBean!=null){
					eventBean.execQuery(this,params,queryConfigBo,result);
				}
			}
		}
		
	}

	@Override
	protected void parseInputParams(HttpServletRequest request,TorchRequest ret) {
		OptimusRequest optimusRequest = (OptimusRequest)ThreadLocalManager.get(ThreadLocalManager.OPTIMUS_REQUEST);
		// 后面的覆盖前面的
		parseInputParams_requestParameter(request,optimusRequest,ret);
		parseInputParams_paginationParams(request,optimusRequest,ret);
		parseInputParams_jazzAttr(request,optimusRequest,ret);
		parseInputParams_Session(request,optimusRequest,ret);
		parseInputParams_Cookie(request,optimusRequest,ret);
		parseInputParams_forms(request,optimusRequest,ret);
	}
	
	@Override
	protected void renderResult(HttpServletRequest request, HttpServletResponse response, Map<String, Object> result) {
		if(result==null || result.isEmpty()){
			return ;
		}
		OptimusResponse optimusResponse = (OptimusResponse)ThreadLocalManager.get(ThreadLocalManager.OPTIMUS_RESPONSE);
		
		FunctionConfigBo fbo = super.getFunctionBo(request);
		List<Map<String,String>> configList = fbo.getConfigList();
		if(configList==null || configList.isEmpty()){
			throw new RuntimeException("Function config has no Query or Edit . ");
		}
		try {
			String configId, type ;
			for(Map<String,String> config : configList){
				configId = config.get("configId");
				type = config.get("type");
				
				if(StringUtils.isBlank(configId) || StringUtils.isBlank(type)){
					continue;
				}
				
				// QueryConfigBo
				if("query".equals(type)){
					QueryConfigBo queryConfigBo = TorchUtils.getQueryConfigBo(configId);
					String queryField = queryConfigBo.getConfigId();
					String queryType = queryConfigBo.getQueryType();
					String formName = queryConfigBo.getConfigName()+"_"+queryType;
					PageBo pageBo = (PageBo)result.get(queryField);// PageBo
					if(pageBo!=null && "Grid".equals(queryType)){
						// 分页
						optimusResponse.setPaginationParams(generatePaginationParams(pageBo));
						optimusResponse.addGrid(formName, pageBo.getResult());
					}else if(pageBo!=null && "Form".equals(queryType)){
						//代码集
						List<Map<String,Object>> pageBoResult = pageBo.getResult();
						if(pageBoResult!=null && !pageBoResult.isEmpty()){
							optimusResponse.addForm(formName, pageBo.getResult().get(0));
						}
					}
					continue;
				}
				// EditConfigBo
				if("edit".equals(type)){
					EditConfigBo editConfigBo = TorchUtils.getEditConfigBo(configId);
					String formName = editConfigBo.getConfigName();
					Object formData = result.get(configId);// ok
					//代码集
					if(formData!=null){
						optimusResponse.addForm(formName+"_Form", formData);
					}
					//optimusResponse.addAttr("result", formData);
					continue;
				} 
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}

	private void parseInputParams_forms(HttpServletRequest request, OptimusRequest optimusRequest, TorchRequest ret) {
		FunctionConfigBo fbo = super.getFunctionBo(request);
		List<Map<String,String>> configList = fbo.getConfigList();
		if(configList==null || configList.isEmpty()){
			throw new RuntimeException("Function config has no Query or Edit . ");
		}
		
		String configId, type ;
		for(Map<String,String> config : configList){
			configId = config.get("configId");
			type = config.get("type");
			
			if(StringUtils.isBlank(configId) || StringUtils.isBlank(type)){
				continue;
			}
			
			// QueryConfigBo
			if("query".equals(type)){
				QueryConfigBo queryConfigBo = TorchUtils.getQueryConfigBo(configId);
				String formName = queryConfigBo.getConfigName();
				Map<String,String> formData = optimusRequest.getForm(formName+"_Form");
				ret.addForm(formName+"_Form", formData);
				continue;
			}
			// EditConfigBo
			if("edit".equals(type)){
				EditConfigBo editConfigBo = TorchUtils.getEditConfigBo(configId);
				String formName = editConfigBo.getConfigName();
				Map<String,String> formData = optimusRequest.getForm(formName+"_Form");
				ret.addForm(formName+"_Form", formData);
				continue;
			} 
		}
	}

	private void parseInputParams_Cookie(HttpServletRequest request, OptimusRequest optimusRequest, TorchRequest ret) {
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			return ;
		}
		String name, value ;
		for(Cookie c : cookies){
			name = c.getName();
			value = c.getValue();
			ret.addCookie(name,value);
		}
	}

	private void parseInputParams_Session(HttpServletRequest request, OptimusRequest optimusRequest, TorchRequest ret) {
		HttpSession session = HttpSessionUtil.getSession();
//		Enumeration<String> names = session.getAttributeNames();
//		while (names.hasMoreElements()) {
//			String key = (String) names.nextElement();
//			Object value = session.getAttribute(key);
//			ret.addSession(key, value);
//		}
		try {
			ret.addSession(OptimusAuthManager.USER, session.getAttribute(OptimusAuthManager.USER));
		} catch (Exception e) {
			System.out.println("----torch准备session异常----->");
		}
		
	}

	private void parseInputParams_jazzAttr(HttpServletRequest request, OptimusRequest optimusRequest,
			TorchRequest ret) {
		@SuppressWarnings("rawtypes")
		Map<String, Map> attrSet = optimusRequest.getAttrSet();
		if(attrSet==null){
			return ;
		}
		String attrValue ;
		for(String attrName : attrSet.keySet()){
			attrValue = StringUtil.safe2String(optimusRequest.getAttr(attrName));
			ret.addParameter(attrName, attrValue);
		}
	}

	private void parseInputParams_requestParameter(HttpServletRequest request, OptimusRequest optimusRequest,
			TorchRequest ret) {
		Map<String, String[]> map = request.getParameterMap();
		String key , value ;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			if(!"postData".equals(key)){
				value = StringUtils.join(entry.getValue(),",");
				ret.addParameter(key, value);
			}
		}
	}
	
	/**
	 * 解析JAZZ分页参数方法
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void parseInputParams_paginationParams(HttpServletRequest request,OptimusRequest optimusRequest,
			TorchRequest ret){
		String postData = request.getParameter("postData");
		if(StringUtils.isBlank(postData)){
			return;
		}
		Map<String, Object> dataMap = JSON.parseObject(postData);
		if(dataMap==null || dataMap.get("data")==null){
			return;
		}
		List<Map> dataList = (List<Map>)dataMap.get("data");
		if(dataList==null || dataList.size()<1){
			return;
		}
		String name;
		String value;
		Map<String,String> pagination = new HashMap<String,String>();
		for (int i = 0; i < dataList.size(); i++) {
			Map data = dataList.get(i);
			name = com.gwssi.torch.util.StringUtil.getMapStr(data,"name");
			value = com.gwssi.torch.util.StringUtil.getMapStr(data,"data");
			if("pagerows".equals(name)){
				pagination.put("pageSize",value);
			}
			if("page".equals(name)){
				pagination.put("pageNo",value);
			}
		}
		ret.setPaginationParams(pagination);
	}
	
	/**
	 * 构造分页参数
	 * 
	 * @param bo
	 * @return
	 */
	public Map<String, Object>generatePaginationParams(Object bo){
		if(bo == null){
			return null;
		}
		Map<String,Object> paginationParamsMap = new HashMap<String,Object>();
		if(bo instanceof PageBo){
			PageBo pageBo = (PageBo)bo;
			paginationParamsMap.put("page", pageBo.getPageIndex());
			paginationParamsMap.put("pagerows", pageBo.getPageRows());
			paginationParamsMap.put("totalrows", pageBo.getTotal());
		}
		return paginationParamsMap;
	}


}
