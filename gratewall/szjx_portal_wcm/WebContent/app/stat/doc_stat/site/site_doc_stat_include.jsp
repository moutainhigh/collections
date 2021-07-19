<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultFilter" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.stat.DocstatHandler4SiteOfUser" %>
<%@ page import="com.trs.components.stat.DocStatHandler4SiteOfGroup" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.List" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	//增量式编程，写完业务逻辑后，要对代码进行整理和重构

	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];

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
	IStatResult result = tool.stat(sStartTime, sEndTime);

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
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				int nSiteId = Integer.parseInt(sKey);
				return oWebSites.indexOf(nSiteId) >= 0;
			}
		});
	}

	//对统计结果总量降序排序
	List SiteKeys = null;
	if(result!=null){
		SiteKeys = result.list();
	}

	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	int nNum = 0;
	if(SiteKeys!=null){
		nNum = SiteKeys.size();
	}
	currPager.setItemCount(nNum);

	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));
%><% out.clear();%>