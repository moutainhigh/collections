<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӷ��ʹ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湲��������ʹ����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湲��������ʹ����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲��������ʹ����' );	// /txn403001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn403001.do
}

// �����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӷ��ʹ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn403003">
  <freeze:block property="record" caption="���ӷ��ʹ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="rule_id" caption="������ʹ���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="week" caption="�����ڼ�" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="start_time" caption="�����쿪ʼʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="end_time" caption="���������ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="times_day" caption="������ɷ��ʴ���" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="count_dat" caption="������һ�οɷ��ʼ�¼��Ŀ" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="total_count_day" caption="������ɷ����ܼ�¼��Ŀ" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>