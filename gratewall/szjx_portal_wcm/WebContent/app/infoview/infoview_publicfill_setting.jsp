<%--
/** Title:			infoview_fieldext.jsp
 *  Description:
 *		WCM5.2 自定义表单字段扩展属性维护界面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			ly
 *  Created:		2007.09.19
 *  Vesion:			1.0
 *
 *  Parameters:
 *		see infoview_fieldext.xml
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.InfoViewMgr" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroup" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroups" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.service.IChannelService" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("nfoview_publicfill_setting.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView currInfoView = InfoView.findById(nInfoViewId);
	if (currInfoView == null){
		throw new WCMException(LocaleServer.getString("nfoview_publicfill_setting.obj.not.found","无法找到InfoView！"));
	}
	String strGroupName = CMyString.showNull(currRequestHelper.getString("IVGroupName"));
	InfoViewGroups oIVGroups = InfoViewGroups.findByName(strGroupName, nInfoViewId);
	if(oIVGroups.size()<=0){
		throw new WCMException(LocaleServer.getString("nfoview_publicfill_setting.wrongNode","当前组节点非Section节点！"));
	}
	InfoViewGroup oIVGroup = (InfoViewGroup)oIVGroups.getAt(0);
	int nGroupId = oIVGroup.getId();
%>
<html>
<HEAD>
<TITLE WCMAnt:param="infoview_publicfill_setting.jsp.title">维护数据区域的发布属性</TITLE>
<!--my import-->
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script>
function Ok(){
	var sXml = getPostData($('DataForm'));
	$('ObjectForm').ObjectXML.value = sXml;	
	new ajaxRequest({
			url : './infoview_save_group.jsp',
			method : 'post',
			parameters : $toQueryStr({
				ObjectXML : $('ObjectForm').ObjectXML.value,
				InfoViewId : $('InfoViewId').value,
				IVGroupId : $('IVGroupId').value,
				CaseSensitive : $('IVGroupId').value
			}),
			onSuccess : function(_trans, _json){
				parent.Ok({
					GroupName : "<%=CMyString.filterForJs(strGroupName)%>",
					PublicFillable : $('PublicFill').checked
				});
			},
			onFailure : function(_trans, _json){
				if(confirm(String.format("保存出错：{0}\n是否需要查看详细信息",$v(_json,'fault.message')))){
					alert($v(_json,'fault.detail'));
				}
			}
	});
}
function getPostData(frmData){
	var inputElements = frmData.getElementsByTagName('input');
	var result = ['<WCMOBJ><PROPERTIES>'];
	for (var i = 0; i < inputElements.length; i++){
		var element = inputElements[i];
		if(element.type =='checkbox')
			element.value = element.checked ? '1' : '0';
		var name = element.name.toUpperCase();
		result.push("<", name, "><![CDATA[", element.value, "]]></", name, ">");
	}
	result.push('</PROPERTIES></WCMOBJ>');
	return result.join("");
}
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}
</script>
<style>
	body{
		font-size:14px;
		line-height:22px;
	}
	fieldset{
		padding:5px 0 5px 5px;
		margin-bottom:5px;
	}
	legend{
		font-weight:bold;
	}
	.inputtext{
		height:20px;
		font-size:14px;
		line-height:18px;
		border:1px solid gray;
	}
	.label{
		display:inline;
		width:100px;
	}
	.value{
		display:inline;
	}
	.input_checkbox{
	}
	.desc{
		color:gray;
		font-size:12px;
		margin-left:10px;
	}
	#VarNameContainer{
		overflow:hidden;
		width:21px;
		margin-left:-21px;
		height:19px;
	}
	#VarName{
		margin-left:-100px;
		margin-top:-2px;
		width:120px;
	}
	#DefaultValue{
		width:123px;
	}
</style>
</HEAD>

<BODY>
<form name="ObjectForm" id="ObjectForm" action="" method="POST">
    <input type="hidden" name="InfoViewId" id="InfoViewId" value="<%=nInfoViewId%>">
    <input type="hidden" name="IVGroupId" id="IVGroupId" value="<%=nGroupId%>">
    <input type="hidden" name="CaseSensitive" id="CaseSensitive" value="true" ignore="1">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</form>
<form name="DataForm" id="DataForm">
	<fieldset><legend WCMAnt:param="infoview_publicfill_setting.jsp.nomalinfo">基本信息</legend>
    <div class="row">
        <span class="label" WCMAnt:param="infoview_publicfill_setting.jsp.fieldname">字段名称：</span>
        <span class="value">
            <input class="inputtext" type="text" name="GroupName" id="GroupName" value="<%=oIVGroup.getName()%>" disabled>
        </span>
    </div>
	</fieldset>
	<fieldset id="fs_publish"><legend WCMAnt:param="infoview_publicfill_setting.jsp.pubshezhi">发布设置</legend>
    <div class="row">
        <span class="label" WCMAnt:param="infoview_publicfill_setting.jsp.allowpub">允许发布：</span>
        <span class="value">
            <input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="PublicFill" id="PublicFill"
				<%=oIVGroup.isPublicFill()?" checked":""%>>
        </span>
    </div>
	</fieldset>
</form>
<table border=0 cellspacing=0 cellpadding=0 width="100%" height="40">
	<tbody>
		<tr>
			<td align="center" valign="middle">
				<input type="button" value="确定" onclick="Ok();" WCMAnt:paramattr="value:infoview_publicfill_setting.jsp.okbutton">&nbsp;&nbsp;&nbsp;
				<input type="button" value="取消" onclick="parent.Cancel();" WCMAnt:paramattr="value:infoview_publicfill_setting.jsp.cancelbutton">
			</td>
		</tr>
	</tbody>
	</table>
</BODY>
</HTML>