<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸��ļ��ϴ��ɼ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ��ϴ��ɼ���' );	// /txn30301001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30301001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸��ļ��ϴ��ɼ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30301002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸��ļ��ϴ��ɼ�����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_table" caption="�ɼ���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="�ɼ�ģʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="file_description" caption="�ļ�����" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="collect_file_name" caption="�ɼ��ļ�����" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="collect_file_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="check_result_file_name" caption="У�����ļ�����" colspan="2" rows="4" maxlength="300" style="width:98%"/>
      <freeze:text property="check_result_file_id" caption="У�����ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
