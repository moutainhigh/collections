<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӹ������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湲�����Ϣ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湲�����Ϣ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲�����Ϣ��' );	// /txn20301001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn20301001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20301003">
  <freeze:block property="record" caption="���ӹ������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="share_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="business_topics_id" caption="ҵ������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_no" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="show_order" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="time_" caption="ʱ���ֶ�" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="table_type" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="table_index" caption="������" colspan="2" rows="4" maxlength="256" style="width:98%"/>
      <freeze:text property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
