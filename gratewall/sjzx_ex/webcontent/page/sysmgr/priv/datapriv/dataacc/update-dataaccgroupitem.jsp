<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�����Ȩ�޷�������Ϣ</title>
</head>

<script language="javascript">

// �� ��
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
<freeze:title caption="�޸�����Ȩ�޷�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103062">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="dataaccgrpid" caption="����Ȩ�޷���ID" style="width:95%"/>
      <freeze:hidden property="dataaccid" caption="����Ȩ������" style="width:95%"/>
      <freeze:hidden property="objectid" caption="����Ȩ�����ʹ���" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����Ȩ�޷�������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="dataaccgrpid" caption="����Ȩ�޷���ID" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="objectid" caption="����Ȩ�����ʹ���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="dataaccid" caption="����Ȩ������" datatype="string" maxlength="200" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
