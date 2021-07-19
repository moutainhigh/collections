<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameter" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameters" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameterType" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameterTypes" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetConstants" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>


<%@include file="../../include/public_server.jsp"%>

<%
	int nWidgetParameterId = currRequestHelper.getInt("objectid",0);
	int nWidgetId = currRequestHelper.getInt("widgetId",0);
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nWidgetId),WCMTypes.getLowerObjName(Widget.OBJ_TYPE)}));
	}
	String sWidgetName = CMyString.transDisplay(currWidget.getWname());
	WidgetParameter currWidgetParameter = null;
	if(nWidgetParameterId>0){
		currWidgetParameter = WidgetParameter.findById(nWidgetParameterId);
		if(currWidgetParameter == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nWidgetParameterId),WCMTypes.getLowerObjName(WidgetParameter.OBJ_TYPE)}));
		}
		if(!SpecialAuthServer.hasRight(loginUser, currWidget, SpecialAuthServer.WIDGET_EDIT)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("widgetparameter_addedit.modify.noRight","您没有权限修改资源变量。"));
		}
	}else{
		currWidgetParameter= WidgetParameter.createNewInstance();
		if(!SpecialAuthServer.hasRight(loginUser, currWidget, SpecialAuthServer.WIDGET_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("widgetparameter_addedit.create.noRight","您没有权限新建资源变量。"));
		}
	}
	String sParamName = CMyString.filterForHTMLValue(currWidgetParameter.getWidgetParamName());
	String sParamDesc = CMyString.filterForHTMLValue(currWidgetParameter.getWidgetParamDesc());
	String sEnumValue = CMyString.filterForHTMLValue(currWidgetParameter.getEnmvalue());
	String sDefaultValue = CMyString.filterForHTMLValue(currWidgetParameter.getDefaultValue());
	int nClassId = currWidgetParameter.getClassId();
	int nParamType = currWidgetParameter.getWidgetParamType();
	int nNotNull = currWidgetParameter.getNotnull();
	int nNotEdit = currWidgetParameter.getNotEdit();
	int nRadOrCkx = currWidgetParameter.getRadorchk();

%><%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<TITLE WCMAnt:param="fieldinfo_add_edit.jsp.newormodifyresourcevarible">新建/修改资源变量</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/widgets.css" rel="stylesheet" type="text/css" />	
<link href="./widgetparameter_addedit.css" rel="stylesheet" type="text/css" />
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<script src="../../classinfo/ClassInfoSelector.js"></script>
<script src="../../js/source/wcmlib/pagecontext/BubblePanel.js"></script>
<!--validator start-->
<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<!--validator end-->
<!-- page js -->
<script src="./widgetparameter_addedit.js"></script>

</head>

<body>
<form name="ObjectForm" id="ObjectForm" style="width:100%;height:100%;overflow:auto;">
    <input type="hidden" name="objectId" id="objectId" value="<%=nWidgetParameterId%>">
    <input type="hidden" name="WIDGETID" id="WIDGETID" value="<%=nWidgetId%>">
    <div id="objectContainer">
		<div class="row">
			<span class="label" WCMAnt:param="widgetparameter_addedit.res.name">资源名称：</span>
			<span class="value" id="widgetName"><%=sWidgetName%></span>
		</div>    
		<div class="row" style="height:36px;">
			<span class="label" WCMAnt:param="widgetparameter_addedit.var.name">变量名称：</span>
			<span class="value">
				<input class="" type="text" name="widgetparamName" id="widgetparamName" oldValue="<%=sParamName%>" value="<%=sParamName%>" validation="type:'string',required:'true',max_len:'30',format:'/^[^\$\[]*$/',desc:'变量名称',rpc:checkWidgetParamName,showid:'wpNameVal'" validation_desc="变量名称" WCMAnt:paramattr="validation_desc:widgetparameter_addedit.var.nameattr">
			</span><br>
			<span id="wpNameVal" style="margin-left:90px;"></span>
		</div>    
		<div class="row" style="height:36px;">
			<span class="label" WCMAnt:param="widgetparameter_addedit.var.name.show" >变量显示名称：</span>
			<span class="value">
				<input type="text" name="widgetparamDesc" id="widgetparamDesc" value="<%=sParamDesc%>" validation="type:'string2',required:'true',max_len:'60',desc:'显示名称',showid:'wpDescVal'">
			</span><br>
			<span id="wpDescVal" style="margin-left:90px;"></span>
		</div>    
		<div class="row">
			<span class="label" WCMAnt:param="widgetparameter_addedit.var.name.type">变量类型：</span>
			<span class="value">
				<select name="widgetParamType" id="widgetParamType" initValue="<%=nParamType%>" >
					<%
						WidgetParameterTypes oWidgetParameterTypes = WidgetConstants.WidgetParameter_TYPES;
						for(int i = 0, length = oWidgetParameterTypes.getParameterTypesCount(); i < length; i++){
							WidgetParameterType oWidgetParameterType = oWidgetParameterTypes.getWidgetParameterTypeAt(i);
							out.println("<option _value='" + oWidgetParameterType.getKey() + "' value='" + oWidgetParameterType.getWidgetParameterType() + "'>" + oWidgetParameterType.getWidgetParameterTypeDesc() + "</option>");
						}
					%>
				</select>
			</span>
		</div>  
		<div class="row" id="enmValueContainer" style="display:none;">
			<span class="label" WCMAnt:param="widgetparameter_addedit.var.name.list">变量枚举值：</span>
			<span class="value">
				<input type="text" name="enmValue" id="enmValue"   value="<%=sEnumValue%>" validation="type:'string',max_len:'160',desc:'枚举值'" onmouseover="showBubblePanel();" onmouseout="hideBubblePanel();" >
			</span>
			<div id="bubblePanel" style="display:none;border:solid black 1px">
				<div WCMAnt:param="fieldinfo_add_edit.jsp.toptip1">格式为:item~item</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip2">其中item为：label`value或label</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip3">如:中国`1~美国`2~英国`3</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip4">中国~美国~英国</div><br><font color='red' WCMAnt:param="fieldinfo_add_edit.jsp.doubletogeneratepage">双击打开构造页面</font>
			</div>
		</div>    
		<div class="row" id="classIdContainer" style="display:none;">
			<span class="label" WCMAnt:param="widgetparameter_addedit.assort">分类法ID：</span>
			<span class="value">
				<input type="text" name="classId" id="classId" disabled value="<%=nClassId%>" validation="showid:'validTip',rpc:'checkClassInfoValid'">
				<span class="selectClassInfo" id="selectClassInfo">&nbsp;&nbsp;&nbsp;</span><span id="validTip" class="validTip"></span>
			</span>
		</div>        
		<div class="row" id="defaultValueContainer">
			<span class="label" WCMAnt:param="widgetparameter_addedit.var.name.acquiesce">变量默认值：</span>
			<span class="value">
				<input type="text" name="defaultValue" id="defaultValue" value="<%=sDefaultValue%>" validation="type:'string',max_len:'60',desc:'默认值',showid:'defaultValidTip'" validation_desc="默认值" WCMAnt:paramattr="validation_desc:widgetparameter_addedit.var.acquiesce"><span class="plainTip" id="plainTip"></span><span class="selectClassInfo" id="defaultSelectClassInfo">&nbsp;&nbsp;&nbsp;</span><span class="selectChannel" id="defaultSelectChannel" Style="dispaly:none;">&nbsp;&nbsp;&nbsp;</span><span id="defaultValidTip" class="validTip"></span>
			</span>
		</div>
		<div id="bubblePanel2" style="display:none;border:solid black 1px">
			<font color='red' WCMAnt:param="fieldinfo_add_edit.jsp.doubletogeneratepage">双击打开构造页面</font>
		</div>
		<div class="row">
			<span class="label">&nbsp;</span>
			<span class="value">			
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notNull" id="notNull" initValue="<%=nNotNull%>" onclick="setNotNullEvent(this);">
			</span><label for="notNull" WCMAnt:param="widgetparameter_addedit.notNull">不能为空</label>			
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notEdit" id="notEdit" initValue="<%=nNotEdit%>">
				</span><label for="notEdit" WCMAnt:param="widgetparameter_addedit.notEdit">不能编辑</label>
			</span>	
			<span style="margin-left:5px;">
				<span id="radioOrCheckContainer" style="display:none;">
					<input isBoolean="true" value="1|0" class="input_checkbox" type="checkbox" name="RADORCHK"  id="RADORCHK" initValue="<%=nRadOrCkx%>"><label for="RADORCHK" WCMAnt:param="widgetparameter_addedit.check">单选树</label>
				</span>
			</span>	
			</span>
		</div>
		
	</div>
</form>

</body>
</html>