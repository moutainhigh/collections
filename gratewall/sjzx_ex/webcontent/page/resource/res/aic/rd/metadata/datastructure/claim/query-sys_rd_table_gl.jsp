<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="300" height="350">
<head>

</head>

<script language="javascript">


// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
      <td style="border-bottom:2px solid white" >δ�����</td>
      <td align="center" style="border-bottom:2px solid white"><freeze:out value="${record.wrlb}"></freeze:out>��</td>
    </tr>
    <tr class="secTitle">
      <td >�������</td>
      <td align="center" >����</td>
    </tr>
    <tr class="oddrow">
      <td >ҵ���</td>
      <td align="center" ><freeze:out value="${record.ywb}"></freeze:out>��</td>
    </tr>
    <tr class="evenrow">
      <td >�����</td>
      <td align="center" ><freeze:out value="${record.dmb}"></freeze:out>��</td>
    </tr>
    <tr class="oddrow">
      <td >ϵͳ��</td>
      <td align="center" ><freeze:out value="${record.xtb}"></freeze:out>��</td>
    </tr>
  </table>
</freeze:form>
</freeze:body>
</freeze:html>
