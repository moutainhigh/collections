<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĺ��������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲���������' );	// /txn40400001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40400001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĺ��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn40400002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="srv_scheduling_id" caption="�������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ��������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="srv_scheduling_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="service_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="job_class_name" caption="�������õ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="scheduling_type" caption="�ƻ���������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="scheduling_day" caption="�ƻ���������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="start_time" caption="�ƻ�����ʼʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="end_time" caption="�ƻ��������ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="scheduling_count" caption="�ƻ�����ִ�д���" datatype="string" maxlength="4" style="width:95%"/>
      <freeze:text property="interval_time" caption="ÿ�μ��ʱ��" datatype="string" maxlength="3" style="width:95%"/>
      <freeze:text property="scheduling_week" caption="�ƻ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:textarea property="scheduling_day1" caption="�ƻ�������������" colspan="2" rows="4" maxlength="500" style="width:98%"/>
      <freeze:text property="task_expression" caption="���ʽ" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:text property="is_markup" caption="����� ��Ч ��Ч" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:text property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
