<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.scm.domain.ISCMAccountMgr" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp" %>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 获取参数
	String sPublishingIds = currRequestHelper.getString("PublishingAccountIds");
	String sContent = currRequestHelper.getString("Content");
	int ObjectId = currRequestHelper.getInt("SCMMicroContentId",0);
	String sPictureName = currRequestHelper.getString("Picture");

	//获取待发布平台的帐号
	HashMap paramters = new HashMap();
	paramters.put("ObjectIds",sPublishingIds);
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	Accounts oAccounts = null;
	try{
		oAccounts = (Accounts)oProcessor.excute("wcm61_scmaccount","findByIds",paramters);
	}catch(Exception e){
		String sErrorMsg = e.getMessage();
		if(sErrorMsg.indexOf("[ERR-") >= 0){
			sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
		}
		out.print(sErrorMsg);
		return;
	}
	
	SCMMicroContent oSCMMicroContent = SCMMicroContent.findById(ObjectId);
	User oPublishingUser = oSCMMicroContent.getCrUser();

	for (int i = 0, nSize = oAccounts.size(); i < nSize; i++) {
		Account oAccount = (Account) oAccounts.getAt(i);
		if (oAccount == null) continue;

		if (!SCMAuthServer.hasRightInAccount(oPublishingUser, oAccount, SCMAuthServer.ACCOUNT_USE)) {
			out.print("发布微博的用户"+ oPublishingUser.getName() +"已没有权限使用指定的帐号发布微博[Account=" + oAccount.getAccountName() + "]。您可重新编辑，取消该帐号的发布！");
			return;
		}
	}
	String sTempContent = CMyContentTranslate.t163UrlLengthTranslate(sContent);
	if (sTempContent.length() > 280) {
		out.print("输入的微博内容长度过长！最长140个字！");
		return;
	} else if (!oSCMMicroContent.isRetweeted() && CMyString.isEmpty(sContent)) {
		out.print("输入的微博内容为空！");
		return;
	}else if(oSCMMicroContent.isRetweeted() && CMyString.isEmpty(sContent)){
		sContent = "转发微博";
	}
    oSCMMicroContent.validCanEditAndLock(loginUser);

	try {
		oSCMMicroContent.setContent(sContent);
		oSCMMicroContent.setPublishAccountIds(sPublishingIds);
		oSCMMicroContent.setPicture(sPictureName);
		oSCMMicroContent.save(loginUser);
		out.print(1);
	} finally {
		if (oSCMMicroContent.isLocked()) {
			try {
				oSCMMicroContent.unlock();
			} catch (Exception e) {
				out.print("解除微博锁时失败");
				return;
			}
		}
	}

%>