<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�ȫ�ļ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ȫ�ļ���' );	// /txn50030101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn50030101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ȫ�ļ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50030102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_search_config_id" caption="��������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ȫ�ļ�����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_search_config_id" caption="��������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="�û�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:textarea property="permit_subject" caption="��Ȩ����" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="create_by" caption="������" datatype="string" maxlength="10" minlength="1" style="width:95%"/>
      <freeze:text property="create_date" caption="��������" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="config_order" caption="����˳��" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="is_pause" caption="�Ƿ���ͣ" datatype="string" maxlength="2" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
