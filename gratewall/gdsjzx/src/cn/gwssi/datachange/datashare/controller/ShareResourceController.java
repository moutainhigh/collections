package cn.gwssi.datachange.datashare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.datachange.datashare.service.ShareResourceService;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/shareResource")
public class ShareResourceController {
	private static  Logger log=Logger.getLogger(ShareResourceController.class);
	
	@Autowired
	private ShareResourceService shareResourceService;
	
	@RequestMapping("/querysr")
	public void querysr(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> shareRes=null;
		if(params!=null){
			shareRes=shareResourceService.queryShareRsList(params);
		}
		res.addGrid("zzjgGrid", shareRes, null);
	}
	
	
	@RequestMapping("/queryfrom")
	public void queryfrom(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String rsid=req.getHttpRequest().getParameter("rsid");//获取参数
		List<Map> shareRes=null;
		Map<String,String> map=new HashMap();
		if(rsid!=null){
			map.put("rsid",rsid);
			shareRes=shareResourceService.queryShareRsList(map);
		}
		res.addForm("formpanel", shareRes.get(0), null);
	}
	
	//表中文名，表类型，表中文名的字段。add共享数据表要的字段
	@RequestMapping("/querysrTofrom")
	public void querysrTofrom(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String tname=req.getHttpRequest().getParameter("tname");
		Map params=new HashMap();
		List<Map> shareRes=null;
		if(tname!=null){
			params.put("tablename", tname);
			shareRes=shareResourceService.queryShareRsList(params);
		}
		if(shareRes!=null){
		res.addForm("formpanel", shareRes.get(0), null);
			}
		}
	@RequestMapping("/querytname")
	@ResponseBody
	public List<Map> querytname(OptimusRequest req, OptimusResponse res) throws OptimusException {
		List<Map> sharetnames=shareResourceService.querytname();
		return sharetnames;
	}
	
	@RequestMapping("/querytdata")
	public void querytdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String params=req.getHttpRequest().getParameter("tname");
		Map map=new HashMap();
		List<Map> sharetnames=null;
		if(StringUtils.isNotBlank(params)){
			map.put("tname", params);
		}
		sharetnames=shareResourceService.querytname();
			res.addGrid("zzjgGrid", sharetnames, null);
	}
	
	@RequestMapping("/querytstate")
	@ResponseBody
	public String querytstate(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String rsid=req.getHttpRequest().getParameter("rsid");
		String state=req.getHttpRequest().getParameter("state");
		Map map=new HashMap();
		if(StringUtils.isNotBlank(rsid)){
			map.put("rsid", rsid);
		}
		if(StringUtils.isNotBlank(state)){
			map.put("state", state);
		}
		return shareResourceService.querystate(map);
	}
	
	@RequestMapping("/queryde")
	public void queryde(OptimusRequest req, OptimusResponse res) throws Exception {
		String tablepkid=req.getHttpRequest().getParameter("rsid");//获取参数
		List<Map> shareRes=null;
		if(tablepkid!=null){
			shareRes=shareResourceService.queryShareLDetail(tablepkid);
		}
		res.addGrid("zzjgGrid", shareRes, null);
	}
	
	@RequestMapping("/selectShareTitle")
	public void selectShareTitle(OptimusRequest req,OptimusResponse res) throws Exception{
		List<Map> shareRes=shareRes=shareResourceService.selectShareTitle();
		String str="";
		StringBuffer sbf = new StringBuffer("{\"data\":[{\"name\":\"subject\",\"vtype\":\"boxlist\",\"data\": [");
		if(shareRes!=null&&shareRes.size()>0){
			for(Map m:shareRes){
				sbf.append("{\"text\":\"");
				sbf.append(m.get("code")).append("\",\"val\": \"").append(m.get("subject")).append("\"},");
			}
			str=sbf.toString().substring(0, sbf.toString().length()-1);
		}
		str = str+"]}]}";
		//res.addResponseBody(str);
		res.addResponseBody(str);
	}

	/**
	 * 根据主题查询对应的表
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/selectShareTable")
	@ResponseBody
	public String selectShareTable(OptimusRequest req,OptimusResponse res) throws Exception{
		//String subject=(String) req.getAttr("subject");//获取参数
		String subject=req.getParameter("subject");
		List<Map> shareRes=shareRes=shareResourceService.selectShareTable(subject);
		//res.addAttr(arg0, arg1, arg2);
		String str="";
		//StringBuffer sbf = new StringBuffer("{\"data\":[{\"name\":\"tablename\",\"vtype\":\"boxlist\",\"data\": [");
		StringBuffer sbf = new StringBuffer("{ \"datalist\":[");
		if(shareRes!=null&&shareRes.size()>0){
			for(Map m:shareRes){
				sbf.append("{\"text\":\"");
				sbf.append(m.get("tablename")).append("\",\"data\": \"").append(m.get("tablecode")).append("\"},");
			}
			str=sbf.toString().substring(0, sbf.toString().length()-1);
		}
		str = str+"]}";
		//str = str+"]}]}";
		return str;
	}
	
	@RequestMapping("/selectShareColumn")
	public void selectShareColumn(OptimusRequest req,OptimusResponse res) throws Exception{
		//String tablecode=(String) req.getAttr("tablecode")  ;//获取参数
		String tablecode=req.getParameter("tablecode")  ;//获取参数
		List<Map> shareRes=shareResourceService.selectShareColumn(tablecode);
		String str="";
		StringBuffer sbf = new StringBuffer("{ \"datalist\":[");
		if(shareRes!=null&&shareRes.size()>0){
			for(Map m:shareRes){
				sbf.append("{\"text\":\"");
				sbf.append(m.get("columnname")).append("\",\"fieldtype\":\"").append(m.get("fieldtype")).append("\",\"data\": \"").append(m.get("columncode")).append("\"},");
			}
			str=sbf.toString().substring(0, sbf.toString().length()-1);
		}
		str = str+"]}";
		res.addResponseBody(str);
	}
}
