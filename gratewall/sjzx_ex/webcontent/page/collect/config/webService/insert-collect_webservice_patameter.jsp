<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���Ӳ���ά����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���������' );	// /txn30103001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30103001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳ���ά����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30103003">
  <freeze:block property="record" caption="���Ӳ���ά����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_patameter_id" caption="����ID" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="webservice_task_id" caption="webservice����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="patameter_type" caption="��������" valueset="��Դ����_��������" notnull="true" style="width:95%"/>
      <freeze:text property="patameter_name" caption="������" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="patameter_value" caption="����ֵ" datatype="string" maxlength="1000" notnull="true" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
