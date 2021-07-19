<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>视图日志列表</title>

<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<script language="javascript">

// 增加服务配置
function func_record_goBack()
{
	goBack();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="视图日志列表"/>
<freeze:errors/>

<freeze:form action="/txn52103008">
  <freeze:frame property="select-key" >
      <freeze:hidden property="config_name" caption="config_name"/>
      <freeze:hidden property="login_name" caption="login_name"/>
      <freeze:hidden property="user_type" caption="user_type"/>
  </freeze:frame>
  <freeze:grid property="record" caption="视图日志列表" keylist="session_id" checkbox="false" width="95%" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:cell property="log" caption="视图日志" style="width:14%" align="left" />
  </freeze:grid>
  
  <div align="center">
	  <table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td>
  		<td><input type="button" class="menu" value="返 回" onclick="func_record_goBack();" /></td>
  		<td class='btn_right'></td></tr></table>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
