<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-print.jsp --%>
<freeze:html width="750" height="400">
<head>
<title>��ӡ���������Ϣ</title>
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
	goBackNoUpdate();	// /txn711001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="��ӡ���������Ϣ"/>
<freeze:errors/>

<!-- �û��Զ���ĺ��� -->
<script language="javascript">
function prepareHead( totalRow, pageRow, totalPage, currentPage )
{
	document.write( '<table border="0" width="100%"><tr><td>��ӡ���Գ���� [headscript] ����</td>' );
	document.write( '<td>��</td>' );
	document.write( '<td align="right">�� ' + currentPage + ' ҳ���ܹ� ' + totalPage + ' ҳ</td></tr></table>' );
}

function prepareTail( totalRow, pageRow, totalPage, currentPage )
{
	document.write( '<table border="0" width="100%"><tr><td>��ӡ���Գ���� [tailscript] ����</td>' );
	document.write( '<td>��</td>' );
	document.write( '<td align="right">�� ' + currentPage + ' ҳ���ܹ� ' + totalPage + ' ҳ</td></tr></table>' );
}
</script>

<freeze:form action="/txn711001">
  <p align="center">
  <freeze:print property="record" fixrow="true" pageRows="24" caption="<font size='5'><b>��ӡ���Գ����[caption]����</b></font>" note="����һ����ӡ���Ա�� [note] ����" headscript="prepareHead" tailscript="prepareTail" align="center" width="95%">
      <freeze:cell property="sys_feedback_id" caption="�������ID" style="width:19%" />
      <freeze:cell property="content" caption="�����������" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="����ʱ��" style="width:17%" />
      <freeze:cell property="author" caption="������" style="width:32%" />
      <freeze:cell property="status" caption="��Ч��־" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="������" style="width:32%" />
  </freeze:print></p>
  <p align="center" class="hide">
  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >
  <input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>
  <p align="center" class="hide">&nbsp;</p>
  
</freeze:form>
</freeze:body>
</freeze:html>
