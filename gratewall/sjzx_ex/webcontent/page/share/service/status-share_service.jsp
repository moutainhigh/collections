<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-status.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸ļ�¼��״̬</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲�����ķ����' );	// /txn4020000b.do
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn4020000b.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var statusFieldName = 'record:status';
	var status = getFormFieldValue( statusFieldName );
	status = (status == '1') ? '0' : '1';
	setFormFieldValue( statusFieldName, 0, status );
	setFormFieldReadonly( statusFieldName, 0, true );
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ļ�¼��״̬"/>
<freeze:errors/>

<freeze:form action="/txn4020000b">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ļ�¼��״̬" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBackNoUpdate" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBackNoUpdate();"/>
      <freeze:hidden property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
