<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӱ����¼����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������¼��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������¼��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '��������¼��' );	// /txn80002301.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002301.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӱ����¼����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002303">
  <freeze:block property="record" caption="���ӱ����¼����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_change_id" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="db_username" caption="�û�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="�����������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="�ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="�ֶ�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_item" caption="�������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="change_before" caption="���ǰ����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_after" caption="���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_result" caption="������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="change_oprater" caption="�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="change_time" caption="���ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="change_reason" caption="���ԭ��" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
