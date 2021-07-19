<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询已认领表字段表列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询已认领表字段表列表"/>
<freeze:errors/>
<br />
<freeze:form action="/txn80003501">
  <freeze:grid property="record" caption="字段列表" checkbox="false" rowselect="false" keylist="sys_rd_column_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     
      <freeze:cell property="column_code" caption="字段名" style="width:15%" />
      <freeze:cell property="column_name" caption="字段中文名" style="width:15%" />
      <freeze:cell property="column_type" caption="字段类型" valueset="字段数据类型" style="width:10%" />
      <freeze:hidden property="column_length" caption="字段长度" style="width:8%" />
      <freeze:cell property="column_codeindex" caption="系统代码集名称" style="width:15%" />
      
      <freeze:hidden property="use_type" caption="是否有效" valueset="布尔型数" style="width:8%" />
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="物理表ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" style="width:12%" />
      <freeze:hidden property="table_code"  caption="物理表名" style="width:20%" />
      <freeze:hidden property="alia_name" caption="字段别名" style="width:20%" />
      <freeze:hidden property="code_name" caption="系统代码名" style="width:20%" />
      <freeze:hidden property="standard_status" caption="合标状态" style="width:10%" />
      <freeze:hidden property="is_extra_col" caption="是否为必查字段" style="width:10%" />
      <freeze:hidden property="sys_column_type" caption="字段类型" style="width:10%" />
      <freeze:hidden property="jc_data_element" caption="基础数据元" style="width:10%" />
      <freeze:hidden property="jc_data_index" caption="基础代码集" style="width:10%" />
      <freeze:hidden property="domain_value" caption="值域" style="width:10%" />
      <freeze:hidden property="unit" caption="计量单位" style="width:12%" />
      <freeze:hidden property="claim_operator" caption="认领人" style="width:12%" />
      <freeze:hidden property="claim_date" caption="认领时间" style="width:10%" />
      <freeze:hidden property="default_value" caption="缺省值" style="width:16%" />
      <freeze:hidden property="is_null" caption="是否为空" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="是否主键" style="width:10%" />
      <freeze:hidden property="is_index" caption="是否索引" style="width:10%" />
      <freeze:hidden property="changed_status" caption="字段变化状态" style="width:10%" />
      <freeze:hidden property="sync_sign" caption="同步标识串" style="width:12%" />
      <freeze:hidden property="description" caption="说明" style="width:20%" visible="false" />
      <freeze:hidden property="column_level" caption="字段级别" style="width:10%" />
      <freeze:hidden property="basic_flag" caption="基础字段标识" style="width:16%" />
      <freeze:hidden property="data_from" caption="数据来源" style="width:16%" />
      <freeze:hidden property="data_from_column" caption="数据来源字段" style="width:16%" />
      <freeze:hidden property="transform_desp" caption="数据转换描述" style="width:20%" />
      <freeze:hidden property="sort" caption="排序" style="width:10%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
