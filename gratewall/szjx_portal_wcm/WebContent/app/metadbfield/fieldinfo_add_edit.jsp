<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>

<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="java.util.Enumeration" %>

<%@include file="../include/public_processor.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>

<html>
<HEAD>
<TITLE WCMAnt:param="fieldinfo_add_edit.jsp.newormodifymetadatafield">新建/修改元数据字段</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metadbfiled.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!--wcm-dialog start-->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<!--page js data-->
<script src="../../app/js/source/wcmlib/core/SysOpers.js"></script>
<script src="../../app/classinfo/ClassInfoSelector.js"></script>
<!--carshboard-->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<!-- datetime -->
<script src="../../app/js/easyversion/calendar3.js"></script>
<!-- page js -->
<script language="javascript" src="../metadata/key_words.js" type="text/javascript"></script>
<script type="text/javascript" src="../metadata/fieldinfo_add_edit_common.js"></script>
<SCRIPT language=JavaScript src="fieldinfo_add_edit.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/pagecontext/BubblePanel.js"></SCRIPT>
<script language="javascript">
<!--
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
        height:18px;
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
		background:url('../images/metadata/selectClassInfo.png') no-repeat 0 0;
		width:18px;
		height:16px;
		cursor:pointer;
		overflow:auto;
		position:absolute;
	}
	.selectValidator{
		background:url('../images/metadata/selectValidator.gif') no-repeat 0 0;
		width:16px;
		height:16px;
		cursor:pointer;
	}
	.validTip{
		margin-left:18px;
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
	/*
	.valid_message{
		margin-left:50px;
		height:15px;
		margin-top:2px;
	}
	*/
</style>
</head>

<body>
<%
	int nObjectId = processor.getParam("objectid", 0);
	MetaDBField metadbField = (MetaDBField) processor.excute("wcm61_metadbfield", "findDBFieldInfoById");		
	String sFieldName = CMyString.filterForHTMLValue(metadbField.getName());
	String sAnotherName = CMyString.filterForHTMLValue(metadbField.getAnotherName());	
	String sDefaultValue = CMyString.filterForHTMLValue(metadbField.getDefaultValue());
	int nDbTableId = processor.getParam("tableinfoid",0);	
	MetaDBTable metadbTable = MetaDBTable.findById(nDbTableId);	
	String sAnotherTableName = CMyString.transDisplay(metadbTable.getAnotherName());
	String sClassId = "";
	int nClassId = metadbField.getClassId();
	if(nClassId!=0)sClassId=nClassId+"";
%>
<%
	int nMaxDBLength = 1;
try{
	String sSQL = "select max(DBLength) MaxDBLength from XWCMVIEWFIELDINFO where DBField=?";
	int[] pParameters = new int[]{nObjectId};
	nMaxDBLength = DBManager.getDBManager().sqlExecuteIntQuery(sSQL, pParameters);
	if(nMaxDBLength == 0) nMaxDBLength = 1;
}catch(Throwable t){
	t.printStackTrace();
}
%>
<form name="ObjectForm" id="ObjectForm" style="width:100%;height:100%;overflow:auto;">
    <input type="hidden" name="tableInfoId" id="tableInfoId" value="">
    <input type="hidden" name="objectId" id="objectId" value="">
    <div id="objectContainer">
		<div class="row">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.metaName1">元数据别名：</span>
			<span class="value" id="tableAnotherName"><%=sAnotherTableName%>[<%=metadbTable.getId()%>]</span>
		</div>    
		<div class="row">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.chineseName">中文名称：</span>
			<span class="value">
				<input type="text" name="anotherName" id="anotherName" value="<%=CMyString.filterForHTMLValue(sAnotherName)%>" validation="type:'string',required:'true',max_len:'100',no_desc:'',showid:'anotherName_msg'">
			</span>
			<span id="anotherName_msg" class="valid_message"></span>
		</div>
		<div class="row">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.englishName">英文名称：</span>
			<span class="value">
				<input class="" type="text" name="fieldName" id="fieldName" oldValue="<%=CMyString.filterForHTMLValue(sFieldName)%>" value="<%=CMyString.filterForHTMLValue(sFieldName)%>" validation="type:'common_char2',required:'true',max_len:'30',no_desc:'',showid:'fieldName_msg'">
			</span>
			<span id="fieldName_msg" class="valid_message"></span>
		</div>   
		<div class="row">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.fieldType">字段类型：</span>
			<span class="value">
				<select name="fieldType" id="fieldType" initValue="<%=metadbField.getType()%>">
					<%
						for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
							MetaDataType dataType = dataTypes.getDataTypeAt(i);
							out.println("<option _value='" + dataType.getKey() + "' value='" + dataType.getDataType() + "'>" + dataType.getDataTypeDesc() + "</option>");
						}
					%>
				</select>
			</span>
		</div>  
		<div class="row" id="enmValueContainer" style="display:none;">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.enum">枚举数值：</span>
			<span class="value">
				<input type="text" name="enmValue" id="enmValue"   value="<%=CMyString.filterForHTMLValue(metadbField.getEnmValue())%>"  onmouseover="showBubblePanel();" onmouseout="hideBubblePanel();" validation="type:'string',max_len:'500',no_desc:''">
			</span>
			<div id="bubblePanel" style="display:none;border:solid black 1px">
				<div WCMAnt:param="fieldinfo_add_edit.jsp.toptip1">格式为:item~item</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip2">其中item为：label`value或label</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip3">如:中国`1~美国`2~英国`3</div><div WCMAnt:param="fieldinfo_add_edit.jsp.toptip4">中国~美国~英国</div><br><font color="red" WCMAnt:param="fieldinfo_add_edit.jsp.doubleClick">双击打开构造页面</font>
			</div>
		</div>    
		<div class="row" id="classIdContainer" style="display:none;">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.classicId">分类法ID：</span>
			<span class="value">
				<input type="text" name="classId" id="classId" disabled value="<%=sClassId%>" validation="showid:'validTip',rpc:'checkClassInfoValid'">
				<span class="selectClassInfo" id="selectClassInfo">&nbsp;&nbsp;&nbsp;</span><span id="validTip" class="validTip"></span>
			</span>
		</div>        
		<div class="row">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.tableType">库中类型：</span>
			<span class="value">
				<select name="dbType" id="dbType" initValue="<%=metadbField.getDBType()%>">
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
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.tableLength">库中长度：</span>
			<span class="value">
				<input type="text" name="dbLength" id="dbLength" value="<%=metadbField.getLength()%>" _minvalue="<%=nMaxDBLength%>" validation="type:'int',min:'<%=nMaxDBLength%>',required:'true',no_desc:''" firstLoaded="true">
			</span>
		</div>    
		<div class="row" id="dbScaleContainer" style="display:none;">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.decimalLength">小数位数：</span>
			<span class="value">
				<input type="text" name="dbScale" id="dbScale" value="<%=metadbField.getScale()%>" 
				<%if(nObjectId == 0){%>
					validation="type:'int',min:'1',required:'true',no_desc:''"
				<%}%>
				>
			</span>
		</div>    
		<div class="row" id="defaultValueContainer">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.defaultValue">字段默认值：</span>
			<span class="value">
				<input type="text" name="defaultValue" id="defaultValue" value="<%=CMyString.filterForHTMLValue(sDefaultValue)%>"><span class="plainTip" id="plainTip"></span><span class="selectClassInfo" id="defaultSelectClassInfo" style="display:none;">&nbsp;&nbsp;&nbsp;</span><span id="defaultValidTip" class="validTip"></span>
			</span>
		</div> 
		<div id="bubblePanel2" style="display:none;border:solid black 1px">
			<font color="red" WCMAnt:param="fieldinfo_add_edit.jsp.doubleClick">双击打开构造页面</font>
		</div>
		<div class="row">
			<span class="label">&nbsp;</span>
			<span class="value">			
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notNull" id="notNull" initValue="<%=metadbField.isNotNull()?"1":"0"%>" onclick="setNotNullEvent(this);">
			</span><label for="notNull" WCMAnt:param="fieldinfo_add_edit.jsp.noNull">不能为空</label>			
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notEdit" id="notEdit" initValue="<%=metadbField.getPropertyAsInt("NOTEDIT",0)%>">
				</span><label for="notEdit" WCMAnt:param="fieldinfo_add_edit.jsp.noEdit">不能编辑</label>
			</span>	
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="hiddenField" id="hiddenField" initValue="<%=metadbField.getPropertyAsInt("HIDDENFIELD",0)%>">
				</span><label for="hiddenField" WCMAnt:param="fieldinfo_add_edit.jsp.hiddenField">隐藏字段</label>
			</span>	
			<span style="margin-left:5px;">
				<span id="radioOrCheckContainer" style="display:none;">
					<input isBoolean="true" value="1|0" class="input_checkbox" type="checkbox" name="RADORCHK"  id="RADORCHK" initValue="<%=metadbField.isRadioOrCheck()?"1":"0"%>"><label for="RADORCHK" WCMAnt:param="fieldinfo_add_edit.jsp.singnlTree">单选树</label>
				</span>
			</span>	
			</span>
		</div>    
		<div class="row" style="display:none;">
			<span class="label"></span>
			<span>
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="inOutline" id="inOutline" initValue="<%=metadbField.getPropertyAsInt("INOUTLINE",0)%>">
				</span><label for="inOutline" WCMAnt:param="fieldinfo_add_edit.jsp.generalShow">概览显示</label>
			</span>
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="titleField" id="titleField" initValue="<%=metadbField.getPropertyAsInt("TITLEFIELD",0)%>" onclick="setTitleEvent(this);">
				</span><label for="titleField" WCMAnt:param="fieldinfo_add_edit.jsp.titleField">标题字段</label>
			</span>
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="searchField" id="searchField" initValue="<%=metadbField.getPropertyAsInt("SEARCHFIELD",0)%>">
				</span><label for="searchField" WCMAnt:param="fieldinfo_add_edit.jsp.queryField">检索字段</label>
			</span>		
			<span style="margin-left:5px;">
				<span>
					<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="inDetail" id="inDetail" initValue="<%=metadbField.getPropertyAsInt("INDETAIL",0)%>">
				</span><label for="inDetail" WCMAnt:param="fieldinfo_add_edit.jsp.detialShow">细览显示</label>
			</span>	
		</div>    	     
		<div class="row" style="display:none;">
			<span class="label" WCMAnt:param="fieldinfo_add_edit.jsp.adjustor">校验器：</span>
			<span class="value">
				<input type="text" name="validator" id="validator" value="<%=CMyString.filterForHTMLValue(CMyString.showNull(metadbField.getValidator()))%>">
				<span class="selectValidator" id="selectValidator">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>    
	</div>
</form>

<%
	//已存在的检查
	TableInfo oTbinfo = DBManager.getDBManager().getTableInfo("WCMMETATABLE"+metadbTable.getName());
	Enumeration fieldNames = null;
	if(oTbinfo != null){
		 fieldNames = oTbinfo.getFieldNames();
	}
	StringBuffer buff = new StringBuffer();
	String fieldName = null;
	while(fieldNames!=null && fieldNames.hasMoreElements()){
		fieldName = (String)fieldNames.nextElement();
		buff.append(",'").append(fieldName.toLowerCase()).append("':1");
	}
%>
<script>
var FIELDS = {
	<%=buff.length()>1?buff.substring(1):""%>
}
</script>
</body>
</html>