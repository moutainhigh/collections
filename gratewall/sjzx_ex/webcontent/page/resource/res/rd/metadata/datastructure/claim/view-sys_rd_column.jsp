<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查看字段信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存已认领表字段表' );	// /txn80002501.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看字段信息"/>
<freeze:errors/>

<freeze:form action="/txn80002506">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="查看字段信息" width="95%">
      
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="物理表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="table_code" caption="物理表名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:cell property="column_code" caption="字段名" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name" caption="字段中文名" datatype="string"  style="width:95%" />
      <freeze:cell property="column_type" caption="字段类型" valueset="字段数据类型" datatype="string"  style="width:95%" />
      <freeze:cell property="column_length" caption="字段长度" datatype="string"  style="width:95%" />
      <freeze:cell property="is_null" caption="是否为空" valueset="是否允许下载" datatype="string" style="width:95%" />
      <freeze:cell property="default_value" caption="缺省值" datatype="string"  style="width:95%" />
      <freeze:cell property="standard_status" caption="合标性" valueset="合标性" datatype="string"  style="width:95%" />
      <freeze:cell property="jc_data_element" caption="基础数据元" valueset="数据元标识符" datatype="string"  style="width:95%" />
      <freeze:cell property="jc_data_index" caption="基础代码集" valueset="取基础代码集" datatype="string"  style="width:95%" />
      
      <freeze:cell property="column_codeindex" caption="系统代码集" valueset="取系统代码集" datatype="string"   style="width:98%" />
      <freeze:cell property="domain_value" caption="值域" datatype="string"  style="width:95%" />
      <freeze:cell property="unit" caption="计量单位" datatype="string"  style="width:95%" />
      <freeze:cell property="column_level" caption="字段级别" valueset="字段级别" datatype="string"  style="width:95%" />
      <freeze:cell property="basic_flag" caption="基础字段标识" datatype="string"  style="width:95%" />
      <freeze:cell property="data_from" caption="数据来源" datatype="string"  style="width:95%" />
      <freeze:cell property="data_from_column" caption="数据来源字段" datatype="string"  style="width:95%" />
      <freeze:cell property="transform_desp" caption="数据转换描述" datatype="string"   style="width:98%" />
      <freeze:cell property="description" caption="说明"   style="width:98%" />
      
      <freeze:hidden property="alia_name" caption="字段别名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="code_name" caption="系统代码名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="is_extra_col" caption="是否为必查字段" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="sys_column_type" caption="字段类型" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="claim_operator" caption="认领人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="认领时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="is_primary_key" caption="是否主键" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_index" caption="是否索引" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="字段变化状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="sync_sign" caption="同步标识串" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sort" caption="排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="关闭" class="menu" onclick="func_record_goBackNoUpdate();" />
  		</td>
		<td class="btn_right"></td>
	</tr>
</table>
  </p>
</freeze:form>
</freeze:body>
</freeze:html>
