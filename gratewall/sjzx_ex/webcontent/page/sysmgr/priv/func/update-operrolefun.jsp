<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改角色功能权限信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存角色功能权限' );	// /txn103031.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn103031.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改角色功能权限信息"/>
<freeze:errors/>

<freeze:form action="/txn103032">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="roleaccid" caption="角色权限代码" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改角色功能权限信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="roleaccid" caption="角色权限代码" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="roleid" caption="角色编号" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="txncode" caption="交易代码" datatype="string" maxlength="15" style="width:95%"/>
      <freeze:text property="dataaccrule" caption="数据权限认证规则" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
