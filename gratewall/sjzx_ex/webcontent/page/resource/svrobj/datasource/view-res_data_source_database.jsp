<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�鿴����Դ��Ϣ</title>
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
<freeze:title caption="�鿴����Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20102006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="data_source_id" caption="����ԴID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴����Դ��Ϣ" width="95%">
      
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�����������" datatype="string" show="name" valueset="��Դ����_�����������" style="width:95%"/>
      <freeze:cell property="data_source_type" caption="����Դ����" valueset="��Դ����_����Դ����" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_name" caption="����Դ����" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_type" caption="���ݿ�����" valueset="��Դ����_���ݿ�����" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_ip" caption="����ԴIP" datatype="string"  style="width:95%"/>
      <freeze:cell property="access_port" caption="���ʶ˿�" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_instance" caption="����Դʵ��" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_username" caption="����Դ�û���" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_password" caption="����Դ����"   colspan="2" style="width:95%"/>
    
      <freeze:cell property="db_desc" caption="����Դ����" colspan="2"  style="width:98%"/>
       <freeze:cell property="crename" caption="������" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="����޸���" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
      
      <freeze:hidden property="access_url" caption="����URL" datatype="string"   style="width:98%"/>
      <freeze:hidden property="db_status" caption="����Դ״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
