<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĺ���ʹ��ͳ����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���湦��ʹ��ͳ��' );	// /txn60900001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn60900001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĺ���ʹ��ͳ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn60900002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="func_name" caption="����ģ��" style="width:95%"/>
      <freeze:hidden property="sjjgid_fk" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ���ʹ��ͳ����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="querytimes" caption="����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="query_date" caption="ִ������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="func_name" caption="����ģ��" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="sjjgid_fk" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="func_index" caption="���" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
