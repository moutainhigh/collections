<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup"%>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.right.AuthServerMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../include/public_processor.jsp"%>
<%
try{
	int nObjectId = processor.getParam("objectid", 0);

	MetaViewField oMetaViewField = (MetaViewField) processor.excute("wcm61_MetaViewField", "jFindById");

	int     nViewId = oMetaViewField.getViewId();
 	MetaView oMetaView = MetaView.findById(nViewId);
	String  sViewDesc = oMetaView.getDesc();

	String  sFieldName = oMetaViewField.getName();
	String  sAnotherName = oMetaViewField.getAnotherName();
	String  sLocateChannel = CMyString.showNull(oMetaViewField.getPropertyAsString("LocateChannel"));
	String  sEnmValue = oMetaViewField.getEnmValue();

	MetaDBField oMetaDBField = oMetaViewField.getDBField();
	String sDBFieldEnmValue = "";
	if(oMetaDBField != null) 
		sDBFieldEnmValue = oMetaDBField.getEnmValue();
	String   sDefaultValue = oMetaViewField.getDefaultValue();
	String   sValidator = CMyString.showNull(oMetaViewField.getValidator());
	int  nFieldType = oMetaViewField.getType();
	int  nClassId = oMetaViewField.getClassId();
	String sClassId = "";
	if(nClassId!=0)sClassId=nClassId+"";
	int  nDBType = oMetaViewField.getDBType();
	int  nDBLength = oMetaViewField.getLength();
	int  nDBScale = oMetaViewField.getScale();
	int  nNotNull = oMetaViewField.isNotNull() == true ? 1 :0;
	int  nNotEdit = oMetaViewField.getPropertyAsBoolean("notedit", false) == true ? 1 :0;
	int  nHiddenField = oMetaViewField.getPropertyAsBoolean("HiddenField",false) == true ? 1 :0;
	int  nIsURLField = oMetaViewField.getPropertyAsBoolean("IsURLField",false) == true ? 1 :0;
	int  nRadorchk = oMetaViewField.isRadioOrCheck() == true ? 1 :0;
	int  nInOutline = oMetaViewField.isInOutline() == true ? 1 :0;
	int  nTitleField = oMetaViewField.isTitleField() == true ? 1 :0;
	int  nIdentityField = oMetaViewField.isIdentityField() == true ? 1 :0;
	int  nSearchField = oMetaViewField.isSearchField() == true ? 1 :0;
	int  nInDetail = oMetaViewField.isInDetail() == true ? 1 :0;
	int  nInMultiTable = oMetaViewField.getPropertyAsBoolean("InMultiTable",false) == true ? 1 :0;
	int  nFromMainTable = oMetaViewField.getPropertyAsBoolean("FromMainTable",false) == true ? 1 :0;
	String sIsToData = CMyString.showNull(oMetaViewField.getAttributeValue("SELTODATA"));
	int nFieldGroupId = oMetaViewField.getFieldGroupId();
	String sParentOptional = CMyString.showNull(oMetaViewField.getAttributeValue("PARENTOPTIONAL"));
	int nRelationViewId = oMetaViewField.getRelationViewId();
	MetaView relationView = null;
	String sRelationViewInfo = "";
	if(nRelationViewId > 0){
		relationView = MetaView.findById(nRelationViewId);
		if(relationView != null){
			sRelationViewInfo = relationView.getDesc();
		}
		if(CMyString.isEmpty(sRelationViewInfo)){
			sRelationViewInfo = nRelationViewId + "";
		}
	}
	
	int nRelationViewChnlId = oMetaViewField.getPropertyAsInt("RELATIONVIEWCHNLID", 0);
	String sRelationViewChnlDesc = "";
	if(nRelationViewChnlId>0){
		Channel oRelViewChnl = Channel.findById(nRelationViewChnlId);
		if(oRelViewChnl != null) {
			sRelationViewChnlDesc = oRelViewChnl.getName();
		}
	}

	String sFeatureViewIds = oMetaViewField.getAttributeValue("FeatureViewIds");
	String sFeatureViewInfos = "";
	if(!CMyString.isEmpty(sFeatureViewIds)){
		MetaViews oFeatureMetaViews = MetaViews.findByIds(loginUser, sFeatureViewIds);
		for(int i = 0, length = oFeatureMetaViews.size(); i < length; i++){
			MetaView oFeatureMetaView = (MetaView)oFeatureMetaViews.getAt(i);
			if(oFeatureMetaView==null) continue;
			sFeatureViewInfos += oFeatureMetaView.getDesc();
			if((i + 1) < length) sFeatureViewInfos += ";";
		}
	}

	String sFeatureChnlIds = oMetaViewField.getAttributeValue("FeatureChnlIds");
	String sFeatureChnlInfos = "";
	if(!CMyString.isEmpty(sFeatureChnlIds)){
		Channels oFeatureChnls = Channels.findByIds(loginUser, sFeatureChnlIds);
		for(int i = 0, length = oFeatureChnls.size(); i < length; i++){
			Channel oFeatureChnl = (Channel)oFeatureChnls.getAt(i);
			if(oFeatureChnl==null) continue;
			sFeatureChnlInfos += oFeatureChnl.getName();
			if((i + 1) < length) sFeatureChnlInfos += ";";
		}
	}
	
	int nHasRight = 0;
	nHasRight = AuthServerMgr.hasRight(ContextHelper.getLoginUser(), oMetaView)== true ? 1 :0;

%>

<html>
<HEAD>
	<TITLE WCMAnt:param="viewfieldinfo_add_edit.jsp.newormodifyviewdata">新建/修改视图字段</TITLE>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/metaviewfield.js"></script>
	<script src="../../app/js/data/locale/metaviewfield.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/data/locale/ajax.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<script src="../../app/js/source/wcmlib/core/SysOpers.js"></script>
    <!--flaotpanel -->
	<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--validator-->
	<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!--processbar -->
	<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
	<!-- dialog -->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<!-- CarshBoard Outer Start -->
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
	<!-- CarshBoard Outer End -->
	<!-- datetime -->
	<script src="../../app/js/easyversion/calendar3.js"></script>
	<!--page js-->
	<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
	<script src="../../app/js/data/opers/metaviewfield.js"></script>
	<script src="../../app/js/data/cmsobj/metaviewfield.js"></script>
	<script src="../../app/classinfo/ClassInfoSelector.js"></script>
	<script type="text/javascript" src="../metadata/key_words.js" type="text/javascript"></script>
	<script type="text/javascript" src="../metadata/fieldinfo_add_edit_common.js"></script>
	<script type="text/javascript" src="viewfieldinfo_add_edit.js"></script>
	<script src="../../app/js/source/wcmlib/pagecontext/BubblePanel.js"></script>
	<script src="../../app/metaview/MetaViewSelector.js"></script>
	<script language="javascript">
		FieldInfos.dataTypeMappings = {
			<%
				MetaDataTypes dataTypes = MetaDataConstants.DATA_TYPES;
				for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
					MetaDataType dataType = dataTypes.getDataTypeAt(i);
					out.print(dataType.getKey() + ":" + dataType.getMaxLength());
					if(i < length-1) out.println(",");
				}
			%>	
		};
//-->
</script>
	<style type="text/css">
		*{
			font-size:12px;
			margin:0px;
			padding:0px;
		}
		.row{
			margin:10px;
		}
		.label{
			float:left;
			line-height:18px;
			width:100px;
			text-align:right;
		}
		.value{
		}
		input{
			width:200px;
			height:18px;
			border:1px solid silver;
		}
		.input_checkbox{
			width:auto;
			height:auto;
			border:0;
		}
		select{
			width:200px;
			height:18px;
			border:1px solid silver;
		}
		.selectClassInfo{
			background:url('../images/metadata/selectClassInfo.png') center center no-repeat;
			width:16px;
			height:16px;
			cursor:pointer;
			position:absolute;
			float:left;
		}
		.FieldGroupManage{
			background:url('../images/metadata/field_group_mgr.gif') center center no-repeat;
			width:16px;
			height:16px;
			cursor:pointer;
			position:absolute;
			float:left;
		}
		.selectMetaView{
			background:url('../images/metadata/view_select.gif') center center no-repeat;
			width:16px;
			height:16px;
			cursor:pointer;
			position:absolute;
			float:left;
		}
		.selectChannel{
			background:url('../images/icon/SitesChannels.gif') center center no-repeat;
			width:16px;
			height:16px;
			cursor:pointer;
			position:absolute;
			float:left;
		}
		
		.validTip{
			margin-left:18px;
		}
		.selectValidator{
			background:url('../images/metadata/selectValidator.gif') center center no-repeat;
			width:16px;
			height:16px;
			cursor:pointer;
			
			position:absolute;
		}
		#bubblePanel{
			position:absolute;
			left:320px;
			top:130px;
			width:200px;
			height:100px;
			background:lightyellow;
			text-align:center;
		}
		#bubblePanel2{
			position:absolute;
			left:320px;
			top:220px;
			width:120px;
			background:lightyellow;
			text-align:center;
		}
		.plainTip{
			color:#FF0000;
		}
		.text_txt {
			font-size: 12px;
			color: #000000;
		}
/*
		.valid_message{
			margin-left:50px;
			height:15px;
			margin-top:2px;
		}
*/
	</style>
</HEAD>

<BODY>
<form name="ObjectForm" id="ObjectForm" style="width:100%;height:100%;overflow:auto;">
    <input type="hidden" name="viewId" id="viewId" value="<%=nViewId %>">
    <input type="hidden" name="objectId" id="objectId" value="<%=nObjectId %>">
	<input type="hidden" name="hasRight" id="hasRight" value="<%=nHasRight%>" >

    <div class="row">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.viewDesc">视图名称：</span>
        <span class="value"><%=CMyString.transDisplay(sViewDesc)%>[<%=nViewId %>]</span>
    </div> 
	 <div class="row">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.anotherName">中文名称：</span>
        <span class="value">
            <input type="text" name="anotherName" id="anotherName" value="<%=CMyString.filterForHTMLValue(sAnotherName)%>" validation="type:'string',required:'true',max_len:'100',no_desc:'',showid:'anotherName_msg'">
        </span>
 		<span id="anotherName_msg" class="valid_message"></span>
   </div>
    <div class="row">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.fielName">英文名称：</span>
        <span class="value">
            <input class="" type="text" name="fieldName" id="fieldName" value="<%=CMyString.filterForHTMLValue(sFieldName)%>" validation="type:'common_char2',required:'true',max_len:'30',no_desc:'',showid:'fieldName_msg'">
        </span>
		<span id="fieldName_msg" class="valid_message"></span>
    </div>
    <div class="row">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.fieldType">字段类型：</span>
        <span class="value">
            <select name="fieldType" id="fieldType" initValue="<%=nFieldType %>">
				<%
					for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
						MetaDataType dataType = dataTypes.getDataTypeAt(i);
						out.println("<option _value='" + dataType.getKey() + "' value='" + dataType.getDataType() + "'>" + dataType.getDataTypeDesc() + "</option>");
					}
				%>
            </select>
        </span>
    </div>
	<div class="row" id="fieldGroupContainer" style="display:none;">
		<span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.group">所属分组：</span>
		<span class="value">
			<select name="FieldGroupId" id="FieldGroupId" _value="<%=nFieldGroupId%>">
				<option value="0"></option>
			</select>
			<span class="FieldGroupManage" id="FieldGroupManage" title="点击进入分组维护" WCMAnt:paramAttr="title:viewfieldinfo_add_edit.jsp.Click_enter_maintain_group"></span>
		</span>
	</div> 
	<div  id="editorContainer" style="display:none;">
		<div class="row">
			<span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.feature_view">特性库视图：</span>
			<span class="value">
				  <input isAttr="1" type="text" name="FeatureViewIds" id="FeatureViewIds" disabled="disabled" value="<%=CMyString.showNull(sFeatureViewInfos)%>" _featureViewIds="<%=CMyString.showNull(sFeatureViewIds)%>" />
				<span class="selectMetaView" id="selectFeatureMetaView"></span>
			</span>
		</div> 
		<div class="row">
			<span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.feature_chnl">特性库栏目：</span>
			<span class="value">
				  <input isAttr="1" type="text" name="FeatureChnlIds" id="FeatureChnlIds" disabled="disabled" value="<%=CMyString.showNull(sFeatureChnlInfos)%>" _featureChnlIds="<%=CMyString.showNull(sFeatureChnlIds)%>" />
				<span class="selectChannel" id="selectChannel"></span>
			</span>
		</div>
	</div>
	<div class="row" id="relationViewContainer" style="display:none;">
			<span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.relative_view">相关视图：</span>
			<span class="value">
				  <input type="text" name="RelationViewId" id="RelationViewId" disabled value="<%=sRelationViewInfo%>" _relationViewId ="<%=nRelationViewId%>" />
				<span class="selectMetaView" id="selectMetaView"></span>
			</span>
		</div> 
    <div class="row" id="locateChannelContainer" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.locateChannel">定位栏目：</span>
        <span class="value">
            <input type="text" name="locateChannel" id="locateChannel" value="<%=CMyString.filterForHTMLValue(sLocateChannel) %>">
        </span>
    </div>    
    <div class="row" id="enmValueContainer" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.enmValue">枚举值：</span>
        <span class="value">
            <input type="text" name="enmValue" id="enmValue" value="<%=CMyString.filterForHTMLValue(sEnmValue) %>" dbfieldEnmValue="<%=CMyString.filterForHTMLValue(sDBFieldEnmValue)%>"  
			onmouseover="showBubblePanel();" onmouseout="hideBubblePanel();" validation="type:'string',max_len:'500',no_desc:''">
			<div id="bubblePanel" style="display:none;border:solid black 1px">
				<div WCMAnt:param="viwefieldinfo_add_edit.jsp.toptip1">格式为:item~item</div><div WCMAnt:param="viewfieldinfo_add_edit.jsp.toptip2">其中item为：label`value或label</div><div WCMAnt:param="viewfieldinfo_add_edit.jsp.toptip3">如:中国`1~美国`2~英国`3</div><div WCMAnt:param="viewfieldinfo_add_edit.jsp.toptip4">中国~美国~英国</div><br><font color="red" WCMAnt:param="viewfieldinfo_add_edit.jsp.doubleClick">双击打开构造页面</font>
			</div>
        </span>
    </div>    
    <div class="row" id="classIdContainer" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.classId">分类法ID：</span>
        <span class="value">
            <input type="text" name="classId" id="classId" disabled value="<%=sClassId%>" validation="showid:'validTip',rpc:'checkClassInfoValid'">
			<span class="selectClassInfo" id="selectClassInfo"></span><span id="validTip" class="validTip"></span>
        </span>
    </div>        
    <div class="row">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.dbType">库中类型：</span>
        <span class="value">
            <select name="dbType" id="dbType" initValue="<%=nDBType%>">
				<%
					MetaDataTypes dbDataTypes = MetaDataConstants.DB_DATA_TYPES;
					for(int i = 0, length = dbDataTypes.getDataTypesCount(); i < length; i++){
						MetaDataType dbDataType = dbDataTypes.getDataTypeAt(i);
						out.println("<option _maxLength='" + dbDataType.getMaxLength() + "' _value='" + dbDataType.getKey() + "' value='" + dbDataType.getDataType() + "'>" + dbDataType.getDataTypeDesc() + "</option>");
					}
				%>
            </select>
        </span>
    </div>    
    <div class="row" id="dbLengthContainer" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.dbLength">库中长度：</span>
        <span class="value">
            <input type="text" name="dbLength" id="dbLength" value="<%=nDBLength%>" validation="type:'int',value_range:'1,4000',required:'true',no_desc:''" firstLoaded="true">
        </span>
    </div>    
    <div class="row" id="dbScaleContainer" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.dbScale">小数位数：</span>
        <span class="value">
            <input type="text" name="dbScale" id="dbScale" value="<%=nDBScale%>" 
			<%if(nObjectId == 0){%>
				validation="type:'int',min:'1',required:'true',no_desc:''"
			<%}%>
			>
        </span>
    </div>    
    <div class="row" id="defaultValueContainer">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.defaultValue">字段默认值：</span>
        <span class="value">
            <input type="text" name="defaultValue" id="defaultValue" value="<%=CMyString.filterForHTMLValue(sDefaultValue)%>" >
			<span class="plainTip" id="plainTip"></span>
			<span class="selectClassInfo" id="defaultSelectClassInfo"></span>
			<span id="defaultValidTip" class="validTip"></span>
        </span>
    </div> 
	<div id="bubblePanel2" style="display:none;border:solid black 1px">
		<font color="red" WCMAnt:param="fieldinfo_add_edit.jsp.doubleClick">双击打开构造页面</font>
	</div>
    <div class="row">
		<span class="label">&nbsp;</span>
		<span>
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="notNull" id="notNull" initValue="<%=nNotNull%>" onclick="setNotNullEvent(this);">
			</span><label for="notNull" WCMAnt:param="viewfieldinfo_add_edit.jsp.notNull">不能为空</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="notEdit" id="notEdit" initValue="<%=nNotEdit%>" <%=nFromMainTable == 1 ? "" : "disabled"%>>
			</span><label for="notEdit" WCMAnt:param="viewfieldinfo_add_edit.jsp.notEdit">不能编辑</label>
		</span>	
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="hiddenField" id="hiddenField" initValue="<%=nHiddenField%>" onclick="setHiddenEvent(this, <%=nFromMainTable%>);">
			</span><label for="hiddenField" WCMAnt:param="viewfieldinfo_add_edit.jsp.hiddenField">隐藏字段</label>
		</span>	
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="IsURLField" id="IsURLField" initValue="<%=nIsURLField%>">
			</span><label for="IsURLField" WCMAnt:param="viewfieldinfo_add_edit.jsp.IsURLField">URL字段</label>
		</span>
		<span style="margin-left:5px;">
			<span id="radioOrCheckContainer" style="display:none;">
				<input isBoolean="true" class="input_checkbox" type="checkbox" name="RADORCHK"  id="RADORCHK" initValue="<%=nRadorchk%>"><label for="RADORCHK" WCMAnt:param="viewfieldinfo_add_edit.jsp.radOrChk">单选树</label>
			</span>
			<span id="selToDatabase" style="display:none;">
				<input isAttr="1" isBoolean="true" class="input_checkbox" type="checkbox" name="SELTODATA"  id="SELTODATA" initValue="<%=sIsToData%>" /><label for="SELTODATA" WCMAnt:param="viewfieldinfo_add_edit.jsp.seltodata">存入库中</label>
			</span>
		</span>	
		<span style="margin-left:5px;" id="parent_optional_base" style="display:none;">
			<span>
				<input isAttr="1" isBoolean="true" type="checkbox" class="input_checkbox" name="PARENTOPTIONAL" id="PARENTOPTIONAL" initValue="<%=sParentOptional%>" />
			</span><label for="PARENTOPTIONAL" WCMAnt:param="viewfieldinfo_add_edit.jsp.parentoptional">不能选择父分类</label>
		</span>
    </div>    
    <div class="row">
		<span class="label">&nbsp;</span>
		<span>
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="inOutline" id="inOutline" initValue="<%=nInOutline%>">
			</span><label for="inOutline" WCMAnt:param="viewfieldinfo_add_edit.jsp.inOutline">概览显示</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="titleField" id="titleField" initValue="<%=nTitleField%>" onclick="setTitleEvent(this);">
			</span><label for="titleField" WCMAnt:param="viewfieldinfo_add_edit.jsp.titleField">标题字段</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="searchField" id="searchField" initValue="<%=nSearchField%>">
			</span><label for="searchField" WCMAnt:param="viewfieldinfo_add_edit.jsp.searchField">检索字段</label>
		</span>		
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="inDetail" id="inDetail" initValue="<%=nInDetail%>">
			</span><label for="inDetail" WCMAnt:param="viewfieldinfo_add_edit.jsp.inDetail">细览显示</label>
		</span>	
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="identityField" id="identityField" initValue="<%=nIdentityField%>">
			</span><label for="identityField" WCMAnt:param="viewfieldinfo_add_edit.jsp.identityField">唯一标识字段</label>
		</span>	
    </div>    
    <div class="row" style="display:none;" id="MultiTableContainer" initValue="<%=nFromMainTable%>">
		<span class="label">&nbsp;</span>
		<span>
			<span>
				<input isBoolean="true" type="checkbox" class="input_checkbox" name="inMultiTable" id="inMultiTable" initValue="<%=nInMultiTable%>">
			</span><label for="inMultiTable" WCMAnt:param="viewfieldinfo_add_edit.jsp.inMultiTable">多表选择</label>
		</span>
    </div>    
    <div class="row" style="display:none;">
        <span class="label" WCMAnt:param="viewfieldinfo_add_edit.jsp.validator">校验器：</span>
        <span class="value">
            <input type="text" name="validator" id="validator" value="<%=CMyString.filterForHTMLValue(sValidator)%>">
			<span class="selectValidator" id="selectValidator"></span>
        </span>
    </div>
</form>
</BODY>
</HTML>
<%
}catch(Exception e) {
	e.printStackTrace();
}	
%>