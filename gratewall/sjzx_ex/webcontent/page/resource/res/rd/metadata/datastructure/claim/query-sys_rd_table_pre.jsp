<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="650" height="350">
<head>
<title>查询已认领列表</title>
<style>
.odd1_b{
	white-space: nowrap;
}
</style>
</head>

<script language="javascript">
// 字段管理
function func_record_columnRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn80003501.do", "查看已认领" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// 返 回
function func_record_goBack()
{
	goBack();	// 
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>
<freeze:form action="/txn80004301">

  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_rd_system_id" valueset="业务主题概览" caption="主题名称" style="width:90%"/>
      <freeze:text property="table_code" caption="物理表" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="table_name" caption="物理表中文名" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="table_type" caption="表类型" valueset="表类型" style="width:90%"/>
      <freeze:hidden property="query_type" value="1" caption="查询方式" style="width:90%"/>
      <freeze:hidden property="sys_simple" caption="查询方式" style="width:90%"/>
      <freeze:hidden property="sys_system_ids" caption="查询主题列表" style="width:90%"/>
  </freeze:block>
<br />
  <freeze:grid property="record" checkbox="false" caption="查询已认领列表" keylist="sys_rd_table_id" width="95%" navbar="bottom"  fixrow="false" onclick="func_record_columnRecord();" >
      <freeze:cell property="table_code" caption="物理表" style="width:30%" ondblclick="func_record_columnRecord();"  />
      <freeze:cell property="table_name" caption="物理表中文名" style="" ondblclick="func_record_columnRecord();"/>
      <freeze:cell property="table_type" caption="表类型" valueset="表类型" style="width:15%" ondblclick="func_record_columnRecord();" />
      <freeze:cell property="sys_rd_system_id" caption="主题名称" valueset="业务主题" style="width:25%" ondblclick="func_record_columnRecord();" />

      <freeze:hidden property="claim_operator" caption="认领人" style="width:15%" />
      <freeze:hidden property="claim_date" caption="认领日期" style="width:10%" />
      <freeze:hidden property="table_no" caption="数据表编号" style="width:12%" />
      <freeze:hidden property="table_sql" caption="数据表sql" style="width:20%"  />
      <freeze:hidden property="table_sort" caption="排序字段" style="width:20%" />
      <freeze:hidden property="table_dist" caption="区县字段" style="width:20%" />
      <freeze:hidden property="table_time" caption="时间字段" style="width:20%" />
      <freeze:hidden property="parent_table" caption="父表名" style="width:20%" />
      <freeze:hidden property="parent_pk" caption="父表主键名" style="width:20%" />
      <freeze:hidden property="table_fk" caption="与父表关联列名" style="width:20%" />
      <freeze:hidden property="first_record_count" caption="初期数据量" style="width:10%" />
      <freeze:hidden property="last_record_count" caption="最后一次同步数据量" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="数据表ID" style="width:10%" />
      <freeze:hidden property="sys_rd_system_id" caption="业务主体ID" style="width:12%" />
      <freeze:hidden property="sys_no" caption="业务主体编号" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="table_primary_key" caption="表主键" style="width:20%" />
      <freeze:hidden property="table_index" caption="表索引" style="width:20%" />
      <freeze:hidden property="table_use" caption="用途" style="width:20%" />
      <freeze:hidden property="gen_code_column" caption="总局代码字段" style="width:12%" />
      <freeze:hidden property="prov_code_column" caption="省局代码字段" style="width:12%" />
      <freeze:hidden property="city_code_column" caption="市局代码字段" style="width:12%" />
      <freeze:hidden property="content" caption="代码字段内容" style="width:12%" />
      <freeze:hidden property="changed_status" caption="变化状态" style="width:10%" />
      <freeze:hidden property="object_schema" caption="表模式" style="width:16%" />
      <freeze:hidden property="memo" caption="备注" style="width:20%"  />
      <freeze:hidden property="is_query" caption="是否可查询" style="width:10%" />
      <freeze:hidden property="is_trans" caption="是否可共享" style="width:10%" />
      <freeze:hidden property="is_download" caption="是否可下载" style="width:10%" />
      <freeze:hidden property="sort" caption="排序" style="width:10%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
