<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����ȫ�ļ�����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����ȫ�ļ���' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ȫ�ļ���' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ȫ�ļ���' );	// /txn50030101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn50030101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ȫ�ļ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50030103">
  <freeze:block property="record" caption="����ȫ�ļ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_search_config_id" caption="��������ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_svr_user_id" caption="�û�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:textarea property="permit_subject" caption="��Ȩ����" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:text property="create_by" caption="������" datatype="string" maxlength="10" minlength="1" style="width:95%"/>
      <freeze:text property="create_date" caption="��������" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="config_order" caption="����˳��" datatype="string" maxlength="" minlength="1" style="width:95%"/>
      <freeze:text property="is_pause" caption="�Ƿ���ͣ" datatype="string" maxlength="2" minlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
