package cn.gwssi.trs.yearReport.controller;

import cn.gwssi.resource.CacheUtile;
import cn.gwssi.resource.FreemarkerUtil;
import cn.gwssi.trs.yearReport.service.ReportService;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private static  Logger log=Logger.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String priPid=req.getParameter("priPid");//获取主体身份代码
		int flag =  Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());
		String sourceflag=req.getParameter("sourceflag");
		String year=req.getParameter("year");
		
		Map<String,String> params =new HashMap<String,String>();
		Map basicdata=null;
		List<Map> list =null;
		params.put("priPid", priPid);
		params.put("sourceflag", sourceflag);
		params.put("year", year);
		switch (flag) {
		case 0://基本信息
				list = reportService.queryReportJbxx(params,"0");
				if(list!=null && list.size()>0){
					res.addForm("formpanel",list.get(0), null);
					}
			break;
		case 1://隶属
			res.addGrid("jbxxGrid", reportService.queryReportFzjgxx(params), null);
			break;
		case 2://党建信息
			res.addGrid("jbxxGrid", reportService.queryReportDjxx(params), null);
			break;
		case 3://对外担保信息
			res.addGrid("jbxxGrid", reportService.queryReportDwdbxx(params), null);
			break;
		case 4://网站网店信息
			res.addGrid("jbxxGrid", reportService.queryReportWzxx(params), null);
			break;
		case 5://股权变更信息
			res.addGrid("jbxxGrid", reportService.queryReportBgxx(params), null);
			break;
		case 6://出资信息 
			List<Map> tzrjcz=reportService.queryReportCzxx(params);
			res.addGrid("jbxxGrid",tzrjcz, null);
			break;
		case 7://对外投资信息
			List<Map> dwcz=reportService.queryReportDwCzxx(params);
			res.addGrid("jbxxGrid1",dwcz, null);
			break;
		case 8://行政许可信息 
			/*List<Map> dwcz=reportService.queryReportDwCzxx(params);
			res.addGrid("jbxxGrid1",dwcz, null);*/
			break;
		case 9://投资人信息
			res.addGrid("jbxxGrid",reportService.queryReportDwCzxx(params), null);
			break;
		default:
			break;
		}
	}
	
	
	//查看年报年份
	@RequestMapping("/year")
	@ResponseBody
	private List year(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String priPid=req.getHttpRequest().getParameter("priPid");
		List year=null;
		if(StringUtils.isNotBlank(priPid)){
			year=reportService.queryYear(priPid);
			if(year.size()>0&&year!=null){
				//sortMapByValue((Map<String, String>) year.get(0));
				year=sort(year);	
				}
		}
		return year;
	}
	
	/**
	 * 冒泡排序
	 * */
	 public List sort(List sortMap){
		 List args=new ArrayList();
		 List yearList=new ArrayList();
		for(int m=0;m<sortMap.size();m++){
			Map map=(Map) sortMap.get(m);
			args.add(map.get("ancheyear"));
		}
		
		  int time1 = 0,time2 = 0;
		  for(int i = 0 ; i < args.size() ; i++){
		   ++time1;
		   for(int j = i+1 ; j < args.size() ; j++){
		    ++time2;
		    int temp ;
		  Integer argsi= Integer.parseInt((String)args.get(i));
		  Integer argsj= Integer.parseInt((String)args.get(j));
		 if( argsi < argsj){
		     args.set(i, argsj.toString());
		     args.set(j, argsi.toString());
		    }
		   }
		  }
		
		  for(int n =0 ;n<args.size();n++){
			  Map yearMap=new HashMap();
			  yearMap.put("ancheyear", args.get(n));
			  yearList.add(yearMap);
		  }
		  return yearList;
	 }
	 
	 
	 	@ResponseBody
		@RequestMapping("reportjbxx")
		public Map<String,Object> report(OptimusRequest req, OptimusResponse res) throws Exception {
	 		
	 		String priPid=req.getParameter("priPid");//获取主体身份代码
			int flag =  Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());
			String year=req.getParameter("year");
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Map<String,String> params =new HashMap<String,String>();
			Map basicdata=null;
			List<Map> list =null;
			params.put("priPid", priPid);
			params.put("year", year);
			switch (flag) {
			case 0:
				list = reportService.queryReportJbxx(params,"0");
					dataMap=list.get(0);
				break;
			}
			Map<String,Object> returnMap = new HashMap<String,Object>();
			String returnString = "";
			if(dataMap!=null && dataMap.size()>0){
				returnMap.put("entname", dataMap.get("entname"));
				returnMap.put("regno", dataMap.get("regno"));
				returnMap.put("enttype", dataMap.get("enttype"));
			//	returnMap.put("enttype", dataMap.get("enttype"));
				try {
					Map<String,Object> datasMap = new HashMap<String,Object>();
					Map<String,Object> sortMap = CacheUtile.getNdBgLinkedHashMap();
					for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
						sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
					}
					//datasMap.put("weaponMap", dataMap);  
					datasMap.put("weaponMap", sortMap); 
					returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerMFileName"), datasMap);
				} catch (IOException  e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			returnMap.put("returnString", returnString);
			return returnMap;
		}
	 	
}


