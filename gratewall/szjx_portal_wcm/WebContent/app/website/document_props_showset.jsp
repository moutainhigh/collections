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
	String sBaseProps = currWebsite.getPropertyAsString("BASEPROPS");
	String sOtherProps = currWebsite.getPropertyAsString("OTHERPROPS");
	String sAdvanceProps = currWebsite.getPropertyAsString("ADVANCEPROPS");
	String sNeededProps = currWebsite.getPropertyAsString("NeededProps","");

	//从配置文件中读取相关信息
	List list = XMLConfigServer.getInstance().getConfigObjects(DocumentShowFieldConfig.class);
	StringBuffer sbFieldName = new StringBuffer();
	StringBuffer sbFieldDesc = new StringBuffer();
	DocumentShowFieldConfig currDocumentShowFieldConfig = null;
	for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
		currDocumentShowFieldConfig = (DocumentShowFieldConfig)it.next();
		if(!currDocumentShowFieldConfig.isWrite())continue;
		if(sbFieldName.length() == 0){
			sbFieldName.append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(currDocumentShowFieldConfig.getDesc());
		}else{
			sbFieldName.append(",").append(currDocumentShowFieldConfig.getFieldname());
			sbFieldDesc.append(",").append(currDocumentShowFieldConfig.getDesc());
		}
	}
	String[] allFields = sbFieldName.toString().split(",");
	String[] aFieldsDesc = sbFieldDesc.toString().split(",");
	HashMap allHasField = new HashMap();
	for(int t=0;t<allFields.length;t++){
		String sSingleField = "";
		String[] aSingleFields = allFields[t].split("\\.");
		if(aSingleFields.length>1){
			sSingleField = aSingleFields[1];
		}else{
			sSingleField = allFields[t];
		}
		allHasField.put(sSingleField,aFieldsDesc[t]);
	}
	String originalFields = "DOCTITLE,SUBDOCTITLE,DOCPEOPLE,DOCSTATUS,DOCRELTIME,CRUSER,CRTIME,DOCPUBTIME,OPERUSER,OPERTIME,DOCPUBURL,DOCCHANNEL,DOCKEYWORDS,DOCSOURCENAME,DOCAUTHOR,DOCWORDSCOUNT,DOCABSTRACT,REFTOCHANNEL,ORDERPRI,PUBCONFIG,DOCQUOTE";
	String sNeedFields = "SUBDOCTITLE,DOCPEOPLE,DOCRELTIME,DOCCHANNEL,DOCKEYWORDS,DOCSOURCENAME,DOCAUTHOR,DOCABSTRACT,PUBCONFIG,DOCQUOTE";
	String defaultBaseprops = "DOCCHANNEL,DOCPEOPLE,SUBDOCTITLE,DOCKEYWORDS,DOCABSTRACT,DOCSOURCENAME,DOCQUOTE";
	String defaultOtherprops = "DOCAUTHOR,DOCRELTIME";
	String defaultAdvanceprops = "PUBCONFIG";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="document_props_showset.jsp.title">文档编辑页面属性定制</title>
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
<link rel="stylesheet" type="text/css" href="document_props_showset.css">
</head>

<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'resetToDefault',
		name : wcm.LANG.CHANNEL_RESET_FIELDS||'恢复默认'
	},{
		cmd : 'save',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	}],
	size : [580, 500]
};
	
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function save(){
		//需要把设置的值拼起来，组成一个字段再提交
		var sBaseProps = "";
		var sOtherProps = "";
		var sAdvanceProps = "";
		var elBase = $('baseprops');
		var elOther = $('otherprops');
		var elAdvance = $('advanceprops');
		var eltoBeSel = $('toBeSel');
		var arrNeededProps = [];
		for (var i = 0; i < elBase.options.length; i++){
			if(sBaseProps==""){
				sBaseProps += elBase.options[i].value;
			}else{
				sBaseProps += ","+elBase.options[i].value;
			}
			if(elBase.options[i].text.indexOf("*")>-1) {
				arrNeededProps.push(elBase.options[i].value);
			}
		}
		for (var m = 0; m < elOther.options.length; m++){
			if(sOtherProps==""){
				sOtherProps += elOther.options[m].value;
			}else{
				sOtherProps += ","+elOther.options[m].value;
			}
			if(elOther.options[m].text.indexOf("*")>-1) {
				arrNeededProps.push(elOther.options[m].value);
			}
		}
		for (var n = 0; n < elAdvance.options.length; n++){
			if(sAdvanceProps==""){
				sAdvanceProps += elAdvance.options[n].value;
			}else{
				sAdvanceProps += ","+elAdvance.options[n].value;
			}
			if(elAdvance.options[n].text.indexOf("*")>-1) {
				arrNeededProps.push(elAdvance.options[n].value);
			}
		}
		for (var k = 0; k < eltoBeSel.options.length; k++){
			if(eltoBeSel.options[k].text.indexOf("*")>-1) {
				arrNeededProps.push(eltoBeSel.options[k].value);
			}
		}
		if(sBaseProps==""&&sOtherProps==""&&sAdvanceProps=="") {
			if(!confirm("没有设置任何字段,将恢复默认设置.是否继续?")){
				return false;
			}
		}
		var oPostData = {
			WebsiteId : <%=nWebsiteId%>,
			BASEPROPS : sBaseProps,
			OTHERPROPS : sOtherProps,
			ADVANCEPROPS:sAdvanceProps,
			NeededProps :arrNeededProps.join(",")
		};
		getHelper().JspRequest(WCMConstants.WCM6_PATH + "website/document_props_showset_dowith.jsp", oPostData,  true, function(transport, json){
			notifyFPCallback();
			FloatPanel.close();
		});
		return false;
	}
	function setNeed(_props){
		var sNotNeededProps = "DOCCHANNEL,PUBCONFIG,DOCQUOTE";
		var sAllPropsNotFields = "DOCPEOPLE,SUBDOCTITLE,DOCKEYWORDS,DOCABSTRACT,DOCSOURCENAME,DOCAUTHOR,DOCRELTIME";
		var elSelect;
		if(_props == "toBeSel") {
			elSelect = $("toBeSel");
		}else {
			elSelect = $(_props.toLowerCase()+"props");
		}
		var nSelectedIndex = elSelect.selectedIndex;
		if(nSelectedIndex <= -1) {
			return;
		}
		if(elSelect.options[nSelectedIndex]!=null){
			var sText = elSelect.options[nSelectedIndex].text;
			var sValue = elSelect.options[nSelectedIndex].value;
			if(_props == "toBeSel" && (","+sAllPropsNotFields+",").indexOf(","+sValue+",")>-1) {
				alert("当前操作只能对扩展字段进行设置，【"+sText+"】为非扩展字段不会显示在页面中，不能设置为必填项");
				return false;
			}
			if(sText.indexOf("*")>-1) {
				elSelect.options[nSelectedIndex].text = sText.substring(0,sText.length-3);
				elSelect.options[nSelectedIndex].style.color = "black";
			}else {
				if((","+sNotNeededProps+",").indexOf(","+sValue+",")>-1) {
					alert("当前操作中【"+sText+"】不能设置为必填项");
				}else {
					elSelect.options[nSelectedIndex].text = sText + " * ";
					elSelect.options[nSelectedIndex].style.color = "blue";
				}
			}
		}
		elSelect.options[nSelectedIndex].selected = false;
		setNeed(_props);
	}
	function moveUp(_props){
		var elSelect = $(_props.toLowerCase()+"props");
		var count = 0;
		for(var i=0;i<elSelect.options.length;i++){
			if(elSelect.options[i]!=null && elSelect.options[i].selected){
				count++;
			}
		}
		var nSelectedIndex = elSelect.selectedIndex;
		var afterSel = nSelectedIndex;
		if(elSelect.options.length <= 1 || nSelectedIndex <= 0){
			return;
		}
		for (var i = 0; i < count; i++){
			var sText = elSelect.options[nSelectedIndex].text;
			var sValue = elSelect.options[nSelectedIndex].value;
			elSelect.options[nSelectedIndex].text = elSelect.options[nSelectedIndex-1].text;
			elSelect.options[nSelectedIndex].value = elSelect.options[nSelectedIndex-1].value;
			elSelect.options[nSelectedIndex-1].text = sText;
			elSelect.options[nSelectedIndex-1].value = sValue;
			elSelect.selectedIndex++;
			nSelectedIndex = elSelect.selectedIndex;
		}
		elSelect.selectedIndex = afterSel-1;
		//把移动的几个都选中
		for(var i=0;i<count;i++){
			elSelect.options[afterSel-1+i].selected = true;
		}
	}
	function moveDown(_props){
		var elSelect = $(_props.toLowerCase()+"props");
		var count = 0;
		for(var i=0;i<elSelect.options.length;i++){
			if(elSelect.options[i]!=null && elSelect.options[i].selected){
				count++;
			}
		}
		var nSelectedIndex = elSelect.selectedIndex;
		var afterSel = nSelectedIndex;
		if(nSelectedIndex<0)return;
		if(elSelect.options.length <= 1 || nSelectedIndex+count-1>=elSelect.options.length-1)
			return;
		for (var j = 0; j < count; j++){
			for (var i = 0; i < count; i++){
				var sText = elSelect.options[nSelectedIndex+i].text;
				var sValue = elSelect.options[nSelectedIndex+i].value;
				elSelect.options[nSelectedIndex+i].text = elSelect.options[nSelectedIndex+i+1].text;
				elSelect.options[nSelectedIndex+i].value = elSelect.options[nSelectedIndex+i+1].value;
				elSelect.options[nSelectedIndex+i+1].text = sText;
				elSelect.options[nSelectedIndex+i+1].value = sValue;
			}
		}
		elSelect.selectedIndex = afterSel+1;
		//把移动的几个都选中
		for(var i=0;i<count;i++){
			elSelect.options[afterSel+1+i].selected = true;
		}
	}
	function moveTo(_props){
		var elSelect = $(_props.toLowerCase()+"props");
		var eltoBeSel = $('toBeSel');
		var count = 0;
		for(var i=0;i<eltoBeSel.options.length;i++){
			if(eltoBeSel.options[i]!=null && eltoBeSel.options[i].selected){
				count++;
			}
		}
		var nSelectedIndex = eltoBeSel.selectedIndex;
		if(nSelectedIndex<0)return;
		for (var i = count; i > 0; i--){
			var sText = eltoBeSel.options[nSelectedIndex+i-1].text;
			var sValue = eltoBeSel.options[nSelectedIndex+i-1].value;
			eltoBeSel.remove(nSelectedIndex+i-1);
			var oOption = document.createElement("OPTION");
			elSelect.options.add(oOption);
			//添加时，去掉必选项标志
			if(sText.indexOf("*")>-1) {
				sText = sText.substring(0,sText.length-3);
			}
			oOption.innerText = sText;
			oOption.value = sValue;
		}
	}
	function deleteTo(_props){
		var elSelect = $(_props.toLowerCase()+"props");
		var count = 0;
		for(var i=0;i<elSelect.options.length;i++){
			if(elSelect.options[i]!=null && elSelect.options[i].selected){
				count++;
			}
		}
		var eltoBeSel = $('toBeSel');
		var nSelectedIndex = elSelect.selectedIndex;
		if(nSelectedIndex<0)return;
		for (var i = count; i > 0; i--){
			var sText = elSelect.options[nSelectedIndex+i-1].text;
			var sValue = elSelect.options[nSelectedIndex+i-1].value;
			elSelect.remove(nSelectedIndex+i-1);
			var oOption = document.createElement("OPTION");
			eltoBeSel.options.add(oOption);
			//删除后，去掉必选项标志
			if(sText.indexOf("*")>-1) {
				sText = sText.substring(0,sText.length-3);
			}
			oOption.innerText = sText;
			oOption.value = sValue;
		}
	}
	
	function resetBaseprops(){
		var baseprops = $('baseprops')
		baseprops.innerHTML="";
		<%
			String[] arrDefaultBaseprops=defaultBaseprops.split(",");
			for(int i=0;i<arrDefaultBaseprops.length;i++){
		%>
				var oOption = document.createElement("OPTION");
				baseprops.options.add(oOption);
				oOption.value="<%=arrDefaultBaseprops[i]%>";
				oOption.innerText="<%=allHasField.get(arrDefaultBaseprops[i]).toString()%>";
		<%
			}
		%>
	}

	function resetOtherprops(){
		var otherprops= $('otherprops');
		otherprops.innerHTML="";
		<%
			String[] arrDefaultOtherprops=defaultOtherprops.split(",");
			for(int i=0;i<arrDefaultOtherprops.length;i++){
		%>
				var oOption = document.createElement("OPTION");
				otherprops.options.add(oOption);
				oOption.value="<%=arrDefaultOtherprops[i]%>";
				oOption.innerText="<%=allHasField.get(arrDefaultOtherprops[i]).toString()%>";
		<%
			}
		%>
	}

	function resetAdvanceprops(){
		var advanceprops= $('advanceprops');
		advanceprops.innerHTML="";
		<%
			String[] arrDefaultAdvanceprops=defaultAdvanceprops.split(",");
			for(int i=0;i<arrDefaultAdvanceprops.length;i++){
		%>
				var oOption = document.createElement("OPTION");
				advanceprops.options.add(oOption);
				oOption.value="<%=arrDefaultAdvanceprops[i]%>";
				oOption.innerText="<%=allHasField.get(arrDefaultAdvanceprops[i]).toString()%>";
		<%
			}
		%>
	}

	function resetToBeSel(){
		var toBeSel= $('toBeSel');
		toBeSel.innerHTML="";
		<%	
			//对于用户新加的字段（非扩展字段）的获取
			for (int i = 0; i < allFields.length; i++){
					String sSingleField = "";
					String[] aSingleFields = allFields[i].split("\\.");
					if(aSingleFields.length>1){
						sSingleField = aSingleFields[1];
					}else{
						sSingleField = allFields[i];
					}
					if((","+originalFields+",").indexOf(","+sSingleField+",")>-1)continue;
					if((","+sNeedFields+",").indexOf(","+sSingleField+",")>-1)continue;
		%>
				var oOption = document.createElement("OPTION");
				toBeSel.options.add(oOption);
				oOption.value="<%=sSingleField%>";
				oOption.innerText="<%=allHasField.get(sSingleField).toString()%>";
		<%
			}
			// 获取扩展字段
			ContentExtFields currExtendedFields = null;
					IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
					if(currWebsite != null){
						currExtendedFields  = currChannelService.getExtFields(currWebsite, null);
						for (int z = 0; z < currExtendedFields.size(); z++){
							ContentExtField currExtendedField = (ContentExtField)currExtendedFields.getAt(z);
							String sDBFieldName = CMyString.transDisplay(currExtendedField.getName());
							String sDBFieldDesc = CMyString.transDisplay(currExtendedField.getPropertyAsString("LOGICFIELDDESC"));
		%>
				var oOption = document.createElement("OPTION");
				toBeSel.options.add(oOption);
				oOption.value="<%=sDBFieldName%>";
				oOption.innerText="<%=sDBFieldDesc%>";
		<%
						}
					}
		%>
	}
	// 恢复到默认
	function resetToDefault(){
		resetBaseprops();
		resetOtherprops();
		resetAdvanceprops();
		resetToBeSel();
		return false;
	}
</script>
<body>
<div style="width:100%;height:450px;">
<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:450px;table-layout:fixed;">
   <tr>
	<td width="150">
		<div style="width:100%;height:100%;">
			<div class="sel_out">
				<select name="toBeSel" id="toBeSel" class="toBeSel" multiple="true">
					<%
						if((!CMyString.isEmpty(sBaseProps))||(!CMyString.isEmpty(sOtherProps))||(!CMyString.isEmpty(sAdvanceProps))){
							String[] aNeedFields = sNeedFields.split(",");
							for(int z=0;z<aNeedFields.length;z++){
								if(aNeedFields[z].equalsIgnoreCase("ORDERPRI")
									||aNeedFields[z].equalsIgnoreCase("REFTOCHANNEL"))continue;
								if(sBaseProps != null && (","+sBaseProps+",").indexOf(","+aNeedFields[z]+",")>-1)continue;
								if(sOtherProps != null && (","+sOtherProps+",").indexOf(","+aNeedFields[z]+",")>-1)continue;
								if(sAdvanceProps != null && (","+sAdvanceProps+",").indexOf(","+aNeedFields[z]+",")>-1)continue;
								
								if((","+sNeededProps+",").indexOf(","+aNeedFields[z]+",")>-1) {
					%>
						<option value="<%=aNeedFields[z]%>" style="color:blue"><%=allHasField.get(aNeedFields[z]).toString()%> * </option>
					<%
								}else {
					%>
						<option value="<%=aNeedFields[z]%>"><%=allHasField.get(aNeedFields[z]).toString()%></option>
					<%
								}
							}
						}
					%>
					<%
						//对于用户新加的字段（非扩展字段）的获取
						for (int i = 0; i < allFields.length; i++){
							String sSingleField = "";
							String[] aSingleFields = allFields[i].split("\\.");
							if(aSingleFields.length>1){
								sSingleField = aSingleFields[1];
							}else{
								sSingleField = allFields[i];
							}
							if((","+originalFields+",").indexOf(","+sSingleField+",")>-1)continue;
							if((","+sBaseProps+",").indexOf(","+sSingleField+",")>-1
								||(","+sOtherProps+",").indexOf(","+sSingleField+",")>-1
								||(","+sAdvanceProps+",").indexOf(","+sSingleField+",")>-1)continue;

							if((","+sNeededProps+",").indexOf(","+allFields[i]+",")>-1) {
					%>
						<option value="<%=sSingleField%>" style="color:blue"><%=allHasField.get(sSingleField).toString()%> * </option>
					<%
							}else {
					%>
						<option value="<%=sSingleField%>"><%=allHasField.get(sSingleField).toString()%></option>
					<%
							}
						}
						//获取扩展字段
						if(currWebsite != null){
							currExtendedFields = currChannelService.getExtFields(currWebsite, null);
							for (int z = 0; z < currExtendedFields.size(); z++){
								ContentExtField currExtendedField = (ContentExtField)currExtendedFields.getAt(z);
								String sDBFieldName = CMyString.transDisplay(currExtendedField.getName());
								String sDBFieldDesc = CMyString.transDisplay(currExtendedField.getPropertyAsString("LOGICFIELDDESC"));
								if(sBaseProps!=null&&sOtherProps!=null&&sAdvanceProps!=null){
									if((","+sBaseProps+",").indexOf(","+sDBFieldName+",")>-1)continue;
									if((","+sOtherProps+",").indexOf(","+sDBFieldName+",")>-1)continue;
									if((","+sAdvanceProps+",").indexOf(","+sDBFieldName+",")>-1)continue;
								}
								if((","+sNeededProps+",").indexOf(","+sDBFieldName+",")>-1) {
					%>
						<option value="<%=sDBFieldName%>" style="color:blue"><%=sDBFieldDesc%> * </option>
					<%
								}else {
					%>
						<option value="<%=sDBFieldName%>"><%=sDBFieldDesc%></option>
					<%
								}
							}
						}
					%>
				</select>
			</div>
			<div>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="setNeedBtn" WCMAnt:param="build_to_view.html.need" onclick='setNeed("toBeSel")'>必填</button>
			</div>
		</div>
	</td>
	<td width="80">
		<div class="ViewCommand">
			<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
			<tbody>
				<tr>
					<td style="vertical-align:center;text-align:center;"><img src="../images/channel/docprop.gif" border=0 alt="" onclick='moveTo("base")'/><br/><span WCMAnt:param="document_props_showset.jsp.addtobase">添加到基本</span></td>
				</tr>
				
			</tbody>
			</table>
		</div>
		<div class="ViewCommand"><table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;"> 
		<tbody>
			<tr>
				<td style="vertical-align:center;text-align:center;"><img src="../images/channel/docprop.gif" border=0 alt="" onclick='moveTo("other")'/><br/><span WCMAnt:param="document_props_showset.jsp.addtoother">添加到其他</span></td>
			</tr>
			
		</tbody>
		</table></div>
		<div class="ViewCommand"><table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
		<tbody>
			<tr>
				<td style="vertical-align:center;text-align:center;"><img src="../images/channel/docprop.gif" border=0 alt="" onclick='moveTo("advance")'/><br/><span WCMAnt:param="document_props_showset.jsp.addtoadvance">添加到高级</span></td>
			</tr>
		</tbody>
		</table></div>
	</td>
	<td>
		<div style="width:100%;height:100%;">
			<fieldset>
				<legend style="color:black;" WCMAnt:param="document_props_showset.jsp.base">基本属性</legend>
				<div class="seled_prop">
				<select name="baseprops" id="baseprops" multiple="true" style="width:100%;height:100%;">
				<%
					if((CMyString.isEmpty(sBaseProps))&&(CMyString.isEmpty(sOtherProps))&&(CMyString.isEmpty(sAdvanceProps))){
				%>
					<option value="DOCCHANNEL"><%=allHasField.get("DOCCHANNEL").toString()%></option>
					<option value="DOCPEOPLE"><%=allHasField.get("DOCPEOPLE").toString()%></option>
					<option value="SUBDOCTITLE"><%=allHasField.get("SUBDOCTITLE").toString()%></option>
					<option value="DOCKEYWORDS"><%=allHasField.get("DOCKEYWORDS").toString()%></option>
					<option value="DOCABSTRACT"><%=allHasField.get("DOCABSTRACT").toString()%></option>
					<option value="DOCSOURCENAME"><%=allHasField.get("DOCSOURCENAME").toString()%></option>
					<option value="DOCQUOTE"><%=allHasField.get("DOCQUOTE").toString()%></option>
				<%
					}else{
						if(!CMyString.isEmpty(sBaseProps)){
							String[] aBaseProps = sBaseProps.split(",");
							for (int i = 0; i < aBaseProps.length; i++){
								String sBaseProp = "";
								if(allHasField.get(aBaseProps[i])==null){
									String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
									WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
									filter.addSearchValues(nWebsiteId);
									filter.addSearchValues(aBaseProps[i]);
									ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
									if(m_oContentExtFields.size()>=1){
										ContentExtField m_oContentExtField = (ContentExtField)m_oContentExtFields.getAt(0);
										sBaseProp = m_oContentExtField.getDesc();
									}
								}else{
									sBaseProp = allHasField.get(aBaseProps[i]).toString();
								}
								if(CMyString.isEmpty(sBaseProp)) {
									continue;//如果使用的扩展字段已经删除，则不能显示空
								}
								if((","+sNeededProps+",").indexOf(","+aBaseProps[i]+",")>-1){
				%>
					<option value="<%=aBaseProps[i]%>" style="color:blue"><%=sBaseProp%> * </option>
				<%
								}else{
				%>
					<option value="<%=aBaseProps[i]%>"><%=sBaseProp%></option>
				<%
								}
							}
						}
					}
				%>
				</select>
				</div>
			</fieldset>

			<div class="sep"></div>
			<fieldset>
				<legend style="color:black;" WCMAnt:param="document_props_showset.jsp.other">其他属性</legend>
				<div class="seled_prop">
				<select name="otherprops" id="otherprops" multiple="true" style="width:100%;height:100%;">
				<%
					if((CMyString.isEmpty(sBaseProps))&&(CMyString.isEmpty(sOtherProps))&&(CMyString.isEmpty(sAdvanceProps))){
				%>
					<option value="DOCAUTHOR"><%=allHasField.get("DOCAUTHOR").toString()%></option>
					<option value="DOCRELTIME"><%=allHasField.get("DOCRELTIME").toString()%></option>
					
				<%
					}else{
						if(!CMyString.isEmpty(sOtherProps)){
							String[] aOtherProps = sOtherProps.split(",");
							for (int i = 0; i < aOtherProps.length; i++){
								String sOtherProp = "";
								if(allHasField.get(aOtherProps[i])==null){
									String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
									WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
									filter.addSearchValues(nWebsiteId);
									filter.addSearchValues(aOtherProps[i]);
									ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
									if(m_oContentExtFields.size()>=1){
										ContentExtField m_oContentExtField = (ContentExtField)m_oContentExtFields.getAt(0);
										sOtherProp = m_oContentExtField.getDesc();
									}
								}else{
									sOtherProp = allHasField.get(aOtherProps[i]).toString();
								}
								if(CMyString.isEmpty(sOtherProp)) {
									continue;//如果使用的扩展字段已经删除，则不能显示空
								}
								if((","+sNeededProps+",").indexOf(","+aOtherProps[i]+",")>-1){

				%>
					<option value="<%=aOtherProps[i]%>" style="color:blue"><%=sOtherProp%> * </option>
				<%
								}else{
				%>
					<option value="<%=aOtherProps[i]%>"><%=sOtherProp%></option>
				<%
								}
							}
						}
					}	
				%>
				</select>
				</div>
			</fieldset>

			<div class="sep"></div>
			<fieldset>
				<legend style="color:black;" WCMAnt:param="document_props_showset.jsp.advance">高级属性</legend>
				<div class="seled_prop">
				<select name="advanceprops" id="advanceprops" multiple="true" style="width:100%;height:100%;">
				<%
					if((CMyString.isEmpty(sBaseProps))&&(CMyString.isEmpty(sOtherProps))&&(CMyString.isEmpty(sAdvanceProps))){
				%>
					
					<option value="PUBCONFIG"><%=allHasField.get("PUBCONFIG").toString()%></option>
				<%
					}else{
						if(!CMyString.isEmpty(sAdvanceProps)){
							String[] aAdvanceProps = sAdvanceProps.split(",");
							for (int i = 0; i < aAdvanceProps.length; i++){
								String sAdvanceProp = "";
								if(allHasField.get(aAdvanceProps[i])==null){
									String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
									WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
									filter.addSearchValues(nWebsiteId);
									filter.addSearchValues(aAdvanceProps[i]);
									ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
									if(m_oContentExtFields.size()>=1){
										ContentExtField m_oContentExtField = (ContentExtField)m_oContentExtFields.getAt(0);
										sAdvanceProp = m_oContentExtField.getDesc();
									}
								}else{
									sAdvanceProp = allHasField.get(aAdvanceProps[i]).toString();
								}
								if(CMyString.isEmpty(sAdvanceProp)) {
									continue;//如果使用的扩展字段已经删除，则不能显示空
								}
								if((","+sNeededProps+",").indexOf(","+aAdvanceProps[i]+",")>-1){
				%>
					<option value="<%=aAdvanceProps[i]%>" style="color:blue"><%=sAdvanceProp%> * </option>
				<%
								}else{
				%>
					<option value="<%=aAdvanceProps[i]%>"><%=sAdvanceProp%></option>
				<%
								}
							}
						}
					}	
				%>
				</select>
				</div>
			</fieldset>
			
		</div>
	</td>
	<td width="80">
		<div class="ViewCommand" id="ViewCommand">
				<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
				<tbody>
					<tr>
						<td style="vertical-align:center;"><button type="button" id="setNeedBtn" WCMAnt:param="build_to_view.html.need" onclick='setNeed("base")'>必填</button><br/>
						<button type="button" id="viewUpBtn" WCMAnt:param="build_to_view.html.upper" onclick='moveUp("base")'>上移</button><br/>
						<button type="button" id="viewDownBtn" WCMAnt:param="build_to_view.html.lower" onclick='moveDown("base")'>下移</button><br/>
						<button type="button" id="viewDeleteBtn" WCMAnt:param="build_to_view.html.delete" onclick='deleteTo("base")'>删除</button>
						</td>
					</tr>
				</tbody>
				</table>
		</div>
		<div class="ViewCommand" id="ViewCommand">
				<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
				<tbody>
					<tr>
						<td style="vertical-align:center;"><button type="button" id="setNeedBtn" WCMAnt:param="build_to_view.html.need" onclick='setNeed("other")'>必填</button><br/>
						<button type="button" id="viewUpBtn" WCMAnt:param="build_to_view.html.upper" onclick='moveUp("other")'>上移</button><br/>
						<button type="button" id="viewDownBtn" WCMAnt:param="build_to_view.html.lower" onclick='moveDown("other")'>下移</button><br/>
						<button type="button" id="viewDeleteBtn" WCMAnt:param="build_to_view.html.delete" onclick='deleteTo("other")'>删除</button>
						</td>
					</tr>
				</tbody>
				</table>
		</div>
		<div class="ViewCommand" id="ViewCommand">
				<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;">
				<tbody>
					<tr>
						<td style="vertical-align:center;"><button type="button" id="setNeedBtn" WCMAnt:param="build_to_view.html.need" onclick='setNeed("advance")'>必填</button><br/>
						<button type="button" id="viewUpBtn" WCMAnt:param="build_to_view.html.upper" onclick='moveUp("advance")'>上移</button><br/>
						<button type="button" id="viewDownBtn" WCMAnt:param="build_to_view.html.lower" onclick='moveDown("advance")'>下移</button><br/>
						<button type="button" id="viewDeleteBtn" WCMAnt:param="build_to_view.html.delete" onclick='deleteTo("advance")'>删除</button>
						</td>
					</tr>
				</tbody>
				</table>
		</div>
	</td>
   </tr>
</table>
</div>
</body>
</html>