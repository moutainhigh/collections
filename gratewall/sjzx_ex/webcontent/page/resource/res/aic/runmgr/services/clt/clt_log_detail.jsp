<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�ɼ���־��ϸ</title>
<style type="text/css">
#navbar{
	display:none;
}
</style>
</head>

<script language="javascript">

// �޸ķ������
function func_record_updateRecord()
{

}

function func_record_goBack()
{
	goBack();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�ɼ���־��ϸ"/>
<freeze:errors/>

<freeze:form action="/txn50212002">
  <freeze:grid property="record" caption="�ɼ���־��ϸ" keylist="sys_clt_log_detail_id" width="95%" navbar="bottom" checkbox="false" fixrow="false">
      <freeze:cell property="@rowid" caption="���" style="width:6%" align="center"/>
      <freeze:cell property="inf_name" caption="�ӿ�����" style="width:24%" align="center" />
      <freeze:cell property="inf_desc" caption="�ӿ�����" style="width:50%" align="center" />
      <freeze:cell property="clt_num" caption="�ɼ�������(��)" style="width:20%" align="center" />
  </freeze:grid>
  <freeze:block property="record" width="95%" border="0">
    <freeze:button name="record_goBack" caption="�� ��" onclick="func_record_goBack();"/>    
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
