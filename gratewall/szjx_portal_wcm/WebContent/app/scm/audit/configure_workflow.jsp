<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>

<%
	out.clear();
%>
<!DOCTYPE html>
<html>
<head>
<title>审核管理-工作流配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="configure_workflow.css">
</head>
	<body class="bodyColor">
	<%
		//非SCM管理员，提示后返回
		if(!SCMAuthServer.isAdminOfSCM(loginUser)){
	%>
			<div  class="sabrosus">
			<font size="4">
					<b>非常抱歉，您不是SCM管理员，不能对工作流进行配置！</b>
			</font>
			</div>
	<%
			return;
		}
		
		// 1获取参数
		int nPageSize = currRequestHelper.getInt("PageSize",0);
		int nPageIndex = currRequestHelper.getInt("PageIndex",0);

		// 2设置参数
		JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
		int nUserId = loginUser.getId();
		HashMap oHashMap = new HashMap();
		oHashMap.put("UserId", String.valueOf(nUserId));

		// 3 调用服务,获得默认的工作流和系统配置的SCM工作流
		String sServiceIdOfWorkFlow = "wcm61_scmworkflow";
		String sMethodNameOfDefaultWorkFlow = "queryDefaultWorkFlow";
		String sMethodNameOfWorkFlow = "queryWorkFlowList";
		Flow oDefaultFlow = null;
		String sErrorMsgOfDefault = "";
		try{	
			oDefaultFlow = (Flow) oProcessor.excute(sServiceIdOfWorkFlow, sMethodNameOfDefaultWorkFlow);
		}catch(WCMException e){
			sErrorMsgOfDefault = "系统配置的默认审核工作流有误：未找到配置的默认工作流！请您修改默认工作流配置！";
		}
		Flows oWorkFlowList = null;
		try{
			oWorkFlowList = (Flows) oProcessor.excute(sServiceIdOfWorkFlow, sMethodNameOfWorkFlow);
		}catch(Exception e){
			sErrorMsgOfDefault = "系统配置的审核工作流有误!</br>请联系WCM系统管理员确认WCM管理台“系统配置”->“用户新增配置”中的“WORKFLOWS_FOR_SCM”配置项内容正确。该配置项内容为:用于SCM审核的工作流ID序列；多个工作流ID之间以英文逗号分隔符隔开。";
			%>
			<div class="configHeader">
			<div class="configHeaderContainer">
				<div style="margin:0 auto; padding-top:6px; padding-bottom:12px;">
					<b><%=sErrorMsgOfDefault%></b>
				</div>
			</div>
		</div>
			<%
				return;
		}
		if(oWorkFlowList== null || oWorkFlowList.size() == 0){
			%>			
		<div class="configHeader">
			<div class="configHeaderContainer">
				<div style="margin:0 auto;">
					<b>非常抱歉，系统还未配置SCM使用的工作流。您可参照用户手册配置SCM审核工作流后，在本页面进行审核管理。</b>
				</div>
			</div>
		</div>
			<%
				return;
		}
		int nInWorkList = oDefaultFlow == null ? 0 : oWorkFlowList.indexOf(oDefaultFlow.getId());
		if(nInWorkList < 0){
			sErrorMsgOfDefault = "系统配置的默认审核工作流有误：默认工作流不在SCM审核工作流列表内！您可参照用户手册检查SCM审核工作流配置。现已屏蔽错误设置！";
			oDefaultFlow = null;
		}
		//5调用服务，获取未定制工作流的分组及定制工作流的分组列表
		String sMethodNameofUnCustomizedGroups = "queryUnCustomizedGroups";
		String sMethodNameofCustomizedGroups = "queryCustomizedGroups";
		oProcessor.reset();
		SCMGroups oUnCustomizedGroups = (SCMGroups) oProcessor.excute(sServiceIdOfWorkFlow, sMethodNameofUnCustomizedGroups);
		SCMGroups oCustomizedGroups = (SCMGroups) oProcessor.excute(sServiceIdOfWorkFlow, sMethodNameofCustomizedGroups);
		int nTotalNum = oCustomizedGroups == null || oCustomizedGroups.size()==0 ? 0 : oCustomizedGroups.size();
		int nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
		if(nPageIndex > nPageCount && nPageCount != 0){
			nPageIndex = nPageCount;
		}
		int nStart = (nPageIndex - 1) * nPageSize;
		int nEnd = nTotalNum == 0 ? 0 : ((nPageCount == nPageIndex) ? nTotalNum : nPageIndex * nPageSize);
		%>
		<!--头部系统工作流设置信息-->
		<div class="configHeader">
			<div class="configHeaderContainer">
				<div class="defaultFont">默认工作流：</div>
				<select class="groupSelect workflowSelect" id="workflowList1" onchange="setWorkFlow(1, $(this).val(),0,<%=nPageIndex%>,<%=nPageSize%>)">
						<%
							for(int i = 0; i < oWorkFlowList.size(); i++){
								Flow oTemp = (Flow) oWorkFlowList.getAt(i);
								if(oTemp == null){ continue;}
						%>
							<option value="<%=oTemp.getId()%>" 
							<%if(oDefaultFlow != null && oTemp.getId() == oDefaultFlow.getId()){ %> selected="selected" <% }%> ><%=CMyString.transDisplay(oTemp.getName())%></option>
						<%
							}
						%>
						<option value="0" <%if(oDefaultFlow == null){ %> selected="selected" <% }%>>不设置审核</option>
				</select>
				<%
				if(!CMyString.isEmpty(sErrorMsgOfDefault)){
				%>
				<div style="font-size:12px; color:#AD251A;padding-top:6px; padding-bottom:6px;"><%=sErrorMsgOfDefault%></br></div>
				<%}%>
				<div class="configHeaderFont" color="#AD251A">注：如果系统中配置了默认工作流，未定制审核工作流的分组将全部使用系统的默认配置进行发布微博审核流程。</br>
				</div>
				<div class="clearFloat"></div>
			</div>
		</div>
	<!--给未配置工作流的分组添加配置-->
	<div class="configContent">
		<%
			if(oUnCustomizedGroups != null && oUnCustomizedGroups.size() != 0){
		%>
		<div class="configContentOut" style="display: block">
				<!--未定制的分组列表-->
				<select class="groupSelect" id="groupList1">
				<%
					for(int j = 0; j < oUnCustomizedGroups.size(); j++){
						SCMGroup oCurrGroup = (SCMGroup)oUnCustomizedGroups.getAt(j);
				%>
					<option value="<%=oCurrGroup.getId()%>" <%if(j ==0) {%> selected="selected"<%}%>><%=CMyString.transDisplay(oCurrGroup.getGroupName())%></option>
				<%
					}//END FOR LOOP
				%>
				</select>
				<!--工作流列表-->
				<select class="groupSelect" id="setWorkflowList2">
				<%
					for(int i = 0; i < oWorkFlowList.size(); i++){
						Flow oTemp = (Flow) oWorkFlowList.getAt(i);
						if(oTemp == null) continue;
				%>
					<option value="<%=oTemp.getId()%>" 
					<%if(oDefaultFlow != null && oTemp.getId() == oDefaultFlow.getId()){ %> selected="selected" <% }%> ><%=CMyString.transDisplay(oTemp.getName())%></option>
				<%
					}
				%>
				<option value="-1" <%if(oDefaultFlow == null){ %> selected="selected" <% }%>>不设置审核</option>
				</select>
				<div class="configContentImg" onclick="setWorkFlow(2, $('#setWorkflowList2').val(), $('#groupList1').val(),<%=nPageIndex%>,<%=nPageSize%>)" title="为分组定制审核工作流，使分组使用指定流程审核微博。">添加定制</div>
			</div>
			<%
			}else{
			%>
				<font>全部分组均已定制了审核工作流。</font>
			<%
			}
			%>
		<div class="clearFloat"></div>
</div>
	<!--定制工作流的分组列表-->
	<%
	for(int i = nStart; i < nEnd; i++){
			if(oCustomizedGroups.getAt(i) == null){
				continue;
			}
			SCMGroup oCurrGroup = (SCMGroup) oCustomizedGroups.getAt(i);
			int nCurrGroupId = oCurrGroup.getId();
			int nCurrWorkFlowId = oCurrGroup.getWorkFlow();
	%>
			<div class="configItem">
				<div class="floatLeft" style="width:180px !important"><%=CMyString.transDisplay(oCurrGroup.getGroupName())%></div>
				<select class="groupSelect workflowSelect" id="workflowList<%=i+2%>" onchange="setWorkFlow1(2, this,<%=nCurrGroupId%>,<%=nPageIndex%>,<%=nPageSize%>,<%=nCurrWorkFlowId%>);">
	<%
					for( int j = 0; j < oWorkFlowList.size(); j++){
							Flow oTemp = (Flow) oWorkFlowList.getAt(j);
							if(oTemp == null){continue;}
	%>
							<option value="<%=oTemp.getId()%>" <%if(oWorkFlowList.getAt(j).getId() == nCurrWorkFlowId){%> selected="selected" <%}%>><%=CMyString.transDisplay(oTemp.getName())%></option>
				<%
					}
				%>
					<!--WCM-995-->
					<option value="-1" <%if( nCurrWorkFlowId < 0){%> selected="selected" <%}%>>不设置审核</option>
				</select>
				<%
					//WCM-995
					String sWarningMsg = null;
					if(nCurrWorkFlowId == SCMGroup.SCMWORKFLOWCONFIG_REMOVED){
						sWarningMsg = "分组定制的工作流已被管理员从系统配置中移除，请您重新定制！";
					}else if(nCurrWorkFlowId == SCMGroup.WORKFLOW_REMOVED){
						sWarningMsg = "分组定制的工作流已被管理员删除，请您重新定制！";
					}
					if(!CMyString.isEmpty(sWarningMsg)){
					%>
						<span style="vertical-align:middle;font-size:12px;color:#ac251d;"><%=sWarningMsg%></span>
					<%}%>
				<span class="delete" onclick="setWorkFlow(2, 0, <%=nCurrGroupId%>,<%=nPageIndex%>,<%=nPageSize%>)">取消定制</span>
				<div class="clearFloat"></div>
			</div>
	<%	
	}
	//分页
		if(nPageCount > 1){	
		%>
			<div class="sabrosus">
				<%if(nPageIndex==1){%>
					<span class='disabled'> 上一页 </span>
				<%}else{%>
					<a href="configure_workflow.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
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
					<a href="configure_workflow.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
				<%
					}//end if
				}//end for
				%>
				<%
				if(nPageIndex==nPageCount){
				%>
					<span class='disabled'> 下一页 </span>
				<%
				}else{
				%>
				<a href="configure_workflow.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>
				<%
					}
				%>
			</div>
		<%
		}
	%>
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="configure_workflow.js"></script>

<script>
<!--
$(function(){
	$(".groupSelect").sSelect();
});
//-->
</script>

	</body>
</html>