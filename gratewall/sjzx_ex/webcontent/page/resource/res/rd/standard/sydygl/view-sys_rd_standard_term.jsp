<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="700" height="270">
<head>
<title>�鿴������Ϣ</title>
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
<freeze:title caption="�鿴������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000106">
 
  <freeze:block  property="record" caption="�鿴������Ϣ" width="95%">
      <freeze:hidden property="sys_rd_standar_term_id" caption="����ID" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_cn" caption="��������" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_en" caption="����Ӣ��" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_definition" caption="���ﶨ��" style="width:98%"/>
      <freeze:cell property="memo" caption="��ע" style="width:98%"/>
      <!-- <freeze:hidden property="timeshtamp" caption="ʱ���" style="width:95%"/>-->
  </freeze:block>
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
