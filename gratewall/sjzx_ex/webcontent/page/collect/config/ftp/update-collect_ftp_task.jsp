<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸�ftp������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����FTP�����','/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id") );	// /txn30201001.do
}

// �� ��
function func_record_goBack()
{
	//goBack();	// /txn30201001.do
	var page = new pageDefine( '/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id"), '��ѯ�ɼ�����');
	page.updateRecord();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ftp������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_task_id" caption="FTP����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ftp������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_task_id" caption="FTP����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="����ID��" datatype="string" readonly="true" style="width:95%"/>
      <freeze:text property="file_name_en" caption="�ļ�����"  readonly="true" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="file_name_cn" caption="�ļ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="��Ӧ�ɼ���" show="name" notnull="true" valueset="��Դ����_��������Ӧ�ɼ���" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="file_description" caption="�ļ�����" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="fj_path" caption="����path"  />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
