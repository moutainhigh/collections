<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��������Ȩ�޷�������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������Ȩ�޷�����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������Ȩ�޷�����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Ȩ�޷�����' );	// /txn103061.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn103061.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��������Ȩ�޷�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103063">
  <freeze:block property="record" caption="��������Ȩ�޷�������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="dataaccgrpid" caption="����Ȩ�޷���ID" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="objectid" caption="����Ȩ�����ʹ���" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="dataaccid" caption="����Ȩ������" datatype="string" maxlength="200" minlength="1" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
