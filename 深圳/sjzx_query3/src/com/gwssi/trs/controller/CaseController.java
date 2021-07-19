package com.gwssi.trs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;







import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;


import com.gwssi.trs.service.CaseService;
import com.gwssi.util.CaseCacheUtile;
import com.gwssi.util.FreemarkerUtil;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/case")
public class CaseController {

	private static  Logger log=Logger.getLogger(MarketEntityController.class);

	@Autowired
	private CaseService caseService;

	/**
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String priPid=req.getParameter("priPid");//获取案件ID代码
		int flag =  Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());
		String economicproperty=req.getParameter("caseType"); // 判断是什么企业 其他1  内资2  外资3
		Map<String,String> params =new HashMap<String,String>();
		Map basicdata=null;
		List<Map> list =null;
		params.put("priPid", priPid);
		//params.put("sourceflag", sourceflag); 平台无
		if("0".equals(economicproperty)){
		switch (flag) {
		case 1: // 当事人信息
			res.addGrid("jbxxGrid", caseService.querydangshirenxx(params), null);
			break;
		case 2: // 违法行为及处罚信息
			res.addGrid("jbxxGrid", caseService.queryweifaxx(params), null);
			break;
		case 3: // 案源信息
			res.addGrid("jbxxGrid", caseService.queryanyuanxx(params), null);
			break;
		case 4: // 案件移送信息
			res.addGrid("jbxxGrid", caseService.queryanjianyisongxx(params), null);
			break;
		default:
			break;
		}
		}
		else if("1".equals(economicproperty)){
			switch (flag) {
			case 1: // 简易案件当事人信息
				res.addGrid("jbxxGrid", caseService.querysimpledangshirenxx(params), null);
				break;
			case 2: // 简易案件违法行为及处罚信息
				res.addGrid("jbxxGrid", caseService.querysimpleweifaxx(params), null);
				break;
			case 3: // 简易案件案源信息
				res.addGrid("jbxxGrid", caseService.querysimpleanyuanxx(params), null);
				break;
			default:
				break;
			}
		}
	}

	@RequestMapping("/queryCount")
	@ResponseBody
	public int querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		int i =caseService.queryRegCount(null);
		return i;
	} 

	/**
	 * 基本信息
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("scztjbxx")
	public Map<String,Object> sczt(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String priPid=req.getParameter("priPid");//获取主体身份代码


		//int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());

		
		String caseType=req.getParameter("caseType");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("priPid", priPid);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
	
			List<Map> list =null;
			if("0".equals(caseType)){//案件信息
				log.info("案件信息查询pripid:"+priPid);
				list = caseService.queryRegJbxx(params,"0");
			}else if("1".equals(caseType)){//简易案件信息
					log.info("简易案件基本信息查询priPid："+priPid);
					list = caseService.queryRegJbxx(params,"1");
			}else {
				this.log.error("企业类型错误。！！！！！"+"当前企业类型："+caseType);
			}
			if(list!=null && list.size()>0){
				dataMap = list.get(0);
			}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			
			returnMap.put("caseType", dataMap.get("caseType"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =CaseCacheUtile.getLinkedHashMap(caseType);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				} 
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerSFileName"), datasMap);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		returnMap.put("returnString", returnString);
		returnMap.put("casename", dataMap.get("casename"));
		return returnMap;
	}

	
	
	/**
	 * 企业变更详细 信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("entAlterDetail")
	public void alterDetail(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String alterno=(String) req.getAttr("alterno");
		String alterbef = (String) req.getAttr("alterbef");
		String alterafter =(String) req.getAttr("alterafter");
		System.out.println("变更项目-------"+alterno);
		System.out.println("变更前:"+alterbef);
		System.out.println("变更后:"+alterafter);
		res.addGrid("qiyeleixingbiangeng", caseService.queryAlterDetail(alterno,alterbef,alterafter));	

	}
	
	
	
	
	@ResponseBody
	@RequestMapping("list")
	public List testList() {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();

		m.put("title", "市场概况");
		m.put("url", "page/analysis/MarketEntityReport.jsp");
		m.put("l", null);
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "企业注吊销");
		m.put("url", "page/analysis/MarketEntityCanlReport.jsp");
		m.put("l", null);
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "企业设立登记");
		m.put("url", "page/analysis/MarketEntityRegReport.jsp");
		m.put("l", null);
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "业务分析");
		m.put("url", "page/analysis/OperationReport.jsp");
		m.put("l", null);
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "数据分析");
		m.put("url", "");
		m.put("l", list(new String[]{"主题分析","专题分析","综合分析"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "报表管理");
		m.put("url", "");
		m.put("l", list(new String[]{"日常报表","自定义报表"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "数据服务");
		m.put("url", "");
		m.put("l", list(new String[]{"数据采集","数据共享","数据治理","服务管理"}));
		l.add(m);

		m= new HashMap<String,Object>();

		m.put("title", "高级查询");
		m.put("url", "");
		m.put("l", list(new String[]{"自定义查询","数据对比"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "资源管理");
		m.put("url", "");
		m.put("l", list(new String[]{"数据标准","数据源","数据结构装载","数据资源","主题库"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "系统管理");
		m.put("url", "");
		m.put("l", list(new String[]{"用户","角色","url","白名单"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "运行监控");
		m.put("url", "");
		m.put("l", list(new String[]{"实时监控","监控管理","警情管理"}));
		l.add(m);

		m= new HashMap<String,Object>();
		m.put("title", "日志管理");
		m.put("url", "");
		m.put("l", list(new String[]{"日志查询","日志统计","试用报告"}));
		l.add(m);
		return l;
	}

	private List<Map<String,Object>> list(String[] strings) {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		if(strings!=null && strings.length>0){
			for(int i=0;i<strings.length;i++){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("title", strings[i]);
				List<Map<String,Object>> tt = list(strings[i]);
				if(tt==null){
					String str = "";
					if("自定义查询".equals(strings[i])){
						str = "page/senior/gjcx_zdjcx.jsp";
					}else if("数据对比".equals(strings[i])){
						str = "page/senior/gjcx_sjdb.jsp";
					}else if("报表管理".equals(strings[i])){
						//str = "bbgl";
					}else if("数据采集".equals(strings[i])){
						//str = "sjcj";
					}else if("数据标准".equals(strings[i])){
						str = "sjbz";
					}else if("数据源".equals(strings[i])){
						str = "page/resources/resourcesManager.jsp";
					}else if("数据结构装载".equals(strings[i])){
						str = "page/resources/dataStructureManager.jsp";
					}else if("数据资源".equals(strings[i])){
						//str = "sjzy";
					}else if("主题库".equals(strings[i])){
						str = "page/resources/themeManager.jsp";
					}else if("用户".equals(strings[i])){
						str = "page/auth/user_list.html";
					}else if("角色".equals(strings[i])){
						str = "page/auth/role_list.html";
					}else if("url".equals(strings[i])){
						str = "page/auth/func_list.html";
					}else if("白名单".equals(strings[i])){
						str = "page/auth/white_list.html";
					}else if("日志查询".equals(strings[i])){
						//str = "rzcx";
					}else if("日志统计".equals(strings[i])){
						//str = "rztj";
					}else if("试用报告".equals(strings[i])){
						//str = "sybg";
					}else if("实时监控".equals(strings[i])){
						str = "page/monitor/realTimeMonitor.jsp";
					}else if("监控管理".equals(strings[i])){
						str = "page/monitor/monitorManager.jsp";
					}else if("警情管理".equals(strings[i])){
						str = "page/monitor/alarmManager.jsp";
					}else if("日常报表".equals(strings[i])){
						str="page/report/bbgl_rcgl.jsp";
					}
					else if("自定义报表".equals(strings[i])){
						str="page/report/bbgl_zdybb.jsp";
					}
					m.put("url", str);
				}else{
					m.put("url", "");
				}
				m.put("l", tt);
				l.add(m);
			}
		}
		return l;
	}

	private List<Map<String,Object>> list(String string) {
		String[] str=null;
		String[] url=null;
		if("主题分析".equals(string)){
			str= (new String[]{"市场主体概况"});
			url= (new String[]{"page/analysis/MarketEntityModel.jsp"});
		}else if("专题分析".equals(string)){
			str= (new String[]{"业务办理"});
			url= (new String[]{"page/analysis/OperationModel.jsp"});
		}else if("综合分析".equals(string)){
			str= (new String[]{"产业结构趋势"});
			url= (new String[]{"page/analysis/tjfx_zhfx_cyjgqs.jsp"});
		}else if("数据共享".equals(string)){
			str= (new String[]{"共享资源查询","共享接口管理","服务发布管理"});
			url= (new String[]{"page/service/shareSources.jsp","page/service/shareInterfaceManager.jsp","page/service/serviceReleaseManager.jsp"});
		}else if("数据采集".equals(string)){
			str= (new String[]{"采集表管理","采集任务管理"});
			url= (new String[]{"page/service/collectionTableManager.jsp","page/service/collectionTaskManager.jsp"});
		}else if("数据治理".equals(string)){
			str= (new String[]{"检查规则配置","规则包管理","常规检查","检查结果统计","自定义检查","数据结构一致性检查","数据量一致性检查","合标性检查"});
			url= (new String[]{"page/service/checkRuleConfig.jsp","page/service/rulePackageManager.jsp","page/service/routineExamination.jsp","page/service/checkResultStatistics.jsp","page/service/customCheck.jsp","page/service/dataStructureCheck.jsp","page/service/dataCheck.jsp","page/service/calibrationCheck.jsp"});
		}else if("服务管理".equals(string)){
			str= (new String[]{"服务管理","服务内容管理","服务对象管理"});
			url= (new String[]{"page/datachange/service/serviceManager.jsp","page/datachange/servicecontent/serviceContentManager.jsp","page/datachange/serviceobject/serviceObject.jsp"});
		}else if("数据标准".equals(string)){
			str= (new String[]{"标准元数据","标准代码集"});
			url= (new String[]{"page/resources/sMetadataManager.jsp","page/resources/sDaiMaJiManager.jsp"});
		}else if("数据源".equals(string)){
		}else if("数据结构装载".equals(string)){
		}else if("数据资源".equals(string)){
			str= (new String[]{"表管理","表变更情况","表关系管理","代码集管理","元数据查询","视图管理",
					"函数管理","存储过程管理"});
			url= (new String[]{"page/resources/tableManager.jsp","page/resources/tableChange.jsp","page/resources/tableRelation.jsp",
					"page/resources/daiMaJiManager.jsp","page/resources/sourceQuery.jsp","page/resources/viewManager.jsp",
					"page/resources/functionManager.jsp","page/resources/proceduresManager.jsp"});
		}else if("主题库".equals(string)){
		}else if("用户".equals(string)){
		}else if("角色".equals(string)){
		}else if("url".equals(string)){
		}else if("白名单".equals(string)){
		}else if("日志查询".equals(string)){
			str= (new String[]{"查询交换日志","查询系统操作日志"});
			url= (new String[]{"page/blog/exchangeLog.jsp","page/blog/sysOperationLog.jsp"});
		}else if("日志统计".equals(string)){
			str= (new String[]{"服务综合统计","未使用服务情况统计","服务异情况统计"});
			url= (new String[]{"page/blog/serviceComStatistics.jsp","page/blog/notUsedServiceStatistics.jsp","page/blog/serviceDiffStatistical.jsp"});
		}else if("试用报告".equals(string)){
			str= (new String[]{"系统使用报告","使用报告使用情况"});
			url= (new String[]{"page/blog/sysUseReport.jsp","page/blog/useReportUseSituation.jsp"});
		}

		if (str==null){
			return null;
		}
		return list1(str,url);
	}


	/*private String commitments(Map basicdata,OptimusRequest req){
		String regcap=(String) basicdata.get("regcap");
		HttpSession session=req.getHttpRequest().getSession();
		if(session.getAttribute("regcap")!=null){
			session.removeAttribute("regcap");
		}
		session.setAttribute("regcap", regcap);
		return regcap;
	}*/
	private List<Map<String, Object>> list1(String[] strings,String[] url) {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		for(int i=0;i<strings.length;i++){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("title", strings[i]);
			m.put("url", url[i]);
			m.put("l", null);
			l.add(m);
		}
		return l;
	}


	public static void main(String[] args) {
		List<Map<String,Object>> t = new MarketEntityController().testList();
		for(Map<String,Object> m : t){
			System.out.println(""+m.get("title"));
			List<Map<String,Object>> t1 = (List<Map<String, Object>>) m.get("l");
			if(t1!=null && t1.size()>0){
				for(Map<String,Object> m1 : t1){
					System.out.println("	"+m1.get("title")+">>"+m1.get("url"));
					List<Map<String,Object>> t2 = (List<Map<String, Object>>) m1.get("l");
					if(t2!=null && t2.size()>0){
						for(Map<String,Object> m2 : t2){
							System.out.println("		"+m2.get("title")+">>"+m2.get("url"));
						}
					}
				}
			}
		}
	}
	
}
