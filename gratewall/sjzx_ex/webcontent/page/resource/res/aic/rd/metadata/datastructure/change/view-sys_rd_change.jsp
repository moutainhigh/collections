<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="450">
<head>
<title>�������</title>
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

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�������"/>
<freeze:errors/>

<freeze:form action="/txn80002306">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="�������" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="db_name" caption="����Դ����" datatype="string"  style="width:95%" />
      <freeze:cell property="db_username" caption="�û�����" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name" caption="�����" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name_cn" caption="�����������" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name" caption="�ֶ�" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name_cn" caption="�ֶ�������" datatype="string"  style="width:95%" />
      <freeze:cell property="change_item" caption="�������" valueset="�������" colspan="2" datatype="string"  style="width:95%" />
      <freeze:cell property="change_before" caption="���ǰ����" valueset="�ֶ���������" datatype="string"  style="width:95%" />
      <freeze:cell property="change_after" caption="���������" valueset="�ֶ���������" datatype="string"  style="width:95%" />
      <freeze:cell property="change_oprater" caption="�����" datatype="string"  style="width:95%" />
      <freeze:cell property="change_time" caption="���ʱ��" datatype="string"  style="width:95%" />
      <freeze:cell property="change_reason" caption="���ԭ��"  valueset="���ԭ��" colspan="2"  style="width:98%" />
      <freeze:hidden property="change_result" caption="������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
 			<input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
 </table>
  </p>
</freeze:form>
</freeze:body>
</freeze:html>
