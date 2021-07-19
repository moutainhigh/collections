<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ include file="../include/register_check.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帐号管理-全部</title>
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" type="text/css" href="../css/count_common.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_no_border.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="account_common.js"></script>
</head>
<body class="bodyColor">
	<div id="userList" class="lineHeight63">
		<div class="groupsList" id="groups">
		<%
			int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
			JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
			String sServiceId = "wcm61_scmgroup" , sMethodName = "getGroupsOfUser";
			HashMap parameters = new HashMap();
			parameters.put("UserId",String.valueOf(loginUser.getId()));

			SCMGroups oSCMGroups = (SCMGroups) processor.excute(sServiceId,sMethodName, parameters);
			if(oSCMGroups == null || oSCMGroups.size() == 0){
			%>
			<div class="sabrosus"><span class="explainWords">
				<br/><br/>
				<font size="4">非常抱歉，您还没有可管理的分组。<br/><br/></font>
			</div>
			<%
				return;
			}

			SCMGroup oSCMGroup = null,oTempSCMGroup = null;
			String sSubGroupName = null,sGroupName = null;
			HashMap oHashMap = new HashMap();
			int nTempFlag = 0;
			for(int j = 0;j < oSCMGroups.size();j++){
				oSCMGroup = (SCMGroup)oSCMGroups.getAt(j);
				if(oSCMGroup == null)
					continue;
				if(oSCMGroup.getId() == nSCMGroupId && j > 4){
					oTempSCMGroup = oSCMGroup;
					nTempFlag = j;
				}
				oHashMap.put(String.valueOf(j),oSCMGroup);
			}
			if(nTempFlag != 0){
				oHashMap.put(String.valueOf(nTempFlag),oHashMap.get("4"));
				oHashMap.put("4",oTempSCMGroup);
			}

			boolean bMoreFlag=false;
			if(oHashMap.size() > 5)
				bMoreFlag=true;
			for(int i=0;i<oHashMap.size();i++){
				oSCMGroup=(SCMGroup)oHashMap.get(String.valueOf(i));
				if(oSCMGroup==null)
					continue;
				sGroupName = oSCMGroup.getGroupName();
				sSubGroupName = CMyString.truncateStr(sGroupName,10);
				if(i<5){
					if(nSCMGroupId == oSCMGroup.getId() || nSCMGroupId == 0 && i == 0){
		%>
				<span>
					<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="all_accounts_list.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="groupFontColorSelect"><%=CMyString.transDisplay(sSubGroupName)%></a>
					<% if(i!=oHashMap.size()-1){ %>
						<font class="seperateLine">|</font>
					<%}%>
				</span>
					<% }else{ %>
				<span>
					<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="all_accounts_list.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="groupFontColor"><%=CMyString.transDisplay(sSubGroupName)%></a>
					<%if(i!=oHashMap.size()-1){%>
					<font class="seperateLine">|</font>
					<%}%>
				</span>	
		<%	}}}
			if(bMoreFlag){
		%>
				<span class="userOperaterbox groupFontColor">
					<span class="userOperater-title" href="javascript:void(0)">更多&nbsp;<img src="../images/more_1.png" border=0 /></span>
					<div class="userOperater-list" id="moreAccountGroups">
					<%
						for(int i = 5;i < oHashMap.size();i++){
							oSCMGroup = (SCMGroup)oHashMap.get(String.valueOf(i));
							if(oSCMGroup == null)
								continue;
							sGroupName = oSCMGroup.getGroupName();
							sSubGroupName = CMyString.truncateStr(sGroupName,10);
					%>
							<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="all_accounts_list.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="option-quote"><%=CMyString.transDisplay(sSubGroupName)%></a>
					<%	}%>
					</div>
				</span>
			<%}%>
		</div>
	</div>

	<div class="clearFloat"></div>
	<%
		for(int j=0;j < oSCMGroups.size();j++){
			oSCMGroup = (SCMGroup)oSCMGroups.getAt(j);				
				if(oSCMGroup != null)
					break;
		}
		if(nSCMGroupId == 0){
	%>
		<iframe id="frame_account" name="frame_account" style="width:100%;height:100%" src="account_list.jsp?SCMGroupId=<%=oSCMGroup.getId()%>&nPageSize=10&nPageIndex=1" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
	<%	}else{%>
		<iframe id="frame_account" name="frame_account" style="width:100%;height:100%" src="account_list.jsp?SCMGroupId=<%=nSCMGroupId%>&nPageSize=10&nPageIndex=1" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
	<%}%>
</body>
</html>