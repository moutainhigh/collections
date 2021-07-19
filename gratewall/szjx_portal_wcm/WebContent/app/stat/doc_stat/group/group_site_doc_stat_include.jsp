<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.stat.IStatResultFilter" %>
<%@ page import="com.trs.components.stat.BaseStatHandler" %>
<%@ page import="com.trs.components.stat.DocStatHandler4ChnlDeptSite" %>
<%@ page import="com.trs.components.stat.DocStatHandler4UserDeptSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.infra.util.database.DBType" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	// 获取页面传入的hostId
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	WebSite site = WebSite.findById(nSiteId);
	String sSiteDesc = CMyString.transDisplay(site.getDesc());
	// 获取页面传入的检索参数(检索项/值,检索时间范围)
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];

	//确定具体检索项
	boolean bSearchDept = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("GName".equalsIgnoreCase(sSelectItem)){
			bSearchDept = true;
		}
	}

	//部门发稿量统计是否采用栏目维度
	boolean bStatByChannel = false;
	String sStatByChannel = ConfigServer.getServer().getSysConfigValue("GROUP_STAT_BY_CHANNEL","false");
	if(sStatByChannel != null && "true".equalsIgnoreCase(sStatByChannel.trim()))
		bStatByChannel = true;
	String sChnlDocStatScale = bStatByChannel ? "ChnlId" : "CrUser";

	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
		// 总的发稿量
		"Select count(*) DataCount, " + sChnlDocStatScale +" , SiteId from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${StartTime}"
				+ " And CrTime<= ${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by " + sChnlDocStatScale + " , SiteId",
		// 按照状态统计
		"Select count(*) DataCount, " + sChnlDocStatScale + " , DocStatus , SiteId from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by " + sChnlDocStatScale + " , DocStatus , SiteId",

		// 按照创作方式(实体/引用)统计
		"Select count(*) DataCount, " + sChnlDocStatScale + " , abs(Modal) , SiteId from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by " + sChnlDocStatScale + " , Modal , SiteId",

		// 对复制型创作方式的统计
		"Select count(*) DataCount, " + sChnlDocStatScale + " , SiteId from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by " + sChnlDocStatScale + " , SiteId",

		// 按照文档所属类型统计
		"Select count(*) DataCount, " + sChnlDocStatScale + " , DocForm , SiteId from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by " + sChnlDocStatScale + " , DocForm , SiteId"
		};
	
	//调用统计类进行统计
	StatAnalysisTool tool = null;
	if(bStatByChannel){
		tool = new StatAnalysisTool(pStatSQL,new DocStatHandler4ChnlDeptSite(nSiteId));
	}else{
		tool = new StatAnalysisTool(pStatSQL,new DocStatHandler4UserDeptSite(nSiteId));
	}
    IStatResult result = tool.stat(sStartTime, sEndTime);

	//构造各种检索条件Filter
	String sWhere = "";
	WCMFilter filter = new WCMFilter("", "", "");
	if(bSearchDept){
		sWhere = " GName like ? ";
		filter.setWhere(sWhere);
		filter.addSearchValues("%" + sSearchValue + "%");
	}
	final Groups groups = Groups.openWCMObjs(null, filter);

	// 按照前面构造的条件,对集合进行过滤
	result = result.filter(new IStatResultFilter() {
		public boolean accept(String key,IStatResult result) {
			String sGroupId = key;
			int nGrupId = Integer.parseInt(sGroupId);
			Group group = null;
			for(int i=0,nSize=groups.size(); i<nSize; i++){
				group = (Group)groups.getAt(i);
				if(group == null) continue;
				if(nGrupId == group.getId()){					
					return true;
				}
			}
			return false;
		}
	});	

	//获取结果集合
	List arGroupKeys = result.list();
	int nNum = 0;
	if(arGroupKeys != null && (arGroupKeys.size() > 0)){
		nNum= arGroupKeys.size();
	}

	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",20);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	currPager.setItemCount(nNum);
	
	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));

	//7.结束
	out.clear();
%>