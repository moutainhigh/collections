<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��������</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	//saveRecord( '', '����֪ͨͨ���' );
	saveAndExit('����ɹ�','����ʧ��',"txn53000001.do");
}

// ���沢����
function func_record_saveAndContinue()
{
    setFormFieldValue('record:sys_notice_state',0,'1');
	saveAndExit('����ɹ�','����ʧ��',"txn53000001.do");
}


// �� ��
function func_record_goBack()
{
	goBack();	// /txn50220001.do
}

// �����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��������"/>
<freeze:errors/>

<freeze:form action="/txn53000003" enctype="multipart/form-data">
  <freeze:block property="record" caption="��������" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_notice_id" caption="����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_notice_title" caption="����" datatype="string" maxlength="50" notnull="true" style="width:95%" colspan="2" />
      <freeze:textarea property="sys_notice_matter" caption="����" colspan="2" rows="4" maxlength="300" style="width:95%"/>
      <freeze:hidden property="sys_notice_promulgator" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_notice_org" caption="������λ" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:hidden property="sys_notice_date" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="sys_notice_state" caption="����״̬" datatype="string" maxlength="1" value="0" style="width:95%"/>
      <freeze:file property="sys_notice_filepath" caption="����" accept="*.*" style="width:95%" colspan="2"/>
  </freeze:block>
    <div id="showHtml"></div>
</freeze:form>
</freeze:body>
</freeze:html>