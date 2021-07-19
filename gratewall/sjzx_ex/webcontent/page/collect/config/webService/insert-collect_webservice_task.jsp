<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����webservice������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	var item=getFormFieldValue('record:method_name_cn');
  		  if(!checkItemLength(item,"100","������������")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:collect_table');
  		  if(!checkItem(item,"100","��Ӧ�ɼ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:collect_mode');
  		  if(!checkItem(item,"100","�ɼ���ʽ")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:encrypt_mode');
  		  if(!checkItem(item,"100","���ܷ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:method_description');
  		  if(!checkItemLength(item,"2000","��������")){
  		    return false;
  		  }
	saveRecord( '', '����webservice�����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����webservice�����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����webservice�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn30102001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����webservice������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30102003">
  <freeze:block property="record" caption="����webservice������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="webservice_task_id" caption="WEBSERVICE����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="method_name_en" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="method_name_cn" caption="������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="collect_table" caption="��Ӧ�ɼ���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="�ɼ���ʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="is_encryption" caption="�Ƿ����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="encrypt_mode" caption="���ܷ�ʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="method_description" caption="��������" colspan="2" rows="2" maxlength="500" style="width:98%"/>
      <freeze:text property="method_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
