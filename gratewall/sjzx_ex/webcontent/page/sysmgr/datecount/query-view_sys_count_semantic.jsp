<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询日增量查询列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<% 
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	String selectKeySysName = context.getRecord("select-key").getValue("sys_name");
	String selectKeyTableName  = context.getRecord("select-key").getValue("table_name_cn");
	String classState  = context.getRecord("select-key").getValue("class_state");
%>
</head>

<script language="javascript">

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var selectObject = document.getElementById("select-key:sys_name");
	selectObject.onchange = function(){
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
		setStateObj();
	}
	var tblObj = document.getElementById("select-key:table_name_cn");
	tblObj.onchange = function(){
		setStateObj();
	}
	
	initClassState();
	
	queryAllZT();
	var nos=document.getElementsByName('span_record:no');
	var currentPage="<freeze:out value='${attribute-node.record_start-row}'/>";
	for(var i=0;i<nos.length;i++){
	    nos[i].innerHTML=parseInt(currentPage)+i;
	}
	
	//var countDate = getFormFieldValue("select-key:count_date");
	//document.getElementById("label:record:count_incre").innerText = countDate + " 增量";
	//document.getElementById("label:record:count_full").innerText = countDate + " 全量";
}

function initClassState(){
	var class_state = document.getElementById("select-key:class_state");
	var selectKeyState = '<%=classState%>';
	var option = document.createElement("option");
    option.value = "1";
    var text = document.createTextNode("开业");
	option.appendChild(text);
	if(selectKeyState == '1'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "2";
    var text = document.createTextNode("吊销");
	option.appendChild(text);
	if(selectKeyState == '2'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "3";
    var text = document.createTextNode("注销");
	option.appendChild(text);
	if(selectKeyState == '3'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	
}

//查询所有主题信息
function queryAllZT(){
	$.get("<%=request.getContextPath()%>/txn50202006.ajax", function(xml){
		var selectObject = document.getElementById("select-key:sys_name");
		if (selectObject){
			//首先清空下拉框
			selectObject.innerHTML = "";
			//创建默认选项
			var option = document.createElement("option");
			option.value = "";
			var text = document.createTextNode("全部");
			option.sysname = '';
	        option.appendChild(text);
	        selectObject.appendChild(option);
	        
	        //循环添加所有主题信息
			var nodeArray = xml.selectNodes("//record");
			for (var i = 0; i < nodeArray.length; i++){
	            var nodeElement = nodeArray.item(i);
	            var optionValue = nodeElement.selectSingleNode("sys_id");
	            var optionText = nodeElement.selectSingleNode("sys_name");
	            var option = document.createElement("option");
	            option.value = optionText.text;
	            var text = document.createTextNode(optionText.text);
	            if('<%=selectKeySysName%>' == optionText.text){
	            	option.selected = true;
	            }
				option.sysname = optionValue.text;
	            option.appendChild(text);
	            selectObject.appendChild(option);
            }
		}
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
	});
}

function queryZTTables(ztParam){
	if(ztParam != ''){
		$.get("<%=request.getContextPath()%>/txn60210008.ajax?record:sys_id=" + ztParam, function(xml){
			var selectObject = document.getElementById("select-key:table_name_cn");
			if (selectObject){
				//首先清空下拉框
				selectObject.innerHTML = "";
				//创建默认选项
				var option = document.createElement("option");
				option.value = "";
				var text = document.createTextNode("全部");
		        option.appendChild(text);
		        selectObject.appendChild(option);
		        
		        //循环添加所有表信息
				var nodeArray = xml.selectNodes("//record");
				for (var i = 0; i < nodeArray.length; i++){
		            var nodeElement = nodeArray.item(i);
		            var optionValue = nodeElement.selectSingleNode("table_no");
		            var optionText = nodeElement.selectSingleNode("table_name_cn");
		            var option = document.createElement("option");
		            option.value = optionText.text;
		            var text = document.createTextNode(optionText.text);
		            if('<%=selectKeyTableName%>' == optionText.text){
		            	option.selected = true;
		            }
		            option.appendChild(text);
		            selectObject.appendChild(option);
	            }
			}
		});
	}
}

function func_doQuery(){
	var date = getFormFieldValue("select-key:count_date");
	if(date.trim() == ''){
		alert("请选择【统计日期】");
		return;
	}
	document.forms[0].submit();
}

function reset(){
	document.forms[0].reset();
	var date = new Date();
	
	setFormFieldValue("select-key:count_date",' ');
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询日增量查询列表"/>
<freeze:errors/>

<freeze:form action="/txn81200001">
  <freeze:block property="select-key" caption="查询条件" width="95%">
      <freeze:button name="record_addRecord" caption=" 查 询 " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
      <freeze:button name="record_addRecord" caption=" 重 填 " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
      <freeze:select property="sys_name" caption="主题名称" style="width:90%"/>
      <freeze:select property="table_name_cn" caption="表中文名" style="width:90%"/>
      <freeze:datebox property="count_date" caption="统计日期" numberformat="1" style="width:90%" />
      <freeze:select property="class_state" caption="企业状态" style="width:90%" />
  </freeze:block>
  <br/>

	   <freeze:grid property="record" caption="日增量列表" width="95%" keylist="ad_mon_bc_rept_id" 
	             checkbox="false" navbar="bottom" >
	   <freeze:button name="record_addRecord" caption="导出"  enablerule="0" hotkey="ADD" align="right" onclick="toExport();"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
       <freeze:button name="record_updateRecord" caption="返回"  enablerule="0" hotkey="UPDATE" align="right" onclick="func_record_goBack();"/>

	      <freeze:cell property="no" caption="序号" style="width:5%" align="center" />
	      <freeze:cell property="sys_name" caption="主题名称" style="width:15%" />
	      <freeze:cell property="table_name_cn" caption="表中文名" style="width:10%" />
	      <freeze:cell property="table_name" caption="表名" style="width:15%" />
	      <freeze:cell property="class_sort" caption="主体类型" style="width:7%" />
	      <freeze:cell property="class_state" caption="主体状态" style="width:7%" />
	      <freeze:cell property="count_date" caption="统计日期" style="width:10%" />
	      <freeze:cell property="count_full" caption="当日全量" style="width:7%" />   
	      <freeze:cell property="count_incre" caption="当日增量" style="width:7%" />    
	  </freeze:grid>
  
</freeze:form>
<form method="post" action="<%=request.getContextPath()%>/sysmgr/datecount/query-view_sys_count_semantic_his_download.jsp" target="_blank" >
	<input type="hidden" id="htmlStr" name="htmlStr" />
</form>
</freeze:body>
<script language="javascript">
function setStateObj(){
	var sysname = getFormFieldValue("select-key:sys_name");
	var tblname = getFormFieldValue("select-key:table_name_cn");
	var sysArray = ['企业登记主题','个体登记主题','法人库主题'];
	var tblArray = ['企业(机构)','主要人员','投资人','个体工商户基本表','质监法人库'];
	
	if(sysname != ''){//如果主题不是“全部”
		if(exist(sysArray, sysname)){//判断是否是有状态的主题
			if(tblname != ''){//如果表不是“全部”
				if(exist(tblArray, tblname)){//判断是否是有状态的表
					setFormFieldDisabled("select-key:class_state", 0, false);
				}else{
					setFormFieldDisabled("select-key:class_state", 0, true);
				}
			}else{
				setFormFieldDisabled("select-key:class_state", 0, false);
			}
		}else{
			setFormFieldDisabled("select-key:class_state", 0, true);
		}
	}else{
		setFormFieldDisabled("select-key:class_state", 0, false);
	}
}

function exist(arrayObj, findValue){
	if(arrayObj){
		for(var i = 0; i < arrayObj.length; i++){
			if(findValue == arrayObj[i]){
				return true;
			}
		}
	}
	return false;
}

function toExport(){
    clickFlag=0;
    document.forms[0].action   = '/txn81200006.do';
    document.forms[0].submit();
}

// 返 回
function func_record_goBack()
{
    clickFlag=0;
	//goBack();	// /txn80002501.do
	var page = new pageDefine( "/txn62010030.do", "数据增长统计" );
	page.updateRecord();
}
</script>
</freeze:html>
