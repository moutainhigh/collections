<%--
/** Title:			jobworkertype_addedit.jsp
 *  Description:
 *		调度类型的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-06 00:17:46
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		CH@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see jobworkertype_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nJobWorkerTypeId = currRequestHelper.getInt("JobWorkerTypeId", 0);
	JobWorkerType currJobWorkerType = null;
	if(nJobWorkerTypeId > 0){
		currJobWorkerType = JobWorkerType.findById(nJobWorkerTypeId);
		if(currJobWorkerType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nJobWorkerTypeId+"]的调度类型失败！");
		}
	}else{//nJobWorkerTypeId==0 create a new group
		currJobWorkerType = JobWorkerType.createNewInstance();
	}
//5.权限校验
	if(nJobWorkerTypeId > 0){
		if(!currJobWorkerType.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "调度类型被用户["+currJobWorkerType.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("jobworkertype.label.addedit", "新建/修改调度类型")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!-- wenyh@2005-8-19 14:38:24 modifed,添加特殊字符校验.-->
function validName(_obj){
	var obj = _obj;
	var pattern_url = /^([a-z]|[A-Z]|[\u4E00-\u9FA5])+(\w|\.|[\u4E00-\u9FA5])*$/g;
	
	return pattern_url.test(obj.value);
}

function submitForm(){
	var frmData = document.frmData;
	if(!validName(frmData.OPNAME)){
		CTRSAction_alert("您输入的调度类型名称不符合规则，请重新输入！");
		return;
	}
	
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nJobWorkerTypeId%>,<%=JobWorkerType.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./jobworkertype_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("jobworkertype.label.addedit", "新建/修改调度类型")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" name="OPERID" value="<%=nJobWorkerTypeId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("jobworkertype.label.name", "名称")%>：</TD>
		<TD><INPUT name="OPNAME" type="text" elname="<%=LocaleServer.getString("jobworkertype.label.name", "名称")%>" size="30" pattern="string" not_null="1" max_len="50" value="<%=PageViewUtil.toHtmlValue(currJobWorkerType.getPropertyAsString("OPNAME"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("jobworkertype.label.desc", "描述")%>：</TD>
		<TD><INPUT name="OPDESC" type="text" size="30" pattern="string" max_len="200" elname="<%=LocaleServer.getString("jobworkertype.label.desc", "描述")%>" value="<%=PageViewUtil.toHtmlValue(currJobWorkerType.getPropertyAsString("OPDESC"))%>"></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("jobworkertype.label.param", "参数")%>：</TD>
		<TD><INPUT name="PARAM" type="text" size="30" pattern="string" elname="<%=LocaleServer.getString("jobworkertype.label.param", "参数")%>" max_len="200" value="<%=PageViewUtil.toHtmlValue(currJobWorkerType.getPropertyAsString("PARAM"))%>"></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("jobworkertype.label.bean", "Bean")%>：</TD>
		<TD><INPUT name="OPBEAN" type="text" size="30" elname="<%=LocaleServer.getString("jobworkertype.label.bean", "Bean")%>" pattern="string" not_null="1" max_len="200" value="<%=PageViewUtil.toHtmlValue(currJobWorkerType.getPropertyAsString("OPBEAN"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
		<TD align="left" height="10" colspan="2">&nbsp;<span class="font_red"><%=LocaleServer.getString("jobworkertype.message.bean", "Bean必须从com.trs.infra.util.job.BaseJob或com.trs.infra.util.job.BaseStatefulJob继承")%></span></TD>
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
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nJobWorkerTypeId%>,<%=JobWorkerType.OBJ_TYPE%>);window.close();");
			
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