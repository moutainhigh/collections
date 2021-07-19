<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	int nSCMMicroContentId = currRequestHelper.getInt("SCMMicroContentId",0);
	if(IS_DEBUG){
		System.out.println("nAccountId:"+nAccountId);
		System.out.println("nSCMMicroContentId:"+nSCMMicroContentId);
	}
	// 2 调用服务补发微博
	String sServiceId = "wcm61_scmmicrocontent",sMethodName="repost";
	Boolean oResult = false;
	try{
		oResult = (Boolean)oProcessor.excute(sServiceId,sMethodName);
	}catch(Exception e){
		String sErrorMsg = e.getMessage();
		sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("\n<--"));
		out.clear();
%>
<%=sErrorMsg%>
<%
		return;
	}
	// 3 判断是否补发成功
	int nReposted = 0;
	if(oResult.booleanValue()==true){nReposted = 1;}
	
	// 4 获取转评数据
	oProcessor.reset();
	HashMap oParameters = new HashMap();
	oParameters.put("SCMMicroContentId",String.valueOf(nSCMMicroContentId));
	Map<Integer, MicroContent> oAccountMicroContent = (HashMap<Integer, MicroContent>) oProcessor.excute("wcm61_scmmicrocontent", "findPostedMicroContents",oParameters);
	MicroContent oMicroContent = oAccountMicroContent.get(nAccountId);
	if(oMicroContent == null){
		nReposted = 0;
		out.print("请稍等几分钟再试！");
		return;
	}
	int nRepostCount = oMicroContent.getRepostCount();
	int nCommentCount = oMicroContent.getCommentCount();
	out.clear();
%>
[{Reposted:<%=nReposted%>,RepostCount:<%=nRepostCount%>,CommentCount:<%=nCommentCount%>}]