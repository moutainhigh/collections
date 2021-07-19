<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改未认领表字段信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存未认领表字段表' );	// /txn80002401.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002401.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改未认领表字段信息"/>
<freeze:errors/>

<freeze:form action="/txn80002402">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="表主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改未认领表字段信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="表主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_unclaim_table_id" caption="物理表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_tab_code" caption="未认领表名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_code" caption="字段代码" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_name" caption="字段名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_type" caption="字段类型" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="unclaim_column_length" caption="字段长度" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="is_primary_key" caption="是否主键" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_index" caption="是否索引" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_null" caption="是否允许为空" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="default_value" caption="默认值" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="remarks" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
