<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĺ���������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����ҵ�������' );	// /txn203011.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn203011.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĺ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn203012">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="business_topics_id" caption="ҵ������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĺ���������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="business_topics_id" caption="ҵ������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_targets_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="topics_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="topics_no" caption="������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:textarea property="topics_desc" caption="��������" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="show_order" caption="��ʾ˳��" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
