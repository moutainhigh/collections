<%--
/** Title:			rightdef_addedit_dowith.jsp
 *  Description:
 *		处理权限的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 11:36:59
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see rightdef_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nRightDefId = currRequestHelper.getInt("RightDefId", 0);
	RightDef currRightDef = null;
	if(nRightDefId > 0){
		currRightDef = RightDef.findById(nRightDefId);
		if(currRightDef == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nRightDefId+"]的权限！");
		}
	}else{//nRightDefId==0 create a new group
		currRightDef = RightDef.createNewInstance();
	}
	
//5.权限校验


//6.业务代码
	try{
		currRightDef = (RightDef)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nRightDefId, RightDef.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存权限时因属性值不正确而失败中止！", ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IRightDefService currRightDefService = (IRightDefService)DreamFactory.createObjectById("IRightDefService");
	currRightDefService.save(currRightDef);
		**/
	currRightDef.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改权限:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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