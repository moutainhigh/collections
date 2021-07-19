<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询物理表认领情况列表</title>
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

// 查询已认领
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002201.do", "查看已认领");
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
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
<freeze:title caption="查询数据库对象认领情况列表"/>
<freeze:errors/>

<freeze:form action="/txn80002000">
   <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="数据源名称" valueset="取数据源"  style="width:39%" colspan="2"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询列表" keylist="sys_rd_data_source_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源id" style="width:20%" />
      <freeze:cell property="@rowid" caption="序号" align="center" style="width:30px;" />
      <freeze:cell property="db_name" caption="数据源名称" style="width:20%" />
      <freeze:cell property="data_object_type" caption="数据库对象类型" valueset="数据库对象类型" style="width:20%" />
      <freeze:cell property="total_num" caption="数据库对象总数" style="width:20%" />
      <freeze:cell property="unclaim_num" caption="未认领数量" style="width:20%" />
      <freeze:cell property="claim_num" caption="已认领数量" style="width:15%" />
      <freeze:cell property="table_name" caption="导出详情" style="width:20%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
