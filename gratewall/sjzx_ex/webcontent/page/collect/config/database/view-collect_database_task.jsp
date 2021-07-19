<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>查看采集数据库信息</title>
</head>

<script language="javascript">

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集数据库表' );	// /txn30501001.do
	//var page = new pageDefine("/txn20202000.ajax", "检查数据项名是否已经使用");	
	//page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	//page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	//page.callAjaxService('nameCheckCallback');
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
	
	alert("data_source_id="+data_source_id);
	if(data_source_id==null || data_source_id == ""){
		alert("请先选择数据源！");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:_tmp_data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//从当前页面取值，组成key=value格式的串  CollectConstants.TYPE_CJLX_FILEUPLOAD值01是数据源中数据库类型
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	alert("source_collect_table="+source_collect_table);
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
<freeze:body>
<freeze:title caption="查看采集数据库信息"/>
<freeze:errors/>

<freeze:form action="/txn30501003">
  <freeze:block property="record" caption="查看采集数据库信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="所属服务对象"   style="width:95%"/>
      <freeze:hidden property="data_source_id" caption="数据源"    style="width:95%"/>
      <freeze:cell property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="采集方式"  show="name"  valueset="资源管理_采集方式" style="width:95%"/>  
      <freeze:cell property="source_collect_table" caption="源采集表"   style="width:95%"/>
      <freeze:cell property="collect_table" caption="目标采集表"  valueset="资源管理_采集表" style="width:95%"/>
      <freeze:cell property="source_collect_column" caption="增量字段"  colspan="2" style="width:38.5%"/>
      <freeze:cell property="description" caption="说明" colspan="2"  style="width:98%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19"  style="width:95%"/>
      <freeze:hidden property="database_task_id" caption="采集数据库主键ID" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
