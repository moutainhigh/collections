<%--
/** Title:			Filter_addedit_dowith.jsp
 *  Description:
 *		处理Filter的添加修改页面
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
 *		see Filter_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.filter.Filter" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nFilterId = currRequestHelper.getInt("FilterId", 0);
	Filter currFilter = null;
	if(nFilterId > 0){
		currFilter = Filter.findById(nFilterId);
		if(currFilter == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("filter_addedit_dowith.jsp.notfindfilterid", "没有找到指定ID为[{0}]的筛选器！"), new int[]{nFilterId}));
		}
	}else{//nFilterId==0 create a new group
		currFilter = Filter.createNewInstance();
	}
	
//5.权限校验
	if(nFilterId > 0){
		if(!currFilter.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("filter_addedit_dowith.jsp.filterislockedbyuser", "筛选器被用户[{0}]锁定！"), new String[]{currFilter.getLockerUserName()}));
		}
	}

//6.业务代码
	try{
		currFilter = (Filter)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nFilterId, Filter.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("filter_addedit_dowith.jsp.savefiltercauseattrvaluenotrightandstop", "保存Filter时因属性值不正确而失败中止！"), ex);
	}
	currFilter.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="filter_addedit_dowith.jsp.trswcmdowithaddeditfilter">TRS WCM 处理添加修改筛选器</TITLE>
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