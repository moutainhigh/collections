<%--
/** Title:			advisor_addedit.jsp
 *  Description:
 *		Advisor的添加修改页面
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
 *		see advisor_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.advisor.Advisor" %>
<%@ page import="com.trs.components.wcm.advisor.Advisors" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	//1 获取参数
	int nAdvisorId = currRequestHelper.getInt("AdvisorId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	//2 参数有效性的校验
	if(nChannelId == 0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("advisor_addedit.jsp.parameterchannelidmustlargethanzaero", "参数ChannelId的值必须大于0！"));
	}
	Channel oChannel = Channel.findById(nChannelId);
	if(oChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("advisor_addedit.jsp.notfindidischnl", "无法找到ID为【{0}】的栏目！"), new int[]{nChannelId}));
	}
	Advisor currAdvisor = null;
	if(nAdvisorId > 0){
		currAdvisor = Advisor.findById(nAdvisorId);
		if(currAdvisor == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("advisor_addedit.jsp.getadvisoridfail", "获取ID为[{0}]的Advisor失败！"),new int[]{nAdvisorId}));
		}
	}else{//nAdvisorId==0 create a new group
		currAdvisor = Advisor.createNewInstance();
	}
	//3 权限校验
	boolean bHasAddRight = AuthServer.hasRight(loginUser, oChannel,WCMRightTypes.CHNL_EDIT);
	if(!bHasAddRight){
		throw new WCMException(CMyString.format(LocaleServer.getString("advisor_addedit.jsp.youhavenorightidchannlnewadvisor", "您没有权限在栏目【ID={0}】下新建修改顾问！"), new int[]{nChannelId}));
	}
	//4 业务代码

	//5 结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="advisor_addedit.jsp.trswcmaddeditadvisor">TRS WCM 添加修改Advisor</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></script>
<%=currRequestHelper.toTRSRequestParam()%>
<!--Form 数据有效性的校验 BEGIN-->
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/TRSBase.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSValidator_res_default.js"></SCRIPT>
<!--Form 数据有效性的校验 END-->
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSBitsValue.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMDialogHead.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/easyversion/lightbase.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function submitForm(){
	var frmData = document.getElementById("frmData");
	WCMAction.doPost(frmData, document.frmAction);
}

function resetForm(){
	var frm = document.getElementById("frmData");
	frm.reset();
}
function addFile(_sFilePath){
	if (_sFilePath == null || _sFilePath.length==0){
		Ext.Msg.alert("请先点击“上传”，将文件上传到服务器后再重试！");
		return;
	}
	$('APPENDIXFILENAME').value = _sFilePath;
}
function removeFile(){
	$('APPENDIXFILENAME').value = "";
}
</SCRIPT>
<style type="text/css">
	html,body{
		overflow:hidden;
	}
</style>
</HEAD>
<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD=POST
	ACTION="./advisor_addedit_dowith.jsp" style="margin-top:0"><%=currRequestHelper.toHTMLHidden()%>
<INPUT TYPE="hidden" NAME="ObjectXML" Value=""></FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<TD align="center" valign="top" class="tanchu_content_td">

		<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
		<INPUT TYPE="hidden" name="AdvisorId" value="<%=nAdvisorId%>">
		<INPUT TYPE="hidden" name="ChannelId" value="<%=nChannelId%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
			<TR>
				<TD class="tanchu_content">
					<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
						<TR>
							<TD width="60" align="left" WCMAnt:param="advisor_addedit.jsp.advisorname">顾问名称:</TD>
							<TD><INPUT name="ADVISORNAME" type="text" style="width:250px" WCMAnt:paramattr="elname:advisor_addedit.jsp.advisornameattr" elname="顾问名称属性" pattern="string" not_null="1" value="<%=PageViewUtil.toHtml(currAdvisor.getAdvisorName())%>"></TD>
						</TR>
						<TR>
							<TD width="60" align="left" WCMAnt:param="advisor_addedit.jsp.viewname">视图名称:</TD>
							<TD><INPUT name="VIEWNAME" type="text" style="width:250px" WCMAnt:paramattr="elname:advisor_addedit.jsp.viewnameattr" elname="视图名称属性" pattern="string"  value="<%=PageViewUtil.toHtml(currAdvisor.getViewName())%>"></TD>
						</TR>
						<TR>
							<TD height="40" align="left" colspan="2">
								<IFRAME name="frmUploadFile" id="frmUploadFile" height="24" width="100%" frameborder="0" vspace="0" src="../file/file_upload.jsp?SelfControl=1&ShowText=1&AllowExt=xls" scrolling="NO" noresize></IFRAME>
							</TD>
						</TR>
						<TR>
							<TD align="left" nowrap WCMAnt:param="advisor_addedit.jsp.relevancefile">关联文件:</TD>
							<TD align="left" colspan="2">
								<INPUT name="APPENDIXFILENAME" id="APPENDIXFILENAME" type="text" size="30" value="<%= PageViewUtil.toHtmlValue(currAdvisor.getAppendixFileName()) %>" readonly="true"><input type="button" name="removeFileBtn" id="removeFileBtn" WCMAnt:paramattr="value:advisor_addedit.jsp.claer" value="清除" onclick="removeFile()">
							</TD>
						</TR>
						<TR>
							<TD colspan="2"  height="10">&nbsp;</TD>
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
						oTRSButtons.addTRSButton("确定", "submitForm()");
						oTRSButtons.addTRSButton("重填", "resetForm()");
						oTRSButtons.addTRSButton("取消", "top.close();");
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