<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�������ù��ܹ�ϵ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������Ȩ�����͹���' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������Ȩ�����͹���' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Ȩ�����͹���' );	// /txn103021.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn103021.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�������ù��ܹ�ϵ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103023">
  <freeze:block property="record" caption="�������ù��ܹ�ϵ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="funcode" caption="���ܴ���" datatype="string" maxlength="36" minlength="1" style="width:95%"/>
      <freeze:text property="objectid" caption="���ݶ������" datatype="string" maxlength="" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
