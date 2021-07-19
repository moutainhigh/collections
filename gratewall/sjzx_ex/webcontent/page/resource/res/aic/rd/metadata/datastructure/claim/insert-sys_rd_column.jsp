<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加已认领表字段表信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存已认领表字段表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存已认领表字段表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存已认领表字段表' );	// /txn80002501.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加已认领表字段表信息"/>
<freeze:errors/>

<freeze:form action="/txn80002503">
  <freeze:block property="record" caption="增加已认领表字段表信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_table_id" caption="物理表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_code" caption="物理表名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_code" caption="字段名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="字段中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_type" caption="字段类型" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="column_length" caption="字段长度" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="alia_name" caption="字段别名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="code_name" caption="系统代码名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="standard_status" caption="合标状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="column_codeindex" caption="系统代码集名称" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="is_extra_col" caption="是否为必查字段" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="sys_column_type" caption="字段类型" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="jc_data_element" caption="基础数据元" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="jc_data_index" caption="基础代码集" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="domain_value" caption="值域" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="计量单位" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="claim_operator" caption="认领人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="claim_date" caption="认领时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="default_value" caption="缺省值" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:text property="is_null" caption="是否为空" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_primary_key" caption="是否主键" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_index" caption="是否索引" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="changed_status" caption="字段变化状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="sync_sign" caption="同步标识串" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="description" caption="说明" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="column_level" caption="字段级别" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="basic_flag" caption="基础字段标识" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from" caption="数据来源" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from_column" caption="数据来源字段" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="transform_desp" caption="数据转换描述" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="sort" caption="排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
