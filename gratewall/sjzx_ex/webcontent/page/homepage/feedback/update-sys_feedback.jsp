<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������' );	// /txn711001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn711001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn711002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="�������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸����������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_feedback_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="content" caption="�����������" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="publish_date" caption="����ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="author" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="status" caption="��Ч��־" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="description" caption="������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
