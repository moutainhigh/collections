<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="300">
<head>
<title>�鿴ָ������Ϣ</title>
<style>
body{
background:#ffffff;
}
</style>
</head>

<script language="javascript">

// �� ӡ
function func_record_printDocument()
{
	print(document);
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="�鿴ָ������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000226">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_column_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="�鿴ָ������Ϣ" width="95%">
      <freeze:hidden property="sys_rd_standard_column_id" caption="����" datatype="string" style="width:95%"/>
      <freeze:hidden property="sys_rd_standard_table_id" caption="��׼������" style="width:95%"/>
      <freeze:cell property="cn_name" caption="ָ��������" style="width:95%"/>
      <freeze:cell property="column_name" caption="�ֶ���" style="width:95%"/>
      <freeze:cell property="column_type" caption="��������" valueset="�ֶ���������" style="width:95%"/>
      <freeze:cell property="column_format" caption="��ʽ" style="width:95%"/>
      <freeze:cell property="code_identifier" caption="�����ʶ��" valueset="�������뼯����" style="width:95%"/>
       <freeze:cell property="data_element_identifier" caption="����Ԫ��ʶ��" valueset="����Ԫ��ʶ��" style="width:95%"/>
      <freeze:cell property="memo" caption="��ע" style="width:95%"/>
  </freeze:block>
  <p align="center" class="print-menu">
  <!--   <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
  </table>
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
