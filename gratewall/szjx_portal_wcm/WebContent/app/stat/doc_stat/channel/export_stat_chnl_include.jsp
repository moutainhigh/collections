<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.WritableFont" %>
<%@ page import="jxl.write.WritableCellFormat" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.write.Number" %>
<%@ page import="jxl.Workbook" %>

<%@ page import="java.util.List" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile"%>

<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultFilter" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.stat.DocStatHandler4Channel" %>
<%@ page import="com.trs.components.stat.DocStatHandler4ChannelOfGroup" %>
<%@ page import="com.trs.components.stat.DocstatHandler4ChannelOfUser" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
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
%>
<%
	String[] pStatSQLOther = new String[] {
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
	String[] pStatSQL = new String[] {
			//站点的总发稿量
			"Select count(*) DataCount, ChnlId from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId",
			// 按状态统计    
			"Select count(*) DataCount, ChnlId, DocStatus from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, DocStatus",
			 // 按使用方式统计    
			"Select count(*) DataCount, ChnlId, Modal from WCMCHNLDOC"
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, Modal",
			// 对复制型创作方式的统计
			"Select count(*) DataCount, ChnlId from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId",
			// 按照文档所属类型统计
			"Select count(*) DataCount, ChnlId, DocForm from WCMCHNLDOC "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocStatus>0 And ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by ChnlId, DocForm"
	};
	StatAnalysisTool tool = null;
	if(!CMyString.isEmpty(sUserName)){
		tool = new StatAnalysisTool(pStatSQL,new DocstatHandler4ChannelOfUser(sUserName));
	}else if(nGroupId>0){
		tool = new StatAnalysisTool(pStatSQL,new DocStatHandler4ChannelOfGroup(nGroupId));
	}else{
		tool = new StatAnalysisTool(pStatSQL,new DocStatHandler4Channel());
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

	
%><% out.clear();%>