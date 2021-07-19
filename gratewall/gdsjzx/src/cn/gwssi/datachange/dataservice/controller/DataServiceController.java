package cn.gwssi.datachange.dataservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

import cn.gwssi.plugin.auth.OptimusAuthManager;
import cn.gwssi.plugin.auth.model.User;

import com.gwssi.optimus.util.TreeUtil;

import cn.gwssi.resource.DateUtil;
import cn.gwssi.broker.server.kafka.producer.ProducThread;
import cn.gwssi.common.resource.ServiceConstants;
import cn.gwssi.datachange.dataservice.model.TPtFwdxjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwdxqxglbBO;
import cn.gwssi.datachange.dataservice.model.TPtFwdypzjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwnrxxBO;
import cn.gwssi.datachange.dataservice.service.DataServiceService;
import cn.gwssi.datachange.datatheme.model.TPtThemexxBO;
import cn.gwssi.datachange.datatheme.service.DataThemeService;
import cn.gwssi.datachange.datatheme.service.ShareServiceService;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpServerConfig;

/**
 * 数据服务控制类
 * @author xue
 * 
 */
@Controller
@RequestMapping("/dataservice")
public class DataServiceController {
	private static  Logger log=Logger.getLogger(DataServiceController.class);

	@Autowired
	private DataServiceService dataServiceService;

	@Autowired
	private DataThemeService dataThemeService;

	@Autowired
	private ShareServiceService shareServiceService;
	/**
	 * 服务基本信息查询
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceList")
	public void servicelist(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		String gnId=req.getParameter("gnId");
		if(StringUtils.isNotBlank(gnId) &&  !"null".equals(gnId)){
			params.put("gnId", gnId);
		}
		List<Map> list=null;
		if(params!=null){
			list=dataServiceService.selectServiceList(params);
		}
		res.addGrid("zzjgGrid", list, null);
	}

	/**
	 * 获取服务详细信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceDetail")
	public void serviceDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String serviceid=req.getParameter("serviceid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("serviceid", serviceid);
		List<Map> list = dataServiceService.selectServiceDetail(params);
		if(list!=null && list.size()>0){
			res.addForm("formpanel",list.get(0), null);
		}
	}

	/**
	 * 保存服务
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveService")
	public void saveService(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwjbxxBO tb = req.getForm("formpanel_edit",TPtFwjbxxBO.class);
		tb.setServiceobjectid((String)req.getAttr("soid"));
		//System.out.println(req.getForm("formpanel_edit").get("funcTree"));
		String update = req.getParameter("update");
		String serviceName=(String)req.getAttr("serviceName");
		String backstageData=(String)req.getAttr("backstageData");
		//验证服务的名字是或相同
		/*boolean bl=dataServiceService.checkServiceName(serviceName);
		if(!bl){
		res.addAttr("back", "errorname");
		return;
		}*/
		try {
			tb.setServicename(serviceName);
			if(backstageData!=null){
				String[] arry=backstageData.split("/");
				/*var name=area+"/"+org+"/"+busName;*/
				for(int i=0;i<arry.length;i++){
					if(i==0){
						tb.setRegion(arry[i]);
					}else{
						if(i==1){
							tb.setServiceorgname(arry[i]);	
						}else{
							if(i==2){
								tb.setBusinessname(arry[i]);			
							}else{
								if(i==3){
									tb.setVersionnumber(arry[i]);			
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*System.out.println(serviceName);
		if(serviceName!=null){
			tb.setServicename(serviceName);
		}*/
		String back = "fail";
		HttpSession session= req.getHttpRequest().getSession();
		User customer=(User)session.getAttribute(OptimusAuthManager.USER);
		Date date  = new Date();
		String url = (String)req.getAttr("url");
		if(StringUtils.isNotEmpty(url)){
			tb.setServiceurl(url);
		}
		String funcIdsStr = (String)req.getAttr("funcIdsStr");
		List<String> funcIds = new ArrayList<String>();
		if(StringUtils.isEmpty(funcIdsStr)){
		}else{
			funcIds = Arrays.asList(funcIdsStr.split(","));
			tb.setServiceconentid(funcIds.get(0));
		}
		String funcNamesStr = (String)req.getAttr("funcNamesStr");
		List<String> funcNames = new ArrayList<String>();
		if(StringUtils.isEmpty(funcNamesStr)){
		}else{
			funcNames = Arrays.asList(funcNamesStr.split(","));
			tb.setServiceconentshow(funcNames.get(0));
		}
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));
			if("2".equals(tb.getServicetype())){
				TPtFwdypzjbxxBO tb1 = req.getForm("formpanel_edit1",TPtFwdypzjbxxBO.class);
				dataServiceService.updateService(tb,tb1);
			}else{
				dataServiceService.updateService(tb);
			}

			back = "success";
		}else{//新增
			//tb.setCreateperson(customer.getLoginName());
			tb.setCreateperson("sys");
			tb.setCreatetime(DateUtil.DateToStr(date));
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));
			try {
				if("2".equals(tb.getServicetype())){
					TPtFwdypzjbxxBO tb1 = req.getForm("formpanel_edit1",TPtFwdypzjbxxBO.class);
					dataServiceService.addService(tb,tb1);
				}else{
					dataServiceService.addService(tb);
				}

				back = "success";
			} catch (Exception e) {
				back="errorname";
			}
			/*String url=tb.getServiceurl();
			if(url!=null){
				if(!tb.getServiceurl().contains("/ws/jaxrs")){
					tb.setServiceurl(url+"/ws/jaxrs");
				}
			}*/

		}	
		res.addAttr("back", back);
	}

	/**
	 * 获取服务树     服务对象----------》服务
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceTree")
	public void serviceTree(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String fwdxjbid = req.getParameter("fwdxjbid");
		List authFuncList = dataServiceService.selectServicemakeCheckedTree(fwdxjbid);
		List allFucnList = dataServiceService.selectServiceTree(fwdxjbid);
		if(allFucnList!=null){
			TreeUtil.makeCheckedTree(allFucnList, authFuncList, "serviceid");
			res.addTree("funcTree", allFucnList);
		}
	}

	/**
	 * 获取所有服务     服务对象----------》服务
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceAll")
	public void serviceAll(OptimusRequest req, OptimusResponse res) throws OptimusException {
		// String fwdxjbid = req.getParameter("fwdxjbid");
		List list = dataServiceService.selectServiceTable();
		res.addGrid("zzjgGrid", list, null);
	}

	/**
	 * 获取已选所有服务     服务对象----------》服务
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceCheckedAll")
	@ResponseBody
	public List<Map> serviceCheckedAll(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String fwdxjbid = req.getParameter("fwdxjbid");
		List checkedFuncList = dataServiceService.selectCheckedServiceTable(fwdxjbid);
		return checkedFuncList;
	}

	/**
	 * 获取服务树              服务内容---》服务
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/servicenrTree")
	public void servicenrTree(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String fwnrid = req.getParameter("fwnrid");
		List authFuncList = dataServiceService.fromnrselectServicemakeCheckedTree(fwnrid);
		List allFucnList = dataServiceService.fromnrselectServiceTree(fwnrid);
		if(allFucnList!=null){
			TreeUtil.makeCheckedTree(allFucnList, authFuncList, "serviceid");
		}
		res.addTree("showpanel", allFucnList);
	}

	/**
	 * 服务内容查询
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceContentList")
	public void servicecontentlist(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=dataServiceService.selectServiceContentList(params);
		//{page=1, totalrows=11, pagerows=20}
		//res.setPaginationParams(pageQuery(list,res));
		res.addGrid("zzjgGrid", list, null);
		System.out.println(",m,m");
	}
	/*	
	private static Map pageQuery(List<Map> list,OptimusRequest req){
		Map map=new HashMap();
		map.put("page", 1);//前面传的
		map.put("totalrows", list.size()-1);//前面传的
		map.put("pagerows", 10);//前面传的
		return map;
	}*/


	/**
	 * 获取内容树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceContentTree")
	public void serviceContentTree(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String serviceid = req.getParameter("serviceid");
		String update = req.getParameter("update");
		List allFucnList = dataServiceService.selectServiceContentTree();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			List authFuncList = dataServiceService.selectServiceContentmakeCheckedTree(serviceid);
			TreeUtil.makeCheckedTree(allFucnList, authFuncList, "serviceconentid");
		}
		res.addTree("funcTree", allFucnList);
	}

	@RequestMapping("/serviceContentSelect")
	public void serviceContentSelect(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String serviceid = req.getParameter("serviceid");
		String update = req.getParameter("update");
		List allFucnList = dataServiceService.selectServiceContent();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			List authFuncList = dataServiceService.selectServiceContentmakeCheckedTree(serviceid);
			for(Object o : allFucnList){
				Map mo = (Map) o;
				for(Object t : authFuncList){
					Map mt = (Map) t;
					if(mo.get("fwnrid") != null && mo.get("fwnrid").equals(mt.get("serviceconentid"))){
						mo.put("checked", "true");
						break ;
					}
				}
			}
			//TreeUtil.makeCheckedTree(allFucnList, authFuncList, "serviceconentid");
		}
		res.addTree("serviceconentid", allFucnList);
	}

	/**
	 * 服务对象（服务账户）查询
	 * @param req
	 * @param res
	 * @throws OptimusException		
	 */
	@RequestMapping("/serviceObjectList")
	public void serviceobjectlist(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		String areaCode = req.getParameter("area");
		List<Map> list=dataServiceService.selectServiceObjectList(params);
		res.addGrid("zzjgGrid", list, null);
	}

	@RequestMapping("/allServiceObjectList")
	public void allServiceobjectlist(OptimusRequest req, OptimusResponse res) throws OptimusException {
		List<Map> list=dataServiceService.selectAllServiceObjectList();
		res.addGrid("zzjgGrid", list, null);
	}

	/**
	 * 保存服务内容
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveServiceContent")
	public void saveServiceContent(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwnrxxBO tb = req.getForm("formpanel_edit",TPtFwnrxxBO.class);
		String update = (String)req.getAttr("update");
		String tableCode = (String) req.getAttr("tableCode");
		String tableName = (String)req.getAttr("tableName");
		String condition = (String)req.getAttr("condition");
		String columnCode = "";
		String columnName = "";
		String back = "fail";

		StringBuffer sbf = new StringBuffer("select ");
		//	if(StringUtils.isNotEmpty(tb.getContenttype())&&"0".equals(tb.getContenttype())){//webservice
		columnCode = (String)req.getAttr("columnCode");
		columnName = (String)req.getAttr("columnName");
		if(StringUtils.isNotEmpty(columnCode)){
			String column = columnCode.substring(1, columnCode.length());
			sbf.append(column).append(" from ");
			tb.setColumncode(column);//字段英文名
			tb.setColumnname(columnName.substring(1, columnName.length()));//字段中文名
		}
		/*}else{
			sbf.append("* from ");
		}*/
		if(StringUtils.isNotEmpty(tableCode)){
			String str = tableCode.substring(1, tableCode.length());
			sbf.append(str);
			tb.setTablecode(str);
		}
		if(StringUtils.isNotEmpty(tableName)){
			tb.setTablename(tableName.substring(1, tableName.length()));
		}
		if(StringUtils.isNotEmpty(condition)){
			//	String[] conditions=condition.split(" // ");
			tb.setServicecontentcondition(condition);
			condition=condition.replace("//", " ");
			sbf.append(" where "+condition.replace("-", " "));
		}
		tb.setServicecontent(sbf.toString());

		HttpSession session= req.getHttpRequest().getSession();
		User customer=(User)session.getAttribute(OptimusAuthManager.USER);
		Date date  = new Date();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));
			List<Map> preview=previewServiceContent(req,res,tb);
			if(preview!=null){
				dataServiceService.updateServiceContent(tb);
				back = "success";
			}else{
				back="sql_error";//校验数据为空
			}

		}else{//新增
			//tb.setCreateperson(customer.getLoginName());
			tb.setCreateperson("sys");
			tb.setCreatetime(DateUtil.DateToStr(date));
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));

			List<Map> preview=previewServiceContent(req,res,tb);
			if(preview!=null){
				if(dataServiceService.addServiceContent(tb) == -1)
					back = "name_unique";
				else
					back = "success";
			}else{
				back="sql_error";//校验数据为空
			}
		}
		res.addAttr("back", back);
	}
	/**
	 * 保存服务内容之前预览或者校验情况
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/previewServiceContent")
	@ResponseBody
	public List<Map> previewServiceContent(OptimusRequest req, OptimusResponse res,TPtFwnrxxBO tb) throws OptimusException {
		StringBuffer sql=new StringBuffer();
		if(tb.getFwnrid()!=null ){
			sql.append(tb.getServicecontent().replace("select","select top 1"));
			try {
				return dataServiceService.previewServiceContent(sql.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}

		}else{
			String tableCode = (String) req.getParameter("tableCode");  //---没有值
			String columnCode = (String)req.getParameter("columnCode");
			String condition = (String)req.getParameter("condition");

			String previewsql=null;
			if(tableCode !=null && columnCode !=null){
				previewsql="select top 200 "+columnCode +" from "+ tableCode;
			}

			if(condition !=null && condition.trim() !=""){
				previewsql=previewsql+" where "+condition.replace("-", " ");
			}
			try {
				return dataServiceService.previewServiceContent(previewsql.replace("//", " "));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
	}







	/**
	 * 数据服务预览数据
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/previewContent")
	@ResponseBody
	public Map previewContent(String id, OptimusResponse res) throws OptimusException {
		String previewsql=null;
		
		if(id!=null ){
			List list=dataServiceService.queryFwnrsql(id);
			if(list!=null&&list.size()>0){
				Map map=(Map) list.get(0);
				String servicecontentname=(String) map.get("servicecontentname");	
				String servicecontent=(String) map.get("servicecontent");		
				String servicecontentshow=(String) map.get("servicecontentshow");	
				String servicecontentcondition=(String) map.get("servicecontentcondition");		
				String contenttype=(String) map.get("contenttype");		
				String columnname=(String) map.get("columnname");		
				String columncode=(String) map.get("columncode");		
				String tablename=(String) map.get("tablename");		
				String tablecode=(String) map.get("tablecode");		
				String isenabled=(String) map.get("isenabled");
				
				if(tablecode !=null && columncode !=null){
					previewsql=servicecontent.replace("select","select top 5 ")  ;
					System.out.println(previewsql);
				}

				if(servicecontentcondition !=null && servicecontentcondition.trim() !=""){
					previewsql=previewsql+" where "+servicecontentcondition.replace("-", " ");
				}
				try {
					List<Map> list1= dataServiceService.previewServiceContent(previewsql.replace("//", " "));
					
					map.put("result", list1);
					return map;
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return null;
				}
			}
			}
			
		return null;
	}



	/**
	 * 保存服务对象
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveServiceObject")
	public void saveServiceObject(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwdxjbxxBO tb = req.getForm("formpanel_edit",TPtFwdxjbxxBO.class);
		String update = req.getParameter("update");
		//String serviceName="client";
		String serviceName=(String)req.getAttr("serviceName");
		String backstageData=(String)req.getAttr("backstageData");

		String funcIdsStr=(String)req.getAttr("funcIdsStr");

		List<String> funcIdslist=Arrays.asList(funcIdsStr.split(","));
		tb.setServiceobjectname(serviceName);



		String back = "fail";
		HttpSession session= req.getHttpRequest().getSession();
		User customer=(User)session.getAttribute(OptimusAuthManager.USER);
		Date date  = new Date();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));


			List<String> serviceobjname=Arrays.asList(tb.getServiceobjectname().split("/"));

			tb.setServiceorgname(serviceobjname.get(1));
			tb.setBusinessname(serviceobjname.get(2));
			//	tb.setServiceorgname("sss");
			//	tb.setBusinessname("oai");
			dataServiceService.updateServiceObject(tb);
			//还原主题列表中的数据
			dataThemeService.originalTheme(tb.getFwdxjbid());

			back = "success";
		}else{//新增
			//tb.setCreateperson(customer.getLoginName());
			List<String> serviceobjname=Arrays.asList(backstageData.split("/"));
			tb.setServiceobjectregion(serviceobjname.get(0));
			tb.setServiceorgname(serviceobjname.get(1));
			tb.setBusinessname(serviceobjname.get(2));
			tb.setCreateperson("sys");
			tb.setCreatetime(DateUtil.DateToStr(date));
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setLastmodifyperson("sys");
			tb.setLastmodifytime(DateUtil.DateToStr(date));
			if(dataServiceService.addServiceObject(tb) == -1)
				back = "name_unique";
			else
				back = "success";
		}

		//修改或增加主题列表
		for(int i=0;i<funcIdslist.size();i++){
			List<Map<String,String>> datatheme=dataThemeService.findZtid(funcIdslist.get(i));
			if(datatheme!=null 	&& datatheme.size()>0){
				TPtThemexxBO bo=new TPtThemexxBO();
				bo.setZtid(funcIdslist.get(i));
				bo.setThemename(datatheme.get(0).get("themename"));
				bo.setCreatetime(datatheme.get(0).get("createtime"));
				bo.setCreateperson(datatheme.get(0).get("createperson"));
				bo.setFwdxname(datatheme.get(0).get("fwdxname"));
				bo.setIsstart(datatheme.get(0).get("isstart"));
				bo.setLastuupdatetime((datatheme.get(0).get("lastuupdatetime")));
				bo.setModifytime(datatheme.get(0).get("modifytime"));
				bo.setLastuupdatetime((datatheme.get(0).get("lastmodifyperson")));
				bo.setFwdxjbid(tb.getFwdxjbid());
				dataThemeService.updateService(bo);
			}
		}
		res.addAttr("back", back);
	}

	/**
	 * 获取服务对象详细信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceObjectDetail")
	public void serviceObjectDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String fwdxjbid=req.getParameter("fwdxjbid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("fwdxjbid", fwdxjbid);
		List<Map> list = dataServiceService.selectServiceObjectDetail(params);
		if(list!=null && list.size()>0){
			res.addForm("formpanel",list.get(0), null);
		}
	}

	/**
	 * 获取服务对象所提供的所有服务信息 和可供使用的服务信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceObjectDetailsfw")
	public void serviceObjectDetailsfw(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String fwdxjbid=req.getParameter("fwdxjbid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("fwdxjbid", fwdxjbid);
		List<Map> list = dataServiceService.selectServiceObjectDetailsfw(params);
		for(int i=0;i<list.size();i++){
			list.get(i).put("lineNo", i+"");
		}
		res.addGrid("zzjgGrid", list, null);
	}

	/**
	 * 获取服务内容详细信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/serviceContentDetail")
	public void serviceContentDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String fwnrid=req.getParameter("fwnrid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("fwnrid", fwnrid);
		List<Map> list = dataServiceService.selectServiceContentDetail(params);
		if(list!=null && list.size()>0){
			res.addForm("formpanel",list.get(0), null);
		}
	}


	/**
	 * 删除服务对象
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteServiceObject")
	public void deleteServiceObject(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		List<Map<String, String>> list = req.getGrid("fwdxjbid");
		for(Map<String, String> map : list){
			String userId = (String) map.get("fwdxjbid");
			dataServiceService.deleteServiceObject(userId);
			//删除主题列表中的数据
			dataThemeService.delTheme(userId);
		}
		resp.addAttr("back", "success");
	}

	/**
	 * 启停服务对象
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/runServiceObject")
	public void runServiceObject(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String fwdxjbid=req.getParameter("fwdxjbid");
		List<Map> obj=dataServiceService.findServiceObject(fwdxjbid);
		String state=req.getParameter("state");
		String servicename=null;
		List<Map> list=null;

		//0是启动
		if("1".equals(state)){


			//推送权限信息
			Map map=new HashMap();
			map.put("serviceDXid",fwdxjbid);
			map.put("handle", "serviceDX");
			list=shareServiceService.findServiceName(map);
		}else{

			if(obj!=null&&obj.size()>0){
				Map map=new HashMap();
				map.put("serviceDXidControl",fwdxjbid);
				map.put("handle", "serviceDXControl");
				list=shareServiceService.findServiceName(map);

			}
		}

		dataServiceService.updateRunServiceObject(fwdxjbid,state);

		if("1".equals(state)){
			//推送
			for(int i=0;i<list.size();i++){
				servicename=(String)list.get(i).get("serviceobjectname");
				List xmllistClient=shareServiceService.selectClientList(servicename);
				new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,xmllistClient).run();
				List xmllistServer=shareServiceService.selectServerList(servicename);
				new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,xmllistServer).run();
			}
		}else{
			if("0".equals(obj.get(0).get("controlobjectstate"))){
				//推送
				for(int i=0;i<list.size();i++){
					servicename=(String)list.get(i).get("serviceobjectname");
					List xmllistClient=shareServiceService.selectClientList(servicename);
					new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,xmllistClient).run();
					List xmllistServer=shareServiceService.selectServerList(servicename);
					new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,xmllistServer).run();
				}
			}else{
				new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,null).run();
				new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,null).run();
			}
		}
		resp.addAttr("back", "success");
	}

	/**
	 * 启停服务内容
	 * @param req
	 * @param resp
	 * 
	 * @throws OptimusException
	 */
	@RequestMapping("/runServiceContent")
	public void runServiceContent(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String fwnrid=req.getParameter("fwnrid");
		String isenabled=req.getParameter("isenabled");
		String str = "";
		if(StringUtils.isBlank(fwnrid) || StringUtils.isBlank(isenabled)){
			str = "fail";
		}
		if("1".equals(isenabled.trim())){//停用时需要检查是否被服务引用
			List list = dataServiceService.selectServiceByContentId(fwnrid);
			if(list!=null&list.size()>0){
				str = "exist";
			}else{
				dataServiceService.updateRunServiceContent(fwnrid,isenabled);
				str = "success";

			}
		}else{
			dataServiceService.updateRunServiceContent(fwnrid,isenabled);
			str = "success";
		}



		resp.addAttr("back", str);
	}


	/**
	 * 启停服务
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/runService")
	public void runService(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String serviceid=req.getParameter("serviceid");
		String state=req.getParameter("state");
		String str = "";
		if(StringUtils.isBlank(state) || StringUtils.isBlank(state)){
			str = "fail";
		}
		if("1".equals(state.trim())){

			//推送权限信息
			Map map=new HashMap();
			map.put("serviceid",serviceid);
			map.put("handle", "service");
			List<Map> list=shareServiceService.findServiceName(map);

			//停用时需要检查是否被服务对象引用,被引用的话，则删掉中间表记录，再改变状态
			dataServiceService.delServiceObjectByServiceId(serviceid);

			//得在调用后执行
			for(int i=0;i<list.size();i++){
				String servicename=(String)list.get(i).get("serviceobjectname");
				List xmllistClient=shareServiceService.selectClientList(servicename);
				new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,xmllistClient).run();
				List xmllistServer=shareServiceService.selectServerList(servicename);
				new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,xmllistServer).run();
			}
		}
		dataServiceService.updateRunService(serviceid,state);
		str = "success";

		resp.addAttr("back", str);
	}

	/**
	 * 保存服务对象授权
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveLicense")
	public void saveLicense(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwdxqxglbBO tPtFwdxqxglbBO = req.getForm("formpanel", TPtFwdxqxglbBO.class);
		String funcIdsStr = (String)req.getAttr("funcIdsStr");
		List<String> funcIds = new ArrayList<String>();
		if(StringUtils.isEmpty(funcIdsStr)){
		}else{
			funcIds = Arrays.asList(funcIdsStr.split(","));
		}
		dataServiceService.insertLicense(tPtFwdxqxglbBO, funcIds);

		//推送权限信息
		Map map=new HashMap();
		map.put("serviceDXid",tPtFwdxqxglbBO.getServiceobjectid());
		map.put("handle", "serviceDX");
		List<Map> list=shareServiceService.findServiceName(map);

		//得在调用后执行
		for(int i=0;i<list.size();i++){
			String servicename=(String)list.get(i).get("serviceobjectname");
			List xmllistClient=shareServiceService.selectClientList(servicename);
			new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,xmllistClient).run();
			List xmllistServer=shareServiceService.selectServerList(servicename);
			new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,xmllistServer).run();
			//new ProducThread("GD-数据中心-01","test","1111111111111111111111111");
		}
	}

	/**
	 * 保存服务对象授权   -- 正在用
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveLicenseTable")
	public void saveLicenseTable(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwdxqxglbBO tPtFwdxqxglbBO = new TPtFwdxqxglbBO();
		String fwdxjbid = (String)req.getParameter("fwdxjbid");
		tPtFwdxqxglbBO.setServiceobjectid(fwdxjbid);
		String funcIdsStr = (String)req.getParameter("fwjbid");
		List<String> funcIds = new ArrayList<String>();
		if(StringUtils.isEmpty(funcIdsStr)){
		}else{
			funcIds = Arrays.asList(funcIdsStr.split(","));
		}
		dataServiceService.insertLicense(tPtFwdxqxglbBO, funcIds);

		//推送权限信息
		Map map=new HashMap();
		map.put("serviceDXid",tPtFwdxqxglbBO.getServiceobjectid());
		map.put("handle", "serviceDX");
		List<Map> list=shareServiceService.findServiceName(map);

		//得在调用后执行
		for(int i=0;i<list.size();i++){
			String servicename=(String)list.get(i).get("serviceobjectname");
			List xmllistClient=shareServiceService.selectClientList(servicename);
			new ProducThread(servicename,ServiceConstants.CLIENT_CAHCHE_TOPIC,xmllistClient).run();
			List xmllistServer=shareServiceService.selectServerList(servicename);
			new ProducThread(servicename,ServiceConstants.SERVER_CAHCHE_TOPIC,xmllistServer).run();
			//new ProducThread("GD-数据中心-01","test","1111111111111111111111111");
		}
	}

	/**
	 * 订阅服务配置基本信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/subscriptionList")
	public void subscriptionList(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=null;
		if(params!=null){
			list=dataServiceService.selectSubscriptionList(params);
		}
		res.addGrid("zzjgGrid", list, null);
	}

	/**
	 * 保存订阅服务配置
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveSubscription")
	public void saveSubscription(OptimusRequest req, OptimusResponse res) throws OptimusException {
		TPtFwdypzjbxxBO tb = req.getForm("formpanel_edit",TPtFwdypzjbxxBO.class);
		String update = req.getParameter("update");
		String back = "fail";
		HttpSession session= req.getHttpRequest().getSession();
		User customer=(User)session.getAttribute(OptimusAuthManager.USER);
		Date date  = new Date();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			//tb.setLastmodifyperson(customer.getLoginName());
			tb.setModifyperson("sys");
			tb.setModifytime(DateUtil.DateToStr(date));
			dataServiceService.updateSubscription(tb);
			back = "success";
		}else{//新增
			tb.setModifyperson("sys");
			tb.setModifytime(DateUtil.DateToStr(date));
			dataServiceService.addSubscription(tb);
			back = "success";
		}
		res.addAttr("back", back);
	}

	/**
	 * 获取订阅服务配置详细信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/subscriptionDetail")
	public void subscriptionDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String fwdypzjbxxid=req.getParameter("fwdypzjbxxid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("fwdypzjbxxid", fwdypzjbxxid);
		List<Map> list = dataServiceService.selectSubscriptionDetail(params);
		if(list!=null && list.size()>0){
			res.addForm("formpanel",list.get(0), null);
		}
	}

	/**
	 * 获取订阅服务配置树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/subscriptionTree")
	public void serviceSubscriptionTree(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String serviceid = req.getParameter("serviceid");
		String update = req.getParameter("update");
		List allFucnList = dataServiceService.selectSubscriptionTree();
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			List authFuncList = dataServiceService.selectSubscriptionmakeCheckedTree(serviceid);
			TreeUtil.makeCheckedTree(allFucnList, authFuncList, "fwdypzjbxxid");
		}
		res.addTree("funcTree1", allFucnList);
	}

	/**
	 * 根据服务id获取订阅服务配置信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/subscriptionDetailByServiceid")
	public void subscriptionDetailByServiceid(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String serviceid=req.getParameter("serviceid");//获取主体身份代码
		Map<String,String> params =new HashMap<String,String>();
		params.put("serviceid", serviceid);
		List<Map> list = dataServiceService.selectSubscriptionDetailByServiceid(params);
		if(list!=null && list.size()>0){
			res.addForm("formpanel",list.get(0), null);
		}
	}




	/**
	 * 初始化区域的值 - 如：广东省/广州市/企管处/登记系统
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/iniArea")
	@ResponseBody
	public List<Map> iniArea(OptimusRequest req, OptimusResponse res)throws OptimusException{
		List<Map> list = new ArrayList<Map>();
		String belongOrg=req.getParameter("belongOrg");
		if(belongOrg!=null){
			list = dataServiceService.iniArea("belongOrg",belongOrg);
		}else{
			list = dataServiceService.iniArea("belongArea","");
		}
		return list;
	}

	/**
	 * 初始化区域的值 - 如：广东省/广州市/企管处/登记系统
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/selectListerInfo")
	@ResponseBody
	public List<Map> selectListerInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String serviceId = req.getParameter("serviceId");
		String serviceObjectId = req.getParameter("serviceObjectId");
		serviceId = (serviceId == null || "".equals(serviceId.trim())) ? null : serviceId;
		serviceObjectId = (serviceObjectId == null || "".equals(serviceObjectId.trim())) ? null : serviceObjectId;
		return dataServiceService.selectListenerInfo(serviceId, serviceObjectId);
	}

	/**
	 * 订阅服务存储
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/saveListenerService")
	@ResponseBody
	public void saveListenerService(OptimusRequest req, OptimusResponse res) throws OptimusException{
		TPtFwdypzjbxxBO tb = req.getForm("formpanel_edit1",TPtFwdypzjbxxBO.class);
		//		System.out.println(tb.getFwdypzjbxxid() + "," + tb.getServiceobjectid() + ", " + tb.getServiceid());
		//验证周期时间设置
		if(!CronExpression.isValidExpression(tb.getFrequency())){
			try{
				Integer.parseInt(tb.getFrequency());
			}catch(Exception e){
				res.addAttr("back", "周期设置格式错误！");
				return ;
			}
		}
		//验证ftp地址是否正确
		try{
			new FtpServerConfig(tb.getPath());
		}catch(Exception e){
			res.addAttr("back", "FTP地址格式错误！");
			return ;
		}
		if(tb.getFwdypzjbxxid() == null || "".equals(tb.getFwdypzjbxxid().trim())) {//增加一个订阅数据
			//serviceObjectId&serviceId相同的是否存在
			List<Map> temp = dataServiceService.selectListenerInfo(tb.getServiceid(), tb.getServiceobjectid());
			if(temp != null && temp.size() > 0){
				res.addAttr("back", "同个对象对同一个服务不能产生多次订阅！");
				return ;
			}
			int result = dataServiceService.addService(tb); //增加
			res.addAttr("back", result == -1 ? "该服务已停用，无法订阅！" : "success");
		}else{//更新一个订阅数据
			dataServiceService.updateSubscription(tb); //更新
			res.addAttr("back", "success");
		}
	}
}
