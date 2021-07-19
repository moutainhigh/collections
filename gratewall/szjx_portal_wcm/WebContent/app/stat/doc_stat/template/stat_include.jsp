<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResultFilter" %>
<%@ page import="com.trs.components.stat.IStatResult" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
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
	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];

	//其它信息/参数处理
	//int nSiteId = currRequestHelper.getInt("SiteId", 0);
	//WebSite currSite = WebSite.findById(nSiteId);
	//if(currSite == null) throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"站点没有找到.");

%>
<%
	//TODO 统计的SQL语句
	String[] pStatSQL = new String[] {
		#STAT_SQL#
	};

	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
	IStatResult result = tool.stat(sStartTime, sEndTime);

	// 按指定的检索过滤
	final String sSearchValue = CMyString.showNull(currRequestHelper.getString("SearchValue"));
	if(!CMyString.isEmpty(sSearchValue)){
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				//TODO 具体的过滤逻辑,需要的数据返回true
				return sKey != null && sKey.indexOf(sSearchValue) != -1;
			}
		});
	}
	
	//对统计结果总量降序排序
	List StatKeys = result.list();
	int nNum =StatKeys != null ? StatKeys.size() : 0;
%>
<%@include file="../../include/static_statpager.jsp"%><% out.clear();%>