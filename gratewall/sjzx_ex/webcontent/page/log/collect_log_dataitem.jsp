<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
	<title>�ɼ���־</title>
</head>

<script language="javascript">





// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	

}
function func_record_goBack()
{
	//window.close();	// /txn201001.do
	goBackNoUpdate();
}




_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="��ѯ�ɼ�������Ϣ" />
	<freeze:errors />

	<freeze:form action="/txn6011006">
	<freeze:frame property="select-key" width="95%">
      <freeze:hidden property="collect_joumal_id" caption="����" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="�鿴�ɼ���־"
			width="95%">
			      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
	  <freeze:hidden property="collect_joumal_id" caption="�ɼ���־ID" style="width:10%" visible="false"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%" />
      <freeze:cell property="task_name" caption="��������" style="width:95%" />
      <freeze:hidden property="service_targets_id" caption="�������ID" style="width:95%" />
      <freeze:cell property="service_targets_name" caption="�����������" style="width:95%" />
      <freeze:cell property="collect_type" caption="��������"  valueset="��Դ����_����Դ����" style="width:95%" />
      <freeze:hidden property="task_id" caption="����ID" style="width:95%" />
      <freeze:cell property="service_no" caption="������" style="width:95%" />
      <freeze:cell property="method_name_cn" caption="��������"  style="width:95%" />
      <freeze:cell property="collect_table_name" caption="�ɼ�������"  style="width:95%" /> 
      <freeze:cell property="task_start_time" caption="����ʼʱ��" style="width:95%" />
      <freeze:cell property="task_end_time" caption="�������ʱ��" style="width:95% " />
      <freeze:cell property="task_consume_time" caption="������ʱ��λ(��)" style="width:95%" />
      <freeze:cell property="return_codes" caption="���ؽ��" style="width:95%" />
      <freeze:cell property="collect_data_amount" caption="���βɼ�������" style="width:95%" />
      <freeze:hidden property="task_status" caption="ִ��״̬" valueset="��Դ����_�鵵����״̬" style="width:95%" />
     
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
