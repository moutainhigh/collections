<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '���������ͼͳ��' );	// /txn620100301.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620100301.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620100302">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="table_class_id" caption="��������id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="class_sort" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="class_state" caption="����״̬" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="count_date" caption="ͳ������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="count_full" caption="��ȫ��" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="count_incre" caption="������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="table_class_id" caption="��������id" datatype="string" maxlength="40" style="width:95%"/>
      <freeze:text property="sys_order" caption="���������ֶ�" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="table_order" caption="�������ֶ�" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="sort_order" caption="��������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="state_order" caption="״̬����" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
