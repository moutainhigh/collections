<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>用户登录统计</title>
</head>

<script language="javascript">
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="用户登录统计"/>
<freeze:errors/>

<freeze:form action="/txn61000002">
  <freeze:grid property="record" caption="用户登录统计" keylist="query_date" width="95%" checkbox="false" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>      
      <freeze:cell property="opername" caption="人员姓名" style="width:14%" />
      <freeze:cell property="query_date" caption="登录日期" datatype="date" style="width:25%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
