<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�����Ȩ�޷�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Ȩ�޷���' );	// /txn103041.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn103041.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�����Ȩ�޷�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103042">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="objectid" caption="��������" style="width:95%"/>
      <freeze:hidden property="dataaccgrpid" caption="����Ȩ�޷�������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����Ȩ�޷�����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="objectid" caption="��������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="dataaccgrpid" caption="����Ȩ�޷�������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="dataaccdispobj" caption="�������" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
