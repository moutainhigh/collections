<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӻ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������' );	// /txn7000411.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000411.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӻ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000413">
  <freeze:block property="record" caption="���ӻ���������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_standar_codeindex" caption="��ʶ��" datatype="string" maxlength="10" readonly="true" style="width:95%"/>
      <freeze:text property="sys_rd_standard_codevalue" caption="����ֵ" datatype="string" maxlength="20" style="width:95%" notnull="true"/>
      <freeze:textarea property="sys_rd_standard_codename" caption="��������" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
