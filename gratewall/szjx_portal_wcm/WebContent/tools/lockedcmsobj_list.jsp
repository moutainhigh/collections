<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM5.2 模板语法检测
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see lockedcmsobj_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.content.CMSBaseObjs" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.presentation.util.CmsObjUnlockTools" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,"对不起,您无权访问该页面!");
	}
	
	int nObjType = currRequestHelper.getInt("ObjType",Document.OBJ_TYPE);
	CMSBaseObjs lockedobjs = CmsObjUnlockTools.listLockedObjs(loginUser,nObjType);
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 对象强制解锁工具::::::::::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/TRSBase.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSValidator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSValidator_res_default.js"></SCRIPT>
<script type="text/javascript" src="../js/CTRSHashtable.js"></script>
<script type="text/javascript" src="../js/CTRSRequestParam.js"></script>
<script type="text/javascript" src="../js/CTRSAction.js"></script>
<script type="text/javascript" src="../js/CTRSButton.js"></script>
<script>
	function forceUnlockeWithForm(){
		var oForm = document.getElementById("lockedobj")
		if(!TRSValidator.validate(oForm)) {
			return false;
		}

		var oAction = new CTRSAction("cmsobj_force_unlock.jsp");
		oAction.setParameter("ObjType",oForm.objType.value);
		oAction.setParameter("ObjId",oForm.objId.value);
		oAction.doXMLHttpAction();
	};
	
	function forceUnlocke(_nObjType,_nObjId){
		var oAction = new CTRSAction("cmsobj_force_unlock.jsp");
		oAction.setParameter("ObjType",_nObjType);
		oAction.setParameter("ObjId",_nObjId);
		oAction.doXMLHttpAction();
		
		oAction = new CTRSAction("lockedcmsobj_list.jsp");
		oAction.setParameter("ObjType",_nObjType);
		oAction.doAction();
	};
	
	function onObjTypeChanged(oSelect){
		var oAction = new CTRSAction("lockedcmsobj_list.jsp");
		oAction.setParameter("ObjType",parseInt(oSelect.value));
		oAction.doAction();
	};
</script>
</HEAD>
<BODY>
<TABLE align="center">
<TBODY>
	<form id="lockedobj">
	<TR>
		<TD>
		<TABLE><TBODY><TR>
		<TD align="right">对象类型：</TD>
		<TD>
			<select id="objType" name="objtype" onchange="onObjTypeChanged(this)">
				<option value="<%=Document.OBJ_TYPE%>" <%=(nObjType==Document.OBJ_TYPE)?"selected":""%>>文档</option>
				<option value="<%=Channel.OBJ_TYPE%>" <%=(nObjType==Channel.OBJ_TYPE)?"selected":""%>>频道</option>
				<option value="<%=WebSite.OBJ_TYPE%>" <%=(nObjType==WebSite.OBJ_TYPE)?"selected":""%>>站点</option>
				<option value="<%=Template.OBJ_TYPE%>" <%=(nObjType==Template.OBJ_TYPE)?"selected":""%>>模板</option>
			</select>
		</TD>
		<TD align="right">对象id：</TD>
		<TD>
			<input id="objId" name="objid" elname="对象id" type="text" size="15" pattern="integer" not_null="1" style="width:100px" class="inputtext"/>
		</TD>
		<TD align="right">
		<script>
			var oTRSButtons	= new CTRSButtons();
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType = TYPE_SMALLROMANTIC_BUTTON;
			oTRSButtons.addTRSButton("解  锁", "forceUnlockeWithForm()");
			oTRSButtons.draw();					
		</script>					
		</TD></TR></TBODY></TABLE></TD></TR></form>
	<TR>
		<TD align="left">当前被锁定的对象:</TD></TR>
	<TR>
		<TD align="right">
		<TABLE align="center" width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
			<TBODY>
				<TR bgcolor="#BEE2FF" class="list_th">
					<TD bgcolor="#BEE2FF" NOWRAP>对象标识</TD>
					<TD bgcolor="#BEE2FF" NOWRAP>当前锁定用户</TD>
					<TD bgcolor="#BEE2FF" NOWRAP>操作</TD></TR>
				<%
					CMSObj lockedobj = null;
					for(int i=0;i<lockedobjs.size();i++){
						lockedobj = (CMSObj)lockedobjs.getAt(i);
						if(lockedobj == null) continue;
				%>
				<TR class="list_tr">
					<TD bgcolor="#FFFFFF" nowrap><%=lockedobj%></TD>
					<TD bgcolor="#FFFFFF" nowrap><%=lockedobj.getLockerUser()%></TD>
					<TD bgcolor="#FFFFFF" nowrap><a href="javascript:forceUnlocke(<%=nObjType%>,<%=lockedobj.getId()%>);">解锁</a></TD></TR>
				<%}%>
				</TBODY></TABLE></TD></TR>
</TBODY>
</TABLE>
</BODY>
</HTML>