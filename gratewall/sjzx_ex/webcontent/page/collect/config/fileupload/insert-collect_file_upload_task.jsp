<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����ļ��ϴ��ɼ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����ļ��ϴ��ɼ���' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����ļ��ϴ��ɼ���' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ��ϴ��ɼ���' );	// /txn30301001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30301001.do
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����ļ��ϴ��ɼ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30301003">
  <freeze:block property="record" caption="�����ļ��ϴ��ɼ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"    style="width:95%"/>
      <freeze:text property="task_name" caption="��������" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="�ɼ���"  notnull="true" maxlength="32" valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:textarea property="task_description" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:file property="collect_file_name" caption="�ɼ��ļ�" accept=".xls,.txt,.mdb"style="width:80%" maxlength="200" colspan="2"/>
      
      
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="�ļ�����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="У�����ļ�����"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="У�����ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
