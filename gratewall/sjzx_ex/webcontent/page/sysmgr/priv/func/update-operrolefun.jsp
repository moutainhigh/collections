<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ľ�ɫ����Ȩ����Ϣ</title>
</head>

<script language="javascript">

// �� ��
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
<freeze:title caption="�޸Ľ�ɫ����Ȩ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103032">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="roleaccid" caption="��ɫȨ�޴���" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ľ�ɫ����Ȩ����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="roleaccid" caption="��ɫȨ�޴���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="roleid" caption="��ɫ���" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="txncode" caption="���״���" datatype="string" maxlength="15" style="width:95%"/>
      <freeze:text property="dataaccrule" caption="����Ȩ����֤����" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
