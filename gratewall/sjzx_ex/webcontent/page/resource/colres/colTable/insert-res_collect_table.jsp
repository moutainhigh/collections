<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="700">
<head>
<title>增加采集数据表信息</title>
<style type="text/css">
#dataitem_tb table{
	width: 95%;
}
</style>
</head>

<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "资源管理_数据项类型");
String tmp = "";
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "资源管理_对应代码表");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var table_id="";
// 保 存
function func_record_saveRecord(){
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'表名称')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "检查数据表名是否已经使用");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("表名称已存在，请重新起名");
  		}else{
  		  var item=getFormFieldValue('record:service_targets_id');
  		  if(!checkItem(item,"100","所属服务对象")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_en');
  		  if(!checkItem(item,"100","表名称")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_cn');
  		  if(!checkItem(item,"100","表中文名称")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_type');
  		  if(!checkItem(item,"100","表类型")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_desc');
  		  if(!checkItemLength(item,"1000","表描述")){
  		    return false;
  		  }
	      saveRecord( '', '保存采集数据表信息表' );
  		}
}
function insertTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    table_id=_getXmlNodeValues(xmlResults,'record:collect_table_id');
		    setFormFieldValue("record:collect_table_id",table_id);
		    setFormFieldValue("primary-key:collect_table_id",table_id);
		    alert("保存成功!");
		}
}


// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存采集数据表信息表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'表名称')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "检查数据表名是否已经使用");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("表名称已存在，请重新起名");
  		}else{
  		   saveAndExit( '', '保存采集数据表信息表' );
  		}
}

// 返 回
function func_record_goBack()
{
	//var page = new pageDefine( "/txn20201001.do", "查询采集数据表");
	//page.updateRecord();
	goBack("/txn20201001.do");	// /txn20201001.do
}
// 增加采集数据项表信息 
function func_record_addRecord()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    if(collect_table_id==null||collect_table_id==""){
	    alert("请先填写采集表信息!");
	    clickFlag=0;
    }else{
    	clickFlag=0; //忽略烽火台的按钮限制
    	//自定义按钮禁用状态
    	now_adding = 1;
    	$('#btn_add').removeClass('btn_add').addClass('btn_add_disabled');
    	$('#btn_add')[0].onclick = function(){
    		alert("请完成本次添加后再次操作.");
    	};
    	var data = getFormFieldValue('record:collect_table_id');
    	$("#dataitem_tb").tablet("insertRow", data);
	}
	 
}

//保存数据项信息
function saveDataItem() {
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	now_line_no = rowIndex;
	var dataitem_id = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
			.find("td:last").find("input[name='collect_dataitem_id']")
			.val();
	if (typeof dataitem_id == 'undefined') {
		var en = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")").find(
				"td:eq(1)").text();
		if (!checkEnName(en, '数据项名称')) {
			return false;
		}
		var dataitem_type = $("#dataitem_tb").find(
				"tr:eq(" + rowIndex + ")").find("td:eq(3)").find(
				'select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find(
				"tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
		if (!checkItemTypeLength(dataitem_type, dataitem_long)) {//校验数据项长度
			return false;
		}
		if (!check_int(dataitem_long)) {//校验数据项长度
			return false;
		}
		var page = new pageDefine("/txn20202000.ajax", "检查数据项名是否已经使用");
		page.addValue(en, "select-key:dataitem_name_en");
		page.addValue(dataitem_id, "select-key:collect_dataitem_id");
		page.addParameter("record:collect_table_id", "select-key:collect_table_id");
		row_index = rowIndex;
		page.callAjaxService('nameCheckCallback3');
	} else {
		var page = new pageDefine("/txn20202002.ajax", "修改采集数据项信息");
		page.addValue(dataitem_id, "primary-key:collect_dataitem_id");
		page.addValue(dataitem_id, "record:collect_dataitem_id");
		var collect_table_id = getFormFieldValue('record:collect_table_id');
		//page.addValue(collect_table_id, "record:collect_table_id");
		//page.updateRecord();
		var dataitem_name_en = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(1)").text();
		var dataitem_name_cn = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(2)").text();
		var dataitem_type = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(3)").find( 'select option:selected').val();
		var dataitem_long = $("#dataitem_tb").find( "tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
		var is_key = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")") .find("td:eq(5)").find('select option:selected').val();
		var code_table = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")") .find("td:eq(6)").find('select option:selected').val();
		var last_modify_id = getFormFieldValue('record:last_modify_id');
		var last_modify_time = getFormFieldValue('record:last_modify_time');
		page.addValue(collect_table_id, "record:collect_table_id");
		page.addValue(dataitem_name_en, "record:dataitem_name_en");
		page.addValue(dataitem_name_cn, "record:dataitem_name_cn");
		page.addValue(dataitem_type, "record:dataitem_type");
		page.addValue(dataitem_long, "record:dataitem_long");
		page.addValue('Y', "record:is_markup");
		page.addValue(is_key, "record:is_key");
		page.addValue(code_table, "record:code_table");
		page.addValue("", "record:dataitem_long_desc");
		//page.addValue(last_modify_id, "record:last_modify_id");
		//page.addValue(last_modify_time, "record:last_modify_time");
		page.callAjaxService('saveAndCon');
	}
}
//检查数据项名称
function nameCheckCallback3(errCode, errDesc, xmlResults) {
	is_name_used = 1;
	if (errCode != '000000') {
		alert('处理错误[' + errCode + ']==>' + errDesc);
		return;
	}
	is_name_used = _getXmlNodeValues(xmlResults, 'record:name_nums');
	
	if (is_name_used[0] > 0) {
		alert("数据项已存在，请重新起名");
	} else {
		if (row_index != -1) {
			var rowIndex = row_index;
			var collect_table_id = getFormFieldValue('record:collect_table_id');
			var dataitem_name_en = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(1)").text();
			var dataitem_name_cn = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(2)").text();
			var dataitem_type = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(3)").find(
					'select option:selected').val();
			var dataitem_long = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(4)").text();
			var is_key = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
					.find("td:eq(5)").find('select option:selected').val();
			var code_table = $("#dataitem_tb").find(
					"tr:eq(" + rowIndex + ")").find("td:eq(6)").find(
					'select option:selected').val();
			var last_modify_id = getFormFieldValue('record:last_modify_id');
			var last_modify_time = getFormFieldValue('record:last_modify_time');

			var page = new pageDefine("/txn20202008.ajax", "保存采集数据项信息");
			page.addValue(collect_table_id, "record:collect_table_id");
			page.addValue(dataitem_name_en, "record:dataitem_name_en");
			page.addValue(dataitem_name_cn, "record:dataitem_name_cn");
			page.addValue(dataitem_type, "record:dataitem_type");
			page.addValue(dataitem_long, "record:dataitem_long");
			page.addValue(is_key, "record:is_key");
			page.addValue(code_table, "record:code_table");
			page.addValue("", "record:dataitem_long_desc");
			page.addValue(last_modify_id, "record:last_modify_id");
			page.addValue(last_modify_time, "record:last_modify_time");
			page.callAjaxService('saveAndCon');
		}
	}
}

function saveAndCon(errCode, errDesc, xmlResults) {
	is_name_used[0] = 1;
	if (errCode != '000000') {
		alert('处理错误[' + errCode + ']==>' + errDesc);
		return;
	} else {
		alert("操作成功!");
		var dataItem_id = _getXmlNodeValues(xmlResults, 'record:collect_dataitem_id');
		dataItem_id = dataItem_id[0];
		var dataitem_id = $("#dataitem_tb").find("tr:eq(" + now_line_no + ")")
				.find("td:last").append("<input name='collect_dataitem_id' type='hidden' value='"+dataItem_id+"' />");
		row_index = -1;
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
		now_line_no = -1;
	}
	namechecked = true;
}

function deleteDataItem() {
	var e = event.srcElement;
	var rowIndex = e.parentNode.parentNode.parentNode.rowIndex;
	if (now_adding == 1) {
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass('btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
	} else if (now_adding == -1) {
		var dataitem_id = $("#dataitem_tb").find("tr:eq(" + rowIndex + ")")
				.find("td:last").find("input[name='collect_dataitem_id']")
				.val();
		if (typeof dataitem_id != 'undefined') {
			var page = new pageDefine("/txn20202005.do", "删除采集数据项信息");
			page.addValue(dataitem_id, "primary-key:collect_dataitem_id");
			page.callAjaxService("deleteBack");
			row_index = rowIndex;
		}
	}
}

function deleteBack(errCode, errDesc, xmlResults) {
	if (errCode != '000000') {
		alert('处理错误[' + errCode + ']==>' + errDesc);
		return;
	} else {
		alert("删除成功!");
		rowIndex = row_index;
		row_index = -1;
		$("#dataitem_tb").find("tr:eq(" + rowIndex + ")").remove();
		now_adding = -1;
		$('#btn_add').removeClass('btn_add_disabled').addClass(
				'btn_add');
		$('#btn_add')[0].onclick = function() {
			func_record_addRecord();
		}
	}
}

// 修改采集数据项表信息
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn20202004.do", "修改采集数据项信息" );
	page.addValue(idx,"primary-key:collect_dataitem_id");
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	
	page.updateRecord();
}

// 删除采集数据项表信息
function func_record_deleteRecord(idx)
{

	var page = new pageDefine( "/txn20202005.do", "删除采集数据项信息" );
	if(confirm("是否删除选中的记录")){
		page.addValue(idx,"primary-key:collect_dataitem_id");
		page.updateRecord();
	}
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20202006.do", "查看采集数据项信息", "modal" );
	page.addValue(idx, "primary-key:collect_dataitem_id" );
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.updateRecord();
}

// 生成表
function func_record_createTable()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    //var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
    var ids = new Array;
    $('#dataitem_tb').find("tr:gt(0)").each(function(){
    	ids.push($(this).find("td:eq(1)").text());
    })
    
    if(collect_table_id==null||collect_table_id==""){
	    alert("请先填写采集表信息!");
	    clickFlag=0;
    }else if(now_adding==1){
        alert("请先保存数据项!");
    }else if(ids==null||ids.length==0){
        alert("请先填写数据项信息!");
	    clickFlag=0;
    }
    else{
        //var key=getFormAllFieldValues("dataItem:is_key");
        var key = new Array;
        $('#dataitem_tb').find("tr:gt(0)").each(function(){
        	key.push($(this).find("td:eq(5) select option:selected").val());
        })
        var num=0;
	    if(ids!=null){
	    for(i=0;i<ids.length;i++){
	    if(key[i]=='是'){
	       num=num+1;
	      }
	     }
	    }
        if(num>1){
	        alert("只能有一个数据项是主键!");
		    clickFlag=0;
		    return false;
        }
    
        var page = new pageDefine( "/txn20201009.ajax", "生成采集数据表!");
        page.addParameter("record:table_name_en","primary-key:table_name_en");
	    page.callAjaxService('creatTableCheck');
	}
}
function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
		
		if(confirm("采集库已存在此数据表名称且数据表里已有数据，是否继续生成该数据表?")){
		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("采集库已存在此数据表名称且数据表里已有数据,不能再生成该表!");
	  		//return false;
  		}else if(is_name_used==-1){
  		
	  		if(confirm("采集库已存在此数据表名称,但数据表里没有数据，是否继续生成该数据表?")){
			   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
			
			//alert("采集库已存在此数据表,不能再生成该表!");
  			//return false;
  		}else{
  		 if(confirm("是否生成数据表?")){
	  		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
  		   }
  		}
}
function creatTable(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("生成表成功!");
		}
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var collect_table_id = getFormAllFieldValues("record:collect_table_id");
	
	if(collect_table_id!=""){
	  alert("保存成功!");
	}
	
	var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	  
	   htm+='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	var data = new Array;

	data.push({
		"rowIndex" : "序号",
		"dataitem_name_en" : "数据项名称",
		"dataitem_name_cn" : "中文名称",
		"dataitem_type" : "数据项类型",
		"dataitem_long" : "数据项长度",
		"is_key" : "是否主键",
		"code_table" : "对应代码表",
		"oper" : "操作"
	});
	var options = {
		data : data,
		editable : true,
		shownum : 10,
		lineNum : false,
		isAlias : false,
		addDelete : false,
		dataitem_type: dataitem_types,
		code_table: code_table_list
	}
	$("#dataitem_tb").tablet(options);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201003" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>
  <freeze:block property="record" caption="采集数据表信息" width="95%">
  	  <freeze:button name="record_saveRecord" caption="保存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_createTable" caption="生成表" hotkey="SAVE" onclick="func_record_createTable();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="表名称" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名称" datatype="string"  notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="表类型" show="name" valueset="资源管理_表类型" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="表描述" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="table_status" caption="表状态" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="采集来源" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="采集库是否生成采集表" datatype="string"  style="width:95%"/>
  </freeze:block>
  <br>
<%--    <freeze:grid property="dataItem" caption="采集数据项列表" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:button name="record_addRecord" caption="添加数据项" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID"  />
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="中文名称" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="数据项类型"  show="name" valueset="资源管理_数据项类型" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键" valueset="资源管理_是否主键" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="是否代码表" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" style="width:12%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="数据项描述" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid> --%>
  
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td><div title="增加数据项" onclick="func_record_addRecord();" id="btn_add" class="btn_add"></div></td></tr>
  </table>
  <div id="dataitem_tb"></div>
</freeze:form>

</freeze:body>
</freeze:html>
