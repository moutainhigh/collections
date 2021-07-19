<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="java.util.HashMap" %>

<%@include file="../include/public_server.jsp"%>
<%
	//1、需要得到站点id，将信息保存到站点上
	int nWebsiteId = currRequestHelper.getInt("WebsiteId",0);
	//2、获取到已设置的字段的值
	WebSite currWebsite = WebSite.findById(nWebsiteId);
	if(currWebsite == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.website", "没有找到ID为{0}的站点"), new int[]{nWebsiteId}));
	}
	String sViewProps = currWebsite.getPropertyAsString("VIEWPROPS");
	String sOriginalViewProps = "Basic,Other,Advanced,Attachment,Relation,Extends";
	HashMap allOriginalViewProps = new HashMap();
	allOriginalViewProps.put("Basic","基本属性");
	allOriginalViewProps.put("Other","其他属性");
	allOriginalViewProps.put("Advanced","高级设置");
	allOriginalViewProps.put("Attachment","附件管理");
	allOriginalViewProps.put("Relation","相关文档");
	allOriginalViewProps.put("Extends","扩展字段");

	if (CMyString.isEmpty(sViewProps)){
		sViewProps = sOriginalViewProps;
	}
	if("0".equals(sViewProps)){
		sViewProps ="";
	}

	String[] arrCanViewProps = sViewProps.split(",");
	String[] arrAllViewProps = sOriginalViewProps.split(",");
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档编辑页面属性视图定制</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
</head>

<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'resetToDefault',
		name : '恢复默认'
	},{
		cmd : 'save',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	}],
	size : [400, 200]
};
	
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function save(){
		//需要把设置的值拼起来，组成一个字段再提交
		var sViewProps = "";
		
		var viewTable = $("canView");
		var len = viewTable.rows.length-1;
		for (var i = 0; i <= len; i++){
			nRow = i+1;
			if(!viewTable.rows[nRow]){
				continue;
			}
			if(sViewProps==""){
				sViewProps = viewTable.rows[nRow].id;
			}else{
				sViewProps += ","+viewTable.rows[nRow].id;
			}
		}
		if (sViewProps==""){
			sViewProps = "0";
		}
		var oPostData = {
			WebsiteId : <%=nWebsiteId%>,
			VIEWPROPS : sViewProps
		};
		getHelper().JspRequest(WCMConstants.WCM6_PATH + "website/document_props_viewset_dowith.jsp", oPostData,  true, function(transport, json){
			notifyFPCallback();
			FloatPanel.close();
		});
		return false;
	}
</script>
<body>
<style type="text/css">
	td{
		text-align:center;
	}
	input{
		text-align:center;
	}
</style>
<div style="width:100%;height:450px;">
	<table id="canView" border="1" cellspacing="0" cellpadding="0" style="table-layout:fixed;width:100%">
		<tbody>
			<tr>
				<td>是否显示</td>
				<td>界面属性名称</td>
				<td>显示顺序</td>
			</tr>
			<%
			String sCheckId = "";
			String sTextId = "";
			HashMap hasViews = new HashMap();
			for (int i = 0; i < arrCanViewProps.length; i++){
				if(CMyString.isEmpty(arrCanViewProps[i]))continue;
				hasViews.put(arrCanViewProps[i], "1");
				sCheckId = "check"+arrCanViewProps[i];
				sTextId = "text"+arrCanViewProps[i];
			%>
			<tr id="<%=arrCanViewProps[i]%>">
				<td><span><input type="checkbox" name="" id="<%=sCheckId%>" value="" checked onclick="notifyTable(this.id)"/></span></td>
				<td><span><%=allOriginalViewProps.get(arrCanViewProps[i])%></span></td>
				<td><span><input type="text" name="" value="<%=i+1%>" id="<%=sTextId%>" size="2" onchange="notifyIndex(this.id)" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></span></td>
			</tr>
			<%}%>
		</tbody>
	</table>
	<table id="canNotView" border="1" cellspacing="0" cellpadding="0" style="table-layout:fixed;width:100%">
		<tbody>
			<tr>
				<hr />
			</tr>

			<tr style="display:none">
				<td>是否显示</td>
				<td>界面属性名称</td>
				<td>显示位置</td>
			</tr>
			<%
			String sNoCheckId = "";
			String sNoTextId = "";

			for (int j = 0; j < arrAllViewProps.length; j++){
				if(hasViews.get(arrAllViewProps[j]) != null){
					continue;
				}
				sNoCheckId = "check"+arrAllViewProps[j];
				sNoTextId = "text"+arrAllViewProps[j];
			%>
			<tr id="<%=arrAllViewProps[j]%>">
				<td><span><input type="checkbox" name="" id="<%=sNoCheckId%>" value="" onclick="notifyTable(this.id)"/></span></td>
				<td><span><%=allOriginalViewProps.get(arrAllViewProps[j])%></span></td>
				<td><span><input type="text" name="" value="" size="2" id="<%=sNoTextId%>" disabled="true" onchange="notifyIndex(this.id)" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></span></td>
			</tr>
			<%}%>
		</tbody>
	</table>
</div>
</body>
<script language="javascript">
<!--
	function notifyTable(id){
		var inputTextId = "text"+id.substring(5);
		var viewTable = $("canView");
		var noneTable = $("canNotView");
		if($(id).checked){//添加到显示的table中
			var len = viewTable.rows.length;
			var oViewTr = $(id).parentElement.parentElement.parentElement;
			Element.first(viewTable).appendChild(oViewTr);
			//
			$(id).checked = true;
			$(inputTextId).value = len;
			$(inputTextId).disabled = false;
		}else {//添加到不显示的table中
			var oNoneTr = $(id).parentElement.parentElement.parentElement;
			Element.first(noneTable).insertBefore(oNoneTr,noneTable.rows[0]);
			//
			$(id).checked = false;
			$(inputTextId).value = "";
			$(inputTextId).disabled = true;
		}
		//调整完毕后，重新调整顺序
		adjustTableIndex(viewTable);
	}
	function notifyIndex(id){
		//事件只在viewTable中触发；
		var viewTable = $("canView");
		var len = viewTable.rows.length-1;
		var index = Math.floor($(id).value);
		//如果当前设置顺序在当前选中之后，则+1
		var lastIndex = parseInt($(id).getAttribute("nIndex",0));
		if(index>lastIndex) {
			index += 1; 
		}
		var oViewTr = $(id).parentElement.parentElement.parentElement;
		if (index>len || isNaN(index)){
			index=len;
			viewTable.tBodies[0].appendChild(oViewTr);
		}else {
			if (index<1)index=1;
			Element.first(viewTable).insertBefore(oViewTr,viewTable.rows[index]);
		}
		$(id).value = index;
		//
		//兼容新添加tr调整顺序后丢失checked
		var inputCheckId = "check" + id.substring(4);
		$(inputCheckId).checked = true;
		//调整完毕后，重新调整顺序
		adjustTableIndex(viewTable);
	}
	function adjustTableIndex(viewTable){
		var len = viewTable.rows.length-1;
		for (var i = 0; i <= len; i++){
			var nRow = i+1;
			if(!viewTable.rows[nRow]){
				continue;
			}
			viewTable.rows[nRow].getElementsByTagName("input")[1].value = nRow;
			viewTable.rows[nRow].getElementsByTagName("input")[1].setAttribute("nIndex",nRow);
		}
	}
	//恢复默认设置
	function resetToDefault(){
		var viewTable = $("canView");
		var noneTable = $("canNotView");
		var temp1Html = [
			'<table id="canView" border="1" cellspacing="0" cellpadding="0" style="table-layout:fixed;width:100%">',
				'<tbody>',
					'<tr>',
						'<td>是否显示</td>',
						'<td>界面属性名称</td>',
						'<td>显示顺序</td>',
					'</tr>',
					'<tr id="Basic">',
						'<td><span><input id="checkBasic" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>基本属性</span></td>',
						'<td><span><input id="textBasic" onchange="notifyIndex(this.id)" value="1" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
					'<tr id="Other">',
						'<td><span><input id="checkOther" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>其他属性</span></td>',
						'<td><span><input id="textOther" onchange="notifyIndex(this.id)" value="2" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
					'<tr id="Advanced">',
						'<td><span><input id="checkAdvanced" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>高级设置</span></td>',
						'<td><span><input id="textAdvanced" onchange="notifyIndex(this.id)" value="3" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
					'<tr id="Attachment">',
						'<td><span><input id="checkAttachment" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>附件管理</span></td>',
						'<td><span><input id="textAttachment" onchange="notifyIndex(this.id)" value="4" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
					'<tr id="Relation">',
						'<td><span><input id="checkRelation" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>相关文档</span></td>',
						'<td><span><input id="textRelation" onchange="notifyIndex(this.id)" value="5" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
					'<tr id="Extends">',
						'<td><span><input id="checkExtends" onclick="notifyTable(this.id)" value="" CHECKED="" type="checkbox"></span></td>',
						'<td><span>扩展字段</span></td>',
						'<td><span><input id="textExtends" onchange="notifyIndex(this.id)" value="6" size="2" type="text" maxlength="1" style="IME-MODE: disabled;" onpropertychange="if(isNaN(value)) value=value.substring(0,value.length-1)" onafterpaste="this.value=this.value.replace(/\D/g,\"\")"></span></td>',
					'</tr>',
				'</tbody>',
			'</table>'
		].join("");
		var temp2Html = [
			'<table style="width: 100%; table-layout: fixed;" id="canNotView" border="1" cellSpacing="0" cellPadding="0">',
				'<tbody>',
					'<tr>',
						'<hr>',
					'</tr>',
					'<tr style="display: none;">',
						'<td>是否显示</td>',
						'<td>界面属性名称</td>',
						'<td>显示位置</td>',
					'</tr>',
				'</tbody>',
			'</table>'
		].join("");

		//viewTable    table innerHTML 在IE10以前只读
		temp = document.createElement('div');
		temp.innerHTML = temp1Html;
		viewTable.replaceChild(temp.firstChild.tBodies[0], viewTable.tBodies[0]);
		//noneTable
		temp.innerHTML = temp2Html;
		noneTable.replaceChild(temp.firstChild.tBodies[0], noneTable.tBodies[0]);
		return false;
	}
//-->
</script>
</html>