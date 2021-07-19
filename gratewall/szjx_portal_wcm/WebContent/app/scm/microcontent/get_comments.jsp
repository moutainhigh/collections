<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.scm.sdk.model.CommentWrapper" %>
<%@ page import="com.trs.scm.sdk.model.Comment" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.scm.sdk.model.MicroContentWrapper" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContents" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.sdk.util.CMyTimeTranslate" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	String sBackPage = currRequestHelper.getString("BackPage");
	if(CMyString.isEmpty(sBackPage)){
		sBackPage="single_microblog_list.jsp";
	}
	if(IS_DEBUG){
		System.out.println("****************get_comments.jsp**************");
		System.out.println("****************获取到的参数值开始**************");
		System.out.println("****************获取到的帐号id："+nAccountId+"**************");
		System.out.println("****************获取到的微博id："+sMicroContentId+"**************");
		System.out.println("****************获取到的参数值结束**************");
	}
	String sPlat =  Account.findById(nAccountId).getPlatform();
	MicroContent oOriginalMC = null;
	if(sPlat.equals(Platform.TecentPlatFormName)){
		oOriginalMC = (MicroContent) oProcessor.excute("wcm61_scmmicrocontent","findByMicroContentId");
		//去原创微博细览页面
		if(oOriginalMC.getRetweetedMicroContent() != null){
			sMicroContentId = oOriginalMC.getRetweetedMicroContent().getId();
		}
	}
	CommentWrapper oResult = null;
	if(!sPlat.equals(Platform.TecentPlatFormName) || (oOriginalMC != null && !oOriginalMC.isRetweeted())){
		try{
			oResult = (CommentWrapper) oProcessor.excute("wcm61_scmcomment", "queryCommentsByMCId");
		}catch(Exception e){
			String sErrorMsg = e.getMessage();
			if(sErrorMsg.indexOf("[ERR-") >= 0 && sErrorMsg.indexOf("<--") >= 0){
				sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
			}
		%>
		<div style="padding:3px 0px 3px 0px;">
			<div style="color:#777777;font-size:12px;padding-left:4px;">非常抱歉，获取评论出错，请您稍后刷新页面后重试。<br/><%=sErrorMsg%></div>
		</div>
		<%
			return;
		}
	}

	// 2.结果
	if((oResult != null && oResult.getTotalNumber()>0) || (oOriginalMC != null && oOriginalMC.isRetweeted() && oOriginalMC.getCommentCount() > 0)){
		String message="查看更多评论";
		if(oResult != null && oResult.getTotalNumber()>0){
			List<Comment> oComments  =(List<Comment>)oResult.getComments();
			int nTotalNum = oComments.size();
			if(IS_DEBUG){
				System.out.println("oResult.getTotalNumber():"+ oResult.getTotalNumber());
			}
			for(int j=1;j<=nTotalNum;j++){
				Comment tempComment = oComments.get(j-1);
				String sHeadPathPic = tempComment.getUser().getHead();
				String sHeadName = tempComment.getUser().getName();
				String lastTime = CMyTimeTranslate.getTimeString(tempComment.getCreateDate());
				if(IS_DEBUG){
					System.out.println("*********第"+j+"条评论*************");
					System.out.println("tempComment.getCommentId():"+tempComment.getCommentId());
					System.out.println("tempComment.getContent():"+tempComment.getContent());
					System.out.println("lastTime:"+lastTime);
					System.out.println("sHeadPathPic:"+sHeadPathPic);
				}
			%>
			<div style="padding:3px 0px 3px 0px;">
			<img src="<%=CMyString.filterForHTMLValue(sHeadPathPic)%>" style="width:30px;height:30px;float:left;" />
			<div style="float:left;margin-left:5px;margin-top:-4px;max-width:600px;word-wrap:break-word">
				<span style="color:#0078B6;font-size:12px;"><%=CMyString.transDisplay(sHeadName)%>:
				<span style="color:#777777;font-size:12px;"><%=CMyContentTranslate.microContentTranslate(tempComment.getContent(),sPlat)%>(<%=CMyString.transDisplay(lastTime)%>)</span>
				</span>
			</div>
			<div style="clear:both"></div>
			</div>
		<%
			}
			if(nTotalNum<5){
				message =  "已显示全部微博评论，去微博看看！";
			}
		}else{
			String sRetweetedUser = oOriginalMC.getUser().getName();
			int nReplyNum = oOriginalMC.getCommentCount();
			
		%>
			<div style="padding:3px 0px 3px 0px;">
			<div style="float:left;margin-left:5px;margin-top:-4px;max-width:600px;word-wrap:break-word">
				<span style="color:#0078B6;font-size:12px;"><%=CMyString.transDisplay(sRetweetedUser)%>
				<span style="color:#777777;font-size:12px;">转发后共引发</span><span style="color:#0078B6;font-size:12px;"><%=nReplyNum%></span><span style="color:#777777;font-size:12px;">次评论</span>
				</span>
			</div>
			<div style="clear:both"></div>
			</div>
		<%
		}	
		%>
		<div style="text-algin:center;font-size:12px;margin-bottom:-5px;">
			<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContentId)%>&BackPage=<%=sBackPage%>" target="frame_content"> <%=CMyString.transDisplay(message)%></a>
		</div>
	<%
	}else{
	%>
		<div style="color:#777777;font-size:12px;padding-left:4px;">暂时还没有人评论过这条微博，亲给个评论吧！<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContentId)%>&BackPage=<%=sBackPage%>" target="frame_content">去微博！</a>
		<div>
	<%
	}
	%>
	