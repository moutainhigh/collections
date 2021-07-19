<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加文件上传采集表信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存文件上传采集表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存文件上传采集表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件上传采集表' );	// /txn30301001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30301001.do
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加文件上传采集表信息"/>
<freeze:errors/>

<freeze:form action="/txn30301003">
  <freeze:block property="record" caption="增加文件上传采集表信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="文件上传任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"    style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="采集表"  notnull="true" maxlength="32" valueset="资源管理_服务对象对应采集表" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:textarea property="task_description" caption="任务说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="备案说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:file property="collect_file_name" caption="采集文件" accept=".xls,.txt,.mdb"style="width:80%" maxlength="200" colspan="2"/>
      
      
      <freeze:hidden property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="文件描述"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="校验结果文件名称"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="校验结果文件ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
