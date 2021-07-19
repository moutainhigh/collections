package cn.gwssi.trs.caseinfoentity.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.resource.FreemarkerUtil;
import cn.gwssi.trs.caseinfoentity.service.CaseInfoService;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/caseinfo")
public class CaseInfoController {
		
	private static  Logger log=Logger.getLogger(CaseInfoController.class);
	
	@Autowired
	private CaseInfoService caseInfoService;
	
	@ResponseBody
	@RequestMapping("caseinfojbxx")
	public Map<String,Object> sczt(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String caseid=req.getParameter("caseid");//获取主体身份代码

		Map<String,Object> dataMap = caseInfoService.selectCaseInfoById(caseid);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("casename", dataMap.get("casename"));
			returnMap.put("caseno", dataMap.get("caseno"));
			returnMap.put("entnameurl", dataMap.get("entnameurl"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap = caseInfoService.selectCaseInfoShowTab();
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}
				datasMap.put("weaponMap", sortMap);
				returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerCaseFileName"), datasMap);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}
}
