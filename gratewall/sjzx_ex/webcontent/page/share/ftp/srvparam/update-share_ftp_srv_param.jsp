<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�ftp���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
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
<freeze:title caption="�޸�ftp���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn40402002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="srv_param_id" caption="����ֵID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ftp���������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="srv_param_id" caption="����ֵID" datatype="string" maxlength="32" style="width:95%"/>
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
