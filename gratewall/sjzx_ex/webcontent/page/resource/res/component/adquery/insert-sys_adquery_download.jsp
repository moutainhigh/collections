<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���Ӹ߼���ѯ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����߼���ѯ������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����߼���ѯ������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����߼���ѯ������' );	// /txn6025001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn6025001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӹ߼���ѯ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn6025003">
  <freeze:block property="record" caption="���Ӹ߼���ѯ��������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_advanced_query_id" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="name" caption="�߼���ѯ��������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="create_by" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="create_date" caption="��������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="last_exec_date" caption="���ִ������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="�û�id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_mutil_download" caption="�Ƿ��ظ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="apply_count" caption="�������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="last_down_date" caption="�������ʱ��" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
