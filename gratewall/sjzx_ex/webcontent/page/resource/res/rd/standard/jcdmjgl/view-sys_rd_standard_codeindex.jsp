<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="750" height="350">
<head>
<title>�鿴�������뼯��Ϣ</title>
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
function func_record_goBack()
{
	goBack();	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="�鿴�������뼯��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000406">


  <freeze:block  property="record" caption="�鿴�������뼯��Ϣ" width="95%">
      <freeze:cell property="sys_rd_standar_codeindex" caption="��ʶ��" datatype="string" style="width:95%"/>
      
      <freeze:cell property="codeindex_name" caption="���뼯����" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_codeindex_version" caption="�汾" datatype="string" style="width:95%"/>
      
      <freeze:cell property="description" caption="˵��" datatype="string" style="width:95%"/>
      <freeze:cell property="representation" caption="��ʾ" datatype="string" style="width:95%"/>
      
      <freeze:cell property="codeindex_category" caption="���뼯����" valueset="���뼯����" style="width:95%"/>
      <freeze:cell property="code_table" caption="�����" datatype="string" style="width:95%"/>
      
      <freeze:cell property="coding_methods" caption="���뷽��" style="width:98%"/>
      
  </freeze:block>
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
		<tr>
			<td class="btn_left"></td>
			<td><input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBack()" style='width:60px' >
			</td><td class="btn_right"></td>
		</tr></table>
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
