<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�ϵͳʹ�����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '������־��������' );	// /txn620200101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620200101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�ϵͳʹ�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620200102" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_report_create_id" caption="��־����id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�ϵͳʹ�����������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_create_id" caption="��־����id" datatype="string" maxlength="32" style="width:95%"/>
   	  <freeze:file property="uploadfile" caption="�ļ���" colspan="2" accept="*.doc,*.docx" style="width:98%"/>
      <freeze:hidden property="report_type" caption="��������" colspan="2" valueset="��������" style="width:95%" />
      <freeze:hidden property="report_name" caption="��������"  maxlength="1000" style="width:98%"/>
      <freeze:hidden property="create_date" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="last_mender" caption="����޸���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="publish_date" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="publish_person" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="state" caption="״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="operate" caption="����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="filename" caption="�ļ���"  maxlength="1000" style="width:98%"/>
      <freeze:hidden property="path" caption="·��"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
