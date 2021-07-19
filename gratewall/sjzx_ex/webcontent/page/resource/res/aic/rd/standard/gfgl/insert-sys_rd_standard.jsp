<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>���ӹ淶��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '������Ϣʵ�����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '������Ϣʵ�����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '������Ϣʵ�����' );	// /txn7000201.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000201.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ淶��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000203" enctype="multipart/form-data">
  <freeze:block property="record" caption="���ӹ淶��Ϣ" width="95%" >
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
  
      <freeze:text property="standard_name" caption="�淶����" datatype="string" maxlength="100" colspan="2" style="width:95%" notnull="true" />
      <freeze:select property="standard_category" caption="�淶����" valueset="�淶����" style="width:95%" notnull="true"/>
      <freeze:text property="standard_category_no" caption="�淶�����" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:select property="standard_issued_unit" caption="�淶������λ"  valueset="�淶������λ" style="width:95%" notnull="true"/>
      <freeze:datebox property="standard_issued_time" caption="�淶����ʱ��" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:text property="file_name" caption="�ļ�����" datatype="string" maxlength="128" colspan="2" style="width:98%"/>
      <freeze:file property="file_path" caption="�ļ���" colspan="2" accept="*.doc,*.docx" style="width:98%"/>
      <freeze:textarea property="standard_range" caption="�淶��Χ" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="sort" caption="�����" datatype="string" maxlength="10" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
