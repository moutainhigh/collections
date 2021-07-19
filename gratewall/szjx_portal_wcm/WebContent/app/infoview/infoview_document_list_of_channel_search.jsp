<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.resource.Status"%>
<%@ page import="com.trs.components.wcm.resource.Statuses"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.lang.StringBuffer"%>
<%@ page import="com.trs.components.infoview.extract.DataType"%>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields"%>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField"%>
<%@ page import="java.sql.Types"%>
<%@ page import="com.trs.cms.content.CMSBaseObjs"%>
<%@include file="./infoview_public_include.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	WCMFilter oFilter = new WCMFilter();
	oFilter.setOrder("statusid asc");
    Statuses sStatus =  Statuses.openWCMObjs(loginUser, oFilter);
	WCMFilter oInfoViewFilter = new WCMFilter();
	oInfoViewFilter.setWhere(" InfoViewId=" + nInfoViewId + " and (DataType = " + Types.DATE + " or DataType = " + Types.TIMESTAMP + ") and InSearch=1");
	InfoViewFields oInfoViewDateFields = (InfoViewFields)CMSBaseObjs.openWCMObjs(loginUser, InfoViewFields.class, oInfoViewFilter);
	StringBuffer sInfoViewDateField = new StringBuffer();
	if(oInfoViewDateFields != null){
		for(int i=0; i<oInfoViewDateFields.size(); i++){
			InfoViewField oInfoViewField = (InfoViewField)oInfoViewDateFields.getAt(i);
			if(i == oInfoViewDateFields.size() - 1)
				sInfoViewDateField.append(oInfoViewField.getName());
			else sInfoViewDateField.append(oInfoViewField.getName() + ",");
		}			 
	}

%>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>Infoview Fields Tree</title>
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<style languageStyle="languageStyle">
body{
	font-size:14px;
	line-height:22px;
	FONT-FAMILY: SimSun
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
	width:100px;
	font-size:14px;
	line-height:18px;
	border:1px solid gray;
}
.label{
	display:inline;
	width:100px;
}
.ext-gecko2 .label{
	display:-moz-inline-grid;
  }
.ext-strict .label{
	display:inline-block;
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
.button{
	border:1px solid gray;
	width:60px;
	cursor:pointer;
}
#bodyContainer{
	padding:0px;
	margin:0px;
	height:380px;
	overflow:auto;
}
.calendarShow{width:24px;height:18px;line-height:18px;}
.calendarShow img{margin:-5px 0 2px -9px!important;margin:-2px 0 0 -1px;}
</style>
</head>
<body>
<div id="bodyContainer" onscroll="forceReplain('_DOCSTATUS');">
	<fieldset style="width:50%;float:left;"><legend WCMAnt:param="infoview.document.list.of.channel.search.syssearchcondition">系统检索条件</legend>
	<div id="system_fields">
	</div>
	</fieldset>
	<fieldset id="crtime_cntr" style="display:none;margin-left:5%;width:40%;float:left;">
	<legend WCMAnt:param="infoview.document.list.of.channel.search.crtime">创建时间:</legend>
	<div class="row">
		<span class="label" style="width:80px;" WCMAnt:param="infoview.document.list.of.channel.search.starttime">开始时间：</span>
		<INPUT class="inputtext" id="StartTime" style="WIDTH: 80px" maxLength=10 WCMAnt:param="name:channel_search.startTime" name="开始时间" ignore="1">
		<button id="startTimeButton" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	<div class="row">
		<span class="label" style="width:80px;" WCMAnt:param="infoview.document.list.of.channel.search.finishtime">结束时间:&nbsp;</span>
		<INPUT class="inputtext" id="EndTime" style="WIDTH: 80px" maxLength=10  WCMAnt:param="name:channel.search.end" name="结束时间" ignore="1">
		<button id="EndTimeButton" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	</fieldset>
	<fieldset id="infoview_fields_cntr" style="display:none;width:95%;float:left;"><legend WCMAnt:param="infoview.document.list.of.channel.search.infoviewfield">表单字段</legend>
	<div id="infoview_fields">
	</div>
	</fieldset>
	<textarea id="template" style="display:none">
	<div class="row">
		<span class="label">{#DISPLAY#}：</span> 
		<span class="value"><input class="inputtext" type="text" name="{#NAME#}" value="" pattern='string' elname="{#DISPLAY#}" max_len="100"></span>
	</div>
	</textarea>
	<textarea id="DateTemplate" style="display:none">
		<div class="row">
			<span class="label">{#DISPLAY#}：</span> 
			<span class="value">
				<input class="inputtext" type="text" id="{#DISPLAY#}" name="{#NAME#}" value="" pattern='string' elname="{#DISPLAY#}" max_len="100">
				<button id="{#DISPLAY#}_Button" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
			</span>
		</div>
	</textarea>
	<div style="display:none">
		<div class="row" id="divDocStatus">
			<span class="label" WCMAnt:param="infoview.document.list.of.channel.search.docstatus">文档状态：</span> 
			<span class="value">
			<select id="_DOCSTATUS" name="_DOCSTATUS" value="">
				<option value=""></option>
				<%	
					StringBuffer sStringbuffer = new StringBuffer();
					for(int i=0; i<sStatus.size(); i++){
						Status oStatus = (Status)sStatus.getAt(i);
						sStringbuffer.append("<option value='"+ oStatus.getId() + "'>" + oStatus.getDisp() + "</option>");
					}
					out.print(sStringbuffer.toString());
				%>
			</select>
			</span>
		</div>
	</div>
	<div style="height:5px;width:100%;float:left;">&nbsp;</div>
</div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/xmlhelper.js"></script>
<script src="../../app/js/easyversion/extjson.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/easyversion/validator52.js"></script>
<script src="../../app/js/data/locale/infoview.js" WCMAnt:locale="../../app/js/locale/$locale$.js"></script>
<script language="javascript" src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/easyversion/calendar3.js" type="text/javascript"></script>

<script>
var m_oSearchFields;
var m_cb;
var m_sInfoViewDateFields = '<%=sInfoViewDateField.toString()%>';
var m_aInfoViewDateFields = m_sInfoViewDateFields.split(",");
function isEmpty(_sValue){
	return !_sValue || _sValue.trim().length<=0;
}
function validate(){
	var rst = TRSValidator52.validatorForm('bodyContainer');
	
	if(!rst.valid){
		Ext.Msg.$alert(rst.einfos.join('\n'), function(){
			try{rst.fstEle.focus();}catch(e){}
		});
		return false;
	}
	
	// 校验开始时间和结束时间
	var sEndTime = $F("EndTime");
	var sStartTime = $F("StartTime");
	if(!isEmpty(sEndTime) && !isEmpty(sStartTime) ){
		sStartTime = sStartTime.replace("-", "/");
		sEndTime = sEndTime.replace("-", "/");
		var oStartDate = new Date(sStartTime), oEndDate = new Date(sEndTime);
		if(oEndDate < oStartDate){
			Ext.Msg.$alert(wcm.LANG.infoview_document_list_of_channel_search_1000 || "开始时间大于结束时间！", function(){
				try{$("StartTime").focus();}catch(e){}
			});
			
			return  false;
		}
	}
	if(!isEmpty(sStartTime)){
		sStartTime = sStartTime.replace("-", "/");
		var oStartDate = new Date(sStartTime);
		if((new Date()) < oStartDate){
			Ext.Msg.$alert(wcm.LANG.infoview_document_list_of_channel_search_2000 || "开始时间大于现在的时间！", function(){
				try{$("StartTime").focus();}catch(e){}
			});
			
			return  false;
		}
	}

	return true;
}

function onOk(){
	if(!validate())
		return false;
	var arg = confirm();
	if(!arg)
		return false;
	m_cb.callback(arg);
	return false;
}
function init(_args, cb) {
	m_cb = cb;
	m_oSearchFields = _args.SearchFields;
	buildUI();
	var oSearchXML = _args.SearchXML;
	var searchXmlJson = parseXml(oSearchXML);
	var elTimeRange = $v(searchXmlJson, '//TimeRange');
	if(elTimeRange != null){
		var oStartTime = $("StartTime");
		oStartTime.value = (elTimeRange.getAttribute("StartTime") || "").replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
		var oEndTime = $("EndTime");
		oEndTime.value = (elTimeRange.getAttribute("EndTime") || "").replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
	}
	var lstNodes = $v(searchXmlJson, '//SearchItem');
	var n = (lstNodes)?lstNodes.length:0;
	var oItemNode = null;
	var sKey, sValue;
	for (var i = 0; i<n; i++){
		oItemNode = lstNodes[i];
		sKey = oItemNode.getAttribute("key");
		sValue = oItemNode.getAttribute("value");
		var elKey = $(sKey);
		if(elKey){
			elKey.value = sValue;
		}
	}
}
wcm.TRSCalendar.get({
		input : 'StartTime',
		handler : 'startTimeButton'
});
wcm.TRSCalendar.get({
		input : 'EndTime',
		handler : 'EndTimeButton'
});
var m_oMapDisplays = {
	DOCTITLE : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",
	CRTIME : wcm.LANG['INFOVIEW_DOC_96'] ||  "创建时间",
	CRUSER : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",
	_DOCSTATUS : wcm.LANG['INFOVIEW_DOC_99'] || "状态",
	DOCNO : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",
	RANDOMSERIAL : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",
	POSTUSER : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)"
}
function getDisplayName(_sFieldName){
	if(_sFieldName.indexOf(':')==-1){
		return m_oMapDisplays[_sFieldName] || _sFieldName;
	}
	return _sFieldName.replace(/^.*?:([^:]*)$/g,'$1');
}
function buildUI(){
	var sTemplatValue = $('template').value;
	var sDateTemplatValue = $('DateTemplate').value;
	var elSystemFields = $('system_fields');
	var elInfoViewFields = $('infoview_fields');
	Element.hide($('crtime_cntr'));
	Element.hide($('infoview_fields_cntr'));
	if(m_oSearchFields.length==0){
		m_oSearchFields = ["DOCTITLE","CRTIME","CRUSER","_DOCSTATUS","DOCNO","RANDOMSERIAL"];
	}
	var bInfoViewExsits = false;
	for(var i=0;i<m_oSearchFields.length;i++){
		var sFieldName = m_oSearchFields[i];
		if(sFieldName.toUpperCase()=='CRTIME'){
			Element.show($('crtime_cntr'));
			continue;
		}
		if(sFieldName.toUpperCase()=='_DOCSTATUS'){
			new Insertion.Bottom(elSystemFields, $('divDocStatus').innerHTML);
			continue;
		}
		var sDisplay = getDisplayName(sFieldName);
		var sHtml = sTemplatValue.replace(/\{#NAME#\}/ig, sFieldName).replace(/\{#DISPLAY#\}/ig, sDisplay);
		if(m_sInfoViewDateFields.indexOf(sDisplay) >= 0)
			sHtml = sDateTemplatValue.replace(/\{#NAME#\}/ig, sFieldName).replace(/\{#DISPLAY#\}/ig, sDisplay);
		if(sFieldName.indexOf(':')==-1){
			new Insertion.Bottom(elSystemFields, sHtml);
		}else{
			bInfoViewExsits = true;
			new Insertion.Bottom(elInfoViewFields, sHtml);
		}
	}
	if(bInfoViewExsits){
		Element.show($('infoview_fields_cntr'));
	}
	initSearchDateField();
}
function initSearchDateField(){
	for(var k=0; k < m_aInfoViewDateFields.length; k++){
		if(m_aInfoViewDateFields[k] == '') continue;
		wcm.TRSCalendar.get({
			input : m_aInfoViewDateFields[k],
			handler : m_aInfoViewDateFields[k] +'_Button',
			dtFmt : 'yyyy-mm-dd',
			withtime : false
		});
	}
}
function jsonTimeRange(){
	var sStartTime = document.getElementById("StartTime").value;
	sStartTime = date_trim(sStartTime, ' 00:00:00');
	var sEndTime = document.getElementById("EndTime").value;
	sEndTime = date_trim(sEndTime, ' 23:59:59');
	return {StartTime : sStartTime, EndTime : sEndTime};
}
function confirm() {
	var oSearchXML = loadXml("<SearchXML></SearchXML>");
	var elRoot = oSearchXML.documentElement;
	var json = {
		TimeRange : jsonTimeRange()
	};
	var arr = [];
	for(var i=0;i<m_oSearchFields.length;i++){
		var sFieldName = m_oSearchFields[i], upFieldName = sFieldName.toUpperCase();
		if(upFieldName=='CRTIME' || upFieldName=='_DOCSTATUS')continue;
		var eField = $(sFieldName);
		if(!eField || !$F(eField))continue;
		var value = $F(eField);
		arr.push({key : sFieldName, value : value});
	}
	if(arr.length>0)json.SearchItem = arr;
	jsonIntoEle(oSearchXML, elRoot, json);
	var oArgs = {
		SearchXML   : oSearchXML,
		DocStatus	: $('_DOCSTATUS').value
	}
	return oArgs;
}
function date_trim(_sDate, _sTime) {
	if(!_sDate) {
		return "";
	}
	return _sDate.replace(/-(\d)$/, '-0$1').replace(/-(\d)-/, '-0$1-') + _sTime;
}
function forceReplain(el){
	Element.removeClassName(el, 'xx');
	Element.addClassName(el, 'xx');
}
</script>
</body>
</html>