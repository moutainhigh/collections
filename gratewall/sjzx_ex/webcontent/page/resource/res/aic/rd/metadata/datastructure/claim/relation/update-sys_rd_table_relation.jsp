<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸ı��ϵ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '������ϵ' );	// /txn80002601.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002601.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ı��ϵ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002602">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ı��ϵ��Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_table_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_code" caption="�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk" caption="�����ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk_name" caption="�����ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_code" caption="���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_name" caption="��������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_fk" caption="�������ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="ralation_table_fk_name" caption="�������ֶ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="sys_rd_system_id" caption="��������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="��������ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_relation_type" caption="������ϵ����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:textarea property="remarks" caption="ע��" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
