<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��������������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '������������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '������������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '������������' );	// /txn105001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn105001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��������������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn105003">
  <freeze:block property="record" caption="��������������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="download_purv_id" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="agency_id" caption="�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="has_purv" caption="�Ƿ���������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="max_result" caption="������������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="last_modi_user" caption="����޸���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="last_modi_date" caption="����޸�����" datatype="string" maxlength="10" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
