<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�û���¼ͳ��</title>
</head>

<script language="javascript">
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�û���¼ͳ��"/>
<freeze:errors/>

<freeze:form action="/txn61000002">
  <freeze:grid property="record" caption="�û���¼ͳ��" keylist="query_date" width="95%" checkbox="false" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>      
      <freeze:cell property="opername" caption="��Ա����" style="width:14%" />
      <freeze:cell property="query_date" caption="��¼����" datatype="date" style="width:25%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
