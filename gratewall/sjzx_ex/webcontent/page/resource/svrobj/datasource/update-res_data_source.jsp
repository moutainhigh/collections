<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�����Դ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '��������Դ��' );	// /txn20102001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn20102001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�����Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20102002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="data_source_id" caption="����ԴID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����Դ����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_targets_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="data_source_type" caption="����Դ����" show="name" valueset="��Դ����_����Դ����"  style="width:95%"/>
      <freeze:text property="data_source_name" caption="����Դ����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="data_source_ip" caption="����ԴIP" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="access_port" caption="���ʶ˿�" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:text property="access_url" caption="����URL" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="db_type" caption="���ݿ�����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="db_instance" caption="����Դʵ��" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:text property="db_username" caption="����Դ�û���" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:textarea property="db_desc" caption="����Դ����" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="db_status" caption="����Դ״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:text property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
