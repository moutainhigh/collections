<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultsFilter" %>
<%@ page import="com.trs.components.stat.IStatResults" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.stat.DocstatHandler4SiteOfUser" %>
<%@ page import="com.trs.components.stat.DocStatHandler4SiteOfGroup" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>

<%
	//增量式编程，写完业务逻辑后，要对代码进行整理和重构

	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	// 传入时间步长单位
	int nTimeStep = currRequestHelper.getInt("TimeStep",1); 
	int nSqlIndex = currRequestHelper.getInt("SqlIndex",1); 
	// 获取文档状态值或者文档类型值
	String sValue = currRequestHelper.getString("value"); 

	String sUserName = CMyString.showNull(currRequestHelper.getString("UserName"));
	int nGroupId = currRequestHelper.getInt("GroupId",0);

	//获取名称检索条件
	String sCurrSiteDesc = CMyString.showNull(currRequestHelper.getString("SearchValue"));
	boolean bNeedSearch = false;
	if(!CMyString.isEmpty(sCurrSiteDesc)){
		bNeedSearch = true;
	}

%>
<%
	String[] pStatSQLOfOther = new String[] {
			//站点的总发稿量
			"Select count(*) DataCount, SiteId, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, CrUser",
			// 按状态统计    
			"Select count(*) DataCount, SiteId, DocStatus, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, DocStatus, CrUser",
			 // 按使用方式统计    
			"Select count(*) DataCount, SiteId, Modal, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, Modal, CrUser",
			// 对复制型创作方式的统计
			"Select count(*) DataCount, SiteId, CrUser from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, CrUser",
			// 按照文档所属类型统计
			"Select count(*) DataCount, SiteId, DocForm, CrUser from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, DocForm, CrUser"
	};
	String[] pStatSQL = new String[] {
			//站点的总发稿量
			"Select count(*) DataCount, SiteId from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId",
			// 按状态统计    
			"Select count(*) DataCount, SiteId, DocStatus from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, DocStatus",
			 // 按使用方式统计    
			"Select count(*) DataCount, SiteId, Modal from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, Modal",
			// 对复制型创作方式的统计
			"Select count(*) DataCount, SiteId from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId",
			// 按照文档所属类型统计
			"Select count(*) DataCount, SiteId, DocForm from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by SiteId, DocForm"
	};
	StatAnalysisTool tool = null;
	if(!CMyString.isEmpty(sUserName)){
		tool = new StatAnalysisTool(pStatSQLOfOther,new DocstatHandler4SiteOfUser(sUserName));
	}else if(nGroupId>0){
		tool = new StatAnalysisTool(pStatSQLOfOther,new DocStatHandler4SiteOfGroup(nGroupId));
	}else{
		tool = new StatAnalysisTool(pStatSQL);
	}
	IStatResults result = tool.stat(arrTime[0], arrTime[1], nTimeStep);

	// 构造按站点名称检索，按发稿人和组织id过滤的条件
	String sWhere = "";
	if(bNeedSearch){
		sWhere = "SiteDesc like ?";
	}
	
	WCMFilter filter = new WCMFilter("", sWhere, null);
	if(bNeedSearch){
		filter.addSearchValues("%"+sCurrSiteDesc+"%");
	}
	if(!CMyString.isEmpty(sWhere)){
		final WebSites oWebSites = WebSites.openWCMObjs(null, filter);

		// 按照前面构造的条件,对集合进行过滤
		result = result.filter(new IStatResultsFilter() {
			public boolean accept(String sKey, IStatResults statResults) {
				int nSiteId = Integer.parseInt(sKey);
				return oWebSites.indexOf(nSiteId) >= 0;
			}
		});
	}

	//对统计结果总量降序排序
	//List SiteKeys = null;
	//if(result!=null){
	//	SiteKeys = result.list();
	//}

	// 获取主体，做分页需要以这个主体做为总数
	List mainObjList = result.list();
	
		//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	currPager.setItemCount(mainObjList.size());

	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(mainObjList.size()));

	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {		
		String sKey = String.valueOf(mainObjList.get(i-1));
		List list = new ArrayList();
		// 统计总量时需要做特殊处理
		if(nSqlIndex==1)
			 list = result.getResult(nSqlIndex,sKey);
		else
			list = result.getResult(nSqlIndex,new String[]{sKey,sValue});
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		WebSite currSite = WebSite.findById(Integer.parseInt(sKey));
		
		if(currSite==null)continue;
		String sSiteDesc = currSite.getDesc();
		elements.append(","+getTrendChartValue("站点",sSiteDesc,list.toString()));
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	String x_labels = result.getXlabels().toString();
	// 设置走势图相关信息
	response.setHeader("XlabelsNum",String.valueOf(result.getXlabels().size()));
	response.setHeader("ChartType","trendchart");
	String sElements = elements.length()>0?elements.substring(1):"";
	out.print(getTrendChartJson(sFlashDesc,sElements,x_labels,y_max,y_step));
%>