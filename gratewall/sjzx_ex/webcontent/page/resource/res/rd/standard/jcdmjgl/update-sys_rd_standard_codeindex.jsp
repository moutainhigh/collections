<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="500">
<head>
<title>�޸Ĵ��뼯��Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����������뼯����' );	// /txn7000401.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn7000401.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĵ��뼯��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000402">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĵ��뼯��Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" datatype="string" maxlength="10" readonly="true" style="width:95%"/>
      <freeze:text property="codeindex_name" caption="���뼯����" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:select property="codeindex_category" caption="���뼯����" valueset="���뼯����" style="width:95%"/>
      <freeze:text property="representation" caption="��ʾ" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="standard_codeindex_version" caption="�汾" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="code_table" caption="�����" datatype="string" maxlength="64" style="width:95%"/>
      <freeze:textarea property="coding_methods" caption="���뷽��" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
