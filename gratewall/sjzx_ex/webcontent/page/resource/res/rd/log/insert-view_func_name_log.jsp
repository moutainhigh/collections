<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӹ��ܴ�С���Ӧ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湦�ܴ�С���Ӧ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湦�ܴ�С���Ӧ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湦�ܴ�С���Ӧ��' );	// /txn620100401.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620100401.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ��ܴ�С���Ӧ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620100403">
  <freeze:block property="record" caption="���ӹ��ܴ�С���Ӧ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="first_func_name" caption="���ܴ���" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:text property="second_func_name" caption="����С��" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
