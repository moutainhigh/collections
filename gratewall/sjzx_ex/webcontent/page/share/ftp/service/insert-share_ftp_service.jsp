<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӹ���ftp������Ϣ</title>
</head>

<script language="javascript">
var svrId = "<%= request.getParameter("svrId") %>";

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湲��ftp����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湲��ftp����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲��ftp����' );	// /txn40401001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40401001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	setFormFieldValue("record:service_id",svrId);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ���ftp������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn40401003">
  <freeze:block property="record" caption="���ӹ���ftp������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_service_id" caption="FTP����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"  />
      <freeze:text property="datasource_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="srv_scheduling_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="file_name" caption="�ļ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="file_type" caption="�ļ�����" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
