<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸�ָ������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ָ����' );	// /txn7000221.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000221.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ָ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000222">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_column_id" caption="ָ����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ָ������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_standard_column_id" caption="ָ����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_standard_table_id" caption="��Ϣʵ��ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:browsebox property="data_element_identifier" caption="����Ԫ��ʶ��" valueset="����Ԫ��ʶ��"   style="width:95%" notnull="true"/>
      <freeze:text property="cn_name" caption="ָ��������" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="column_name" caption="�ֶ�����" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:select property="column_type" caption="��������" valueset="�ֶ���������" style="width:95%"/>
      <freeze:text property="column_format" caption="��ʽ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="code_identifier" caption="�����ʶ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
