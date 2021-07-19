<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="1000" height="400">
<head><title>手动执行采集任务</title></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

function RunManual(){
	var page = new pageDefine( "/txn30182103.ajax","执行");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addParameter("record:collect_type","record:collect_type");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.addParameter("record:file_name_en","record:file_name_en");
	page.addParameter("record:collect_table","record:collect_table");
	page.addParameter("record:collect_mode","record:collect_mode");
	page.addParameter("record:file_sepeator","record:file_sepeator");
	page.callAjaxService("resultBack");
}

function resultBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var count=_getXmlNodeValue(xmlResults, 'rsBack:count');
		var errorMsg=_getXmlNodeValue(xmlResults, 'rsBack:errorMsg');
		alert("执行结束 \n插入数据条数："+count+"\n返回信息："+errorMsg);		
	}
}

//根据任务类型显示对应的配置参数
function showParams(){
	var collectType = getFormFieldValue("record:collect_type");
	if(collectType=='02'){
		$('#row_4').show();
	}else{
		$('#row_4').hide();
	}
	
	
}
function getSourceParameter(){
	//从当前页面取值，组成key=value格式的串
	var collectType = getFormFieldValue("record:collect_type");
	var service_targets_id=getFormFieldValue('record:service_targets_id');
	if(!(service_targets_id && collectType)){
		alert("请先选择服务对象与采集类型!");
		return;
	}
    var parameter = "input-data:service_targets_id="+ service_targets_id+"&input-data:collectType="+collectType;
	return parameter;
}

function getTableParameter(){
	//从当前页面取值，组成key=value格式的串
	var service_targets_id=getFormFieldValue('record:service_targets_id');
	if(!service_targets_id){
		alert("请先选择服务对象!");
		return;
	}
    var parameter='input-data:service_targets_id='+service_targets_id;
	return parameter;
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$('#row_4').hide();
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="配置任务数据"/>
<freeze:errors/>

<freeze:form action="/txn30182103">

	<freeze:block property="record" caption="采集任务配置信息"  width="95%">
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:browsebox property="collect_type" caption="采集类型" show="name" notnull="true" valueset="采集任务_采集类型" style="width:95%" onchange="showParams()"/>
      <freeze:browsebox property="data_source_id" caption="数据源" show="name" valueset="资源管理_服务对象对应数据源" notnull="true" parameter="getSourceParameter()"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="采集表"   notnull="true" valueset="资源管理_服务对象对应采集表" show="name" parameter="getTableParameter();" style="width:95%" />
      <freeze:browsebox property="collect_mode" caption="采集方式"  notnull="true" show="name" valueset="资源管理_采集方式"  style="width:95%"  />
	  <freeze:cell caption="执行参数:" colspan="2"> </freeze:cell>
	  <freeze:text property="file_name_en" caption="文件全名" notnull="true" maxlength="100" style="width:95%"></freeze:text>
	  <freeze:text property="file_sepeator" caption="列分隔符" notnull="true" maxlength="5" style="width:95%"></freeze:text>
	  
	</freeze:block>
	<br />
	<div style="width:100%;text-align:center" >
		<input type="button" value="执行任务" onclick="RunManual();" />
	</div>
	
</freeze:form>
</freeze:body>
</freeze:html>
