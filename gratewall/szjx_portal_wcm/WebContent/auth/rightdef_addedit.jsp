<%--
/** Title:			rightdef_addedit.jsp
 *  Description:
 *		权限的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 11:36:59
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *	
 *	History				Who				What
 *	2006-01-13			wenyh			系统预设的权限定义从240增加到241
 *
 *  Parameters:
 *		see rightdef_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
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
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nRightDefId+"]的权限失败！");
		}
	}else{//nRightDefId==0 create a new group
		currRightDef = RightDef.createNewInstance();
	}
//5.权限校验
	if(nRightDefId>0){
		if(!currRightDef.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "权限["+currRightDef.getName()+"]["+currRightDef.getId()+"]被用户［"+currRightDef.getLockerUserName()+"］锁定！您不能修改！");
		}
	}
//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>
TRS WCM 5.2 <%=LocaleServer.getString("auth.label.addedit", "添加修改权限")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
TRSValidator.addValidators("rightdef_name",  "CWCMRightDefNameValidator", "../js/CWCMRightDefNameValidator.js");
TRSValidator.addValidators("rightdef_index",  "CWCMRightDefIndexValidator", "../js/CWCMRightDefIndexValidator.js");
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!-- wenyh@2005-8-19 14:38:24 modifed,添加特殊字符校验.-->
function validName(_obj){
	var obj = _obj;
	var pattern_url = /^([a-z]|[A-Z]|[\u4E00-\u9FA5])+(\w|\.|[\u4E00-\u9FA5])*$/g;
	
	return pattern_url.test(obj.value);
}

function submitForm(){
	var frmData = document.frmData;
	if(!validName(frmData.RIGHTNAME)){
		CTRSAction_alert("您输入的权限名称不符合规则，请重新输入！");
		return;
	}
	
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}

function setObjType() {
	var frmData = document.frmData;
	var nObjType = frmData.ObjType.value;
	frmData.RIGHTINDEX.ObjType = nObjType;
	frmData.RIGHTNAME.ObjType = nObjType;
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nRightDefId%>,<%=RightDef.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./rightdef_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("auth.label.addedit", "添加/修改权限")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" name="RightDefId" value="<%=nRightDefId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
		
		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("auth.label.objtype", "对象类型")%>：</TD>
		<TD>
	    <select name="ObjType" onChange="setObjType()">
			<option value="<%=WebSite.OBJ_TYPE%>"  <%=(currRightDef.getObjType()==WebSite.OBJ_TYPE)?"selected":""%>><%=WCMTypes.getObjName(WebSite.OBJ_TYPE, true)%></option>
			<option value="<%=Channel.OBJ_TYPE%>" <%=(currRightDef.getObjType()==Channel.OBJ_TYPE)?"selected":""%>><%=WCMTypes.getObjName(Channel.OBJ_TYPE, true)%></option>
			<option value="<%=Document.OBJ_TYPE%>" <%=(currRightDef.getObjType()==Document.OBJ_TYPE)?"selected":""%>><%=WCMTypes.getObjName(Document.OBJ_TYPE, true)%></option>
			<option value="1" <%=(currRightDef.getObjType()==1)?"selected":""%>>系统属性</option>
		</select>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("auth.label.index", "权限索引")%>：</TD>
		<TD><INPUT name="RIGHTINDEX" type="text" elname="<%=LocaleServer.getString("auth.label.index", "权限索引")%>" size="4" pattern="rightdef_index" not_null="1" min_value="0" max_value="255" ObjType="" RightDefId="<%=nRightDefId%>" value="<%=PageViewUtil.toHtmlValue(currRightDef.getPropertyAsString("RIGHTINDEX"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("auth.label.name", "权限名称")%>：</TD>
		<TD><INPUT name="RIGHTNAME" type="text" elname="<%=LocaleServer.getString("auth.label.name", "权限名称")%>" size="30" pattern="rightdef_name" not_null="1" max_len="50" ObjType="" RightDefId="<%=nRightDefId%>" value="<%=PageViewUtil.toHtmlValue(currRightDef.getPropertyAsString("RIGHTNAME"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("auth.label.desc", "权限说明")%>：</TD>
		<TD>
		<TEXTAREA name="RIGHTDESC" rows="3" cols="25" elname="<%=LocaleServer.getString("auth.label.desc", "权限说明")%>" pattern="string" max_len="200"><%=PageViewUtil.toHtmlValue(currRightDef.getPropertyAsString("RIGHTDESC"))%></TEXTAREA>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("auth.label.sysdefined", "系统定义权限")%>：</TD>
		<TD>
		<select name="SYSDEFINED" <%=(nRightDefId == 0 || nRightDefId > 241)?"":"disabled"%>>
			<option value="1">是</option>
			<option value="0" <%=currRightDef.isSysDefined()?"":"selected"%>>否</option>
		</select>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		</TABLE>
	</TD>
	</TR>
	<TR>
	<TD align="center">
		<script src="../js/CTRSButton.js"></script>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons		= new CTRSButtons();
			
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "submitForm()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nRightDefId%>,<%=RightDef.OBJ_TYPE%>);window.close();");
			
			oTRSButtons.draw();	
		</script>
	</TD>
	</TR>
	</TABLE>
</FORM>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>
<SCRIPT LANGUAGE="JavaScript">
<!--
setObjType();
//-->
</SCRIPT>