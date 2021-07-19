<%--
/** Title:			wizard_step_4.jsp
 *  Description:
 *		置标向导 第四步
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-30 15:52:00
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-30 / 2005-04-30
 *	Update Logs:
 *		WSW@2005-5-16 产生此文件
 *
 *  Parameters:
 *		see wizard_step_4.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.List" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.publish.template.CMyTemplateWizard" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nType = currRequestHelper.getInt("TagType", 10);
	String sObjXML = currRequestHelper.getString("ObjectXML");
	//System.out.println(nType);
	//System.out.println(sObjXML);

//5.权限校验

//6.业务代码
	String sTemplateCode = CMyTemplateWizard.toTemplateCode(nType, sObjXML);
	//System.out.println(sTemplateCode);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="template_wizard_step3.jsp.title">TRS WCM 置标向导</TITLE>
<BASE TARGET="_self">
<style type="text/css">
	body{
		padding: 0px;
		margin: 0px;
		font-size: 12px;
		background: #ffffff
	}
</style>
</HEAD>

<body style="background: transparent">
<div style="width: 100%; background: #fefefe; padding-top: 10px; height: 100%;">
	<div style="width: 100%; height: 100%; text-align: center;">
		<TEXTAREA id="txtCode" NAME="TemplateCode" class="txtCode" style="border: 1px solid silver; display: none;overflow-y:auto;"><%=sTemplateCode%></TEXTAREA>
	</div>
</div>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function doCopy(){
	try{
		clipboardData.setData('Text',$("txtCode").value);
		return true;
	}catch(ex){
		Ext.Msg.alert(wcm.LANG.TEMPLATE_ALERT11||'您的浏览器不支持自动复制操作，请手工拷贝！');
		$('txtCode').focus();
		$('txtCode').select();
		return false;
	}
}
function getText(){
	return $("txtCode").value;
}
function getPageParams(){
	return document.location.search.substring(1);
	//return 'TagType=<%=nType%>';
}
</SCRIPT>
<script language="javascript">
<!--
	if(window.top && window.top.$winzard) {
		window.top.$winzard.doAfterSteped();
	}
	/*var nBoxHeight = Element.getDimensions(document.body)["height"];
	var nBoxWidth  = Element.getDimensions(document.body)["width"];
	$('txtCode').style.height = (nBoxHeight - 20) + 'px';
	$('txtCode').style.width  = (nBoxWidth - 50) + 'px';
	Element.show('txtCode');*/
	$('txtCode').style.height = (260 - 20) + 'px';
	$('txtCode').style.width  = '99%';
	$("txtCode").style.display = "block";
//-->
</script>
</BODY>
</HTML>