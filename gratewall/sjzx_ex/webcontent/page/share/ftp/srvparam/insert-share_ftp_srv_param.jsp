<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����ftp���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ftp�������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ftp�������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ftp�������' );	// /txn40402001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40402001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ftp���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn40402003">
  <freeze:block property="record" caption="����ftp���������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="srv_param_id" caption="����ֵID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="ftp_service_id" caption="FTP����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="param_value_type" caption="�����String INT BOOLEAN" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="patameter_name" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="patameter_value" caption="����ֵ" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="style" caption="��ʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="showorder" caption="˳���ֶ�" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
