<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../include/public_server.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>帐号管理-权限</title>
		<link rel="stylesheet" href="../css/public.css">
		<link rel="stylesheet" type="text/css" href="../css/count_common.css">
		<script src="../js/jquery-1.7.2.min.js"></script>
		<script src="../js/scm_common.js"></script>
		<script src="../js/iframe_public.js"></script>
		<script src="../js/getActualTop.js" type="text/javascript"></script>
		<script src="rights_management_list.js"></script>
		<script src="account_common.js"></script>
	</head>
	<body class="bodyColor">
	<%
		//非SCM管理员，提示后返回
		if(!SCMAuthServer.isAdminOfSCM(loginUser)){
	%>
			<div  class="sabrosus">
			<font size="4">
				<b>非常抱歉，您不是SCM管理员，不能对系统权限进行配置！</b>
			</font>
			</div>
	<%
			return;
		}
	%>
		<div id="userList" class="lineHeight63">
			<div class="groupsList" id="groups">
			<%
				// 获取用户可以设置权限的分组
				JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
				int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
				String sServiceId = "wcm61_scmgroup" , sMethodName = "getGroupsForRightSet";
				HashMap parameters = new HashMap();
				parameters.put("UserId",String.valueOf(loginUser.getId()));
				SCMGroups oSCMGroups = (SCMGroups) processor.excute(sServiceId,sMethodName, parameters);
				
				// 实现分组切换效果，通过将分组存入HashMap对象中实现
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
					oHashMap.put(String.valueOf(j), oSCMGroup);
				}
				if(nTempFlag != 0){
					oHashMap.put(String.valueOf(nTempFlag), oHashMap.get("4"));
					oHashMap.put("4", oTempSCMGroup);
				}

				boolean bMoreFlag=false;
				if(oHashMap.size() > 5){
					bMoreFlag=true;
				}
				
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
					<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="rights_management.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="groupFontColorSelect"><%=CMyString.transDisplay(sSubGroupName)%></a>
					<% if(i != oHashMap.size()-1){ %>
						<font class="seperateLine">|</font>
					<%}%>
				</span>
					<% }else{ %>
				<span>
					<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="rights_management.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="groupFontColor"><%=CMyString.transDisplay(sSubGroupName)%></a>
					<% if(i!=oHashMap.size()-1){ %>
					<font class="seperateLine">|</font>
					<%}%>
				</span>

			<%}}}
				if(bMoreFlag){
			%>
					<span class="userOperaterbox groupFontColor">
					<span class="userOperater-title" href="javascript:void(0)">更多&nbsp;<img src="../images/more_1.png" border=0 /></span>
					<div class="userOperater-list" id="moreGroups">
				<%
					for(int i=5;i<oHashMap.size();i++){
						oSCMGroup=(SCMGroup)oHashMap.get(String.valueOf(i));
						if(oSCMGroup==null)
							continue;
						sGroupName = oSCMGroup.getGroupName();
						sSubGroupName = CMyString.truncateStr(sGroupName,10);
				%>
						<a title="<%=CMyString.filterForHTMLValue(sGroupName)%>" href="rights_management.jsp?SCMGroupId=<%=oSCMGroup.getId() %>" class="option-quote"><%=CMyString.transDisplay(sSubGroupName)%></a>
				<%
					}
				%>
					</div>
					</span>	
				<%
					}
				%>
			</div>
			<div class="createGroupPicture">
				<img src="../images/createGroup.png" class="margin_5pxBottom" onclick="showCreate()" border="0"></img>
			</div>
		</div>
		<div class="clearFloat"></div>
		<%
			for(int i=0;i < oSCMGroups.size();i++){
				oSCMGroup = (SCMGroup)oSCMGroups.getAt(i);				
					if(oSCMGroup != null)
						break;
			}
			if(nSCMGroupId == 0){
		%>
		<iframe id="frame_account" name="frame_account" style="width:100%;height:100%" src="rights_management_list.jsp?SCMGroupId=<%=oSCMGroup.getId()%>&nPageSize=10&nPageIndex=1" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
		<% }else{ %>
		<iframe id="frame_account" name="frame_account" style="width:100%;height:100%" src="rights_management_list.jsp?SCMGroupId=<%=nSCMGroupId%>&nPageSize=10&nPageIndex=1" scrolling="no" frameborder="0" marginwidth="0" marginheight="0"></iframe>
		<%}%>
	</body>
</html>
