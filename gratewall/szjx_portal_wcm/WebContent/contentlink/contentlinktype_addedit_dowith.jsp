<%--
/** Title:			contentlinktype_addedit_dowith.jsp
 *  Description:
 *		处理ContentLinkType的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WenYehui
 *  Created:		2006-12-11 13:13:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-11 / 2006-12-11
 *	Update Logs:
 *		WenYehui@2006-12-11 产生此文件
 *
 *  Parameters:
 *		see contentlinktype_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nContentLinkTypeId = currRequestHelper.getInt("ContentLinkTypeId", 0);
	ContentLinkType currContentLinkType = null;
	if(nContentLinkTypeId > 0){
		currContentLinkType = ContentLinkType.findById(nContentLinkTypeId);
		if(currContentLinkType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nContentLinkTypeId+"]的ContentLinkType！");
		}
	}else{//nContentLinkTypeId==0 create a new group
		currContentLinkType = ContentLinkType.createNewInstance();
	}
	
//5.权限校验
	if(nContentLinkTypeId > 0){
		if(!currContentLinkType.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, "ContentLinkType被用户["+currContentLinkType.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	try{
		currContentLinkType = (ContentLinkType)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nContentLinkTypeId, ContentLinkType.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存ContentLinkType时因属性值不正确而失败中止！", ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IContentLinkTypeService currContentLinkTypeService = (IContentLinkTypeService)DreamFactory.createObjectById("IContentLinkTypeService");
	currContentLinkTypeService.save(currContentLinkType);
		**/
	currContentLinkType.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改ContentLinkType</TITLE>
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