<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>�޸ķ����ѯ������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�������������' );	// /txn40210001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40210001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ķ����ѯ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn40210002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="condition_id" caption="�����ѯ����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ķ����ѯ������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="condition_id" caption="�����ѯ����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="frist_connector" caption="���ӷ�1" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="left_paren" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_en" caption="�ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="�ֶ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="second_connector" caption="���ӷ�2" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="param_value" caption="����ֵ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="param_type" caption="����ֵ����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="right_paren" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="show_order" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="need_input" caption="�Ƿ���Ҫ����" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
