<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����ļ������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����ļ����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����ļ����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ����' );	// /txn604050101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn604050101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����ļ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn604050103">
  <freeze:block property="record" caption="�����ļ������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="cclbbh_pk" caption="�ļ������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="cclbmc" caption="�����������" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="lbmcbb" caption="������ư汾" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="ccgml" caption="�洢��Ŀ¼" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="ejmlgz" caption="����Ŀ¼����" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
      <freeze:text property="gzfzzd" caption="�������ֶ�" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="zt" caption="״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="bz" caption="��ע" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
