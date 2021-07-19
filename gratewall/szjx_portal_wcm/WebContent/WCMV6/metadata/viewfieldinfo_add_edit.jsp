<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaDataTypes" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataType" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>

<html>
<HEAD>
<TITLE>新建/修改视图字段</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<SCRIPT language=JavaScript src="../js/com.trs.util/Common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
    $import('com.trs.wcm.MessageCenter');
    $import('com.trs.validator.Validator');
    $import('com.trs.crashboard.CrashBoarder');
	$import('com.trs.wcm.domain.ViewFieldInfoMgr');
	$import('com.trs.wcm.domain.ClassInfoMgr');
	$import('com.trs.wcm.PopTip');		
</SCRIPT>
<script language="javascript" src="key_words.js" type="text/javascript"></script>
<SCRIPT language=JavaScript src="viewfieldinfo_add_edit.js"></SCRIPT>
<script language="javascript">
<!--
	ViewFieldInfos.dataTypeMappings = {
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
	}
	.selectValidator{
		background:url('../images/metadata/selectValidator.gif') center center no-repeat;
		width:16px;
		height:16px;
		cursor:pointer;
	}
</style>
</HEAD>

<BODY>
<form name="ObjectForm" id="ObjectForm" style="width:100%;height:100%;overflow:auto;">
    <input type="hidden" name="viewId" id="viewId" value="">
    <input type="hidden" name="objectId" id="objectId" value="">
    <div id="objectContainer"></div>
</form>
<textarea name="objectTemplate" id="objectTemplate" rows="" cols="" style="display:none;">
    <div class="row">
        <span class="label">视图名称：</span>
        <span class="value" id="viewDesc"></span>
    </div>    
    <div class="row">
        <span class="label">英文名称：</span>
        <span class="value">
            <input class="" type="text" name="fieldName" id="fieldName" value="{#MetaViewField.FieldName}" validation="type:'common_char2',required:'true',max_len:'30',no_desc:''">
        </span>
    </div>    
    <div class="row">
        <span class="label">中文名称：</span>
        <span class="value">
            <input type="text" name="anotherName" id="anotherName" value="{#MetaViewField.AnotherName}" validation="type:'string',required:'true',max_len:'100',no_desc:''">
        </span>
    </div>    
    <div class="row">
        <span class="label">字段类型：</span>
        <span class="value">
            <select name="fieldType" id="fieldType" initValue="{#MetaViewField.FieldType}">
				<%
					for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
						MetaDataType dataType = dataTypes.getDataTypeAt(i);
						out.println("<option _value='" + dataType.getKey() + "' value='" + dataType.getDataType() + "'>" + dataType.getDataTypeDesc() + "</option>");
					}
				%>
            </select>
        </span>
    </div>  
    <div class="row" id="locateChannelContainer" style="display:none;">
        <span class="label">定位栏目：</span>
        <span class="value">
            <input type="text" name="locateChannel" id="locateChannel" value="{#MetaViewField.locateChannel}">
        </span>
    </div>    
    <div class="row" id="enmValueContainer" style="display:none;">
        <span class="label">枚举值：</span>
        <span class="value">
            <input type="text" name="enmValue" id="enmValue" value="{#MetaViewField.enmValue}" validation="type:'string',max_len:'200',no_desc:''" _title="格式为:item~item<br><span style='white-space:nowrap;overflow:visible;'>其中item为：label`value或label</span><br>如:<li>中国`1~美国`2~英国`3<br><li>中国~美国~英国<br><font color='red'>双击打开构造页面</font>">
        </span>
    </div>    
    <div class="row" id="classIdContainer" style="display:none;">
        <span class="label">分类法ID：</span>
        <span class="value">
            <input type="text" name="classId" id="classId" value="{#MetaViewField.CLASSID}" validation="showid:'validTip',rpc:'checkClassInfoValid'">
			<span class="selectClassInfo" id="selectClassInfo"></span><span id="validTip"></span>
        </span>
    </div>        
    <div class="row">
        <span class="label">库中类型：</span>
        <span class="value">
            <select name="dbType" id="dbType" initValue="{#MetaViewField.dbType}">
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
        <span class="label">库中长度：</span>
        <span class="value">
            <input type="text" name="dbLength" id="dbLength" value="{#MetaViewField.DBLENGTH}" validation="type:'int',min:'1',no_desc:''" firstLoaded="true">
        </span>
    </div>    
    <div class="row" id="dbScaleContainer" style="display:none;">
        <span class="label">小数位数：</span>
        <span class="value">
            <input type="text" name="dbScale" id="dbScale" value="{#MetaViewField.DBScale}" validation="type:'int',min:'0',max:'38',no_desc:''">
        </span>
    </div>    
    <div class="row" id="defaultValueContainer">
        <span class="label">字段默认值：</span>
        <span class="value">
            <input type="text" name="defaultValue" id="defaultValue" value="{#MetaViewField.DefaultValue}" validation="type:'string',max_len:'100',no_desc:'',showid:'defaultValidTip'">
			<span class="selectClassInfo" id="defaultSelectClassInfo"></span><span id="defaultValidTip"></span>
        </span>
    </div>    
    <div class="row">
		<span class="label"></span>
		<span>
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notNull" id="notNull" initValue="{#MetaViewField.NotNull}">
			</span><label for="notNull">不能为空</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="notEdit" id="notEdit" initValue="{#MetaViewField.NotEdit}">
			</span><label for="notEdit">不能编辑</label>
		</span>	
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="hiddenField" id="hiddenField" initValue="{#MetaViewField.hiddenField}">
			</span><label for="hiddenField">隐藏字段</label>
		</span>	
		<span style="margin-left:5px;">
			<span id="radioOrCheckContainer" style="display:none;">
				<input isBoolean="true" value="1|0" class="input_checkbox" type="checkbox" name="RADORCHK"  id="RADORCHK" initValue="{#MetaViewField.RADORCHK}"><label for="RADORCHK">单选树</label>
			</span>
		</span>	
    </div>    
    <div class="row">
		<span class="label"></span>
		<span>
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="inOutline" id="inOutline" initValue="{#MetaViewField.inOutline}">
			</span><label for="inOutline">概览显示</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="titleField" id="titleField" initValue="{#MetaViewField.titleField}" onclick="setTitleEvent(this);">
			</span><label for="titleField">标题字段</label>
		</span>
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="searchField" id="searchField" initValue="{#MetaViewField.searchField}">
			</span><label for="searchField">检索字段</label>
		</span>		
		<span style="margin-left:5px;">
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="inDetail" id="inDetail" initValue="{#MetaViewField.inDetail}">
			</span><label for="inDetail">细览显示</label>
		</span>	
    </div>    
    <div class="row" style="display:none;" id="MultiTableContainer" initValue="{#MetaViewField.FROMMAINTABLE}">
		<span class="label"></span>
		<span>
			<span>
				<input isBoolean="true" value="1|0" type="checkbox" class="input_checkbox" name="inMultiTable" id="inMultiTable" initValue="{#MetaViewField.inMultiTable}">
			</span><label for="inMultiTable">多表选择</label>
		</span>
    </div>    
    <div class="row" style="display:none;">
        <span class="label">校验器：</span>
        <span class="value">
            <input type="text" name="validator" id="validator" value="{#MetaViewField.Validator}">
			<span class="selectValidator" id="selectValidator"></span>
        </span>
    </div>    
</textarea>
</BODY>
</HTML>