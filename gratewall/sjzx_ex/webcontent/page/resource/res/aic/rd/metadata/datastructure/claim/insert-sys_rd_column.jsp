<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>������������ֶα���Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '������������ֶα�' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '������������ֶα�' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '������������ֶα�' );	// /txn80002501.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="������������ֶα���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002503">
  <freeze:block property="record" caption="������������ֶα���Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_code" caption="�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_code" caption="�ֶ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="�ֶ�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_type" caption="�ֶ�����" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="column_length" caption="�ֶγ���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="alia_name" caption="�ֶα���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="code_name" caption="ϵͳ������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="standard_status" caption="�ϱ�״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="column_codeindex" caption="ϵͳ���뼯����" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="is_extra_col" caption="�Ƿ�Ϊ�ز��ֶ�" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="sys_column_type" caption="�ֶ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="jc_data_element" caption="��������Ԫ" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="jc_data_index" caption="�������뼯" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="domain_value" caption="ֵ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="������λ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="claim_operator" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="claim_date" caption="����ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="default_value" caption="ȱʡֵ" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:text property="is_null" caption="�Ƿ�Ϊ��" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_primary_key" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="is_index" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="changed_status" caption="�ֶα仯״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="sync_sign" caption="ͬ����ʶ��" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="column_level" caption="�ֶμ���" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="basic_flag" caption="�����ֶα�ʶ" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from" caption="������Դ" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from_column" caption="������Դ�ֶ�" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="transform_desp" caption="����ת������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="sort" caption="����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
