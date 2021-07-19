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
<%@include file="element_processor.jsp"%>

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
	MetaViewData obj = (MetaViewData) processor.excute("wcm61_MetaViewData", "findViewDataById");
	MetaView oMetaView = obj.getMetaView();
	nViewId = oMetaView.getId();
	IMetaDataDefCacheMgr m_oDataDefCacheMgr = (IMetaDataDefCacheMgr) DreamFactory
            .createObjectById("IMetaDataDefCacheMgr");
	oMetaViewFields = m_oDataDefCacheMgr.getSortedMetaViewFields(nViewId);

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
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
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
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
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
			tableName : '<%=CMyString.filterForJs(sTrueTableName)%>',
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

<%
	int nFieldType = 0;
	int nDBLength = 0;
	int nDBType = 0;
	String sFieldName = "";
	String sAnotherName = "";
	String sEnumValue  = "";
	String sFieldValue ="";
	
	
	for(int i = 1;i <= oMetaViewFields.size(); i++ ){
		MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.get(i - 1);
		if (oMetaViewField == null)
			continue;
		if(oMetaViewField.isSearchField()){
			sFieldName = oMetaViewField.getPropertyAsString("DBFIELDNAME");
			nDBType = oMetaViewField.getPropertyAsInt("DBTYPE", 0);
			sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME");
			nFieldType = oMetaViewField.getPropertyAsInt("FIELDTYPE", 0);
			nDBLength = oMetaViewField.getPropertyAsInt("DBLENGTH", 0);
			sEnumValue = oMetaViewField.getPropertyAsString("ENMVALUE");
			//To add  more field and status 
%>
			<div class="row">
<%
			switch(nFieldType){
				case MetaDataConstants.FIELD_TYPE_NORMALTEXT:
					%>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
							<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'string',max_len:'<%=nDBLength%>',no_desc:''">
						</span>
					<%
					break;
				case MetaDataConstants.FIELD_TYPE_PASSWORD:
					%>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
							<input type="password" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'string',max_len:'<%=nDBLength%>',no_desc:''">
						</span>
					<%
					break;
				case MetaDataConstants.FIELD_TYPE_MULTITEXT:
					%>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
							<input name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>"		validation="type:'string',max_len:'<%=nDBLength%>',no_desc:''">
							<br>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_TRUEORFALSE:
					 %>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
						<span class='input_radio_container'>
							<input type=radio name='<%=sFieldName%>' id='<%=sFieldName%>_1' value='1' />
							<label for='<%=sFieldName%>_1' WCMAnt:param="advance_search.jsp.yes">是</label>
							<input type=radio name='<%=sFieldName%>' id='<%=sFieldName%>_0' value='0' />
							<label for='<%=sFieldName%>_0' WCMAnt:param="advance_search.jsp.no">否</label>
						</span>
						<span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_RADIO:
					 %>
						<span class="label" style="float:left;"><%=sAnotherName%>：</span>
						<span class="value" style="margin-left:80px;display:block;">
							<%out.print(dealWith_radio(oMetaViewField,null));%>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_CHECKBOX:
					 %>
						<span class="label" style="float:left;"><%=sAnotherName%>：</span>
						<span class="value" style="margin-left:80px;display:block;">
							<%out.print(dealWith_checkbox(oMetaViewField,null));%>
							<div style="color:red;display:block;">
								<input type="checkbox" ignore="true" name="mode_query" id="mode_<%=sFieldName%>" style="width:auto;border:0px;">
								<label for="mode_<%=sFieldName%>" WCMAnt:param="advance_search.jsp.condition">组合成And条件</label>
							</div>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_SELECT:
				case MetaDataConstants.FIELD_TYPE_INPUT_SELECT:
				case MetaDataConstants.FIELD_TYPE_SUGGESTION: 
					boolean isInputable = true;
					if(nFieldType == MetaDataConstants.FIELD_TYPE_SELECT){
						isInputable = false;
					}
					 %>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value ext-ie6">
						<div style="display:inline;" _type="inputselect" _attachElement="<%=sFieldName%>">
						<a name="controller"></a>
						</div>
						<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" class="inputSelect_input">
						<span class="inputSelect_span">
							<select id="<%=sFieldName%>_sel" class="inputSelect_select" _attachElement="<%=sFieldName%>"><%=getInputSelectHTML(oMetaViewField, false)%>
							</select>
						</span>
						<a name="selectMultiValue" _attachElement="<%=sFieldName%>" class="selectMultiValueA"></a>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_TIMESTAMP:
					 %>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
								<span WCMAnt:param="advance_search.jsp.from">从</span>
								<span>
									<script language="javascript">
										TRSCalendar.render({
											id : '<%=sFieldName%>' + '_Start',
											//value :  defaultTime(true),
											timeable : false,
											required : false,
											submit : true
										});
									</script>
								</span>
								<span WCMAnt:param="advance_search.jsp.to">至</span>
								<span>
									<script language="javascript">
										TRSCalendar.render({
											id : '<%=sFieldName%>' +'_End',
											//value :  defaultTime(true),
											timeable : false,
											required : false,
											submit : true
										});
									</script>
								</span>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_CLASS:
					 %>
						<span class="label" style="float:left;clear:left;"><%=sAnotherName%>：</span>
						<span class="value">
							<%out.print(delaWith_class(oMetaViewField,null));%>
							<div style="margin-left:80px;color:red;display:block;clear:left;">
								<input type="checkbox" ignore="true" name="mode_query" id="mode_<%=sFieldName%>" style="width:auto;border:0px;">
								<label for="mode_<%=sFieldName%>" WCMAnt:param="advance_search.jsp.condition">组合成And条件</label>
							</div>
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_HTML_CHAR:
					 %>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
							<input name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'string',max_len:'<%=nDBLength%>',no_desc:''">
						</span>
					 <%
					break;
				case MetaDataConstants.FIELD_TYPE_SELFDEFINE:
					 %>
						<span class="label"><%=sAnotherName%>：</span>
						<span class="value">
						<%
						switch(nDBType){
							case 12:
						%>
							<!--自定义类型：字符串-->
							<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'string',max_len:'<%=nDBLength%>',no_desc:''">
							<%
							break;
							case 4:
							%>
							<!--自定义类型：整数-->
							<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'int',no_desc:''">
							<%
							break;
							case 6:
							%>
							<!--自定义类型：小数-->
							<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'float',no_desc:''">
							<%
							break;
							case 8:
							%>
							<!--自定义类型：双精度-->
							<input type="text" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" validation="type:'double',no_desc:''">
							<%
							break;
							case 93:
							%>
							<!-- 自定义时间戳类型-->
							<span WCMAnt:param="advance_search.jsp.from">从</span>
							<span>
								<script language="javascript">
									TRSCalendar.render({
										id : '<%=sFieldName%>' + '_Start',
										//value :  defaultTime(true),
										timeable : true,
										required : false,
										submit : true
									});
								</script>
							</span>
							<span WCMAnt:param="advance_search.jsp.to">至</span>
							<span>
								<script language="javascript">
									TRSCalendar.render({
										id : '<%=sFieldName%>' +'_End',
										//value :  defaultTime(true),
										timeable : true,
										required : false,
										submit : true
									});
								</script>
							</span>
						<%	
							break;			
						}
						%>
						</span>
						<%
					break;
				default:
					out.println("");

			}
%>
				
			</div> 
<%
		}
	}
%>
		<div class="row">
			<div class="label" style="float:left;" WCMAnt:param="advance_search.jsp.conditionAdd">额外条件：</div>
			<div class="value" style="margin-left:80px;display:block;">
				<input type="text" name="_sqlWhere_" id="_sqlWhere_" value="" style="width:350px;" title="额外条件写法如：field1=a or field2 like '%aa%'" WCMAnt:paramattr="title:advance_search.jsp.conditionAddTip">
				<button type="button" onclick="showViewFields(this, '_sqlWhere_');return false;">...</button>
				<div id="viewFields" style="display:none;"></div>
			</div>
		</div>    
		<div class="row">
			<span class="label"></span>
			<span class="value">
				<input type="checkbox" name="mode_query" id="mode_query_isOr" value="" ignore="true" style="width:auto;border:0px;">
				<label for="mode_query_isOr" WCMAnt:param="advance_search.jsp.comToOr">将所有条件组合成Or</label>
			</span>
		</div>    
        <FIELDSET style="margin:10px 30px 10px 30px;"><legend WCMAnt:param="advance_search.jsp.queryrange">检索范围:</legend>
		<div style="margin:10px;display:block;">
			<input type="radio" class="queryType" name="_queryType_" id="queryType0" value="0" checked><label for="queryType0" WCMAnt:param="advance_search.jsp.localChannelOnly">只包含本栏目</label>
		</div>
		<div style="margin:10px;display:block;">
			<input type="radio" class="queryType" name="_queryType_" id="queryType1" value="1"><label for="queryType1" WCMAnt:param="advance_search.jsp.localAll">包含本栏目及本栏目下具有相同视图的子栏目</label>
		</div>
		<div style="margin:10px;display:block;">
			<input type="radio" class="queryType" name="_queryType_" id="queryType2" value="2"><label for="queryType2" WCMAnt:param="advance_search.jsp.viewsAll">包含所有具有相同视图的栏目</label>
		</div>
        </FIELDSET>
		<div id="copyToClipBoard" WCMAnt:param="advance_search.jsp.copytoBoard">复制参数到剪贴板</div>
	<div id="validTip" class="validTip"></div>
	<!-- 使此处的multiValueContainer相对于form（relative）来定位从而保持此块不随滚动条滚动。 -->
	<div id="multiValueContainer" class="multiValueContainer" style="display:none;">
		<div id="multiValueContainerInner" class="multiValueContainerInner"></div>
		<div class="commandBtn">
			<button type="button" onclick="SelectMulitValue_onOk();" WCMAnt:param="advance_search.jsp.suer">确定</button>
			<button type="button" onclick="SelectMulitValue_onCancel();" WCMAnt:param="advance_search.jsp.cancel">取消</button>
		</div>
	</div>
	<iframe id="multiValueShield" src="page/blank.html" frameborder="no" class="multiValueShield" style="display:none;"></iframe>
</form>

</BODY>
</HTML>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("advance_search.runExce","metaviewdata_query.jsp运行期异常!"), tx);
}
%>