<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="300" height="350">
<head>

</head>

<script language="javascript">


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn80002201">

<br />
 <table width="90%"  align="center"  cellspacing="0" cellpadding="0" style="border-collapse:collapse;">
    <tr class="secTitle" >
      <td style="border-bottom:2px solid white" >未认领表</td>
      <td align="center" style="border-bottom:2px solid white"><freeze:out value="${record.wrlb}"></freeze:out>张</td>
    </tr>
    <tr class="secTitle">
      <td >已认领表</td>
      <td align="center" >数量</td>
    </tr>
    <tr class="oddrow">
      <td >业务表</td>
      <td align="center" ><freeze:out value="${record.ywb}"></freeze:out>张</td>
    </tr>
    <tr class="evenrow">
      <td >代码表</td>
      <td align="center" ><freeze:out value="${record.dmb}"></freeze:out>张</td>
    </tr>
    <tr class="oddrow">
      <td >系统表</td>
      <td align="center" ><freeze:out value="${record.xtb}"></freeze:out>张</td>
    </tr>
  </table>
</freeze:form>
</freeze:body>
</freeze:html>
