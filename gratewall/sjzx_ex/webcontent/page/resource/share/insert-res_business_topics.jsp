<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӹ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ҵ�������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ҵ�������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ҵ�������' );	// /txn203011.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn203011.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn203013">
  <freeze:block property="record" caption="���ӹ���������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="business_topics_id" caption="ҵ������ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="service_targets_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="topics_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="topics_no" caption="������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:textarea property="topics_desc" caption="��������" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="show_order" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
