<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultFilter" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.stat.DocStatHandler4ChannelOfGroup" %>
<%@ page import="com.trs.components.stat.DocstatHandler4ChannelOfUser" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	//增量式编程，写完业务逻辑后，要对代码进行整理和重构
	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	//获取名称检索条件
	String selectItem = currRequestHelper.getString("SelectItem");
	String searchValue = CMyString.showNull(currRequestHelper.getString("SearchValue"));

	String sUserName = CMyString.showNull(currRequestHelper.getString("UserName"));
	int nGroupId = currRequestHelper.getInt("GroupId",0);
	boolean bNeedSearch = false;
	if(!CMyString.isEmpty(searchValue)){
		bNeedSearch = true;
	}
	String sChnlOrSiteDesc = "ChnlDesc";
	boolean bSearchBySiteDesc = "sitename".equals(selectItem);
	if(bSearchBySiteDesc){
		sChnlOrSiteDesc = "SiteDesc";
	}
	String sUserOrGroup = "";
%>
<%
	String[] pStatSQL = new String[] {
			//站点的总发稿量
			"Select count(*) DataCount, ChnlId, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, CrUser",
			// 按状态统计    
			"Select count(*) DataCount, ChnlId, DocStatus, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, DocStatus, CrUser",
			 // 按使用方式统计    
			"Select count(*) DataCount, ChnlId, Modal, CrUser from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, Modal, CrUser",
			// 对复制型创作方式的统计
			"Select count(*) DataCount, ChnlId, CrUser from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, CrUser",
			// 按照文档所属类型统计
			"Select count(*) DataCount, ChnlId, DocForm, CrUser from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, DocForm, CrUser"
	};

	StatAnalysisTool tool = null;
	if(!CMyString.isEmpty(sUserName)){
		tool = new StatAnalysisTool(pStatSQL,new DocstatHandler4ChannelOfUser(sUserName));
		sUserOrGroup = CMyString.format(LocaleServer.getString("group_include.user","用户【<span class=\"detail-hostname\">{0}</span>】的"),new String[]{sUserName});
	}else if(nGroupId>0){
		tool = new StatAnalysisTool(pStatSQL,new DocStatHandler4ChannelOfGroup(nGroupId));
		Group currGroup = Group.findById(nGroupId);
		String sGroupName = "";
		if(currGroup!=null){
			sGroupName = currGroup.getName();
		}
		sUserOrGroup = CMyString.format(LocaleServer.getString("group_include.org","组织【<span class=\"detail-hostname\">{0}</span>】的"),new String[]{sGroupName});
	}
	IStatResult result = tool.stat(sStartTime, sEndTime);

	// 构造按站点名称检索，按发稿人和组织id过滤的条件
	String sWhere = "";
	if(bNeedSearch){
		if(bSearchBySiteDesc){
			sWhere = "SiteId in (select SiteId from WCMWEBSITE where SiteDesc like ?)";
		}else{
			sWhere = "ChnlDesc like ?";
		}
	}
	
	WCMFilter filter = new WCMFilter("", sWhere, null);
	if(bNeedSearch){
		filter.addSearchValues("%"+searchValue+"%");
	}
	
	if(!CMyString.isEmpty(sWhere)){
		final Channels oChannels = Channels.openWCMObjs(null, filter);
		
		// 按照前面构造的条件,对集合进行过滤
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				int nChnlId = Integer.parseInt(sKey);
				return oChannels.indexOf(nChnlId) >= 0;
			}
		});
	}

	//对统计结果总量降序排序
	List ChnlKeys = null;
	if(result!=null){
		ChnlKeys = result.list();
	}

	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	int nNum = 0;
	if(ChnlKeys!=null){
		nNum = ChnlKeys.size();
	}
	currPager.setItemCount(nNum);

	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));
%><% out.clear();%>