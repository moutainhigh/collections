<%--
/** Title:			status_addedit.jsp
 *  Description:
 *		文档状态的添加修改页面
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
 *		see status_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
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
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nStatusId+"]的文档状态失败！");
		}
	}else{//nStatusId==0 create a new group
		currStatus = Status.createNewInstance();
	}
//5.权限校验
	if(nStatusId > 0){
		if(!currStatus.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "文档状态被用户["+currStatus.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	
//7.结束
	out.clear();

%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("document.label.addedit_of_status", "添加修改文档状态")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<Script>
//register Validator
TRSValidator.addValidators("status_name",  "CWCMStatusNameValidator",  "../js/validator/CWCMStatusNameValidator.js"); 
</Script>

<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var frmData = document.frmData;
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nStatusId%>,<%=Status.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./status_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("document.label.addedit_of_status", "添加/修改文档状态")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" Status="StatusId" value="<%=nStatusId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
		

		<TR>
		<TD width="100" align="left"><%=LocaleServer.getString("document.label.rightindex_of_status", "权限索引")%>：</TD>
		<TD><INPUT name="RIGHTINDEX" type="text" elname="<%=LocaleServer.getString("document.label.rightindex_of_status", "权限索引")%>" size="4" <%=(nStatusId>0&&currStatus.getRightIndex()==0)?"readonly style='background:silver'":"pattern=\"integer\""%> not_null="1" min_value="1" max_value="30000" value="<%=PageViewUtil.toHtmlValue(currStatus.getPropertyAsString("RIGHTINDEX"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.name_of_status", "名称")%>：</TD>
		<TD><INPUT name="SNAME" type="text" elname="<%=LocaleServer.getString("document.label.name_of_status", "名称")%>" size="30" pattern="status_name" not_null="1" max_len="50"  StatusId="<%=nStatusId%>" value="<%=PageViewUtil.toHtmlValue(currStatus.getPropertyAsString("SNAME"))%>"> <span class="font_red" ><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.desc_of_status", "说明")%>：</TD>
		<TD>
		<TEXTAREA name="SDESC" elname="<%=LocaleServer.getString("document.label.desc_of_status", "说明")%>" rows="3" cols="25" pattern="string" max_len="200"><%=PageViewUtil.toHtmlValue(currStatus.getPropertyAsString("SDESC"))%></TEXTAREA>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.show_of_status", "显示")%>：</TD>
		<TD><INPUT name="SDISP" type="text" elname="<%=LocaleServer.getString("document.label.show_of_status", "显示")%>" size="30" pattern="string" max_len="20" not_null="1" value="<%=PageViewUtil.toHtmlValue(currStatus.getPropertyAsString("SDISP"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.isused_of_status", "是否使用")%>：</TD>
		<TD>
		<select name="SUSED">
			<option value="1">是</option>
			<option value="0" <%=currStatus.isUsed()?"":"selected"%>>否</option>
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
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nStatusId%>,<%=Status.OBJ_TYPE%>);window.close();");
			
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