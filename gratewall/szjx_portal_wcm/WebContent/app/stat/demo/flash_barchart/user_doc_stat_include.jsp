<%@ page import="com.trs.components.stat.StatDataResultForUser" %>
<%@ page import="com.trs.components.stat.StatDataCountForUser" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>

<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
<%
	// 获取页面参数
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");
	boolean bSearchUser = false, bSearchDept = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("UserName".equalsIgnoreCase(sSelectItem)){
			bSearchUser = true;
		}
		if("GName".equalsIgnoreCase(sSelectItem)){
			bSearchDept = true;
		}
	}
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
			// 总的发稿量
			"Select count(*) DataCount, CrUser from WCMChnlDoc "
					// 统计指定时间段的数据
					+ " Where CrTime>=${StartTime}"
					+ " And CrTime<=${EndTime}"
					+ " and DocStatus>0 and CHNLID>0"
					+ (bSearchUser ? " and CrUser like '%" + sSearchValue + "%'" :"") 
					+ " Group by CrUser",

			// 按照状态统计
			"Select count(*) DataCount, CrUser, DocStatus from WCMDocument "
					// 统计指定时间段的数据
					+ " Where CrTime>=${StartTime}"
					+ " And CrTime<=${EndTime}"
					+ " and DocStatus>0 and CHNLID>0"
					+ (bSearchUser ? " and CrUser like '%" + sSearchValue + "%'" :"")
					+ " Group by CrUser, DocStatus",

			// 按照创作方式(实体/引用)统计
			"Select count(*) DataCount, CrUser, abs(Modal) from WCMChnlDoc "
					// 统计指定时间段的数据
					+ " Where CrTime>=${StartTime}"
					+ " And CrTime<=${EndTime}"
					+ " and DocStatus>0 and CHNLID>0"
					+ (bSearchUser ? " and CrUser like '%" + sSearchValue + "%'" :"")
					+ " Group by CrUser, Modal",

			// 对复制型创作方式的统计
			"Select count(*) DataCount, CrUser from WCMDocument "
					// 统计指定时间段的数据
					+ " Where CrTime>=${StartTime}"
					+ " And CrTime<=${EndTime}"
					+ " And DocOutUpId > 0"
					+ " and DocStatus>0 and CHNLID>0"
					+ (bSearchUser ? " and CrUser like '%" + sSearchValue + "%'" :"")
					+ " Group by CrUser",

			// 按照文档所属类型统计
			"Select count(*) DataCount, CrUser, DocForm from WCMDocument "
					// 统计指定时间段的数据
					+ " Where CrTime>=${StartTime}"
					+ " And CrTime<=${EndTime}"
					+ " and DocStatus>0 and CHNLID>0"
					+ (bSearchUser ? " and CrUser like '%" + sSearchValue + "%'" :"")
					+ " Group by CrUser, DocForm"
			};
	
	CMyDateTime dtQueryStart = new CMyDateTime(),dtQueryEnd=new CMyDateTime();
	//设置时间范围
	String sStartTime = currRequestHelper.getString("StartTime");
	String sEndTime = currRequestHelper.getString("EndTime");
	
	if(!CMyString.isEmpty(sStartTime))
		dtQueryStart.setDateTimeWithString(sStartTime);
	else
		dtQueryStart.setDateTimeWithString("2000-01-01");
	if(!CMyString.isEmpty(sEndTime))
		dtQueryEnd.setDateTimeWithString(sEndTime);
	else
		dtQueryEnd = CMyDateTime.now();
	
	StatDataCountForUser stater = new StatDataCountForUser(pStatSQL);
	StatDataResultForUser result = stater.stat(dtQueryStart,dtQueryEnd);
	//获取用户集合
	int nNum = 0;
	List arUserNames = result.sort(1);
	if(arUserNames != null && (arUserNames.size() > 0)){
		nNum= arUserNames.size();
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