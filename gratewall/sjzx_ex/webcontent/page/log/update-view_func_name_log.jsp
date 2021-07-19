<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改功能大小类对应信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存功能大小类对应表' );	// /txn620100401.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620100401.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改功能大小类对应信息"/>
<freeze:errors/>

<freeze:form action="/txn620100402">
  <freeze:frame property="primary-key" width="95%">
  </freeze:frame>

  <freeze:block property="record" caption="修改功能大小类对应信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="first_func_name" caption="功能大类" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:text property="second_func_name" caption="功能小类" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
