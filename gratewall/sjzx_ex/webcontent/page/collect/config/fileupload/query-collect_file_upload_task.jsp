<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询文件上传采集表列表</title>
</head>

<script language="javascript">

// 增加文件上传采集表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_file_upload_task.jsp", "增加文件上传采集表", "modal" );
	page.addRecord();
}

// 修改文件上传采集表
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30301004.do", "修改文件上传采集表", "modal" );
	page.addParameter( "record:file_upload_task_id", "primary-key:file_upload_task_id" );
	page.updateRecord();
}

// 删除文件上传采集表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30301005.do", "删除文件上传采集表" );
	page.addParameter( "record:file_upload_task_id", "primary-key:file_upload_task_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询文件上传采集表列表"/>
<freeze:errors/>

<freeze:form action="/txn30301001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="collect_table" caption="采集表" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="采集模式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_status" caption="文件状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_description" caption="文件描述" datatype="string" maxlength="2000" style="width:95%"/>
      <freeze:text property="collect_file_name" caption="采集文件名称" datatype="string" maxlength="200" style="width:95%"/>
      <freeze:text property="collect_file_id" caption="采集文件ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询文件上传采集表列表" keylist="file_upload_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加文件上传采集表" txncode="30301003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改文件上传采集表" txncode="30301004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除文件上传采集表" txncode="30301005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="file_upload_task_id" caption="文件上传任务ID" style="width:11%" />
      <freeze:cell property="collect_task_id" caption="采集任务ID" style="width:11%" />
      <freeze:cell property="collect_table" caption="采集表" style="width:11%" />
      <freeze:cell property="collect_mode" caption="采集模式" style="width:11%" />
      <freeze:cell property="file_status" caption="文件状态" style="width:12%" />
      <freeze:cell property="file_description" caption="文件描述" style="width:20%" visible="false" />
      <freeze:cell property="collect_file_name" caption="采集文件名称" style="width:20%" />
      <freeze:cell property="collect_file_id" caption="采集文件ID" style="width:12%" />
      <freeze:cell property="check_result_file_name" caption="校验结果文件名称" style="width:20%" visible="false" />
      <freeze:cell property="check_result_file_id" caption="校验结果文件ID" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
