<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="950" height="350">
<head>
<title>���ӻ�������Ԫ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '�����������Ԫ����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����������Ԫ����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������Ԫ����' );	// /txn7000301.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000301.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӻ�������Ԫ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000303">
  <freeze:block property="record" caption="���ӻ�������Ԫ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="identifier" caption="��ʶ��" datatype="string" maxlength="8" style="width:95%" notnull="true"/>
      <freeze:select property="standard_category" caption="�淶����" valueset="�淶����" style="width:95%" notnull="true"/>   
      <freeze:text property="cn_name" caption="��������" colspan="2"  maxlength="256" style="width:98%" notnull="true"/>
      <freeze:text property="en_name" caption="Ӣ������" colspan="2"  maxlength="256" style="width:98%"/>
      <freeze:text property="column_nane" caption="�ֶ�����" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:select property="data_type" caption="��������" valueset="�ֶ���������" style="width:95%" notnull="true"/>
      <freeze:text property="data_length" caption="���ݳ���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="data_format" caption="���ݸ�ʽ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="value_domain" caption="ֵ��" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="jc_standar_codeindex" caption="�������뼯��ʶ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="������λ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="synonyms" caption="ͬ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="version" caption="�汾" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:textarea property="representation" caption="��ʾ" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
