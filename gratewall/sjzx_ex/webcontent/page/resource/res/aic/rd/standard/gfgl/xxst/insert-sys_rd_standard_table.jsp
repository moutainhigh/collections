<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>����ʵ����Ϣ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ʵ����Ϣ' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ʵ����Ϣ' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ʵ����Ϣ' );	// /txn7000211.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000211.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ʵ����Ϣ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000213">
  <freeze:block property="record" caption="����ʵ����Ϣ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_standard_id" caption="��׼ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="�淶����" readonly="true" datatype="string" maxlength="100"colspan="2" style="width:95%"/>
      <freeze:text property="table_name" caption="ʵ����Ϣ����" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="table_belongs" caption="������ϵ" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="sort" caption="�����" datatype="string" maxlength="" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
