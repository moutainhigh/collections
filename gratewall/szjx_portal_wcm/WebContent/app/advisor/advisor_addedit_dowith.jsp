<%--
/** Title:			advisor_addedit_dowith.jsp
 *  Description:
 *		处理Advisor的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-07-03 00:24:48
 *  Vesion:			1.0
 *  Last EditTime:	2011-07-03 / 2011-07-03
 *	Update Logs:
 *		TRS WCM 5.2@2011-07-03 产生此文件
 *
 *  Parameters:
 *		see advisor_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.advisor.Advisor" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nAdvisorId = currRequestHelper.getInt("AdvisorId", 0);
	Advisor currAdvisor = null;
	if(nAdvisorId > 0){
		currAdvisor = Advisor.findById(nAdvisorId);
		if(currAdvisor == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("advisor_addedit_dowith.jsp.notfindadvisorid", "没有找到指定ID为[{0}]的Advisor！"), new int[]{nAdvisorId}));
		}
	}else{//nAdvisorId==0 create a new group
		currAdvisor = Advisor.createNewInstance();
	}
	
//5.权限校验
	if(nAdvisorId > 0){
		if(!currAdvisor.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("advisor_addedit_dowith.jsp.advisorislockedbyuser", "Advisor被用户[{0}]锁定！"), new String[]{currAdvisor.getLockerUserName()}));
		}
	}

//6.业务代码
	try{
		currAdvisor = (Advisor)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nAdvisorId, Advisor.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("advisor_addedit_dowith.jsp.saveadvisorcauseattrivaluenotrightandfail", "保存Advisor时因属性值不正确而失败中止！"), ex);
	}
	currAdvisor.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="advisor_addedit_dowith.jsp.trswcmdowithaddeditadvisor">TRS WCM 处理添加修改Advisor</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener){
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	top.close();
}else{
	top.returnValue = true;
	top.close();
}

</SCRIPT>
</BODY>
</HTML>