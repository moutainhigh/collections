<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html >
<head>
<title>�鿴�淶��Ϣ</title>
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
<freeze:title caption="�鿴�淶��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn7000206">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_standard_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="�鿴�淶��Ϣ" width="95%">
      <freeze:cell property="sys_rd_standard_id" caption="����" datatype="string" style="width:95%" visible="false"/>
      <freeze:cell property="standard_name" caption="�淶����" style="width:95%"/>
      <freeze:cell property="standard_issued_unit" caption="�淶������λ" valueset="�淶������λ"  style="width:95%"/>
      <freeze:cell property="standard_issued_time" caption="�淶����ʱ��" style="width:95%"/>
      <freeze:cell property="standard_category" caption="�淶����" valueset="�淶����" style="width:95%"/>
      <freeze:cell property="standard_category_no" caption="��׼�����" style="width:95%"/>
      <freeze:cell property="standard_range" caption="��׼��Χ" style="width:95%"/>
      <freeze:hidden property="file_name" caption="�淶�ļ�" style="width:95%"/>
      <freeze:hidden property="sort" caption="����" style="width:95%"/>
      <freeze:cell property="memo" caption="��ע" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:95%"/>
  </freeze:block>
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
  <input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </td><td class="btn_right"></td></tr></table>
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
