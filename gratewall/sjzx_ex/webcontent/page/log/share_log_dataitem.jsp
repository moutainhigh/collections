<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="450">
<head>
	<title>������־</title>
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
	<freeze:title caption="��ѯ���������Ϣ" />
	<freeze:errors />

	<freeze:form action="/txn6010006">
	<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="log_id" caption="����" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="�鿴������־"
			width="95%">
			      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
			<freeze:hidden property="log_id" caption="����"
				datatype="string" maxlength="100" style="width:95%" />
			<freeze:cell property="service_targets_name" caption="�����������" style="width:95%" />
			<freeze:cell property="service_type" caption="��������" valueset="��Դ����_����Դ����" style="width:95%" />
			<freeze:cell property="service_name" caption="��������" style="width:95%" />
			<freeze:cell property="return_codes" caption="����״̬" style="width:95%" />
			<freeze:cell property="service_start_time" caption="����ʼʱ��" style="width:95%" />
			<freeze:cell property="service_end_time" caption="�������ʱ��" style="width:95%" />
			<freeze:cell property="consume_time" caption="��������ʱ��" style="width:95%" />
			<freeze:cell property="record_start" caption="��ʼ��¼��" style="width:95%" />
			<freeze:cell property="record_end" caption="������¼��" style="width:95%" />
			<freeze:cell property="record_amount" caption="���η���������" style="width:95%" />
			<freeze:cell property="access_ip" caption="����IP" style="width:95%" />
			<freeze:cell property="patameter" caption="�������" style="width:95%" colspan="2"/>
			
			
			
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
