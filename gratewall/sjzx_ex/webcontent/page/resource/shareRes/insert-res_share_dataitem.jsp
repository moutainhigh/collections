<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>���ӹ�����������Ϣ��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湲����������Ϣ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湲����������Ϣ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲����������Ϣ��' );	// /txn20301021.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn20301021.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���ӹ�����������Ϣ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20301023">
  <freeze:block property="record" caption="���ӹ�����������Ϣ��Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="share_dataitem_id" caption="����������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="share_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="��������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_type" caption="����������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="dataitem_long" caption="�������" datatype="string" maxlength="4" style="width:95%"/>
      <freeze:text property="code_table_name" caption="ϵͳ������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="code_table" caption="��Ӧ���뼯" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_key" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="dataitem_desc" caption="����" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="show_order" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
