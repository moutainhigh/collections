<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="300">
<head>
<title>�޸�ftp������Ϣ</title>
</head>

<script language="javascript">



// �� ��
function func_record_goBack()
{
	goBack();	// /txn30201001.do
	//var page = new pageDefine( '/txn30101013.do?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id"), '��ѯ�ɼ�����');
	//page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴ftp�ļ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_task_id" caption="FTP����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴ftp�ļ���Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ftp_task_id" caption="FTP����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="����ID" datatype="string" style="width:95%"/>
      <freeze:cell property="file_name_en" caption="�ļ�����"  datatype="string"  style="width:95%"/>
      <freeze:cell property="file_name_cn" caption="�ļ���������" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_table" caption="��Ӧ�ɼ���" show="name"  valueset="��Դ����_�ɼ���"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" show="name"  valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:cell property="file_description" caption="�ļ�����"   style="width:98%"/>
      <freeze:cell property="file_sepeator" caption="�зָ���"    style="width:95%"/>
      <freeze:cell property="file_title_type" caption="����������"  show="name"  valueset="����������" style="width:95%"/>
      <freeze:cell property="month" caption="�·�"   valueset="��Դ����_�·�ѡ��" show="name"  style="width:95%" />
      <freeze:cell property="day_month" caption="����"   show="name" valueset="��Դ����_����ѡ��"  show="name" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
