<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改采集数据库信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	//saveAndExit( '', '保存采集数据库表' );	// /txn30501001.do
	var collect_mode = getFormFieldValue("record:collect_mode");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	var collect_table = getFormFieldValue("record:collect_table");
	var source_collect_column = getFormFieldValue("record:source_collect_column");
	
	if(collect_mode==null || collect_mode==""){
		alert("【采集方式】不能为空!");
		return;
	}
	
	if(collect_table==null || collect_table==""){
		alert("【目标采集表】不能为空!");
		return;
	}
	if(source_collect_table==null || source_collect_table==""){
		alert("【源采集表】不能为空!");
		return;
	}
	
	if(collect_mode=="1"){//增量
		if(source_collect_column==null || source_collect_column==""){
			alert("【增量字段】不能为空!");
			return;
		}	
	}else if (collect_mode=="2"){//全量
		setFormFieldValue("record:source_collect_column",0,"");
	}
	
	
	var page = new pageDefine( "/txn30501006.ajax", "测试数据库任务是否重复" );
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:source_collect_table","record:source_collect_table");
	page.addParameter("record:collect_table","record:collect_table");
	page.callAjaxService("checkTaskBack");
	
}

function checkTaskBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var task_num = _getXmlNodeValue(xmlResults,"record:task_num");
		if(task_num>0){
			alert("任务重复！");
			return;
		}else{
			saveAndExit( '', '保存采集数据库表' );	// /txn30501001.do
		}
	}
}


// 返 回
function func_record_goBack()
{
	goBack();	// /txn30501001.do
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function getParameterForTable(){
	//从当前页面取值，组成key=value格式的串  CollectConstants.TYPE_CJLX_FILEUPLOAD值01是数据源中数据库类型
	var data_source_id = getFormFieldValue("record:data_source_id");
	
	if(data_source_id==null || data_source_id == ""){
		alert("请先选择数据源！");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//从当前页面取值，组成key=value格式的串  CollectConstants.TYPE_CJLX_FILEUPLOAD值01是数据源中数据库类型
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	if(source_collect_table==null || source_collect_table == ""){
		alert("请先选择【源采集表】！");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id')+"&input-data:source_collect_table="+ getFormFieldValue('record:source_collect_table');
		return parameter;
	}
}

function funTBChanged(){
	var collect_mode = getFormFieldValue('record:collect_mode');
	
	if(collect_mode=='1'){//增量
	setDisplay("2",true);
	
	}else if(collect_mode=='2'){//全量
	setDisplay("2",false);
	
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd12';
		}
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged();">
<freeze:title caption="修改采集数据库信息"/>
<freeze:errors/>

<freeze:form action="/txn30501002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="database_task_id" caption="采集数据库主键ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改采集数据库信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="所属服务对象"   style="width:95%"/>
      <freeze:hidden property="data_source_id" caption="数据源"    style="width:95%"/>
      <freeze:cell property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" onchange="funTBChanged();" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>  
      <freeze:browsebox property="source_collect_table" caption="源采集表"  valueset="采集任务_根据数据源取对方库中的所有表" notnull="true" parameter="getParameterForTable();"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="目标采集表"  notnull="true" maxlength="32" valueset="资源管理_服务对象对应采集表" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:browsebox property="source_collect_column" caption="增量字段" valueset="采集任务_根据数据源取对方库中表的字段信息"  parameter="getParameterForTableColumn();" colspan="2"  maxlength="100"  style="width:38.5%"/>
      <freeze:textarea property="description" caption="说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19"  style="width:95%"/>
      <freeze:hidden property="database_task_id" caption="采集数据库主键ID" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
