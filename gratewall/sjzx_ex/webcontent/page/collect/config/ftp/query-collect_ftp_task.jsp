<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询ftp任务列表</title>
</head>

<script language="javascript">

// 增加ftp任务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_ftp_task.jsp", "增加ftp任务", "modal" );
	page.addRecord();
}

// 修改ftp任务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30201004.do", "修改ftp任务", "modal" );
	page.addParameter( "record:ftp_task_id", "primary-key:ftp_task_id" );
	page.updateRecord();
}

// 删除ftp任务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30201005.do", "删除ftp任务" );
	page.addParameter( "record:ftp_task_id", "primary-key:ftp_task_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询ftp任务列表"/>
<freeze:errors/>

<freeze:form action="/txn30201001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询ftp任务列表" keylist="ftp_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加ftp任务" txncode="30201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改ftp任务" txncode="30201004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除ftp任务" txncode="30201005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ftp_task_id" caption="FTP任务ID" style="width:10%" />
      <freeze:cell property="collect_task_id" caption="采集任务ID" style="width:10%" />
      <freeze:cell property="service_no" caption="任务编号" style="width:11%" />
      <freeze:cell property="file_name_en" caption="文件名称" style="width:18%" />
      <freeze:cell property="file_name_cn" caption="文件中文名称" style="width:18%" />
      <freeze:cell property="collect_mode" caption="采集方式" style="width:11%" />
      <freeze:cell property="collect_table" caption="对应采集表" style="width:11%" />
      <freeze:cell property="file_status" caption="文件状态" style="width:11%" />
      <freeze:cell property="file_description" caption="文件描述" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
