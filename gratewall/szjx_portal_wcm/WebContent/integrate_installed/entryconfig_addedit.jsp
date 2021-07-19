<%--
/**
* modify by gfc@2005-12-22 10:37 
* for modify the checking of server-path 

* modify by gfc@2005-12-15 14:37 
* for adding a file called 'test_contaction' at the plugins application dir
*/
/** Title:			entryconfig_addedit.jsp
 *  Description:
 *		EntryConfig的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			GFC@TRSWCM
 *  Created:		2005-11-03 15:55:13
 *  Vesion:			1.0
 *  Last EditTime:	2005-11-03 / 2005-11-03
 *	Update Logs:
 *		GFC@TRSWCM@2005-11-03 产生此文件
 *
 *  Parameters:
 *		see entryconfig_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nEntryConfigId = currRequestHelper.getInt("EntryConfigId", 0);
	boolean bDirectlySettingDisable = currRequestHelper.getBoolean("DirectlySettingDisable", false);
	String sCertiString = currRequestHelper.getString("CertiString");
	EntryConfig currEntryConfig = null;
	if(nEntryConfigId > 0){
		currEntryConfig = EntryConfig.findById(nEntryConfigId);
		if(currEntryConfig == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nEntryConfigId+"]的EntryConfig失败！");
		}
	}else{//nEntryConfigId==0 create a new group
		currEntryConfig = EntryConfig.createNewInstance();
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 修改参数设置</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var sTargetUrl = document.getElementById("txtLinkPath").value;
	if (isEmpty(sTargetUrl)){
		CTRSAction_alert("请填写服务器地址!");	
		return;
	}
	
	if (sTargetUrl.length > 250){
		CTRSAction_alert("服务器地址长度不大于[250]个字符 (注:每个汉字相当于两个字符)");	
		return;
	}
	
	//服务器地址HTTP格式检验
	var ptnRootDomain = /^(http:\/\/|https:\/\/)./;
	var strRootDomain = sTargetUrl || "";
	strRootDomain = strRootDomain.toLowerCase();
	if(strRootDomain!="" && !strRootDomain.match(ptnRootDomain)) {
		CTRSAction_alert("<%=LocaleServer.getString("system.label.rootdomain", "站点HTTP")%>不完整，正确格式为：http(s)://[站点](:[端口])/(子目录)！");
		return;
	}
	
	//服务器地址连接检验	
	if (!testTargetUrl(sTargetUrl)){
		return;
	}
	
	var frmData = document.frmData;
	WCMAction.doPost(frmData, document.frmAction);	
}
function isEmpty(_str){
	if (_str == null || _str.length == 0)
		return true;
	for (var i=0; i<_str.length; i++){
		if (_str.charAt(i) != ' ')
			return false;
	}
	
	return true;
}
function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}

function testTargetUrl(_sTargetUrl, _bDisplayInfoIfValid){
	var sCertificationFileName = "certification.txt";
	var bResult = CTRSAction_validateURL(_sTargetUrl + "/" + sCertificationFileName, "<%=CMyString.filterForJs(sCertiString) %>");
	if(bResult){
		if(_bDisplayInfoIfValid)
			CTRSAction_alert("服务器地址["+_sTargetUrl+"]测试通过!", true);
	}else{
		bResult = CTRSAction_confirm("服务器地址["+_sTargetUrl+"]测试不通过! \n是否仍要设置该值?");
	}
	return bResult;
}

</SCRIPT>
</HEAD>

<BODY>
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./entryconfig_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("修改参数设置");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" name="EntryConfigId" value="<%=nEntryConfigId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
		
		<TR>
		<TD width="60" align="left">访问地址: </TD>
		<TD><INPUT id="txtLinkPath" name="LinkPath" type="text" style="width:280" elname="访问地址" pattern="string" not_null="1" value="<%=currEntryConfig.getLinkPath()%>" max_len="250"> <span class="font_red">*</span><span style="color:navy">(不超过250字符)</span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<%
			if (!bDirectlySettingDisable){
		%>
		<tr>
		<td colspan="2">
			<table align="left" width="80%" cellpadding="5" cellspacing="1" style="background-color:#f6f6f6;border:1px #cccccc solid">
				<tr>
					<td align="left">
					
					<INPUT id="isEnterDirectly" name="EnterDirectly" type="checkbox" IsBoolean="1" elname="EnterDirectly属性"> 跳过选件说明页面直接进入访问页面
						
					</td>
				</tr>
			</table>
		</td>
		</tr>
		<%}%>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD align="center" colspan="2" >
			<script src="../js/CTRSButton.js"></script>
			<script>
				//定义一个TYPE_ROMANTIC_BUTTON按钮
				var oTRSButtons		= new CTRSButtons();
				
				oTRSButtons.cellSpacing	= "0";
				oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;
	
				oTRSButtons.addTRSButton("确定", "submitForm()");
				oTRSButtons.addTRSButton("取消", "window.close();");
				
				oTRSButtons.draw();	
			</script>
		</TD>
		</TR>		
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</FORM>
</TD>
</TR>
</TABLE>
<script>
// 初始化显示 radio 和 checkbox
function init(){
	// 是否支持附加选项
	var isEnterDirectly = document.all("isEnterDirectly");
	if(isEnterDirectly)
		isEnterDirectly.checked = <%=currEntryConfig.isEnterDirectly()%>;
}
window.onload = init;
</script>

</BODY>
</HTML>