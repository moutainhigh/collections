<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="250">
<head>
<title>�鿴��Ϣʵ����Ϣ</title>
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
<freeze:title caption="�鿴��Ϣʵ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000216">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_table_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block   property="record" caption="�鿴��Ϣʵ����Ϣ" width="95%" columns="1">
      <freeze:cell property="sys_rd_standard_table_id" caption="����" datatype="string" style="width:95%" visible="false"/>
      <freeze:hidden property="sys_rd_standard_id" caption="�淶����" style="width:95%"/>
      <freeze:cell property="standard_name" caption="�淶����" style="width:95%"/>
      <!--<freeze:cell property="table_code" caption="��׼����" style="width:95%"/>-->
      <freeze:cell property="table_name" caption="��Ϣʵ������" style="width:95%"/>
      <freeze:cell property="table_belongs" caption="������ϵ" style="width:95%"/>
      <freeze:cell property="memo" caption="��ע" style="width:95%"/>
      <!--<freeze:cell property="sort" caption="����" style="width:95%"/>-->
      <!--<freeze:cell property="timestamp" caption="ʱ���" style="width:95%"/>-->
  </freeze:block>
  <p align="center" class="print-menu">
  <!--   <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
  <input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </td><td class="btn_right"></td></tr></table>
  </p>
  <!--   <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
