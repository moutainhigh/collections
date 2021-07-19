<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询webservice任务列表</title>
</head>

<script language="javascript">

// 增加webservice任务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_webservice_task.jsp", "增加webservice任务", "modal" );
	page.addRecord();
}

// 修改webservice任务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30102004.do", "修改webservice任务", "modal" );
	page.addParameter( "record:webservice_task_id", "primary-key:webservice_task_id" );
	page.updateRecord();
}

// 删除webservice任务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30102005.do", "删除webservice任务" );
	page.addParameter( "record:webservice_task_id", "primary-key:webservice_task_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询webservice任务列表"/>
<freeze:errors/>

<freeze:form action="/txn30102001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询webservice任务列表" keylist="webservice_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加webservice任务" txncode="30102003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改webservice任务" txncode="30102004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除webservice任务" txncode="30102005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="webservice_task_id" caption="WEBSERVICE任务ID" style="width:12%" />
      <freeze:cell property="collect_task_id" caption="采集任务ID" style="width:12%" />
      <freeze:cell property="service_no" caption="任务编号" style="width:12%" />
      <freeze:cell property="method_name_en" caption="方法名称" style="width:20%" />
      <freeze:cell property="method_name_cn" caption="方法中文名称" style="width:20%" />
      <freeze:cell property="collect_table" caption="对应采集表" style="width:12%" />
      <freeze:cell property="collect_mode" caption="采集方式" style="width:12%" />
      <freeze:cell property="is_encryption" caption="是否加密" style="width:12%" />
      <freeze:cell property="encrypt_mode" caption="加密方式" style="width:12%" />
      <freeze:cell property="method_description" caption="方法描述" style="width:20%" visible="false" />
      <freeze:cell property="method_status" caption="方法状态" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
