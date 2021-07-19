<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集数据库列表</title>
</head>

<script language="javascript">

// 增加采集数据库
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_database_task.jsp", "增加采集数据库", "modal" );
	page.addRecord();
}

// 修改采集数据库
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30501004.do", "修改采集数据库", "modal" );
	page.addParameter( "record:database_task_id", "primary-key:database_task_id" );
	page.updateRecord();
}

// 删除采集数据库
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30501005.do", "删除采集数据库" );
	page.addParameter( "record:database_task_id", "primary-key:database_task_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询采集数据库列表"/>
<freeze:errors/>

<freeze:form action="/txn30501001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询采集数据库列表" keylist="database_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加采集数据库" txncode="30501003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改采集数据库" txncode="30501004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除采集数据库" txncode="30501005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="database_task_id" caption="采集数据库主键ID" style="width:50%" />
      <freeze:cell property="collect_task_id" caption="采集任务ID" style="width:50%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
