<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="400">
<head>
<title>��ѯ�ɼ����ݿ��б�</title>
</head>

<script type="text/javascript">


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30300007">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯETL�ɼ�����" keylist="workflow_name" width="95%" checkbox="false" rowselect="false">
      <freeze:cell property="tno" caption="���" style="width:8%" align="center"/>
      <freeze:cell property="instance_name" caption="����������" style="width:50%" align="center"/>
      <freeze:cell property="remark" caption="��������" style="width:42%" />
  </freeze:grid>
  <table width="95%" align="center"><tr><td align="center">
  	<div class="btn_cancel" onclick="window.close();"></div>
  </td></tr></table>
<Br/>
</freeze:form>
</freeze:body>
</freeze:html>
