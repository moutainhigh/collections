<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӽ�ɫ����Ȩ����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����ɫ����Ȩ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����ɫ����Ȩ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ɫ����Ȩ��' );	// /txn103031.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn103031.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӽ�ɫ����Ȩ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103033">
  <freeze:block property="record" caption="���ӽ�ɫ����Ȩ����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="roleaccid" caption="��ɫȨ�޴���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="roleid" caption="��ɫ���" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="txncode" caption="���״���" datatype="string" maxlength="15" style="width:95%"/>
      <freeze:text property="dataaccrule" caption="����Ȩ����֤����" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
