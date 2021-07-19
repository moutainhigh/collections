<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
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
		<title>帐号管理-权限</title>
		<link rel="stylesheet" href="../css/public.css">
		<link rel="stylesheet" type="text/css" href="../css/count_common.css">
		<script src="../js/jquery-1.7.2.min.js"></script>
		<script src="../js/scm_common.js"></script>		
		<script src="../js/iframe_public.js"></script>
		<script src="rights_management_list.js"></script>		
		<script src="../js/getActualTop.js" type="text/javascript"></script>
	</head>	
<body>
<%
	// 获取参数
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",1);
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nPageIndex = currRequestHelper.getInt("PageIndex",1);
	int nPageStart = (nPageIndex-1)*nPageSize;
	int nPageEnd = nPageStart+nPageSize-1;
	String sSCMGroupId = String.valueOf(nSCMGroupId);
	SCMGroup oSCMGroup = SCMGroup.findById(nSCMGroupId);
	String sGroupName = oSCMGroup.getGroupName();

	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup" , sMethodName = "getAdminsOfGroup";

	Users oUsers = (Users) processor.excute(sServiceId,sMethodName);
	// 获取用户IDs
	String sUserIds = "";
	if(oUsers != null && oUsers.size() > 0){
		for(int i = 0;i < oUsers.size();i++){
			User oUser = (User)oUsers.getAt(i);
			sUserIds = sUserIds + "," + oUser.getId();
		}
		sUserIds = sUserIds.substring(1);
	}
	// 计算出帐号共有多少页
	int nPageCount = (oUsers.size()%nPageSize==0)?(oUsers.size()/nPageSize):(oUsers.size()/nPageSize+1);
%>
<script language="javascript">
<!--
	var nLoginUserId = <%=loginUser.getId()%>;
	var sGroupName = "<%=CMyString.filterForJs(sGroupName)%>";
//-->
</script>
	<div class="operateGroup">		
		<img src="../images/groupPic.png" border=0 class="margin_6PxBottom" />该组共有<%=oUsers.size()%>个维护人员&nbsp;&nbsp;
		<%if(oSCMGroup.getRemovable() == SCMGroup.REMOVEABLE){%>
		<img src="../images/pen.png" border=0 class="margin_4PxBottom"  />
		<span class="pointerHand" onclick="showModify(<%=nSCMGroupId %>,sGroupName)">修改分组</span>
		<img src="../images/wrong.png" border=0 class="margin_3PxBottom"  />
		<span class="pointerHand" onclick="deleteThisGroup(<%=sSCMGroupId %>)">删除分组</span>
		<%}%>
		<span class="pointerHand addusertogroup" onclick="addUserToGroup(<%=nSCMGroupId%>,'<%=sUserIds%>')" title="添加分组维护人员后，分组管理员可协助您管理该分组下的微博帐号,如发布微博、回复评论等">添加维护人员</span>
	</div>
<%
	if(oUsers == null || oUsers.size() == 0){
%>
		<div class="sabrosus">
			<span class="explainWords"><font size="4">
				该分组还没有分组维护人员，添加分组维护人员后，<br><br>分组管理员可协助您管理该分组下的微博帐号,<br><br>如发布微博、回复评论等。</font>
			</span>
		</div>
<%
	}else{
%>
	<div class="paddingLeftRight14Px">
		<table border="0" cellspacing="0" cellpadding="0" class="rightsInfoTable">
			<tr class="rightsInfoTableTh">
				<td>序号</td>
				<td>用户名</td>
				<td>当前状态</td>
				<td>真实姓名</td>
				<td>删除</td>
			</tr>
			<%
				for(int j = nPageStart;j <= nPageEnd && j < oUsers.size();j++){
					User oUser = (User)oUsers.getAt(j);
					if(oUser == null)
						continue;
			%>
			<tr>
				<td class="underLine"><%=j+1 %></td>
				<td class="underLine"><%=CMyString.transDisplay(oUser.getName())%></td>
				<td class="underLine">已开通</td>
				<td class="underLine"><%=CMyString.transDisplay(oUser.getTrueName())%></td>
				<td class="underLine">
					<img class="pointerHand" src="../images/delete_flag.png" onclick="removeUser(<%=oUser.getId()%>,<%=nSCMGroupId%>,<%=oUsers.size()%>)"/>
				</td>
			</tr>
			<%}%>
		</table>
	</div>
	<div class="sabrosus">
	<%if(nPageIndex==1){%>
		<span class='disabled'> 上一页 </span>
	<%}else{%>
	<a href="rights_management_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
	<%}
	int nStartNum = nPageIndex<=5?1:nPageIndex-5;
	int nEndNum = nPageIndex<=5?11:nPageIndex+5;
	for(int i=nStartNum;i<=nEndNum && i<=nPageCount;i++){
		if(i==nPageIndex){
	%>
			<span class="current"><%=i%></span>
	<%
		}else{
	%>
		<a href="rights_management_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
	<%}}
		if(nPageIndex==nPageCount || nPageCount==0){
	%>
		<span class='disabled'> 下一页 </span>
	<%
		}else{
	%>
	<a href="rights_management_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>
	<%}%>
	</div>
<%}%>
</body>
</html>