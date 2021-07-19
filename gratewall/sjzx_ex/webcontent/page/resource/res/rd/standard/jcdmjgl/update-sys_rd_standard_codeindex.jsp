<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="500">
<head>
<title>修改代码集信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存基础代码集管理' );	// /txn7000401.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000401.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改代码集信息"/>
<freeze:errors/>

<freeze:form action="/txn7000402">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standar_codeindex" caption="代码集标识符" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改代码集信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_standar_codeindex" caption="代码集标识符" datatype="string" maxlength="10" readonly="true" style="width:95%"/>
      <freeze:text property="codeindex_name" caption="代码集名称" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:select property="codeindex_category" caption="代码集类型" valueset="代码集类型" style="width:95%"/>
      <freeze:text property="representation" caption="表示" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="standard_codeindex_version" caption="版本" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="code_table" caption="代码表" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:textarea property="coding_methods" caption="编码方法" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="description" caption="说明" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
