<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-view.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�鿴���������Ϣ</title>
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
<freeze:title caption="�鿴���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn711006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="�������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block theme="print" property="record" caption="�鿴���������Ϣ" width="95%">
      <freeze:cell property="sys_feedback_id" caption="�������ID" style="width:95%"/>
      <freeze:cell property="content" caption="�����������" colspan="2" datatype="text" style="width:98%"/>
      <freeze:cell property="publish_date" caption="����ʱ��" style="width:95%"/>
      <freeze:cell property="author" caption="������" style="width:95%"/>
      <freeze:cell property="status" caption="��Ч��־" datatype="string" style="width:95%" visible="false"/>
      <freeze:cell property="description" caption="������" colspan="2" style="width:98%"/>
  </freeze:block>
  <p align="center" class="hide">
  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >
  <input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
