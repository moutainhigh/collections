<%--
/** Title:			entryconfig_addedit_dowith.jsp
 *  Description:
 *		处理EntryConfig的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			GFC@TRSWCM
 *  Created:		2005-11-03 15:55:13
 *  Vesion:			1.0
 *  Last EditTime:	2005-11-03 / 2005-11-03
 *	Update Logs:
 *		GFC@TRSWCM@2005-11-03 产生此文件
 *
 *  Parameters:
 *		see entryconfig_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nEntryConfigId = currRequestHelper.getInt("EntryConfigId", 0);
	EntryConfig currEntryConfig = null;
	if(nEntryConfigId > 0){
		currEntryConfig = EntryConfig.findById(nEntryConfigId);
		if(currEntryConfig == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nEntryConfigId+"]的EntryConfig！");
		}
	}else{//nEntryConfigId==0 create a new group
		currEntryConfig = EntryConfig.createNewInstance();
	}
	
//5.权限校验
	if(nEntryConfigId > 0){
		if(!currEntryConfig.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, "EntryConfig被用户["+currEntryConfig.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	try{
		currEntryConfig = (EntryConfig)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nEntryConfigId, EntryConfig.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存EntryConfig时因属性值不正确而失败中止！", ex);
	}

	currEntryConfig.save();

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改EntryConfig</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener){
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	window.close();
}else{
	window.returnValue = true;
	window.close();
}

</SCRIPT>
</BODY>
</HTML>