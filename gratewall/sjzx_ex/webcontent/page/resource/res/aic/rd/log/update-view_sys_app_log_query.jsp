<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改日志查询信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存日志查询' );	// /txn620100101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620100101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改日志查询信息"/>
<freeze:errors/>

<freeze:form action="/txn620100102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="username" caption="用户姓名" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改日志查询信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="username" caption="用户姓名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="query_time" caption="查询时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="orgid" caption="机构id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="ipaddress" caption="ip地址" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="type" caption="类型" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
