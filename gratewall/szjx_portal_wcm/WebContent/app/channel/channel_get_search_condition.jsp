<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="java.sql.Types" %>
<%@include file="../include/public_server.jsp"%>
<%
	DBManager currDBMgr =  DBManager.getDBManager();
	int currType = currDBMgr.getDBType().getType();

%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title id="tlDoc" WCMAnt:param="channel_get_search_condition.jsp.selectsearchcondition">select search condition</title>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<style type="text/css">
html, body{
	margin:0px;
	padding:0px;
}
.ext-gecko .sp_name{
	display:-moz-inline-box;
}
.ext-safari .sp_name{
	display:inline-block;
}
td, select, input{
	font-size:12px;
}
.sp_name{
	font-size:12px;
}
#dvContainer{
	width:100%;
	height:100%;
	overflow:hidden;
}
#inputModeContainer{
	width:100%;
	height:100%;
	overflow:hidden;
}
#dvContent{
	width:99%;
	height:75%;
	overflow-x:hidden;
	overflow-y:auto;
	scrollbar-face-color: #f6f6f6;
	scrollbar-highlight-color: #ffffff;
	scrollbar-shadow-color: #cccccc; 
	scrollbar-3dlight-color: #cccccc; 
	scrollbar-arrow-color: #330000; 
	scrollbar-track-color: #f6f6f6; 
	scrollbar-darkshadow-color: #ffffff;
}
legend{
	font-size:13px;
	font-weight:bold;
}
.deleteElement{
	background:url("../images/channel/delete.gif") center center no-repeat;
	width:16px;
	height:16px;
	cursor:pointer;
	position:absolute;
}
.dvBottom{
	width:100%;
	height:5%;
	padding:3px;
}
.grayTips{
	color:#888;
	font-style:italic;
	font-size:12px;
}
</style>
<link rel="stylesheet" type="text/css" WCMAnt:locale="channel_get_search_condition_$locale$.css" href="channel_get_search_condition_cn.css" />
</HEAD>
<BODY style="width:100%;height:100%;margin:5px;overflow:hidden;">
<div id="dvContainer" style="display:none;">
	<div>
		<button onclick="addSelectDiv(this);return false;"><img src="../images/channel/option_add.gif" align="absmiddle" border="0">&nbsp;<span WCMAnt:param="channel_get_search_condition.jsp.addcondition">增加条件</span></button>
		<div class="wrapDv">
		    <input type="radio" name="conditonType" id="conditonType1" value="and" checked/><label for="conditonType1" style="font-size:14px;font-weight:bold" WCMAnt:param="channel_get_search_condition.jsp.andcondition">符合以下所有条件</label>
		</div>
		<div class="wrapDv">
		    <input type="radio" name="conditonType" id="conditonType2" value="or" /><label for="conditonType2" style="font-size:14px;font-weight:bold" WCMAnt:param="channel_get_search_condition.jsp.orcondition">符合以下任一条件</label>
		</div>
	</div>
	<div id="dvContent" onscroll="forceReplain();">
	</div>
	<div class="dvBottom">
		<A href="#" class="sp_name" onclick='changgeToInputMode(this.checked)' WCMAnt:param="channel_get_search_condition.jsp.selectmode">切换到输入模式</A>
	</div>
</div>
<div id="inputModeContainer" style="display:none;">
	<div 	style="width:99%;height:85%;">
		<span class="sp_name" WCMAnt:param="channel_get_search_condition.jsp.inputsearchcondition" >请输入检索条件：</span><input type="text" name="" id="inputValue" value="" size="30"/>
	</div>
	<div class="dvBottom">
		 <A href="#" class="sp_name" onclick='changgeToSelectMode(this.checked)' WCMAnt:param="channel_get_search_condition.jsp.inputmode">切换到选择模式</A>
	</div>
</div>
<div id="template" style="display:none">
	<div style="margin-top:8px;width:98%;position:relative;" name="content">
		<span>
			<span class="sp_name" WCMAnt:param="channel_get_search_condition.jsp.fieldname">字段名:</span>
			<select name="SearchConField" style="width:100px;">
				<option value="" WCMAnt:param="channel_get_search_condition.jsp.pleaseselect">请选择</option>
				<option name="doctitleOption" id="doctitleOption" value="wcmdocument.doctitle" dataType="string" WCMAnt:param="channel_get_search_condition.jsp.title">标题</option>
				<%
					String sTableName = "true".equals(request.getParameter("wcmdoconly")) ? "wcmdocument" : "wcmchnldoc";
				%>
				<option value="<%=sTableName%>.crtime" dataType="date" WCMAnt:param="channel_get_search_condition.jsp.crtime">创建时间</option>
				<option value="<%=sTableName%>.cruser" dataType="string" WCMAnt:param="channel_get_search_condition.jsp.cruser">创建者</option>
				<option value="<%=sTableName%>.docreltime" dataType="date" WCMAnt:param="channel_get_search_condition.jsp.docreltime">撰写时间</option>
				<option value="<%=sTableName%>.operuser" dataType="string" WCMAnt:param="channel_get_search_condition.jsp.operuser">最后修改人</option>
				<option value="<%=sTableName%>.opertime" dataType="date" WCMAnt:param="channel_get_search_condition.jsp.opertime">最后修改时间</option>
				<option value="<%=sTableName%>.docstatus" dataType="int" WCMAnt:param="channel_get_search_condition.jsp.docstatus">文档状态</option>
				<option name="docflagOption" id="docflagOption" value="wcmdocument.docflag" dataType="int" WCMAnt:param="channel_get_search_condition.jsp.docflag">表单文档标识</option>
			</select>
		</span>&nbsp;
		<span>
			<span class="sp_name" WCMAnt:param="channel_get_search_condition.jsp.opertype">操作类型:</span>
			<select name="SearchConWay" style="width:100px;">
				<option value="" WCMAnt:param="channel_get_search_condition.jsp.pleaseselect">请选择</option>
				<option value="<=" WCMAnt:param="channel_get_search_condition.jsp.xiaoyudengyu">小于等于</option>
				<option value="<" WCMAnt:param="channel_get_search_condition.jsp.xiaoyu">小于</option>
				<option value="&gt;="  WCMAnt:param="channel_get_search_condition.jsp.dayudengyu">大于等于</option>
				<option value="=" WCMAnt:param="channel_get_search_condition.jsp.dengyu">等于</option>
				<option value="<&gt;" WCMAnt:param="channel_get_search_condition.jsp.budengyu">不等于</option>
				<option value="&gt;" WCMAnt:param="channel_get_search_condition.jsp.dayu">大于</option>
				<option value="like" WCMAnt:param="channel_get_search_condition.jsp.mohu">包含</option>
			</select>
		</span>&nbsp;
		<span>
			<span class="sp_name" WCMAnt:param="channel_get_search_condition.jsp.value">值:</span>
			<input type="text" name="fieldValue" id="fieldValue" value="" style="width:110px;"/>
		</span>
		<span class="deleteElement" id="deleteEl" onClick="deleteSelectDiv(this)"></span>
	</div>
</div>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/channel.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<script language="javascript">
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG['CHANNEL_TRUE'] || '确定',
			cmd : function(){
				return submit();
			}
		},{
			text : wcm.LANG['CHANNEL_CANCEL'] || '取消',
			cmd : function(){
				return cancel();
			}
		}
	]
};

function init(selValue){
	//by CC 20120618 如果栏目配置了视图，需要隐藏掉wcmdocument.doctitle和docflag字段检索
	var bHiddenDocTitle = selValue.bHiddenDocTitle;
	if(bHiddenDocTitle == "true" || bHiddenDocTitle == true){
		$("SearchConField").remove($("doctitleOption").index);
		$("SearchConField").remove($("docflagOption").index);
	}
	
	selValue = selValue.initValue.trim();
	$('inputValue').value = selValue;
	if(selValue == ''){
		$('dvContainer').style.display = '';
		new Insertion.Bottom($('dvContent'), $('template').innerHTML);
	}else if(!canAnalyse(selValue)){
		$('dvContainer').style.display = 'none';
		$('inputModeContainer').style.display = '';
	}else{
		$('dvContainer').style.display = '';
		$('inputModeContainer').style.display = 'none';
		var conditionComponets = new Array();
		if(selValue.indexOf(' and ') > 0){
			conditionComponets = selValue.split('and');
			$('conditonType1').checked = true;
		}else if(selValue.indexOf(' or ') > 0){
			conditionComponets = selValue.split('or');
			$('conditonType2').checked = true;
		}else{
			conditionComponets[0] = selValue;
		}
		for(var index=0; index < conditionComponets.length; index++){
			var conditionComponet = conditionComponets[index];
			initSelectModeByValue(conditionComponet);
		}

	}

	//绑定元素的事件
	var nameSelectEls = document.getElementsByName('SearchConField');
	for(var k=0; k<nameSelectEls.length; k++){
		Event.observe(nameSelectEls[k], 'change', initTipValueElByFieldSelectEl.bind(nameSelectEls[k], null));
	}
	var valueInputEls = document.getElementsByName('fieldValue');
	for(var j=0; j<valueInputEls.length; j++){
		Event.observe(valueInputEls[j], 'focus', fieldValueElFocus.bind(valueInputEls[j]));
		Event.observe(valueInputEls[j], 'blur', fieldValueElBlur.bind(valueInputEls[j]));
	}

};

function initTipValueElByFieldSelectEl(inputEl){
	var currSelectFieldEl = this;
	if(!currSelectFieldEl) return;
	var fieldValueEl;
	if(inputEl) fieldValueEl = inputEl;
	else fieldValueEl = getValueElByFieldSelect(currSelectFieldEl);
	var currSelectedDataType = currSelectFieldEl.options[currSelectFieldEl.selectedIndex].getAttribute("dataType");
	if(fieldValueEl && (fieldValueEl.value.trim() == '' || (fieldValueEl.value.trim() != '' && Element.hasClassName(fieldValueEl, 'grayTips')))){
		Element.addClassName(fieldValueEl, 'grayTips');
		if(currSelectedDataType == 'string'){
			 fieldValueEl.value = wcm.LANG['CHANNEL_108'] || '字符串：例如 test';
		}else if(currSelectedDataType == 'int'){
			 fieldValueEl.value = wcm.LANG['CHANNEL_109'] || '整型：例如 5';
		}else if(currSelectedDataType == 'date'){
			 fieldValueEl.value = wcm.LANG['CHANNEL_110'] || '日期：例如 2010-1-22';
		}
	}
}
function fieldValueElFocus(){
	if(Element.hasClassName(this, 'grayTips')) { 
         Element.removeClassName(this, 'grayTips')
		 this.value = ""; 	
    }
}
function fieldValueElBlur(){
	if(!Element.hasClassName(this, 'grayTips') && this.value.trim() == '') {
		var selectEl = getFielaSelectElByInputEl(this);
        initTipValueElByFieldSelectEl.bind(selectEl, this)();
    }
}
function getFielaSelectElByInputEl(inputEl){
	var selectEl = null;
	var firstParentEl = inputEl.parentNode.parentNode;
	if(firstParentEl.tagName.toUpperCase() == 'DIV'){
		selectEl = firstParentEl.getElementsByTagName('select')[0];
	}
	return selectEl;
}
function getValueElByFieldSelect(selectEl){
	var inputEl = null;
	var firstParentEl = selectEl.parentNode.parentNode;
	if(firstParentEl.tagName.toUpperCase() == 'DIV'){
		inputEl = firstParentEl.getElementsByTagName('input')[0];
	}
	return inputEl;
}
function canAnalyse(selValue){
	if(selValue.indexOf('(') >0 && selValue.toLowerCase().indexOf('to_date(') < 0 && selValue.toLowerCase().indexOf('timestamp(') < 0) return false;
	if(selValue.indexOf(' and ')>0 && selValue.indexOf(' or ')>0) return false;
	return true;
}
function initSelectModeByValue(conditionComponet){
	if(conditionComponet.trim()=='')return;
	new Insertion.Bottom($('dvContent'), $('template').innerHTML);
	var newNode = Element.last($('dvContent'));
	var selectEls = newNode.getElementsByTagName('select');
	var inputEls  = newNode.getElementsByTagName('input');
	if(selectEls[1]){
		var compareType = null;
		var operTypeOptions = selectEls[1].options;
		//开始寻找比较的方式，找到后对选择框进行初始化
		for(var i=0; i<operTypeOptions.length; i++){
			if(conditionComponet.indexOf(operTypeOptions[i].value)<=0){
				continue;
			}else{
				operTypeOptions[i].selected = true;
				compareType = operTypeOptions[i].value;
				break;
			}
		}
		if(compareType == null) return;
		//开始初始化字段和比较值
		var fieldAndValue = conditionComponet.trim().split(compareType);
		if(fieldAndValue[0].trim()!=''){
			initSelectEl(selectEls[0], fieldAndValue[0]);
		}
		fieldAndValue[1] = fieldAndValue[1].trim();
		if(fieldAndValue[1]!=''){
			if(fieldAndValue[1].indexOf("'")==0 || fieldAndValue[1].indexOf('"')==0)
				fieldAndValue[1] = fieldAndValue[1].substr(1, fieldAndValue[1].length-1);
			var strLength = fieldAndValue[1].length;
			if(fieldAndValue[1].indexOf("'")== (strLength -1) || fieldAndValue[1].indexOf('"') == (strLength-1))
				fieldAndValue[1] = fieldAndValue[1].substr(0, fieldAndValue[1].length-1);
			if(compareType == 'like' && (fieldAndValue[1].indexOf("%")== 0 || fieldAndValue[1].indexOf("%")== fieldAndValue[1].length)){
				inputEls[0].value = fieldAndValue[1].substr(1, fieldAndValue[1].length-2);
				return;
			}
			if(fieldAndValue[1].toLowerCase().indexOf('to_date(') >= 0 || fieldAndValue[1].toLowerCase().indexOf('timestamp(') >= 0){
				fieldAndValue[1] = (fieldAndValue[1].match(/(\d{4})-(\d{1,2})-(\d{1,2})/))[0];
				inputEls[0].value = fieldAndValue[1];
				return;
			}
			else  inputEls[0].value = fieldAndValue[1];
		}
	}
}
function submit(){
	var lastSubmitData;
	if($('inputModeContainer').style.display == ''){
		lastSubmitData = $('inputValue').value;
	}else{
		lastSubmitData = getSelectSubmitData();
		if(!lastSubmitData) return false;
	}
	var tipMessage = $('inputModeContainer').style.display == '' ? "您确定要采用输入模式的值吗？" : "您确定要采用选择模式的值吗？";
	if(!confirm(tipMessage)){
		if($('inputModeContainer').style.display == ''){ 
			lastSubmitData = getSelectSubmitData();
			if(!lastSubmitData) return false;
		}
		else lastSubmitData =  $('inputValue').value;
	}
	var cbr = wcm.CrashBoarder.get("selectSearchConditonWay");
	cbr.notify(lastSubmitData);
	return false;
}
function getDateSqlValue(_dateValue){
	var rightDateValue = _dateValue;
	var sqlType = <%=currType%>;
	switch (sqlType){
		case 1: //oracle
		   rightDateValue = "to_date('"+rightDateValue+"','yyyy-mm-dd')";
			break;
		case 2: //sqlserver
		case 4: //Sybase
		case 7://mysql
			rightDateValue = "'" + rightDateValue + "'"
			break;
		case 3://DB2UDB
		case 5://DB2UDB2
		case 6://DB2AS400
			rightDateValue = "TIMESTAMP('"+rightDateValue + " 00:00:00')";
			break;
		default:
			rightDateValue = "'" + rightDateValue + "'"
	}
	return rightDateValue;
}
function validValueType(_dataType, valueEl){
	_dataType = _dataType.trim();
	var sValue = valueEl.value;
	if(_dataType == 'int' && isNaN(sValue)){
		valueEl.focus();
		return false;
	}else if(_dataType == 'date'){
		var regExp = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
		var result = sValue.match(regExp);
		if(!result){
			valueEl.focus();
			alert('输入的日期格式不正确，正确的类型为[yyyy-mm-dd] ！');
			return false;
		}
		var year = parseInt(result[1], 10);
		var month = parseInt(result[2], 10);
		var day = parseInt(result[3], 10);
		if(month < 1 || month > 12){
			alert("月份应该为1到12的整数"); 
			return false;
		}
		if (day < 1 || day > 31){
			alert("每个月的天数应该为1到31的整数"); 
			return false;
		}
		if ((month==4 || month==6 || month==9 || month==11) && day==31){   
			alert("该月不存在31号");   
			return false;
		}   
		if (month==2){   
			var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));   
			if (day>29){   
				alert("2月最多有29天");  
				return false;
			}   
			if ((day==29) && (!isleap)){   
				alert("闰年2月才有29天");   
				return false;
			}   
		} 
	}
	return true;
}
function getSelectSubmitData(){
	var submitData = "";
	var fieldSelectEls = document.getElementsByName('SearchConField');
	var waySelectEls = document.getElementsByName('SearchConWay');
	var valueInputEls = document.getElementsByName('fieldValue');
	var conditionType = getRadioValue('conditonType');
	for(var index=0; index<fieldSelectEls.length-1; index++){
		if(fieldSelectEls[index].value.trim() == '' || valueInputEls[index].value.trim() == '' || waySelectEls[index].value.trim() == '' ||  Element.hasClassName(valueInputEls[index], 'grayTips')){
			//alert((wcm.LANG['CHANNEL_82'] || "第[") + (index + 1) + (wcm.LANG['CHANNEL_83'] || "]个设置为空！"));
			alert(String.format("第{0}个设置为空！", 
				index + 1));
			return false;
		}
		var dataType = fieldSelectEls[index].options[fieldSelectEls[index].selectedIndex].getAttribute("dataType");
		if(dataType == null){
			alert("设置的值初始化没有找到对应的类型，请切换到输入模式进行编辑！");
			return false;
		}
		if(!validValueType(dataType, valueInputEls[index])){
			if(dataType == 'date'){
				return false;
			}
			var errMsg = String.format("第[{0}]个设置的值类型不正确，正确的类型为{1}",
				index + 1,dataType);
			//if(dataType == 'date')errMsg+="[yyyy-mm-dd]";
			alert(errMsg);
			return false;
		}
		if(index > 0){
			submitData+=" " + conditionType + " " +  fieldSelectEls[index].value;
		}else{
			submitData+=fieldSelectEls[index].value;
		}
		if(waySelectEls[index].value.trim() == 'like'){
			submitData+=" like '%" + valueInputEls[index].value.trim() +"%'";
		}else if(dataType == 'date'){
			var dateValue = getDateSqlValue(valueInputEls[index].value.trim());
			submitData+=' ' + waySelectEls[index].value + ' ' + dateValue;
		}else{
			submitData+=' ' + waySelectEls[index].value + ' ' + "'" + valueInputEls[index].value.trim() + "'";
		}
	}
	return submitData;
}
function cancel(){
	var cbr = wcm.CrashBoarder.get("selectSearchConditonWay");
	cbr.notify("");
	return false;
}
function addSelectDiv(currEl){
	new Insertion.Bottom($('dvContent'), $('template').innerHTML);
	var newNode = Element.last($('dvContent'));
	var newSelectEls = newNode.getElementsByTagName('select');
	var newInputEls = newNode.getElementsByTagName('input');
	newSelectEls[0].focus();
	//绑定事件
	Event.observe(newSelectEls[0], 'change', initTipValueElByFieldSelectEl.bind(newSelectEls[0], null));
	Event.observe(newInputEls[0], 'focus', fieldValueElFocus.bind(newInputEls[0]));
	Event.observe(newInputEls[0], 'blur', fieldValueElBlur.bind(newInputEls[0]));
}
function deleteSelectDiv(currEl){
	var tempNode = getDeleteSelectDiv(currEl);
	if(tempNode == null) return;
	if(Element.previous(tempNode) || Element.next(tempNode)){
		Element.remove(tempNode);
	}
}
function getDeleteSelectDiv(currEl){
	var parentEl = currEl.parentNode;
	if(parentEl.tagName.toLowerCase() != 'div')
	   return null;
	else return parentEl;
}
function initSelectEl(_selectEL, _value){
	if(_selectEL.tagName.toUpperCase() != 'SELECT' || _value.trim() == '')
		return;
	else{
		var optionEls = _selectEL.options;
		var bFindTargetOption = false;
		for(var i=0; i<optionEls.length; i++){
			if(optionEls[i].value != _value.trim().toLowerCase())continue;
			else {
				optionEls[i].selected = true;
				bFindTargetOption = true;
				break;
			}
		}
		if(!bFindTargetOption){
			var varItem = new Option(_value.trim(), _value.trim());  
			_selectEL.options.add(varItem);
			varItem.selected = true;
		}
	}
}
function changgeToInputMode(){
	$('dvContainer').style.display = 'none';
	$('inputModeContainer').style.display = '';
}
function changgeToSelectMode(){
	$('inputModeContainer').style.display = 'none';
	$('dvContainer').style.display = '';
}
function forceReplain(){
	var fieldSelectEls = document.getElementsByName('SearchConField');
	for(var index=0; index<fieldSelectEls.length; index++){
		var el = fieldSelectEls[index];
		Element.removeClassName(el, 'xx');
		Element.addClassName(el, 'xx');
	}
	var waySelectEls = document.getElementsByName('SearchConWay');
	for(var i=0; i<waySelectEls.length; i++){
		var el = waySelectEls[i];
		Element.addClassName(el, 'xx');
		Element.removeClassName(el, 'xx');
	}	
}
function getRadioValue(_sName){
	var elms = document.getElementsByName(_sName);
	if(elms == null || elms.length <= 0) {
		return null;
	}
	//else
	for (var i = 0; i < elms.length; i++){
		var elm = elms[i];
		if(elm.checked) {
			return elm.value;
		}
	}
	return null;
}
</script>
</BODY>

</HTML>