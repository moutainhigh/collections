<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����û�����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����û���������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����û���������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����û���������' );	// /txn50011701.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn50011701.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����û�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50011703">
  <freeze:block property="record" caption="�����û�����������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_svr_limit_id" caption="�û�������������" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="�û�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_service_id" caption="����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="is_limit_week" caption="�Ƿ����Ʊ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_time" caption="�Ƿ�����ʱ��" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_number" caption="�Ƿ����ƴ���" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="is_limit_total" caption="�Ƿ�����������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="limit_week" caption="��������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="limit_time" caption="����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_start_time" caption="��ֹ����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_end_time" caption="��������ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_number" caption="���ƴ���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_total" caption="����������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="limit_desp" caption="��������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
