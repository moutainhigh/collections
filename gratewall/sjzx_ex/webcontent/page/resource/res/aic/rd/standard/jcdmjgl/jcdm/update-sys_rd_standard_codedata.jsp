<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>修改基础代码信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存基础代码' );	// /txn7000411.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000411.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改基础代码信息"/>
<freeze:errors/>

<freeze:form action="/txn7000412">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="id" caption="代码值ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改基础代码信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="id" caption="代码值ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_standar_codeindex" caption="代码集标识符" datatype="string" maxlength="10" readonly="true" style="width:95%"/>
      <freeze:text property="sys_rd_standard_codevalue" caption="代码值" datatype="string" maxlength="20" style="width:95%" notnull="true"/>
      <freeze:textarea property="sys_rd_standard_codename" caption="代码内容" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="description" caption="说明" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
