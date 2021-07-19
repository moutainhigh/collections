<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.Comment" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor();

	//获取帐号对象和回复内容
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	String sContent = currRequestHelper.getString("Content");
	String sCommentId = currRequestHelper.getString("CommentId");
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	boolean bIsReplyComment = currRequestHelper.getBoolean("isReplyComment",false);

	//将回复内容转换
	Account oAccount = Account.findById(nAccountId);
	sContent = CMyContentTranslate.platTranslateFaceText(sContent,oAccount.getPlatform());
	//将转换后的值给oParams调用服务
	HashMap oParams = new HashMap();
	oParams.put("AccountId",String.valueOf(nAccountId));
	oParams.put("Content",sContent);
	oParams.put("CommentId",sCommentId);
	oParams.put("MicroContentId",sMicroContentId);
	oParams.put("isReplyComment",Boolean.valueOf(bIsReplyComment));
	// 2 调用服务删除微博
	String sServiceId = "wcm61_scmcomment",sMethodName="createComment";
	Comment oResult = null;
	try{
		oResult = (Comment)oProcessor.excute(sServiceId,sMethodName,oParams);
	}catch(Exception e){
		String sErrorMsg = e.getMessage();
		if(sErrorMsg.indexOf("[ERR-") >= 0){
			sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
		}
		out.print(sErrorMsg);
		return;
	}
	if(oResult!=null){out.print(1);}else{out.print("保存评论内容失败！");}
%>