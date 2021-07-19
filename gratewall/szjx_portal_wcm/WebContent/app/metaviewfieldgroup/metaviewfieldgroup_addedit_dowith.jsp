<%--
/** Title:			metaviewfieldgroup_addedit_dowith.jsp
 *  Description:
 *		处理MetaViewFieldGroup的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-06-11 03:19:07
 *  Vesion:			1.0
 *  Last EditTime:	2011-06-11 / 2011-06-11
 *	Update Logs:
 *		TRS WCM 5.2@2011-06-11 产生此文件
 *
 *  Parameters:
 *		see metaviewfieldgroup_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nMetaViewFieldGroupId = currRequestHelper.getInt("MetaViewFieldGroupId", 0);
	MetaViewFieldGroup currMetaViewFieldGroup = null;
	if(nMetaViewFieldGroupId > 0){
		currMetaViewFieldGroup = MetaViewFieldGroup.findById(nMetaViewFieldGroupId);
		if(currMetaViewFieldGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("metaviewfieldgroup_addedit_dowith.jsp.fail2get_MetaViewFieldGroup", "没有找到指定ID为[{0}]的MetaViewFieldGroup!"), new int[]{nMetaViewFieldGroupId}));
		}
	}else{//nMetaViewFieldGroupId==0 create a new group
		currMetaViewFieldGroup = MetaViewFieldGroup.createNewInstance();
	}
	
//5.权限校验
	 if (!loginUser.isAdministrator()) {
		throw new WCMException(LocaleServer.getString("metaviewfieldgroup_addedit_dowith.jsp.rightValid","您不是管理员，无权进行当前操作！"));
	}
	if(nMetaViewFieldGroupId > 0){
		if(!currMetaViewFieldGroup.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED, CMyString.format(LocaleServer.getString("metaviewfieldgroup_addedit_dowith.jsp.syn_by_user", "MetaViewFieldGroup被用户[{0}]锁定!"), new String[]{currMetaViewFieldGroup.getLockerUserName()}));
		}
	}

//6.业务代码
	try{
		currMetaViewFieldGroup = (MetaViewFieldGroup)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nMetaViewFieldGroupId, MetaViewFieldGroup.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("metaviewfieldgroup_addedit_dowith.jsp.label.fail2save", "保存MetaViewFieldGroup时因属性值不正确而失败中止！"), ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IMetaViewFieldGroupService currMetaViewFieldGroupService = (IMetaViewFieldGroupService)DreamFactory.createObjectById("IMetaViewFieldGroupService");
	currMetaViewFieldGroupService.save(currMetaViewFieldGroup);
		**/
	if(nMetaViewFieldGroupId == 0){
		currMetaViewFieldGroup.setOrder(-1);
	}
	currMetaViewFieldGroup.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="metaviewfieldgroup_addedit_dowith.jsp.title">TRS WCM 处理添加修改MetaViewFieldGroup</TITLE>
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