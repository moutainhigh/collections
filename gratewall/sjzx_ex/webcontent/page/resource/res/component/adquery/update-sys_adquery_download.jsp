<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸ĸ߼���ѯ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
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
<freeze:title caption="�޸ĸ߼���ѯ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn6025002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_advanced_query_id" caption="����" style="width:95%"/>
      <freeze:hidden property="name" caption="�߼���ѯ��������" style="width:95%"/>
      <freeze:hidden property="create_date" caption="��������" style="width:95%"/>
      <freeze:hidden property="last_exec_date" caption="���ִ������" style="width:95%"/>
      <freeze:hidden property="sys_svr_user_id" caption="�û�id" style="width:95%"/>
      <freeze:hidden property="apply_count" caption="�������" style="width:95%"/>
      <freeze:hidden property="last_down_date" caption="�������ʱ��" style="width:95%"/>
      <freeze:hidden property="is_mutil_download" caption="�Ƿ��ظ�����" style="width:95%"/>
      <freeze:hidden property="create_by" caption="������" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ĸ߼���ѯ��������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_advanced_query_id" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="name" caption="�߼���ѯ��������" datatype="string" maxlength="200" style="width:95%"/>
      <freeze:hidden property="create_by" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="create_date" caption="��������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="last_exec_date" caption="���ִ������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_svr_user_id" caption="�û�id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="is_mutil_download" caption="�Ƿ��ظ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="apply_count" caption="�������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="last_down_date" caption="�������ʱ��" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
