<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����ļ�ӳ����Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����ļ�ӳ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����ļ�ӳ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ�ӳ��' );	// /txn604060101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn604060101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����ļ�ӳ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn604060103">
  <freeze:block property="record" caption="�����ļ�ӳ����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ysbh_pk" caption="ӳ����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="cclbbh_pk" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="wybs" caption="Ψһ��ʶ" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:98%"/>
      <freeze:text property="wjmc" caption="�ļ�����" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:98%"/>
      <freeze:text property="wjzt" caption="�ļ�״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="cclj" caption="�洢·��" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="cjsj" caption="����ʱ��" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="scxgsj" caption="�ϴ��޸�ʱ��" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="bz" caption="��ע" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="ywbz" caption="ҵ��ע" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="xm_fk" caption="��Ŀfk" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
