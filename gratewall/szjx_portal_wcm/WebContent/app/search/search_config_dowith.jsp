<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.ISearchTaskMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.wcm.trsserver.task.SearchTaskConstants" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServer" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="com.trs.infra.persistent.db.DBConnectionConfig" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@include file="../include/public_server.jsp"%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
	//参数接收
	int nTaskId = currRequestHelper.getInt("taskId",0);
	String sMainTableName = currRequestHelper.getString("taskSource");
	String sTaskName = currRequestHelper.getString("taskName");
	int nServerId = currRequestHelper.getInt("serverId",0);
	int nGateWayId = currRequestHelper.getInt("gatewayId",0);
	String sSiteIds = currRequestHelper.getString("siteIds");
	String sFields = currRequestHelper.getString("fields");
	String igNoreSite = currRequestHelper.getString("ignoreSite");

	//参数校验
	if(sSiteIds == null || sSiteIds.trim().equalsIgnoreCase("null")){
		sSiteIds = "";
	}
	if(nServerId <= 0 || nGateWayId <= 0){
		throw new WCMException(LocaleServer.getString("search_config_dowith.jsp.label.error_param", "参数传入有误，请确保传入有效地Server和GateWay信息！"));	
	}

	TRSServer server = TRSServer.findById(nServerId);
	if(server == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_config_dowith.jsp.trsserver_notfound", "没有找到ID为[{0}]的TRSServer!"), new int[]{nServerId}));
	String sTableName = server.getTableName();
	if(sTableName == null) sTableName = "websearch";
	
	TRSGateway gateway = TRSGateway.findById(nGateWayId);
	if(gateway == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_config_dowith.jsp.trsgateway_notfound", "没有找到ID为[{0}]的TRSGateway!"), new int[]{nGateWayId}));
	
	if(sMainTableName == null){
		throw new WCMException(LocaleServer.getString("search_config_dowith.jsp.label.cannot_find_search_datasource", "未指定检索数据源！"));
	}
	//业务逻辑
	try{
		SearchTask task = null;
		if(nTaskId == 0){
			task = SearchTask.createNewInstance();
		}else{
			task = SearchTask.findById(nTaskId);
			if(task == null)
				throw new WCMException(CMyString.format(LocaleServer.getString("search_config_dowith.jsp.cannot_find_search_task", "没有找到ID为[{0}]的检索任务!"), new int[]{nTaskId}));
		}
		if(nTaskId == 0){
			if(sMainTableName.equalsIgnoreCase("WCMDOCUMENT")){
				// 1.1 主表和主键，视图主键
				task.setMainTableName(SearchTaskConstants.DBNAME_DEFAULT_WCMDOC);
				task.setMainIDName(SearchTaskConstants.DB_MAINID_NAME_DEFAULT_WCMDOC);

				// 1.2 视图名称
				task.setViewType(SearchTaskConstants.VIEWTYPE_WCMDOC);
				task.setViewIDName(SearchTaskConstants.VIEW_MAINID_NAME_DEFAULT_WCMDOC);
			}else if(sMainTableName.equalsIgnoreCase("WCMCHANNEL")){
				// 1.1 主表和主键，视图主键
				task.setMainTableName(SearchTaskConstants.DBNAME_DEFAULT_WCMCHNL);
				task.setMainIDName(SearchTaskConstants.DB_MAINID_NAME_DEFAULT_WCMCHNL);

				// 1.2 视图名称
				task.setViewType(SearchTaskConstants.VIEWTYPE_WCMCHNL);
				task.setViewIDName(SearchTaskConstants.VIEW_MAINID_NAME_DEFAULT_WCMCHNL);
			}else{
				// 1.1 主表和主键，视图主键
				task.setMainTableName(SearchTaskConstants.DBNAME_DEFAULT_WCMCHNLDOC);
				task.setMainIDName(SearchTaskConstants.DB_MAINID_NAME_DEFAULT_WCMCHNLDOC);

				// 1.2 视图名称
				task.setViewType(SearchTaskConstants.VIEWTYPE_WCMCHNLDOC);
				task.setViewIDName(SearchTaskConstants.VIEW_MAINID_NAME_DEFAULT_WCMCHNLDOC);
			}
		}
		//获取当前关系数据库的信息
        /*DBConnectionConfig dbConfig = DBManager.getDBManager()
                .getDBConnConfig();
		if(dbConfig.getName().equalsIgnoreCase("ORACLE")){
				task.setViewName(sTableName);
		}else{
			if(sMainTableName.equalsIgnoreCase("WCMDOCUMENT")){
				task.setViewName(SearchTaskConstants.VIEWNAME_WCMDOC_PRE + sTableName);
			}else if(sMainTableName.equalsIgnoreCase("WCMCHANNEL")){
				task.setViewName(SearchTaskConstants.VIEWNAME_WCMCHNL_PRE + sTableName);
			}else{
				task.setViewName(SearchTaskConstants.VIEWNAME_WCMCHNLDOC_PRE + sTableName);
			}
		}*///

		task.setViewName(makeViewName(sMainTableName, sTableName));

		// 1.3 使用的网关和TRSServer
		if(nGateWayId !=0 )task.setTRSGWId(nGateWayId);
		if(nServerId !=0 )task.setTRServerId(nServerId);

		// 1.4 检索的名称
		if(sTaskName != null){
			task.setName(sTaskName);
		}

		// 1.5 检索的字段
		if(sFields != null){
			if(sFields.indexOf("DOCID") < 0 && sMainTableName.equalsIgnoreCase("WCMDOCUMENT"))
				sFields = sFields + ",DOCID,DOCCONTENT";
			task.setFields(sFields);
		}
		if(sMainTableName != null){
			//chnldoc的主表和from有些区别，在此需要特殊设置下
			if("WCMCHNLDOC".equalsIgnoreCase(sMainTableName)){
				task.setFrom("WCMCHNLDOC,WCMDOCUMENT,WCMCHANNEL");
			}else{
				task.setFrom(sMainTableName);
			}
		}

		String sWhere  = "";
		if(sMainTableName.trim().equalsIgnoreCase("WCMCHANNEL")){
			sWhere = "CHNLTYPE not in(11, 2) and Status>=0";
		}else if(sMainTableName.trim().equalsIgnoreCase("WCMDOCUMENT")){
			sWhere = "DocStatus=10 and DocChannel>0";
		}else{
			sWhere = "wcmchnldoc.docid = wcmdocument.docid and wcmchnldoc.chnlid = wcmchannel.channelid and wcmchnldoc.DocStatus=10 and wcmchnldoc.DocChannel>0 and wcmchnldoc.chnlid>0";
		}
		if(!igNoreSite.trim().equalsIgnoreCase("true")){
			if(sMainTableName.trim().equalsIgnoreCase("WCMCHANNEL") || sMainTableName.trim().equalsIgnoreCase("WCMDOCUMENT")){
				sWhere += " and SiteId in (" + sSiteIds + ")";
			}else{
				sWhere += " and wcmchannel.siteid in(" + sSiteIds + ")";
			}
		}

		task.setWhere(sWhere);
		task.setSiteIds(sSiteIds);

		ISearchTaskMgr oSearchTaskMgr = (ISearchTaskMgr) DreamFactory
				.createObjectById("ISearchTaskMgr");
		task = oSearchTaskMgr.saveTask(loginUser, task);

	}catch(Exception ex){
		throw new WCMException("保存检索配置出现异常！",ex);
	}
%>
<%!
private final static String makeViewName(String sMainTableName,String sTableName) {
	//wenyh@2012-07-09,viewname会用于gateway创建数据库临时表.
	//在Oracle等数据库中会容易导致标识符超长的问题,在这里做个判断和处理.尽量避免这个问题.
	//最长标识符的组成为PK_WCM_${viewname}_nnn_INC$_TEMP.nnn是一个重名计数.
	//一般来说,应该较少重名.2位的计数应该差不多.而sTableName的取值最长为6.
	//因此,截取主表名的(3~6)这三个字母(前三个一般是WCM).
	if("WCMCHNLDOC".equalsIgnoreCase(sMainTableName)){
		sMainTableName = "CDOC";
	}else if("WCMDOCUMENT".equalsIgnoreCase(sMainTableName)){
		sMainTableName = "DOC";
	}else if("WCMCHANNEL".equalsIgnoreCase(sMainTableName)){
		sMainTableName = "CHNL";
	}else{
		sMainTableName = sMainTableName.substring(3, 6);
	}
	return sMainTableName + sTableName;
}
%>