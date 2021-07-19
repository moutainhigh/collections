<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContents" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
// 设置参数
HashMap oHashMap = new HashMap();
int nSCMMicroContentId = currRequestHelper.getInt("SCMMicroContentId",0);
oHashMap.put("ContentId", String.valueOf(nSCMMicroContentId)); 
oHashMap.put("ContentType", String.valueOf(SCMMicroContent.OBJ_TYPE));
JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
// 其他属性： 
// CurrUser、SearchValue、OrderBy
String sUserName = loginUser.getName();
FlowDocs oFlowDocsResult = (FlowDocs)oProcessor.excute("wcm6_process","getFlowDocsOfContent",oHashMap);
if(oFlowDocsResult != null && oFlowDocsResult.size()!=0){
%>
<p><b>流转情况:</b></p>
<%
	for (int j = 0 ; j < oFlowDocsResult.size(); j++){
		FlowDoc oCurrFlowDoc = (FlowDoc) oFlowDocsResult.getAt(j); 
		if(oCurrFlowDoc == null){ continue; }
		//获取所需要的流转轨迹信息
		StringBuffer ReviewURLString = new StringBuffer();
		String sProcessTime = CMyDateTime.getStr(oCurrFlowDoc.getCrTime(), CMyDateTime.DEF_DATETIME_FORMAT_PRG);
		String sPostUser = oCurrFlowDoc.getPostUserName(); 
		String sDesc = CMyString.truncateStr(oCurrFlowDoc.getPostDesc(), 20);
		sDesc = CMyString.transDisplay(sDesc);

		//对流转轨迹做所需的处理
		if(IS_DEBUG){
			System.out.println("sProcessTime:"+sProcessTime);
			System.out.println("sPostUser:"+sPostUser);
			System.out.println("sDesc:"+sDesc);
		}
		ReviewURLString.append(sProcessTime).append("，").append(sPostUser).append("提交，操作：");
		if(j != oFlowDocsResult.size() - 1){
			ReviewURLString.append(ProcessConstants.getFlagDesc(oCurrFlowDoc.getFlag())).append("，审核意见：");
		}
		ReviewURLString.append(sDesc);
%>
	<span class="font12Px">
		<%=ReviewURLString%>
	</span><br />
<%
	}
}
%>