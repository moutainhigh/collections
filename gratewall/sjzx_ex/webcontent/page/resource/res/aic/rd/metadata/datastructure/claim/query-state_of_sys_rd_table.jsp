<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询已认领列表</title>
</head>

<script language="javascript">

// 增加已认领
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table.jsp", "增加已认领", "modal" );
	page.addRecord();
}

// 修改已认领
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002204.do", "修改已认领", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// 删除已认领
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002205.do", "删除已认领" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 表管理
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002201.do", "表管理");
	page.addParameter( "record:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record:db_name", "select-key:db_name" );
	page.updateRecord();
}

// 字段管理
function func_record_columnRecord()
{
	var page = new pageDefine( "/txn80002501.do", "查看已认领", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询已认领列表"/>
<freeze:errors/>

<freeze:form action="/txn80002207">
   <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="db_name" caption="数据源名称" datatype="string" maxlength="100" style="width:39%" colspan="2"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询列表" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_viewRecord" caption="表管理" txncode="80002201" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
  
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源id" style="width:20%" />
      <freeze:cell property="db_name" caption="数据源名称" style="width:20%" />
      <freeze:cell property="claim_num" caption="已认领数量" style="width:20%" />
      <freeze:cell property="table_name" caption="导出详情" style="width:20%" visible="false"/>
      <freeze:cell property="table_type" caption="导出数据结构详情" style="width:20%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
