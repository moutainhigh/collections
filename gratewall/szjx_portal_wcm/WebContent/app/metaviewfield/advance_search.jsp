<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_processor.jsp"%>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>

<%!
	MetaView gMetaView = null;
%>
<%
String sViewDesc = "";
String sViewInfoId = "";
String sTableName = "";
String sDBFieldName = "";
String sTrueTableName = "";
boolean bIsMutiTable = false;

int nViewId = 0;
List oMetaViewFields = null;
try{
	
	nViewId = processor.getParam("nViewId",0);
	MetaView oMetaView = MetaView.findById(nViewId);
	gMetaView = oMetaView;
	sViewDesc = oMetaView.getPropertyAsString("VIEWDESC");
	sViewInfoId = oMetaView.getPropertyAsString("VIEWINFOID");
	sTableName = oMetaView.getPropertyAsString("TABLENAME");
	sDBFieldName = oMetaView.getPropertyAsString("DBFIELDNAME");
	sTrueTableName = oMetaView.getTrueTableName();
	bIsMutiTable = oMetaView.isMultiTable();

%>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE WCMAnt:param="advance_search.jsp.title">检索数据</TITLE>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/core/SysOpers.js"></script>
<!--validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js" WCMAnt:locale="../../app/js/source/wcmlib/com.trs.validator/lang/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--floatpannel-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!-- CarshBoard Inner Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<!--calendar-->
<script type="text/javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js"></script>
<script type="text/javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js"  WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
<script type="text/javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js"></script>
<script type="text/javascript" src="../../app/js/source/wcmlib/calendar/CTRSDateTimeCompare.js"></script>
<link href="../../app/js/source/wcmlib/calendar/calendar_style/calendar-blue.css" rel="stylesheet" type="text/css" />
<!--page js-->
<script src="../../app/js/data/opers/metaviewdata.js"></script>
<script src="../../app/classinfo/ClassInfoSelector.js"></script>
<script language="javascript" src="advance_search.js" type="text/javascript"></script>
<link href="advance_search.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	function getPageParams(){
		return {
			viewId : <%=nViewId%>,
			tableName : '<%=sTrueTableName%>',
			isMutiTable : <%=bIsMutiTable%>
		};
	}
</script>

</HEAD>

<BODY>
<form name="objectForm" id="objectForm" style="width:100%;height:100%;overflow:visible;position:relative;">
	<input type="hidden" name="_dateStartSuffix_" value="_Start">
	<input type="hidden" name="_dateEndSuffix_" value="_End">
	<input type="hidden" name="_isAdvanceSearch_" value="true">
	<fieldset style="margin:10px 30px 10px 30px;"><legend WCMAnt:param="advance_search.jsp.query">检索字段:</legend>
		<div class="row">
			<span class="label">中文名称：</span>
			<span class="value">
				<input type="text" name="queryAnotherName" id="queryAnotherName" value="" validation="type:'string',max_len:'200',no_desc:''">
			</span>
		</div>
		<div class="row">
			<span class="label">英文名称：</span>
			<span class="value">
				<input type="text" name="queryFieldName" id="queryFieldName" value="" validation="type:'string',max_len:'200',no_desc:''">
			</span>
		</div>
		<div class="row">
			<span class="label">创建者：</span>
			<span class="value">
				<input type="text" name="CrUser" id="CrUser" value="" validation="type:'string',max_len:'200',no_desc:''">
			</span>
		</div>
		<div class="row">
			<span class="label">所属分组：</span>
			<span class="value">
				<select name="FieldGroupId" id="FieldGroupId" _value="-1">
					<option value="-1">--选择分组--</option>
				</select>
			</span>
		</div>
	</fieldset>
	<fieldset style="margin:10px 30px 10px 30px;"><legend WCMAnt:param="advance_search.jsp.query">其他属性检索字段:</legend>
		<div class="row" style="color:red;">是: 检索属性选中项;否: 检索属性未选中项;全部: 检索属性选中和未选中项</div>
		<div class="row">
			<span class="label" style="float:left;">不能为空：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="notNull_1" name="notNull" value="1" type="radio"><label for="notNull_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="notNull_0" name="notNull" value="0" type="radio"><label for="notNull_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="notNull_2" name="notNull" value="2" type="radio" checked="checked"><label for="notNull_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">不能编辑：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="notEdit_1" name="notEdit" value="1" type="radio"><label for="notEdit_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="notEdit_0" name="notEdit" value="0" type="radio"><label for="notEdit_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="notEdit_2" name="notEdit" value="2" type="radio" checked="checked"><label for="notEdit_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">隐藏字段：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="hiddenField_1" name="hiddenField" value="1" type="radio"><label for="hiddenField_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="hiddenField_0" name="hiddenField" value="0" type="radio"><label for="hiddenField_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="hiddenField_2" name="hiddenField" value="2" type="radio" checked="checked"><label for="hiddenField_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">URL字段：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="IsURLField_1" name="IsURLField" value="1" type="radio"><label for="IsURLField_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="IsURLField_0" name="IsURLField" value="0" type="radio"><label for="IsURLField_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="IsURLField_2" name="IsURLField" value="2" type="radio" checked="checked"><label for="IsURLField_2">全部</label>
				</span>
			</span>
		</div><div class="row">
			<span class="label" style="float:left;">概览显示：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="inOutline_1" name="inOutline" value="1" type="radio"><label for="inOutline_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="inOutline_0" name="inOutline" value="0" type="radio"><label for="inOutline_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="inOutline_2" name="inOutline" value="2" type="radio" checked="checked"><label for="inOutline_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">标题字段：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="titleField_1" name="titleField" value="1" type="radio"><label for="titleField_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="titleField_0" name="titleField" value="0" type="radio"><label for="titleField_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="titleField_2" name="titleField" value="2" type="radio" checked="checked"><label for="titleField_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">检索字段：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="searchField_1" name="searchField" value="1" type="radio"><label for="searchField_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="searchField_0" name="searchField" value="0" type="radio"><label for="searchField_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="searchField_2" name="searchField" value="2" type="radio" checked="checked"><label for="searchField_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">细缆显示：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="inDetail_1" name="inDetail" value="1" type="radio"><label for="inDetail_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="inDetail_0" name="inDetail" value="0" type="radio"><label for="inDetail_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="inDetail_2" name="inDetail" value="2" type="radio" checked="checked"><label for="inDetail_2">全部</label>
				</span>
			</span>
		</div>
		<div class="row">
			<span class="label" style="float:left;">唯一标识：</span>
			<span class="value" style="margin-left:80px;display:block;">
				<span class="input_radio_container">
					<input id="identityField_1" name="identityField" value="1" type="radio"><label for="identityField_1">是</label>
				</span>
				<span class="input_radio_container">
					<input id="identityField_0" name="identityField" value="0" type="radio"><label for="identityField_0">否</label>
				</span>
				<span class="input_radio_container">
					<input id="identityField_2" name="identityField" value="2" type="radio" checked="checked"><label for="identityField_2">全部</label>
				</span>
			</span>
		</div>
	</fieldset>
	<div class="row">
		<span class="value" style="color:red;padding-left:30px;">
			<input type="checkbox" name="mode_query" id="mode_query_isOr" value="" ignore="true" style="width:auto;border:0px;">
			<label for="mode_query_isOr" WCMAnt:param="advance_search.jsp.comToOr">将所有条件组合成Or</label>
		</span>
	</div>
	<div id="validTip" class="validTip"></div>
	<!-- 使此处的multiValueContainer相对于form（relative）来定位从而保持此块不随滚动条滚动。 -->
	<div id="multiValueContainer" class="multiValueContainer" style="display:none;">
		<div id="multiValueContainerInner" class="multiValueContainerInner"></div>
		<div class="commandBtn">
			<button type="button" onclick="SelectMulitValue_onOk();" WCMAnt:param="advance_search.jsp.suer">确定</button>
			<button type="button" onclick="SelectMulitValue_onCancel();" WCMAnt:param="advance_search.jsp.cancel">取消</button>
		</div>
	</div>
	<iframe id="multiValueShield" src="../base/blank.html" frameborder="no" class="multiValueShield" style="display:none;"></iframe>
</form>

</BODY>
</HTML>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException("metaviewdata_query.jsp运行期异常!", tx);
}
%>

