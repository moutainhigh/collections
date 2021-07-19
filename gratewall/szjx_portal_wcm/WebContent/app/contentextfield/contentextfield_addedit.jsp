<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.cms.content.ExtendedFields" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	ContentExtField currContentExtField = null;
	ExtendedFields currContentExtFields = null;
	ExtendedField newContentExtField = null;
	String sContentExtFieldTypeName = "";
	String sEnumValue = "";
	String sDBscale = "2";
	if(nObjectId == 0){
		currContentExtField = ContentExtField.createNewInstance();
	}else{
		currContentExtField = ContentExtField.findById(nObjectId);
		//参数校验
		if(currContentExtField == null){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjectId),WCMTypes.getLowerObjName(ContentExtField.OBJ_TYPE)}));
		}
		newContentExtField = ExtendedField.findById(currContentExtField.getExtFieldId());
		sContentExtFieldTypeName = CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("FIELDTYPE"));
		sContentExtFieldTypeName = (sContentExtFieldTypeName == "" ? "1": sContentExtFieldTypeName);
		sDBscale = CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("DBSCALE"));
		sEnumValue =  CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("ENMVALUE"));
	}
	int nHostId = currRequestHelper.getInt("HostId",0);
	int nHostType = currRequestHelper.getInt("HostType",0);
	String sContentExtFieldName = CMyString.filterForHTMLValue(currContentExtField.getName());
	String sContentExtFieldOutName = CMyString.filterForHTMLValue(currContentExtField.getDesc());
	String sContentExtFieldDBTypeName = CMyString.filterForHTMLValue(currContentExtField.getTypeDesc());
	String sContentExtFieldDefaultValue = CMyString.filterForHTMLValue(currContentExtField.getExtFieldProperty("FIELDDEFAULT"));
	int nContentExtFieldLength =  currContentExtField.getMaxLength();
	if(nContentExtFieldLength == 0) nContentExtFieldLength = 1;
	//遍历取当前系统已有的扩展字段
	String sWhere = "not exists (select ExtFieldId from WCMContentExtField where WCMExtField.ExtFieldId=WCMContentExtField.ExtFieldId and ObjType=? and ObjId=?) and TableName='WCMDOCUMENT'";
	WCMFilter filter = null;
	filter = new WCMFilter("WCMEXTFIELD", sWhere, "FIELDTYPE","");
	filter.addSearchValues(0, nHostType);
	filter.addSearchValues(1, nHostId);
	try{
		currContentExtFields = ExtendedFields.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
	    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("object.failed.found", "获取扩展字段集合失败!"),e);
	}
	//获取当前列已有的扩展字段数
	int nDocOrder = 0;
	
	if(nObjectId != 0){
		nDocOrder = currContentExtField.getPropertyAsInt("EXTORDER",0);
	}
	DBManager currDBManager = DBManager.getDBManager();
	if(nHostType == 101 && nObjectId == 0){
		String sSql = "SELECT MAX(extorder) FROM " + ContentExtField.DB_TABLE_NAME
				+ " WHERE objtype=101 and objid=" + nHostId;
		nDocOrder = currDBManager.sqlExecuteIntQuery(sSql) + 1;
	}

%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="contentextfield_addedit.jsp.title">扩展字段列表</title>
<!--FloatPanel Inner Start-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/contentextfield.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!--my import-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--carshboard-->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!--locker-->
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<!--page scope-->
<script label="PageScope" src="contentextfield_addedit.js"></script>
<SCRIPT src="../../app/js/easyversion/bubblepanel.js"></SCRIPT>
<link href="contentextfield_addedit.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" WCMAnt:locale="./contentextfield_addedit_$locale$.css"  href="./contentextfield_addedit_cn.css" />
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'saveExtField',
		name : wcm.LANG.CONTENTEXTFIELD_CONFIRM_10 || '确定'
	}],
	size : [560, 300]
};
</script>
<!--FloatPanel Inner End-->
</head>
<body class="editbody">
<form style="margin:0px" id="addEditForm" name="addEditForm" method="post" action="">
<input type="hidden" name="ContainsChildren" id="ContainsChildren" value="false">

<div id="create_area" name="create_area">
	<div id="createContainer" name="createContainer" style="height:450px;">
		<input type="hidden" name="ObjectId" id="ContentExtFieldId" value="<%=nObjectId%>">
		<input type="hidden" name="HostId" id="HostId" value="<%=nHostId%>">
		<input type="hidden" name="HostType" id="HostType" value="<%=nHostType%>">
		<input type="hidden" name="ExtOrder" id="ExtOrder" value="<%=nDocOrder%>">
		<div style="padding-left:2px">
			<div class="middle_register" justReplace="true">			
				<div class="middle_sash_register_right"></div>
				<div class="forme_arrowhead_down"></div>
				<div class="middle_sash_register_left"></div>
				<div WCMAnt:param="contentextfield_addedit.jsp.attr" class="middle_register_title_text">字段属性</div>	
			</div>
		</div>
		<div class="full_register">
			<div class="middle_register_text1" justReplace="true">				
				<div>
					<span WCMAnt:param="contentextfield_addedit.jsp.objectName" class="sAttrClue">英文名称：</span>
					<%
						if(nObjectId == 0){
					%>
					<span class="text_txt combo_cnt">
						<input name="FieldName" id="FieldName" type="text" class="kuang_as combo_input" value="" onkeydown="if(event.keyCode==13) return false;" validation="type:'common_char2',required:'',max_len:'25',desc:'字段名称',rpc:'checkFieldName',showid:'FieldName_valid'" validation_desc="字段名称" WCMAnt:paramattr="validation_desc:contentextfield_addedit.jsp.objectNameAttr"/>
						<span class="combo_icon">
							<select id="selFieldInfo" tabindex="-1"><!-- 去掉style="width:200px",使下拉菜单可以显示完整最大长度的英文名称的信息 -->
								<option WCMAnt:param="contentextfield_addedit.jsp.exist" value="-1">&nbsp;&nbsp;---系统已有扩展字段---</option>			<%
								if(currContentExtFields.size() > 0){
									for(int i=0;i < currContentExtFields.size();i++){
										newContentExtField = (ExtendedField) currContentExtFields.getAt(i);	
										if(newContentExtField == null) continue;
										int sMaxLength = newContentExtField.getMaxLength();
										if(sMaxLength==0) sMaxLength =8;
								%>
								<option _fieldName="<%=CMyString.filterForHTMLValue(newContentExtField.getName())%>" _dataType="<%=CMyString.filterForHTMLValue(newContentExtField.getTypeName())%>" 
								_fieldType="<%=CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("FIELDTYPE"))%>"
								_fieldLength="<%=newContentExtField.getMaxLength()%>" _defaultValue="<%=CMyString.filterForHTMLValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"))%>"
								_scale="<%=CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("DBSCALE"))%>" _enum="<%=CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("ENMVALUE"))%>" value="<%=CMyString.filterForHTMLValue(newContentExtField.getTypeDesc())%>"><label width="100px;"><%=CMyString.filterForHTMLValue(newContentExtField.getName())%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=CMyString.filterForHTMLValue(newContentExtField.getTypeDesc())%>&nbsp;&nbsp;&nbsp;<span WCMAnt:param="contentextfield_addedit.jsp.length">长度</span>=<%=sMaxLength%>
								</option>
								<%
									}
								}
								%>
							</select>
						</span>
					</span>
					<button  type="button" WCMAnt:param="contentextfield_addedit.jsp.cancelSelect" style="display:none" WCMAnt:paramattr="title:contentextfield_addedit.jsp.cancelSelected" id="cancelFieldSelected" title="取消使用已有字段的选择" class="combo_cancelselect">取消选择</button>
					<br/><span id="FieldName_valid" style="position:absolute;left:13px;top:48px;height:18px;width:410px;">&nbsp;</span>
					<%}else{%>
						<input name="FieldName" id="FieldName" type="text" class="kuang_as" style="width:150px;" value="<%=sContentExtFieldName%>" disabled="true"></input>
					<%}%>
				</div>
				<div style="width:1px;">&nbsp;</div>
				<div>
					<span WCMAnt:param="contentextfield_addedit.jsp.outName" class="spAttrClue">中文名称：</span>
					<span class="text_txt">
						<input name="FieldDesc" id="FieldDesc" type="text" class="kuang_as" onkeydown="if(event.keyCode==13) return false;" value="<%=sContentExtFieldOutName%>" style="width:150px;" validation="type:'string',required:'',max_len:'50',desc:'显示名称'" validation_desc="显示名称" WCMAnt:paramattr="validation_desc:contentextfield_addedit.jsp.outNameAttr"></input>				
					</span>
				</div>
				<div>					
					<span WCMAnt:param="contentextfield_addedit.jsp.objectType" class="spAttrClue">字段类型：</span>	
					<select name="FieldType" id="FieldType" class="kuang_as" style="width:150px;" initValue="<%=sContentExtFieldTypeName%>">
						<option WCMAnt:param="contentextfield_addedit.jsp.normaltext" value="3" _type="normaltext" _maxLength="200">普通文本</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.password" value="2" _type="password" _maxLength="50">密码文本</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.multitext" value="4" _type="multitext" _maxLength="1000">多行文本</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.radio" value="6" _type="radio" _maxLength="50">单选框</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.checkbox" value="8" _type="checkbox" _maxLength="50">多选</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.select" value="7" _type="select" _maxLength="50">下拉列表</option>
						<!--<option WCMAnt:param="contentextfield_addedit.jsp.inputselect" value="0" _type="inputselect" _maxLength="50">可输入下拉列表</option>-->
						<option WCMAnt:param="contentextfield_addedit.jsp.timetemp" value="9" _type="timestamp" _maxLength="0">时间</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.editor" value="10" _type="editor" _maxLength="1000">简易编辑器</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.selfdefine" value="1" _type="selfdefine" _maxLength="200">自定义类型</option>						
					</select>										
				</div>
				<div style="display:none;" id="enmValueContainer">
					<span WCMAnt:param="contentextfield_addedit.jsp.enum" class="spAttrClue">枚举数值：</span>
					<span class="text_txt">
						<input name="enmValue" id="enmValue" type="text" class="kuang_as" style="width:150px;" onkeydown="if(event.keyCode==13) return false;" value="<%=sEnumValue%>" validation="type:'string',max_len:'200',no_desc:''" onmouseover="showBubblePanel();" onmouseout="hideBubblePanel();"></input>	
					</span>
					<div id="bubblePanel" style="border:solid black 1px;position:absolute;left:280px;text-align:left;padding-left:5px;display:none;">
					<table style="font-size:12px;">
					<tr><td WCMAnt:param="contentextfield_addedit.jsp.toptip1">格式为：</td><td>item~item</td></tr>
					<tr><td WCMAnt:param="contentextfield_addedit.jsp.toptip2">其中item为：</td><td>label`value or label</td></tr>
					<tr><td WCMAnt:param="contentextfield_addedit.jsp.toptip6">如：</td><td WCMAnt:param="contentextfield_addedit.jsp.toptip3">中国`1~美国`2~英国`3</td></tr>
					<tr><td></td><td WCMAnt:param="contentextfield_addedit.jsp.toptip4">中国~美国~英国</td></tr></table>
					<font color='red'><div WCMAnt:param="contentextfield_addedit.jsp.toptip5">双击打开构造页面</div></font>
					</div>
				</div>
				<div>
					<span WCMAnt:param="contentextfield_addedit.jsp.tableType" class="spAttrClue">库中类型：</span>
						<select name="dbType" id="dbType" class="kuang_as" style="width:150px;" initValue="<%=sContentExtFieldDBTypeName%>">
						<option WCMAnt:param="contentextfield_addedit.jsp.optinVarchar" value="NVARCHAR" _type="char" _maxLength="1000">文本型</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.optionInteger" value="INT" _type="int" _maxLength="4">整数型</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.optionTime" value="DATETIME" _type="timeStamp" _maxLength="8">时间型</option>
						<option WCMAnt:param="contentextfield_addedit.jsp.optionDecimal" value="FLOAT" _type="float" 
						_maxLength="8">小数型</option>				
					</select>		
				</div>
				<div id="dbLengthContainer" style="display:none;">
					<span WCMAnt:param="contentextfield_addedit.jsp.objectLength" class="spAttrClue">库中长度：</span>
					<span class="text_txt">
						<input name="dbLength" type="text" class="kuang_as" style="width:150px;" id="dbLength" onkeydown="if(event.keyCode==13) return false;" validation="type:'int',required:'true',value_range:'<%=nContentExtFieldLength%>,2000',showid:'ValidatorMsg'" validation_desc="库中长度" WCMAnt:paramattr="validation_desc:contentextfield_addedit.jsp.objectLengthAttr" firstLoaded="true" value="<%=nContentExtFieldLength%>"></input>
					</span>
					<span id="ValidatorMsg" style="height:14px">&nbsp;</span>
				</div>
				<div id="dbScaleContainer" style="display:none;">
					<span WCMAnt:param="contentextfield_addedit.jsp.decimalLength" class="spAttrClue">小数位数：</span>
					<span class="text_txt">
						<input type="text" name="dbScale" id="dbScale" value="<%=sDBscale%>" style="width:150px;" onkeydown="if(event.keyCode==13) return false;" validation="type:'int',value_range:'<%=sDBscale%>,18',required:'true',showid:'scaleMsg',no_desc:''">
					</span>
					<span id="scaleMsg" style="height:14px;margin-left:8px;">&nbsp;</span>
				</div>
				<div id="defaultValueContainer">
					<span WCMAnt:param="contentextfield_addedit.jsp.defaultValue" class="spAttrClue">字段默认值：</span>
					<span class="text_txt">
						<input type="text" name="defaultValue" id="defaultValue" style="width:150px;" value="<%=sContentExtFieldDefaultValue%>" class="kuang_as" onkeydown="if(event.keyCode==13) return false;">&nbsp;&nbsp;&nbsp;</span><span id="defaultValidTip" class="validTip"></span>
					</span>
				</div>
				<div>
					<span style="padding-left:10px;padding-bottom:5px;cursor:hand;">
						<input type="checkbox" id="syncchildren" ><label WCMAnt:param="contentextfield_addedit.jsp.syn" for="syncchildren">同步创建到子对象</label>
					</span>
				</div>
			</div>		
		</div>
	</div>
</div>
</form>
<script language="javascript">
<!--
	ValidationHelper.initValidation();
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('saveExtField', false);
	}, "addEditForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveExtField', true);
	}, "addEditForm");
//-->
</script>
</body>
</html>
<%!
	private String display(String sOriginalValue){
		if(sOriginalValue == null || sOriginalValue.trim().equals(""))return LocaleServer.getString("contentextfield.label.selfdefine", "自定义类型");
		switch(Integer.parseInt(sOriginalValue)){
			case 1:
				return LocaleServer.getString("contentextfield.label.selfdefine", "自定义类型");
			case 2:
				return LocaleServer.getString("contentextfield.label.password", "密码文本");
			case 3:
				return LocaleServer.getString("contentextfield.label.normaltext", "普通文本");
			case 4:
				return LocaleServer.getString("contentextfield.label.multitext", "多行文本");
			case 7:
				return LocaleServer.getString("contentextfield.label.select", "下拉框");
			case 6:
				return LocaleServer.getString("contentextfield.label.radio", "单选框");
			case 8:
				return LocaleServer.getString("contentextfield.label.checkbox", "多选框");
			case 9:
				return LocaleServer.getString("contentextfield.label.timetemp", "时间");
			case 0:
				return LocaleServer.getString("contentextfield.label.inputselect", "可输入下拉框");
			default	:
				return LocaleServer.getString("contentextfield.label.undefined", "未知类型");
		}
	}
%>