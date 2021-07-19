<%--
/** Title:			status_addedit_dowith.jsp
 *  Description:
 *		处理文档状态的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 15:31:18
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see status_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nStatusId = currRequestHelper.getInt("StatusId", 0);
	Status currStatus = null;
	if(nStatusId > 0){
		currStatus = Status.findById(nStatusId);
		if(currStatus == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nStatusId+"]的文档状态！");
		}
	}else{//nStatusId==0 create a new group
		currStatus = Status.createNewInstance();
	}
	
//5.权限校验

//6.业务代码
	try{
		currStatus = (Status)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nStatusId, Status.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存文档状态时因属性值不正确而失败中止！", ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IStatusService currStatusService = (IStatusService)DreamFactory.createObjectById("IStatusService");
	currStatusService.save(currStatus);
		**/
	currStatus.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改文档状态:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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