<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="220">
<head>
<title>修改数据权限类型管理信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据权限类型管理' );	// /txn103011.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn103011.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改数据权限类型管理信息"/>
<freeze:errors/>

<freeze:form action="/txn103012">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="objectid" caption="数据权限类型代码" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改数据权限类型管理信息" width="95%" columns="1">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="objectid" caption="数据权限类型代码" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="objectsource" caption="数据权限来源"  maxlength="50" minlength="1" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
