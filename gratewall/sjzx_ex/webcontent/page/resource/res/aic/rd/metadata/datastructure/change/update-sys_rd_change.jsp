<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="450">
<head>
<title>������</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	
	saveAndExit( '', '��������¼��' );	// /txn80002301.do
	
	
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002301.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="������"/>
<freeze:errors/>

<freeze:form action="/txn80002302">
 

  <freeze:block property="record" caption="������" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <!-- ƴ������λ�� -->
      <freeze:text property="db_name" caption="����Դ����" datatype="string"  colspan="2" style="width:98%" readonly="true"/>
      <freeze:text property="table_name" caption="�����" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="table_name_cn" caption="�����������" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="column_name" caption="�ֶ�" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="column_name_cn" caption="�ֶ�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="change_item" caption="�������"  colspan="2"  valueset="�������" readonly="true" style="width:98%" />
     <freeze:if test="${record.change_item==5}"> 
      <freeze:select property="change_before" caption="���ǰ����" valueset="�ֶ���������"  style="width:95%" readonly="true"/>
      <freeze:select property="change_after" caption="���������" valueset="�ֶ���������"  style="width:95%" readonly="true"/>
     </freeze:if>
     <freeze:if test="${record.change_item!=5}"> 
      <freeze:text property="change_before" caption="���ǰ����"  style="width:95%" readonly="true"/>
      <freeze:text property="change_after" caption="���������"   style="width:95%" readonly="true"/>
     </freeze:if>
      <freeze:text property="change_oprater" caption="�����" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="change_time" caption="���ʱ��" datatype="string" maxlength="32" style="width:95%" readonly="true"/>
      <freeze:select property="change_reason" caption="���ԭ��"  valueset="���ԭ��"  colspan="2" notnull="true" style="width:98%"/>
      
      <freeze:hidden property="sys_rd_table_id" caption="�������ID" style="width:10%" />
      <freeze:hidden property="sys_rd_change_id" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="db_username" caption="�û�����" datatype="string" maxlength="100" style="width:95%"/>
      
      <freeze:hidden property="change_result" caption="������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
