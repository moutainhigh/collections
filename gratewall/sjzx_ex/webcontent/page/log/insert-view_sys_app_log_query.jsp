<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>������־��ѯ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '������־��ѯ' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '������־��ѯ' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '������־��ѯ' );	// /txn620100101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620100101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="������־��ѯ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620100103">
  <freeze:block property="record" caption="������־��ѯ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="username" caption="�û�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="query_time" caption="��ѯʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="orgid" caption="����id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="ipaddress" caption="ip��ַ" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="type" caption="����" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
