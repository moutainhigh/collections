<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĺ��ܴ�С���Ӧ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���湦�ܴ�С���Ӧ��' );	// /txn620100401.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620100401.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĺ��ܴ�С���Ӧ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620100402">
  <freeze:frame property="primary-key" width="95%">
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ��ܴ�С���Ӧ��Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="first_func_name" caption="���ܴ���" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:text property="second_func_name" caption="����С��" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
