<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ȡ��־��ϸ��Ϣ</title>
</head>

<script language="javascript">
// �� ��
function func_record_goBack()
{
	goBack();	
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ȡ��־��ϸ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn501030008">

  <freeze:block property="record" caption="��ȡ��־��ϸ��Ϣ" width="95%">
      <freeze:button name="record_back" caption="����" enablerule="0" align="right" onclick="func_record_goBack();"/>
      <freeze:hidden property="workflow_id" caption="WorkFlow_Id��" style="width:95%" />
      <freeze:cell property="mapping_name" caption="ת�����ԣ�" colspan="2" align="left" style="width:95%"/> 
      <freeze:cell property="start_time" caption="��ʼʱ�䣺" align="left" style="width:95%" />
      <freeze:cell property="end_time" caption="����ʱ�䣺" align="left" style="width:95%" />
      <freeze:cell property="src_success_rows" caption="Դ�ɹ�������" align="left" style="width:95%" />  
      <freeze:cell property="src_failed_rows" caption="Դʧ��������" align="left" style="width:95%" />
      <freeze:cell property="targ_success_rows" caption="Ŀ��ɹ�������" align="left" style="width:95%"/>
      <freeze:cell property="targ_failed_rows" caption="Ŀ��ʧ��������" align="left" style="width:95%"/>
      <freeze:cell property="first_error_code" caption="�����ţ�" colspan="2" align="left" style="width:95%" />
      <freeze:cell property="first_error_msg" caption="����������" colspan="2" align="left" style="width:95%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
