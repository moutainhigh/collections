<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="600" height="200">
<head>
<title>�޸Ĳ���ά����Ϣ</title>
</head>

<script language="javascript">

// �� ��
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
<freeze:title caption="ά��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30103002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_patameter_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="ά��������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_patameter_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="webservice_task_id" caption="webservice����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="patameter_type" caption="��������" valueset="��Դ����_��������" notnull="true" style="width:95%"/>
      <freeze:text property="patameter_name" caption="������" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:textarea property="patameter_value" caption="����ֵ"  colspan="2" rows="2" maxlength="1000" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
