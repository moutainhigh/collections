<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸ı�����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '������־����ʹ�����' );	// /txn620200201.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620200201.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ı�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620200202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_report_use_id" caption="��־�������id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ı�����������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_use_id" caption="��־�������id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="log_report_create_id" caption="��־��������id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="browser_person" caption="�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="browser_date" caption="�������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="operate" caption="����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="filename" caption="�ļ���" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="path" caption="·��" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
