<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�δ������ֶ���Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����δ������ֶα�' );	// /txn80002401.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002401.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�δ������ֶ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002402">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�δ������ֶ���Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_unclaim_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_tab_code" caption="δ�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_code" caption="�ֶδ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_name" caption="�ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_column_type" caption="�ֶ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="unclaim_column_length" caption="�ֶγ���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="is_primary_key" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_index" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_null" caption="�Ƿ�����Ϊ��" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="default_value" caption="Ĭ��ֵ" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="remarks" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
