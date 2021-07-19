<%--
/** Title:			test.jsp
 *  Description:
 *		处理文档来源的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-01 15:34:11
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		CH@2005-04-01 产生此文件
 *		wenyh@2006-05-19 修正返回处理,如果是从文档编辑页面提交的新增处理,则返回新增的来源id
 *
 *  Parameters:
 *		see test.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nSourceId = currRequestHelper.getInt("SourceId", 0);
	Source currSource = null;
	if(nSourceId > 0){
		currSource = Source.findById(nSourceId);
		if(currSource == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nSourceId+"]的文档来源！");
		}
	}else{//nSourceId==0 create a new group
		currSource = Source.createNewInstance();
	}

//5.权限校验

//6.业务代码
	try{
		currSource = (Source)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nSourceId, Source.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存文档来源时因属性值不正确而失败中止！", ex);
	}
	currSource.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改文档来源:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
<%
	if(request.getParameter("flag") != null){//for add from document_addedit.jsp
		out.clear();
		out.println(currSource.getId()+"#");
	}
%>