<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="550" >
<head>
<title>�޸ļ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������' );	// /txn50010201.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn50010201.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ļ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50010202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_search_svr_id" caption="��������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ļ���������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_search_svr_id" caption="��������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="svr_name" caption="��������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="svr_db" caption="�������ݿ�" datatype="string" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="svr_query" caption="������ѯ��" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="svr_template" caption="��������ģ��" colspan="8" rows="4" maxlength="4000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
