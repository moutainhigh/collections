<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸����ù��ܹ�ϵ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Ȩ�����͹���' );	// /txn103021.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn103021.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸����ù��ܹ�ϵ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn103022">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="funcode" caption="���ܴ���" style="width:95%"/>
      <freeze:hidden property="objectid" caption="���ݶ������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸����ù��ܹ�ϵ��Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="funcode" caption="���ܴ���" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="objectid" caption="���ݶ������" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
